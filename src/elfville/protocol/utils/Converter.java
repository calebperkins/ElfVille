package elfville.protocol.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Converter {

	public static byte[] intToByteArray(int value) {
		return new byte[] {
				(byte)(value >>> 24),
				(byte)(value >>> 16),
				(byte)(value >>> 8),
				(byte)value};
	}
	
	public static int byteArrayToInt(byte [] b) {
		return (b[0] << 24)
				+ ((b[1] & 0xFF) << 16)
				+ ((b[2] & 0xFF) << 8)
				+ (b[3] & 0xFF);
	}
	
	// byteRange is the largest number of bytes you want.
	// i.e. byteRange=1 means you want a random number from 0 - 2^8
	public static int secureRandomInt(int byteRange) {
		byte[] b = new byte[2];
		SecureRandom sr;
		try {
			sr = SecureRandom.getInstance("SHA1PRNG");
			sr.nextBytes(b);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] b2 = new byte[4];
		b2[0] = b[0];
		b2[1] = b[1];
		return byteArrayToInt(b2);
	}

}
