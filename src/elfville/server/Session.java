package elfville.server;

import java.net.*;

import java.io.*;

import elfville.protocol.*;

public class Session implements Runnable {
	private Socket clientSocket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private CurrentUserProfile currentUser;

	// private SecretKey shared_key; // TODO

	public Session(Socket client) {
		clientSocket = client;
		currentUser = new CurrentUserProfile(); // a new user, not logged in
		try {
			ois = new ObjectInputStream(clientSocket.getInputStream());
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (IOException e1) {
			try {
				clientSocket.close();
			} catch (IOException e2) {
				System.err.println(e2.getMessage());
			}
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Request request = (Request) ois.readObject();
				Response response = Routes.processRequest(request, currentUser);

				// set session authentication
				if ((request instanceof SignUpRequest)
						|| (request instanceof SignInRequest)) {
					if (response.isOK())
						System.out.printf("the current user's id is: %d\n",
								currentUser.getCurrentUserId());
				}

				oos.writeObject(response);
				oos.flush();
			} catch (EOFException e) {
				System.out.println("Client disconnected.");
				break;
			} catch (IOException e) {
				System.err.println("Client connection broke.");
				break;
			} catch (ClassNotFoundException e) {
				System.out.println("Client sent malformed request.");
				break;
			}
		}

		try {
			// close connections
			ois.close();
			oos.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
