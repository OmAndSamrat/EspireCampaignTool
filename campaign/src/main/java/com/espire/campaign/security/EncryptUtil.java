package com.espire.campaign.security;
import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author om.pandey
 *
 */
public class EncryptUtil {

	private static final String ALGO = "AES";
	private static final byte[] keyValue = new byte[] {'T', 'h', 'e', 'B', 'e', 's', 't','S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };
	
	private static Key generateKey() {
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}
	
	public static String encrypt(String data) throws Exception {
		Key key = generateKey();
		Cipher cipher = Cipher.getInstance(ALGO);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] bytes = cipher.doFinal(data.getBytes());
		String encryptedVal = Base64.getEncoder().encodeToString(bytes);
		return encryptedVal;
	}
	
	public static String decrypt(String data) {
		try {
			Key key = generateKey();
			Cipher cipher = Cipher.getInstance(ALGO);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] base64DecodedVal = Base64.getDecoder().decode(data);
			byte[] decodedBytes = cipher.doFinal(base64DecodedVal);
			return new String(decodedBytes);
		} catch(Exception ex) {
			System.out.println("Error in password conversion");
			return null;
		}
	}
}
