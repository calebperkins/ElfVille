package testcases;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import javax.crypto.SecretKey;

import org.junit.Before;
import org.junit.Test;

import elfville.client.PublicKeyCipher;
import elfville.protocol.CentralBoardRequest;
import elfville.protocol.Request;
import elfville.protocol.SharedKeyCipher;

public class ClientEncryptionTest {
	PublicKeyCipher pk;
	SharedKeyCipher sk;

	@Before
	public void setUp() throws Exception {
		pk = new PublicKeyCipher("/Users/caleb/Desktop/elfville.pub.der");
		sk = new SharedKeyCipher();
	}

	@Test
	public void testGetNewSharedKey() throws Exception {
		SecretKey k1 = sk.getNewSharedKey();
		assertNotNull(k1);
		SecretKey k2 = sk.getNewSharedKey();
		assertNotNull(k2);
		assertNotSame(k1, k2);
	}

	@Test
	public void testEncryptWithServerKey() throws Exception {
		Request req = new CentralBoardRequest();
		assertNotNull(pk.encrypt(req));
	}

	@Test
	public void testEncryptWithSharedKey() throws Exception {
		sk.getNewSharedKey();
		Request req = new CentralBoardRequest();
		assertNotNull(sk.encrypt(req));
	}

}