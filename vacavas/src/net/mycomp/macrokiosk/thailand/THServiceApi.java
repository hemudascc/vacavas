package net.mycomp.macrokiosk.thailand;

import java.sql.Timestamp;
import java.util.TimeZone;

import net.util.HTTPResponse;
import net.util.HttpURLConnectionUtil;
import net.util.MUtility;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("thServiceApi")
public class THServiceApi {

	private static final Logger logger = Logger
			.getLogger(THServiceApi.class.getName());
	
	
	
	private final String accessTokenUrl;
	
	private HttpURLConnectionUtil httpURLConnectionUtil; 
	
	@Autowired
	public THServiceApi(
			@Value("${thi.api.access.token.url}")String accessTokenUrl){
		
		this.accessTokenUrl=accessTokenUrl;
		ThiaConstant.yyyyMMddHHmmssAccessToken.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		httpURLConnectionUtil=new HttpURLConnectionUtil();
	}
	//http://mis.etracker.cc/THPSMSToken/GetToken?
	//AccessToken=<accesstoken>&Keyword=<keyword>&ShortCode=<shortcode>&DateTime=<datetime>
	
	
	public String getToken(THConfig thConfig,String token){
		
		try{
			
		String dateTime=ThiaConstant.yyyyMMddHHmmssAccessToken.
		format(new Timestamp(System.currentTimeMillis()));
		
		String accessToken=ThiaConstant.md5(thConfig.getUser().toUpperCase()
				+thConfig.getKeyword().toUpperCase()+thConfig.getShortcode()
				+dateTime+ThiaConstant.md5(thConfig.getPassword()).toUpperCase());
		
		String url=accessTokenUrl
		.replaceAll("<accesstoken>",MUtility.urlEncoding(accessToken))
		.replaceAll("<keyword>",MUtility.urlEncoding(thConfig.getKeyword()))
		.replaceAll("<shortcode>",MUtility.urlEncoding(thConfig.getShortcode()))
		.replaceAll("<datetime>",MUtility.urlEncoding(dateTime));
		
		
		HTTPResponse  httpResponse=httpURLConnectionUtil.invokeGetURL(url);
		logger.info("getToken::: token:: "+token+" ,url::::::::: "+url+" , httpResponse: "+httpResponse);
		
		if(httpResponse.getResponseCode()==200){
			return httpResponse.getResponseStr().split(",")[0];
		}
	}catch(Exception ex){
		logger.error("Exception:: ",ex);
	}
		return null;
	}
}
