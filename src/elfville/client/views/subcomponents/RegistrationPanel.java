package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import elfville.client.ClientWindow;
import elfville.client.SocketController;
import elfville.client.views.CentralBoard;
import elfville.protocol.Response;
import elfville.protocol.SignUpRequest;

/**
 * Provides the user with the ability to request the server
 * register a new elf with a new name and description. 
 *
 */
public class RegistrationPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final JTextField usernameField = new JTextField();
	private final JTextArea descriptionArea = new JTextArea();
	private final JButton registerButton = new JButton("Register");
	private final JLabel usernameLabel = new JLabel("Username");
	private final JLabel descriptionLabel = new JLabel("Elf Description");
	private SocketController socketController;
	private ClientWindow clientWindow;
	
	/**
	 * Create the panel.
	 */
	public RegistrationPanel(SocketController socketController, ClientWindow clientWindow) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Register"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));


		this.socketController = socketController;
		this.clientWindow = clientWindow;

		usernameLabel.setLabelFor(usernameField);
		descriptionLabel.setLabelFor(descriptionArea);
		registerButton.addActionListener(this);

		add(usernameLabel);
		add(usernameField);
		add(descriptionLabel);
		add(descriptionArea);
		add(registerButton);
	}

	/**
	 * Attempt to register.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		SignUpRequest req = new SignUpRequest(usernameField.getText(), descriptionArea.getText());
		try {
			Response resp = socketController.send(req);
			if (resp.status == Response.Status.SUCCESS) {
				new CentralBoard(clientWindow, socketController);
			} else {
				clientWindow.showError(resp.message, "Registration error:");
			}
		} catch (IOException e) {
			clientWindow.showConnectionError();
		}
	}

}
