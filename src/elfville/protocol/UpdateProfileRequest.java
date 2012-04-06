package elfville.protocol;

public class UpdateProfileRequest extends Request {
	private static final long serialVersionUID = 1L;

	public String description;

	public UpdateProfileRequest(String description) {
		super();
		this.description= description;
	}

}
