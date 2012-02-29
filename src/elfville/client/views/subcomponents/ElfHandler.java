package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import elfville.client.views.Board;
import elfville.client.views.ElfProfile;

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
