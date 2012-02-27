package elfville.server.model;

/*
 * User Model.
 * Contains authentication information.
 */
public class User extends Model {

	private String username;
	private String password;
	private Elf elf;

	// auto generated getters and setters
	public String getUsername() {
		String n;
		synchronized(this) {
			n = username;
		}
		return n;
	}

	public void setUsername(String username) {
		synchronized (this) {
			this.username = username;
		}
	}

	public String getPassword() {
		String p;
		synchronized (this) {
			p = password;
		}
		return p;
	}

	public void setPassword(String password) {
		synchronized (this) {
			this.password = password;
		}
	}

	public Elf getElf() {
		Elf e;
		synchronized (this) {
			e = elf;
		}
		return e;
	}

	public void setElf(Elf elf) {
		synchronized (this) {
			this.elf = elf;
		}
	}

}
