package elfville.server.database;

import java.util.HashMap;

import elfville.server.SecurityUtils;
import elfville.server.model.Elf;

public class ElfDB extends DB {

	private HashMap<Integer, Elf> elves;

	public ElfDB() {
		elves = new HashMap<Integer, Elf>();
	}

	public void insert(Elf elf) {
		elves.put(elf.getModelID(), elf);
	}

	// No elf delete function

	public Elf findByID(int modelID) {
		return elves.get(modelID);
	}

	public Elf findByEncryptedID(String encID) {
		int modelID = SecurityUtils.decryptStringToInt(encID);
		return findByID(modelID);
	}

}
