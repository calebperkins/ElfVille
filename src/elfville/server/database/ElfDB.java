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
	
	public Elf findElfByModelID(int modelID) {
		return elves.get(modelID);
	}
	
	public Elf findElfByEncryptedModelID(String encID) {
		int modelID = SecurityUtils.decryptStringToInt(encID);
		return findElfByModelID(modelID);
	}
	
}
