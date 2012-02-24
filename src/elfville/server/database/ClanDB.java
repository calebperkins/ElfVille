package elfville.server.database;

import java.util.ArrayList;
import java.util.List;

import elfville.server.model.*;

public class ClanDB extends DB {

	private List<Clan> clans;

	public ClanDB() {
		clans = new ArrayList<Clan>();
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
	
	/* auto generated getter and setter functions */
	public List<Clan> getClans() {
		return clans;
	}

	public void setClans(List<Clan> clans) {
		this.clans = clans;
	}
}
