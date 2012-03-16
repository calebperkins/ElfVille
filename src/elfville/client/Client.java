package elfville.client;

import java.awt.EventQueue;
import java.io.IOException;

/**
 * Main entry point for client.
 * 
 */
public class Client {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception {
		final String host;
		final int port;
		String publicKeyPath;
		if (args.length != 3) {
			System.out.println("Usage: host port /path/to/elfville.pub.der");
			System.out.println("Not enough arguments. Default: localhost 8444 resources/elfville.pub.der");
			host = "localhost";
			port = 8444;
			publicKeyPath = "resources/elfville.pub.der"; 
		} else {
			host = args[0];
			port = Integer.parseInt(args[1]);
			publicKeyPath = args[2];
		}
		
		PublicKeyCipher.instance = new PublicKeyCipher(publicKeyPath);

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				try {
					SocketController socketController = new SocketController(
							host, port);
					ClientWindow window = new ClientWindow(socketController);
					window.setVisible(true);
				} catch (IOException e) {
					ClientWindow window = new ClientWindow(null);
					window.showConnectionError();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
