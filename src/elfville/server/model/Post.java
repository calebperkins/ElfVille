package elfville.server.model;

import java.util.Date;
import java.util.List;

/*
 * Post Model
 */
public class Post extends Model{
	private Elf elf;
	private String title;
	private String content;
	private List<Elf> upsockedElves;
	private List<Elf> downsockedElves;

	public void delete() {
		database.postDB.delete(this);
	}

	public void upsock(Elf upsockingElf) {
		if (!upsockedElves.contains(upsockingElf)) {
			upsockedElves.add(upsockingElf);
		}
	}
	
	// Each post cannot have < 0 socks. 
	public void downsock(Elf downsockingElf) {
		if (!downsockedElves.contains(downsockingElf) && getNumSock() > 0) {
			downsockedElves.add(downsockingElf);
		}		
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

	public Date getUpdatedAt() {
		return (Date) updated_at.clone();
	}

	public Date getCreatedAt() {
		return (Date) created_at.clone();
	}
}
