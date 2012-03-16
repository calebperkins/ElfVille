package elfville.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SealedObject;

import elfville.client.views.Board;
import elfville.protocol.CentralBoardRequest;
import elfville.protocol.CentralBoardResponse;
import elfville.protocol.ClanBoardRequest;
import elfville.protocol.ClanBoardResponse;
import elfville.protocol.ClanListingRequest;
import elfville.protocol.ClanListingResponse;
import elfville.protocol.CreateClanRequest;
import elfville.protocol.ModifyClanRequest;
import elfville.protocol.PostCentralBoardRequest;
import elfville.protocol.PostClanBoardRequest;
import elfville.protocol.ProfileRequest;
import elfville.protocol.ProfileResponse;
import elfville.protocol.Request;
import elfville.protocol.Response;
import elfville.protocol.SignInRequest;
import elfville.protocol.SignUpRequest;
import elfville.protocol.VoteRequest;
import elfville.protocol.utils.SharedKeyCipher;

/**
 * Generic class for sending socket requests. *
 * 
 */
public class SocketController {
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private SharedKeyCipher cipher = null;
	private int current_shared_nonce = 0;

	/**
	 * Used to tell the sendRequest method how to respond to a success message
	 * from the server (e.g., how to create a new central board given that the
	 * server responded with the current contents a central board should
	 * display). Essentially a nasty but common hack to pass a function.
	 * 
	 */
	public interface SuccessFunction {
		public void handleRequestSuccess(Response resp);
	}

	public void sendRequest(Request req, Board board, String failMessage,
			SuccessFunction fun) {
		try {
			Response resp = board.getSocketController().send(req);
			if (resp.isOK()) {
				if (null == fun) {
					board.refresh();
				} else {
					fun.handleRequestSuccess(resp);
				}
			} else {
				board.getClientWindow().showError(resp.message, failMessage);
			}
		} catch (IOException e1) {
			board.getClientWindow().showConnectionError();
		}
	}

	public SocketController(String host, int port) throws UnknownHostException,
			IOException {
		socket = new Socket(host, port);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}

	public void setCipher(SharedKeyCipher c) {
		cipher = c;
	}

	private Response write(Request req) throws IOException {
		try {
			if ((req instanceof SignUpRequest) || (req instanceof SignInRequest)) {
				out.writeObject(PublicKeyCipher.instance.encrypt(req));
			} else {
				out.writeObject(cipher.encrypt(req));
			}
			out.flush();
			return cipher.decrypt((SealedObject) in.readObject());
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
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

	public ClanBoardResponse send(ClanBoardRequest req) throws IOException {
		return (ClanBoardResponse) write(req);
	}

	public Response send(CreateClanRequest req) throws IOException {
		return write(req);
	}

	public ClanListingResponse send(ClanListingRequest req) throws IOException {
		return (ClanListingResponse) write(req);
	}

	public Response send(ModifyClanRequest req) throws IOException {
		return write(req);
	}

	public Response send(Request req) throws IOException {
		return write(req);
	}

	public ProfileResponse send(ProfileRequest req) throws IOException {
		return (ProfileResponse) write(req);
	}
}
