package elfville.server.controller;

import elfville.protocol.*;
import elfville.server.model.Post;

/*
 * Control all requests (POST/GET/DELETE) to Central Board
 */
public class CentralBoardControl extends Controller{

	public static GetCentralBoardResponse getPosts(GetCentralBoardRequest inM) {
		database.postDB.printPosts();
		GetCentralBoardResponse outM = new GetCentralBoardResponse();
		outM.secret = "Valentine's day surprise!";
		return outM;
	}
	
}
