package elfville.client.views;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import elfville.protocol.*;

/**
 * Renders the central board using the central board request.
 * @author caleb
 *
 */
public class CentralBoard extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JLabel title = new JLabel("Central Board");
	private final List<Post> posts = new ArrayList<Post>();

	/**
	 * Create the panel.
	 */
	public CentralBoard() {
		super();
		add(title);
		// list of posts here....
		add(new CreatePostPanel());
	}
	
	public void load(GetCentralBoardResponse response) {
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
