package elfville.server;

import elfville.server.database.*;

/*
 * Contains data structures that represent the server's database
 */
public class Database {

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
	public Database(String dbLocation) {

	}

	public synchronized int getAndIncrementCountID() {
		countID++;
		return countID;
	}

	protected void readFromDisk() {

	}

	protected void writeToDisk() {

	}
}
