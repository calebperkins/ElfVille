package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import elfville.protocol.Response;
import elfville.protocol.VoteRequest;
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
		private String postModelID;
		
		public DeleteHandler(String postModelID) {
			this.postModelID = postModelID;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO write this
		}
	}


	/**
	 * Create the panel.
	 */
	public Post(SerializablePost p, boolean viewerIsClanLeader) {
		super();
		
		// setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		username = new JButton(p.username);
		username.addActionListener(new ElfHandler(p.elfModelID));
		content = new JTextArea(p.content);
		content.setLineWrap(true);
		content.setWrapStyleWord(true);
		content.setEditable(false);
		title = new JTextArea(p.title);
		title.setLineWrap(true);
		title.setWrapStyleWord(true);
		title.setEditable(false);
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new DeleteHandler(p.modelID));

		add(username);
		add(new JScrollPane(title));
		add(new JScrollPane(content));
		if (p.myPost || viewerIsClanLeader) {
			add(deleteButton);
		}
	}

}
