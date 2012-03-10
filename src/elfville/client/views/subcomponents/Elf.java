package elfville.client.views.subcomponents;

import javax.swing.JButton;
import javax.swing.JPanel;

import elfville.client.views.Board;
import elfville.protocol.models.SerializableElf;

/**
 * Similar to Clan.java and Post.java, this provides the basic summary of an elf
 * (for use in listings of elves), which is represented as a button that allows
 * the client to view the elf's profile.
 * 
 */
public class Elf extends JPanel {
	private static final long serialVersionUID = 1L;

	public Elf(Board board, SerializableElf elf) {
		super();
		JButton elfButton = new JButton(elf.elfName + ", "
				+ Integer.toString(elf.numSocks));
		elfButton.addActionListener(new ElfHandler(board, elf.modelID));
		add(elfButton);
	}

}
