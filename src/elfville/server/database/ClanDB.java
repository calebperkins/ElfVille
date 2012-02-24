package elfville.server.database;

import java.util.ArrayList;
import java.util.List;

import elfville.server.SecurityUtils;
import elfville.server.model.*;

public class ClanDB extends DB {

	private List<Clan> clans;

	public ClanDB() {
		clans = new ArrayList<Clan>();
	}

	public Clan findClanByModelID(int modelID) {
		for (Clan clan : clans) {
			if (clan.getModelID() == modelID) {
				return clan;
			}
		}
		return null;
	}
	
	public Clan findClanByEncryptedModelID(String encID) {
		int modelID = SecurityUtils.decryptStringToInt(encID);
		return findClanByModelID(modelID);
	}
	
	public void insert(Clan clan) {
		clans.add(clan);
	}
	
	public void delete(Clan clan) {
		// delete all posts of the clan
		for (Post post : clan.getPosts()) {
			post.delete();
		}
		// delete all ClanElfRelationships of this clan 
		for (ClanElf clanElf : database.clanElfDB.getClanElves()) {
			if (clanElf.getClan() == clan) {
				database.clanElfDB.delete(clanElf);
			}
		}
	}
	
}
