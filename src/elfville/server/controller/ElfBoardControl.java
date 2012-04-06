package elfville.server.controller;

import elfville.protocol.ProfileRequest;
import elfville.protocol.ProfileResponse;
import elfville.protocol.Response;
import elfville.protocol.Response.Status;
import elfville.protocol.UpdateProfileRequest;
import elfville.protocol.models.SerializableElf;
import elfville.server.CurrentUserProfile;
import elfville.server.model.Elf;
import elfville.server.model.User;

public class ElfBoardControl extends Controller {

	public static ProfileResponse getProfile(ProfileRequest r,
			CurrentUserProfile currentUser) {
		ProfileResponse resp = new ProfileResponse(Status.FAILURE);

		User user = database.userDB.findUserByModelID(currentUser
				.getCurrentUserId());
		if (user == null) {
			return resp;
		}

		// check to make sure that we were sent modelID
		if (r.modelID == null) {
			return resp;
		}

		Elf elf = database.elfDB.findByEncryptedID(r.modelID);

		// check to see that the requested elf actually exists
		if (elf == null) {
			return resp;
		}

		SerializableElf profile = new SerializableElf();
		profile.description = elf.getDescription();
		profile.elfName = elf.getName();
		profile.centralBoardPosts = ControllerUtils.buildPostList(
				database.postDB.findCentralPostsByElf(elf), user.getElf());
		profile.numSocks = elf.getNumSocks();

		resp.status = Status.SUCCESS;
		resp.elf = profile;
		return resp;
	}

	public static Response updateProfile(UpdateProfileRequest r,
			CurrentUserProfile currentUser) {
		Response resp = new Response(Status.FAILURE);

		User user = database.userDB.findUserByModelID(currentUser
				.getCurrentUserId());
		if (user == null) {
			return resp;
		}

		Elf elf = user.getElf();

		// check to see that the requested elf actually exists
		if (elf == null) {
			return resp;
		}

		//make sure the description is not null
		if(r.description == null){
			return resp;
		}
		
		//make sure the description is not too long
		if(r.description.length() > UpdateProfileRequest.MAX_DESCRIPTION_SIZE){
			resp.message= "Description is longer than 250 characters";
			return resp;
		}

		resp.status = Status.SUCCESS;
		return resp;
	}

}
