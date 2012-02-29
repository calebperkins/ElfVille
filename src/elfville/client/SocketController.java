package elfville.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import elfville.client.views.Board;
import elfville.protocol.*;

/**
 * Generic class for sending socket requests. Most methods are static. Why? Why
 * not?
 * 
 * @author Caleb Perkins
 * 
 */
public class SocketController {
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public interface SuccessFunction {
		public void handleRequestSuccess(Response resp);
	}
	
	public void sendRequest(Request req, Board board,
			String failMessage, SuccessFunction fun) {
		try {
			Response resp = board.getSocketController().send(req);
			if (resp.isOK()) {
				if (null == fun) {
					board.refresh();
				} else {
					fun.handleRequestSuccess(resp);
				}
			} else {
				board.getClientWindow().showError(
						resp.message, failMessage);
			}
		} catch (IOException e1) {
			board.getClientWindow().showConnectionError();
		}
	}

	public SocketController(String host, int port) throws UnknownHostException, IOException {
		socket = new Socket(host, port);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}

	private Response write(Request req) throws IOException {
		out.writeObject(req);
		out.flush();
		try {
			return (Response) in.readObject();
		} catch (ClassNotFoundException e) {
			return null; // shouldn't happen?
		}
	}

	public CentralBoardResponse send(CentralBoardRequest req)
			throws IOException {
		return (CentralBoardResponse) write(req);
	}

	public Response send(SignInRequest req) throws IOException {
		return write(req);
	}

	public Response send(SignUpRequest req) throws IOException {
		return write(req);
	}

	public Response send(PostCentralBoardRequest req) throws IOException {
		return write(req);
	}

	public Response send(PostClanBoardRequest req) throws IOException {
		return write(req);
	}

	public Response send(VoteRequest req) throws IOException {
		return write(req);
	}

	public ClanBoardResponse send(ClanBoardRequest req)
			throws IOException {
		return (ClanBoardResponse) write(req);
	}

	public Response send(CreateClanRequest req) throws IOException {
		return write(req);
	}

	public ClanListingResponse send(ClanListingRequest req)
			throws IOException {
		return (ClanListingResponse) write(req);
	}

	public Response send(ModifyClanRequest req) throws IOException {
		return write(req);
	}
	
	public Response send(Request req) throws IOException {
		return write(req);
	}
}
