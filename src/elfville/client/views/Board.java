package elfville.client.views;

import javax.swing.JPanel;

import elfville.client.ClientWindow;
import elfville.client.SocketController;

public abstract class Board extends JPanel {
	private static final long serialVersionUID = 1L;
	protected ClientWindow clientWindow;
	protected SocketController socketController;
	
	public abstract void refresh();
	
	public Board(ClientWindow clientWindow, 
			SocketController socketController) {
		super();
		this.clientWindow = clientWindow;
		this.socketController = socketController;
	}

	public ClientWindow getClientWindow() {
		return clientWindow;
	}

	public SocketController getSocketController() {
		return socketController;
	}

}
