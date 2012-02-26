package elfville.client.views;

import java.io.IOException;

import javax.swing.*;

import elfville.client.ClientWindow;
import elfville.client.SocketController;
import elfville.client.views.subcomponents.Clan;
import elfville.client.views.subcomponents.CreateClanPanel;
import elfville.protocol.*;
import elfville.protocol.models.SerializableClan;

public class ClanDirectory extends JPanel implements Refreshable {
	private static final long serialVersionUID = 1L;
	private static final JLabel title = new JLabel("Clan Directory");
	private final JPanel createClan = new CreateClanPanel(this);

	/**
	 * Create the panel.
	 */
	public ClanDirectory(ClanListingResponse response) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(title);
		add(createClan);
		JPanel clanPanel = new JPanel();
		clanPanel.setLayout(new BoxLayout(clanPanel, BoxLayout.Y_AXIS));
		for (SerializableClan clan : response.clans) {
			clanPanel.add(new Clan(clan));
		}
		JScrollPane scroll = new JScrollPane(clanPanel);
		add(scroll);
	}

	@Override
	public void refresh() {
		showClanDirectory();
	}

	public static void showClanDirectory() {
		try {
			ClanListingResponse resp = SocketController
					.send(new ClanListingRequest());
			if (resp.status == Response.Status.SUCCESS) {
				ClanDirectory d = new ClanDirectory(resp);
				ClientWindow.switchScreen(d);
			} else {
				ClientWindow.showError(resp.message,
						"Error retrieving list of clans");
			}
		} catch (IOException e) {
			ClientWindow.showConnectionError();
		}
	}
}
