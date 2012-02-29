package elfville.protocol;

import elfville.protocol.models.SerializableClan;
import elfville.protocol.models.SerializableElf;
import elfville.protocol.models.SerializablePost;

public class ModifyClanRequest extends Request {
	private static final long serialVersionUID = 1L;

	public enum ModClan {
		LEAVE, DELETE, ACCEPT, DENY, DELETEPOST, APPLY
	}
	
	// for accepting or denying applicants
	public ModifyClanRequest(SerializableElf elf, SerializableClan clan,
			boolean accept) {
		this.applicant = elf;
		this.clan = clan;
		if (accept) {
			this.requestType = ModClan.ACCEPT;
		} else {
			this.requestType = ModClan.DENY;
		}
	}
	
	// for leaving, deleting, or applying to a clan
	public ModifyClanRequest(SerializableClan clan, ModClan action) {
		this.clan = clan;
		this.requestType = action;
	}
	
	// for deleting posts on a clan board
	public ModifyClanRequest(SerializableClan clan, SerializablePost post) {
		this.clan = clan;
		this.requestType = ModClan.DELETEPOST;
		this.post = post;
	}

	public ModClan requestType;
	public SerializableClan clan;
	// TODO: should only need to send clan's modelID.
	public SerializableElf applicant;
	public SerializablePost post;
	// TODO: perhaps we only need a post model ID here?

}
