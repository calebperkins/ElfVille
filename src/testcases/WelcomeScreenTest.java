package testcases;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.Test;

import elfville.protocol.Response;
import elfville.protocol.SignInRequest;
import elfville.protocol.SignUpRequest;
import elfville.protocol.Response.Status;

public class WelcomeScreenTest extends TestBase {

	@Test
	public void test1SignUp() throws UnknownHostException, IOException {
		SignUpRequest req = new SignUpRequest("user1");
		Response resp = socketControllers.get(0).send(req);
		System.out.println("signUpTest: " + resp.status.toString());
		assertEquals(resp.status, Status.SUCCESS);
	}

	@Test
	public void test2SignUpDuplicate() throws UnknownHostException, IOException {
		// should fail because we signed up the same username once above
		SignUpRequest req = new SignUpRequest("user1");
		Response resp = socketControllers.get(0).send(req);
		System.out.println(resp.status.toString());
		assertEquals(resp.status, Status.FAILURE);
	}

	@Test
	public void test3SignInWrong() throws UnknownHostException, IOException {
		SignInRequest req = new SignInRequest("user2");
		Response resp = socketControllers.get(0).send(req);
		System.out.println(resp.status.toString());
		assertEquals(resp.status, Status.FAILURE);
	}

	@Test
	public void test4SignIn() throws UnknownHostException, IOException {
		SignInRequest req = new SignInRequest("user1");
		Response resp = socketControllers.get(0).send(req);
		System.out.println(resp.status.toString());
		assertEquals(resp.status, Status.SUCCESS);
	}
}
