package elfville.server.classloader;

public class ClassLoaderThread extends Thread {
	private Object pass = new Object();
	private CustomClassLoader loader = new CustomClassLoader();
	private CustomSecurityManager sm = new CustomSecurityManager(pass);
	
	private String newLoadingClassName;
	private String userIdToken;
	
	public ClassLoaderThread(String newUserClassName, String userIdToken) {
		newLoadingClassName = newUserClassName;
		this.userIdToken = userIdToken;
	}
	
	public void run() {
		SecurityManager old = System.getSecurityManager();
		System.setSecurityManager(sm);
		try {
			runUntrustedCode();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sm.disable(pass);
		System.setSecurityManager(old);
	}
	
	private void runUntrustedCode() throws Exception {
		Class clsB64 = Class.forName(newLoadingClassName, true, loader);
		java.lang.reflect.Method main = clsB64.getMethod("main", 
				new Class[] {String.class});
		main.invoke(null, new Object[] {userIdToken});
	}
	
	
}