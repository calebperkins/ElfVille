package elfville.server;

import elfville.protocol.*;
import elfville.server.controller.CentralBoardControl;

public class Routes {

	public static Message processRequest(Message m) {
		if (m.getType() == "" || true) {
			GetCentralBoardRequest inM = (GetCentralBoardRequest) m;
			GetCentralBoardResponse outM = CentralBoardControl.getPosts(inM);
			return outM;
		}
		return m;
	}
}
