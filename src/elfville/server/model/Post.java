package elfville.server.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import elfville.protocol.models.SerializablePost;

/*
 * Post Model
 */
public class Post extends Model implements Comparable<Post> {
	@Override
	public void save() {
		super.save(); 
		database.postDB.insert(this);
	}

	private static final long serialVersionUID = 6422767335685038776L;
	private final int elfID;
	private final String title;
	private final String content;

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
		sPost.createdAt = getCreatedAt();
		sPost.upvotes = getNumUpsock();
		sPost.downvotes = getNumDownsock();
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
		database.postDB.delete(modelID);
		database.persist(new Deletion(this));
	}

	public boolean upsock(Elf upsockingElf) {
		// each elf can only upsock OR downsock a single post
		Integer id = upsockingElf.modelID;
		boolean voted = !downsocks.contains(id) && upsocks.add(id);
		if (voted) save();
		return voted;
	}

	// Each post cannot have < 0 socks.
	public boolean downsock(Elf downsockingElf) {
		Integer id = downsockingElf.modelID;
		boolean voted = !upsocks.contains(id) && downsocks.add(id);
		if (voted) save();
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
		return database.postDB.findByEncryptedModelID(encID);
	}

	@Override
	public int compareTo(Post other) {
		if (getNumSock() > other.getNumSock()) {
			return -1;
		} else if (getNumSock() < other.getNumSock()) {
			return 1;
		} else {
			return new Integer(other.modelID).compareTo(modelID);
		}
	}
}
