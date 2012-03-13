package elfville.protocol;

import javax.crypto.SecretKey;

public class SignInRequest extends Request {
	private static final long serialVersionUID = 1L;
	public String username;
	public SecretKey shared_key; // TODO

	// public String password;

	public SignInRequest(String u) {
		username = u;
	}
}
