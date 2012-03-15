package elfville.client.views;

import javax.swing.JLabel;

import elfville.client.ClientWindow;
import elfville.client.SocketController;
import elfville.client.views.subcomponents.LoginPanel;
import elfville.client.views.subcomponents.RegistrationPanel;
import elfville.protocol.Response;

public class WelcomeScreen extends Board {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a panel with login and registration options.
	 */
	public WelcomeScreen(ClientWindow clientWindow,
			SocketController socketController) {
		super(clientWindow, socketController);
		add(new JLabel("Welcome to Elfville"));
		add(new LoginPanel(this));
		add(new RegistrationPanel(this));
	}

	@Override
	public void handleRequestSuccess(Response resp) {
		// then we've logged in or signed up, show central board
		new CentralBoard(clientWindow, socketController);
	}

	@Override
	public void refresh() {
		new WelcomeScreen(clientWindow, socketController);
	}

}
