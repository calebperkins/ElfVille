package elfville.protocol;

import elfville.protocol.models.SerializableClan;
import elfville.protocol.models.SerializablePost;

public class PostClanBoardRequest extends Request {
	private static final long serialVersionUID = 1L;

	public SerializablePost post;

	public SerializableClan clan;

	public PostClanBoardRequest(SerializablePost post, String clanID) {
		this.post = post;
		this.clan = new SerializableClan();
		clan.modelID = clanID;
	}

	public PostClanBoardRequest(String title, String content, String clanID) {
		post = new SerializablePost();
		post.content = content;
		post.title = title;
		this.clan = new SerializableClan();
		clan.modelID = clanID;
	}
}
