package elfville.protocol;

import java.io.Serializable;
import java.util.Date;

public class SerializablePost implements Serializable {
	private static final long serialVersionUID = -2419148757484798094L;
	public String username;
	public int upvotes;
	public int downvotes;
	public String content;
	public Date timestamp;
}
