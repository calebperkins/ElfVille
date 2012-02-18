package elfville.server;

import elfville.protocol.*;
import elfville.server.controller.CentralBoardControl;

public class Routes {

	public static Response processRequest(Request m) {
		
		if (m.isA(GetCentralBoardRequest.class)) {
			GetCentralBoardRequest inM = (GetCentralBoardRequest) m;
			GetCentralBoardResponse outM = CentralBoardControl.getPosts(inM);
			return outM;
		} else if (m.isA(SignInRequest.class)) {
			// TODO: implement
			return new SignInResponse(Response.Status.FAILURE, "Not implemented");
		}
		return null; // TODO: implement rest
	}
}
