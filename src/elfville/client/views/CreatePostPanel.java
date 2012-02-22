package elfville.client.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class CreatePostPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final JTextArea text = new JTextArea();
	private final JButton button = new JButton("Post");

	/**
	 * Create the panel.
	 */
	public CreatePostPanel() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("New Post"),
                BorderFactory.createEmptyBorder(5,5,5,5)));
		
		button.addActionListener(this);
		
		add(text);
		add(button);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO post the message
		
	}

}
