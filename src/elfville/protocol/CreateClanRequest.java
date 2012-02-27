package elfville.protocol;

import elfville.protocol.models.SerializableClan;

public class CreateClanRequest extends Request {
	private static final long serialVersionUID = 2889176986829681490L;
	public SerializableClan clan;

	public CreateClanRequest(SerializableClan clan) {
		this.clan = clan;
	}

}
