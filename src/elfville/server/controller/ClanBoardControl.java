package elfville.server.controller;

import elfville.protocol.ClanListingResponse;
import elfville.protocol.Response.Status;
import elfville.server.SecurityUtils;
import elfville.server.model.Clan;
import elfville.server.model.Elf;

/*
 * Control all requests (POST/GET/DELETE) to Clan Board
 */

public class ClanBoardControl extends Controller{
	/*
	/logic here to make sure that the user is allowed to get posts from this clan board
	Elf elf = database.userDB.findUserByModelID(userNumber).getElf();
	Clan clan= database.clanDB.findClanByModelID(SecurityUtils.decryptStringToInt(inM.clanID));
	if(clan.isMember(elf)){
		outM = new ClanListingResponse(Status.SUCCESS,
				"whatever", clan.getPosts());
	} else {
		outM= new ClanListingResponse(Status.FAILURE, "lol", null);
	}
	*/
}
