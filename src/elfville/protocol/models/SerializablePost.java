package elfville.protocol.models;

import java.util.Date;

public class SerializablePost extends SerializableModel {
	private static final long serialVersionUID = -2419148757484798094L;
	public String username;
	public String title;
	public int upvotes;
	public int downvotes;
	public String content;
	public Date createdAt;
	public String elfID;
	public Boolean myPost;
	public Boolean iVoted;
}
