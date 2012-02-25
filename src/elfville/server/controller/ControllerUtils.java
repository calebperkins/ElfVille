package elfville.server.controller;

import java.util.ArrayList;
import java.util.List;

import elfville.protocol.SerializablePost;
import elfville.server.model.Post;

public class ControllerUtils extends Controller {
	
	public static ArrayList<SerializablePost> buildPostList(List<Post> boardPosts){
		ArrayList<SerializablePost> out = new ArrayList<SerializablePost>();
		for (Post p : boardPosts){
			SerializablePost s= p.getSerializablePost();
			out.add(s);
		}
		return out;
	}

}
