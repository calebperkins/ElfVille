package elfville.protocol.utils;

import java.io.IOException;

import java.security.GeneralSecurityException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

import elfville.protocol.Request;
import elfville.protocol.Response;

public class SharedKeyCipher {
	private final Cipher enc;
	private final Cipher dec;
	private final KeyGenerator gen;
	
	private SecretKey shared_key;

	private static final String SHARED = "AES/CBC/PKCS5Padding";
	private static final int SHAREDKEY_LENGTH = 128;

	/*
	 * used by client to allow creation of a key that can be passed to server
	 */
	public SharedKeyCipher() throws GeneralSecurityException {
		enc = Cipher.getInstance(SHARED);
		dec = Cipher.getInstance(SHARED);
		
		// Note: key generator takes only algorithm as parameter! 
		gen = KeyGenerator.getInstance("AES");
		gen.init(SHAREDKEY_LENGTH);
		
		reinitialize();
	}
	
	public void reinitialize() throws GeneralSecurityException {
		shared_key = gen.generateKey();
		enc.init(Cipher.ENCRYPT_MODE, shared_key);
		dec.init(Cipher.DECRYPT_MODE, shared_key, enc.getParameters());
	}

	/*
	 * used by server given client's generated key
	 */
	public SharedKeyCipher(SecretKey shared_key, AlgorithmParameterSpec spec)
			throws GeneralSecurityException {
		enc = Cipher.getInstance(SHARED);
		dec = Cipher.getInstance(SHARED);
		gen = null;
		enc.init(Cipher.ENCRYPT_MODE, shared_key, spec);
		dec.init(Cipher.DECRYPT_MODE, shared_key, spec);
	}

	/*
	 * Used by client to generate the key
	 */
	public SecretKey getSharedKey() {
		return shared_key;
	}
	
	public byte[] getIV() {
		return enc.getIV();
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
