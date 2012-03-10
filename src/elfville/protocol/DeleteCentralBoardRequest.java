package elfville.protocol;

import elfville.protocol.models.SerializablePost;

public class DeleteCentralBoardRequest extends Request {
	public DeleteCentralBoardRequest(SerializablePost post) {
		this.post = post;
	}

	private static final long serialVersionUID = 6976109492024907178L;
	public SerializablePost post;

}
