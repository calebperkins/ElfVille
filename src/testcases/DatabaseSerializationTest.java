package testcases;

import static org.junit.Assert.*;

import org.junit.Test;

import elfville.server.Database;
import elfville.server.model.*;

public class DatabaseSerializationTest {

	@Test
	public void testDBrestore() throws Exception {
		Database.load("/tmp/test.db");

		Elf e = new Elf("Larry", "Likes pork");
		e.save();

		Clan c = new Clan("Pork Lovers", "Clan of Larry", e);
		c.save();

		Database.getInstance().flush();

		try {
			Database.load("/tmp/test.db");
			assertEquals("Elfs persist", e.getName(), Elf.get(e.getModelID())
					.getName());
			assertEquals("Clans persist", c.getName(),
					Database.getInstance().clanDB.findByModelID(c.getModelID())
							.getName());
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("lol");
		}
	}

}
