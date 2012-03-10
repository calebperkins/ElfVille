package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import elfville.client.ClientWindow;
import elfville.client.SocketController;
import elfville.client.views.CentralBoard;
import elfville.client.views.ClanDirectory;

/**
 * Displays two buttons, one which directs the client to the public central
 * board (showing postings by users) and the other which directs the client to
 * the clan directory. With these two buttons any elf can reach anything they
 * have authentication to access, and this panel is displayed on every
 * board/page.
 * 
 */
public class NavigationScreen extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton centralBoard;
	private JButton clanDirectory;
	private ClientWindow clientWindow;
	private SocketController socketController;

	private class ButtonHandler implements ActionListener {
		private boolean centralBoard;

		public ButtonHandler(boolean centralBoard) {
			this.centralBoard = centralBoard;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (centralBoard) {
				new CentralBoard(clientWindow, socketController);
			} else {
				new ClanDirectory(clientWindow, socketController);
			}
		}
	}

	public NavigationScreen(ClientWindow clientWindow,
			SocketController socketController) {
		super();

		this.clientWindow = clientWindow;
		this.socketController = socketController;
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		centralBoard = new JButton("Central Board");
		clanDirectory = new JButton("Clan Directory");

		centralBoard.addActionListener(new ButtonHandler(true));
		clanDirectory.addActionListener(new ButtonHandler(false));

		add(centralBoard);
		add(clanDirectory);
	}
}
