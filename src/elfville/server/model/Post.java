package elfville.server.model;

/*
 * Post Model
 */
public class Post extends Model{
	private String content;
	public Post() {
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String ct) {
		content = ct;
	}

}
