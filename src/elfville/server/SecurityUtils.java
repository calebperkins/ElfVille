package elfville.server;

public class SecurityUtils {

	public static String encryptIntToString(int intNum) {
		return new Integer(intNum).toString();
	}

	public static int decryptStringToInt(String str) {
		return Integer.parseInt(str);
	}
}
