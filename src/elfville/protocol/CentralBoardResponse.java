package elfville.protocol;

import java.util.ArrayList;
import java.util.List;

import elfville.server.model.Post;

public class CentralBoardResponse extends Response {

	private static final long serialVersionUID = 1L;
	public ArrayList<SerializablePost> posts;
	
	public CentralBoardResponse(Status s, String msg, ArrayList<SerializablePost> arrayList){
		super();
		this.status= s;
		this.posts= arrayList;
	}
}
