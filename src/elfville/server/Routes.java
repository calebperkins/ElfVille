package elfville.server;

import elfville.protocol.*;
import elfville.server.controller.*;

public class Routes {
	
	private static SignInResponse respond(SignInRequest r, int currUserModelID) {
		return AuthenticationControl.signIn(r, currUserModelID);
	}

	private static SignUpResponse respond(SignUpRequest r, int currUserModelID) {
		return AuthenticationControl.signUp(r, currUserModelID);
	}
	
	private static CentralBoardResponse respond(CentralBoardRequest r, int currUserModelID) {
		return CentralBoardControl.getAllPosts(r);
	}
	
	private static PostCentralBoardResponse respond(PostCentralBoardRequest r, int currUserModelID) {
		return CentralBoardControl.addPost(r, currUserModelID);
	}
	
	private static PostResponse respond(PostRequest r, int currUserModelID) {
		return new PostResponse(null); // TODO
	}

	public static Response processRequest(Request r, int currUserModelID) {	
		if (r instanceof CentralBoardRequest) {
			return respond((CentralBoardRequest) r, currUserModelID);
		} 
		else if (r instanceof SignInRequest) {
			return respond((SignInRequest) r, currUserModelID);
		} 
		else if (r instanceof SignUpRequest) {
			return respond((SignUpRequest) r, currUserModelID);
		} 
		else if (r instanceof PostRequest) {
			return respond((PostRequest) r, currUserModelID);
		}
		return null; // TODO: implement rest
	}
}
