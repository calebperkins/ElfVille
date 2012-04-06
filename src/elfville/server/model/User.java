package elfville.server.model;

/*
 * User Model.
 * Contains authentication information.
 */
public class User extends Model {
	private static final long serialVersionUID = -5955064170275055506L;
	private final String username;
	private String password;
	private final int elfId;
	private long lastLogin;
	private long lastLogout;

	public User(Elf e, String uname) {
		super();
		elfId = e.modelID;
		username = uname;
	}

	public String getUsername() {
		return username;
	}

	public synchronized String getPassword() {
		return password;
	}

	public synchronized void setPassword(String password) {
		this.password = password;
		save();
	}

	public Elf getElf() {
		return Elf.get(elfId);
	}

	@Override
	public void save() {
		super.save();
		database.userDB.insert(this);
	}

	public synchronized boolean laterThanLastLogin(long currTime) {
		return currTime > lastLogin;
	}

	public synchronized void setLastLogin(long lastLogin) {
		this.lastLogin = lastLogin;
	}

	public synchronized boolean laterThanLastLogout(long currTime) {
		return currTime > lastLogout;
	}

	public synchronized void setLastLogout(long lastLogout) {
		this.lastLogout = lastLogout;
	}

}
