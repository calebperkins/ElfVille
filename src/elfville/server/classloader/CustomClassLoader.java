package elfville.server.classloader;
import java.io.*;

public class CustomClassLoader extends ClassLoader {
	private static final int BUFFER_SIZE = 8192;

	protected synchronized Class loadClass(String className, boolean resolve) 
			throws ClassNotFoundException {
		System.out.println("Loading class: " + className + ", resolve: " + resolve);

		// If the user uploaded code accesses anything in the server package (except
		// the current package), throw ClassNotFoundException.
		String[] classpath = className.replace('.','/').split("/"); 
		if (classpath.length > 1 &&
				classpath[0].equals("elfville") &&
				classpath[1].equals("server") &&
				classpath.length != 2 &&
				!classpath[2].equals("classloader")) {
			throw new ClassNotFoundException();
		}

		// 1. is this class already loaded?
		Class cls = findLoadedClass(className);
		if (cls != null) {
			return cls;
		}
		
		if (!(classpath.length > 1 && classpath[0].equals("userLoadClasses"))) {
			return super.loadClass(className, resolve);
		}
		
		// if (classpath.length > 0 && classpath[0].equals("elfville")) {
		// 	return super.loadClass(className, resolve);
		// }

		// 2. get class file name from class name
		String clsFile = className.replace('.', '/') + ".class";

		// 3. get bytes for class
		byte[] classBytes = null;
		try {
		    File myFile = new File("userclasses/" + clsFile);
			classBytes = new byte[(int) myFile.length()];
		    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
		    bis.read(classBytes, 0, classBytes.length);
			
			/*
			InputStream in = getResourceAsStream(clsFile);
			byte[] buffer = new byte[BUFFER_SIZE];
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int n = -1;
			while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
				out.write(buffer, 0, n);
			}
			classBytes = out.toByteArray();
			*/
		}
		catch (IOException e) {
			System.out.println("ERROR loading class file: " + e);
		}

		if (classBytes == null) {
			throw new ClassNotFoundException("Cannot load class: " + className);
		}

		// 4. turn the byte array into a Class
		try {
			cls = defineClass(className, classBytes, 0, classBytes.length);
			if (resolve) {
				resolveClass(cls);
			}
		}
		catch (SecurityException e) { 
			// loading core java classes such as java.lang.String
			// is prohibited, throws java.lang.SecurityException.
			// delegate to parent if not allowed to load class
			cls = super.loadClass(className, resolve);
		}

		System.out.println("load class end");
		return cls;
	}


	public static void main(String[] args) throws Exception {

		CustomSecurityManager sm = new CustomSecurityManager(null);
		// SecurityManager old = System.getSecurityManager();

		CustomClassLoader loader = new CustomClassLoader();
		System.setSecurityManager(sm);

		Class userUploadedClass = Class.forName("loader.MyClass", true, loader);

		// System.setSecurityManager(old);


		/*
		System.out.println("load class done?");
		System.out.println("Base64 class: " + clsB64);

		if (MyClass.class.equals(clsB64)) {
			System.out.println("Base64 loaded through custom loader is the same as that loaded by System loader.");
		}
		else {
			System.out.println("Base64 loaded through custom loader is NOT same as that loaded by System loader.");
		}
		 */
		// call the main method in Base64
		java.lang.reflect.Method main = userUploadedClass.getMethod("main", 
				new Class[] {String[].class});
		main.invoke(null, new Object[]{ new String[]{} });
	}

}