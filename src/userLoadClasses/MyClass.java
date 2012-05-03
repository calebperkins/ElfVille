package userLoadClasses;

import java.lang.reflect.InvocationTargetException;

import elfville.protocol.CentralBoardRequest;
import elfville.protocol.PostCentralBoardRequest;
import elfville.protocol.PostClassLoaderBoardRequest;
import elfville.protocol.Request;
import elfville.protocol.Response;
import elfville.protocol.api.API;

public class MyClass {

	public static void main(String arg) {
		// System.out.println(MyClass.class.getClassLoader().getParent().loadClass("loader.MainRun"));
		API api = new API(arg);
		String title = "Paul awesome post";
		String content = "Hello TAs";
		PostCentralBoardRequest req = new PostCentralBoardRequest(title,
				content);
		Response postCentralBoardResponse = api.process(req);

		PostClassLoaderBoardRequest postReq = new PostClassLoaderBoardRequest();
		postReq.message = "Im just testing to see if this works";
		api.process(postReq);
	}
}
