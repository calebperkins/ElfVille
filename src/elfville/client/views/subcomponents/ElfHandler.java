package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ElfHandler implements ActionListener {
	private String elfModelID;
	
	public ElfHandler(String elfModelID) {
		this.elfModelID = elfModelID;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO load the elf profile (which currently doesn't exist
		// (profiles don't))
	}

}
