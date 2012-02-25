package elfville.server.controller;

import java.util.List;

import elfville.protocol.ClanListingRequest;
import elfville.protocol.ClanListingResponse;
import elfville.protocol.CreateClanRequest;
import elfville.protocol.CreateClanResponse;
import elfville.protocol.Response.Status;
import elfville.protocol.SerializableClan;
import elfville.server.CurrentUserProfile;
import elfville.server.model.Clan;
import elfville.server.model.Elf;
import elfville.server.model.User;

public class ClanDirectoryControl extends Controller {
	public static ClanListingResponse getClanListing(ClanListingRequest inM, CurrentUserProfile currentUser) {
		ClanListingResponse resp= new ClanListingResponse(Status.FAILURE);
		
		User user = database.userDB.findUserByModelID(currentUser.getCurrentUserId());
		if (user == null) {
			return resp;
		}
		
		List <SerializableClan> clans= ControllerUtils.buildBoardList(database.clanDB.getClans());
		
		resp.status= Status.SUCCESS;
		resp.clans= clans;

		return resp;
	}
	
	public static CreateClanResponse createClan(CreateClanRequest createRequest, CurrentUserProfile currentUser){
		CreateClanResponse resp= new CreateClanResponse(Status.FAILURE);
		Elf leader;
		
		User user = database.userDB.findUserByModelID(currentUser.getCurrentUserId());
		if (user == null) {
			return resp;
		}
		
		leader= user.getElf();
		if(leader == null){
			return resp;
		}		
		
		if(createRequest.clan == null){
			return resp;
		}
		
		if(database.clanDB.findClanByName(createRequest.clan.clanName) == null){
			return resp;
		}
		
		//require the clan name to have at least one character
		if(createRequest.clan.clanName == null || createRequest.clan.clanName == ""){
			return resp;
		}
		
		//require the clan description to have at least one character
		if(createRequest.clan.clanDescription == null || createRequest.clan.clanDescription == ""){
			return resp;
		}
		
		Clan clan= new Clan(createRequest.clan.clanName, createRequest.clan.clanDescription);
		clan.setLeader(leader);
		database.clanDB.insert(clan);
		
		resp.status = Status.SUCCESS;
		return resp;
	}

}
