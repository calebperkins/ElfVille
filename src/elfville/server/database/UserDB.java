package elfville.server.database;

import java.util.ArrayList;
import java.util.List;

import elfville.server.SecurityUtils;
import elfville.server.model.Clan;
import elfville.server.model.User;

public class UserDB extends DB {

	private List<User> users;
	
	public UserDB() {
		users = new ArrayList<User>();
	}
	
	public User findUserByModelID(int modelID) {
		for (User user : users) {
			if (user.getModelID() == modelID) {
				return user;
			}
		}
		return null;
	}
	
	public User findUserByEncryptedModelID(String encID) {
		int modelID = SecurityUtils.decryptStringToInt(encID);
		return findUserByModelID(modelID);
	}
	
	public User findUserByUsername(String username) {
		System.out.println("username being found: " + username);
		for (User user : users) {
			System.out.println("Looping username: " + user.getUsername());
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	public void insert(User user) {
		users.add(user);
	}
}
