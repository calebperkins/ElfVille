package elfville.server.classloader;

import java.util.ArrayList;


import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import elfville.protocol.Request;
import elfville.server.classloader.*;

public class LoadClassRequestQueue {
	// public static LinkedBlockingQueue<String> apiQueue = new LinkedBlockingQueue<String>();
	private static ConcurrentLinkedQueue<Request> apiQueue = new ConcurrentLinkedQueue<Request>();
	// private static ArrayList<String> apiQueue = new ArrayList<String>();
	
	public static void startNewThread(String newUserClassName) {
    	ClassLoaderThread t = new ClassLoaderThread(newUserClassName);
    	t.start();
	}
	public static void addElement(String s) {
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
