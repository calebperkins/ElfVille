package elfville.protocol;

import javax.crypto.SecretKey;

public class SignInRequest extends Request {
	private static final long serialVersionUID = 1L;
	public String username;
	public char[] password;
	public SecretKey shared_key;

	public byte[] login_nonce;
	public byte[] shared_nonce;

	// TODO add time in

	public SignInRequest(String u, char[] p, SecretKey s, byte[] login_nonce,
			byte[] shared_nonce) {
		username = u;
		password = p;
		shared_key = s;
		this.login_nonce = login_nonce;
		this.shared_nonce = shared_nonce;
		// TODO send time
	}

	// TODO delete this backwards compatibility with test code constructor
	public SignInRequest(String u) {
		username = u;
	}

	public void zeroPasswordArray() {
		for (int i = 0; i < password.length; i++) {
			password[i] = 0;
		}
	}
}
