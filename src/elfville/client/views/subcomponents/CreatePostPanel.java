package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
import java.io.IOException;

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
		// TODO Nice helpful "need to entire title" or "need to enter text" messages
		// but these are enforced by server anyway, just not a helpful error message.
		if (textField.getText().equals("") || textArea.getText().equals("")) {
			System.err.println("Need both a " + textFieldLabel +
					" and a " + textAreaLabel + " to " + buttonLabel);
		} else {
			try {
				Response resp;
				if (clanID == null) {
					PostCentralBoardRequest req = new PostCentralBoardRequest(
							textField.getText(), textArea.getText());
					resp = board.getSocketController().send(req); // returns
														// PostCentralBoardResponse
				} else {
					PostClanBoardRequest req = new PostClanBoardRequest(
							textField.getText(), textArea.getText(), clanID);
					resp = board.getSocketController().send(req); // returns
														// PostClanlBoardResponse
				}
	
				if (resp.isOK()) {
					board.refresh();
				} else {
					System.err.println("Not posted!");
				}
			} catch (IOException e1) {
				board.getClientWindow().showConnectionError();
			}
		}
	}

}
