package elfville.protocol.models;

import java.util.Date;

public class SerializablePost extends SerializableModel implements Comparable<SerializablePost> {
	private static final long serialVersionUID = -2419148757484798094L;
	public String username;
	public String title;
	public int upvotes;
	public int downvotes;
	public String content;
	public Date createdAt;
	public String elfModelID;
	public boolean myPost;
	public boolean iVoted;
	
	public int getNumSocks() {
		return upvotes - downvotes;
	}
	
	/**
	 * Sort by greatest number of socks first, then newest.
	 */
	@Override
	public int compareTo(SerializablePost other) {
		if (getNumSocks() == other.getNumSocks()) {
			return other.createdAt.compareTo(createdAt);
		}
		return other.getNumSocks() - getNumSocks();
	}
}
