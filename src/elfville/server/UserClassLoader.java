package elfville.server;

import java.security.SecureClassLoader;

public class UserClassLoader extends SecureClassLoader {

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		//SecurityManager sm = System.getSecurityManager();
		
		return super.findClass(name);
	}

}
