package testcases;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.junit.Test;

import elfville.client.PublicKeyCipher;
import elfville.client.SocketController;
import elfville.server.Server;

public class ServerStart extends TestBase {

	public class ServerThread extends Thread {
		@Override
		public void run() {
			String args[] = new String[0];
			try {
				Server.main(args);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public class ClientThread extends Thread {
		@Override
		public void run() {
			try {
				socketControllers.add(new SocketController("localhost", 8444));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Test
	public void test() throws IOException, InterruptedException,
			GeneralSecurityException {
		new ServerThread().start();
		String arg2 = "elfville.pub.der";
		PublicKeyCipher.instance = new PublicKeyCipher(arg2);

		Thread.sleep(500); // sleep for 0.5 second to wait for the server start
		for (int i = 0; i < clientNum; i++) {
			new ClientThread().start();
		}
		Thread.sleep(500); // sleep for 0.5 second to wait for all clients to
							// start
	}

}
