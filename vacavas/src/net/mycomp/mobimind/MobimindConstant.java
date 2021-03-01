package net.mycomp.mobimind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MobimindConstant {

	public static final int MOBIMIND_SERVICE_ID=27;
	public static final int MOBIMIND_PRODUCT_ID=12;
	public static final String RECYCLED_SUBCRIBER="RECYCLED_SUBCRIBER";
	
	public static final String MOBIMIND_CACHE_PREFIX="MOBIMIND_CACHE_PREFIX";
	
public static List<MobimindServiceConfig> listMobimindServiceConfig=new ArrayList<MobimindServiceConfig>();
	
public static Map<Integer,MobimindServiceConfig> mapServiceIdToMobimindServiceConfig=
new HashMap<Integer,MobimindServiceConfig>();


	public static Map<String,MobimindServiceConfig> mapMobimindServiceIdToMobimindServiceConfig=
			new HashMap<String,MobimindServiceConfig>();
	
	public static Map<String,MobimindServiceConfig> mapChannelIdToMobimindServiceConfig=
			new HashMap<String,MobimindServiceConfig>();
	
	public static void main(String arg[]){
		System.out.println("Action:: "+MobimindSubcriberStatus.findAction("BLD-SB"));
	}
	
}
