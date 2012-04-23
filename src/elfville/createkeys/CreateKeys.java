package elfville.createkeys;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.security.AlgorithmParameters;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.KeySpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class CreateKeys {
	public static final int KEYSIZE = 128;

	public static void main(String[] args) throws Exception {
		// ask admin for password to create keys under
		Scanner scanner = new Scanner(System.in);
		System.out.println("Input admin password: ");
		String s = scanner.nextLine();
		char[] adminpwd = s.toCharArray();

		// use that password to create the cipher
		// from stack overflow (specifically the PBKDF2WithHmacSHA1)
		SecretKeyFactory factory = SecretKeyFactory
				.getInstance("PBKDF2WithHmacSHA1");
		byte[] salt = new byte[1];
		salt[0] = (byte) 9238;
		KeySpec spec = new PBEKeySpec(adminpwd, salt, 65536, KEYSIZE);
		SecretKey tmp = factory.generateSecret(spec);
		SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
		Cipher adminCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		adminCipher.init(Cipher.ENCRYPT_MODE, secret);

		/*
		 * MessageDigest md = MessageDigest.getInstance("SHA-256"); byte[] hash
		 * = md.digest(adminpwd.getBytes()); SecretKeySpec adminKey = new
		 * SecretKeySpec(hash, "AES"); Cipher adminDec =
		 * Cipher.getInstance("AES"); adminDec.init(Cipher.DECRYPT_MODE,
		 * adminKey);
		 */

		// store the initialization vector
		AlgorithmParameters params = adminCipher.getParameters();
		byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
		System.out.println("Input desired IV filename: ");
		s = scanner.nextLine();
		FileOutputStream fos = new FileOutputStream(s);
		DataOutputStream dos = new DataOutputStream(fos);
		dos.write(iv, 0, iv.length);
		dos.flush();
		dos.close();

		// create database AES private key
		KeyGenerator aesgen = KeyGenerator.getInstance("AES");
		aesgen.init(KEYSIZE);
		SecretKey aeskey = aesgen.generateKey();
		byte[] aesdec = aeskey.getEncoded();

		// encrypt AES private key under admin's generated cipher
		byte[] aesenc = adminCipher.doFinal(aesdec); // 128 bit block

		// put encrypted AES key into file
		System.out.println("Input desired db key filename: ");
		s = scanner.nextLine();
		fos = new FileOutputStream(s);
		dos = new DataOutputStream(fos);
		dos.write(aesenc, 0, aesenc.length);
		dos.flush();
		dos.close();

		/*
		 * FileInputStream fis = new FileInputStream(s); DataInputStream dis =
		 * new DataInputStream(fis); byte[] b = new byte[32]; dis.readFully(b);
		 * // fis.read(b); dis.close(); // fis.close(); spec = new
		 * PBEKeySpec(adminpwd, salt, 65536, KEYSIZE); tmp =
		 * factory.generateSecret(spec); secret = new
		 * SecretKeySpec(tmp.getEncoded(), "AES"); Cipher adminDec =
		 * Cipher.getInstance("AES/CBC/PKCS5Padding");
		 * adminDec.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
		 * byte[] keyBytes = adminDec.doFinal(b); new SecretKeySpec(keyBytes,
		 * "AES");
		 */

		// create database RSA keypair
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(4096);
		KeyPair kp = kpg.generateKeyPair();

		// write public key into file
		System.out.println("Input desired rsa public key filename: ");
		s = scanner.nextLine();
		fos = new FileOutputStream(s);
		dos = new DataOutputStream(fos);
		dos.write(kp.getPublic().getEncoded(), 0,
				kp.getPublic().getEncoded().length);
		dos.flush();
		dos.close();

		// encrypt RSA private key
		byte[] rsaenc = adminCipher.doFinal(kp.getPrivate().getEncoded());

		// write encrypted RSA key into file
		System.out.println("Input desired rsa private key filename: ");
		s = scanner.nextLine();
		fos = new FileOutputStream(s);
		dos = new DataOutputStream(fos);
		dos.write(rsaenc, 0, rsaenc.length);
		dos.flush();
		dos.close();

		System.out.println("Generation complete.");
	}
}
