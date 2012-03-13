package testcases;

import static org.junit.Assert.*;

import javax.crypto.SecretKey;
import elfville.protocol.*;

import org.junit.Before;
import org.junit.Test;

import elfville.client.EncryptionService;

public class ClientEncryptionTest {
	EncryptionService es;

	@Before
	public void setUp() throws Exception {
		es = new EncryptionService("/Users/caleb/Desktop/elfville.pub.der");
	}

	@Test
	public void testGetNewSharedKey() throws Exception {
		SecretKey k1 = es.getNewSharedKey();
		assertNotNull(k1);
		SecretKey k2 = es.getNewSharedKey();
		assertNotNull(k2);
		assertNotSame(k1, k2);
	}

	@Test
	public void testEncryptWithServerKey() throws Exception {
		Request req = new CentralBoardRequest();
		assertNotNull(es.encryptWithServerKey(req));
	}

	@Test
	public void testEncryptWithSharedKey() throws Exception {
		es.getNewSharedKey();
		Request req = new CentralBoardRequest();
		assertNotNull(es.encryptWithSharedKey(req));
	}

}
