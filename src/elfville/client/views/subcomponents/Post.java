package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import elfville.client.views.Board;
import elfville.protocol.ClanBoardResponse;
import elfville.protocol.DeleteCentralBoardRequest;
import elfville.protocol.ModifyClanRequest;
import elfville.protocol.Request;
import elfville.protocol.Response;
import elfville.protocol.VoteRequest;
import elfville.protocol.models.SerializableClan;
import elfville.protocol.models.SerializablePost;

/**
 * Displays an individual post.
 * 
 * @author Caleb Perkins
 * @author Aaron Martinez
 * 
 */
public class Post extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton username;
	private JButton deleteButton;
	private JTextArea content;
	private JTextArea title;

	public class DeleteHandler implements ActionListener {
		private SerializablePost post;
		private Board board;
		ClanBoardResponse response;
		
		public DeleteHandler(Board board, SerializablePost post, ClanBoardResponse response) {
			this.post = post;
			this.board = board;
			this.response = response;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Request req;
			if (null == response) {
				req = new DeleteCentralBoardRequest(post);
			} else {
				req = new ModifyClanRequest(response.clan, post);
			}
			board.getSocketController().sendRequest(req, board, "Failed to delete post.", null);
		}
	}


	/**
	 * Create the panel.
	 */
	public Post(Board board, SerializablePost p, ClanBoardResponse response) {
		super();
		
		// setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		username = new JButton(p.username);
		username.addActionListener(new ElfHandler(board, p.elfModelID));
		content = new JTextArea(p.content);
		content.setLineWrap(true);
		content.setWrapStyleWord(true);
		content.setEditable(false);
		title = new JTextArea(p.title);
		title.setLineWrap(true);
		title.setWrapStyleWord(true);
		title.setEditable(false);
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new DeleteHandler(board, p, response));

		add(username);
		JScrollPane scroll = new JScrollPane(title);
		JLabel label = new JLabel("Title");
		label.setLabelFor(scroll);
		add(label);
		add(scroll);
		scroll = new JScrollPane(content);
		label = new JLabel("Content");
		label.setLabelFor(scroll);
		add(label);
		add(scroll);
		if (p.myPost || (null != response && response.elfStatus ==
				ClanBoardResponse.ElfClanRelationship.LEADER)) {
			add(deleteButton);
		}
	}

}
