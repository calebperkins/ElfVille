package elfville.server;

import elfville.server.database.*;
import elfville.server.model.*;

/*
 * Contains data structures that represent the server's database
 */
public class Database {
	
	public ClanDB clanDB;
	public PostDB postDB;
	
	// Initiate a new Database object
	public Database() {
		clanDB = new ClanDB();
		postDB = new PostDB();
		
		// TODO: testing. deleted later!
		Post p = new Post();
		p.setContent("this post is created in Database.java just to test");
		postDB.insert(p);
		
	}
	
	// Read the database from disk
	public Database(String dbLocation) {
		
	}
	
	protected void readFromDisk() {
		
	}
	
	protected void writeToDisk() {
		
	}
}
