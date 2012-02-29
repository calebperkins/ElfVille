package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import elfville.client.views.Board;
import elfville.client.views.ElfProfile;

/**
 * The action listener that requests an elf's profile and displays it
 * when a client clicks on a button linking to that elf.
 *
 */
public class ElfHandler implements ActionListener {
	private String elfModelID;
	Board board;
	
	public ElfHandler(Board board, String elfModelID) {
		this.elfModelID = elfModelID;
		this.board = board;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		new ElfProfile(board.getClientWindow(), board.getSocketController(), elfModelID);
	}

}
