package elfville.server.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import elfville.protocol.models.SerializablePost;
import elfville.server.Database;

/*
 * Post Model
 */
public class Post extends Model implements Comparable<Post> {
	@Override
	public void save() {
		super.save();
		Database.getInstance().postDB.add(this);
	}

	private static final long serialVersionUID = 6422767335685038776L;
	private final int elfID;
	private final String title;
	private final String content;

	private final static Random rand = new Random();

	public int clanID = 0;

	private final Set<Integer> upsocks = Collections
			.synchronizedSet(new HashSet<Integer>());
	private final Set<Integer> downsocks = Collections
			.synchronizedSet(new HashSet<Integer>());

	// TODO: this should not take a serializable post as an argument. code must
	// be refactored
	public Post(SerializablePost postRequest, Elf elf) {
		super();
		title = postRequest.title;
		content = postRequest.content;
		this.elfID = elf.modelID;
	}

	public SerializablePost toSerializablePost() {
		SerializablePost sPost = new SerializablePost();
		sPost.title = getTitle();
		sPost.content = getContent();
		sPost.upvotes = getNumUpsock();
		sPost.downvotes = getNumDownsock();
		sPost.createdAt = getCreatedAt();

		// Add a small random fluctuation in votes so attacker cannot determine
		// who upvoted/downvoted
		sPost.upvotes = Math.max(getNumUpsock() - 1 + rand.nextInt(3), 0);
		sPost.downvotes = Math.max(getNumDownsock() - 1 + rand.nextInt(3), 0);

		sPost.username = getElf().getName();
		sPost.elfModelID = getElf().getEncryptedModelID();
		sPost.modelID = getEncryptedModelID();
		return sPost;
	}

	public SerializablePost toSerializablePost(Elf elf) {
		SerializablePost sPost = toSerializablePost();
		Integer id = elf.modelID;
		sPost.iVoted = this.downsocks.contains(id) || this.upsock(elf);
		sPost.myPost = elf.modelID == elfID;
		return sPost;
	}

	public void delete() {
		Database.getInstance().postDB.remove(modelID);
		Database.getInstance().persist(new Deletion(this));
	}

	public boolean upsock(Elf upsockingElf) {
		// each elf can only upsock OR downsock a single post
		Integer id = upsockingElf.modelID;
		boolean voted = !downsocks.contains(id) && upsocks.add(id);
		if (voted)
			save();
		return voted;
	}

	// Each post cannot have < 0 socks.
	public boolean downsock(Elf downsockingElf) {
		Integer id = downsockingElf.modelID;
		boolean voted = !upsocks.contains(id) && downsocks.add(id);
		if (voted)
			save();
		return voted;
	}

	public int getNumUpsock() {
		return upsocks.size();
	}

	public int getNumDownsock() {
		return downsocks.size();
	}

	public int getNumSock() {
		return upsocks.size() - downsocks.size();
	}

	/* auto generated getters and setters */

	public Elf getElf() {
		return Elf.get(elfID);
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public static Post get(String encID) {
		return Database.getInstance().postDB.findByEncryptedModelID(encID);
	}

	@Override
	public int compareTo(Post other) {
		if (getNumSock() == other.getNumSock())
			return other.modelID - modelID;
		return other.getNumSock() - getNumSock();
	}
}
