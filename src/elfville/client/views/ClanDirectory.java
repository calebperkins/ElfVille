package elfville.client.views;

import java.io.IOException;

import javax.swing.*;

import elfville.client.ClientWindow;
import elfville.client.SocketController;
import elfville.client.views.subcomponents.Clan;
import elfville.client.views.subcomponents.CreateClanPanel;
import elfville.protocol.*;
import elfville.protocol.models.SerializableClan;

public class ClanDirectory extends Board {
	private static final long serialVersionUID = 1L;
	private final JLabel title;
	private final JPanel createClan;

	private ClientWindow clientWindow;
	private SocketController socketController;

	public ClanDirectory(ClientWindow clientWindow, SocketController socketController) {
		super(clientWindow, socketController);

		this.clientWindow = clientWindow;
		this.socketController = socketController;
		
		title = new JLabel("Clan Directory");
		createClan = new CreateClanPanel(this);

		try {
			ClanListingResponse response = socketController.send(new ClanListingRequest());
			if (response.status == Response.Status.SUCCESS) {
				setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				add(title);
				add(createClan);
				JPanel clanPanel = new JPanel();
				clanPanel.setLayout(new BoxLayout(clanPanel, BoxLayout.Y_AXIS));
				for (SerializableClan clan : response.clans) {
					clanPanel.add(new Clan(clan, clientWindow, socketController));
				}
				JScrollPane scroll = new JScrollPane(clanPanel);
				add(scroll);
				clientWindow.switchScreen(this);
			} else {
				clientWindow.showError(response.message, "Error retrieving list of clans");
			}

		} catch (IOException e) {
			clientWindow.showConnectionError();
		}
	}

	@Override
	public void refresh() {
		new ClanDirectory(clientWindow, socketController);
	}

}
