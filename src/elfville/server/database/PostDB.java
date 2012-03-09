package elfville.server.database;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import elfville.server.SecurityUtils;
import elfville.server.model.*;

public class PostDB extends DB {
	private static final long serialVersionUID = -5939983651247977959L;
	private final ConcurrentHashMap<Integer, Post> idMap = new ConcurrentHashMap<Integer, Post>();

	public void insert(Post post) {
		idMap.put(post.getModelID(), post);
	}

	public void delete(int i) {
		idMap.remove(i);
	}

	public Post findByModelID(int modelID) {
		return idMap.get(modelID);
	}

	public Post findByEncryptedModelID(String encID) {
		int modelID = SecurityUtils.decryptStringToInt(encID);
		return findByModelID(modelID);
	}

	// Only the elf's posts on the central board will be returned
	public List<Post> findCentralPostsByElf(Elf elf) {
		List<Post> posts = new ArrayList<Post>(idMap.values());
		List<Post> matchedPosts = new ArrayList<Post>();
		for (Post post : posts) {
			if (post.getElf() == elf) {
				matchedPosts.add(post);
			}
		}
		return matchedPosts;
	}

	/**
	 * Returns a list of central board posts sorted by socks.
	 * 
	 * @return
	 */
	public List<Post> getCentralPosts() {
		List<Post> posts = new ArrayList<Post>(idMap.values());
		Collections.sort(posts);
		return posts;
	}

}
