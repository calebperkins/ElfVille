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
	private final List<Post> centralPosts = Collections.synchronizedList(new ArrayList<Post>());

	public void insert(Post post) {
		idMap.put(post.getModelID(), post);
		if (post.clanID == 0)
			centralPosts.add(post);
	}

	public void delete(int i) {
		Post p = idMap.get(i);
		idMap.remove(i);
		if (p.clanID == 0) {
			centralPosts.remove(p);
		}
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
		List<Post> posts = new ArrayList<Post>();
		for (Post post : centralPosts) {
			if (post.getElf().equals(elf)) {
				posts.add(post);
			}
		}
		return posts;
	}

	/**
	 * Returns a list of central board posts sorted by socks.
	 * 
	 * @return
	 */
	public List<Post> getCentralPosts() {
		Collections.sort(centralPosts);
		return centralPosts;
	}

}
