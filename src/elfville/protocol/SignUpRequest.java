package elfville.protocol;

import javax.crypto.SecretKey;

public class SignUpRequest extends SignInRequest {
	private static final long serialVersionUID = 1L;
	public String description;

	public SignUpRequest(String u, char[] p, SecretKey s, byte[] n1,
			String d) {
		super(u, p, s, n1);
		description = d;
	}

}
