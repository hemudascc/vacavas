package net.util;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.AttributeConverter;
import org.apache.log4j.Logger;


public class JpaStringListConverter implements AttributeConverter<List, String> {
	
	private static final Logger logger = Logger.getLogger(JpaStringListConverter.class);
	
	  private static Pattern pattern = Pattern.compile(",");
	  
	  @Override
	  public String convertToDatabaseColumn(List list) {
		  StringBuilder sb=new StringBuilder();
		  try{
			  for(Object s: list){
				  sb.append(s+",");
			  }
			 return sb.toString();
	   // return String.join(",", list.stream().map(String::valueOf).collect(Collectors.toList()).toString()); 	
		  }catch(Exception ex){
			  logger.warn("convertToDatabaseColumn:: ",ex);
		  }
		  return null;
	  }

	  @Override
	  public List convertToEntityAttribute(String joined) {
		try{
		return  pattern.splitAsStream(joined)
		                              .map(Integer::valueOf)
		                              .collect(Collectors.toList());
		  }catch(Exception ex){
			  logger.warn("convertToEntityAttribute"+ex);
		  }
		  return null;	   
	  }
	}
