package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import elfville.client.views.CentralBoard;
import elfville.client.views.ClanDirectory;

public class NavigationScreen extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton centralBoard;
	private JButton clanDirectory;
	
	private class ButtonHandler implements ActionListener {
		private boolean centralBoard;
		
		public ButtonHandler(boolean centralBoard) {
			this.centralBoard = centralBoard;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (centralBoard) {
				CentralBoard.showCentralBoard();
			} else {
				ClanDirectory.showClanDirectory();
			}
		}
	}
	
	public NavigationScreen() {
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		centralBoard = new JButton("Central Board");
		clanDirectory = new JButton("Clan Directory");
		
		centralBoard.addActionListener(new ButtonHandler(true));
		clanDirectory.addActionListener(new ButtonHandler(false));
		
		add(centralBoard);
		add(clanDirectory);
	}
}
