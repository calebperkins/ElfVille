package elfville.protocol;

import java.util.ArrayList;

import java.util.List;

import elfville.protocol.Response.Status;
import elfville.server.model.Post;

public class ClanBoardResponse extends Response {

	private static final long serialVersionUID = 1L;
	private ArrayList<SerializablePost> posts;
	
	public ClanBoardResponse(Status s, String msg, List<Post> clanBoardPosts){
		super();
		this.status= s;
		this.secret= msg;
		posts= buildPostList(clanBoardPosts);
	}

	@SuppressWarnings("unchecked")
	public List<SerializablePost> getPosts() {
		return (List<SerializablePost>) posts.clone();
	}
	
	private ArrayList<SerializablePost> buildPostList(List<Post> centralBoardPosts){
		ArrayList<SerializablePost> out = new ArrayList<SerializablePost>();
		for (Post p : centralBoardPosts){
			SerializablePost s= new SerializablePost(p);
			out.add(s);
		}
		return out;
	}
}
