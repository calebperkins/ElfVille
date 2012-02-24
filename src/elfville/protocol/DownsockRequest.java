package elfville.protocol;

public class DownsockRequest extends Request {
	private static final long serialVersionUID = 1L;
	
	public String modelID;
	
	public DownsockRequest(String modelID) {
		this.modelID = modelID;
	}
}
