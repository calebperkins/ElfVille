package elfville.server;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import elfville.server.database.*;
import elfville.server.model.*;

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
	public static ObjectOutputStream Stream;

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
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					dbLocation));
			Database db = new Database();
			try {
				Object m;
				// TODO: handle deleted objects
				while ((m = ois.readObject()) != null) {
					if (m instanceof Clan) {
						db.clanDB.insert((Clan) m);
					} else if (m instanceof Elf) {
						db.elfDB.insert((Elf) m);
					} else if (m instanceof Post) {
						db.postDB.insert((Post) m);
					} else if (m instanceof User) {
						db.userDB.insert((User) m);
					}
				}
			} catch (EOFException e) {
			} finally {
				ois.close();
			}
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
}
