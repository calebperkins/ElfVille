package elfville.client.views;

import java.awt.BorderLayout;

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

	public ClanBoard(ClientWindow clientWindow, SocketController socketController, String clanID) {
		super(clientWindow, socketController);
		this.clanID = clanID;
		ClanBoardRequest req = new ClanBoardRequest(clanID);
		getSocketController().sendRequest(req, this,
				"Error retrieving clan board.", this);
	}

	@Override
	public void refresh() {
		new ClanBoard(clientWindow, socketController, clanID);
	}

	@Override
	public void handleRequestSuccess(Response resp) {
		ClanBoardResponse response = (ClanBoardResponse) resp;
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
	}

}
