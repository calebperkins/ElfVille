package testcases;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Test;

import elfville.protocol.*;
import elfville.protocol.Response.Status;
import elfville.protocol.models.SerializableClan;
import elfville.protocol.models.SerializableElf;

//TODO: actual tests
public class ClanBoardTest extends TestBase {
	@Test
	public void test1CreateClan() throws IOException {
		// Assert you can save clans successfully
		for (int i = 0; i < clientNum; i++) {
			String x = String.format("%2d", i); // needed to ensure lexographic order
			String clanName= "clan-" + x;
			String description= "description-" + x;
			SerializableClan clan = new SerializableClan();
			clan.clanName= clanName;
			clan.clanDescription= description;
			CreateClanRequest req= new CreateClanRequest(clan);
			Response resp = socketControllers.get(i % clientNum).send(req);
			assertTrue(resp.isOK());
		}
	}
	
	@Test
	// All clients try to apply to all other clans. Check if this SUCCEED.
	// Client try to apply to its own clan. Check if this FAIL.
	public void test2ApplyClan() throws IOException {

		ClanListingRequest req = new ClanListingRequest();
		ClanListingResponse resp = socketControllers.get(0).send(req);
		assertEquals(resp.status, Status.SUCCESS);

		for (int i = 0; i < clientNum; i++) {
			for (int k = 0; k < clientNum; k ++) {
				SerializableClan clan = resp.clans.get(k);
				ModifyClanRequest modReq = new ModifyClanRequest(clan, ModifyClanRequest.ModClan.APPLY);
				Response modRes = socketControllers.get(i).send(modReq);
				assertEquals(modRes.isOK(), i != k);
			}
		}
	}
	
	@Test
	// Clan owners accept half of the applications to check SUCCESS.
	// CLan owners deny the other half of the applicants to check SUCCESS
	public void test3ReplyApplicant() throws IOException {
		ClanListingRequest req = new ClanListingRequest();
		ClanListingResponse resp = socketControllers.get(0).send(req);
		assertEquals(resp.status, Status.SUCCESS);

		for (int i = 0; i < clientNum; i++) {
			SerializableClan clan = resp.clans.get(i);
			
			ClanBoardRequest clanReq = new ClanBoardRequest(clan.modelID);
			ClanBoardResponse clanRes = socketControllers.get(i).send(clanReq);
			for (int k = 0; k < clanRes.clan.applicants.size(); k++) {
				SerializableElf elf = clanRes.clan.applicants.get(k);
				ModifyClanRequest modReq = new ModifyClanRequest(elf, clan, k<i);
				Response modRes = socketControllers.get(i).send(modReq);
				assertTrue(modRes.isOK());
			}
		}
	}
	
	@Test
	// Clan owners and members create posts, check SUCCESS
	// outsiders create posts on the clan board, check FAILURE
	public void test4CreatePost() throws IOException {
		
		
	}
	
	@Test
	// Clan owners and member get Clan Board, check SUCCESS. The clan page should contain name, description, numSock, and private board. 
	// Outsider get Clan Board, check SUCCESS. The clan page should contain everything except private board.
	public void test5GetClanBoard() throws IOException {
		
	}
	
	@Test
	// Clan owners and members remove posts, check SUCCESS
	// outsiders remove posts on the clan board, check FAILURE
	public void test6RemovePost() throws IOException {
		
		
	}
	
	@Test
	// Clan members leave Clan, check SUCCESS. Also check all posts on the clan posting board is gone.
	// Retest all previous tests to make sure the left member is treated as an outsider.
	// Check the member list in Clan does not contain this elf.
	// Outsiders leave clan, check FAILURE
	// Leader leaves clan, check FAILURE
	public void test7LeaveClan() throws IOException {
		
	}
	
	@Test
	// Leader disbands a clan, check SUCCESS. Check that Clan Directory does not contain it.
	// Also recheck the former steps and expect FAILURE.
	// Clan members try to disband a clan, check FAILURE.
	// Outsiders try to disband a clan, check FAILURE.
	public void test8DisbandClan() throws IOException {
		
	}
	
}
