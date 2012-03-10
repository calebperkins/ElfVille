package elfville.server.model;

import java.io.Serializable;
import java.util.Date;

import elfville.server.Database;
import elfville.server.SecurityUtils;

public abstract class Model implements Serializable {
	private static final long serialVersionUID = -3671088963465928601L;

	protected static Database database = Database.getInstance();

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
	 * Write a copy of this object to the output stream. All setters should run
	 * this to persist changes. Controllers should only need to call this method
	 * explicitly if you have created a new object!
	 */
	public void save() {
		database.persist(this);
	}
}
