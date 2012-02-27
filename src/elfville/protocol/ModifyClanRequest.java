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
		JOIN, LEAVE, DELETE, ACCEPT, DELETEPOST
	}
	
	//TODO needs a contstructor (also fix clandetails.java if we add this)

	public ModClan requestType;
	public SerializableClan clan;
	// TODO: should only need to send clan's modelID.
	public SerializableElf applicant;
	// TODO: this makes aaron wary, why is elf self reporting who he is?
	// actually nvm, it's worse than that, because the client has no easy way of knowing who this elf is.
	public SerializablePost post;
	// Hmm, aaron does not understand point of serializable post here...

}
