package elfville.server.controller;

import elfville.protocol.CentralBoardRequest;
import elfville.protocol.CentralBoardResponse;
import elfville.protocol.ClanListingRequest;
import elfville.protocol.ClanListingResponse;
import elfville.protocol.CreateClanRequest;
import elfville.protocol.CreateClanResponse;
import elfville.protocol.PostCentralBoardRequest;
import elfville.protocol.PostCentralBoardResponse;
import elfville.protocol.Response.Status;
import elfville.server.model.Post;

public class ClanListingControl extends Controller {
	public static ClanListingResponse getClanListing(ClanListingRequest inM, int userNumber) {
		//logic here to make sure that the user is allowed to get posts from this clan board
		Elf elf = database.userDB.findUserByModelID(userNumber).getElf();
		
		ClanListingResponse outM = new ClanListingResponse(Status.SUCCESS,
				"whatever", database.postDB.getCentralPosts());
		return outM;
	}
	
	public static CreateClanResponse createClan(CreateClanRequest createRequest, int userNumber){
		Clan clan= new Clan(createRequest.post);
		database.postDB.insert(post);
		return new CreateClanResponse(Status.SUCCESS);
	}

}
