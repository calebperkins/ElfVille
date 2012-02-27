package elfville.client.views.subcomponents;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import elfville.client.ClientWindow;
import elfville.client.SocketController;
import elfville.client.views.ClanDirectory;
import elfville.client.views.Board;
import elfville.protocol.*;
import elfville.protocol.models.SerializableClan;

public class CreateClanPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final JTextArea description = new JTextArea();
	private final JTextField name = new JTextField();
	private final JButton button = new JButton("Create Clan");
	private final JLabel nameLabel = new JLabel("Name");
	private final JLabel descriptionLabel = new JLabel("Description");
	private final ClanDirectory clanDirectory;

	public CreateClanPanel(ClanDirectory board) {
		super();
		this.clanDirectory = board;
		makeThePanel();
	}

	private void makeThePanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Create Clan"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		button.addActionListener(this);

		add(nameLabel);
		//name.setMinimumSize(new Dimension(300, 20));
		//name.setPreferredSize(new Dimension(300, 20));
		add(name);
		add(descriptionLabel);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		JScrollPane scrollableDescription = new JScrollPane(description);
		scrollableDescription.setPreferredSize(new Dimension(300,80));
		scrollableDescription.setMinimumSize(new Dimension(300, 40));
		add(scrollableDescription);
		add(button);
		//setMaximumSize(new Dimension(400, 470));
		//setMinimumSize(new Dimension(400, 470));
		//TODO: fix the code duplication between create clan panel and create post panel
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (name.getText().equals("")) {
			System.err.println("Need a clan name to create a clan!");
		} else {
			try {
				SerializableClan clan = new SerializableClan();
				clan.clanName = name.getText();
				clan.clanDescription = description.getText();
				CreateClanRequest req = new CreateClanRequest(clan);
				Response resp = clanDirectory.getSocketController().send(req);
				if (resp.isOK()) {
					clanDirectory.refresh();
				} else {
					System.err.println("Posting failed.");
				}
			} catch (IOException e1) {
				clanDirectory.getClientWindow().showConnectionError();
			}
		}
	}

}
