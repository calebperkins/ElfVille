package testcases;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Test;

import elfville.protocol.*;
import elfville.protocol.Response.Status;
import elfville.protocol.models.SerializableClan;

public class ClanDirectoryTest extends TestBase {

	@Test
	public void test1CreateClan() throws IOException {
		// Assert you can save clans successfully
		for (int i = 0; i < 50; i++) {
			String clanName= "clan-" + i;
			String description= "description-" + i;
			SerializableClan clan = new SerializableClan();
			clan.clanName= clanName;
			clan.clanDescription= description;
			CreateClanRequest req= new CreateClanRequest(clan);
			Response resp = socketControllers.get(0).send(req);
			System.out.println(resp.status);
			assertEquals(resp.status, Status.SUCCESS);
		}
	}

	@Test
	public void test2getClans() throws IOException {
		System.out.println("getClansTesting");
		ClanListingRequest req = new ClanListingRequest();
		ClanListingResponse resp = socketControllers.get(0).send(req);
		System.out.println(resp.status);
		assertEquals(resp.status, Status.SUCCESS);
		System.out.println(resp.clans.size());

		for (int i = 0; i < 50; i++) {
			SerializableClan clan = resp.clans.get(i);
			System.out.println("what?");
			System.out.println(clan.clanName);
			System.out.println(clan.clanDescription);
			System.out.println(clan.leader.elfName);
			assertEquals("clan-" + i, clan.clanName);
			assertEquals("description-" + i, clan.clanDescription);
			assertEquals("user1", clan.leader.elfName);
		}

	}

}

