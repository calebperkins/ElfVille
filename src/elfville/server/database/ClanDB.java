package elfville.server.database;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import elfville.server.SecurityUtils;
import elfville.server.model.*;

public class ClanDB extends DB implements Iterable<Clan> {
	private final ConcurrentHashMap<Integer, Clan> idMap = new ConcurrentHashMap<Integer, Clan>();;
	private final ConcurrentSkipListMap<String, Clan> nameMap = new ConcurrentSkipListMap<String, Clan>();;

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

	/**
	 * Returns clans sorted by name.
	 */
	@Override
	public Iterator<Clan> iterator() {
		return nameMap.values().iterator();
	}

}
