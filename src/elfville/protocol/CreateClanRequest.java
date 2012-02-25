package elfville.protocol;

public class CreateClanRequest extends Request {

	public SerializableClan clan;
	
	public CreateClanRequest(SerializableClan clan){
		this.clan = clan;
	}

}
