package elfville.client;

import javax.swing.*;

/**
 * Main entry point for client.
 * 
 * @author caleb
 * 
 */
public class Client extends JFrame {

	private static final long serialVersionUID = 2917659626432208915L;

	public Client() {
		setTitle("ElfVille");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Client ex = new Client();
				ex.setVisible(true);
			}
		});
	}

}
