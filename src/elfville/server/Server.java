package elfville.server;

import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.io.*;

public class Server {

	/**
	 * Starts a server. Use the first argument to provide a port, the second,
	 * the path to the database.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = null;
		boolean listening = true;

		final int port;

		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 8444;
		}

		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + port);
			System.exit(-1);
		}

		// Initialize database
		if (args.length == 2) {
			Database.load(args[1]);
		} else {
			System.out
					.println("No database specified. Creating an empty one with no persistance.");
		}

		ExecutorService pool = Executors.newCachedThreadPool();

		System.out.println("Server now listening...");

		// Support Multiple Clients
		while (listening) {
			try {
				pool.execute(new Session(serverSocket.accept()));
			} catch (IOException e) { // couldn't set timeout? drop them.
				continue;
			}
		}

		serverSocket.close();
	}
}
