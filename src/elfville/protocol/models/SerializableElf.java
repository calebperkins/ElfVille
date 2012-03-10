package elfville.protocol.models;

import java.util.List;

public class SerializableElf extends SerializableModel {
	private static final long serialVersionUID = 1L;

	public String elfName;
	public int numSocks;
	public String description;
	public List<SerializablePost> centralBoardPosts;

}
