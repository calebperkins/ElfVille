package elfville.client;

import java.awt.BorderLayout;

import javax.swing.*;

import elfville.client.views.WelcomeScreen;
import elfville.client.views.subcomponents.NavigationScreen;

public class ClientWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	// TODO - remove global variable, or at least find a way to make it final
	public static ClientWindow window;

	private static final JPanel navigation = new NavigationScreen();
	private static JPanel main = new JPanel();
	private JPanel current;

	public static void switchScreen(JPanel next) {
		window.getContentPane().removeAll();
		main = new JPanel();
		main.setLayout(new BorderLayout());
		window.getContentPane().add(main);
		// following line only needed if we implement logout
		//if (!(next instanceof WelcomeScreen)){
		main.add(navigation, BorderLayout.PAGE_START);
		//}
		main.add(next, BorderLayout.CENTER);
		window.current = next;
		window.validate();
	}

	/**
	 * Create the frame.
	 */
	public ClientWindow() {
		super("ElfVille");
		setBounds(100, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		current = new WelcomeScreen();

		main.setLayout(new BorderLayout());
		this.getContentPane().add(main);
		//main.add(navigation, BorderLayout.PAGE_START);
		main.add(current, BorderLayout.CENTER);
		window = this;
	}

	/**
	 * Used when a socket error occurs. Shows an alert dialog.
	 */
	public static void showConnectionError() {
		showError("Socket connection broke. Try again.", "Connection error");
		System.exit(-1);
	}

	public static void showError(String msg, String title) {
		JOptionPane.showMessageDialog(null, msg, title,
				JOptionPane.ERROR_MESSAGE);
	}

}
