package elfville.server;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.crypto.*;

import elfville.protocol.*;

/**
 * Corresponds to each client connection, to encrypt data
 * 
 * @author caleb
 * 
 */
public class ClientCipher {
	private Cipher enc;
	private Cipher dec;

	// The algorithms used
	private static final String SHARED = "DES";

	public ClientCipher(SecretKey shared_key) throws GeneralSecurityException {
		enc = Cipher.getInstance(SHARED);
		dec = Cipher.getInstance(SHARED);
		enc.init(Cipher.ENCRYPT_MODE, shared_key);
		dec.init(Cipher.DECRYPT_MODE, shared_key);
	}

	public Request decryptWithSharedKey(SealedObject request)
			throws GeneralSecurityException, IOException,
			ClassNotFoundException {
		return (Request) request.getObject(dec);
	}

	public SealedObject encrypt(Response response)
			throws IllegalBlockSizeException, IOException {
		return new SealedObject(response, enc);
	}
}
