package testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import elfville.protocol.ClanBoardRequest;
import elfville.protocol.ClanBoardResponse;
import elfville.protocol.ClanListingRequest;
import elfville.protocol.ClanListingResponse;
import elfville.protocol.CreateClanRequest;
import elfville.protocol.ModifyClanRequest;
import elfville.protocol.PostClanBoardRequest;
import elfville.protocol.Response;
import elfville.protocol.Response.Status;
import elfville.protocol.models.SerializableClan;
import elfville.protocol.models.SerializableElf;
import elfville.protocol.models.SerializablePost;

public class ClanBoardTest extends TestBase {

	@Test
	// calls the tests in the proper order
	public void orderTests() throws IOException {
		test1CreateClan();
		test2ApplyClan();
		test3ReplyApplicant();
		// test4CreatePost(); // works if I slow things down a little bit
		// (debug)
		test5GetClanBoard();
		test6RemovePost();
		test7LeaveClan();
		test8DisbandClan();
	}

	// @Test
	// 10 clients create one clan each.
	public void test1CreateClan() throws IOException {
		// Assert you can save clans successfully
		for (int i = 0; i < clientNum; i++) {
			String x = String.format("%2d", i); // needed to ensure lexographic
												// order
			String clanName = "clan-" + x;
			String description = "description-" + x;
			SerializableClan clan = new SerializableClan();
			clan.clanName = clanName;
			clan.clanDescription = description;
			CreateClanRequest req = new CreateClanRequest(clan);
			Response resp = socketControllers.get(i % clientNum).send(req);
			assertTrue(resp.isOK());
		}
	}

	// @Test
	// All clients try to apply to all other clans. Check if this SUCCEED.
	// Client try to apply to its own clan. Check if this FAIL.
	public void test2ApplyClan() throws IOException {

		ClanListingRequest req = new ClanListingRequest();
		ClanListingResponse resp = socketControllers.get(0).send(req);
		assertEquals(resp.status, Status.SUCCESS);

		for (int i = 0; i < clientNum; i++) {
			for (int k = 0; k < clientNum; k++) {
				SerializableClan clan = resp.clans.get(k);
				ModifyClanRequest modReq = new ModifyClanRequest(clan,
						ModifyClanRequest.ModClan.APPLY);
				Response modRes = socketControllers.get(i).send(modReq);
				assertEquals(modRes.isOK(), i != k);
			}
		}
	}

	// @Test
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
			assertTrue(clanRes.isOK());

