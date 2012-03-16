package elfville.server;

import java.net.*;
import java.security.GeneralSecurityException;

import java.io.*;

import javax.crypto.*;

import elfville.protocol.*;
import elfville.protocol.utils.SharedKeyCipher;
import elfville.server.controller.ControllerUtils;

public class Session implements Runnable {
	private Socket clientSocket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private CurrentUserProfile currentUser;

	private int consecutive_failures = 0;

	private static int TIMEOUT_IN_MS = 10 * 1000; // auto log out after 10
														// seconds
	private static int CONSECUTIVE_FAILURE_LIMIT = 5;

	private SharedKeyCipher sks = null;

	public Session(Socket client) throws IOException {
		clientSocket = client;
		clientSocket.setSoTimeout(TIMEOUT_IN_MS);
		currentUser = new CurrentUserProfile(); // a new user, not logged in
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

				SealedObject encrypted_request = (SealedObject) ois
						.readObject();

				if (sks == null) {
					request = PKcipher.instance.decrypt(encrypted_request);
					sks = new SharedKeyCipher(
							((SignInRequest) request).getSharedKey());
				} else {
					try{
						request = sks.decryptWithSharedKey(encrypted_request);
					} catch (javax.crypto.BadPaddingException e) {
						break;
					}
				}

				Response response = Routes.processRequest(request, currentUser);

				if (response.isOK()) {
					consecutive_failures = 0;
				} else {
					consecutive_failures++;
					if (consecutive_failures >= CONSECUTIVE_FAILURE_LIMIT) {
						break;
					}
				}

				// set session authentication
				if ((request instanceof SignUpRequest)
						|| (request instanceof SignInRequest)) {
					if (response.isOK())
						System.out.printf("the current user's id is: %d\n",
								currentUser.getCurrentUserId());
				}

				response.nonce = request.getNonce() + 1; // increment nonce

				SealedObject encrypted_response = sks.encrypt(response);
				oos.writeObject(encrypted_response);
				oos.flush();
			}
			// if the user has been idle too long, log him out
		} catch (SocketTimeoutException e) {
			System.out.println("User session timed out.");
		} catch (EOFException e) {
			ControllerUtils.signOut(currentUser);
			System.out.println("Client disconnected.");
		} catch (IOException e) {
			System.out.println("Client connection broke.");
		} catch (ClassNotFoundException e) {
			System.out.println("Client sent malformed request.");
		} catch (GeneralSecurityException e) {
			System.out.println("Client sent bad key.");
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
