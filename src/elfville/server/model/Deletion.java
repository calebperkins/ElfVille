package elfville.server.model;

import java.io.Serializable;

/***
 * Hacky way to delete objects from a stream. See Database.load() for use.
 * 
 * @author Caleb Perkins
 * 
 */
public final class Deletion implements Serializable {
	private static final long serialVersionUID = -5494544262655106127L;
	// private static final Database db = Database.getInstance();
	public final Model model;

	public Deletion(Model m) {
		this.model = m;
	}

}
