package elfville.server;

import java.net.*;

import java.io.*;

import elfville.protocol.*;

public class Session implements Runnable {
	private Socket clientSocket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private CurrentUserProfile currentUser;

	public Session(Socket client) {
		clientSocket = client;
		currentUser = new CurrentUserProfile();
		try {
			ois = new ObjectInputStream(clientSocket.getInputStream());
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (Exception e1) {
			try {
				clientSocket.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return;
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Request request = (Request) ois.readObject();
				Response response = Routes.processRequest(request,
						currentUser);
				
				// set session authentication
				if ((request instanceof SignUpRequest) || (request instanceof SignInRequest)) {
					if (response.isOK())
						System.out.printf("the current user's id is: %d", currentUser.getCurrentUserId());
						
						//currentUserId = 1; // TODO: real user ID
				}
				
				oos.writeObject(response);
				oos.flush();
			} catch (EOFException e) {
				System.out.println("Client disconnected.");
				break;
			} catch (Exception e) {
				// Catch client errors
				e.printStackTrace();
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
