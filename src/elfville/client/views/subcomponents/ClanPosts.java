package elfville.client.views.subcomponents;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import elfville.client.views.Board;
import elfville.protocol.ClanBoardResponse;
import elfville.protocol.models.SerializablePost;


public class ClanPosts extends JPanel {
	private static final long serialVersionUID = 1L;

	public ClanPosts(Board board, ClanBoardResponse response) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JPanel postPanel = new JPanel();
		postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
		for (SerializablePost post : response.clan.posts) {
			postPanel.add(new Post(board, post,
					response.elfStatus ==
					ClanBoardResponse.ElfClanRelationship.LEADER));
		}
		JScrollPane scroll = new JScrollPane(postPanel);
		add(scroll);
	}
}
