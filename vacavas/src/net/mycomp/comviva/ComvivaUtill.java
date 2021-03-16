package net.mycomp.comviva;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.Key;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.util.MUtility;

@Component
public class ComvivaUtill {
	
	private static final Logger logger = Logger.getLogger(ComvivaUtill.class);
	
	@Value("${comviva.jks.file.path}")
	private String comvivaJKSFilePath;
	
	public String encrypt(String queryStr) {
		String encyptedQueryStr="";
		try{
			String checksum ="";
			String secretEncriptionKeyAlias="com";
			String secretEncriptionKeyStorePassword="com@123";
			String secretEncriptionKeyPassword="com@123";
			String cpId="VacaStudio";
			Mac sha256HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secretkey = new SecretKeySpec(cpId.getBytes(), "HmacSHA256");
			sha256HMAC.init(secretkey);
			checksum=URLEncoder.encode(Base64.encodeBase64String(sha256HMAC.doFinal(queryStr.getBytes())),"utf-8");
			File file = new File(comvivaJKSFilePath);
			InputStream keystoreStream = new FileInputStream(file); 
			KeyStore keystore = KeyStore.getInstance("JCEKS");
			keystore.load(keystoreStream, secretEncriptionKeyStorePassword.toCharArray()); 
			Key key = keystore.getKey(secretEncriptionKeyAlias, secretEncriptionKeyPassword.toCharArray());
			keystoreStream.close();
			IvParameterSpec iv = new IvParameterSpec(new byte[16]);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
			cipher.init(Cipher.ENCRYPT_MODE, key,iv); 		     
			byte[] encryptedMessageInBytes = cipher.doFinal(queryStr.getBytes()); 
			String encriptedRequestParam=Base64.encodeBase64String(encryptedMessageInBytes);
			encyptedQueryStr="CpId="+MUtility.urlEncoding(cpId)
			+"&requestParam="+encriptedRequestParam
			+"&checksum="+checksum;
		}catch(Exception ex){
			logger.error("Exception:: ",ex);
		}
		return encyptedQueryStr;
	}
}