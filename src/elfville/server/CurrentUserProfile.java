package elfville.server;

import javax.crypto.SecretKey;

import elfville.server.model.*;

public class CurrentUserProfile {
	
	private SecretKey key;
	private int nonce;
	
	private int currentUserId;

	public CurrentUserProfile() {
		currentUserId = -1;
	}

	public boolean loggedIn() {
		return currentUserId != -1;
	}

	public boolean loggedOut() {
		return !loggedIn();
	}

	public int getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(int currentUserId) {
		this.currentUserId = currentUserId;
	}

	public User getUser() {
		return Database.getInstance().userDB.findUserByModelID(currentUserId);
	}

	public Elf getElf() {
		return getUser().getElf();
	}
	
	public void setSharedKey(SecretKey key){
		this.key= key;
	}
	
	public SecretKey getSecretKey(){
		return key;
	}
	
	public void setNonce(int nonce){
		this.nonce= nonce;
	}
	
	public int getNonce(){
		return nonce;
	}

}
