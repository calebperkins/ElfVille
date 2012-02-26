package elfville.protocol;

import elfville.protocol.models.SerializableClan;
import elfville.protocol.models.SerializableElf;

public class ModifyClanRequest extends Request {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum ModClan {
		JOIN, LEAVE, DELETE, ACCEPT, DELETEPOST
	}

	public ModClan requestType;
	public SerializableClan clan;
	public SerializableElf applicant;

}
