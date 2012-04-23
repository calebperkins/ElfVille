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
import elfville.server.classloader.*;

public class LoadClassRequestQueue {
	// public static LinkedBlockingQueue<String> apiQueue = new LinkedBlockingQueue<String>();
	private static ConcurrentLinkedQueue<Request> apiQueue = new ConcurrentLinkedQueue<Request>();
	// private static ArrayList<String> apiQueue = new ArrayList<String>();
	
	public static void startNewThread(String newUserClassName) {

		ExecutorService executor = Executors.newSingleThreadExecutor();
		try {
			executor.submit(new ClassLoaderThread(newUserClassName)).get(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executor.shutdown();
		
		
    	ClassLoaderThread t = new ClassLoaderThread(newUserClassName);
    	t.start();
	}
	
	public static void addElement(Request req) {
		apiQueue.add(req);
		System.out.println("apiQueue added 1 element");
		System.out.println(apiQueue.size());
	}
	
	public static int size() {
		return apiQueue.size();
	}
	
	public static boolean isEmpty() {
		return apiQueue.isEmpty();
	}
	
	public static Request poll() {
		return apiQueue.poll();
	}

}
