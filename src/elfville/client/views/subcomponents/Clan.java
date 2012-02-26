package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import elfville.client.views.ClanBoard;
import elfville.protocol.models.SerializableClan;

public class Clan extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton clanName;
	private JTextArea clanDescription;
	private JButton leaderName;

	// TODO: perhaps add leader name with a link to profile

	private class ClanHandler implements ActionListener {
		private String modelID;
		private String clanName;

		public ClanHandler(String modelID, String clanName) {
			this.modelID = modelID;
			this.clanName = clanName;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ClanBoard.showClanBoard(modelID, clanName);
		}

	}

	private class LeaderHandler implements ActionListener {
		private String elfID;
		private String elfName;

		public LeaderHandler(String elfID, String elfName) {
			this.elfID = elfID;
			this.elfName = elfName;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO load the elf profile (which currently doesn't exist
			// (profiles don't))
		}

	}

	/**
	 * Create the panel.
	 */
	public Clan(SerializableClan c) {
		super();
		// TODO: possible to maybe deduplicate code between this and Post.java

		clanName = new JButton(c.clanName);
		clanDescription = new JTextArea(c.clanDescription);
		clanDescription.setLineWrap(true);
		clanDescription.setWrapStyleWord(true);
		leaderName = new JButton(c.leader.elfName);

		clanName.addActionListener(new ClanHandler(c.modelID, c.clanName));
		leaderName.addActionListener(new LeaderHandler(c.leader.modelID,
				c.leader.elfName));
		add(clanName);
		add(leaderName); // TODO we need a way to distinguish between clan and
							// leader names
		add(new JScrollPane(clanDescription));
	}

}