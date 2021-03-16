package net.mycomp.comviva;

import java.util.HashMap;
import java.util.Map;

public interface ComvivaConstant {
	
	String SUBSCRIBE = "SUBSCRIBE";
	String UNSUBSCRIBE = "UNSUBSCRIBE";
	String SUCCESS_UNSUBSCRIBE = "DBILL:Ok, Accepted";
	String SUCCESS_RESULT = "SUCCESS";
	Map<Integer, ComvivaServiceConfig> mapServiceIdToComvivaServiceConfig = new HashMap<>();
	
}
