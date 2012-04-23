package elfville.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.SealedObject;

import elfville.protocol.Request;
import elfville.protocol.Response;
import elfville.protocol.SignInRequest;
import elfville.protocol.SignUpRequest;
import elfville.protocol.utils.SharedKeyCipher;
import elfville.server.classloader.LoadClassRequestQueue;
import elfville.server.controller.ControllerUtils;

public class Session implements Runnable {
	private Socket clientSocket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private CurrentUserProfile currentUser;
	private int nonce;

	private int consecutive_failures = 0;

	private static int TIMEOUT_IN_MS = 10 * 60 * 1000; // auto log out after 10
														// seconds
	private static int CONSECUTIVE_FAILURE_LIMIT = 11;

	private SharedKeyCipher sks = null;

	public final static Logger logger = Logger.getLogger("sessions");

	public void removeCipher() {
		sks = null;
	}

	public Session(Socket client) throws IOException {
		clientSocket = client;
		clientSocket.setSoTimeout(TIMEOUT_IN_MS);
		currentUser = new CurrentUserProfile(this); // a new user, not logged in
		try {
			ois = new ObjectInputStream(clientSocket.getInputStream());
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			clientSocket.close();
			throw e;
		}
		startUserClassHandler();
	}
	
	public void startUserClassHandler() {
		UserClassHandler classHandler = new UserClassHandler(currentUser);
		classHandler.start();
	}

	@Override
	public String toString() {
		return currentUser + " at port " + clientSocket.getPort();
	}

	@Override
	public void run() {
		try {
			while (true) {
				Request request;
				Response response = null;

				SealedObject encrypted_request = (SealedObject) ois
						.readObject();

				if (sks == null) {
					request = PublicKeyCipher.instance
							.decrypt(encrypted_request);
					sks = new SharedKeyCipher(
							((SignInRequest) request).getSharedKey(),
							((SignInRequest) request).getIV());
					nonce = ((SignInRequest) request).getNonce(); // init nonce
				} else {
					try {
						request = sks.decryptWithSharedKey(encrypted_request);
					} catch (javax.crypto.BadPaddingException e) {
						break;
					}
					nonce += 1; // compute expected request nonce
					if (nonce != request.getNonce()) {
						// TODO we should not be telling the adversary what the
						// nonce should be ! - someone
						// Aaron: I do not understand this complaint.
						response = new Response(Response.Status.FAILURE,
								"bad nonce");
					}
				}
				nonce += 1; // compute proper response nonce
				if (null == response) {
					response = Routes.processRequest(request, currentUser);
				}
				response.setNonce(nonce);
				// TODO fix my bad nonce code above because I (Aaron) don't know
				// your server stuff. This works though

				// set session authentication
				if ((request instanceof SignUpRequest)
						|| (request instanceof SignInRequest)) {
					if (response.isOK())
						logger.info(this + " logged in.");
				}

				response.setChecksum();
				SealedObject encrypted_response = sks.encrypt(response);
				oos.writeObject(encrypted_response);
				oos.flush();

				if (response.isOK()) {
					consecutive_failures = 0;
				} else {
					// reset the shared key if the sign up or sign in request
					// failed. this allows the client to retry
					// with a new key
					if ((request instanceof SignUpRequest)
							|| (request instanceof SignInRequest)) {
						sks = null;
					}

					consecutive_failures++;
					if (consecutive_failures >= CONSECUTIVE_FAILURE_LIMIT) {
						break;
					}
				}

			}
			// if the user has been idle too long, log him out
		} catch (SocketTimeoutException e) {
			logger.info(this + " session timed out.");
		} catch (EOFException e) {
			logger.info(this + " disconnected.");
			ControllerUtils.signOut(currentUser);
		} catch (IOException e) {
			logger.info(this + " connection broke.");
		} catch (ClassNotFoundException e) {
			logger.info(this + " sent malformed request.");
		} catch (GeneralSecurityException e) {
			logger.info(this + " sent bad key.");
		} catch (Exception e) {
			logger.log(Level.WARNING, this + " unknown exception", e);
		} finally {
			currentUser.logOut();
		}

		try { // close connections
			ois.close();
			oos.close();
			clientSocket.close();
		} catch (IOException e) {
		} finally {
			currentUser.logOut();
		}
	}
}
