package net.mycomp.comviva;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public interface ComvivaConstant {
	
	String SUBSCRIBE = "SUBSCRIBE";
	String CHECK_SUB = "CHECK_SUB";
	String SMS_MT = "SMS_MT";
	String UNSUBSCRIBE = "UNSUBSCRIBE";
	String SUCCESS_UNSUBSCRIBE = "DBILL:Ok, Accepted";
	String SUCCESS_RESULT = "SUCCESS";
	Map<Integer, ComvivaServiceConfig> mapServiceIdToComvivaServiceConfig = new HashMap<>();
	static String getSubMessage(String subMessageTemplate, ComvivaServiceConfig comvivaServiceConfig) {
			return subMessageTemplate
					.replaceAll("<productname>", comvivaServiceConfig.getProductName())
					.replaceAll("<amount>", Objects.toString(comvivaServiceConfig.getPrice()))
					.replaceAll("<days>", "1")
					.replaceAll("<unsubkeyword>", comvivaServiceConfig.getUnsubKeyword())
			.replaceAll("<shortcode>", comvivaServiceConfig.getShortCode());
	}
	
}
