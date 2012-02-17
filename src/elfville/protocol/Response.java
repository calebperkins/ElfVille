package elfville.protocol;

public abstract class Response extends Message {
	private static final long serialVersionUID = 1L;

	public static enum Status {
		SUCCESS,
		FAILURE
	}
	
	public Status status;
}
