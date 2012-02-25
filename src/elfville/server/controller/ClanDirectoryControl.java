package elfville.server.controller;

import java.util.List;

import elfville.protocol.ClanListingRequest;
import elfville.protocol.ClanListingResponse;
import elfville.protocol.CreateClanRequest;
import elfville.protocol.CreateClanResponse;
import elfville.protocol.Response.Status;
import elfville.protocol.SerializableClan;
import elfville.server.model.Clan;
import elfville.server.model.Elf;

public class ClanDirectoryControl extends Controller {
	public static ClanListingResponse getClanListing(ClanListingRequest inM, int userNumber) {
		ClanListingResponse outM; 
		List <SerializableClan> clans= null; //TODO: do actual stuff here
		outM = new ClanListingResponse(Status.SUCCESS, "ok", clans);
		
		return outM;
	}
	
	public static CreateClanResponse createClan(CreateClanRequest createRequest, int userNumber){
		CreateClanResponse resp= new CreateClanResponse(Status.FAILURE);
		Elf leader;
		
		if(createRequest.clan == null){
			return resp;
		}
		if(database.clanDB.findClanByName(createRequest.clan.clanName) == null){
			return resp;
		}
		//createRequest
		//if(){
			
		
		Clan clan= new Clan();
		
		resp.status = Status.SUCCESS;
		return resp;
	}

}
