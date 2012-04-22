package elfville.protocol.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SerializableClan extends SerializableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String clanName;
	public String clanDescription;
	public int numSocks;
	public SerializableElf leader;
	public Set<SerializableElf> members;
	public List<SerializableElf> applicants;
	public List<SerializablePost> posts;

	public SerializableClan() {
		super();
		posts = new ArrayList<SerializablePost>();
		applicants = new ArrayList<SerializableElf>();
	}

	public SerializableClan(String name, String description) {
		clanName = name;
		clanDescription = description;
	}

	@Override
	public int getChecksum() {
		int c = clanName.hashCode() ^ clanDescription.hashCode() ^ numSocks ^ leader.getChecksum();
		for (SerializableModel member : members)
			c ^= member.getChecksum();
		for (SerializableModel member : applicants)
			c ^= member.getChecksum();
		for (SerializableModel member : posts)
			c ^= member.getChecksum();
		return c;
	}

}
