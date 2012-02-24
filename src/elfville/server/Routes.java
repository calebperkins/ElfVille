package elfville.server;

import elfville.protocol.*;
import elfville.server.controller.CentralBoardControl;

public class Routes {
	
	private static SignInResponse respond(SignInRequest r, int currElfModelID) {
		return new SignInResponse(Response.Status.FAILURE, "Not implemented"); // TODO
	}
	
	private static CentralBoardResponse respond(CentralBoardRequest r, int currElfModelID) {
		return CentralBoardControl.getPosts(r);
	}
	
	private static SignUpResponse respond(SignUpRequest r, int currElfModelID) {
		return new SignUpResponse(Response.Status.FAILURE, "Not implemented"); // TODO
	}
	
	private static PostResponse respond(PostRequest r, int currElfModelID) {
		return new PostResponse(null); // TODO
	}

	public static Response processRequest(Request r, int currElfModelID) {	
		if (r instanceof CentralBoardRequest) {
			return respond((CentralBoardRequest) r, currElfModelID);
		} 
		else if (r instanceof SignInRequest) {
			return respond((SignInRequest) r, currElfModelID);
		} 
		else if (r instanceof SignUpRequest) {
			return respond((SignUpRequest) r, currElfModelID);
		} 
		else if (r instanceof PostRequest) {
			return respond((PostRequest) r, currElfModelID);
		}
		return null; // TODO: implement rest
	}
}
