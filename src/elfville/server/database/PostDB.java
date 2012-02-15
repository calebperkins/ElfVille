package elfville.server.database;

import java.util.ArrayList;
import java.util.List;

import elfville.server.model.Post;

public class PostDB extends DB {
	
	private List<Post> posts;
	
	public PostDB() {
		posts = new ArrayList<Post>();
	}
	
	public void insert(Post post) {
		posts.add(post);
	}
	
	// Debugging function
	public void printPosts() {
		for (Post p : posts) {
			System.out.println(p.getContent());
		}
	}
}
