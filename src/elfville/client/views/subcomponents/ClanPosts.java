package elfville.client.views.subcomponents;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import elfville.protocol.ClanBoardResponse;
import elfville.protocol.models.SerializablePost;


public class ClanPosts extends JPanel {
	private static final long serialVersionUID = 1L;

	public ClanPosts(ClanBoardResponse response) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JPanel postPanel = new JPanel();
		postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
		for (SerializablePost post : response.clan.posts) {
			postPanel.add(new Post(post));
		}
		JScrollPane scroll = new JScrollPane(postPanel);
		add(scroll);
	}
}