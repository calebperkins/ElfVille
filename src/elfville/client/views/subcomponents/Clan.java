package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import elfville.client.views.Board;
import elfville.client.views.ClanBoard;
import elfville.protocol.models.SerializableClan;

public class Clan extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton clanName;
	private JTextArea clanDescription;
	private JButton leaderName;

	Board board;

	/**
	 * Displays the summary of a clan on the clan directory board
	 * specifically its name (with link to page), its leader (with
	 * link to its page), and its description.
	 */
	public Clan(Board board, SerializableClan c) { 
		super();
		// TODO: possible to maybe deduplicate code between this and Post.java
		this.board = board;

		clanName = new JButton(c.clanName + ", " + c.numSocks);
		clanDescription = new JTextArea(c.clanDescription);
		clanDescription.setEditable(false);
		clanDescription.setLineWrap(true);
		clanDescription.setWrapStyleWord(true);
		leaderName = new JButton("Leader: " + c.leader.elfName);

		clanName.addActionListener(new ClanHandler(c.modelID, c.clanName));
		leaderName.addActionListener(new ElfHandler(board, c.leader.modelID));
		add(clanName);
		add(leaderName);
		JScrollPane scroll = new JScrollPane(clanDescription);
		JLabel label = new JLabel("Description");
		label.setLabelFor(scroll);
		add(label);
		add(scroll);
	}
	


	private class ClanHandler implements ActionListener {
		private String modelID;

		public ClanHandler(String modelID, String clanName) {
			this.modelID = modelID;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			new ClanBoard(board.getClientWindow(), board.getSocketController(), modelID);
		}

	}

}