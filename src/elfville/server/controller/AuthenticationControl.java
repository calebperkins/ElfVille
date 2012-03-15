package elfville.server.controller;

import elfville.protocol.Response.Status;
import elfville.protocol.utils.Converter;
import elfville.protocol.SignInRequest;
import elfville.protocol.Response;
import elfville.protocol.SignUpRequest;
import elfville.server.CurrentUserProfile;
import elfville.server.model.*;

/* 
 * Controls sign in, sign up
 */
public class AuthenticationControl extends Controller {

	public static Response signIn(SignInRequest r,
			CurrentUserProfile currentUser) { 
		
		Response resp= new Response(Status.FAILURE);
		User user = database.userDB.findByUsername(r.getUsername());
		
		if (user == null) {
			return resp;
		}
		
		if(!r.getPassword().equals(user.getPassword())){
			return resp;
		}
		
		currentUser.setSharedKey(r.getSharedKey());
		currentUser.setNonce(Converter.byteArrayToInt(r.getNonce()));
		resp= new Response(Status.SUCCESS);
		currentUser.setCurrentUserId(user.getModelID());
		return resp;
	}

	public static Response signUp(SignUpRequest inM,
			CurrentUserProfile currentUser) {
		System.out.println("Sign up is called!");
		Response outM;
		User user = User.get(inM.getUsername());
		if (user != null) {
			// username is taken
			outM = new Response(Status.FAILURE, "The username is already taken");
			System.out.println("The username is already taken");
		} else {
			Elf elf = new Elf(inM.getUsername(), inM.description);
			elf.save();
			user = new User(elf, inM.getUsername());
			// user.setPassword("lolskates"); //TODO: password
			user.save();
			// sign the user in
			currentUser.setCurrentUserId(user.getModelID());

			System.out.println("sign up success");
			outM = new Response(Status.SUCCESS, "word");
		}
		return outM;
	}

}
