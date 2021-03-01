package net.mycomp.altruist;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.util.Base64Utils;

public class AltruistUtil {
	
	private static final String ALGO = "AES";
	private static final String secretKeyPhrase=AltruistConstant.encriptionKey;
	private static final String PREFIX= "971";
	public static String decrypt(String encryptedData, String secretKeyPhrase) throws Exception
	{
		Key key = new SecretKeySpec(secretKeyPhrase.getBytes(), AltruistUtil.ALGO);
		Cipher c = Cipher.getInstance(AltruistUtil.ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = Base64Utils.decodeFromString(encryptedData);
		byte[] decValue = c.doFinal(decordedValue);

		return new String(decValue);
	}
	
	public static String encrypt(String Data) throws Exception {
		
		Key key = new SecretKeySpec(secretKeyPhrase.getBytes(), ALGO);
    
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64Utils.encodeToString(encVal);
        return encryptedValue;
    }
	
	/*
	 * public static String generateMD5Hash(String password){ try{
	 * 
	 * MessageDigest md = MessageDigest.getInstance("MD5"); byte[] dataBytes = new
	 * byte[1024]; md.update(password.getBytes());
	 * 
	 * byte byteData[] = md.digest(); StringBuffer sb = new StringBuffer(); for (int
	 * i = 0; i < byteData.length; i++) { sb.append(Integer.toString((byteData[i] &
	 * 0xff) + 0x100, 16).substring(1)); }
	 * 
	 * System.out.println("Digest(in hex format):: " + sb.toString());
	 * 
	 * //convert the byte to hex format method 2 StringBuffer hexString = new
	 * StringBuffer(); for (int i=0;i<byteData.length;i++) { String
	 * hex=Integer.toHexString(0xff & byteData[i]); if(hex.length()==1)
	 * hexString.append('0'); hexString.append(hex); }
	 * System.out.println("Digest(in hex format):: " + hexString.toString());
	 * 
	 * return hexString.toString();
	 * 
	 * 
	 * }catch(Exception e){
	 * 
	 * } return "0";
	 * 
	 * }
	 */
	
	public static String formatMsisdn(String msisdn) {
		if(msisdn.startsWith(PREFIX) && msisdn.length()==12) {
			return msisdn;
		}else {
			return PREFIX+msisdn;
		}
	}
	
	/*
	 * public static void main(String [] ar) throws Exception{ String msisdn="999";
	 * 
	 * String result2=AltruistUtil.decrypt(msisdn, "DHDUFYlinsGDDSSs");
	 * System.out.println(result2); }
	 */
	
}
