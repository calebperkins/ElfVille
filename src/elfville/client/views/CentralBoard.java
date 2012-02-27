package elfville.client.views;

import java.io.IOException;

import javax.swing.*;

import elfville.client.ClientWindow;
import elfville.client.SocketController;
import elfville.client.views.subcomponents.CreatePostPanel;
import elfville.client.views.subcomponents.VotablePost;
import elfville.protocol.*;
import elfville.protocol.models.SerializablePost;

/**
 * Renders the central board using the central board request.
 * 
 * @author Caleb Perkins
 * 
 */
public class CentralBoard extends JPanel implements Board {
	private static final long serialVersionUID = 1L;
	private final JLabel title = new JLabel("Central Board");
	private final JPanel createPost = new CreatePostPanel(this, null);
	private ClientWindow clientWindow;
	private SocketController socketController;

	public CentralBoard(ClientWindow clientWindow, 
			SocketController socketController) {
		super();
		CentralBoardResponse response;
		try {
			response = socketController.send(new CentralBoardRequest());
			if (response.status == Response.Status.SUCCESS) {

				this.clientWindow = clientWindow;
				this.socketController = socketController;
				setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				add(title);
				add(createPost);
				JPanel postPanel = new JPanel();
				postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
				for (SerializablePost post : response.posts) {
					postPanel.add(new VotablePost(post, this));
				}
				JScrollPane scroll = new JScrollPane(postPanel);
				add(scroll);

				clientWindow.switchScreen(this);
			} else {
				clientWindow.showError(response.message,
						"Error retrieving central board");
			}
		} catch (IOException e) {
			clientWindow.showConnectionError();
		}
	}

	public ClientWindow getClientWindow() {
		return clientWindow;
	}

	public SocketController getSocketController() {
		return socketController;
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		new CentralBoard(clientWindow, socketController);
	}

}
