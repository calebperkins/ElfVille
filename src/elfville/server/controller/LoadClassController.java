package elfville.server.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import elfville.protocol.LoadClassRequest;
import elfville.protocol.Request;
import elfville.protocol.Response;
import elfville.server.CurrentUserProfile;
import elfville.server.Server;
import elfville.server.classloader.Holder;
import elfville.server.classloader.QueueClassLoader;

public class LoadClassController extends Controller {
	public static String postLoadMessage = null;
	public static Object postLoadMessageLock = new Object(); 

	public static Response load(LoadClassRequest r,
			CurrentUserProfile currentUser) {

		if (!Request.USERCLASSLOAD) {
			return new Response();
		}
		// save the file stored inside r locally to userLoadClasses

		FileOutputStream fos;
		try {
			String[] paths = r.filepath.split("/");

			String fileName = "userclasses/userLoadClasses/" + paths[paths.length-1];

			fos = new FileOutputStream(fileName);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			bos.write(r.fileBytes, 0, r.fileBytes.length);
			bos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO: encrypt user id!
		String token = "" + currentUser.getCurrentUserId();
		System.out.println("!!!!!!!" + r.fileName + "!!!!!!");
		QueueClassLoader.startNewThread(r.fileName, token);

		Response res = new Response();
		synchronized(postLoadMessageLock) {
			while (postLoadMessage == null) {
				try {
					postLoadMessageLock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					postLoadMessage = e.getMessage();
				}
			}

			res.message = postLoadMessage;
			postLoadMessage = null;
		}
		return res;
	}
}