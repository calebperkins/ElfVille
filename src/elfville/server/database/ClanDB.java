package elfville.server.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import elfville.server.SecurityUtils;
import elfville.server.model.*;

public class ClanDB extends DB implements Iterable<Clan> {
	private final ConcurrentHashMap<Integer, Clan> idMap = new ConcurrentHashMap<Integer, Clan>();;
	private final ConcurrentHashMap<String, Clan> nameMap = new ConcurrentHashMap<String, Clan>();;

	public boolean contains(Clan clan) {
		return idMap.containsKey(clan.getModelID());
	}

	public Clan findByModelID(int modelID) {
		return idMap.get(modelID);
	}

	public Clan findByEncryptedModelID(String encID) {
		// TODO: security issue here. it is possible to break the server by
		// sending garbage as the encID
		int modelID = SecurityUtils.decryptStringToInt(encID);
		return findByModelID(modelID);
	}

	public void add(Clan clan) {
		// if (!hasModel(clan)) {
		idMap.put(clan.getModelID(), clan);
		nameMap.put(clan.getName(), clan);
		// }
	}

	public void remove(Clan clan) {
		idMap.remove(clan.getModelID());
		nameMap.remove(clan.getName());

		/*
		 * // delete all posts of the clan for (Post post : clan.getPosts()) {
		 * post.delete(); } // delete all ClanElfRelationships of this clan for
		 * (ClanElf clanElf : database.clanElfDB.getClanElves()) { if
		 * (clanElf.getClan() == clan) { database.clanElfDB.delete(clanElf); } }
		 */
	}

	public Clan findByName(String clanName) {
		return nameMap.get(clanName);
	}

	@Override
	public Iterator<Clan> iterator() {
		ArrayList<Clan> clans = new ArrayList<Clan>(nameMap.values());
		Collections.sort(clans);
		return clans.iterator();
	}

}
