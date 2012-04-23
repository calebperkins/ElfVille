package elfville.server;

import elfville.protocol.Request;
import elfville.protocol.Response;
import elfville.server.classloader.LoadClassRequestQueue;

public class UserClassHandler extends Thread {
	
	private CurrentUserProfile currentUser;
	
	UserClassHandler(CurrentUserProfile currentUser) {
		this.currentUser = currentUser;
	}

	public void handleRequestQueue() throws InterruptedException {
		while(true) {
			Thread.sleep(500);
			System.out.println("apiQueue length is: " + LoadClassRequestQueue.size());
			if (LoadClassRequestQueue.isEmpty()) {
				continue;
			}
			Request userReq = LoadClassRequestQueue.poll();
			System.out.println(userReq.toString());
			
			Response response = Routes.processRequest(userReq, currentUser);

		}
	}
	
	public void run() {
		try {
			handleRequestQueue();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
    public static void main(String[] args) throws InterruptedException {
    	Thread.sleep(500);
    	LoadClassRequestQueue.startNewThread();
    	// handleRequestQueue();
    }
    */
}
