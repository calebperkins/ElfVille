package elfville.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import elfville.server.database.*;

/*
 * Contains data structures that represent the server's database
 */
public class Database implements Serializable {

	private static final long serialVersionUID = -4429813192513967745L;

	/*
	 * A static database used for controllers. Server.java initialize this.
	 * Notice this is a class variable!
	 */
	public static Database DB;

	public final ClanDB clanDB = new ClanDB();
	public final PostDB postDB = new PostDB();
	public final ElfDB elfDB = new ElfDB();
	public final UserDB userDB = new UserDB();

	// Determines the modelID of all model objects.
	// getAndIncrementCountID() will increment this by 1.
	private int countID = -1;

	// Read the database from disk
	static public Database load(String dbLocation) throws Exception {
		try {
			FileInputStream fin = new FileInputStream(dbLocation);
			ObjectInputStream ois = new ObjectInputStream(fin);
			Database db = (Database) ois.readObject();
			ois.close();
			return db;
		} catch (FileNotFoundException ex) {
			System.err.println(dbLocation + " not found. Creating...");
			return new Database();
		}
	}

	public synchronized int getAndIncrementCountID() {
		countID++;
		return countID;
	}

	public void writeToDisk(String path) throws IOException {
		FileOutputStream fout = new FileOutputStream(path);
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(this);
		oos.close();
	}
}
