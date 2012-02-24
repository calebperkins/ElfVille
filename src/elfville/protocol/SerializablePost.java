package elfville.protocol;

import java.io.Serializable;
import java.util.Date;

public class SerializablePost implements Serializable {
	private static final long serialVersionUID = -2419148757484798094L;
	public String username;
	public int upvotes;
	public int downvotes;
	public String content;
	public Date updated_at;
	public Date created_at;
	
	public SerializablePost(elfville.server.model.Post post){
		super();
		username= post.getElf().getName();
		upvotes = post.getNumUpsock();
		downvotes = post.getNumDownsock();
		content= post.getContent();
		updated_at= post.getUpdatedAt();
		created_at= post.getCreatedAt();
	}
}
