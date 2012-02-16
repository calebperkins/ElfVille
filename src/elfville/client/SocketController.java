package elfville.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
	
	public static void initialize() throws Exception {
		socket = new Socket("localhost", 8444);
		//socket.setSoTimeout(5000);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		
		// Just for testing, remove me later
		System.out.println(send(new GetCentralBoardRequest()).secret);
	}
	
	private static ResponseMessage write(Message req) throws IOException {
		out.writeObject(req);
		out.flush();
		try {
			return (ResponseMessage) in.readObject();
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	public static GetCentralBoardResponse send(GetCentralBoardRequest req) throws IOException {
		return (GetCentralBoardResponse) write(req);
	}
}
