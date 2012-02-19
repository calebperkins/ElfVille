package elfville.client.views;

import javax.swing.*;

public class WelcomeScreen extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public WelcomeScreen() {
		add(new JLabel("Welcome to Elfville"));
		add(new LoginPanel());
		// TODO add registration panel
	}

}
