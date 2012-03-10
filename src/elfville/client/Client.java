package elfville.client;

import java.awt.*;
import java.io.IOException;

/**
 * Main entry point for client.
 * 
 */
public class Client {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		final String host;
		final int port;

		if (args.length == 2) {
			host = args[0];
			port = Integer.parseInt(args[1]);
		} else {
			host = "localhost";
			port = 8444;
		}

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
