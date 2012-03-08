package elfville.server.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import elfville.protocol.models.SerializableClan;
import elfville.protocol.models.SerializableElf;
import elfville.server.SecurityUtils;

/*
 * Clan Model.
 */
public class Clan extends Model implements Comparable<Clan> {
	private static final long serialVersionUID = -696380887203611286L;
	private final String name;
	private final String description;
	private final List<Post> posts;

	private final Elf leader;
	private final SortedSet<Elf> applicants = Collections.synchronizedSortedSet(new TreeSet<Elf>());
	private final SortedSet<Elf> members = Collections.synchronizedSortedSet(new TreeSet<Elf>());;

	// private List<Post> posts;

	public Clan(String name, String description, Elf leader) {
		super();
		this.name = name;
		this.description = description;
		posts = Collections.synchronizedList(new ArrayList<Post>());
		this.leader = leader;
		members.add(leader);
	}

	// make a serializable clan object out of this clan
	public SerializableClan toSerializableClan() {
		SerializableClan sClan = new SerializableClan();
		sClan.clanName = getName();
		sClan.clanDescription = getDescription();
		sClan.numSocks = getNumSock();
		sClan.modelID = getEncryptedModelID();
		sClan.applicants = getApplicants();
		sClan.members = getMembers();
		sClan.leader = getLeader().toSerializableElf();
		return sClan;
	}

	public List<Post> getPosts() {
		Collections.sort(posts);
		return posts;
	}
	
	public List<SerializableElf> getApplicants() {
		List<SerializableElf> applicantList = new ArrayList<SerializableElf>();
		for (Elf e : applicants) {
			applicantList.add(e.toSerializableElf());
		}
		return applicantList;
	}

	public Set<SerializableElf> getMembers() {
		Set<SerializableElf> memberList = new HashSet<SerializableElf>();
		for (Elf member : members) {
			memberList.add(member.toSerializableElf());
		}
		return memberList;
	}
	
	/* The number of socks owned by all clan members combined */
	public int getNumSock() {
		int numSock = 0;
		for (Elf e : members) {
			numSock += e.getNumSocks();
		}
		return numSock;
	}

	public Elf getLeader() {
		return leader;
	}
	
	// A stranger becomes an applicant
	public void apply(Elf elf) {
		if (applicants.contains(elf)
				|| members.contains(elf)) {
			return;
		}
			applicants.add(elf);
	}

	// An applicant becomes a member
	public void join(Elf elf) {
		if (applicants.contains(elf)
				&& !members.contains(elf)) {
			members.add(elf);
			applicants.remove(elf);
		}
	}

	// the database takes care of cascading delete
	public void delete() {
		database.clanDB.delete(this);
	}

	// The clan leader cannot do this operation
	public void leaveClan(Elf elf) {
		if (isLeader(elf)) {
			return;
		}
		for (Post p : posts) {
			if (p.getElf().equals(elf)) {
				posts.remove(p);
			}
		}
		members.remove(elf);
	}

	public boolean isLeader(Elf elf) {
		return elf.equals(leader);
	}

	// also true if the elf is the leader
	public boolean isMember(Elf elf) {
		return members.contains(elf);
	}

	public boolean isApplicant(Elf elf) {
		//TODO this function returns the wrong thing.
		// this is why I can't accept applicants (because they
		// appear to not be applicants) and it is why an applicant
		// doesn't appear as an applicant to the client.
		return applicants.contains(elf);
	}

	public Post getPostFromEncrpytedModelID(String encryptedModelID) {
		int id = SecurityUtils.decryptStringToInt(encryptedModelID);
		for (Post p : posts) {
			if (p.modelID == id)
				return p;
		}
		return null;
	}

	public void createPost(Post post) {
		posts.add(post);
	}

	public void deletePost(Post post) {
		posts.remove(post);
	}

	public void deletePost(String encryptedModelID) {
		deletePost(getPostFromEncrpytedModelID(encryptedModelID));
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public boolean save() {
		// TODO add validations
		if (database.clanDB.findByModelID(modelID) == null) {
			database.clanDB.insert(this);
		}
		return true;
	}

	public void deny(Elf elf) {
		if (applicants.contains(elf)
				&& !members.contains(elf)) {
			applicants.remove(elf);
		}
	}
	
	public static Clan get(String name) {
		return database.clanDB.findByName(name);
	}

	@Override
	public int compareTo(Clan c) {
		return name.compareTo(c.name);
	}
}
