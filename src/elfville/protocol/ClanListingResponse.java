package elfville.protocol;

import java.util.List;

import elfville.protocol.Response.Status;
import elfville.protocol.models.SerializableClan;

public class ClanListingResponse extends Response {

	public ClanListingResponse(Status s) {
		this.status= s;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<SerializableClan> clans;	


}
