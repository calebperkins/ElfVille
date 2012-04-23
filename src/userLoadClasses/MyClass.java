package userLoadClasses;

import java.lang.reflect.InvocationTargetException;

import elfville.protocol.CentralBoardRequest;
import elfville.protocol.PostCentralBoardRequest;
import elfville.protocol.Request;
import elfville.server.classloader.API;

public class MyClass {

	public static void main(String [] args) throws InterruptedException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		// System.out.println(MyClass.class.getClassLoader().getParent().loadClass("loader.MainRun"));
		String title = "Paul awesome post";
		String content = "Hello TAs";
		PostCentralBoardRequest req = new PostCentralBoardRequest(title,
				content);
		API.addRequest(req);
		
		// System.getSecurityManager();

		// MyClass.class.getClassLoader().getParent().loadClass("loader.MainRun").getMethod("addElement", null).invoke(null, null);
		// MainRun.addElement();
	}
}
