package elfville.protocol;

public class LoadClassRequest extends Request {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public byte[] fileBytes;
	public String fileName;
	public String filepath;
}
