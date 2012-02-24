package elfville.protocol;

import elfville.protocol.Response.Status;

public class PostClanBoardResponse extends Response {
	private static final long serialVersionUID = 1L;
	
	public PostClanBoardResponse(Status s){
		super();
		status= s;
	}
}
