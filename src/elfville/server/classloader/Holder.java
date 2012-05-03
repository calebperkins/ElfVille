package elfville.server.classloader;

import java.util.ArrayList;
import java.util.Arrays;


import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import elfville.protocol.Request;
import elfville.protocol.Response;
import elfville.server.CurrentUserProfile;
import elfville.server.classloader.*;

public class Holder {
	// public LinkedBlockingQueue<String> apiQueue = new LinkedBlockingQueue<String>();
	// private ConcurrentLinkedQueue<Request> apiQueue = new ConcurrentLinkedQueue<Request>();
	// private ArrayList<String> apiQueue = new ArrayList<String>();

	public Request requestHolder;
	public Object requestHolderLock = new Object();
	public Response responseHolder;
	public Object responseHolderLock = new Object();
	public CurrentUserProfile currentUserProfile;
	
	public Response processRequest(Request req) throws InterruptedException {
		Response resp = null;
		synchronized (requestHolderLock) {
			requestHolder = req;
			requestHolderLock.notify();
		}

		synchronized (responseHolderLock) {
			while (responseHolder == null) {
				responseHolderLock.wait();
			}

			resp = responseHolder;
			responseHolder = null;
		}
		return resp;

	}

}
