package elfville.protocol;

import elfville.protocol.models.SerializableClan;
import elfville.protocol.models.SerializableElf;
import elfville.protocol.models.SerializablePost;

public class ModifyClanRequest extends Request {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum ModClan {
		LEAVE, DELETE, ACCEPT, DENY, DELETEPOST, APPLY
	}
	
	//TODO needs a contstructor (also fix clandetails.java if we add this)
	// (though this request is used for so many things a constructor may be
	// difficult)

	public ModClan requestType;
	public SerializableClan clan;
	// TODO: should only need to send clan's modelID.
	public SerializableElf applicant;
	public SerializablePost post;
	// TODO: perhaps we only need a post model ID here?

}
