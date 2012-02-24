package elfville.client.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import elfville.client.SocketController;
import elfville.protocol.*;

public class RegistrationPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final JTextField usernameField = new JTextField();
	private final JButton registerButton = new JButton("Register");
	private final JLabel usernameLabel = new JLabel("Username");

	/**
	 * Create the panel.
	 */
	public RegistrationPanel() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Register"),
                BorderFactory.createEmptyBorder(5,5,5,5)));
		
		usernameLabel.setLabelFor(usernameField);
		registerButton.addActionListener(this);
		
		add(usernameLabel);
		add(usernameField);
		add(registerButton);
	}

	/**
	 * Attempt to register.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		SignUpRequest req = new SignUpRequest(usernameField.getText());
		try {
			SignUpResponse resp = SocketController.send(req);
			if (resp.status == Response.Status.SUCCESS) {
				CentralBoard b = (CentralBoard) ClientWindow.switchScreen("central_board");
				b.load(SocketController.send(new CentralBoardRequest()));
			} else {
				ClientWindow.showError(this, resp.message, "Registration error");
			}
		} catch (IOException e) {
			ClientWindow.showConnectionError(this);
		}
	}

}
