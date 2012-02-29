package elfville.client.views.subcomponents;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import elfville.client.views.Board;
import elfville.protocol.ClanBoardResponse;
import elfville.protocol.models.SerializableElf;

/**
 * This creates a panel that lists the members in a clan with links
 * to their profiles. 
 *
 */
public class ClanMembers extends JPanel {
	private static final long serialVersionUID = 1L;

	public ClanMembers(Board board, ClanBoardResponse response) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		JPanel memberPanel = new JPanel();
		memberPanel.setLayout(new BoxLayout(memberPanel, BoxLayout.Y_AXIS));
		for (SerializableElf elf : response.clan.members) {
			memberPanel.add(new Elf(board, elf));
		}
		JScrollPane scroll = new JScrollPane(memberPanel);
		//scroll.setMinimumSize(new Dimension(100, 40));
		//scroll.setPreferredSize(new Dimension(100, 80));
		add(scroll);
	}

}
