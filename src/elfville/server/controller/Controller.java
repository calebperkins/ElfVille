package elfville.server.controller;

import elfville.server.Database;

public abstract class Controller {
	
	/* A static database used for controllers.
	 * Server.java initialize this.
	 * No other classes except controllers can access this.
	 */
	public static Database database;
	
}
