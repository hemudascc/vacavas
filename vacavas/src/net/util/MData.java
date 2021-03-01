package net.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.persist.bean.AdnetworkOperatorConfig;
import net.persist.bean.AdnetworkTypeConfig;
import net.persist.bean.Adnetworks;
import net.persist.bean.Aggregator;
import net.persist.bean.BlockSeries;
import net.persist.bean.Country;
import net.persist.bean.Operator;
import net.persist.bean.OperatorCircles;
import net.persist.bean.Product;
import net.persist.bean.Service;
import net.persist.bean.VWCircleInfo;
import net.persist.bean.VWServiceCampaignDetail;

public interface MData {

	 static final Logger logger = Logger.getLogger(MData.class);

	public static final Map<Integer, Map<Integer, AdnetworkOperatorConfig>> mapAdnetworkOpConfig=new HashMap<Integer, Map<Integer, AdnetworkOperatorConfig>>();
	public static final Map<Integer,VWServiceCampaignDetail> mapCamapignIdToVWServiceCampaignDetail=new HashMap<Integer,VWServiceCampaignDetail>();
	public static final Map<Integer,List<VWServiceCampaignDetail>> mapSmartCamapignIdToVWServiceCampaignDetail=new HashMap<Integer,List<VWServiceCampaignDetail>>();
	
	public static final Map<String, AdnetworkTypeConfig> mapAdnetworkTypeConfig=new HashMap<String, AdnetworkTypeConfig>();
	public static Map<Integer, Adnetworks> mapAdnetworks=new HashMap<Integer, Adnetworks>();
	public static Map<String, VWCircleInfo> mapVWCircleInfo=new HashMap<String, VWCircleInfo>();
	public static Map<Integer, Map<String, OperatorCircles>> mapCircleCodeWiseOperatorCircle=new HashMap<Integer, Map<String, OperatorCircles>>();
	public static Map<Integer, OperatorCircles> mapCircleIdWiseOperatorCircles=new HashMap<Integer, OperatorCircles>();
	public static Map<String, BlockSeries> mapBlockSeries=new HashMap<String, BlockSeries>();
	public static Map<Integer, Service> mapServiceIdToService=new HashMap<Integer, Service>();
	
	public static Map<Integer,Aggregator> mapIdToAggregator=new HashMap<Integer, Aggregator>();
	public static Map<Integer, Country> mapIdToCountry=new HashMap<Integer, Country>();
	public static Map<Integer, Operator> mapIdToOperator=new HashMap<Integer, Operator>();
	public static Map<Integer, Product> mapIdToProduct=new HashMap<Integer, Product>();
	
	
	
	public static int findCampaignId(final Integer adnetWorkId,Integer serviceId){
		try{
		 return  mapCamapignIdToVWServiceCampaignDetail.entrySet().stream().filter(a->
		  a.getValue().getAdNetworkId()==adnetWorkId.intValue()&&
		  a.getValue().getServiceId()==serviceId.intValue()).findFirst()
		  .map(a->a.getValue()).get().getCampaignId();
		}catch(Exception ex){
			logger.error("findCampaignId:: ",ex);
		}
		return MConstants.DEFAULT_ADNETWORK_CAMPAIGN_ID;
	}
	
}
