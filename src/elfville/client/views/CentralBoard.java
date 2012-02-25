package elfville.client.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import elfville.client.SocketController;
import elfville.protocol.*;
import elfville.protocol.models.SerializablePost;

/**
 * Renders the central board using the central board request.
 * 
 * @author Caleb Perkins
 * 
 */
public class CentralBoard extends JPanel implements Refreshable {
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
		add(new CreatePostPanel(this, null));
	}

	@Override
	public void refresh() {
		CentralBoardRequest req = new CentralBoardRequest();
		try {
			CentralBoardResponse resp = SocketController.send(req);
			load(resp);
		} catch (IOException e) {
			ClientWindow.showConnectionError();
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
			add(p);
		}
		revalidate();
	}

}
