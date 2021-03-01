package net.mycomp.intarget;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.JAXBContext;

import net.util.MConstants;

import org.apache.log4j.Logger;
import org.eclipse.persistence.jaxb.JAXBContextFactory;

public interface IntargetConstant {

	 static final Logger logger = Logger
			.getLogger(IntargetConstant.class.getName());
	
	public final String INTRAGET_TRX_PREFIX="INTRAGET_TRX_PREFIX";
	
	public static final String INVALID_KEYWORD="INVALID_KEYWORD";
	public static Map<Integer,InTargetConfig> mapServiceIdTpInTargetConfig=new HashMap<Integer,InTargetConfig>();
	
	public static Map<String,InTargetConfig> mapServiceCodeToInTargetConfig=new HashMap<String,InTargetConfig>();
	
	public static AtomicInteger inTargetUssdTransIdAtomicInteger=new AtomicInteger(0);
	
	public final static String USSD_MSG="USSD_MSG";
	public final static String BILLLED_MSG="BILLLED_MSG";
	public final static String CONTENT_MSG="CONTENT_MSG"; 
	public static String getAction(String text,InTargetConfig inTargetConfig){
		
		if(text==null){
			return INVALID_KEYWORD;
		}
		text=text.toUpperCase();
		if(text.contains(inTargetConfig.getUnsubKeyword().toUpperCase())){
			return MConstants.DCT;
		}
		if(text.contains(inTargetConfig.getKeyword().toUpperCase())){
			return MConstants.ACT;
		}
		
	      return INVALID_KEYWORD;
	}
	
	
	public static String getMsg(String msg,InTargetConfig inTargetConfig){
		try{
			msg=msg
		.replaceAll("<servicename>", inTargetConfig.getServcieName())
		.replaceAll("<pricepoint>", Objects.toString(inTargetConfig.getPricePoint()))
		.replaceAll("<validity>", Objects.toString(inTargetConfig.getValidity()))
		.replaceAll("<portalurl>", Objects.toString(inTargetConfig.getPortalUrl()));
		}catch(Exception ex){
			logger.error("getMsg:: ",ex);
		}
		return msg;
	}
	
	
}
