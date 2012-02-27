package elfville.client.views;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.*;

import elfville.client.ClientWindow;
import elfville.client.SocketController;
import elfville.client.views.subcomponents.ClanDetails;
import elfville.client.views.subcomponents.ClanMembers;
import elfville.client.views.subcomponents.ClanPosts;
import elfville.client.views.subcomponents.CreatePostPanel;
import elfville.protocol.*;

/**
 * Renders the central board using the central board request.
 * 
 * @author aaron
 * 
 */
public class ClanBoard extends JPanel implements Refreshable {
	private static final long serialVersionUID = 1L;
	private String clanID;
	private String clanName;

	public ClanBoard(ClanBoardResponse response) {
		super();
		this.clanID = response.clan.modelID;
		this.clanName = response.clan.clanName;
		
		this.setLayout(new BorderLayout());
		
		//TODO might be worth passing more restricted things to each of these
		// constructors, they don't need the entire response, and might be
		// more readable.
		
		ClanDetails details = new ClanDetails(response, this);
		add(details, BorderLayout.PAGE_START);
		
		ClanMembers members = new ClanMembers(response);
		add(members, BorderLayout.LINE_END);
		
		CreatePostPanel createPost = new CreatePostPanel(this, clanID);
		add(createPost, BorderLayout.LINE_START);
		
		ClanPosts posts = new ClanPosts(response);
		add(posts, BorderLayout.CENTER);
		
		ClanApplicants applicants = new ClanApplicants(this, response);
		add(applicants, BorderLayout.PAGE_END);
	}

	@Override
	public void refresh() {
		showClanBoard(clanID, clanName);
	}

	public static void showClanBoard(String clanID, String clanName) {
		try {
			ClanBoardResponse resp = SocketController
					.send(new ClanBoardRequest(clanID));
			if (resp.status == Response.Status.SUCCESS) {
				ClientWindow
						.switchScreen(new ClanBoard(resp));
			} else {
				ClientWindow.showError(resp.message,
						"Error retrieving clan board.");
			}
		} catch (IOException e1) {
			ClientWindow.showConnectionError();
		}
	}
}
