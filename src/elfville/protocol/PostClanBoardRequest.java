package elfville.protocol;

import elfville.protocol.models.SerializablePost;

public class PostClanBoardRequest extends Request {
	private static final long serialVersionUID = 1L;

	public String clanName;

	public SerializablePost post;

	public PostClanBoardRequest(SerializablePost post, String clanName) {
		this.post = post;
		this.clanName = clanName;
	}

	public PostClanBoardRequest(String content, String title, String clanName) {
		post = new SerializablePost();
		post.content = content;
		post.title = title;
		this.clanName = clanName;
	}
}
