package elfville.protocol;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SignInRequest extends Request {
	private static final long serialVersionUID = 1L;
	private char[] username;
	private char[] password;
	private byte[] shared_key;
	private int shared_nonce;
	private long time;

	public SignInRequest(String name, char[] pass, SecretKey s) {
		username = name.toCharArray();
		password = pass;
		shared_key = s.getEncoded();
		
		try {
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			shared_nonce = sr.nextInt();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
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

	public int getNonce() {
		return shared_nonce;
	}

	public long getTime() {
		return time;
	}
}
