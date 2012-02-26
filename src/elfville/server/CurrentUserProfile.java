package elfville.server;

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

}
