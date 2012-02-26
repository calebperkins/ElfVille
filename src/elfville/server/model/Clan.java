package elfville.server.model;

import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import elfville.protocol.models.SerializableClan;

/*
 * Clan Model.
 */
public class Clan extends Model {

	private String name;
	private String description;
	private ConcurrentHashMap<Integer, Post> posts;
	
	private Elf leader;
	private ConcurrentHashMap<Integer, Elf> applicants;
	private ConcurrentHashMap<Integer, Elf> members;
	// private List<Post> posts;

	public Clan(String name, String description) {
		super();
		this.name = name;
		this.description = description;
		posts = new ConcurrentHashMap<Integer, Post>();
		members = new ConcurrentHashMap<Integer, Elf>();
		applicants = new ConcurrentHashMap<Integer, Elf>();
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
		for (ConcurrentHashMap.Entry<Integer, Post> post : posts.entrySet()) {
			sClan.posts.add(post.getValue().getSerializablePost());
		}
		sClan.leader = getLeader().getSerializableElf();
		for (ConcurrentHashMap.Entry<Integer, Elf> member : members.entrySet()) {
			sClan.members.add(member.getValue().getSerializableElf());
		}
		return sClan;
	}

	// returns a list of elves who are either member or leader of the clan
	public ConcurrentHashMap<Integer, Elf> getMembers() {
		return members;
	}

	public ConcurrentHashMap<Integer, Elf> getApplicants() {
		return applicants;
	}

	/* The number of socks owned by all clan members combined */
	public int getNumSock() {
		int numSock = 0;
		for (ConcurrentHashMap.Entry<Integer, Elf> member : members.entrySet()) {
			numSock += member.getValue().getNumSocks();
		}
		return numSock;
	}

	public Elf getLeader() {
		return leader;
	}

	public void setLeader(Elf elf) {
		if (leader != null) {
			
		}
		leader = elf;
	}

	public void applyClan(Elf elf) {
		// TODO: what if there is a former relationship between this elf and
		// this clan?
		/*
		if (database.clanElfDB.getApplicantsForClan(this).contains(elf))
			return;
		if (database.clanElfDB.getElvesForClan(this).contains(elf))
			return;
		ClanElf clanElf = new ClanElf(this, elf,
				Model.ClanElfRelationship.APPLICANT);
		database.clanElfDB.insert(clanElf);
		*/
	}

	public void joinClan(Elf elf) {
		// TODO: what if there is a former relationship between this elf and
		// this clan?
		/*
		ClanElf clanElf = new ClanElf(this, elf,
				Model.ClanElfRelationship.MEMBER);
		database.clanElfDB.insert(clanElf);
		*/
	}

	// the database takes care of cascading delete
	public void deleteClan() {
		database.clanDB.delete(this);
	}

	// The clan leader cannot do this operation
	public void leaveClan(Elf elf) {
		// delete all posts from this elf in this clan
		/*
		for (Post post : posts) {
			if (post.getElf() == elf) {
				post.delete();
			}
		}
		database.clanElfDB.deleteElf(elf);
		*/
	}

	public boolean isLeader(Elf elf) {
		if (elf == getLeader()) {
			return true;
		}
		return false;
	}

	// also true if the elf is the leader
	public boolean isMember(Elf elf) {
		return members.contains(elf.modelID);
	}

	public boolean isApplicant(Elf elf) {
		return applicants.contains(elf.modelID);
	}

	public void createPost(Post post) {
		posts.put(post.modelID, post);
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
