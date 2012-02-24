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
	private final String clanName;

	/**
	 * Create the panel.
	 */
	public CreatePostPanel() {
		super();
		clanName = null;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("New Post"),
                BorderFactory.createEmptyBorder(5,5,5,5)));
		
		button.addActionListener(this);
		
		add(text);
		add(button);
	}
	
	public CreatePostPanel(String clanName) {
		super();
		this.clanName = clanName;
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
		// TODO!
		try {
			Response resp;
			if (clanName == null) {
				PostCentralBoardRequest req = new PostCentralBoardRequest(text.getText(), "TODO");
				resp = (Response) SocketController.send(req); // returns PostCentralBoardResponse
			} else {
				PostClanBoardRequest req = new PostClanBoardRequest(text.getText(), "TODO", clanName);
				resp = (Response) SocketController.send(req); // returns PostClanlBoardResponse
			}
			
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
