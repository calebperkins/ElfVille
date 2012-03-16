package elfville.server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import elfville.protocol.utils.Converter;
import elfville.server.model.Model;

public class SecurityUtils {
	static final int PEPPER_SIZE = 1;  // 1 byte
	
	// Pepper password	
	public static String generateRandomPepper(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		int pepper = Converter.secureRandomInt(PEPPER_SIZE);
		System.out.println("the pepper is: " + pepper);
		return generatePepper(password, pepper);
	}
	
	private static String generatePepper(String password, int pepper) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String pepperPassword = pepper + password;
		
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.reset();
		byte[] pwdByte = digest.digest(pepperPassword.getBytes("UTF-8"));

		String hashedPassword = new String(pwdByte, "UTF-8"); 
		return hashedPassword;

	}
	
	public static boolean checkPepperPassword(String password, String hashedPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		for (int i = 0; i < Math.pow(2, 8*PEPPER_SIZE); i++) {
			if (generatePepper(password, i).equals(hashedPassword)) {
				return true;
			}
		}
		return false;
	}
	
	public static String encryptIntToString(int intNum) {
		return new Integer(intNum).toString();
	}

	public static int decryptStringToInt(String str) {
		return Integer.parseInt(str);
	}
	
	public static SealedObject encrypt(Serializable m, Cipher enc) throws IllegalBlockSizeException,
	IOException {
		return new SealedObject(m, enc);
	}
	
	public static Serializable decrypt(SealedObject m, Cipher dec)
			throws IllegalBlockSizeException, BadPaddingException, IOException,
			ClassNotFoundException {
		return (Serializable) m.getObject(dec);
	}
	
	public static SecretKey getKeyFromFile(String filepath) throws IOException{
		File f = new File(filepath);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[32];
		dis.readFully(keyBytes);
		dis.close();
		return new SecretKeySpec(keyBytes, "AES");	
		
	}
	 
}
