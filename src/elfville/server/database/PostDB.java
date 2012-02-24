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
	
	public Post findPostByModelID(int modelID) {
		for (Post post : posts) {
			if (post.getModelID() == modelID) {
				return post;
			}
		}
		return null;
	}

	// Only the elf's posts on the central board will be returned
	public List<Post> findCentralPostsByElf(Elf elf) {
		List<Post> matchedPosts = new ArrayList<Post>();
		for (Post post : posts) {
			if (post.getElf() == elf) {
				matchedPosts.add(post);
			}
		}
		return matchedPosts;
	}
	
	// return all public posts on the Central Board.
	public List<Post> getCentralPosts() {
		return posts;
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
