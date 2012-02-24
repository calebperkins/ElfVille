package elfville.protocol;

import java.io.Serializable;
import java.util.Date;

public class SerializablePost implements Serializable {
	private static final long serialVersionUID = -2419148757484798094L;
	public String username;
	public String title;
	public int upvotes;
	public int downvotes;
	public String content;
	public Date createdAt;
	
	public SerializablePost(elfville.server.model.Post post){
		super();
		username= post.getElf().getName();
		upvotes = post.getNumUpsock();
		downvotes = post.getNumDownsock();
		content= post.getContent();
		createdAt= post.getCreatedAt();
	}
	
	public SerializablePost(String c) {
		super();
		username = null;
		content = c;
		upvotes =0;
		downvotes = 0;
		createdAt = new Date();
	}
}
