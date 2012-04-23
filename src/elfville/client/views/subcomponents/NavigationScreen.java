package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import elfville.client.ClientWindow;
import elfville.client.SocketController;
import elfville.client.views.CentralBoard;
import elfville.client.views.ClanDirectory;
import elfville.client.views.LoadClassScreen;
import elfville.protocol.LoadClassRequest;
import elfville.protocol.Request;

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
	private JButton uploadButton;
	private ClientWindow clientWindow;
	private SocketController socketController;

	private class ButtonHandler implements ActionListener {
		private int buttonId;

		public ButtonHandler(int buttonId) {
			this.buttonId = buttonId;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (buttonId == 0) {
				new CentralBoard(clientWindow, socketController);
			} else if (buttonId == 1){
				new ClanDirectory(clientWindow, socketController);
			} else {
				new LoadClassScreen(clientWindow, socketController);
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
		uploadButton = new JButton("Upload Java Class");
		
		centralBoard.addActionListener(new ButtonHandler(0));
		clanDirectory.addActionListener(new ButtonHandler(1));
		uploadButton.addActionListener(new ButtonHandler(2));

		add(centralBoard);
		add(clanDirectory);

		if (Request.USERCLASSLOAD) {
			add(uploadButton);
		}
	}
}
