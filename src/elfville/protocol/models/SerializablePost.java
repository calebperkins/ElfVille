package elfville.protocol.models;

public class SerializablePost extends SerializableModel {
	private static final long serialVersionUID = -2419148757484798094L;
	public String username;
	public String title;
	public int upvotes;
	public int downvotes;
	public String content;
	public String elfModelID;
	public boolean myPost;
	public boolean iVoted;
}
