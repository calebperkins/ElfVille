package elfville.server.database;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentHashMap;

import elfville.server.SecurityUtils;
import elfville.server.model.User;

public class UserDB extends DB {
	private final ConcurrentHashMap<Integer, User> id_map = new ConcurrentHashMap<Integer, User>();
	private final ConcurrentHashMap<String, User> username_map = new ConcurrentHashMap<String, User>();

	public boolean hasModel(User user) {
		return id_map.containsKey(user.getModelID());
	}
	
	public User findUserByModelID(int modelID) {
		return id_map.get(modelID);
	}

	public User findByEncryptedModelID(String encID) {
		int modelID = SecurityUtils.decryptStringToInt(encID);
		return findUserByModelID(modelID);
	}
	
	// used by sign up controller to check
	public User findByUsernameHashedPassword(String username, String password) {
		User u = username_map.get(username);
		boolean pwdIsRight = false;
		if (u == null) {
			return null;
		}
		try {
			pwdIsRight = SecurityUtils.checkPepperPassword(password, u.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (!pwdIsRight) {
			return null;
		}
		return u;
	}
	
	public User findByUsername(String username) {
		return username_map.get(username);
	}

	public void insert(User user) {
		id_map.put(user.getModelID(), user);
		username_map.put(user.getUsername(), user);
	}
}
