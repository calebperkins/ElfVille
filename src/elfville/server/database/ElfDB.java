package elfville.server.database;

import java.util.ArrayList;
import java.util.List;

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
	
	// auto generated getters and setters
	public List<Elf> getElves() {
		return elves;
	}

	public void setElves(List<Elf> elves) {
		this.elves = elves;
	}

}
