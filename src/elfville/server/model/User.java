package elfville.server.model;

/*
 * User Model.
 * Contains authentication information.
 */
public class User extends Model {
	private static final long serialVersionUID = -5955064170275055506L;
	private String username;
	private String password;
	private final int elfId;

	public User(Elf e, String uname) {
		super();
		elfId = e.modelID;
		username = uname;
	}

	public synchronized String getUsername() {
		return username;
	}

	public synchronized void setUsername(String username) {
		this.username = username;
	}

	public synchronized String getPassword() {
		return password;
	}

	public synchronized void setPassword(String password) {
		this.password = password;
	}

	public Elf getElf() {
		return Elf.get(elfId);
	}

	public static User get(String username) {
		return database.userDB.findUserByUsername(username);
	}

	@Override
	public void save() {
		super.save();
		database.userDB.insert(this);
	}

}
