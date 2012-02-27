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
public class ClanBoard extends JPanel implements Board {
	private static final long serialVersionUID = 1L;
	private String clanID;

	private ClientWindow clientWindow;
	private SocketController socketController;

	public ClanBoard(ClientWindow clientWindow, SocketController socketController, String clanID) {
		super();

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


	public ClientWindow getClientWindow() {
		return clientWindow;
	}

	public SocketController getSocketController() {
		return socketController;
	}

}
