package net.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.persistence.AttributeConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;

public class JpaConverterJson implements AttributeConverter<Object, String> {

	  private final static ObjectMapper objectMapper = new ObjectMapper();
	  private static final Log logger = LogFactory.getLog(JpaConverterJson.class);
		
		static{
			objectMapper.configure(Feature.WRITE_DATES_AS_TIMESTAMPS, false);
			objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			
		}
		
	  @Override
	  public String convertToDatabaseColumn(Object meta) {
	    try {
	      return objectMapper.writeValueAsString(meta);
	    } catch (Exception ex) {
	    	logger.error("convertToDatabaseColumn::  " + ex);
	      return null;
	      // or throw an error
	    }
	  }

	  @Override
	  public Object convertToEntityAttribute(String dbData) {
	    try {
	      return objectMapper.readValue(dbData, Object.class);
	    } catch (Exception ex) {
	       logger.error("convertToEntityAttribute::" + dbData+", exception: "+ex);
	      return null;
	    }
	  }
	  
	  

	}