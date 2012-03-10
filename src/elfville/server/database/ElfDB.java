package elfville.server.database;

import java.util.concurrent.ConcurrentHashMap;

import elfville.server.SecurityUtils;
import elfville.server.model.Elf;

public class ElfDB extends DB {
	private final ConcurrentHashMap<Integer, Elf> elves = new ConcurrentHashMap<Integer, Elf>();

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
