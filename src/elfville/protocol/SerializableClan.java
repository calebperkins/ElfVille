package elfville.protocol;

import java.io.Serializable;
import elfville.server.model.Clan;

public class SerializableClan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String clanName;
	private String description;
	
	public SerializableClan(Clan clan){
		super();
		setClanName(clan.getName());
		setDescription(clan.getDescription()); 
	}

	public String getClanName() {
		return clanName;
	}

	public void setClanName(String clanName) {
		this.clanName = clanName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
