package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import elfville.client.SocketController;
import elfville.client.views.Board;
import elfville.protocol.Response;
import elfville.protocol.SignUpRequest;
import elfville.protocol.UpdateProfileRequest;
import elfville.protocol.utils.SharedKeyCipher;

/**
 * Provides the user with the ability to request the server register a new elf
 * with a new name and description.
 * 
 */
public class RegistrationPanel extends JPanel implements ActionListener,
		SocketController.SuccessFunction {
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
		// Some helpful client side rejection, what the server does internally,
		// but nice to do it here as well (server does password checks, but
		// those cannot be done here because they rely on regular expressions
		// which can't be done on a char array and if we convert to string then
		// we lose the ability to zero it out and are instead relying on the
		// garbage collector to remove it from memory.

		String username = usernameField.getText();

		// 20 char max, 4 char min
		if (20 < username.length() || username.length() < 4) {
			System.err.println("User name must be between 4 and 20 characters");
			return;
		}

		if (username.contains(" ")) {
			System.err.println("No spaces in the username");
			return;
		}

		// make sure the username contains only letters and numbers
		Pattern p = Pattern.compile("[^a-z0-9]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(username);

		if (m.matches()) {
			System.err.println("Only letters and numbers in the username");
			return;
		}

		if (descriptionArea.getText().equals("")) {
			System.err.println("Description area cannot be empty.");
			return;
		}

		if (descriptionArea.getText().length() > UpdateProfileRequest.MAX_DESCRIPTION_SIZE) {
			System.err.println("Description cannot be longer than 250 characters");
			return;
		}
		// create new shared key
		SharedKeyCipher cipher;
		try {
			// create new shared key
			cipher = new SharedKeyCipher();
			board.getSocketController().setCipher(cipher);
			int nonce = SecureRandom.getInstance("SHA1PRNG").nextInt();
			board.getSocketController().setNonce(nonce);

			// create request
			SignUpRequest req = new SignUpRequest(usernameField.getText(),
					passwordField.getPassword(), cipher, nonce, descriptionArea.getText());
			board.getSocketController().sendRequest(req, board,
					"Registration error", this);
			req.zeroOutPassword();
		} catch (Exception e2) {
			// TODO Hmm... not much we really can do to recover
			// though I guess we could report an error, and ask them
			// to try again and refresh this "board" (welcome screen)
			e2.printStackTrace();
		}
	}

	@Override
	public void handleRequestSuccess(Response resp) {
		// Yay, registration was successful, time to send the profile
		// description.
		try {
			UpdateProfileRequest req = new UpdateProfileRequest(
					descriptionArea.getText());
			board.getSocketController().sendRequest(req, board,
					"Description error", board);
		} catch (Exception e3) {
			e3.printStackTrace();
		}
	}
}
