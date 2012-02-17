package elfville.client.views;

import javax.swing.*;

/**
 * Renders the central board using the central board request.
 * @author caleb
 *
 */
public class CentralBoard extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel title;
	//private List<Post> posts;

	/**
	 * Create the panel.
	 */
	public CentralBoard(elfville.protocol.GetCentralBoardResponse response) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		title = new JLabel("Central Board");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		
		add(title);
		
		for (elfville.protocol.SerializablePost post : response.getPosts()) {
			add(new Post(post));
		}
	}

}
