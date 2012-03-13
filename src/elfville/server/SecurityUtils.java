package elfville.server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class SecurityUtils {

	public static PublicKey public_key;
	public static PrivateKey private_key;

	private static byte[] loadKey(String filename) throws Exception {
		File f = new File(filename);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int) f.length()];
		dis.readFully(keyBytes);
		dis.close();
		return keyBytes;
	}

	public static void loadPublicKey(String filename) throws Exception {
		byte[] keyBytes = loadKey(filename);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		public_key = kf.generatePublic(spec);
	}

	public static void loadPrivateKey(String filename) throws Exception {
		byte[] keyBytes = loadKey(filename);
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		private_key = kf.generatePrivate(spec);
	}

	public static String encryptIntToString(int intNum) {
		return new Integer(intNum).toString();
	}

	public static int decryptStringToInt(String str) {
		return Integer.parseInt(str);
	}
}
