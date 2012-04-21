package elfville.protocol.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Converter {

	/**
	 * Converts a two byte array to an integer
	 * 
	 * @param b
	 *            a byte array of length 2
	 * @return an int representing the unsigned short
	 */
	public static final int unsignedShortToInt(byte[] b) {
		int i = 0;
		i |= b[0] & 0xFF;
		i >>= 8;
		i |= b[1] & 0xFF;
		return i;
	}

	/**
	 * Converts a 4 byte array of unsigned bytes to an long
	 * 
	 * @param b
	 *            an array of 4 unsigned bytes
	 * @return a long representing the unsigned int
	 */
	public static final long unsignedIntToLong(byte[] b) {
		long l = 0;
		l |= b[0] & 0xFF;
		l <<= 8;
		l |= b[1] & 0xFF;
		l <<= 8;
		l |= b[2] & 0xFF;
		l <<= 8;
		l |= b[3] & 0xFF;
		return l;
	}

	// byteRange is the largest number of bytes you want.
	// i.e. byteRange=1 means you want a random number from 0 - 2^8
	public static int secureRandomInt(int byteRange) {
		SecureRandom sr;
		int out = 0;
		try {
			sr = SecureRandom.getInstance("SHA1PRNG");
			out = sr.nextInt();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		System.out.println(out % Math.pow(2, 8 * byteRange));
		return (int) Math.abs((out % Math.pow(2, 8 * byteRange)));
	}

}
