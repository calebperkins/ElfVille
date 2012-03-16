package elfville.protocol;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public abstract class Request implements Serializable {
	private static final long serialVersionUID = 1L;
	private int nonce;

	public Request() {
		try {
			nonce = SecureRandom.getInstance("SHA1PRNG").nextInt();
		} catch (NoSuchAlgorithmException e) {
			// shouldn't happen?
		}
	}

	public int getNonce() {
		return nonce;
	}
}
