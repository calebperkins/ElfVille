package elfville.protocol;

public class PostCentralBoardRequest extends Request {
	private static final long serialVersionUID = 1L;
	
	public String content;
	
	public PostCentralBoardRequest(String content) {
		this.content = content;
	}
}
