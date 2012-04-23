package elfville.server.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import elfville.protocol.LoadClassRequest;
import elfville.protocol.Request;
import elfville.protocol.Response;
import elfville.server.CurrentUserProfile;
import elfville.server.Server;
import elfville.server.classloader.LoadClassRequestQueue;

public class LoadClassController extends Controller {

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

		LoadClassRequestQueue.startNewThread(r.fileName);
		Response res = new Response();
		return res;
	}
}
