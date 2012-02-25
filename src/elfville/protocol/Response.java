package elfville.protocol;

import java.io.Serializable;

public class Response implements Serializable {
	private static final long serialVersionUID = 1L;
	public String message;
	public Status status;

	public static enum Status {
		SUCCESS, FAILURE
	}

	public boolean isOK() {
		return status == Status.SUCCESS;
	}

	public Response() {
		status = Status.FAILURE;
		message = "Unknown error";
	}

	public Response(Status s, String msg) {
		status = s;
		message = msg;
	}
	
	public Response(Status s) {
		status = s;
	}
}
