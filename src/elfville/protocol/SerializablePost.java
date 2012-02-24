package elfville.protocol;

import java.io.Serializable;
import java.util.Date;

public class SerializablePost implements Serializable {
	private static final long serialVersionUID = -2419148757484798094L;

	public String modelID;
	public String elfID;
	public String username;
	
	public String title;
	public String content;
	
	public int upvotes;
	public int downvotes;
	
	public Date createdAt;
	
}
