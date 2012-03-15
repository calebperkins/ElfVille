package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.crypto.SecretKey;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import elfville.client.SharedKeyCipher;
import elfville.client.views.Board;
import elfville.protocol.SignInRequest;

/**
 * Collection of controls to allow user to login.
 * 
 */
public class LoginPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextField usernameField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JButton loginButton = new JButton("Login");
	private JLabel usernameLabel = new JLabel("Username");
	private JLabel passwordLabel = new JLabel("Password");
	private Board board;

	/**
	 * Create the panel.
	 */
	public LoginPanel(Board board) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Login"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		this.board = board;

		usernameLabel.setLabelFor(usernameField);
		passwordLabel.setLabelFor(passwordField);
		loginButton.addActionListener(this);

		add(usernameLabel);
		add(usernameField);
		add(passwordLabel);
		add(passwordField);
		add(loginButton);

	}

	/**
	 * Log user in
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// create new secret key and nonce
		SharedKeyCipher cipher;
		SecretKey shared_key;
		byte[] nonce;
		try {
			cipher = new SharedKeyCipher();
			shared_key = cipher.getNewSharedKey();
			board.getSocketController().setCipher(cipher);
		} catch (Exception e2) {
			// TODO Hmm... not much we really can do to recover
			// though I guess we could report an error, and ask them
			// to try again and refresh this "board" (welcome screen)
			e2.printStackTrace();
			return;
		}

		// create request
		SignInRequest req = new SignInRequest(usernameField.getText(),
				passwordField.getPassword(), shared_key);
		board.getSocketController().sendRequest(req, board, "Login error",
				board);
		req.zeroPasswordArray();
	}
}
