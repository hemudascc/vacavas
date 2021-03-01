package net.util;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {

	private static final Logger logger = Logger.getLogger(JsonMapper.class);

	private static ObjectMapper mapper = new ObjectMapper();
	static{
		//mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
	}
	
	public static String getObjectToJson(Object obj){
		String jsonInString=null;
		try {
			 jsonInString = mapper.writeValueAsString(obj);
		} catch (Exception ex) {
			logger.error("getObjectToJson:: ",ex);
			jsonInString=null;
		}
		return jsonInString;
	}
	
	
	public static  <T> T getJsonToObject(String jsonInString,Class<T> cl){
		
		try {
			return mapper.readValue(jsonInString, cl);
		} catch (Exception ex) {
			logger.error("getObjectToJson:: ",ex);
			jsonInString=null;
		}
		return null;
	}
}
