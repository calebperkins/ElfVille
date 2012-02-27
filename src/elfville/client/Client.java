package elfville.client;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;

/**
 * Main entry point for client.
 * 
 * @author Caleb Perkins
 * 
 */
public class Client {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					SocketController socketController = new SocketController("localhost", 8444);
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
