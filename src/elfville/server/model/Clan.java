package elfville.server.model;

import java.util.concurrent.ConcurrentHashMap;
import elfville.protocol.models.SerializableClan;
import elfville.server.SecurityUtils;

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
		sClan.clanName = getName();
		sClan.clanDescription = getDescription();
		sClan.numSocks = getNumSock();
		sClan.modelID = SecurityUtils.encryptIntToString(this.getModelID());
		for (ConcurrentHashMap.Entry<Integer, Post> post : posts.entrySet()) {
			sClan.posts.add(post.getValue().getSerializablePost());
		}
		sClan.leader = getLeader().getSerializableElf();
		for (ConcurrentHashMap.Entry<Integer, Elf> member : members.entrySet()) {
			sClan.members.add(member.getValue().getSerializableElf());
		}
		return sClan;
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
		Elf l;
		synchronized(this) {
			l = leader;
		}
		return l;
	}

	public void setLeader(Elf elf) {
		if (leader != null) {
			// TODO: throws some errors!
		}
		synchronized (this) {
			leader = elf;
		}
		members.put(elf.getModelID(), elf);
	}

	// A stranger becomes an applicant
	public void applyClan(Elf elf) {
		if (applicants.contains(elf.getModelID()) || members.contains(elf.getModelID())) {
			return;
		}
		applicants.put(elf.getModelID(), elf);
	}

	// An applicant becomes a member
	public void joinClan(Elf elf) {
		if (applicants.contains(elf.getModelID()) && !members.contains(elf.getModelID())) {
			members.put(elf.getModelID(), elf);
		}
	}

	// the database takes care of cascading delete
	public void deleteClan() {
		database.clanDB.delete(this);
	}

	// The clan leader cannot do this operation
	public void leaveClan(Elf elf) {
		if (elf == getLeader()) {
			return;
		}
		for (ConcurrentHashMap.Entry<Integer, Post> post : posts.entrySet()) {
			if (post.getValue().getElf() == elf) {
				posts.remove(post.getValue().getModelID());
			}
		}
		members.remove(elf.getModelID());
	}

	public boolean isLeader(Elf elf) {
		if (elf == getLeader()) {
			return true;
		}
		return false;
	}

	// also true if the elf is the leader
	public boolean isMember(Elf elf) {
		return members.contains(elf.getModelID());
	}

	public boolean isApplicant(Elf elf) {
		return applicants.contains(elf.getModelID());
	}

	public Post getPostFromEncrpytedModelID (String encryptedModelID) {
		return posts.get(SecurityUtils.decryptStringToInt(encryptedModelID));
	}
	
	public void createPost(Post post) {
		posts.put(post.getModelID(), post);
	}
	
	public boolean hasPost(Post post) {
		return posts.contains(post.getModelID());
	}
	
	public boolean hasPost(String encryptedModelID) {
		return hasPost(getPostFromEncrpytedModelID(encryptedModelID));
	}
	
	public void deletePost(Post post) {
		if (posts.contains(post.getModelID())) {
			posts.remove(post.getModelID());
		}
	}
	
	public void deletePost(String encryptedModelID) {
		deletePost(getPostFromEncrpytedModelID(encryptedModelID));
	}

	// auto generated getters and setters
	public String getName() {
		String n;
		synchronized (this) {
			n = name;
		}
		return n;
	}

	public void setName(String name) {
		synchronized(this) {
			this.name = name;
		}
	}

	public String getDescription() {
		String d;
		synchronized (this) {
			d = description;
		}
		return d;
	}

	public void setDescription(String description) {
		synchronized(this) {
			this.description = description;
		}
	}
}
