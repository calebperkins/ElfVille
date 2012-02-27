package elfville.server.controller;

import java.util.ArrayList;
import java.util.List;

import elfville.protocol.models.SerializableClan;
import elfville.protocol.models.SerializablePost;
import elfville.server.model.Clan;
import elfville.server.model.Post;

public class ControllerUtils extends Controller {

	public static ArrayList<SerializablePost> buildPostList(
			List<Post> boardPosts) {
		ArrayList<SerializablePost> out = new ArrayList<SerializablePost>();
		for (Post p : boardPosts) {
			SerializablePost s = p.toSerializablePost();
			out.add(s);
		}
		return out;
	}

	public static ArrayList<SerializableClan> buildBoardList(List<Clan> clans) {
		ArrayList<SerializableClan> out = new ArrayList<SerializableClan>();
		for (Clan q : clans) {
			SerializableClan c = q.getSerializableClan();
			// wipe the posts so that the user can't look at them
			c.posts = null;
			out.add(c);
		}
		return out;
	}

}
