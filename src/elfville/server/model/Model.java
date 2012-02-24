package elfville.server.model;

import java.util.Date;

import elfville.server.Database;

public abstract class Model {
	static Database database = Database.DB;

	private final Date createdAt;
	final int modelID;
	Date updatedAt;  // Not used
	
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

	public Date getCreatedAt() {
		return createdAt;
	}
}
