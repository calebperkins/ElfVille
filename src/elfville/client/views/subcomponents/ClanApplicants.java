package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import elfville.client.views.Board;
import elfville.protocol.ClanBoardResponse;
import elfville.protocol.ModifyClanRequest;
import elfville.protocol.ModifyClanRequest.ModClan;
import elfville.protocol.Response;
import elfville.protocol.models.SerializableClan;
import elfville.protocol.models.SerializableElf;

public class ClanApplicants extends JPanel {
	private static final long serialVersionUID = 1L;

	private Board board;

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
				Response resp = board.getSocketController().send(req);
				if (resp.isOK()) {
					board.refresh();
				} else {
					System.err.println("Failed to decide applicant.");
				}
			} catch (IOException e1) {
				board.getClientWindow().showConnectionError();
			}
		}
	}

	public ClanApplicants(Board board, ClanBoardResponse response) {
		this.board = board;
		JPanel allApplicantsPanel = new JPanel();
		for (SerializableElf applicant : response.clan.applicants) {
			JPanel applicantPanel = new JPanel();
			applicantPanel.add(new Elf(applicant));
			
			JButton accept = new JButton("Accept");
			accept.addActionListener(new ApplicantHandler(response.clan, applicant, true));
			applicantPanel.add(accept);
			
			JButton deny = new JButton("Deny");
			deny.addActionListener(new ApplicantHandler(response.clan, applicant, false));
			applicantPanel.add(deny);
			
			allApplicantsPanel.add(applicantPanel);
		}
		JScrollPane scroll = new JScrollPane(allApplicantsPanel);
		add(scroll);
	}

}
