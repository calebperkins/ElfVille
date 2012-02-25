package elfville.client.views;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import elfville.protocol.*;


/**
 * Renders the central board using the central board request.
 * @author aaron
 *
 */
public class ClanBoard extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel title;
	private final List<Post> posts = new ArrayList<Post>();
	private CreatePostPanel postPanel;

	/**
	 * Create the panel.
	 */
	public ClanBoard() {
		super();
		title = new JLabel("Clan Board");
		add(title);
		postPanel = null;
	}
	
	public void changeClanLoadPosts(String clanName, ClanBoardResponse response) {
		// TODO add clan summary? Member listing?
		for (Post p : posts) {
			remove(p);
		}
		posts.clear();
		if (null != postPanel) {
			remove(postPanel);
		}
		remove(title);
		
		title = new JLabel(clanName + "'s Board");
		add(title);
		postPanel = new CreatePostPanel(clanName);
		add(postPanel);
		
		for (SerializablePost post : response.getPosts()) {
			Post p = new Post(post);
			posts.add(p);
			add(p);
		}
	}
	
}
