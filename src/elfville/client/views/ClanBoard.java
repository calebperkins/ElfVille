package elfville.client.views;

import java.io.IOException;

import javax.swing.*;

import elfville.client.ClientWindow;
import elfville.client.SocketController;
import elfville.client.views.subcomponents.CreatePostPanel;
import elfville.client.views.subcomponents.Post;
import elfville.protocol.*;
import elfville.protocol.models.SerializablePost;

/**
 * Renders the central board using the central board request.
 * 
 * @author aaron
 * 
 */
public class ClanBoard extends JPanel implements Refreshable {
	private static final long serialVersionUID = 1L;
	private final JLabel title;
	private String clanID;
	private String clanName;

	public ClanBoard(String clanID, String clanName, ClanBoardResponse response) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.clanID = clanID;
		this.clanName = clanName;
		title = new JLabel("Board of the " + clanName);
		add(title);
		// TODO add clan summary? Member listing? (will need to change layout)
		title.setText(clanName + "'s Board");
		add(title);
		CreatePostPanel createPost = new CreatePostPanel(this, clanID);
		add(createPost);
		
		JPanel postPanel = new JPanel();
		postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
		for (SerializablePost post : response.clan.posts) {
			postPanel.add(new Post(post));
		}
		JScrollPane scroll = new JScrollPane(postPanel);
		add(scroll);
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
						.switchScreen(new ClanBoard(clanID, clanName, resp));
			} else {
				ClientWindow.showError(resp.message,
						"Error retrieving clan board.");
			}
		} catch (IOException e1) {
			ClientWindow.showConnectionError();
		}
	}
}
