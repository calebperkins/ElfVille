package userLoadClasses;

import java.lang.reflect.InvocationTargetException;

import elfville.protocol.CentralBoardRequest;
import elfville.protocol.Request;
import elfville.server.classloader.API;

public class MyClass {

	public static void main(String [] args) throws InterruptedException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		while(true) {
			Thread.sleep(100);
			String s = "Hi hottie";
			System.out.println(s);
			// System.out.println(MyClass.class.getClassLoader().getParent().loadClass("loader.MainRun"));
			Request req = new CentralBoardRequest();
			API.addRequest(req);
			// MyClass.class.getClassLoader().getParent().loadClass("loader.MainRun").getMethod("addElement", null).invoke(null, null);
			// MainRun.addElement();

		}
	}
}
