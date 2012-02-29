package elfville.protocol;

import elfville.protocol.models.SerializableElf;

public class ProfileRequest extends Request {

	public ProfileRequest(String elfID) {
		modelID = elfID;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//public SerializableElf elf;
	public String modelID;

}
