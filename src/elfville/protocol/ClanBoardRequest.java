package elfville.protocol;

public class ClanBoardRequest extends Request {
	private static final long serialVersionUID = 1L;
	private String clanName;
	
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
