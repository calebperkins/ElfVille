package elfville.client.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import elfville.client.SocketController;
import elfville.protocol.*;

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

	// Post the message
	// TODO finish
	@Override
	public void actionPerformed(ActionEvent e) {
		PostCentralBoardRequest req = new PostCentralBoardRequest(text.getText());
		try {
			PostCentralBoardResponse resp = SocketController.send(req);
			if (resp.isOK()) {
				System.out.println("Posted!");
			} else {
				System.out.println("Not posted!");
			}
		} catch (IOException e1) {
			ClientWindow.showConnectionError(this);
		}
	}

}
