package elfville.server.classloader;

import java.lang.reflect.InvocationTargetException;

import elfville.protocol.Request;


public class API {
	// Fun1 f1 = new Fun1();
	public static void addRequest(Request req) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException {
		
		// Class queueClass = API.class.getClassLoader().getParent().loadClass("elfville.server.classloader.LoadClassRequestQueue");
		Class partypes[] = new Class[1];
        partypes[0] = Request.class;
        LoadClassRequestQueue.addElement(req);
        
		// java.lang.reflect.Method addElementMethod = LoadClassRequestQueue.class.getMethod("addElement", partypes);
				// new Class[] {String[].class});
		// addElementMethod.invoke(null, req);
		
		// API.class.getClassLoader().getParent().loadClass("elfville.server.classloader.LoadClassRequestQueue").getMethod("addElement", null).invoke(null, null);
	}

}
