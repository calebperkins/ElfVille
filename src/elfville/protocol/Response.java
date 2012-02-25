package elfville.protocol;

import java.io.Serializable;

public abstract class Response implements Serializable {
	private static final long serialVersionUID = 1L;
	public String message;

	public static enum Status {
		SUCCESS, FAILURE
	}

	public Status status = Status.FAILURE;

	public boolean isOK() {
		return status == Status.SUCCESS;
	}
}
