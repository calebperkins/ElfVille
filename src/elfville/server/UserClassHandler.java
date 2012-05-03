package elfville.server;

import elfville.protocol.CentralBoardRequest;
import elfville.protocol.PostClassLoaderBoardRequest;
import elfville.protocol.Request;
import elfville.protocol.Response;
import elfville.server.classloader.Holder;
import elfville.server.classloader.QueueClassLoader;
import elfville.server.controller.LoadClassController;

public class UserClassHandler extends Thread {

	public void handleRequestQueue() throws InterruptedException {
		Request userReq;
		Holder holder;
		while(true) {
			userReq = null;
			holder = QueueClassLoader.dequeue();
			synchronized(holder.requestHolderLock) {
				while (holder.requestHolder == null) {
					holder.requestHolderLock.wait();
				}
				userReq = holder.requestHolder;
				holder.requestHolder = null;
			}

			if (userReq instanceof PostClassLoaderBoardRequest) {
				synchronized(LoadClassController.postLoadMessageLock) {
					LoadClassController.postLoadMessage = ((PostClassLoaderBoardRequest) userReq).message;
					LoadClassController.postLoadMessageLock.notify();
				}
			}

			synchronized(holder.responseHolderLock) {
				userReq.setChecksum();
				System.out.println(userReq.toString());

				holder.responseHolder = Routes.processRequest(userReq, holder.currentUserProfile);
				holder.responseHolderLock.notify();
			}

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
