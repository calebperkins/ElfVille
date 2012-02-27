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

	/*
	 * A static database used for controllers. Server.java initialize this.
	 * Notice this is a class variable!
	 */
	public static Database DB;

	public ClanDB clanDB;
	public PostDB postDB;
	public ElfDB elfDB;
	// public ClanElfDB clanElfDB;
	public UserDB userDB;

	private int countID;

	// Initiate a new Database object
	public Database() {
		clanDB = new ClanDB();
		postDB = new PostDB();
		elfDB = new ElfDB();
		// clanElfDB = new ClanElfDB();
		userDB = new UserDB();
		countID = -1;

		// TODO: testing. deleted later!
		// Post p = new Post();
		// p.setContent("this post is created in Database.java just to test");
		// postDB.insert(p);

	}

	// Read the database from disk
	static public Database load(String dbLocation) throws Exception {
		FileInputStream fin = new FileInputStream("/tmp/elfville.db");
	    ObjectInputStream ois = new ObjectInputStream(fin);
	    Database db = (Database)ois.readObject();
	    ois.close();
	    return db;
	}

	public synchronized int getAndIncrementCountID() {
		countID++;
		return countID;
	}

	public void writeToDisk() throws IOException {
		FileOutputStream fout = new FileOutputStream("/tmp/elfville.db");
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(this);
		oos.close();
	}
}
