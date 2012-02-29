package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;

import elfville.client.views.ClanDirectory;
import elfville.protocol.*;
import elfville.protocol.models.SerializableClan;

/**
 * This panel (for use in clan listing page) allows the
 * user to create a new clan (providing room for 
 * clan name and description, and a button to request
 * that the server create it). 
 *
 */
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
			SerializableClan clan;
			clan = new SerializableClan(textField.getText(), textArea.getText());
			CreateClanRequest req = new CreateClanRequest(clan);
			board.getSocketController().sendRequest(req, board, "Failed to create clan.", null);
		}
	}

}
