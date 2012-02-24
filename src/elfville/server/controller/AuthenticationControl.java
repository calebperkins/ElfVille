package elfville.server.controller;

import elfville.protocol.Response.Status;
import elfville.protocol.SignInRequest;
import elfville.protocol.SignInResponse;
import elfville.protocol.SignUpRequest;
import elfville.protocol.SignUpResponse;
import elfville.server.model.*;

/* 
 * Controls sign in, sign up
 */
public class AuthenticationControl extends Controller {
	
	public static SignInResponse getPosts(SignInRequest inM) {
		SignInResponse outM;
		User user = database.userDB.findUserByUsername(inM.username);
		if(user != null){
			 outM = new SignInResponse(Status.SUCCESS, "word");
		} else {
			 outM = new SignInResponse(Status.FAILURE, "word");
		}
		return outM;
	}

	public static SignUpResponse getPosts(SignUpRequest inM) {
		SignUpResponse outM;
		User user = database.userDB.findUserByUsername(inM.username);
		if(user != null){
			//username is taken
			 outM = new SignUpResponse(Status.FAILURE, "word");
		} else {
			elf = new Elf();
			elf.setUserName(inM.username);
			elf.setDescription(inM.description);
			database.elfDB.insert(elf);
			outM= new SignUpResponse(Status.SUCCESS, "word");
		}
		return outM;
	}
	
}
