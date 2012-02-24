package elfville.server;

import elfville.protocol.*;
import elfville.server.controller.CentralBoardControl;

public class Routes {
	
	private static SignInResponse respond(SignInRequest r) {
		return new SignInResponse(Response.Status.FAILURE, "Not implemented"); // TODO
	}
	
	private static CentralBoardResponse respond(CentralBoardRequest r) {
		return CentralBoardControl.getPosts(r);
	}
	
	private static SignUpResponse respond(SignUpRequest r) {
		return new SignUpResponse(Response.Status.FAILURE, "Not implemented"); // TODO
	}
	
	private static PostResponse respond(PostRequest r) {
		return new PostResponse(null); // TODO
	}

	public static Response processRequest(Request r) {	
		if (r instanceof CentralBoardRequest) {
			return respond((CentralBoardRequest) r);
		} else if (r instanceof SignInRequest) {
			return respond((SignInRequest) r);
		} else if (r instanceof SignUpRequest) {
			return respond((SignUpRequest) r);
		} else if (r instanceof PostRequest) {
			return respond((PostRequest) r);
		}
		return null; // TODO: implement rest
	}
}
