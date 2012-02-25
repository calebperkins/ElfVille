package elfville.protocol;

import java.util.ArrayList;
import java.util.List;

import elfville.server.model.Post;

public class ClanBoardResponse extends Response {

	private static final long serialVersionUID = 1L;
	
	public SerializableClan clan;
	public ArrayList<SerializablePost> posts;
	
	public ClanBoardResponse(Status s) {
		super();
		status = s;
	}
	
	public ClanBoardResponse(Status s, String msg, List<Post> clanBoardPosts){
		super();
		this.status= s;
		this.message= msg;
		// posts= buildPostList(clanBoardPosts);
	}

}
