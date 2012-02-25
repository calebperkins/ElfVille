package elfville.client.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import elfville.client.SocketController;
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
	
	public void refresh() {
		CentralBoardRequest req = new CentralBoardRequest();
		try {
			CentralBoardResponse resp = SocketController.send(req);
			load(resp);
		} catch (IOException e) {
			ClientWindow.showConnectionError(this);
		}
	}
	
	public void load(CentralBoardResponse response) {
		for (Post p : posts) {
			remove(p);
		}
		posts.clear();
		
		for (SerializablePost post : response.posts) {
			Post p = new Post(post);
			posts.add(p);
			//I'm not sure this is right... I think we need to keep a reference
			// to the created CreatePostPanel and add p to that... Otherwise,
			// I'm not sure what adding the CreatePostPanel actually ends up doing. - aaron
			// see also ClanBoard.java (which suggests a need for  refactoring...)
			add(p);
		}
	}

}
