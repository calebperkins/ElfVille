package elfville.protocol.models;

import java.util.List;

public class SerializableElf extends SerializableModel {
	private static final long serialVersionUID = 1L;

	public String elfName;
	public int numSocks;
	public String description;
	public List<SerializablePost> centralBoardPosts;

	@Override
	public int getChecksum() {
		int c = elfName.hashCode() ^ numSocks ^ description.hashCode();
		if (centralBoardPosts != null) {
			for (SerializablePost p : centralBoardPosts)
				c ^= p.getChecksum();
		}
		return c;
	}

}
