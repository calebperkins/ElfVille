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
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Elf getElf() {
		return elf;
	}

	public void setElf(Elf elf) {
		this.elf = elf;
	}

}
