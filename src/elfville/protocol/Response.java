package elfville.protocol;

import java.io.Serializable;

public class Response implements Serializable {
	private static final long serialVersionUID = 1L;
	public String message;
	public Status status;
	public int nonce; // TODO

	public static enum Status {
		SUCCESS, FAILURE
	}

	public boolean isOK() {
		return status == Status.SUCCESS;
	}

	public int getNonce() {
		return nonce;
	}

	public void setNonce(int n) {
		nonce = n;
	}

	public Response() {
		status = Status.FAILURE;
		message = "Unknown error";
	}

	public Response(Status s, String msg) { // TODO: add nonce
		status = s;
		message = msg;
	}

	public Response(Status s) {
		status = s;
		message = "";
	}
}
