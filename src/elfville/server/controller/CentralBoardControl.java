package elfville.server.controller;

import java.util.ArrayList;
import java.util.List;

import elfville.protocol.*;

import elfville.server.CurrentUserProfile;
import elfville.server.model.*;
import elfville.protocol.Response.Status;

/*
 * Control all requests (POST/GET/DELETE) to Central Board
 */
public class CentralBoardControl extends Controller {

	public static CentralBoardResponse getAllPosts(CentralBoardRequest inM) {
		CentralBoardResponse outM = new CentralBoardResponse(Status.SUCCESS,
				"whatever", ControllerUtils.buildPostList(database.postDB
						.getCentralPosts()));
		outM.message = "Valentine's day surprise!";
		return outM;
	}

	public static Response addPost(PostCentralBoardRequest postRequest,
			CurrentUserProfile currentUser) {
		User user = database.userDB.findUserByModelID(currentUser
				.getCurrentUserId());
		if (user == null) {
			return new Response(Status.FAILURE);
		}
		Elf elf = user.getElf();
		Post post = new Post(postRequest.post, elf);
		database.postDB.insert(post);
		return new Response(Status.SUCCESS);
	}

}
