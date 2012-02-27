package elfville.server.model;

import java.util.List;

import elfville.protocol.models.SerializableElf;
import elfville.server.Database;

/*
 * Elf Model.
 */
public class Elf extends Model {
	private static final long serialVersionUID = 4948830835289818367L;
	private String elfName;
	private String description;

	public List<Post> getPosts() {
		return database.postDB.findCentralPostsByElf(this);
	}

	public List<Clan> getClans() {
		// TODO: haven't been implemented yet
		return null;
	}

	public SerializableElf getSerializableElf() {
		SerializableElf elf = new SerializableElf();
		elf.elfName = elfName;
		elf.modelID = getEncryptedModelID();
		elf.numSocks = getNumSocks();
		return elf;
	}

	// the number of socks in all posts owned by the elf combined
	public int getNumSocks() {
		int numPost = 0;
		for (Post post : getPosts()) {
			numPost += post.getNumSock();
		}
		return numPost;
	}

	/* auto generated getter and setter functions */
	public String getElfName() {
		String n;
		synchronized (this) {
			n = elfName;
		}
		return n;
	}

	public void setElfName(String elfName) {
		synchronized (this) {
			this.elfName = elfName;
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
		synchronized (this) {
			this.description = description;
		}
	}

	@Override
	public boolean save() {
		// TODO add validations
		if (Database.DB.elfDB.findByID(modelID) == null) {
			Database.DB.elfDB.insert(this);
		}
		return true;
	}
}
