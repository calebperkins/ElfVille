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
			JOptionPane.showMessageDialog(frame, "Could not establish connection to server. Exiting.", "Connection error", JOptionPane.ERROR_MESSAGE);;
			System.exit(-1);
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

}
