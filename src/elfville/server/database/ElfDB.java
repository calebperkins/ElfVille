package elfville.server.database;

import java.util.ArrayList;
import java.util.List;

import elfville.server.SecurityUtils;
import elfville.server.model.Clan;
import elfville.server.model.Elf;

public class ElfDB extends DB {
	
	private List<Elf> elves;
	
	public ElfDB() {
		elves = new ArrayList<Elf>();
	}
	
	public void insert(Elf elf) {
		elves.add(elf);
	}

	// No elf delete function
	
	public Elf findElfByModelID(int modelID) {
		for (Elf elf : elves) {
			if (elf.getModelID() == modelID) {
				return elf;
			}
		}
		return null;
	}
	
	public Elf findElfByEncryptedModelID(String encID) {
		int modelID = SecurityUtils.decryptStringToInt(encID);
		return findElfByModelID(modelID);
	}

	// auto generated getters and setters
	public List<Elf> getElves() {
		return elves;
	}

	public void setElves(List<Elf> elves) {
		this.elves = elves;
	}

}
