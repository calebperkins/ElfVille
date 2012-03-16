package elfville.protocol;

import java.io.Serializable;

public abstract class Request implements Serializable {
	private static final long serialVersionUID = 1L;
	private int nonce;

	public void setNonce(int n) {
		nonce = n;
	}

	public int getNonce() {
		return nonce;
	}
}
