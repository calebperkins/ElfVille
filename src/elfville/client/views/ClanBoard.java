package elfville.client.views;

import java.awt.BorderLayout;
import java.io.IOException;

import elfville.client.ClientWindow;
import elfville.client.SocketController;
import elfville.client.views.subcomponents.*;
import elfville.protocol.*;

/**
 * Renders the central board using the central board request.
 * 
 * @author aaron
 * 
 */
public class ClanBoard extends Board {
	private static final long serialVersionUID = 1L;
	private String clanID;

	private ClientWindow clientWindow;
	private SocketController socketController;

	public ClanBoard(ClientWindow clientWindow, SocketController socketController, String clanID) {
		super(clientWindow, socketController);
		this.clanID = clanID;

		try {
			ClanBoardResponse response = socketController.send(new ClanBoardRequest(clanID));
			if (response.status == Response.Status.SUCCESS) {

				this.clientWindow = clientWindow;
				this.socketController = socketController;

				this.setLayout(new BorderLayout());

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
				
				clientWindow.switchScreen(this);
			} else {
				clientWindow.showError(response.message, "Error retrieving clan board.");
			}

		} catch (IOException e1) {
			clientWindow.showConnectionError();
		}
	}

	@Override
	public void refresh() {
		new ClanBoard(clientWindow, socketController, clanID);
	}

}
