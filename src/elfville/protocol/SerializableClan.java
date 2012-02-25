package elfville.protocol;

import java.io.Serializable;
import java.util.List;

public class SerializableClan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String clanName;
	public String clanDescription;
	public SerializableElf leader;
	public List<SerializableElf> members;

}
