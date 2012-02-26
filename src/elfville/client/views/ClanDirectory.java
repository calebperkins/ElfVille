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
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(title);
		add(createClan);
		for (SerializableClan clan : response.clans) {
			add(new Clan(clan));
		}
		// TODO we need a class Clan that's like post (the above ClickHandler
		// action listener should
		// be a part of that class). Then we need to make this more like
		// CentralBoard and ClanBoard
		// with a load clans method (instead of loading posts).
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
