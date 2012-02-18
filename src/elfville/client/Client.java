package elfville.client;

import java.awt.*;
import javax.swing.*;
import elfville.client.views.*;

/**
 * Main entry point for client.
 * @author Caleb Perkins
 *
 */
public class Client {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Client() {
		initialize();
		try {
			SocketController.initialize();	
		} catch (Exception e) {
			showConnectionError(frame);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("ElfVille");
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		frame.add(new LoginPanel());
	}
	
	/**
	 * Used when a socket error occurs. Shows an alert dialog.
	 * @param c
	 */
	public static void showConnectionError(Component c) {
		JOptionPane.showMessageDialog(c, "Socket connection broke. Try again.", "Connection error", JOptionPane.ERROR_MESSAGE);
		System.exit(-1);
	}

}
