package testcases;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import elfville.server.Database;
import elfville.server.model.*;

public class DatabaseSerializationTest {
	private Elf e;
	private Clan c1;
	private Clan c2;

	@Before
	public void setup() throws Exception {
		// Database.load("/tmp/test.db");

		e = new Elf("Larry");
		e.save();

		c1 = new Clan("Pork Lovers", "Clan of Larry", e);
		c1.save();

		c2 = new Clan("Delete Me", "Needs to be deleted", e);
		c2.save();
		c2.delete();

		Database.getInstance().flush();
	}

	@Test
	public void deletedObjects() throws Exception {
		// Database.load("/tmp/test.db");
		assertNull("deleted object", Clan.get(c2.getName()));
	}

	@Test
	public void testDBrestore() throws Exception {
		// Database.load("/tmp/test.db");
		assertEquals("Clans persist", c1, Clan.get(c1.getName()));
		assertEquals("Elfs persist", e, Elf.get(e.getModelID()));
	}

}
