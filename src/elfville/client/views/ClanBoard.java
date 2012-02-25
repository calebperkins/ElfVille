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
	}
	
	public void changeClan(String clanName) {
		remove(postPanel);
		remove(title);
		title = new JLabel(clanName + "'s Board");
		add(title);
		postPanel = new CreatePostPanel(clanName);
		add(postPanel);
	}
	
	public void load(ClanBoardResponse response) {
		for (Post p : posts) {
			remove(p);
		}
		posts.clear();
		
		/*
		for (SerializablePost post : response.getPosts()) {
			Post p = new Post(post);
			posts.add(p);
			//I'm not sure this is right... I think we need to keep a reference
			// to the created CreatePostPanel and add p to that... Otherwise,
			// I'm not sure what adding the CreatePostPanel actually ends up doing. - aaron
			// see also CentralBoard.java (which suggests a need for refactoring...)
			add(p);
		}
		*/
	}
}
