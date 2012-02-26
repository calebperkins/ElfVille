package elfville.server.database;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import elfville.server.SecurityUtils;
import elfville.server.model.*;

public class ClanDB extends DB {

	private ConcurrentHashMap<Integer, Clan> idMap;
	private ConcurrentHashMap<String, Clan> nameMap;

	public List<Clan> getClans() {
		return new ArrayList<Clan>(nameMap.values());
	}

	public ClanDB() {
		idMap = new ConcurrentHashMap<Integer, Clan>();
		nameMap = new ConcurrentHashMap<String, Clan>();
	}

	public Clan findByModelID(int modelID) {
		return idMap.get(modelID);
	}

	public Clan findByEncryptedModelID(String encID) {
		int modelID = SecurityUtils.decryptStringToInt(encID);
		return findByModelID(modelID);
	}

	public void insert(Clan clan) {
		idMap.put(clan.getModelID(), clan);
		nameMap.put(clan.getName(), clan);
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

	public Clan findByName(String clanName) {
		return nameMap.get(clanName);
	}

}
