package elfville.protocol;

import elfville.protocol.models.SerializableClan;

public class CreateClanRequest extends Request {

	public SerializableClan clan;
	
	public CreateClanRequest(SerializableClan clan){
		this.clan = clan;
	}

}
