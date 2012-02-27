package testcases;

import java.io.IOException;

import org.junit.Test;

import elfville.client.SocketController;
import elfville.server.Server;

public class ServerStart {

	public class ServerThread extends Thread {
		@Override
		public void run() {
			String args[]= new String[0];
			try {
				Server.main(args);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Test
	public void test() throws IOException, InterruptedException {
		new ServerThread().start();
		Thread.sleep(500); // sleep for 0.5 second to wait for the server start
		SocketController.initialize();
	}

}
