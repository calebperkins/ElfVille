package elfville.server.model;

import java.util.List;

/*
 * Elf Model.
 */
public class Elf extends Model{
	
	private String name;
	private String description;

	public List<Post> getPosts() {
		return database.postDB.findCentralPostsByElf(this);
	}
	public List<Clan> getClans() {
		return database.clanElfDB.getClansForElf(this);
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
