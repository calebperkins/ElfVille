/*
package elfville.server.database;

import java.util.ArrayList;
import java.util.List;

import elfville.server.model.*;

public class NClanElfDB extends DB {

	private List<ClanElf> clanElves;

	public NClanElfDB() {
		clanElves = new ArrayList<ClanElf>();
	}

	public void insert(ClanElf clanElf) {
		clanElves.add(clanElf);
	}

	public void delete(ClanElf clanElf) {
		clanElves.remove(clanElf);
	}

	public void deleteElf(Elf elf) {
		for (ClanElf clanElf : clanElves) {
			if (clanElf.getElf() == elf
					&& clanElf.getRelationship() != Model.ClanElfRelationship.LEADER) {
				// TODO: check if deleting while iterating is possible
				clanElves.remove(clanElf);
			}
		}
	}

	// Get all clans that an elf has joined.
	public List<Clan> getClansForElf(Elf elf) {
		List<Clan> clans = new ArrayList<Clan>();
		for (ClanElf clanElf : clanElves) {
			if (clanElf.getElf() == elf
					&& clanElf.getRelationship() != Model.ClanElfRelationship.APPLICANT) {
				clans.add(clanElf.getClan());
			}
		}
		return clans;
	}

	// Get all elves under a clan, including both members and leader
	public List<Elf> getElvesForClan(Clan clan) {
		List<Elf> elves = new ArrayList<Elf>();
		for (ClanElf clanElf : clanElves) {
			if (clanElf.getClan() == clan
					&& clanElf.getRelationship() != Model.ClanElfRelationship.APPLICANT) {
				elves.add(clanElf.getElf());
			}
		}
		return elves;
	}

	// Get all elves under a clan, including both members and leader
	public List<Elf> getApplicantsForClan(Clan clan) {
		List<Elf> elves = new ArrayList<Elf>();
		for (ClanElf clanElf : clanElves) {
			if (clanElf.getClan() == clan
					&& clanElf.getRelationship() == Model.ClanElfRelationship.APPLICANT) {
				elves.add(clanElf.getElf());
			}
		}
		return elves;
	}

	// Get a clan's leader
	public Elf getClanLeader(Clan clan) {
		for (ClanElf clanElf : clanElves) {
			if (clanElf.getClan() == clan
					&& clanElf.getRelationship() == Model.ClanElfRelationship.LEADER) {
				return clanElf.getElf();
			}
		}
		return null;
	}

	// auto generated getters and setters

	public List<ClanElf> getClanElves() {
		return clanElves;
	}

	public void setClanElves(List<ClanElf> clanElves) {
		this.clanElves = clanElves;
	}

}
*/