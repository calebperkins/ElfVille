package elfville.server.controller;

import elfville.protocol.*;

import elfville.server.model.*;
import elfville.protocol.Response.Status;

/*
 * Control all requests (POST/GET/DELETE) to Central Board
 */
public class CentralBoardControl extends Controller{

	public static CentralBoardResponse getAllPosts(CentralBoardRequest inM) {
		database.postDB.printPosts();
		CentralBoardResponse outM = new CentralBoardResponse(Status.SUCCESS,
				"whatever", database.postDB.getCentralPosts());
		outM.message = "Valentine's day surprise!";
		return outM;
	}
	
	public static PostCentralBoardResponse addPost(PostCentralBoardRequest postRequest, int currUserModelID) {
		User user = database.userDB.findUserByModelID(currUserModelID);
		if (user == null) {
			return new PostCentralBoardResponse(Status.FAILURE);
		}
		Elf elf = user.getElf();
		Post post= new Post(postRequest.post, elf);
		database.postDB.insert(post);
		return new PostCentralBoardResponse(Status.SUCCESS);
	}
	
}
