package elfville.server.model;

import java.util.Date;

import elfville.server.Database;

public abstract class Model {
	static Database database = Database.DB;

	Date created_at;
	Date updated_at;	

	public enum ClanElfRelationship {
		APPLICANT, MEMBER, LEADER
	}
	
}
