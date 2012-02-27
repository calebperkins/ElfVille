package elfville.server.database;

import java.io.Serializable;

import elfville.server.Database;

public abstract class DB implements Serializable {
	private static final long serialVersionUID = -7080569461972656844L;
	static Database database = Database.DB;

}
