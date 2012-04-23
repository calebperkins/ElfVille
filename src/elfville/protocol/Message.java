package elfville.protocol;

import java.io.Serializable;
import java.lang.reflect.Field;

import elfville.protocol.models.SerializableModel;

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
	public int getChecksum() {
		Field[] fields = this.getClass().getDeclaredFields();
		int c = 0;
		for (Field f : fields) {
			if (!f.getName().equals("checksum"))
				try {
					Object o = f.get(this);
					c ^= getChecksumFor(o);
				} catch (IllegalAccessException e) {
					// System.err.println("illegal to access " + f.getName());
					// ignore it...
				}
		}
		return c;
	}

	private int getChecksumFor(Object o) {
		int c = 0;
		if (o instanceof SerializableModel) {
			c ^= ((SerializableModel) o).getChecksum();
		} else if (o instanceof Enum) {
			c = c ^ o.toString().hashCode();
		} else if (o instanceof Iterable<?>) {
			Iterable<?> i = (Iterable<?>) o;
			for (Object oo : i) {
				System.err.println("hi");
				c ^= getChecksumFor(oo);
			}
		} else {
			if (o != null)
				c = c ^ o.hashCode();
		}
		return c;
	}

	/**
	 * Use this to enforce message integrity. If you receive a message and this
	 * returns true, the message is corrupted.
	 * 
	 * @return whether this object is corrupted / hasn't been saved yet
	 */
	public final boolean isDirty() {
		// can be combined into line below, split for debugging
		int currentChecksum = getChecksum();
		return checksum != currentChecksum;
	}

	public void setChecksum() {
		checksum = getChecksum();
	}
}
