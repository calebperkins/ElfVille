package elfville.protocol;

import elfville.protocol.models.SerializableElf;

public class ProfileResponse extends Response {
	private static final long serialVersionUID = 1L;

	public ProfileResponse(Status s) {
		this.status = s;
	}

	public SerializableElf elf;

}
