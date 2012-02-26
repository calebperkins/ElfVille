package elfville.server.model;

public class ClanElf extends Model {

	private Clan clan;
	private Elf elf;

	private ClanElfRelationship relationship;

	public ClanElf() {
	}

	public ClanElf(Clan clan, Elf elf, ClanElfRelationship relationship) {
		this.clan = clan;
		this.elf = elf;
		this.relationship = relationship;
	}

	public Clan getClan() {
		return clan;
	}

	public void setClan(Clan clan) {
		this.clan = clan;
	}

	public Elf getElf() {
		return elf;
	}

	public void setElf(Elf elf) {
		this.elf = elf;
	}

	public ClanElfRelationship getRelationship() {
		return relationship;
	}

	public void setRelationship(ClanElfRelationship relationship) {
		this.relationship = relationship;
	}
}
