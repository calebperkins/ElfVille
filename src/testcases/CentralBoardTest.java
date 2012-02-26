package testcases;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;


import org.junit.Test;

import elfville.client.SocketController;
import elfville.protocol.*;
import elfville.protocol.Response.Status;
import elfville.protocol.models.SerializablePost;

public class CentralBoardTest {

	@Test
	public void test1post() throws IOException {
		for (int i = 0; i < 50; i++) {
			String title = "title-" + i;
			String content = "content-" + i;
			PostCentralBoardRequest req = new PostCentralBoardRequest(content, title);
			Response resp = (Response) SocketController.send(req);
			System.out.println(resp.status.toString());
			assertEquals(resp.status, Status.SUCCESS);
		}
	}

	@Test
	public void test2get() throws IOException {
		System.out.println("getTesting");
		CentralBoardRequest req = new CentralBoardRequest();
		CentralBoardResponse resp = (CentralBoardResponse) SocketController.send(req);
		System.out.println(resp.status.toString());
		assertEquals(resp.status, Status.SUCCESS);
		System.out.println(resp.posts.size());
		
		for (int i = 0; i < 50; i++) {
			SerializablePost post = resp.posts.get(i);
			System.out.println("what?");
			System.out.println(post.title);
			System.out.println(post.content);
			assertEquals("title-" + i, post.title);
			assertEquals("content-" + i, post.content);
		}
		
	}

}
