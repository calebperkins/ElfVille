package elfville.client;

import java.awt.*;
import java.io.IOException;
import elfville.client.views.*;

/**
 * Main entry point for client.
 * @author Caleb Perkins
 *
 */
public class Client {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientWindow window = new ClientWindow();
					SocketController.initialize();
					window.setVisible(true);
				} catch (IOException e) {
					ClientWindow.showConnectionError(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
