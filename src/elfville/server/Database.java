package elfville.server;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

import elfville.server.database.ClanDB;
import elfville.server.database.ElfDB;
import elfville.server.database.PostDB;
import elfville.server.database.UserDB;
import elfville.server.model.Clan;
import elfville.server.model.Deletion;
import elfville.server.model.Elf;
import elfville.server.model.Model;
import elfville.server.model.Post;
import elfville.server.model.User;

/*
 * Contains data structures that represent the server's database.
 * Is a singleton.
 */
public class Database {
	private static Database instance;
	private ObjectOutputStream stream = null;
	public final ClanDB clanDB = new ClanDB();
	public final PostDB postDB = new PostDB();
	public final ElfDB elfDB = new ElfDB();
	public final UserDB userDB = new UserDB();

	private static SecretKey databaseSecret;
	private static Cipher enc;
	private static Cipher dec;

	// Determines the modelID of all model objects.
	// getAndIncrementCountID() will increment this by 1.
	private int countID = -1;

	private final Logger logger;

	protected Database(String dbLocation) throws Exception {
		logger = Logger.getLogger(dbLocation + " database");
		int oldCountId = 0;
		int objects_read = 0;

		int dbCounter = 0;
		String currentDbLocation = dbLocation + dbCounter;
		while (true) {
			currentDbLocation = dbLocation + dbCounter;
			dbCounter++;

			try {
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(currentDbLocation));
				SealedObject msg;
				while (true) {
					Serializable m;
					// read from database, if encrypted then decrypt it
					if (Server.DBENCRYPT) {
						msg = (SealedObject) ois.readObject();
						if (msg == null)
							break;
						m = SecurityUtils.decrypt(msg, dec);
					} else {
						m = (Serializable) ois.readObject();
						if (m == null)
							break;
					}
					objects_read++;

					if (m instanceof Model) {
						Model x = (Model) m;
						if (x.isDirty()) {
							logger.warning(x + " is corrupted.");
						}
						System.out.println("loading model ID: "
								+ x.getModelID());
						if (oldCountId < x.getModelID()) {
							oldCountId = x.getModelID();
						}
					}

					if (m instanceof Clan) {
						clanDB.add((Clan) m);
						logger.info("loading Clan");
					} else if (m instanceof Elf) {
						elfDB.add((Elf) m);
						logger.info("loading Elf");
					} else if (m instanceof Post) {
						postDB.add((Post) m);
						logger.info("loading Post");
					} else if (m instanceof User) {
						userDB.add((User) m);
						logger.info("loading User");
					} else if (m instanceof Deletion) {
						logger.info("loading Deletion");
						Deletion d = (Deletion) m;
						if (d.model instanceof Clan) {
							clanDB.remove((Clan) d.model);
						} else if (d.model instanceof Post) {
							System.out.println(d.model.getModelID());
							postDB.remove(d.model.getModelID());
						}

					}
				}
			} catch (FileNotFoundException ex) {
				logger.info(currentDbLocation + " not found. Creating...");
				break;
			} catch (EOFException ex) {
				logger.info("Loaded " + objects_read + " objects");
			} catch (StreamCorruptedException ex) {
				logger.warning("Stream Corrupted Exception");
			}
		}
		stream = new ObjectOutputStream(new FileOutputStream(currentDbLocation));
		resetCountID(oldCountId);
	}

	static public Database getInstance() {
		assert instance != null;
		return instance;
	}

	public void persist(Serializable obj) {
		if (stream != null) {
			try {
				Serializable msg;
				if (Server.DBENCRYPT) {
					msg = SecurityUtils.encrypt(obj, enc);
				} else {
					msg = obj;
				}
				stream.writeUnshared(msg);
			} catch (IOException e) {
				logger.warning(obj + " could not be saved.");
			} catch (IllegalBlockSizeException e) {
				logger.log(Level.WARNING, "Bad block size", e);
			}
		}
		flush();
	}

	public void flush() {
		if (stream != null) {
			logger.info("flush called!");
			try {
				stream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Read the database from disk
	static public void load() throws Exception {
		String dbLocation;
		String db_key_path;
		if (Server.DEBUG) {
			// dbLocation =
			// "resources/elfville"+System.currentTimeMillis()+".db";
			dbLocation = "resources/elfville.db";
			db_key_path = "resources/elfville.db.der";
		} else {
			// Ask users for database shared key
			Scanner scanner = new Scanner(System.in);
			System.out
					.println("Input Database file path (type 'resources/elfville.db' for demonstration: ");
			dbLocation = scanner.nextLine();

			System.out
					.println("Input Database encryption key file path\n (type 'resources/elfville.db.der' for demonstration,\n of course you can load one from your flash drive\n that you are inserting right now): ");
			db_key_path = scanner.nextLine();
		}

		// Initiate database key
		if (Server.DBENCRYPT) {
			databaseSecret = SecurityUtils.getKeyFromFile(db_key_path);
			enc = Cipher.getInstance("AES");
			dec = Cipher.getInstance("AES");
			enc.init(Cipher.ENCRYPT_MODE, databaseSecret);
			dec.init(Cipher.DECRYPT_MODE, databaseSecret);
		}
		instance = new Database(dbLocation);
	}

	public synchronized int getAndIncrementCountID() {
		countID++;
		return countID;
	}

	private synchronized void resetCountID(int oldCountId) {
		countID = oldCountId;
	}
}
