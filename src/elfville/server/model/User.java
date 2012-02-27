package elfville.server.model;

/*
 * User Model.
 * Contains authentication information.
 */
public class User extends Model {
	private static final long serialVersionUID = -5955064170275055506L;
	private String username;
	private String password;
	private Elf elf;

	// auto generated getters and setters
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

	public synchronized Elf getElf() {
		return elf;
	}

	public synchronized void setElf(Elf elf) {
		this.elf = elf;
	}
	
	@Override
	public boolean save() {
		// TODO Auto-generated method stub
		return false;
	}

}
