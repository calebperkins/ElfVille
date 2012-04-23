package elfville.server;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.security.spec.KeySpec;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Server {

	public static final boolean DBENCRYPT = true;
	public static boolean DEBUG = true;
	public static final int KEYSIZE = 128;

	/**
	 * Starts a server. Use the first argument to provide a port, the second,
	 * the path to the database.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		int port = 8444;
		if (args.length < 1) {
			System.out.println("port not specified. Use 8444 as default");
		} else {
			port = Integer.parseInt(args[0]);
			System.out.println("Using port " + port + " as default");
		}

		DEBUG = args.length > 1 && args[1].equals("DEBUG");
		// DEBUG = true;

		ServerSocket serverSocket = null;
		boolean listening = true;

		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + port);
			System.exit(-1);
		}

		// get admin pwd
		char[] adminpwd;
		Scanner scanner = new Scanner(System.in);
		;
		String s;
		if (DEBUG) {
			RandomAccessFile f = new RandomAccessFile(
					"resources/imitationadmin", "r");
			adminpwd = f.readLine().toCharArray();
			f.close();

		} else {
			System.out
					.println("Input admin password (resouces/initializationvector for demo): ");
			s = scanner.nextLine();
			adminpwd = s.toCharArray();
		}

		// get initialization vector
		if (DEBUG) {
			s = "resources/initializationvector";
		} else {
			System.out.println("Input IV filename: ");
			s = scanner.nextLine();
		}
		FileInputStream fis = new FileInputStream(s);
		DataInputStream dis = new DataInputStream(fis);
		byte[] iv = new byte[16];
		dis.readFully(iv);
		dis.close();

		// use that password to create the cipher
		// from stack overflow (specifically the PBKDF2WithHmacSHA1)
		SecretKeyFactory factory = SecretKeyFactory
				.getInstance("PBKDF2WithHmacSHA1");
		byte[] salt = new byte[1];
		salt[0] = (byte) 9238;
		KeySpec spec = new PBEKeySpec(adminpwd, salt, 65536, KEYSIZE);
		SecretKey tmp = factory.generateSecret(spec);
		SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
		Cipher adminDec = Cipher.getInstance("AES/CBC/PKCS5Padding");
		// Cipher adminDec = Cipher.getInstance("AES");
		adminDec.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));

		// Initialize database
		Database.load(adminDec);

		String dbPrivateKeyPath;
		if (DEBUG) {
			dbPrivateKeyPath = "resources/elfville.der";
		} else {
			System.out
					.println("Input the file path for the private encryption key to use for socket communications.\n(Type 'resources/elfville.der' for demonstration,\n of course you can load one from your flash drive\n that you are inserting right now): ");
			dbPrivateKeyPath = scanner.nextLine();
		}
		// Initialize private key
		PublicKeyCipher.instance = new PublicKeyCipher(dbPrivateKeyPath,
				adminDec);

		ExecutorService pool = Executors.newCachedThreadPool();

		// Start the user load class queue
    	// LoadClassRequestQueue.startNewThread("userLoadClasses.MyClass");
    	
		System.out.println("Server now listening...");

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Shutting down...");
			}
		});

		// Support Multiple Clients
		while (listening) {
			try {
				pool.execute(new Session(serverSocket.accept()));
			} catch (IOException e) { // couldn't set timeout? drop them.
			}
		}
		// TODO: close the file correctly!
		serverSocket.close();
	}
}
