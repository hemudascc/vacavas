package net.mycomp.timwe;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.springframework.web.util.UriUtils;

import net.util.MConstants;

public class TimweUtill {

	private static final Logger logger = Logger.getLogger(TimweUtill.class);

	public static String findAction(TimweNotification timweNotification) {
		String action=null;
		switch (timweNotification.getNotificationType()) {
		case TimweConstant.FIRST_CHARGE_NOTIFICATION:
			action=MConstants.ACT;
			break;
		case TimweConstant.OPTIN_NOTIFICATION:
			action=MConstants.GRACE;
			break;
		case TimweConstant.RENEWAL_NOTIFICATION:
			action=MConstants.RENEW;
			break;
		case TimweConstant.OPTOUT_NOTIFICATION:
			action=MConstants.DCT;
			break;
		default:
			break;
		}
		return action;
	}

	public static Double findAmmountByPriceId(String pricepointId) {

		if(TimweConstant.WEEKLY_PRICEPONT_ID.equals(pricepointId)) {
			return new Double(0.7);
		}
		if(TimweConstant.DAILY_PRICEPONT_ID.equals(pricepointId)) {
			return new Double(0.1);
		}
		return new Double(0);
	}


	public static String dcryptMsisdn(String encrypedMsisdn) {

		try {
			encrypedMsisdn=UriUtils.decode(encrypedMsisdn, "UTF-8");
			byte[] keyBinary = DatatypeConverter.parseBase64Binary(TimweConstant.PSK); 
			SecretKey secret = new SecretKeySpec(keyBinary, "AES");
			// encrypted msisdn
			byte[] bytes = DatatypeConverter.parseBase64Binary(encrypedMsisdn);
			// iv
			byte[] iv = DatatypeConverter.parseBase64Binary(TimweConstant.IV_PARAM_SPEC_KEY); 
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
			String msisdn = new String(cipher.doFinal(bytes), "UTF-8");
			return msisdn;
		} catch (Exception e) {
			logger.error("error while dcrypting msisdn"+encrypedMsisdn,e);
		}
		return "";
	}

	public static String getAuthCode(String preSharedKey){
		String presharedKey = preSharedKey; 
		String phrasetoEncrypt = TimweConstant.SERVICE_ID+"#"+System.currentTimeMillis(); 
		String encryptionAlgorithm = "AES/ECB/PKCS5Padding";
		String encrypted = "";
		try {
			Cipher cipher = Cipher.getInstance(encryptionAlgorithm);
			SecretKeySpec key = new SecretKeySpec(presharedKey.getBytes(), "AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			final byte[] crypted = cipher.doFinal(phrasetoEncrypt.getBytes());
			encrypted = Base64.getEncoder().encodeToString(crypted);
		} catch (Exception e) {
			System.out.println(e);
		}
		return encrypted;
	}
	
	public static void main(String str[]) {
		System.out.println(getAuthCode("tS5EngbxxqBSSDID"));
	}

}
