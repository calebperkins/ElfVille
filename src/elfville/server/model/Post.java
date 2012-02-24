package elfville.server.model;

import java.util.Date;

/*
 * Post Model
 */
public class Post extends Model{
	private Elf elf;
	private String title;
	private String content;
	private Clan clan;  // left null for central board
	private int upvotes;
	private int downvotes;
	
	public void delete() {
		database.postDB.delete(this);
	}

	/* auto generated getters and setters */

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

	public Date getUpdatedAt() {
		return (Date) updated_at.clone();
	}

	public Date getCreatedAt() {
		return (Date) created_at.clone();
	}

	public int getUpvotes() {
		return upvotes;
	}
	
	public void incrUpvotes(){
		upvotes++;
	}
	
	public int getDownvotes() {
		return downvotes;
	}
	
	public void incrDownvotes(){
		downvotes++;
	}
}
