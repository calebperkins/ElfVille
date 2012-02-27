package elfville.protocol;

import java.util.List;

import elfville.protocol.models.SerializableClan;

public class ClanListingResponse extends Response {
	private static final long serialVersionUID = 1L;

	public ClanListingResponse(Status s) {
		this.status = s;
	}

	public List<SerializableClan> clans;

}
