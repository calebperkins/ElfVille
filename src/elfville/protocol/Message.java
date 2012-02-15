package elfville.protocol;

import java.io.Serializable;

/**
 * Abstract class for socket messages. In a derived class's constructor, be sure to always call super,
 * in order to set the type field correctly.
 * @author caleb
 *
 */
public abstract class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private String type;
	public String secret; // TODO: delete! for testing only!
	
	public Message() {
		type = this.getClass().getName();
	}
	
	public String getType() {
		return type;
	}
	
}
