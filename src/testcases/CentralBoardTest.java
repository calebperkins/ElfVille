package testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import elfville.protocol.CentralBoardRequest;
import elfville.protocol.CentralBoardResponse;
import elfville.protocol.DeleteCentralBoardRequest;
import elfville.protocol.PostCentralBoardRequest;
import elfville.protocol.ProfileRequest;
import elfville.protocol.ProfileResponse;
import elfville.protocol.Response;
import elfville.protocol.Response.Status;
import elfville.protocol.VoteRequest;
import elfville.protocol.models.SerializablePost;

public class CentralBoardTest extends TestBase {

	@Test
	// calls the tests in the proper order
	public void orderTests() throws IOException {
		test1post();
		test2get();
		test3SingleVote();
		test4VoteTwice();
		test5DeletePost();
		test6VoteOrderAndProfiles();
	}

	// @Test
	// clientNum clients create clientNum posts. Check if all posts succeeded.
	public void test1post() throws IOException {
		System.out.println("CENTRAL BOARD TEST 1!");
		for (int i = 0; i < clientNum; i++) {
			String title = "title-" + i;
			String content = "content-" + i;
			PostCentralBoardRequest req = new PostCentralBoardRequest(title,
					content);
			Response resp = socketControllers.get(i).send(req);
			// System.out.println("test1post() " + i + " " +
			// resp.status.toString());
			assertEquals(resp.status, Status.SUCCESS);
		}
	}

	// @Test
	// One single client gets all posts from the central board. The returned
	// posts should be the
	// same as those that were inserted earlier, ordered by created dates.
	public void test2get() throws IOException {
		System.out.println("CENTRAL BOARD TEST 2!");
		CentralBoardRequest req = new CentralBoardRequest();
		CentralBoardResponse resp = socketControllers.get(0).send(req);
		assertEquals(resp.status, Status.SUCCESS);

		for (int i = clientNum - 1; i > 0; i--) {
			// SerializablePost post = resp.posts.get(clientNum - i);
			SerializablePost post = resp.posts.get(clientNum - i - 1);
			// the latest comes first
			System.out.println("get " + i);
			System.out.println("title is: " + post.title);
			System.out.println("content is: " + post.content);
			System.out.flush();
			assertEquals("title-" + i, post.title);
			assertEquals("content-" + i, post.content);
		}

	}

	// @Test
	// A single client votes either Upsock or Downsock on posts.
	// Check if SUCCESS is returned. Check if the returned posts get
	// the right number of upsock/downsock.
	public void test3SingleVote() throws IOException {
		CentralBoardRequest req = new CentralBoardRequest();
		CentralBoardResponse resp = socketControllers.get(0).send(req);
		assertEquals(resp.status, Status.SUCCESS);

		System.out.println("CENTRAL BOARD TEST 3!");
		for (int i = 0; i < clientNum; i++) {
			SerializablePost post = resp.posts.get(i);
			VoteRequest voteReq = new VoteRequest(post.modelID, i / 2 == 0);
			Response voteRes = socketControllers.get(0).send(voteReq);
			assertTrue(voteRes.isOK());
		}

		resp = socketControllers.get(0).send(req);
		assertEquals(resp.status, Status.SUCCESS);

		for (int i = 0; i < clientNum; i++) {
			SerializablePost post = resp.posts.get(i);
			if (i / 2 == 0) {
				assertEquals(1, post.upvotes);
			} else {
				assertEquals(1, post.downvotes);
			}
		}
	}

	// @Test
	// Test if the same client can vote twice
	public void test4VoteTwice() throws IOException {
		System.out.println("CENTRAL BOARD TEST 4!");
		CentralBoardRequest req = new CentralBoardRequest();
		CentralBoardResponse resp = socketControllers.get(0).send(req);
		assertEquals(resp.status, Status.SUCCESS);

		for (int i = 0; i < clientNum; i++) {
			SerializablePost post = resp.posts.get(i);
			VoteRequest voteReq = new VoteRequest(post.modelID, i / 2 == 0);
			Response voteRes = socketControllers.get(0).send(voteReq);
			assertFalse(voteRes.isOK());
		}

		for (int i = 0; i < clientNum; i++) {
			SerializablePost post = resp.posts.get(i);
			VoteRequest voteReq = new VoteRequest(post.modelID, i / 2 != 0);
			Response voteRes = socketControllers.get(0).send(voteReq);
			assertFalse(voteRes.isOK());
		}

	}

	// @Test
	// Clients delete their posts. Check SUCCEED
	public void test5DeletePost() throws IOException {
		CentralBoardRequest req = new CentralBoardRequest();
		CentralBoardResponse resp = socketControllers.get(0).send(req);
		assertEquals(resp.status, Status.SUCCESS);

		System.out.println("CENTRAL BOARD TEST 5!");
		for (int i = 0; i < clientNum; i++) {
			SerializablePost post = resp.posts.get(clientNum - i - 1);
			System.out.println("deleting post is: " + post.title);
			DeleteCentralBoardRequest deleteReq = new DeleteCentralBoardRequest(
					post);

			Response deleteRes = socketControllers.get(i).send(deleteReq);

			System.out.println("delete::: " + i + " " + post.elfModelID + " "
					+ post.myPost + " " + deleteRes.status.toString());
			assertTrue(deleteRes.isOK());
		}
	}

	// @Test
	// Multiple clients vote on different posts. Check if the returned posts are
	// ordered correctly.
	public void test6VoteOrderAndProfiles() throws IOException {
		test1post();

		CentralBoardRequest req = new CentralBoardRequest();
		CentralBoardResponse resp = socketControllers.get(0).send(req);
		assertTrue(resp.isOK());

		System.out.println("CENTRAL BOARD TEST 6!");
		// Vote in the descending order, i.e. vote clientNum gets
		for (int i = 0; i < clientNum; i++) {
			for (int k = i; k < clientNum; k++) {
				VoteRequest voteReq = new VoteRequest(
						resp.posts.get(k).modelID, true);
				Response voteRes = socketControllers.get(i).send(voteReq);
				System.out.println("voted once: " + voteRes.status.toString());
				assertTrue(voteRes.isOK());
			}
		}

		req = new CentralBoardRequest();
		resp = socketControllers.get(0).send(req);
		assertEquals(resp.status, Status.SUCCESS);

		for (int i = 0; i < clientNum; i++) {
			SerializablePost post = resp.posts.get(i); // reordered now. the
														// first comes first
			System.out.println(post.title);
			System.out.println(post.content);
			ProfileRequest elfreq = new ProfileRequest(post.elfModelID);
			ProfileResponse elfresp = socketControllers.get(i).send(elfreq);
			assertEquals(elfresp.status, Status.SUCCESS);
			if (i != 0) {
				assertEquals(WelcomeScreenTest.descriptions.get(i),
						elfresp.elf.description);
			}
			assertEquals(clientNum - i, elfresp.elf.numSocks);

			assertEquals("title-" + i, post.title);
			assertEquals("content-" + i, post.content);
		}

	}
}
