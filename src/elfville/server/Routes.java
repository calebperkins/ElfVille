package elfville.server;

import elfville.protocol.*;
import elfville.server.controller.AuthenticationControl;
import elfville.server.controller.CentralBoardControl;
import elfville.server.controller.ClanBoardControl;
import elfville.server.model.Elf;
import elfville.server.model.Post;
import elfville.server.model.User;

public class Routes {

	private static Response respond(SignInRequest r,
			CurrentUserProfile currentUser) {
		return AuthenticationControl.signIn(r, currentUser); // TODO
	}

	private static Response respond(SignUpRequest r,
			CurrentUserProfile currentUser) {
		return AuthenticationControl.signUp(r, currentUser); // TODO
	}

	private static CentralBoardResponse respond(CentralBoardRequest r,
			CurrentUserProfile currentUser) {
		return CentralBoardControl.getAllPosts(r);
	}

	private static Response respond(PostCentralBoardRequest r,
			CurrentUserProfile currentUser) {
		return CentralBoardControl.addPost(r, currentUser);
	}

	private static Response respond(ModifyClanRequest r,
			CurrentUserProfile currentUser) {
		return ClanBoardControl.modifyClan(r, currentUser);
	}

	private static Response respond(VoteRequest r,
			CurrentUserProfile currentUser) {
		Response resp = new Response();
		User user = Database.DB.userDB.findUserByModelID(currentUser
				.getCurrentUserId());
		if (user == null) {
			return resp;
		}
		Elf e = user.getElf();
		if (e == null) {
			return resp;
		}
		Post post = Database.DB.postDB.findByEncryptedModelID(r.modelID);

		if (r.upsock && post.upsock(e)) {
			resp.status = Response.Status.SUCCESS;
		} else if (!r.upsock && post.downsock(e)) {
			resp.status = Response.Status.SUCCESS;
		}
		return resp;
	}

	public static Response processRequest(Request r,
			CurrentUserProfile currentUser) {
		if (r instanceof CentralBoardRequest) {
			return respond((CentralBoardRequest) r, currentUser);
		} else if (r instanceof SignInRequest) {
			return respond((SignInRequest) r, currentUser);
		} else if (r instanceof SignUpRequest) {
			return respond((SignUpRequest) r, currentUser);
		} else if (r instanceof PostCentralBoardRequest) {
			return respond((PostCentralBoardRequest) r, currentUser);
		} else if (r instanceof VoteRequest) {
			return respond((VoteRequest) r, currentUser);
		} else if (r instanceof ModifyClanRequest) {
			return respond((ModifyClanRequest) r, currentUser);
		}
		return null; // TODO: implement rest
	}
}
