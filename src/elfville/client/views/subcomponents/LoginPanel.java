package elfville.client.views.subcomponents;

import javax.swing.*;

import elfville.client.ClientWindow;
import elfville.client.SocketController;
import elfville.client.views.CentralBoard;
import elfville.protocol.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Collection of controls to allow user to login.
 * 
 */
public class LoginPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private SocketController socketController;
	private ClientWindow clientWindow;

	/**
	 * Create the panel.
	 */
	public LoginPanel(SocketController socketController,
			ClientWindow clientWindow) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Login"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		this.socketController = socketController;
		this.clientWindow = clientWindow;

		usernameField = new JTextField();
		passwordField = new JPasswordField();
		loginButton = new JButton("Login");
		loginButton.addActionListener(this);

		usernameLabel = new JLabel("Username");
		usernameLabel.setLabelFor(usernameField);

		passwordLabel = new JLabel("Password");
		passwordLabel.setLabelFor(passwordField);

		add(usernameLabel);
		add(usernameField);
		// add(passwordLabel);
		// add(passwordField);
		add(loginButton);

	}

	/**
	 * Log user in
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		SignInRequest req = new SignInRequest(usernameField.getText());

		try {
			Response m = socketController.send(req);
			if (m.status == Response.Status.SUCCESS) {
				new CentralBoard(clientWindow, socketController);
			} else {
				clientWindow.showError(m.message, "Login error");
			}
		} catch (IOException e1) {
			clientWindow.showConnectionError();
		}
	}

}
