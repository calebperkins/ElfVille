package elfville.protocol;

public class ClanBoardRequest extends Request {
	private static final long serialVersionUID = 1L;
	private String clanName;
	private String clanModelID;
	
	public String getClanModelID() {
		return clanModelID;
	}

	public void setClanModelID(String clanModelID) {
		this.clanModelID = clanModelID;
	}

	public ClanBoardRequest(String clanName){
		this.setClanName(clanName);
	}

	public String getClanName() {
		return clanName;
	}

	public void setClanName(String clanName) {
		this.clanName = clanName;
	}
}
