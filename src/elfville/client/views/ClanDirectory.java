package elfville.client.views;

import java.io.IOException;

import javax.swing.*;

import elfville.client.SocketController;
import elfville.client.views.subcomponents.CreateClanPanel;
import elfville.protocol.CentralBoardResponse;
import elfville.protocol.ClanListingRequest;
import elfville.protocol.ClanListingResponse;

public class ClanDirectory extends JPanel implements Refreshable {
	private static final long serialVersionUID = 1L;
	private static final JLabel title = new JLabel("Clan Directory");
	private final JPanel createClan = new CreateClanPanel(this);

	/**
	 * Create the panel.
	 */
	public ClanDirectory(ClanListingResponse response) {
		super();
		add(title);
		add(createClan);
		//for (SerializableClan clan : response.) // (clan listing response not yet implemented
		// TODO we need a class Clan that's like post (the above ClickHandler action listener should
		// be a part of that class). Then we need to make this more like CentralBoard and ClanBoard
		// with a load clans method (instead of loading posts).
	}
	
	@Override
	public void refresh() {
		showClanDirectory();
	}
	
	public static void showClanDirectory(){
		// TODO do this
	}
}
