package elfville.server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.SealedObject;
import elfville.protocol.*;

/**
 * Used to decrypt requests from the Client that were encrypted with the
 * server's public key. You only need one instance of this class.
 * 
 * @author Caleb Perkins
 * 
 */
public class PublicKeyCipher {
	private final PrivateKey private_key;

	public static PublicKeyCipher instance;

	private static final String ALG = "RSA";

	private byte[] loadKey(String filename) throws Exception {
		File f = new File(filename);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int) f.length()];
		dis.readFully(keyBytes);
		dis.close();
		return keyBytes;
	}

	private PrivateKey loadPrivateKey(String filename) throws Exception {
		byte[] keyBytes = loadKey(filename);
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance(ALG);
		return kf.generatePrivate(spec);
	}

	public PublicKeyCipher(String private_key_path) throws Exception {
		private_key = loadPrivateKey(private_key_path);
	}

	public SignInRequest decrypt(SealedObject obj) throws IOException,
			ClassNotFoundException, GeneralSecurityException {
		return (SignInRequest) obj.getObject(private_key);
	}

}
