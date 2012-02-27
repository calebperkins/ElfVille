package elfville.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import elfville.protocol.*;

/**
 * Generic class for sending socket requests. Most methods are static. Why? Why
 * not?
 * 
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

	public static CentralBoardResponse send(CentralBoardRequest req)
			throws IOException {
		return (CentralBoardResponse) write(req);
	}

	public static Response send(SignInRequest req) throws IOException {
		return write(req);
	}

	public static Response send(SignUpRequest req) throws IOException {
		return write(req);
	}

	public static Response send(PostCentralBoardRequest req) throws IOException {
		return write(req);
	}

	public static Response send(PostClanBoardRequest req) throws IOException {
		return write(req);
	}

	public static Response send(VoteRequest req) throws IOException {
		return write(req);
	}

	public static ClanBoardResponse send(ClanBoardRequest req)
			throws IOException {
		return (ClanBoardResponse) write(req);
	}

	public static Response send(CreateClanRequest req) throws IOException {
		return write(req);
	}

	public static ClanListingResponse send(ClanListingRequest req)
			throws IOException {
		return (ClanListingResponse) write(req);
	}

	public static Response send(ModifyClanRequest req) throws IOException {
		return write(req);
	}
}
