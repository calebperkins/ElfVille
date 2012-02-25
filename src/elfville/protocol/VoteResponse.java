package elfville.protocol;

public class VoteResponse extends Response {
	private static final long serialVersionUID = 1L;

	public VoteResponse(Status s) {
		status = s;
	}
}
