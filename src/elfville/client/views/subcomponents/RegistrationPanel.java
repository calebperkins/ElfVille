package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;

import javax.crypto.SecretKey;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import elfville.client.views.Board;
import elfville.protocol.SignUpRequest;
import elfville.protocol.utils.SharedKeyCipher;

/**
 * Provides the user with the ability to request the server register a new elf
 * with a new name and description.
 * 
 */
public class RegistrationPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final JTextField usernameField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private final JTextArea descriptionArea = new JTextArea();
	private final JButton registerButton = new JButton("Register");
	private final JLabel usernameLabel = new JLabel("Username");
	private JLabel passwordLabel = new JLabel("Password");
	private final JLabel descriptionLabel = new JLabel("Elf Description");
	private Board board;

	/**
	 * Create the panel.
	 */
	public RegistrationPanel(Board board) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Register"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		this.board = board;

		usernameLabel.setLabelFor(usernameField);
		passwordLabel.setLabelFor(passwordField);
		descriptionLabel.setLabelFor(descriptionArea);
		registerButton.addActionListener(this);

		add(usernameLabel);
		add(usernameField);
		add(passwordLabel);
		add(passwordField);
		add(descriptionLabel);
		add(descriptionArea);
		add(registerButton);
	}

	/**
	 * Attempt to register.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// create new shared key
		SharedKeyCipher cipher;
		SecretKey shared_key;
		try {
			// create new shared key
			cipher = new SharedKeyCipher();
			shared_key = cipher.getNewSharedKey();
			board.getSocketController().setCipher(cipher);
			int nonce = SecureRandom.getInstance("SHA1PRNG").nextInt();
			board.getSocketController().setNonce(nonce);

			// create request
			SignUpRequest req = new SignUpRequest(usernameField.getText(),
					passwordField.getPassword(), shared_key, nonce,
					descriptionArea.getText());
			board.getSocketController().sendRequest(req, board,
					"Registration error", board);
			req.zeroOutPassword();
		} catch (Exception e2) {
			// TODO Hmm... not much we really can do to recover
			// though I guess we could report an error, and ask them
			// to try again and refresh this "board" (welcome screen)
			e2.printStackTrace();
		}
	}

}
