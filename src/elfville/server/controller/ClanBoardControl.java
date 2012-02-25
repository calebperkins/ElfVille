package elfville.server.controller;

import elfville.protocol.*;
import elfville.protocol.Response.Status;
import elfville.server.SecurityUtils;
import elfville.server.model.*;

/*
 * Control all requests (POST/GET/DELETE) to Clan Board
 */

public class ClanBoardControl extends Controller{
	
	
	public static ClanBoardResponse getClanBoard(ClanBoardRequest inM, Integer currUserModelID) {
		// Check if the user's elf is a clan member
		User user = database.userDB.findUserByModelID(currUserModelID);
		if (user == null) {
			return new ClanBoardResponse(Status.FAILURE);
		}
		Elf elf = user.getElf();
		
		Clan clan= database.clanDB.findClanByEncryptedModelID(inM.getClanModelID());
		if (clan == null) {
			return new ClanBoardResponse(Status.FAILURE);
		}
		
		ClanBoardResponse outM = new ClanBoardResponse(Status.SUCCESS);
		
		// Basic board info that is visible to all elves
		outM.clan.clanName = clan.getName();
		outM.clan.clanDescription = clan.getDescription();
		
		boolean isMember = clan.isMember(elf);
		
		return outM;
	}
	
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
