package elfville.client.views;

import javax.swing.JPanel;

import elfville.client.ClientWindow;
import elfville.client.SocketController;

/**
 * abstract class that gives access to the clientWindow and 
 * socket controller (which used to be static to avoid this
 * passing around, but we decided the evils of static were worse),
 * as well as forces there to be a "refresh" method that buttons
 * (actions really) can use to show that something did or did not happen. 
 *
 */
public abstract class Board 
extends JPanel implements SocketController.SuccessFunction {
	private static final long serialVersionUID = 1L;
	protected ClientWindow clientWindow;
	protected SocketController socketController;
	
	/**
	 * allows actions (from buttons) to refresh the display
	 * of the board that the client is currently viewing
	 * (thereby showing the client that their request went through).
	 */
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
