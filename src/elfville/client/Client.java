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
		if (args.length != 3) {
			System.err.println("Usage: host port /path/to/elfville.pub.der");
			System.exit(-1);
		}

		final String host = args[0];
		final int port = Integer.parseInt(args[1]);
		
		PublicKeyCipher.instance = new PublicKeyCipher(args[2]);

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
