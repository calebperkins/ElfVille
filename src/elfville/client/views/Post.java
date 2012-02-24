package elfville.client.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import elfville.protocol.*;

/**
 * Displays an individual post.
 * @author Caleb Perkins
 *
 */
public class Post extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel username;
	private JTextArea content;
	private JButton upvote;
	private JButton downvote;
	
	private class VoteHandler implements ActionListener {
		private boolean like;
		private int postID;
		
		public VoteHandler(int postID, boolean like) {
			this.like = like;
			this.postID = postID;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO send vote request			
		}
		
	}

	/**
	 * Create the panel.
	 */
	public Post(SerializablePost p) {
		super();
		
		username = new JLabel(p.username);
		content = new JTextArea(p.content);
		upvote = new JButton("Likes: " + Integer.toString(p.upvotes));
		downvote = new JButton("Dislikes: " + Integer.toString(p.downvotes));
		
		upvote.addActionListener(new VoteHandler(666, true)); // TODO: actual post ID
		downvote.addActionListener(new VoteHandler(666, false));
		
		add(username);
		add(content);
		add(upvote);
		add(downvote);
	}

}
