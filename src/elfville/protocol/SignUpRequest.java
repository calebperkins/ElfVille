package elfville.protocol;

import elfville.protocol.utils.SharedKeyCipher;

public class SignUpRequest extends SignInRequest {
	private static final long serialVersionUID = 1L;
	public String description;

	public SignUpRequest(String username, char[] pass, SharedKeyCipher cipher, int nonce, String desc) {
		super(username, pass, cipher, nonce);
		description = desc;
	}

}
