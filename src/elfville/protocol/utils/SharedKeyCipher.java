package elfville.protocol.utils;

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

	private static final String SHARED = "AES";
	private static final int SHAREDKEY_LENGTH = 128;

	/*
	 * used by client to allow creationg of a key that can be passed to server
	 */
	public SharedKeyCipher() throws GeneralSecurityException {
		enc = Cipher.getInstance(SHARED);
		dec = Cipher.getInstance(SHARED);
		gen = KeyGenerator.getInstance(SHARED);
		gen.init(SHAREDKEY_LENGTH);
	}

	/*
	 * used by server given client's generated key
	 */
	public SharedKeyCipher(SecretKey shared_key)
			throws GeneralSecurityException {
		enc = Cipher.getInstance(SHARED);
		dec = Cipher.getInstance(SHARED);
		enc.init(Cipher.ENCRYPT_MODE, shared_key);
		dec.init(Cipher.DECRYPT_MODE, shared_key);
	}

	/*
	 * Used by client to generate the key
	 */
	public SecretKey getNewSharedKey() throws InvalidKeySpecException,
			NoSuchAlgorithmException, InvalidKeyException,
			NoSuchPaddingException {
		SecretKey shared_key = gen.generateKey();
		enc.init(Cipher.ENCRYPT_MODE, shared_key);
		dec.init(Cipher.DECRYPT_MODE, shared_key);
		return shared_key;
	}

	/*
	 * Used by client to encrypt request
	 */
	public SealedObject encrypt(Request req) throws IllegalBlockSizeException,
			IOException {
		return new SealedObject(req, enc);
	}

	/*
	 * Used by server to encrypt response to client
	 */
	public SealedObject encrypt(Response response)
			throws IllegalBlockSizeException, IOException {
		return new SealedObject(response, enc);
	}

	/*
	 * used by client to decrypt server response
	 */
	public Response decrypt(SealedObject response)
			throws IllegalBlockSizeException, BadPaddingException, IOException,
			ClassNotFoundException {
		return (Response) response.getObject(dec);
	}

	/*
	 * used by server to decrypt client request
	 */
	public Request decryptWithSharedKey(SealedObject request)
			throws GeneralSecurityException, IOException,
			ClassNotFoundException {
		return (Request) request.getObject(dec);
	}
}
