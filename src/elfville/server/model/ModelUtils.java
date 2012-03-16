package elfville.server.model;
import java.io.BufferedWriter;
import java.io.FileWriter;
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

public class ModelUtils {


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
		public void getNewSharedKey() throws InvalidKeySpecException,
		NoSuchAlgorithmException, InvalidKeyException,
		NoSuchPaddingException {
			SecretKey shared_key = gen.generateKey();
			enc.init(Cipher.ENCRYPT_MODE, shared_key);
			dec.init(Cipher.DECRYPT_MODE, shared_key); 
			try{
				// Create file 
				FileWriter fstream = new FileWriter("database_key.der");
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(shared_key.toString());
				//Close the output stream
				out.close();
			}catch (Exception e){//Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
		}

		/*
		 * Used by client to encrypt request
		 */
		public SealedObject encrypt(Model m) throws IllegalBlockSizeException,
		IOException {
			return new SealedObject(m, enc);
		}
		

		/*
		 * used by client to decrypt server response
		 */
		public Model decrypt(SealedObject m)
				throws IllegalBlockSizeException, BadPaddingException, IOException,
				ClassNotFoundException {
			return (Model) m.getObject(dec);
		}

		/*
		 * used by server to decrypt client request
		 */
	}
}
