package elfville.protocol;

public class UpsockRequest extends Request {
	private static final long serialVersionUID = 1L;
	
	public String modelID;
	
	public UpsockRequest(String modelID) {
		this.modelID = modelID;
	}
	
}
