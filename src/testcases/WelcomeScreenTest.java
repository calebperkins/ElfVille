package testcases;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.Test;

import elfville.client.SocketController;
import elfville.protocol.Response;
import elfville.protocol.SignInRequest;
import elfville.protocol.SignUpRequest;
import elfville.protocol.Response.Status;

public class WelcomeScreenTest extends TestBase {

	@Test
	public void signUpTest() throws UnknownHostException, IOException {
		SignUpRequest req = new SignUpRequest("user1");
		Response resp = socketControllers.get(0).send(req);
		System.out.println(resp.status.toString());
		assertEquals(resp.status, Status.SUCCESS);
	}

	@Test
	public void signUpDuplicateTest() throws UnknownHostException, IOException {
		// should fail because we signed up the same username once above
		SignUpRequest req = new SignUpRequest("user1");
		Response resp = socketControllers.get(0).send(req);
		System.out.println(resp.status.toString());
		assertEquals(resp.status, Status.FAILURE);
	}

	@Test
	public void signInWrongTest() throws UnknownHostException, IOException {
		SignInRequest req = new SignInRequest("user2");
		Response resp = socketControllers.get(0).send(req);
		System.out.println(resp.status.toString());
		assertEquals(resp.status, Status.FAILURE);
	}

	@Test
	public void signInTest() throws UnknownHostException, IOException {
		SignInRequest req = new SignInRequest("user1");
		Response resp = socketControllers.get(0).send(req);
		System.out.println(resp.status.toString());
		assertEquals(resp.status, Status.SUCCESS);
	}
}
