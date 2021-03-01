package net.common.service;



import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service("blockSeriesRedisCacheService")
public class BlockSeriesRedisCacheService {

	private static final Logger logger = Logger.getLogger(BlockSeriesRedisCacheService.class);

	@Autowired
	@Qualifier(value="blockSeriesStringRedisTemplate")
	private StringRedisTemplate blockSeriesStringRedisTemplate;

	
	
	private  final String BLOCK_SERIES="block_series";
	
	HashOperations<String, String, String> hashOps;

	
	@PostConstruct
	public void init() {
		hashOps = blockSeriesStringRedisTemplate.opsForHash();
	}

	public boolean putAllValueInMap(String key, Map<String, String> map) {
		hashOps.putAll(key, map);
		return true;
	}

	public boolean putValueInHashMap(String hashMapName, String key, String value) {
		hashOps.put(hashMapName, key, value);
		return true;
	}

	public String getValue(String hashMapName, List<String> key) {
		String value = hashOps.size(hashMapName) + "    "
				+ hashOps.multiGet(hashMapName, key);
		return value;
	}

	
	public boolean putAllMapValueInBlockSeries(Map<String,String> map){  
		  HashOperations<String,String,String> hashOps = blockSeriesStringRedisTemplate.opsForHash();
		  hashOps.putAll(BLOCK_SERIES,map);
		  return true;
	}
		 
		 public boolean putBlockSeriesValue(String key,String value){    
		  HashOperations<String,String,String> hashOps = blockSeriesStringRedisTemplate.opsForHash();  
		  hashOps.put(BLOCK_SERIES,key,value); 
		  return true;
		 }

		 public String getBlockSeriesSizeAndKeyValue(List<String> key){  
		  String value=hashOps.size(BLOCK_SERIES)+"    "+hashOps.multiGet(BLOCK_SERIES, key);  
		  return value;
		 }
		 
      public boolean isBlockSeries(List<String> key) {  
		  
		    List<String> list= hashOps.multiGet(BLOCK_SERIES, key);  
		  if( list!=null){
		   return list.contains("1");
		  }
		  return false;
	}	
}