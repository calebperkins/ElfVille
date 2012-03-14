package elfville.client;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;

import elfville.protocol.Request;

/**
 * Used to encrypt the initial sign in / sign up request to the server
 * @author Caleb Perkins
 *
 */
public class PublicKeyCipher {
	private Cipher cipher;

	private static final String PUBLIC = "RSA";

	public PublicKeyCipher(String public_key_path) throws IOException,
			GeneralSecurityException {
		byte[] keyBytes = loadKey(public_key_path);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance(PUBLIC);
		PublicKey server_public_key = kf.generatePublic(spec);
		cipher = Cipher.getInstance(PUBLIC);
		cipher.init(Cipher.ENCRYPT_MODE, server_public_key);
	}

	private byte[] loadKey(String filename) throws IOException {
		File f = new File(filename);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int) f.length()];
		dis.readFully(keyBytes);
		dis.close();
		return keyBytes;
	}

	public SealedObject encrypt(Request req) throws GeneralSecurityException,
			IOException {
		return new SealedObject(req, cipher);
	}
}
