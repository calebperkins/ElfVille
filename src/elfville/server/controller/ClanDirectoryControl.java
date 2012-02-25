package elfville.server.controller;

import java.util.List;

import elfville.protocol.CentralBoardRequest;
import elfville.protocol.CentralBoardResponse;
import elfville.protocol.ClanListingRequest;
import elfville.protocol.ClanListingResponse;
import elfville.protocol.CreateClanRequest;
import elfville.protocol.CreateClanResponse;
import elfville.protocol.PostCentralBoardRequest;
import elfville.protocol.PostCentralBoardResponse;
import elfville.protocol.Response.Status;
import elfville.protocol.SerializableClan;
import elfville.server.model.Clan;
import elfville.server.model.Elf;
import elfville.server.model.Post;
import elfville.server.SecurityUtils;

public class ClanDirectoryControl extends Controller {
	public static ClanListingResponse getClanListing(ClanListingRequest inM, int userNumber) {
		ClanListingResponse outM; 
		List <SerializableClan> clans= null; //TODO: do actual stuff here
		outM = new ClanListingResponse(Status.SUCCESS, "ok", clans);
		
		return outM;
	}
	
	public static CreateClanResponse createClan(CreateClanRequest createRequest, int userNumber){
		Clan clan= new Clan(createRequest.clan);
		database.clanDB.insert(clan);
		return new CreateClanResponse(Status.SUCCESS);
	}

}
