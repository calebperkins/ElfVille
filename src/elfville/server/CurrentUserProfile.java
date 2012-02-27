package elfville.server;
import elfville.server.model.*;

public class CurrentUserProfile {
	private int currentUserId;

	public CurrentUserProfile() {
		currentUserId = -1;
	}

	public int getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(int currentUserId) {
		this.currentUserId = currentUserId;
	}
	
	public User getUser() {
		return Database.DB.userDB.findUserByModelID(currentUserId);
	}
	
	public Elf getElf() {
		return getUser().getElf();
	}

}
