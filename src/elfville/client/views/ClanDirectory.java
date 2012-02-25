package elfville.client.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import elfville.client.SocketController;
import elfville.protocol.ClanBoardRequest;
import elfville.protocol.ClanBoardResponse;
import elfville.protocol.Response;

public class ClanDirectory extends JPanel {
	
	private class ClickHandler implements ActionListener {
		private String name;
		private ClanDirectory component;
		
		public ClickHandler(String name, ClanDirectory component) {
			this.name = name;
			this.component = component;
		}

		/**
		 * Click on a clan and get directed to its clan page.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO name is the name of the clan, right?
			ClanBoardRequest req = new ClanBoardRequest(name);
			try {
				ClanBoardResponse resp = SocketController.send(req);
				if (resp.status == Response.Status.SUCCESS){
					ClanBoard b = (ClanBoard) ClientWindow.switchScreen("clan_board");
					b.changeClanLoadPosts(name, SocketController.send(new ClanBoardRequest(name)));
				} else {
					ClientWindow.showError(component, resp.message, "Login error");
				}
			} catch (IOException e1) {
				ClientWindow.showConnectionError(component);
			}
		}
		
	}

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ClanDirectory() {
		super();
		add(new JLabel("Clan Directory"));
		// TODO we need a class Clan that's like post (the above ClickHandler action listener should
		// be a part of that class). Then we need to make this more like CentralBoard and ClanBoard
		// with a load clans method (instead of loading posts).
	}

}
