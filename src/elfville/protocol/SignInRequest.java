package elfville.protocol;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SignInRequest extends Request {
	private static final long serialVersionUID = 1L;
	private char[] username;
	private char[] password;
	private byte[] shared_key;
	private byte[] shared_nonce;
	private long time;

	public SignInRequest(String name, char[] pass, SecretKey s,
			byte[] shared_nonce) {
		username = name.toCharArray();
		password = pass;
		shared_key = s.getEncoded();
		this.shared_nonce = shared_nonce;
		time = System.currentTimeMillis();
	}

	public String getUsername() {
		return new String(username);
	}

	public String getPassword() {
		return new String(password);
	}

	public SecretKey getSharedKey() {
		return new SecretKeySpec(shared_key, 0, shared_key.length, "AES");
	}

	public byte[] getNonce() {
		return shared_nonce;
	}

	public long getTime() {
		return time;
	}
}
