package elfville.client.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import elfville.client.SocketController;
import elfville.protocol.ClanListingRequest;
import elfville.protocol.ClanListingResponse;
import elfville.protocol.Response;

public class ClanDirectory extends JPanel implements Refreshable {
	private static final long serialVersionUID = 1L;
	private final List<Post> posts = new ArrayList<Post>();

	/**
	 * Create the panel.
	 */
	public ClanDirectory() {
		super();
		add(new JLabel("Clan Directory"));
		add(new CreateClanPanel(this));
		// TODO we need a class Clan that's like post (the above ClickHandler action listener should
		// be a part of that class). Then we need to make this more like CentralBoard and ClanBoard
		// with a load clans method (instead of loading posts).
	}
	
	public void loadClans(ClanListingResponse response) {
		//TODO do this.
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
}
