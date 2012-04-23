package elfville.server.classloader;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

public class CustomSecurityManager extends SecurityManager {
	private Object secret;
	private List<String> allowedPerms;
	private String[] allowedPermString= {"java.io.FilePermission", "java.util.PropertyPermission"};

	public CustomSecurityManager(Object pass) {
		super();
		secret = pass; 
	}

	public void disable(Object pass) {
		if (pass == secret) secret = null;
	}

	// ... override checkXXX method(s) here.
	// Always allow them to succeed when secret==null

	public void checkPermission(Permission perm) {
		if (secret == null) {
			return;
		}
		System.out.println("Permission check called");
		System.out.println(perm.getActions());
		System.out.println(perm.getName());
		System.out.println(perm.getClass());
		System.out.println(perm);
		//erm.equals(new RuntimePermission("createClassLoader")) ||
		//perm.equals(new RuntimePermission("setSecurityManager")) ||
		if (perm.getClass().toString().equals("class java.io.FilePermission") ||
				perm.getClass().toString().equals("class java.util.PropertyPermission")
				) {
			return;
		}
		throw new SecurityException();

	}
}

