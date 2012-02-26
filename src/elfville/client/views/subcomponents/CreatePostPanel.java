package elfville.client.views.subcomponents;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import elfville.client.ClientWindow;
import elfville.client.SocketController;
import elfville.client.views.Refreshable;
import elfville.protocol.*;

public class CreatePostPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final JTextArea text = new JTextArea();
	private final JTextField title = new JTextField();
	private final JButton button = new JButton("Post");
	private final String clanID;
	private final JLabel titleLabel = new JLabel("Title");
	private final JLabel textLabel = new JLabel("Text");
	private final Refreshable board;

	public CreatePostPanel(Refreshable board, String clanID) {
		super();
		this.clanID = clanID;
		this.board = board;
		makeThePanel();
	}

	private void makeThePanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("New Post"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		button.addActionListener(this);

		add(titleLabel);
		add(title);
		add(textLabel);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		JScrollPane scrollableText = new JScrollPane(text);
		scrollableText.setPreferredSize(new Dimension(300,100));
		scrollableText.setMinimumSize(new Dimension(300, 40));
		add(scrollableText);
		add(button);
		setMaximumSize(new Dimension(300, 150));
		//TODO: fix the code duplication between create clan panel and create post panel
	}

	// Post the message
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Nice helpful "need to entire title" or "need to enter text" messages
		// but these are enforced by server anyway, just not a helpful error message.
		try {
			Response resp;
			if (clanID == null) {
				PostCentralBoardRequest req = new PostCentralBoardRequest(
						text.getText(), title.getText());
				resp = SocketController.send(req); // returns
													// PostCentralBoardResponse
			} else {
				PostClanBoardRequest req = new PostClanBoardRequest(
						text.getText(), title.getText(), clanID);
				resp = SocketController.send(req); // returns
													// PostClanlBoardResponse
			}

			if (resp.isOK()) {
				board.refresh();
			} else {
				System.err.println("Not posted!");
			}
		} catch (IOException e1) {
			ClientWindow.showConnectionError();
		}
	}

}
