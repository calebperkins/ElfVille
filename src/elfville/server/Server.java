package elfville.server;

import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.*;

public class Server {

	/**
	 * Starts a server. Use the first argument to provide a path to the database
	 * file, the second, to provide a port.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = null;
		boolean listening = true;
		
		final int port;
		
		if (args.length == 2) {
			port = Integer.parseInt(args[1]);
		} else {
			port = 8444;
		}

		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + port);
			System.exit(-1);
		}

		ScheduledExecutorService scheduler = Executors
				.newSingleThreadScheduledExecutor();

		// Initialize database
		if (args.length > 0) {
			Database.DB = Database.load(args[0]);
			scheduler.scheduleAtFixedRate(new DatabaseBackup(args[0]), 30, 30,
					TimeUnit.SECONDS);
		} else {
			Database.DB = new Database();
			System.out
					.println("No database specified. Creating an empty one with no persistance.");
		}

		ExecutorService pool = Executors.newCachedThreadPool();

		System.out.println("Server now listening...");

		// Support Multiple Clients
		while (listening) {
			pool.execute(new Session(serverSocket.accept()));
		}

		serverSocket.close();
	}
}
