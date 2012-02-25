package elfville.server;

import java.net.*;

import java.io.*;

import elfville.protocol.*;

public class Session implements Runnable {
	private Socket clientSocket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public int currentUserId; // TODO: make this better

	public Session(Socket client) {
		clientSocket = client;
		currentUserId = -1;
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
						currentUserId);
				
				// set session authentication
				if ((request instanceof SignUpRequest) || (request instanceof SignInRequest)) {
					if (response.isOK())
						currentUserId = 1; // TODO: real user ID
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
