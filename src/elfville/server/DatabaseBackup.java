package elfville.server;

import java.io.IOException;

public class DatabaseBackup implements Runnable {
	private final String path;
	
	public DatabaseBackup(String path) {
		this.path = path;
	}

	@Override
	public void run() {
		try {
			System.out.println("Backing up database...");
			Database.DB.writeToDisk(path);
			System.out.println("Backup completed.");
		} catch (IOException e) {
			System.err.println("Could not backup database:");
			e.printStackTrace();
		}
	}

}
