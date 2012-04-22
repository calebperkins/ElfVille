package elfville.protocol;

import java.io.Serializable;
import java.lang.reflect.Field;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private int nonce;
	private int checksum;
	
	public int getNonce() {
		return nonce;
	}

	public void setNonce(int n) {
		nonce = n;
	}
	
	/**
	 * Construct a checksum using reflection.
	 * 
	 * @return an XOR of all this object's field hash codes
	 */
	public final int getChecksum() {
		Field[] fields = this.getClass().getDeclaredFields();
		int c = 0;
		for (Field f : fields) {
			if (!f.getName().equals("checksum"))
				try {
					c = c ^ f.get(this).hashCode();
				} catch (IllegalAccessException e) {
					// ignore it...
				}
		}
		return c;
	}
	
	/**
	 * Use this to enforce message integrity. If you receive a message
	 * and this returns true, the message is corrupted.
	 * 
	 * @return whether this object is corrupted / hasn't been saved yet
	 */
	public final boolean isDirty() {
		return checksum != getChecksum();
	}
	
	public void setChecksum() {
		checksum = getChecksum();
	}
}
