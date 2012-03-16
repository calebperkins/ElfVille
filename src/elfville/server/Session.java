package elfville.server;

import java.net.*;
import java.security.GeneralSecurityException;

import java.io.*;

import javax.crypto.*;

import elfville.protocol.*;
import elfville.protocol.utils.SharedKeyCipher;

public class Session implements Runnable {
	private Socket clientSocket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private CurrentUserProfile currentUser;

	private int consecutive_failures = 0;

	private static int TIMEOUT_IN_MS = 15*60 * 1000;  // auto log out after 10 seconds
	private static int CONSECUTIVE_FAILURE_LIMIT = 5;

	private static boolean ENCRYPTION_ENABLED = true;

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

				if (ENCRYPTION_ENABLED) {
					SealedObject encrypted_request = (SealedObject) ois
							.readObject();

					if (sks == null) {
						request = PKcipher.instance.decrypt(encrypted_request);
						sks = new SharedKeyCipher(
								((SignInRequest) request).getSharedKey());
					} else {
						request = sks.decryptWithSharedKey(encrypted_request);
					}
				} else {
					request = (Request) ois.readObject();
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

				if (ENCRYPTION_ENABLED) {
					SealedObject encrypted_response = sks.encrypt(response);
					oos.writeObject(encrypted_response);
				} else {
					oos.writeObject(response);
				}

				oos.flush();
			}
			//if the user has been idle too long, log him out
		} catch (SocketTimeoutException e){
			currentUser.logOut();
			System.out.println("User session timed out.");
		} catch (EOFException e) {
			System.out.println("Client disconnected.");
		} catch (IOException e) {
			System.out.println("Client connection broke.");
		} catch (ClassNotFoundException e) {
			System.out.println("Client sent malformed request.");
		} catch (GeneralSecurityException e) {
			System.out.println("Client sent bad key.");
			e.printStackTrace();
		}

		try { // close connections
			ois.close();
			oos.close();
			clientSocket.close();
		} catch (IOException e) {
		}
	}
}
