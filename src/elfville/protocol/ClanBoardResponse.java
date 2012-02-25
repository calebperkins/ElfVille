package elfville.protocol;

public class ClanBoardResponse extends Response {

	private static final long serialVersionUID = 1L;
	
	public SerializableClan clan;
	
	public enum ElfClanRelationship {
		OUTSIDER, APPLICANT, MEMBER, LEADER
	}
	
	public ElfClanRelationship elfStatus;
	
	public ClanBoardResponse(Status s) {
		super();
		status = s;
	}
	
}
