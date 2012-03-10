package elfville.server.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import elfville.protocol.models.SerializablePost;

/*
 * Post Model
 */
public class Post extends Model implements Comparable<Post> {
	private static final long serialVersionUID = 6422767335685038776L;
	private final int elfID;
	private final String title;
	private final String content;

	private final Set<Integer> upsockedElves = Collections.synchronizedSet(new HashSet<Integer>());
	private final Set<Integer> downsockedElves = Collections.synchronizedSet(new HashSet<Integer>());

	//TODO: this should not take a serializable post as an argument.  code must be refactored
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
	
	public SerializablePost toSerializablePost(Elf elf){
		SerializablePost sPost= toSerializablePost();
		Integer id = elf.modelID;
		sPost.iVoted= this.downsockedElves.contains(id) || this.upsock(elf);
		sPost.myPost= elf.modelID == elfID;
		return sPost;
	}

	public void delete() {
		database.postDB.delete(modelID);
	}

	public boolean upsock(Elf upsockingElf) {
		//each elf can only upsock OR downsock a single post
		Integer id = upsockingElf.modelID;
		if (!upsockedElves.contains(id) && !downsockedElves.contains(id)) {
			upsockedElves.add(id);
			return true;
		}
		return false;
	}

	// Each post cannot have < 0 socks.
	public boolean downsock(Elf downsockingElf) {
		Integer id = downsockingElf.modelID;
		//each elf can only upsock OR downsock a single post
		if (!downsockedElves.contains(id) && !upsockedElves.contains(id)) {
			downsockedElves.add(id);
			return true;
		}
		return false;
	}

	public int getNumUpsock() {
		return upsockedElves.size();
	}

	public int getNumDownsock() {
		return downsockedElves.size();
	}

	public int getNumSock() {
		return upsockedElves.size() - downsockedElves.size();
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
	public boolean save() {
		// TODO Auto-generated method stub
		return false;
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
