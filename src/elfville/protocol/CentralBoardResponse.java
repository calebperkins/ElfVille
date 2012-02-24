package elfville.protocol;

import java.util.ArrayList;
import java.util.List;

import elfville.server.model.Post;

public class CentralBoardResponse extends Response {

	private static final long serialVersionUID = 1L;
	private ArrayList<SerializablePost> posts;
	
	public CentralBoardResponse(Status s, String msg, List<Post> centralBoardPosts){
		super();
		this.status= s;
		posts= buildPostList(centralBoardPosts);
	}

	@SuppressWarnings("unchecked")
	public List<SerializablePost> getPosts() {
		return (List<SerializablePost>) posts.clone();
	}
	
	private ArrayList<SerializablePost> buildPostList(List<Post> centralBoardPosts){
		ArrayList<SerializablePost> out = new ArrayList<SerializablePost>();
		for (Post p : centralBoardPosts){
			SerializablePost s= p.getSerializablePost();
			out.add(s);
		}
		return out;
	}
}
