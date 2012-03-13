package elfville.client;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import elfville.protocol.Request;
import elfville.protocol.Response;

public class EncryptionService {
	private Cipher shared_encryption_cipher;
	private Cipher shared_decryption_cipher;
	private KeyGenerator gen;

	// The algorithms used
	private static final String SHARED = "DES";
	private static final String PUBLIC = "RSA";

	// Used to send the shared key and initialize the shared cipher
	private Cipher public_encryption_cipher;

	public EncryptionService(String public_key_path) throws Exception {
		shared_encryption_cipher = Cipher.getInstance(SHARED);
		shared_decryption_cipher = Cipher.getInstance(SHARED);
		gen = KeyGenerator.getInstance(SHARED);
		initPublicKeyCipher(public_key_path);
	}

	private byte[] loadKey(String filename) throws Exception {
		File f = new File(filename);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int) f.length()];
		dis.readFully(keyBytes);
		dis.close();
		return keyBytes;
	}

	private void initPublicKeyCipher(String filename) throws Exception {
		byte[] keyBytes = loadKey(filename);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance(PUBLIC);
		PublicKey server_public_key = kf.generatePublic(spec);
		public_encryption_cipher = Cipher.getInstance(PUBLIC);
		public_encryption_cipher.init(Cipher.ENCRYPT_MODE, server_public_key);
	}

	/**
	 * Call this each time you make a new TCP connection.
	 * 
	 * @return a fresh shared key
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 */
	public SecretKey getNewSharedKey() throws InvalidKeySpecException,
			NoSuchAlgorithmException, InvalidKeyException,
			NoSuchPaddingException {
		SecretKey shared_key = gen.generateKey();
		shared_encryption_cipher.init(Cipher.ENCRYPT_MODE, shared_key);
		shared_decryption_cipher.init(Cipher.DECRYPT_MODE, shared_key);
		return shared_key;
	}

	public SealedObject encryptWithServerKey(Request req)
			throws IllegalBlockSizeException, IOException {
		return new SealedObject(req, public_encryption_cipher);
	}

	public SealedObject encryptWithSharedKey(Request req)
			throws IllegalBlockSizeException, IOException {
		return new SealedObject(req, shared_encryption_cipher);
	}

	public Response decrypt(SealedObject response)
			throws IllegalBlockSizeException, BadPaddingException, IOException,
			ClassNotFoundException {
		return (Response) response.getObject(shared_decryption_cipher);
	}

}
