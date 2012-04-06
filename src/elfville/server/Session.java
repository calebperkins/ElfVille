package elfville.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.GeneralSecurityException;

import javax.crypto.SealedObject;

import elfville.protocol.Request;
import elfville.protocol.Response;
import elfville.protocol.SignInRequest;
import elfville.protocol.SignUpRequest;
import elfville.protocol.utils.SharedKeyCipher;
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
							((SignInRequest) request).getSharedKey());
					nonce = ((SignInRequest) request).getSharedNonce();
					// -1 is for compatibility with below if-statement
				} else {
					try {
						request = sks.decryptWithSharedKey(encrypted_request);
					} catch (javax.crypto.BadPaddingException e) {
						break;
					}
					if (nonce + 1 != request.getNonce()) {
						//we should not be telling the adversary what the nonce should be !
						response = new Response(Response.Status.FAILURE, "bad nonce");
					}
					nonce += 1;
				}
				nonce += 1;
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
						System.out.printf("the current user's id is: %d\n",
								currentUser.getCurrentUserId());
				}

				SealedObject encrypted_response = sks.encrypt(response);
				oos.writeObject(encrypted_response);
				oos.flush();
				
				if (response.isOK()) {
					consecutive_failures = 0;
				} else {
					//reset the shared key if the sign up or sign in request failed.  this allows the client to retry
					//with a new key
					if ((request instanceof SignUpRequest)
							|| (request instanceof SignInRequest)){
						sks= null;
					}
					
					consecutive_failures++;
					if (consecutive_failures >= CONSECUTIVE_FAILURE_LIMIT) {
						break;
					}
				}
				
			}
			// if the user has been idle too long, log him out
		} catch (SocketTimeoutException e) {
			System.out.println("User session timed out.");
			e.printStackTrace();
		} catch (EOFException e) {
			ControllerUtils.signOut(currentUser);
			System.out.println("Client disconnected.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Client connection broke.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Client sent malformed request.");
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			System.out.println("Client sent bad key.");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
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
