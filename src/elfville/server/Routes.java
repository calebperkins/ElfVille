package elfville.server;

import elfville.protocol.*;
import elfville.server.controller.AuthenticationControl;
import elfville.server.controller.CentralBoardControl;

public class Routes {

	private static SignInResponse respond(SignInRequest r,
			CurrentUserProfile currentUser) {
		return AuthenticationControl.signIn(r, currentUser); // TODO
	}

	private static SignUpResponse respond(SignUpRequest r,  CurrentUserProfile currentUser) {
		return AuthenticationControl.signUp(r, currentUser); // TODO
	}

	private static CentralBoardResponse respond(CentralBoardRequest r,
			CurrentUserProfile currentUser) {
		return CentralBoardControl.getAllPosts(r);
	}

	private static PostCentralBoardResponse respond(PostCentralBoardRequest r,
			CurrentUserProfile currentUser) {
		return CentralBoardControl.addPost(r, currentUser);
	}

	private static PostResponse respond(PostRequest r, CurrentUserProfile currentUser) {
		return new PostResponse(null); // TODO
	}

	public static Response processRequest(Request r, CurrentUserProfile currentUser) {
		if (r instanceof CentralBoardRequest) {
			return respond((CentralBoardRequest) r, currentUser);
		} else if (r instanceof SignInRequest) {
			return respond((SignInRequest) r, currentUser);
		} else if (r instanceof SignUpRequest) {
			return respond((SignUpRequest) r, currentUser);
		} else if (r instanceof PostRequest) {
			return respond((PostRequest) r, currentUser);
		} else if (r instanceof PostCentralBoardRequest) {
			return respond((PostCentralBoardRequest) r, currentUser);
		}
		return null; // TODO: implement rest
	}
}
