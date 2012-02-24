package elfville.server.model;

import java.util.Date;
import java.util.List;

import elfville.protocol.*;
import elfville.server.SecurityUtils;

/*
 * Post Model
 */
public class Post extends Model{
	private Elf elf;
	private String title;
	private String content;
	private List<Elf> upsockedElves;
	private List<Elf> downsockedElves;
	
	public Post(SerializablePost postRequest){
		super();
		// elf= postRequest.username;
		// title= postRequest.title;
		content= postRequest.content;
	}

	public SerializablePost getSerializablePost() {
		SerializablePost sPost = new SerializablePost();
		sPost.title = title;
		sPost.content = content;
		sPost.createdAt = getCreatedAt();
		sPost.upvotes = getNumUpsock();
		sPost.downvotes = getNumDownsock();
		sPost.username = elf.getUserName();
		sPost.elfID = elf.getEncryptedModelID();
		sPost.modelID = getEncryptedModelID();
		return sPost;
	}
	
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
