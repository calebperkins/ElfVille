package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import elfville.client.views.ClanBoard;
import elfville.client.views.ClanDirectory;
import elfville.protocol.*;
import elfville.protocol.ModifyClanRequest.ModClan;
import elfville.protocol.models.SerializableClan;


public class ClanDetails extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	//private final String clanModelID;
	private final SerializableClan clan;
	//private final String elfModelID;
	private final ModClan action;
	ClanBoard board;
	
	public ClanDetails(ClanBoardResponse response, ClanBoard board){
		super();
		this.board = board;
		//elfModelID = response.
		//clanModelID = response.clan.modelID;
		clan = response.clan;
		
		JLabel title = new JLabel("Board of the " + response.clan.clanName);
		add(title);
		
		JButton leaderName = new JButton(response.clan.leader.elfName);
		leaderName.addActionListener(new ElfHandler(response.clan.leader.modelID));
		add(leaderName);
		
		String buttonLabel;
		if (response.elfStatus == ClanBoardResponse.ElfClanRelationship.OUTSIDER) {
			buttonLabel = "Request to Join";
			action = ModClan.JOIN;
		} else if (response.elfStatus == ClanBoardResponse.ElfClanRelationship.APPLICANT) {
			buttonLabel = "Application Processing";
			action = null;
		} else if (response.elfStatus == ClanBoardResponse.ElfClanRelationship.MEMBER) {
			buttonLabel = "Leave Clan";
			action = ModClan.LEAVE;
		} else if (response.elfStatus == ClanBoardResponse.ElfClanRelationship.LEADER) {
			buttonLabel = "Disband Clan";
			action = ModClan.DELETE;
		} else {
			buttonLabel = "Error";
			action = null;
		}
		
		JButton clanAction = new JButton(buttonLabel);
		
		if (buttonLabel.equals("Error") || buttonLabel.equals("Application Processing")) {
			clanAction.setEnabled(false);
		} else {
			clanAction.addActionListener(this);
		}
		add(clanAction);
		
		JTextArea description = new JTextArea(response.clan.clanDescription);
		add(description);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			ModifyClanRequest req = new ModifyClanRequest();
			req.clan = clan;
			req.requestType = action;
			Response resp = board.getSocketController().send(req);
			
			if (resp.isOK() && (action == ModClan.LEAVE || action == ModClan.DELETE)) {
				new ClanDirectory(board.getClientWindow(), board.getSocketController());
			} else if (resp.isOK()) {
				board.refresh();
			} else {
				System.err.println("Not posted!");
			}
		} catch (IOException e1) {
			board.getClientWindow().showConnectionError();
		}
	}
}
