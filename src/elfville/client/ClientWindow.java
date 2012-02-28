package elfville.client;

import java.awt.BorderLayout;

import javax.swing.*;

import elfville.client.views.WelcomeScreen;
import elfville.client.views.subcomponents.NavigationScreen;

public class ClientWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private final JPanel navigation;
	private JPanel main;
	private JPanel current;
	private SocketController socketController;

	/**
	 * Create the frame.
	 */
	public ClientWindow(SocketController socketController) {
		super("ElfVille");
		setBounds(100, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.socketController = socketController;
		
		current = new WelcomeScreen(socketController, this);
		navigation = new NavigationScreen(this, socketController);
		main = new JPanel();

		main.setLayout(new BorderLayout());
		this.getContentPane().add(main);
		//main.add(navigation, BorderLayout.PAGE_START);
		main.add(current, BorderLayout.CENTER);
	}
	
	public void switchScreen(JPanel next) {
		getContentPane().removeAll();
		main = new JPanel();
		main.setLayout(new BorderLayout());
		getContentPane().add(main);
		// following line only needed if we implement logout
		//if (!(next instanceof WelcomeScreen)){
		main.add(navigation, BorderLayout.PAGE_START);
		//}
		main.add(next, BorderLayout.CENTER);
		current = next;
		validate();
	}

	/**
	 * Used when a socket error occurs. Shows an alert dialog.
	 */
	public void showConnectionError() {
		showError("Socket connection broke. Try again.", "Connection error");
		System.exit(-1);
	}

	public void showError(String msg, String title) {
		JOptionPane.showMessageDialog(null, msg, title,
				JOptionPane.ERROR_MESSAGE);
	}

	public SocketController getSocketController() {
		return socketController;
	}

	public void setSocketController(SocketController socketController) {
		this.socketController = socketController;
	}

}
