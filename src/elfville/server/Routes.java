package elfville.server;

import elfville.protocol.*;
import elfville.server.controller.AuthenticationControl;
import elfville.server.controller.CentralBoardControl;
import elfville.server.model.Elf;
import elfville.server.model.Post;

public class Routes {

	private static SignInResponse respond(SignInRequest r,
			Integer currUserModelID) {
		return AuthenticationControl.signIn(r, currUserModelID); // TODO
	}

	private static SignUpResponse respond(SignUpRequest r, int currUserModelID) {
		return AuthenticationControl.signUp(r); // TODO
	}

	private static CentralBoardResponse respond(CentralBoardRequest r,
			int currUserModelID) {
		return CentralBoardControl.getAllPosts(r);
	}

	private static PostCentralBoardResponse respond(PostCentralBoardRequest r,
			int currUserModelID) {
		return CentralBoardControl.addPost(r, currUserModelID);
	}

	private static PostResponse respond(PostRequest r, int currUserModelID) {
		return new PostResponse(Response.Status.FAILURE); // TODO
	}
	
	private static VoteResponse respond(VoteRequest r, int currUserModelID) {
		Elf e = Database.DB.userDB.findUserByModelID(currUserModelID).getElf();
		Post post = Database.DB.postDB.findPostByModelID(SecurityUtils.decryptStringToInt(r.modelID));
		VoteResponse resp = new VoteResponse(Response.Status.FAILURE);
		if (r.upsock && post.upsock(e)) {
			resp.status = Response.Status.SUCCESS;
		} else if (!r.upsock && post.downsock(e)) {
			resp.status = Response.Status.SUCCESS;
		}
		return resp;
	}

	public static Response processRequest(Request r, int currUserModelID) {
		if (r instanceof CentralBoardRequest) {
			return respond((CentralBoardRequest) r, currUserModelID);
		} else if (r instanceof SignInRequest) {
			return respond((SignInRequest) r, currUserModelID);
		} else if (r instanceof SignUpRequest) {
			return respond((SignUpRequest) r, currUserModelID);
		} else if (r instanceof PostRequest) {
			return respond((PostRequest) r, currUserModelID);
		} else if (r instanceof PostCentralBoardRequest) {
			return respond((PostCentralBoardRequest) r, currUserModelID);
		} else if (r instanceof VoteRequest) {
			return respond((VoteRequest) r, currUserModelID);
		}
		return null; // TODO: implement rest
	}
}
