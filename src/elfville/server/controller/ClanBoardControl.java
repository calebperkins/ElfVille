package elfville.server.controller;

import elfville.protocol.*;
import elfville.protocol.Response.Status;
import elfville.server.CurrentUserProfile;
import elfville.server.model.*;

/*
 * Control all requests (POST/GET/DELETE) to Clan Board
 */

public class ClanBoardControl extends Controller{
	
	
	public static ClanBoardResponse getClanBoard(ClanBoardRequest inM, CurrentUserProfile currentUser) {
		// Check if the user's elf is a clan member
		User user = database.userDB.findUserByModelID(currentUser.getCurrentUserId());
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
		outM.clan = clan.getSerializableClan();
		
		boolean isMember = clan.isMember(elf);
		if (isMember) {
			if (clan.isLeader(elf)) {
				outM.elfStatus = ClanBoardResponse.ElfClanRelationship.LEADER;
			} else {
				outM.elfStatus = ClanBoardResponse.ElfClanRelationship.MEMBER;
			}
		} else {
			if (clan.isApplicant(elf)) {
				outM.elfStatus = ClanBoardResponse.ElfClanRelationship.APPLICANT;
			} else {
				outM.elfStatus = ClanBoardResponse.ElfClanRelationship.OUTSIDER;
			}
			// Posts are only visible to clan members
			outM.clan.posts = null;
		}
		
		return outM;
	}
}
