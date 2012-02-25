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
		
		public ClickHandler(String name) {
			this.name = name;
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
					b.changeClan(name);
					b.load(SocketController.send(new ClanBoardRequest(name)));
				} else {
					// TODO: not sure how to get the following line to do what we want.
					//ClientWindow.showError(this, resp.message, "Login error");
				}
			} catch (IOException e1) {
				// TODO: not sure how to get the following line to do what we want.
				//ClientWindow.showConnectionError(this);
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
	}

}
