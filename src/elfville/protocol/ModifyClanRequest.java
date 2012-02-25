package elfville.protocol;

import elfville.protocol.models.SerializableClan;

public class ModifyClanRequest extends Request {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public enum ModClan {
		JOIN, LEAVE, DELETE
	}
	
	public ModClan requestType;
	public SerializableClan clan;

}