			for (int k = 0; k < clanRes.clan.applicants.size(); k++) {
				SerializableElf elf = clanRes.clan.applicants.get(k);
				ModifyClanRequest modReq = new ModifyClanRequest(elf, clan,
						k < i);
				System.out.println("giving access to: " + elf.modelID + " " + k
						+ " " + i);
				Response modRes = socketControllers.get(i).send(modReq);
				assertTrue(modRes.isOK());
			}
		}
	}

	// @Test
	// Clan owners and members create posts, check SUCCESS
	// outsiders create posts on the clan board, check FAILURE
	public void test4CreatePost() throws IOException {
		ClanListingRequest req = new ClanListingRequest();
		ClanListingResponse resp = socketControllers.get(0).send(req);
		assertEquals(resp.status, Status.SUCCESS);

		for (int i = 0; i < clientNum; i++) {
			SerializableClan clan = resp.clans.get(i);
			for (SerializableElf e : clan.members) {
				System.out.println("clan " + clan.clanName + " has member elf "
						+ e.elfName);
			}

			for (int j = 0; j < clientNum; j++) {
				SerializablePost post = new SerializablePost();
				post.title = "post on board: " + i + " by elf: " + j;
				post.content = "lol";
				PostClanBoardRequest postReq = new PostClanBoardRequest(post,
						clan.modelID);
				System.out.println("i: " + i + "  j:" + j);
				Response postResp = socketControllers.get(j).send(postReq);

				// if the client is a smaller or equal number to the owner,
				// the post will succeed. otherwise, it will fail.
				if (j <= i) {
					assertTrue(postResp.isOK());
				} else {
					assertFalse(postResp.isOK());
				}
			}
		}

	}

	// @Test
	// Clan owners and member get Clan Board, check SUCCESS. The clan page
	// should contain name, description, numSock, and private board.
	// Outsider get Clan Board, check SUCCESS. The clan page should contain
	// everything except private board.
	public void test5GetClanBoard() throws IOException {
		ClanListingRequest req = new ClanListingRequest();
		ClanListingResponse resp = socketControllers.get(0).send(req);
		assertEquals(resp.status, Status.SUCCESS);

		for (int i = 0; i < clientNum; i++) {
			SerializableClan clan = resp.clans.get(i);
			String x = String.format("%2d", i); // needed to ensure lexographic
												// order

			for (int k = 0; k < clientNum; k++) {
				ClanBoardRequest clanReq = new ClanBoardRequest(clan.modelID);
				ClanBoardResponse clanRes = socketControllers.get(k).send(
						clanReq);
				System.out.println("getclanboard: " + k + i + " "
						+ clanRes.isOK());
				// Check if clan page contains name, description, numSock
				assertEquals("clan-" + x, clanRes.clan.clanName);
				assertEquals("description-" + x, clanRes.clan.clanDescription);
				assertEquals("user" + (i % clientNum),
						clanRes.clan.leader.elfName);

				// assertTrue((k > i) == (clanRes.clan.posts.size() == 0));
			}
		}
	}

	// @Test
	// Clan owners and members remove posts, check SUCCESS
	// outsiders remove posts on the clan board, check FAILURE
	public void test6RemovePost() throws IOException {
		ClanListingRequest req = new ClanListingRequest();
		ClanListingResponse resp = socketControllers.get(0).send(req);
		assertEquals(resp.status, Status.SUCCESS);

		for (int i = 0; i < clientNum; i++) {
			SerializableClan clan = resp.clans.get(i);

			for (int j = 0; j < clientNum; j++) {
				ClanBoardRequest req2 = new ClanBoardRequest(clan.modelID);
				ClanBoardResponse resp2 = socketControllers.get(j).send(req2);
				assertEquals(resp2.status, Status.SUCCESS);
				ArrayList<SerializablePost> posts = (ArrayList<SerializablePost>) resp2.clan.posts;

				for (int k = 0; k < posts.size(); k++) {
					SerializablePost post = posts.get(k);
					ModifyClanRequest modReq = new ModifyClanRequest(clan, post);

					// should fail if the user did not make the post. don't let
					// the leader
					// try because he can remove anything
					if (i != j) {
						Response deleteResp = socketControllers.get(j).send(
								modReq);
						if (post.myPost) {
							assertTrue(deleteResp.isOK());
						} else {
							assertFalse(deleteResp.isOK());
						}
					}
				}
			}
		}
	}

	// @Test
	// Clan members leave Clan, check SUCCESS. Also check all posts on the clan
	// posting board is gone.
	// Retest all previous tests to make sure the left member is treated as an
	// outsider.
	// Check the member list in Clan does not contain this elf.
	// Outsiders leave clan, check FAILURE
	// Leader leaves clan, check FAILURE
	public void test7LeaveClan() throws IOException {
		ClanListingRequest req = new ClanListingRequest();
		ClanListingResponse resp = socketControllers.get(0).send(req);
		assertEquals(resp.status, Status.SUCCESS);

		for (int i = 0; i < clientNum; i++) {
			SerializableClan clan = resp.clans.get(i);

			for (SerializableElf memberElf : clan.members) {
				System.out.println("member elf: " + memberElf.elfName);
			}

			for (int k = 0; k < clientNum; k++) {
				ModifyClanRequest modReq = new ModifyClanRequest(clan,
						ModifyClanRequest.ModClan.LEAVE);
				Response modRes = socketControllers.get(k).send(modReq);
				System.out.println("leave clan: " + k + " " + i + " "
						+ modRes.isOK());
				assertTrue(modRes.isOK() == (k < i));
			}
		}

		req = new ClanListingRequest();
		resp = socketControllers.get(0).send(req);
		assertEquals(resp.status, Status.SUCCESS);

		// Double check if all member sizes are 1
		for (int i = 0; i < clientNum; i++) {
			SerializableClan clan = resp.clans.get(i);
			assertTrue(clan.members.size() == 1);
		}

	}

	// @Test
	// Leader disbands a clan, check SUCCESS. Check that Clan Directory does not
	// contain it.
	// Also recheck the former steps and expect FAILURE. (try to disband already
	// disbanded clan)
	// Clan members try to disband a clan, check FAILURE.
	// Outsiders try to disband a clan, check FAILURE.
	// Applicants try to disband a clan, check FAILURE.
	public void test8DisbandClan() throws IOException {
		// step 1, 0 create clan
		CreateClanRequest createClan = new CreateClanRequest("test8 clan",
				"the clan used for test 8");
		Response resp = socketControllers.get(0).send(createClan);
		assertTrue(resp.isOK());

		// get clan we just created
		ClanListingRequest getClans = new ClanListingRequest();
		ClanListingResponse clans = socketControllers.get(0).send(getClans);
		SerializableClan clan = null;
		for (SerializableClan candidateClan : clans.clans) {
			if (candidateClan.clanName.equals("test8 clan")) {
				clan = candidateClan;
			}
		}
		// step 2, 1 applies to clan
		ModifyClanRequest apply = new ModifyClanRequest(clan,
				ModifyClanRequest.ModClan.APPLY);
		socketControllers.get(1).send(apply);
		// step 6, 2 applies to clan
		socketControllers.get(2).send(apply);
		// step 7, 0 accepts 1
		ClanBoardRequest clanBoardReq = new ClanBoardRequest(clan.modelID);
		ClanBoardResponse clanBoard = socketControllers.get(0).send(
				clanBoardReq);
		SerializableElf elf = null;
		for (SerializableElf elfCandidate : clanBoard.clan.members) {
			if (elfCandidate.elfName.equals("user1")) {
				elf = elfCandidate;
			}
		}
		ModifyClanRequest accept = new ModifyClanRequest(elf, clan, true);
		socketControllers.get(0).send(accept);
		// step 8, 3 disband clan
		ModifyClanRequest disband = new ModifyClanRequest(clan,
				ModifyClanRequest.ModClan.DELETE);
		resp = socketControllers.get(3).send(disband);
		assertTrue(!resp.isOK());
		// step 9, 2 disband clan
		resp = socketControllers.get(2).send(disband);
		assertTrue(!resp.isOK());
		// step 10, 1 disband clan
		resp = socketControllers.get(1).send(disband);
		assertTrue(!resp.isOK());
		// step 2, 0 disband clan
		resp = socketControllers.get(0).send(disband);
		assertTrue(resp.isOK());
		// step 3, 0 disband clan
		resp = socketControllers.get(1).send(disband);
		assertTrue(!resp.isOK());
	}

}
