package elfville.protocol;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import elfville.protocol.utils.SharedKeyCipher;

public class SignInRequest extends Request {
	private static final long serialVersionUID = 1L;
	private char[] username;
	private char[] password;
	private byte[] shared_key;
	private long time;
	private int shared_nonce;
	private byte[] iv;

	public SignInRequest(String name, char[] pass, SharedKeyCipher cipher, int nonce) {
		super();
		username = name.toCharArray();
		password = pass;
		shared_key = cipher.getSharedKey().getEncoded();
		time = System.currentTimeMillis();
		shared_nonce = nonce;
		this.iv = cipher.getIV();
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
	
	public AlgorithmParameterSpec getIV() {
		return new IvParameterSpec(iv);
	}

	public long getTime() {
		return time;
	}

	public int getSharedNonce() {
		return shared_nonce;
	}

	public void zeroOutPassword() {
		for (int i = 0; i < password.length; i++) {
			password[i] = 0;
		}
	}
}
