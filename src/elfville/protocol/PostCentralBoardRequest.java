package elfville.protocol;

public class PostCentralBoardRequest extends Request {
	private static final long serialVersionUID = 1L;
	
	public SerializablePost post;
	
	public PostCentralBoardRequest(SerializablePost post) {
		this.post= post;
	}
	
	public PostCentralBoardRequest(String content, String title) {
		post = new SerializablePost();
		post.content = content;
		post.title = title;
	}
}
