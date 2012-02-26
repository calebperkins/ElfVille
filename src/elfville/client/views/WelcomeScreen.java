package elfville.client.views;

import javax.swing.*;

import elfville.client.views.subcomponents.LoginPanel;
import elfville.client.views.subcomponents.RegistrationPanel;

public class WelcomeScreen extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public WelcomeScreen() {
		add(new JLabel("Welcome to Elfville"));
		add(new LoginPanel());
		add(new RegistrationPanel());
	}

}
