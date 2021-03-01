package net.common.service;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import net.persist.bean.AdnetworkOperatorConfig;
import net.persist.bean.Operator;
import net.persist.bean.Product;
import net.util.MConstants;

@Service("redisCacheService")
public class RedisCacheService {

	private static final Logger logger = Logger
			.getLogger(RedisCacheService.class);

    public static final String  ADNETWORK_CAPPING_KEY="ADNETWORK_CAPPING_KEY";	
	public static final String  PRODUCT_HOURLY_CLICK_CAPPING_KEY="PRODUCT_HOURLY_CLICK_CAPPING_KEY";
	public static final String  PRODUCT_DAILY_CONVERSION_CAPPING_KEY="PRODUCT_DAILY_CONVERSION_CAPPING_KEY";
	
	public static final String  OPERATOR_HOURLY_CONVERSION_CAPPING_KEY="OPERATOR_HOURLY_CONVERSION_CAPPING_KEY";
	public static final String  OPERATOR_DAILY_CONVERSION_CAPPING_KEY="OPERATOR_DAILY_CONVERSION_CAPPING_KEY";
	
	public static String dateddMMYYYY=MConstants.sdfDDMMyyyy.format(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
	public static String dateddMMYYYYHH=MConstants.sdfDDMMyyyyHH.format(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
	
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	@Qualifier(value = "redisTemplateInt")
	private RedisTemplate<String, Integer> redisTemplateInt;

	@Value("${redis.eviction.time}")
	private Integer redisCahcheEvictionTime;

	@Value("${redis.object.eviction.time}")
	private Integer redisObjectEvictionTime;

	HashOperations<String, String, String> hashOps;
	SetOperations<String, String> setOps;
	ValueOperations<String, String> valueOps;
	ValueOperations<String, Integer> valueIntOps;
	ValueOperations<String, Object> valueObjectOps;

	
	@PostConstruct
	public void init() {

		hashOps = stringRedisTemplate.opsForHash();
		setOps = stringRedisTemplate.opsForSet();
		valueOps = stringRedisTemplate.opsForValue();
		valueObjectOps = redisTemplate.opsForValue();
		redisTemplateInt.setKeySerializer(new StringRedisSerializer());
		redisTemplateInt.setValueSerializer(new GenericToStringSerializer<Integer>(
				Integer.class));
		valueIntOps = redisTemplateInt.opsForValue();
	}

	public boolean putIntValue(String key,int value) {
		if(key==null){
			return false;
		}
		try{
		 valueIntOps.set(key, value,redisCahcheEvictionTime, TimeUnit.MINUTES);
		return true;
		}catch(Exception ex){
			
		}
		return false;
	}
	
	public boolean putIntValue(String key,int value,int evictionMinute) {
		if(key==null){
			return false;
		}
		try{
		 valueIntOps.set(key, value,evictionMinute, TimeUnit.MINUTES);
		return true;
		}catch(Exception ex){
			
		}
		return false;
	}

	public Integer getIntValue(String key) {
		if(key==null){
			return 0;
		}
		return valueIntOps.get(key);		
	}
	
	public Long getAndIcrementIntValue(String key,int value) {
		return valueIntOps.increment(key, value);	
	}
	
	 public Long getAndIcrementIntValue(String key,int value,int expireTime) {
   	  try{
   		   valueIntOps.setIfAbsent(key, 0);
   	       valueIntOps.getOperations().expire(key,expireTime, TimeUnit.MINUTES);
 		   return valueIntOps.increment(key, value);	
   	  }catch(Exception ex){
   		 logger.error("getAndIcrementIntValue::: exception ", ex);
   	  }
   	  return 0l;
 	}
    
	 
	
	public boolean putAllValue(String key, Map<String, String> map) {

		hashOps.putAll(key, map);
		return true;
	}

	public boolean putValue(String hashMapName, String key, String value) {

		hashOps.put(hashMapName, key, value);
		return true;
	}

	public String getValue(String hashMapName, List<String> key) {
		String value = hashOps.size(hashMapName) + "    "
				+ hashOps.multiGet(hashMapName, key);
		return value;
	}

	public String getValueString(String key) {
		String value = valueOps.get(key);
		return value;
	}

	public void putCacheValue(String key, int i) {
		long time=System.currentTimeMillis();
		if(key==null){
			return;
		}
		try {

			setOps.add(key, String.valueOf(i));
			stringRedisTemplate.expire(key, redisCahcheEvictionTime,
					TimeUnit.MINUTES);
			logger.error("Total time::putCacheValue "+(System.currentTimeMillis()-time));
		} catch (Exception ex) {
			logger.error("putCacheValue::: exception " + ex);
		}
	}

	public Set<String> getCacheValue(String str) {
		long time=System.currentTimeMillis();
		try {
			return setOps.members(str);
		} catch (Exception ex) {
			logger.error("getCacheValue::: exception " + ex);
		}finally{
			logger.error("Total time::getCacheValue "+(System.currentTimeMillis()-time));
		}
		return null;
	}

	public void putTokenValueInCache(String token) {
		long time=System.currentTimeMillis();
		try {
		    valueOps.append(token, token);
			stringRedisTemplate.expire(token, 24,
					TimeUnit.HOURS);
		} catch (Exception ex) {
			logger.error("putTokenValueInCache::: exception " + ex);
		}finally{
			logger.error("Total time::putTokenValueInCache "+(System.currentTimeMillis()-time));
		}
	}

	public String getTokenCacheValue(String str) {
		long time=System.currentTimeMillis();
		try {
			return valueOps.get(str);
		} catch (Exception ex) {
			logger.error("getTokenCacheValue::: exception " + ex);
		}finally{
			logger.error("Total time::getTokenCacheValue "+(System.currentTimeMillis()-time));
		}
		return null;
	}

	public void putObjectCacheValueByEvictionMinute(String str, Object object,int minute) {
		long time=System.currentTimeMillis();
		try {
			valueObjectOps.set(str, object);
			redisTemplate
					.expire(str, minute, TimeUnit.MINUTES);
			// mcc.set(str, memcachedObjectEvictionTime, object);
		} catch (Exception ex) {
			logger.error("putObjectCacheValue::: exception ", ex);
		}finally{
			logger.error("Total time::putObjectCacheValue "+(System.currentTimeMillis()-time));
		}
	}

	public Object getObjectCacheValue(String str) {
		try {
			return valueObjectOps.get(str);
		} catch (Exception ex) {
			logger.error("getObjectCacheValue::: exception ", ex);
		}
		return null;
	}
	
	public Object removeObjectCacheValue(String key) {
		try {
			 valueObjectOps.getOperations().delete(key);
			 return true;
		} catch (Exception ex) {
			logger.error("removeObjectCacheValue::: exception ", ex);
		}
		return false;
	}
	
	public void putObjectCacheValueByEvictionDay(String str, Object object,int evictionDay) {
		long time=System.currentTimeMillis();
		try {
			valueObjectOps.set(str, object);
			redisTemplate
					.expire(str, evictionDay, TimeUnit.DAYS);			
		} catch (Exception ex) {
			logger.error("putObjectCacheValueByEvictionDay::: exception ", ex);
		}finally{
			logger.error("Total time::putObjectCacheValueByEvictionDay "+(System.currentTimeMillis()-time));
		}
	}

	public Long getObjectCacheValueByEvictionSecond(String str, Long value,
			int evictionSecond) {
		long time = System.currentTimeMillis();
		Long cahcehValue=0L;
		try {			
			cahcehValue=valueObjectOps.increment(str, value);
			redisTemplate.expire(str, evictionSecond, TimeUnit.SECONDS);
		} catch (Exception ex) {
			logger.error("getObjectCacheValueByEvictionSecond::: exception ", ex);
		} finally {
			logger.info("Total time::getObjectCacheValueByEvictionSecond "
					+ (System.currentTimeMillis() - time));
		}
		return cahcehValue;
	}
	
	public boolean putObjectCacheValueByEvictionSecond(String str, Object value,
			int evictionSecond) {
		long time = System.currentTimeMillis();
		
		try {			
			valueObjectOps.set(str, value);
			redisTemplate.expire(str, evictionSecond, TimeUnit.SECONDS);
			return true;
		} catch (Exception ex) {
			logger.error("getObjectCacheValueByEvictionSecond::: exception ", ex);
		} finally {
			logger.info("Total time::getObjectCacheValueByEvictionSecond "
					+ (System.currentTimeMillis() - time));
		}
		return false;
	}
	
	 public String getConversionCapping(int adnetworkId,int operatorId,int productId,Timestamp ts) {
    	 
   	  String info="";
   	  try {
    		 
   		 info+="<BR>Daily Adnetwork Operator Config: "+valueIntOps.get(getDailyAdnetworkOperatorCappingkey(adnetworkId,operatorId,ts));     		 	
    		 info+="<BR>Per Hour Capping:  "+valueIntOps.get(getHourlyAdnetworkOperatorCappingkey(adnetworkId,operatorId,ts));
  		  info+="<BR> Daily Operator Capping :: "+valueIntOps.get(getDailyOperatorCappingkey(operatorId,ts));
 		  info+="<BR>Per Hour Operator Capping:: "+valueIntOps.get(getHourlyOperatorCappingkey(operatorId,ts));
    		 info+="<BR> Daily Product Capping :: "+valueIntOps.get(getDailyProductCappingkey(productId,ts));
   		 info+="<BR>Per Hour Click Product Capping:: "+valueIntOps.get(getHourlyClickProductCappingkey(
   				 productId,ts));
	      	
    		} catch (Exception ex) {  			
    			logger.error("getConversionCapping::: exception ", ex);
    			info+=ex.toString();
    		}
    		return info;    		
   	}
     
     
     public boolean isCappingOver(AdnetworkOperatorConfig adnetworkOperatorConfig,
   		  Operator operator,Product product,Timestamp ts) {
   		
   	  try {
   		  
   		 Integer cappingCount=0;
   		  
   		  if(adnetworkOperatorConfig!=null){
   	    	
   			cappingCount=valueIntOps.get(getDailyAdnetworkOperatorCappingkey(
   					adnetworkOperatorConfig.getAdNetworkId(),
   	    			adnetworkOperatorConfig.getOperatorId(),ts));
   		 	
   		    if((cappingCount!=null&&adnetworkOperatorConfig.getPerDaiilyConversionCapping()!=null
   		    		&&cappingCount>=adnetworkOperatorConfig.getPerDaiilyConversionCapping())
   		    		||(adnetworkOperatorConfig.getPerDaiilyConversionCapping()!=null&&
   		    		adnetworkOperatorConfig.getPerDaiilyConversionCapping()==0)){
   		 	     return true;
   		    }
   			
   		    cappingCount=valueIntOps.get(getHourlyAdnetworkOperatorCappingkey(
   		    		adnetworkOperatorConfig.getAdNetworkId(), adnetworkOperatorConfig.getOperatorId(),ts));
   		    if(cappingCount!=null&&adnetworkOperatorConfig.getPerhourConversionCapping()!=null
   		    		&&cappingCount.intValue()>=adnetworkOperatorConfig.getPerhourConversionCapping()){
       		 	return true;
       		    }
   	     }
   		  
   		  if(product!=null){
	    		 cappingCount=valueIntOps.get(getDailyProductCappingkey(product.getId(),ts));
	    		  if(cappingCount!=null&&product.getProductWiseCappingPerDay()!=null&&cappingCount>=
	    				  product.getProductWiseCappingPerDay()){
	      		 	return true;
	      		    }
	      			cappingCount=valueIntOps.get(getHourlyClickProductCappingkey(product.getId(),ts));
	      		    if(cappingCount!=null&&product.getProductWiseHourlyClickCappingRedirectToCg()!=null
	      		    		&&cappingCount>=product.getProductWiseHourlyClickCappingRedirectToCg()){
	          		 	return true;
	          		    }	    		 
    		 }
   		  
   		  if(operator!=null){
 	    		 cappingCount=valueIntOps.get(getDailyOperatorCappingkey(operator.getOperatorId(),ts));
 	    		  if(cappingCount!=null&&operator.getPerDaiilyConversionCapping()!=null&&cappingCount>=
 	    				operator.getPerDaiilyConversionCapping()){
 	      		 	return true;
 	      		    }
 	      			cappingCount=valueIntOps.get(getHourlyOperatorCappingkey(operator.getOperatorId(),ts));
 	      		    if(cappingCount!=null&&operator.getPerhourConversionCapping()!=null
 	      		    		&&cappingCount>=operator.getPerhourConversionCapping()){
 	          		 	return true;
 	          		    }	    		 
     		 }
   		  
   		} catch (Exception ex) {  			
   			logger.error("isAdnetworkConversionCappingOver::: exception ", ex);
   			return true;
   		}
   		return false;
   	}
     
     
    private String  getDailyAdnetworkOperatorCappingkey(Integer adnetworkId,Integer operatorId,Timestamp ts){
   	 return ADNETWORK_CAPPING_KEY+ adnetworkId
	    		+MConstants.KEY_SEPERATOR+operatorId+MConstants.KEY_SEPERATOR+ 
	    		MConstants
 	    		.sdfDDMMyyyy.format(ts.toLocalDateTime());
    }
    
    
    private String  getHourlyAdnetworkOperatorCappingkey(Integer adnetworkId,Integer operatorId,Timestamp ts){
   	 return ADNETWORK_CAPPING_KEY+ adnetworkId
	    		+MConstants.KEY_SEPERATOR+operatorId+MConstants.KEY_SEPERATOR+ 
	    		MConstants.sdfDDMMyyyyHH.format(ts.toLocalDateTime());
    }
    
    private String  getHourlyClickProductCappingkey(Integer productId,Timestamp ts){
   	 return PRODUCT_HOURLY_CLICK_CAPPING_KEY+productId+MConstants.KEY_SEPERATOR
   			 + MConstants.sdfDDMMyyyyHH.format(ts.toLocalDateTime())
   			;
    }
    
    private String  getDailyProductCappingkey(Integer productId,Timestamp ts){
   	 return PRODUCT_DAILY_CONVERSION_CAPPING_KEY+productId+MConstants.KEY_SEPERATOR+ 
   			MConstants.sdfDDMMyyyy.format(ts.toLocalDateTime());
    }
    
    private String  getHourlyOperatorCappingkey(Integer opId,Timestamp ts){
   	 return OPERATOR_HOURLY_CONVERSION_CAPPING_KEY+opId+MConstants.KEY_SEPERATOR+ 
   			 MConstants.sdfDDMMyyyyHH.format(ts.toLocalDateTime());
    }
    
    private String  getDailyOperatorCappingkey(Integer opId,Timestamp ts){
   	 return OPERATOR_DAILY_CONVERSION_CAPPING_KEY+opId+MConstants.KEY_SEPERATOR+
   			 MConstants.sdfDDMMyyyy.format(ts.toLocalDateTime());
    }
    
    public void putConversionCapping(int adnetworkId,int operatorId,
  		  int productId,Timestamp ts) {
		  
		  try {
		
		String dailyAdnetworkOperatorCappingkey =getDailyAdnetworkOperatorCappingkey(adnetworkId, operatorId,ts);//CAPPING_KEY+ adnetworkId+MConstants.KEY_SEPERATOR+operatorId+MConstants.KEY_SEPERATOR+ dateddMMYYYY;
		String hourlyAdnetworkOperatorkeyCapping = getHourlyAdnetworkOperatorCappingkey(adnetworkId, operatorId,ts);//CAPPING_KEY+ adnetworkId+MConstants.KEY_SEPERATOR+operatorId+MConstants.KEY_SEPERATOR+ dateddMMYYYYHH;
		
		String dailyOperatorCappingkey =getDailyOperatorCappingkey(operatorId,ts);// CAPPING_KEY+operatorId+MConstants.KEY_SEPERATOR+ dateddMMYYYY;
		String hourlyOperatorCappingkey = getHourlyOperatorCappingkey(operatorId,ts);//CAPPING_KEY+operatorId+MConstants.KEY_SEPERATOR+ dateddMMYYYYHH;
		
		
		String dailyProductCappingkey =getDailyProductCappingkey(productId,ts);
		//String hourlyProductCappingkey = getHourlyProductCappingkey(productId);
		
		
			
			valueIntOps.setIfAbsent(dailyAdnetworkOperatorCappingkey, 0);
			valueIntOps.increment(dailyAdnetworkOperatorCappingkey, 1);
			valueIntOps.getOperations().expire(dailyAdnetworkOperatorCappingkey,25, TimeUnit.HOURS);
			
			valueIntOps.setIfAbsent(hourlyAdnetworkOperatorkeyCapping, 0);
			valueIntOps.increment(hourlyAdnetworkOperatorkeyCapping, 1);
			valueIntOps.getOperations().expire(hourlyAdnetworkOperatorkeyCapping,2, TimeUnit.HOURS);
			
			//Uncommnent  030302010
			valueIntOps.setIfAbsent(dailyOperatorCappingkey, 0);
			valueIntOps.increment(dailyOperatorCappingkey, 1);
			valueIntOps.getOperations().expire(dailyOperatorCappingkey,25, TimeUnit.HOURS);
			
			valueIntOps.setIfAbsent(hourlyOperatorCappingkey, 0);
			valueIntOps.increment(hourlyOperatorCappingkey, 1);
			valueIntOps.getOperations().expire(hourlyOperatorCappingkey,2, TimeUnit.HOURS);
		//Uncommnent  030302010
			
			valueIntOps.setIfAbsent(dailyProductCappingkey, 0);
			valueIntOps.increment(dailyProductCappingkey, 1);
			valueIntOps.getOperations().expire(dailyProductCappingkey,25, TimeUnit.HOURS);
			
//			valueIntOps.setIfAbsent(hourlyProductCappingkey, 0);
//			valueIntOps.increment(hourlyProductCappingkey, 1);
//			valueIntOps.getOperations().expire(hourlyProductCappingkey,2, TimeUnit.HOURS);
			
		} catch (Exception ex) {  			
			logger.error("putConversionCapping::: exception ", ex);
		}
	}


}
