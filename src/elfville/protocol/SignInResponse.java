package elfville.protocol;

public class SignInResponse extends Response {
	private static final long serialVersionUID = 1L;
	
	public SignInResponse(Status s, String msg) {
		super();
		status = s;
	}
}
