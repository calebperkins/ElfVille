package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import elfville.client.ClientWindow;
import elfville.client.SocketController;
import elfville.client.views.ClanBoard;
import elfville.protocol.models.SerializableClan;

public class Clan extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton clanName;
	private JTextArea clanDescription;
	private JButton leaderName;

	private ClientWindow clientWindow;
	private SocketController socketController;

	/**
	 * Create the panel.
	 */
	public Clan(SerializableClan c, ClientWindow clientWindow, SocketController socketController) {
		super();
		// TODO: possible to maybe deduplicate code between this and Post.java

		this.clientWindow = clientWindow;
		this.socketController = socketController;
		
		clanName = new JButton(c.clanName);
		clanDescription = new JTextArea(c.clanDescription);
		clanDescription.setEditable(false);
		clanDescription.setLineWrap(true);
		clanDescription.setWrapStyleWord(true);
		leaderName = new JButton("Leader: " + c.leader.elfName);

		clanName.addActionListener(new ClanHandler(c.modelID, c.clanName));
		leaderName.addActionListener(new ElfHandler(c.leader.modelID));
		add(clanName);
		add(leaderName);
		add(new JScrollPane(clanDescription));
	}
	


	private class ClanHandler implements ActionListener {
		private String modelID;

		public ClanHandler(String modelID, String clanName) {
			this.modelID = modelID;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			new ClanBoard(clientWindow, socketController, modelID);
		}

	}

}