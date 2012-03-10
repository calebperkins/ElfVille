package testcases;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.junit.Test;

import elfville.server.Database;
import elfville.server.model.*;

public class DatabaseSerializationTest {

	@Test
	public void testDBrestore() throws IOException {
		Database.DB = new Database();
		
		FileOutputStream fos = new FileOutputStream("/tmp/test.db");
		Database.Stream = new ObjectOutputStream(fos);
		
		Elf e = new Elf("Larry", "Likes pork");
		e.save();
		
		Clan c = new Clan("Pork Lovers", "Clan of Larry", e);
		c.save();
		
		Database.Stream.flush();
		
		try {
			Database.DB = Database.load("/tmp/test.db");
			assertEquals("Elfs persist", e.getName(), Elf.get(e.getModelID()).getName());
			assertEquals("Clans persist", c.getName(), Database.DB.clanDB.findByModelID(c.getModelID()).getName());
		} catch (Exception e1) {
			e1.printStackTrace();
			fail("lol");
		}
	}

}
