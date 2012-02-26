package elfville.server.controller;

import elfville.protocol.*;
import elfville.protocol.Response.Status;
import elfville.server.CurrentUserProfile;
import elfville.server.model.*;

/*
 * Control all requests (POST/GET/DELETE) to Clan Board
 */

public class ClanBoardControl extends Controller {

	public static ClanBoardResponse getClanBoard(ClanBoardRequest inM,
			CurrentUserProfile currentUser) {
		// Check if the user's elf is a clan member
		User user = database.userDB.findUserByModelID(currentUser
				.getCurrentUserId());
		if (user == null) {
			return new ClanBoardResponse(Status.FAILURE);
		}
		Elf elf = user.getElf();

		Clan clan = database.clanDB.findClanByEncryptedModelID(inM
				.getClanModelID());
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

	public static Response modifyClan(ModifyClanRequest req,
			CurrentUserProfile currentUser) {
		Response resp = new Response(Status.FAILURE);

		// make sure that the current user still exists!
		User user = database.userDB.findUserByModelID(currentUser
				.getCurrentUserId());
		if (user == null) {
			return resp;
		}
		Elf elf = user.getElf();

		// check to make sure that we were sent a clan
		if (req.clan == null) {
			return resp;
		}

		Clan clan = database.clanDB.findClanByEncryptedModelID(req.clan.clanID);

		// check to see that the requested clan actually exists
		if (clan == null) {
			return resp;
		}

		switch (req.requestType) {
		case DELETE:
			// make sure that this elf is actually the leader of the clan
			if (!clan.isLeader(elf)) {
				return resp;
			}
			clan.deleteClan();
			break;

		case JOIN:
			// see if this elf has not already applied or is in the clan
			if (!clan.isApplicant(elf) || !clan.isLeader(elf)
					|| !clan.isMember(elf)) {
				return resp;
			}
			clan.applyClan(elf);
			break;

		case LEAVE:
			// see if the elf is in the clan and is not the leader
			if (clan.isLeader(elf) || !clan.isMember(elf)) {
				return resp;
			}
			clan.leaveClan(elf);
			break;
		}

		resp.status = Status.SUCCESS;

		return resp;
	}
}
