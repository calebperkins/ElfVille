package elfville.client;

import java.awt.*;
import java.io.IOException;

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
					ClientWindow window = new ClientWindow();
					SocketController.initialize();
					window.setVisible(true);
				} catch (IOException e) {
					ClientWindow.showConnectionError();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
