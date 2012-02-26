package elfville.client.views.subcomponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import elfville.client.ClientWindow;
import elfville.client.SocketController;
import elfville.client.views.Refreshable;
import elfville.protocol.*;
import elfville.protocol.models.SerializableClan;

public class CreateClanPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final JTextArea description = new JTextArea();
	private final JTextField name = new JTextField();
	private final JButton button = new JButton("Create Clan");
	private final JLabel nameLabel = new JLabel("Name");
	private final JLabel descriptionLabel = new JLabel("Description");
	private final Refreshable board;

	public CreateClanPanel(Refreshable board) {
		super();
		this.board = board;
		makeThePanel();
	}

	private void makeThePanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Create Clan"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		button.addActionListener(this);

		add(nameLabel);
		add(name);
		add(descriptionLabel);
		add(description);
		add(button);
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
				Response resp = SocketController.send(req);
				if (resp.isOK()) {
					board.refresh();
				} else {
					System.err.println("Posting failed.");
				}
			} catch (IOException e1) {
				ClientWindow.showConnectionError();
			}
		}
	}

}
