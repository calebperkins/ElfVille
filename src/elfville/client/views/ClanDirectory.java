package elfville.client.views;

import javax.swing.*;

import elfville.client.ClientWindow;
import elfville.client.SocketController;
import elfville.client.views.subcomponents.Clan;
import elfville.client.views.subcomponents.CreateClanPanel;
import elfville.protocol.*;
import elfville.protocol.models.SerializableClan;

public class ClanDirectory extends Board {
	private static final long serialVersionUID = 1L;
	private final JLabel title = new JLabel("Clan Directory");
	private final JPanel createClan = new CreateClanPanel(this);

	public ClanDirectory(ClientWindow clientWindow, SocketController socketController) {
		super(clientWindow, socketController);
		ClanListingRequest req = new ClanListingRequest();
		getSocketController().sendRequest(req, this,
				"Error retrieving list of clans.", this);
	}

	@Override
	public void refresh() {
		new ClanDirectory(clientWindow, socketController);
	}

	@Override
	public void handleRequestSuccess(Response resp) {
		ClanListingResponse response = (ClanListingResponse) resp;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(title);
		add(createClan);
		JPanel clanPanel = new JPanel();
		clanPanel.setLayout(new BoxLayout(clanPanel, BoxLayout.Y_AXIS));
		for (SerializableClan clan : response.clans) {
			clanPanel.add(new Clan(this, clan));
		}
		JScrollPane scroll = new JScrollPane(clanPanel);
		add(scroll);
		clientWindow.switchScreen(this);
	}

}
