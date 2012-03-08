package elfville.server.model;

import java.util.List;

import elfville.protocol.models.SerializableElf;

/*
 * Elf Model.
 */
public class Elf extends Model {
	private static final long serialVersionUID = 4948830835289818367L;
	private String name;
	private String description;
	
	public Elf(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public List<Post> getPosts() {
		return database.postDB.findCentralPostsByElf(this);
	}

	public SerializableElf toSerializableElf() {
		SerializableElf elf = new SerializableElf();
		elf.elfName = name;
		elf.modelID = getEncryptedModelID();
		elf.numSocks = getNumSocks();
		elf.description = description;
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

	public String getName() {
		return name;
	}

	public synchronized void setName(String elfName) {
		this.name = elfName;
	}

	public String getDescription() {
		return description;
	}

	public synchronized void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean save() {
		if (isValid()) {
			if (get(modelID) == null)
				database.elfDB.insert(this);
			return true;
		}
		return false;
	}
	
	private boolean isValid() {
		return !name.isEmpty() && !description.isEmpty();
	}
	
	public static Elf get(int id) {
		return database.elfDB.findByID(id);
	}
}
