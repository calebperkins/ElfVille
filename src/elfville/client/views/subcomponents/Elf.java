package elfville.client.views.subcomponents;

import javax.swing.JButton;
import javax.swing.JPanel;

import elfville.protocol.models.SerializableElf;

public class Elf extends JPanel {
	private static final long serialVersionUID = 1L;

	public Elf(SerializableElf elf) {
		super();
		JButton elfButton = new JButton(elf.elfName + 
				", " + Integer.toString(elf.numSocks));
		elfButton.addActionListener(new ElfHandler(elf.modelID));
		add(elfButton);
	}

}
