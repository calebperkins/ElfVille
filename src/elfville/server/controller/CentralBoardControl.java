package elfville.server.controller;

import elfville.protocol.*;

import elfville.server.model.Post;
import elfville.protocol.Response.Status;

/*
 * Control all requests (POST/GET/DELETE) to Central Board
 */
public class CentralBoardControl extends Controller{

	public static CentralBoardResponse getPosts(CentralBoardRequest inM) {
		database.postDB.printPosts();
		CentralBoardResponse outM = new CentralBoardResponse(Status.SUCCESS,
				"whatever", database.postDB.getCentralPosts());
		outM.message = "Valentine's day surprise!";
		return outM;
	}
	
	public static PostCentralBoardResponse addPost(PostCentralBoardRequest postRequest){
		Post post= new Post(postRequest.post);
		database.postDB.insert(post);
		return new PostCentralBoardResponse(Status.SUCCESS);
	}
	
}
