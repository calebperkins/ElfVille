package elfville.client.views;

import java.awt.CardLayout;
import java.awt.Component;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.*;

public class ClientWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	// TODO - remove global variable
	private static final JPanel cards = new JPanel(new CardLayout());
	private static final HashMap<String, JPanel> screens = new HashMap<String, JPanel>();

	/**
	 * Allows client to switch between screens
	 * 
	 * @param name
	 *            the name of the screen
	 * @return the screen panel
	 */
	public static JPanel switchScreen(String name) {
		CardLayout cl = (CardLayout) cards.getLayout();
		cl.show(cards, name);
		return screens.get(name);
	}

	/**
	 * Create the frame.
	 */
	public ClientWindow() {
		super("ElfVille");
		setBounds(100, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add all the different screens to hash table
		screens.put("welcome", new WelcomeScreen());
		screens.put("central_board", new CentralBoard());
		screens.put("clan_directory", new ClanDirectory());
		screens.put("clan_board", new ClanBoard());

		// Add screens to cards
		Iterator<Entry<String, JPanel>> it = screens.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, JPanel> pair = it.next();
			cards.add(pair.getValue(), pair.getKey());
		}

		switchScreen("welcome");
		add(cards);
	}

	/**
	 * Used when a socket error occurs. Shows an alert dialog.
	 */
	public static void showConnectionError() {
		showError("Socket connection broke. Try again.",
				"Connection error");
		System.exit(-1);
	}
	
	public static void showError(String msg, String title) {
		JOptionPane.showMessageDialog(null, msg,
				title, JOptionPane.ERROR_MESSAGE);
	}

}
