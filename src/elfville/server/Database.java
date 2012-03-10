package elfville.server;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import elfville.server.database.*;
import elfville.server.model.*;

/*
 * Contains data structures that represent the server's database.
 * Is a singleton.
 */
public class Database {
	private static Database instance = new Database();
	public ObjectOutputStream stream = null;
	public final ClanDB clanDB = new ClanDB();
	public final PostDB postDB = new PostDB();
	public final ElfDB elfDB = new ElfDB();
	public final UserDB userDB = new UserDB();

	// Determines the modelID of all model objects.
	// getAndIncrementCountID() will increment this by 1.
	private int countID = -1;

	protected Database() {
		// Solely to prevent outside instantiation.
	}

	static public Database getInstance() {
		return instance;
	}
	
	public void persist(Serializable obj) {
		if (stream != null) {
			try {
				stream.writeUnshared(obj);
			} catch (IOException e) {
				System.err.println(obj + " could not be saved.");
			}
		}
	}
	
	public void flush() throws IOException {
		if (stream != null) {
			stream.flush();
		}
	}

	// Read the database from disk
	static public void load(String dbLocation) throws Exception {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					dbLocation));
			try {
				Object m;
				// TODO: handle deleted objects
				while ((m = ois.readObject()) != null) {
					if (m instanceof Clan) {
						instance.clanDB.insert((Clan) m);
					} else if (m instanceof Elf) {
						instance.elfDB.insert((Elf) m);
					} else if (m instanceof Post) {
						instance.postDB.insert((Post) m);
					} else if (m instanceof User) {
						instance.userDB.insert((User) m);
					}
				}
			} catch (EOFException e) {
			} finally {
				ois.close();
			}
		} catch (FileNotFoundException ex) {
			System.err.println(dbLocation + " not found. Creating...");
		}
		instance.stream = new ObjectOutputStream(new FileOutputStream(dbLocation));
	}

	public synchronized int getAndIncrementCountID() {
		countID++;
		return countID;
	}
}
