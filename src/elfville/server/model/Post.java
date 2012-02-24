package elfville.server.model;

import java.util.Date;

/*
 * Post Model
 */
public class Post extends Model{
	private Elf elf;
	private String title;
	private String content;
	private int numSock;
	private Clan clan;  // left null for central board

	public void delete() {
		database.postDB.delete(this);
	}

	/* auto generated getters and setters */
	public int getNumSock() {
		return numSock;
	}

	public void setNumSock(int numSock) {
		this.numSock = numSock;
	}

	public Elf getElf() {
		return elf;
	}

	public void setElf(Elf elf) {
		this.elf = elf;
		this.updated_at = new Date();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		this.updated_at = new Date();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		this.updated_at = new Date();
	}

	public Clan getClan() {
		return clan;
	}

	public void setClan(Clan clan) {
		this.clan = clan;
	}
	
	
}
