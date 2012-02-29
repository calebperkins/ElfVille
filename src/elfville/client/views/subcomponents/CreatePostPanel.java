package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;

import elfville.client.views.Board;
import elfville.protocol.*;

public class CreatePostPanel extends CreatePanel {
	private static final long serialVersionUID = 1L;
	private final String clanID;

	public CreatePostPanel(Board board, String clanID) {
		super(board, "Title", "Text", "Post");
		this.clanID = clanID;
	}

	// Post the message
	@Override
	public void actionPerformed(ActionEvent e) {
		if (textField.getText().equals("") || textArea.getText().equals("")) {
			System.err.println("Need both a " + textFieldLabel +
					" and a " + textAreaLabel + " to " + buttonLabel);
		} else {
			Request req;
			if (clanID == null) {
				req = new PostCentralBoardRequest(
						textField.getText(), textArea.getText());
			} else {
				req = new PostClanBoardRequest(
						textField.getText(), textArea.getText(), clanID);
			}
			board.getSocketController().sendRequest(req, board, "Posting Failed.", null);
		}
	}

}
