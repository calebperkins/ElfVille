package elfville.server;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import elfville.protocol.utils.Converter;

public class SecurityUtils {
	static final int PEPPER_SIZE = 1;  // 1 byte
	
	// Pepper password	
	public static String generateRandomPepper(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		int pepper = Converter.secureRandomInt(PEPPER_SIZE);
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
	 
}
