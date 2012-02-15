package elfville.server.controller;

import elfville.protocol.*;
import elfville.server.model.Post;

/*
 * Control all requests (POST/GET/DELETE) to Central Board
 */
public class CentralBoardControl extends Controller{

	public static GetCentralBoardOut getPosts(GetCentralBoardIn inM) {
		database.postDB.printPosts();
		GetCentralBoardOut outM = new GetCentralBoardOut();
		outM.secret = "Valentine's day surprise!";
		return outM;
	}
	
}
