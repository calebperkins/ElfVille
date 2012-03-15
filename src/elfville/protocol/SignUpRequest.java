package elfville.protocol;

import javax.crypto.SecretKey;

public class SignUpRequest extends SignInRequest {
	private static final long serialVersionUID = 1L;
	public String description;

	public SignUpRequest(String u, char[] p, SecretKey s, byte[] n1, byte[] n2,
			String d) {
		super(u, p, s, n1, n2);
		description = d;
	}

	// TODO delete this backwards compatibility with test code constructor
	public SignUpRequest(String u, String d) {
		super(u);
		description = d;
	}
}
