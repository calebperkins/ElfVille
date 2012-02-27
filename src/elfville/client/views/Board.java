package elfville.client.views;

import elfville.client.ClientWindow;
import elfville.client.SocketController;

public interface Board {
	public void refresh();

	public ClientWindow getClientWindow();

	public SocketController getSocketController();

}
