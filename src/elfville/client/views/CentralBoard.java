package elfville.client.views;

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
 */
public class CentralBoard extends Board {
	private static final long serialVersionUID = 1L;
	private final JLabel title = new JLabel("Central Board");
	private final JPanel createPost = new CreatePostPanel(this, null);
	

	/**
	 * Creates a new CentralBoard pane (with updated information)
	 * to display to the user.
	 * @param clientWindow
	 * @param socketController
	 */
	public CentralBoard(ClientWindow clientWindow, 
			SocketController socketController) {
		super(clientWindow, socketController);
		CentralBoardRequest req = new CentralBoardRequest();
		getSocketController().sendRequest(req, this,
				"Error retrieving central board.", this);
	}

	@Override
	public void refresh() {
		new CentralBoard(clientWindow, socketController);
	}

	@Override
	public void handleRequestSuccess(Response resp) {
		CentralBoardResponse response = (CentralBoardResponse) resp;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(title);
		add(createPost);
		//TODO from here to "add(scroll)" is commonly duplicated code that could use a refactor
		JPanel postPanel = new JPanel();
		postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
		for (SerializablePost post : response.posts) {
			postPanel.add(new VotablePost(this, post));
		}
		JScrollPane scroll = new JScrollPane(postPanel);
		add(scroll);

		clientWindow.switchScreen(this);
	}

}
