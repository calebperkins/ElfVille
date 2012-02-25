package elfville.client.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import elfville.client.SocketController;
import elfville.protocol.*;

/**
 * Displays an individual post.
 * @author Caleb Perkins
 * @author Aaron Martinez
 *
 */
public class Post extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel username;
	private JTextArea content;
	private JButton upvote;
	private JButton downvote;
	private JTextArea title;
	
	private class VoteHandler implements ActionListener {
		private boolean upsock;
		private String postID;
		private Post component;
		
		public VoteHandler(String postID, boolean upsock, Post component) {
			this.upsock = upsock;
			this.postID = postID;
			this.component = component;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			VoteRequest req = new VoteRequest(postID, upsock);
			try {
				VoteResponse resp = SocketController.send(req);
				
				if (!resp.isOK()) {
					System.err.println("Did not vote!");
				}
			} catch (IOException e1) {
				// TODO: not sure how to get the following line to do what we want.
				ClientWindow.showConnectionError(component);
			}
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
		title = new JTextArea(p.title);
		
		upvote.addActionListener(new VoteHandler(p.modelID, true, this));
		downvote.addActionListener(new VoteHandler(p.modelID, false, this));
		
		add(username);
		add(title);
		add(content);
		add(upvote);
		add(downvote);
	}

}
