package elfville.protocol;

import javax.crypto.SecretKey;

public class SignUpRequest extends Request {
	private static final long serialVersionUID = 1L;
	public String username;
	// public String password;
	public String description;
	public SecretKey shared_key; // TODO

	public SignUpRequest(String u, String string) {
		username = u;
		description = string;
	}
}
