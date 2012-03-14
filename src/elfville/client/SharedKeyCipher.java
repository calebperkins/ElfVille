package elfville.client;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

import elfville.protocol.Request;
import elfville.protocol.Response;

public class SharedKeyCipher {
	private Cipher enc;
	private Cipher dec;
	private KeyGenerator gen;

	private static final String SHARED = "DES";

	public SharedKeyCipher() throws GeneralSecurityException {
		enc = Cipher.getInstance(SHARED);
		dec = Cipher.getInstance(SHARED);
		gen = KeyGenerator.getInstance(SHARED);
	}

	public SecretKey getNewSharedKey() throws InvalidKeySpecException,
			NoSuchAlgorithmException, InvalidKeyException,
			NoSuchPaddingException {
		SecretKey shared_key = gen.generateKey();
		enc.init(Cipher.ENCRYPT_MODE, shared_key);
		dec.init(Cipher.DECRYPT_MODE, shared_key);
		return shared_key;
	}

	public SealedObject encrypt(Request req) throws IllegalBlockSizeException,
			IOException {
		return new SealedObject(req, enc);
	}

	public Response decrypt(SealedObject response)
			throws IllegalBlockSizeException, BadPaddingException, IOException,
			ClassNotFoundException {
		return (Response) response.getObject(dec);
	}
}
