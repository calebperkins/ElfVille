package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.io.IOException;

import elfville.client.views.ClanDirectory;
import elfville.protocol.*;
import elfville.protocol.models.SerializableClan;

public class CreateClanPanel extends CreatePanel {
	private static final long serialVersionUID = 1L;

	public CreateClanPanel(ClanDirectory board) {
		super(board, "Name", "Description", "Create");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (textField.getText().equals("") || textArea.getText().equals("")) {
			System.err.println("Need both a " + textFieldLabel +
					" and a " + textAreaLabel + " to " + buttonLabel);
		} else {
			try {
				//TODO: maybe consider deduplicating this similar code with
				// CreatePostPanel. Maybe not.
				SerializableClan clan;
				clan = new SerializableClan(textField.getText(), textArea.getText());
				CreateClanRequest req = new CreateClanRequest(clan);
				Response resp = board.getSocketController().send(req);
				if (resp.isOK()) {
					board.refresh();
				} else {
					System.err.println("Posting failed.");
				}
			} catch (IOException e1) {
				board.getClientWindow().showConnectionError();
			}
		}
	}

}
