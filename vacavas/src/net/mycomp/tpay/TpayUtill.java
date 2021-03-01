package net.mycomp.tpay;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

public class TpayUtill {
	
	private static final Logger logger = Logger.getLogger(TpayController.class);
	public static String TIMESTAMP = getCurrentTimeStamp();
	//private static final Logger LOGGER = LoggerFactory.getLogger(TpayUtill.class);

	public static String hmacSHA256(String message, byte[] key) {
		MessageDigest sha256 = null;
		try {
			sha256 = MessageDigest.getInstance("SHA-256");
		} catch (Exception e) {
		}
		if (key.length > 64) {
			sha256.update(key);
			key = sha256.digest();
			sha256.reset();
		}
		byte block[] = new byte[64];
		for (int i = 0; i < key.length; ++i)
			block[i] = key[i];
		for (int i = key.length; i < block.length; ++i)
			block[i] = 0;
		for (int i = 0; i < 64; ++i)
			block[i] ^= 0x36;
		sha256.update(block);
		try {
			sha256.update(message.getBytes("UTF-8"));
		} catch (Exception e) {
		}
		byte[] hash = sha256.digest();
		sha256.reset();
		for (int i = 0; i < 64; ++i)
			block[i] ^= (0x36 ^ 0x5c);
		sha256.update(block);
		sha256.update(hash);
		hash = sha256.digest();
		char[] hexadecimals = new char[hash.length * 2];
		for (int i = 0; i < hash.length; ++i) {
			for (int j = 0; j < 2; ++j) {
				int value = (hash[i] >> (4 - 4 * j)) & 0xf;
				char base = (value < 10) ? ('0') : ('a' - 10);
				hexadecimals[i * 2 + j] = (char) (base + value);
			}
		}
		return new String(hexadecimals);
	}

	private static String getCurrentTimeStamp() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.format(date);
	}

	public static String CalculateDigest(String publicKey, String message, String privateKey)
    {
        try 
        {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(privateKey.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] digest = sha256_HMAC.doFinal(message.getBytes());
            String result = publicKey + ":" + new BigInteger(1, digest).toString(16);
            logger.info("DIGEST:"+result);
            return result;
        } 
		
		catch(Exception ex){
			throw new RuntimeException(ex);
		}
    }

	/*
	 * public static void main(String args[]) throws Exception {
	 * 
	 * String message = TIMESTAMP + "Z" + TpayConstant.LANG + TpayConstant.THEME;
	 * 
	 * System.out.println("  MESSAGE " + message); byte[] keyBytes =
	 * TpayConstant.SECRET_KEY.getBytes();
	 * 
	 * String digest = hmacSHA256(message, keyBytes); System.out.println(" DIGEST "
	 * + TpayConstant.PUBLIC_KEY + ":" + digest); }
	 */
	public static void main(String[] args) {
		System.out.println(CalculateDigest(TpayConstant.PUBLIC_KEY,
				TpayConstant.WELCOME_MESSAGE_SMS_ARB.replaceAll("<msisdn>", "201558802080")
				.replaceAll("<lang>", "2")
				.replaceAll("<price>", "2")
				.replaceAll("<billing_sequence>", "DAY")
				.replaceAll("<unsub_keyword>", "STOP GMP")
				.replaceAll("<shortcode>", "4041")
				+"201558802080"+"60204",TpayConstant.PRIVATE_KEY));
	}
	
}
