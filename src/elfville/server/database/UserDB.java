package elfville.server.database;

import java.util.concurrent.ConcurrentHashMap;

import elfville.server.SecurityUtils;
import elfville.server.model.User;

public class UserDB extends DB {
	private final ConcurrentHashMap<Integer, User> id_map = new ConcurrentHashMap<Integer, User>();
	private final ConcurrentHashMap<String, User> username_map = new ConcurrentHashMap<String, User>();

	public User findUserByModelID(int modelID) {
		return id_map.get(modelID);
	}

	public User findByEncryptedModelID(String encID) {
		int modelID = SecurityUtils.decryptStringToInt(encID);
		return findUserByModelID(modelID);
	}
	
	// used by sign up controller to check
	public User findByUsernamePassword(String username, String password) {
		return username_map.get(username);
	}
	
	public User findByUsername(String username) {
		return username_map.get(username);
	}

	public void insert(User user) {
		id_map.put(user.getModelID(), user);
		username_map.put(user.getUsername(), user);
	}
}
