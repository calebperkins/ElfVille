package elfville.protocol;

import java.io.Serializable;

/**
 * Abstract class for socket messages.
 * @author caleb
 *
 */
public abstract class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	public String secret; // TODO: delete! for testing only!	
}
