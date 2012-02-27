package elfville.client.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import elfville.client.ClientWindow;
import elfville.client.SocketController;
import elfville.client.views.subcomponents.Elf;
import elfville.protocol.ClanBoardResponse;
import elfville.protocol.ModifyClanRequest;
import elfville.protocol.ModifyClanRequest.ModClan;
import elfville.protocol.Response;
import elfville.protocol.models.SerializableClan;
import elfville.protocol.models.SerializableElf;

public class ClanApplicants extends JPanel {
	private static final long serialVersionUID = 1L;

	private Refreshable board;

	private class ApplicantHandler implements ActionListener {
		private SerializableClan clan;
		private SerializableElf elf;
		private boolean accept;

		public ApplicantHandler(SerializableClan clan, SerializableElf elf, boolean accept) {
			this.clan = clan;
			this.elf = elf;
			this.accept = accept;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				ModifyClanRequest req = new ModifyClanRequest();
				req.applicant = elf;
				req.clan = clan;
				if (accept) {
					req.requestType = ModClan.ACCEPT;
				} else {
					req.requestType = ModClan.DENY;
				}
				Response resp = SocketController.send(req);
				if (resp.isOK()) {
					
				}
				if (resp.status == Response.Status.SUCCESS) {
					board.refresh();
				} else {
					System.err.println("Failed to decide applicant.");
				}
			} catch (IOException e1) {
				ClientWindow.showConnectionError();
			}
		}
	}

	public ClanApplicants(Refreshable board, ClanBoardResponse response) {
		//setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.board = board;
		JPanel allApplicantsPanel = new JPanel();
		//panel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
		for (SerializableElf applicant : response.clan.applicants) {
			JPanel applicantPanel = new JPanel();
			applicantPanel.add(new Elf(applicant));
			JButton accept = new JButton("Accept");
			accept.addActionListener(new ApplicantHandler(response.clan, applicant, true));
			JButton deny = new JButton("Deny");
		}
		JScrollPane scroll = new JScrollPane(allApplicantsPanel);
		add(scroll);
	}

}
