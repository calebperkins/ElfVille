package elfville.server.model;

import java.util.HashSet;
import java.util.Set;

import elfville.protocol.models.SerializablePost;

/*
 * Post Model
 */
public class Post extends Model {
	private Elf elf;
	private String title;
	private String content;
	private Set<Elf> upsockedElves;
	private Set<Elf> downsockedElves;

	//TODO: this should not take a serializable post as an argument.  code must be refactored
	public Post(SerializablePost postRequest, Elf elf) {
		super();
		// elf= postRequest.username;
		title = postRequest.title;
		content = postRequest.content;
		this.elf = elf;
		upsockedElves = new HashSet<Elf>();
		downsockedElves = new HashSet<Elf>();
	}

	public SerializablePost getSerializablePost() {
		SerializablePost sPost = new SerializablePost();
		sPost.title = title;
		sPost.content = content;
		sPost.createdAt = getCreatedAt();
		sPost.upvotes = getNumUpsock();
		sPost.downvotes = getNumDownsock();
		sPost.username = elf.getElfName();
		sPost.elfID = elf.getEncryptedModelID();
		sPost.modelID = getEncryptedModelID();
		return sPost;
	}

	public void delete() {
		database.postDB.delete(this);
	}

	public boolean upsock(Elf upsockingElf) {
		if (!upsockedElves.contains(upsockingElf)) {
			upsockedElves.add(upsockingElf);
			return true;
		}
		return false;
	}

	// Each post cannot have < 0 socks.
	public boolean downsock(Elf downsockingElf) {
		if (!downsockedElves.contains(downsockingElf) && getNumSock() > 0) {
			downsockedElves.add(downsockingElf);
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
		return elf;
	}

	public void setElf(Elf elf) {
		this.elf = elf;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
