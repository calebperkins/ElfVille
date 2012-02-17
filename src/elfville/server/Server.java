package elfville.server;

import java.net.*;
import java.io.*;

import elfville.server.controller.Controller;

public class Server {
	
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(8444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 8444.");
            System.exit(-1);
        }

        // Initialize database
        Database.DB = new Database();
        // Read database from Disk
        // Database.db = new Database("OnDiskLocation");
        
        // Support Multiple Clients
        while (listening) {
        	// Socket acceptedSocket = serverSocket.accept();
        	// System.out.println("Accepted a cennection from: " + acceptedSocket.getInetAddress());
        	new ServerThread(serverSocket.accept()).start();
        }

        serverSocket.close();
    }
}
