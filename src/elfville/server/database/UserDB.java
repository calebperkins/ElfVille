package elfville.server.database;

import java.util.ArrayList;
import java.util.List;

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
}
