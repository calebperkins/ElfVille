package elfville.protocol;

public class SignUpResponse extends Response {
	private static final long serialVersionUID = 1L;
	
	public SignUpResponse(Status s, String msg) {
		super();
		status = s;
		message = msg;
	}
}
