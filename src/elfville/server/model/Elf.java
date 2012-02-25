package elfville.server.model;

import java.util.List;

import elfville.protocol.SerializableElf;

/*
 * Elf Model.
 */
public class Elf extends Model{
	
	private String elfName;
	private String description;

	public List<Post> getPosts() {
		return database.postDB.findCentralPostsByElf(this);
	}
	public List<Clan> getClans() {
		return database.clanElfDB.getClansForElf(this);
	}
	
	public SerializableElf getSerializableElf(){
		SerializableElf elf= new SerializableElf();
		elf.elfName= elfName;
		elf.modelID= getEncryptedModelID();
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
		return elfName;
	}
	public void setElfName(String elfName) {
		this.elfName = elfName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
