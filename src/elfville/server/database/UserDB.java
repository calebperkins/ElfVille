package elfville.server.database;

import java.util.concurrent.ConcurrentHashMap;

import elfville.server.SecurityUtils;
import elfville.server.model.User;

public class UserDB extends DB {

	private ConcurrentHashMap<Integer, User> id_map;
	private ConcurrentHashMap<String, User> username_map;

	public UserDB() {
		id_map = new ConcurrentHashMap<Integer, User>();
		username_map = new ConcurrentHashMap<String, User>();
	}

	public User findUserByModelID(int modelID) {
		return id_map.get(modelID);
	}

	public User findUserByEncryptedModelID(String encID) {
		int modelID = SecurityUtils.decryptStringToInt(encID);
		return findUserByModelID(modelID);
	}

	public User findUserByUsername(String username) {
		return username_map.get(username);
	}

	public void insert(User user) {
		id_map.put(user.getModelID(), user);
		username_map.put(user.getUsername(), user);
	}
}
