package elfville.server.controller;


import elfville.protocol.Response.Status;
import elfville.protocol.SignInRequest;
import elfville.protocol.SignInResponse;
import elfville.protocol.SignUpRequest;
import elfville.protocol.SignUpResponse;
import elfville.server.CurrentUserProfile;
import elfville.server.model.*;

/* 
 * Controls sign in, sign up
 */
public class AuthenticationControl extends Controller {
	
	public static SignInResponse signIn(SignInRequest r, CurrentUserProfile currentUser) {
		SignInResponse outM;
		User user = database.userDB.findUserByUsername(r.username);
		if(user != null){
			currentUser.setCurrentUserId(user.getModelID());
			outM = new SignInResponse(Status.SUCCESS, "Welcome :)");
		} else {
			outM = new SignInResponse(Status.FAILURE, "Username not found/incorrect.");
		}
		return outM;
	}

	public static SignUpResponse signUp(SignUpRequest inM, CurrentUserProfile currentUser) {
		System.out.println("Sign up is called!");
		SignUpResponse outM;
		User user = database.userDB.findUserByUsername(inM.username);
		Elf elf;
		if (user != null){ 
			//username is taken
			outM = new SignUpResponse(Status.FAILURE, "The username is already taken");
			System.out.println("The username is already taken");
		} else {
			elf = new Elf();
			elf.setElfName(inM.username);
			elf.setDescription(inM.description);
			database.elfDB.insert(elf);
			user= new User();
			user.setElf(elf);
			//user.setPassword("lolskates");  //TODO: password
			user.setUsername(inM.username);
			database.userDB.insert(user);
			//sign the user in
			currentUser.setCurrentUserId(user.getModelID());
			
			System.out.println("sign up success");
			outM= new SignUpResponse(Status.SUCCESS, "word");
		}
		return outM;
	}
	
}
