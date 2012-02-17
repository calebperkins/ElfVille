package elfville.server.database;

import java.util.ArrayList;

import java.util.List;

import elfville.server.model.*;

public class PostDB extends DB {
	
	private List<Post> posts;
	
	public PostDB() {
		posts = new ArrayList<Post>();
	}
	
	public void insert(Post post) {
		posts.add(post);
	}
	
	public void delete(Post post) {
		posts.remove(post);
	}

	// Only the elf's posts on the central board will be returned
	public List<Post> findCentralPostsByElf(Elf elf) {
		List<Post> matchedPosts = new ArrayList<Post>();
		for (Post post : posts) {
			if (post.getElf() == elf && post.getClan() == null) {
				matchedPosts.add(post);
			}
		}
		return matchedPosts;
	}
	
	public List<Post> findPostsByClan(Clan clan) {
		List<Post> matchedPosts = new ArrayList<Post>();
		for (Post post : posts) {
			if (post.getClan() == clan){
				matchedPosts.add(post);
			};
		}
		return matchedPosts;
	}
	
	// Debugging function
	public void printPosts() {
		for (Post p : posts) {
			System.out.println(p.getContent());
		}
	}
	
	// auto generated getters and setters	
	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

}
