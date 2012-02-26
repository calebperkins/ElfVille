package elfville.client.views.subcomponents;

import javax.swing.*;

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
	private JLabel username;
	private JTextArea content;
	private JTextArea title;



	/**
	 * Create the panel.
	 */
	public Post(SerializablePost p) {
		super();
		
		// setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		username = new JLabel(p.username);
		content = new JTextArea(p.content);
		content.setLineWrap(true);
		content.setWrapStyleWord(true);
		title = new JTextArea(p.title);
		title.setLineWrap(true);
		title.setWrapStyleWord(true);

		add(username);
		add(new JScrollPane(title));
		add(new JScrollPane(content));
	}

}
