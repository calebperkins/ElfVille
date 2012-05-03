package elfville.protocol.api;


import elfville.protocol.Request;

import elfville.protocol.Response;
import elfville.server.CurrentUserProfile;
import elfville.server.classloader.Holder;
import elfville.server.classloader.QueueClassLoader;


public class API {
	// Fun1 f1 = new Fun1();
	public CurrentUserProfile currentUserProfile;
	public API(String token) {
		// TODO encrypt token!
		currentUserProfile = new CurrentUserProfile();
		currentUserProfile.setCurrentUserId(Integer.parseInt(token));
	}
	
	public Response process(Request req) {
		Response resp = new Response();
		Holder holder = new Holder();
		holder.currentUserProfile = currentUserProfile;
		QueueClassLoader.enqueue(holder);
		try {
			resp = holder.processRequest(req);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			resp.message = e.getMessage();
		}
		return resp;
	}

}
