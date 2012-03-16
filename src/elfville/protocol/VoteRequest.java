package elfville.protocol;

public class VoteRequest extends Request {
	private static final long serialVersionUID = 1L;

	public String modelID;
	public boolean upsock;

	public VoteRequest(String modelID, boolean upsock) {
		super();
		this.modelID = modelID;
		this.upsock = upsock;
	}

}
