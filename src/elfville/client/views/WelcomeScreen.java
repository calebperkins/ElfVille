package elfville.client.views;

import javax.swing.*;

import elfville.client.ClientWindow;
import elfville.client.SocketController;
import elfville.client.views.subcomponents.LoginPanel;
import elfville.client.views.subcomponents.RegistrationPanel;

public class WelcomeScreen extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a panel with login and registration options.
	 */
	public WelcomeScreen(SocketController socketController,
			ClientWindow clientWindow) {
		super();
		add(new JLabel("Welcome to Elfville"));
		add(new LoginPanel(socketController, clientWindow));
		add(new RegistrationPanel(socketController, clientWindow));
	}

}
