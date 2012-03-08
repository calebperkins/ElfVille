package elfville.server.controller;

import elfville.protocol.Response.Status;
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
		Response outM;
		User user = database.userDB.findUserByUsername(r.username);
		if (user != null) {
			currentUser.setCurrentUserId(user.getModelID());
			outM = new Response(Status.SUCCESS, "Welcome :)");
		} else {
			outM = new Response(Status.FAILURE, "Username not found/incorrect.");
		}
		return outM;
	}

	public static Response signUp(SignUpRequest inM,
			CurrentUserProfile currentUser) {
		System.out.println("Sign up is called!");
		Response outM;
		User user = User.get(inM.username);
		if (user != null) {
			// username is taken
			outM = new Response(Status.FAILURE, "The username is already taken");
			System.out.println("The username is already taken");
		} else {
			Elf elf = new Elf(inM.username, inM.description);
			elf.save();
			user = new User(elf, inM.username);
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
