package elfville.server;

import elfville.protocol.*;
import elfville.server.controller.CentralBoardControl;

public class Routes {
	
	private static SignInResponse respond(SignInRequest r) {
		return new SignInResponse(Response.Status.FAILURE, "Not implemented"); // TODO
	}
	
	private static GetCentralBoardResponse respond(GetCentralBoardRequest r) {
		return CentralBoardControl.getPosts(r);
	}
	
	private static SignUpResponse respond(SignUpRequest r) {
		return new SignUpResponse(); // TODO
	}
	
	private static CreatePostResponse respond(CreatePostRequest r) {
		return new CreatePostResponse(); // TODO
	}

	public static Response processRequest(Request r) {	
		if (r instanceof GetCentralBoardRequest) {
			return respond((GetCentralBoardRequest) r);
		} else if (r instanceof SignInRequest) {
			return respond((SignInRequest) r);
		} else if (r instanceof SignUpRequest) {
			return respond((SignUpRequest) r);
		} else if (r instanceof CreatePostRequest) {
			return respond((CreatePostRequest) r);
		}
		return null; // TODO: implement rest
	}
}
