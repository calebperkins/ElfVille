package elfville.server.model;

import java.io.Serializable;
import java.util.Date;

import elfville.server.Database;
import elfville.server.SecurityUtils;

public abstract class Model implements Serializable {
	private static final long serialVersionUID = -3671088963465928601L;

	static Database database = Database.DB;

	private final Date createdAt;
	protected final int modelID;
	Date updatedAt; // Not used

	public Model() {
		modelID = database.getAndIncrementCountID();
		createdAt = new Date();
	}

	public enum ClanElfRelationship {
		APPLICANT, MEMBER, LEADER
	}

	public int getModelID() {
		return modelID;
	}

	public String getEncryptedModelID() {
		return SecurityUtils.encryptIntToString(getModelID());
	}

	public Date getCreatedAt() {
		return createdAt;
	}
	
	/**
	 * Save this model object, inserting a new one if this object has not been saved before.
	 * @return true if successful
	 */
	public abstract boolean save();
}
