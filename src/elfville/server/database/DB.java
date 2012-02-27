package elfville.server.database;

import java.io.Serializable;

import elfville.server.Database;

public abstract class DB implements Serializable {
	static Database database = Database.DB;

}
