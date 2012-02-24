package elfville.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import elfville.protocol.*;

/**
 * Generic class for sending socket requests. Most methods are static. Why? Why not?
 * @author Caleb Perkins
 *
 */
public class SocketController {
	private static Socket socket;
	private static ObjectOutputStream out;
	private static ObjectInputStream in;
	
	public static void initialize() throws UnknownHostException, IOException {
		socket = new Socket("localhost", 8444);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}
	
	private static Response write(Request req) throws IOException {
		out.writeObject(req);
		out.flush();
		try {
			return (Response) in.readObject();
		} catch (ClassNotFoundException e) {
			return null; // shouldn't happen?
		}
	}

	public static CentralBoardResponse send(CentralBoardRequest req) throws IOException {
		return (CentralBoardResponse) write(req);
	}
	
	public static SignInResponse send(SignInRequest req) throws IOException {
		return (SignInResponse) write(req);
	}
	
	public static SignUpResponse send(SignUpRequest req) throws IOException {
		return (SignUpResponse) write(req);
	}
	
	public static PostResponse send(PostRequest req) throws IOException {
		return (PostResponse) write(req);
	}
}
