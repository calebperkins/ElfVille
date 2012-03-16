package elfville.protocol;

import javax.crypto.SecretKey;

public class SignUpRequest extends SignInRequest {
	private static final long serialVersionUID = 1L;
	public String description;

	public SignUpRequest(String username, char[] pass, SecretKey s, int nonce,
			String desc) {
		super(username, pass, s, nonce);
		description = desc;
	}

}
