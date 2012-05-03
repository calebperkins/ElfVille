package elfville.server.classloader;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import elfville.protocol.Request;

public class QueueClassLoader {
	private static ConcurrentLinkedQueue<Holder> holderQueue = new ConcurrentLinkedQueue<Holder>();


	public static void startNewThread(String newUserClassName, String userIdToken) {

		/*
		ExecutorService executor = Executors.newSingleThreadExecutor();
		try {
			executor.submit(new ClassLoaderThread(newUserClassName, userIdToken)).get(5, TimeUnit.SECONDS);
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
*/

		ClassLoaderThread t = new ClassLoaderThread(newUserClassName, userIdToken);
		t.start();
	}

	public static void enqueue(Holder h) {
		holderQueue.add(h);
		synchronized(holderQueue) {
			if (holderQueue.size() == 1) {
				holderQueue.notify();
			}
		}
	}

	public static Holder dequeue() {
		synchronized(holderQueue) {
			while (holderQueue.isEmpty()) {
				try {
					holderQueue.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return holderQueue.poll();
	}
}
