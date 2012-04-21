package testcases;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.crypto.SecretKey;

import org.junit.Test;

import elfville.client.SocketController;
import elfville.protocol.Response;
import elfville.protocol.Response.Status;
import elfville.protocol.SignUpRequest;
import elfville.protocol.utils.SharedKeyCipher;

public class WelcomeScreenTest extends TestBase {

	public static ArrayList<String> descriptions = new ArrayList<String>();

	// create new shared key
	private SecretKey newSharedKey(SocketController sc) {
		SharedKeyCipher cipher;
		SecretKey shared_key = null;
		try {
			cipher = new SharedKeyCipher();
			shared_key = cipher.getNewSharedKey();
			sc.setCipher(cipher);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shared_key;
	}

	@Test
	public void test1SignUp() throws UnknownHostException, IOException {
		int currentUser = 0;
		String password = "what is my password";
		SocketController thisController = socketControllers.get(currentUser);
		SecretKey newSharedKey = newSharedKey(thisController);
		
		// FIXME: IV should not be null, but this is difficult to refactor
		SignUpRequest req = new SignUpRequest("user" + currentUser,
				password.toCharArray(), newSharedKey, 0, null, "I am an awesome elf");
		Response resp = thisController.send(req);
		// System.out.println("signUpTest: " + resp.status.toString());
		assertEquals(resp.status, Status.SUCCESS);
	}

	/*
	 * @Test public void test2SignUpDuplicate() throws UnknownHostException,
	 * IOException { // should fail because we signed up the same username once
	 * above String password = "what is my password"; SocketController
	 * thisController = socketControllers.get(0); SecretKey newSharedKey =
	 * newSharedKey(thisController); SignUpRequest req = new
	 * SignUpRequest("user0", password.toCharArray(), newSharedKey,
	 * "I am an awesome elf"); Response resp = thisController.send(req); //
	 * System.out.println(resp.status.toString()); assertEquals(resp.status,
	 * Status.FAILURE); }
	 */
	@Test
	public void test3MultiSignUp() throws UnknownHostException, IOException {
		descriptions.add("sdfkjdsf");
		for (int i = 1; i < clientNum; i++) {
			// System.out.println("kkkkkkk " + i);
			String password = "what is my password";
			SocketController thisController = socketControllers.get(i);
			SecretKey newSharedKey = newSharedKey(thisController);
			// FIXME: IV should not be null!
			SignUpRequest req = new SignUpRequest("user" + i,
					password.toCharArray(), newSharedKey, 0,
					null, "I am an awesome elf");
			descriptions.add("sdfkjdsf" + i);
			req.description = descriptions.get(i);
			Response resp = thisController.send(req);
			// System.out.println("multi signup: " + i + " " +
			// resp.status.toString());
			assertEquals(resp.status, Status.SUCCESS);
		}
	}
	/*
	 * @Test public void test4SignInWrong() throws UnknownHostException,
	 * IOException { String password = "what is my password"; SocketController
	 * thisController = socketControllers.get(0); SecretKey newSharedKey =
	 * newSharedKey(thisController); SignInRequest req = new
	 * SignInRequest("user", password.toCharArray(), newSharedKey);
	 * 
	 * Response resp = thisController.send(req); //
	 * System.out.println(resp.status.toString()); assertEquals(resp.status,
	 * Status.FAILURE); }
	 * 
	 * @Test public void test5SignIn() throws UnknownHostException, IOException
	 * { int currentUser = 0; String password = "what is my password";
	 * SocketController thisController = socketControllers.get(currentUser);
	 * SecretKey newSharedKey = newSharedKey(thisController); SignInRequest req
	 * = new SignInRequest("user" + currentUser, password.toCharArray(),
	 * newSharedKey);
	 * 
	 * Response resp = thisController.send(req); //
	 * System.out.println(resp.status.toString()); assertEquals(resp.status,
	 * Status.SUCCESS); }
	 */
}
