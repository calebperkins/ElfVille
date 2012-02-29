package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import elfville.client.views.CentralBoard;
import elfville.protocol.VoteRequest;
import elfville.protocol.models.SerializablePost;

public class VotablePost extends Post {
	private static final long serialVersionUID = 1L;
	private final CentralBoard board;
	
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
			board.getSocketController().sendRequest(req, board, "Did not vote!", null);
		}
	}

	public VotablePost(SerializablePost p, CentralBoard board) {
		super(p, false);
		// TODO have some indication of whether (and how) the user has voted on
		// this post.
		this.board = board;
		JButton upvote = new JButton("Likes: " + Integer.toString(p.upvotes));
		JButton downvote = new JButton("Dislikes: " + Integer.toString(p.downvotes));
		upvote.addActionListener(new VoteHandler(p.modelID, true, this));
		downvote.addActionListener(new VoteHandler(p.modelID, false, this));
		add(upvote);
		add(downvote);
	}

}
