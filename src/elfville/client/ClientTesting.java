package elfville.client;

import java.io.*;
import java.net.*;

import elfville.protocol.*;

public class ClientTesting {
	public static void main(String[] args) {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		try {
			// open a socket connection
			Socket socket = new Socket("Heran-Yangs-MacBook-Pro.local", 8444);
			// open I/O streams for objects
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());

			for (int i = 0; i < 50; i++) {
				Message outMessage = new GetCentralBoardIn();

				// write the objects to the server
				oos.writeObject(outMessage);
				oos.flush();
				// read an object from the server
				Message inMessage = (Message) ois.readObject();

				System.out.println("The secret is: " + inMessage.secret);
				
				Thread.sleep(1000L);
			}
			oos.close();
			ois.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}

	}
}