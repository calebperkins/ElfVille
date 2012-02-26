package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import elfville.client.ClientWindow;
import elfville.client.SocketController;
import elfville.protocol.*;
import elfville.protocol.models.SerializablePost;

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
		
		public VoteHandler(String postID, boolean upsock, Post component) {
			this.upsock = upsock;
			this.postID = postID;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			VoteRequest req = new VoteRequest(postID, upsock);
			try {
				Response resp = SocketController.send(req);
				
				if (!resp.isOK()) {
					System.err.println("Did not vote!");
				}
				// TODO after we indicate how a user has voted, probably want to refresh the board.
			} catch (IOException e1) {
				ClientWindow.showConnectionError();
			}
		}
		
	}

	/**
	 * Create the panel.
	 */
	public Post(SerializablePost p) {
		super();
		// TODO have some indication of whether (and how) the user has voted on this post.
		//setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
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
