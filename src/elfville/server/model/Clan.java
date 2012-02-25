package elfville.server.model;

import java.util.ArrayList;
import java.util.List;

import elfville.protocol.SerializableClan;

/*
 * Clan Model.
 */
public class Clan extends Model {

	private String name;
	private String description;
	private List<Post> posts;

	public Clan(String name, String description) {
		super();
		this.name = name;
		this.description = description;
		this.posts = new ArrayList<Post>();
	}

	public Clan() {
		super();
	}

	// make a serializable clan object out of this clan
	public SerializableClan getSerializableClan() {
		SerializableClan sClan = new SerializableClan();
		sClan.clanName = name;
		sClan.clanDescription = description;
		sClan.numSocks = getNumSock();
		for (Post post : posts) {
			sClan.posts.add(post.getSerializablePost());
		}
		sClan.leader = getLeader().getSerializableElf();
		for (Elf elf : getMembers()) {
			sClan.members.add(elf.getSerializableElf());
		}
		return sClan;
	}

	// returns a list of elves who are either member or leader of the clan
	public List<Elf> getMembers() {
		return database.clanElfDB.getElvesForClan(this);
	}

	public List<Elf> getApplicants() {
		return database.clanElfDB.getApplicantsForClan(this);
	}

	/* The number of socks owned by all clan members combined */
	public int getNumSock() {
		int numSock = 0;
		for (Elf elf : getMembers()) {
			numSock += elf.getNumSocks();
		}
		return numSock;
	}

	public Elf getLeader() {
		return database.clanElfDB.getClanLeader(this);
	}

	public void setLeader(Elf elf) {
		// TODO: what if there is a former relationship between this elf and
		// this clan?
		ClanElf clanElf = new ClanElf(this, elf,
				Model.ClanElfRelationship.LEADER);
		database.clanElfDB.insert(clanElf);
	}

	public void applyClan(Elf elf) {
		// TODO: what if there is a former relationship between this elf and
		// this clan?
		if (database.clanElfDB.getApplicantsForClan(this).contains(elf))
			return;
		if (database.clanElfDB.getElvesForClan(this).contains(elf))
			return;
		ClanElf clanElf = new ClanElf(this, elf,
				Model.ClanElfRelationship.APPLICANT);
		database.clanElfDB.insert(clanElf);
	}

	public void joinClan(Elf elf) {
		// TODO: what if there is a former relationship between this elf and
		// this clan?
		
		ClanElf clanElf = new ClanElf(this, elf,
				Model.ClanElfRelationship.MEMBER);
		database.clanElfDB.insert(clanElf);
	}

	// the database takes care of cascading delete
	public void deleteClan() {
		database.clanDB.delete(this);
	}

	// The clan leader cannot do this operation
	public void leaveClan(Elf elf) {
		// delete all posts from this elf in this clan
		for (Post post : posts) {
			if (post.getElf() == elf) {
				post.delete();
			}
		}
		database.clanElfDB.deleteElf(elf);
	}

	public boolean isLeader(Elf elf) {
		if (elf == getLeader()) {
			return true;
		}
		return false;
	}

	// also true if the elf is the leader
	public boolean isMember(Elf elf) {
		List<Elf> elves = getMembers();
		if (elves.contains(elf)) {
			return true;
		}
		return false;
	}

	public boolean isApplicant(Elf elf) {
		List<Elf> elves = getApplicants();
		if (elves.contains(elf)) {
			return true;
		}
		return false;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void createPost(Post post) {
		posts.add(post);
	}

	public void deletePost(Post post) {
		database.postDB.delete(post);
	}

	// auto generated getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
