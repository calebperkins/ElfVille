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
	private final JLabel title;
	private final List<Post> posts = new ArrayList<Post>();

	/**
	 * Create the panel.
	 */
	public ClanBoard(String clanName) {
		super();
		title = new JLabel(clanName + "'s Board");
		add(title);
		// list of posts here....
		add(new CreatePostPanel(clanName));
	}
	
	public void load(ClanBoardResponse response) {
		for (Post p : posts) {
			remove(p);
		}
		posts.clear();
		
		for (SerializablePost post : response.getPosts()) {
			Post p = new Post(post);
			posts.add(p);
			add(p);
		}
	}
}
