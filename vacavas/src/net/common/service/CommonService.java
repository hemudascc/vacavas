package net.common.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.net.util.SubnetUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.jpa.repository.JPAAggregator;
import net.jpa.repository.JPACountry;
import net.jpa.repository.JPAProduct;
import net.jpa.repository.JPAService;
import net.persist.bean.AdnetworkOperatorConfig;
import net.persist.bean.AdnetworkTypeConfig;
import net.persist.bean.Adnetworks;
import net.persist.bean.Aggregator;
import net.persist.bean.Country;
import net.persist.bean.IPPool;
import net.persist.bean.LiveReport;
import net.persist.bean.Operator;
import net.persist.bean.OperatorCircles;
import net.persist.bean.Product;
import net.persist.bean.VWCircleInfo;
import net.persist.bean.VWServiceCampaignDetail;
import net.process.bean.AdNetworkRequestBean;
import net.process.bean.AdnetworkCampaignReport;
import net.process.bean.AdnetworkCompaignReportWrapper;
import net.util.HttpURLConnectionUtil;
import net.util.MConstants;
import net.util.MData;
import net.util.MUtility;

@Service("commonService" )
public class CommonService {

	private static final Logger logger = Logger.getLogger(CommonService.class);

	
	public static Timestamp lastreloadConfigTime;
	
	public static AtomicInteger adnetwrokTokenAtomicInteger;
	public static List<IPPool> listIPPool;
	public static Map<Integer,Operator> mapOperator=new HashMap<Integer,Operator>();
	public static List<Operator> listOperator;
	protected HttpURLConnectionUtil httpURLConnectionUtil;

	@Autowired
	private IDaoService daoService;

	@Autowired
	private JPAService jpaService;
	
	@Autowired
	private JPAAggregator jpaAggregator;
	
	@Autowired
	private JPACountry jpaCountry;
	
	@Autowired
	private JPAProduct jpaProduct;
	
	
	@Autowired
	private IpCheckService ipCheckService;
	@Value("${waste.traffic.url}")
	private String wasteUrl;
	

	@Value("${jdbc.db.name}")
	private String dbName;
	
	
	

	@PostConstruct
	public void init() {
		httpURLConnectionUtil = new HttpURLConnectionUtil();
		try {
			adnetwrokTokenAtomicInteger = new AtomicInteger(daoService.findNextAutoIncrementId("adnetwork_tokens", dbName));
		}catch (Exception e) {
			//adnetwrokTokenAtomicInteger= new AtomicInteger(new Integer(0));
			e.printStackTrace();
		}
		
		
		loadConfiguration();
	}

	public void loadConfiguration() {

		 
		
		List<net.persist.bean.Service> listService =jpaService.findAll();
		MData.mapServiceIdToService.putAll(listService.stream().collect(
				Collectors.toMap(p -> p.getServiceId(), p -> p)));
		
		List<Adnetworks> listAdnetworks = daoService.findAllEnableAdnetworks();
		Map<Integer, Adnetworks> mapAdnetworks = new HashMap<Integer, Adnetworks>();
		for (Adnetworks adnetworks : listAdnetworks) {
			mapAdnetworks.put(adnetworks.getAdNetworkId(), adnetworks);
		}
		MData.mapAdnetworks.clear();
		MData.mapAdnetworks.putAll(mapAdnetworks);

		List<AdnetworkOperatorConfig> adnOpConfigList = 
				daoService.findAdnetworkOperatorConfig();
		logger.info("loadConfiguration:::::::::::::::AdnetworkOperatorList:: " + adnOpConfigList);
		adnOpConfigList.forEach(a->{
			int gcd=MUtility.gcd(a.getSkipNumber(), 100);
			a.atomicActSkipNumber.set(0, a.getSkipNumber()/gcd);
			a.atomicActSkipNumber.set(1, 100/gcd);
			a.atomicActSkipNumber.set(2,1);});
		
		MData.mapAdnetworkOpConfig.clear();
		MData.mapAdnetworkOpConfig.putAll(adnOpConfigList.stream()
				.collect(Collectors.groupingBy(AdnetworkOperatorConfig::getOperatorId,
						Collectors.toMap(AdnetworkOperatorConfig::getAdNetworkId, a ->a))));
		
		List<VWServiceCampaignDetail> listVWServiceCampaignDetail = daoService.findEnableVWServiceCampaignDetail();
		MData.mapCamapignIdToVWServiceCampaignDetail.clear();
		MData.mapCamapignIdToVWServiceCampaignDetail.putAll(listVWServiceCampaignDetail
				.stream().collect(Collectors.toMap(VWServiceCampaignDetail::getCampaignId, 
				Function.identity())));
		
//		List<VWServiceCampaignDetail> listSmartVWServiceCampaignDetail=daoService.
//				findEnableSmartVWServiceCampaignDetail();
//		if(listSmartVWServiceCampaignDetail!=null){
//			MData.mapSmartCamapignIdToVWServiceCampaignDetail.clear();
//			MData.mapSmartCamapignIdToVWServiceCampaignDetail.putAll(listSmartVWServiceCampaignDetail
//					.stream().collect(Collectors.
//							groupingBy(VWServiceCampaignDetail::getAdNetworkId, 
//									Collectors.mapping(a->a, Collectors.toList()))));
//		}
			
		List<VWCircleInfo> listVWCircleInfo = daoService.findAllCircleInfo();
		Map<String, VWCircleInfo> mapVWCircleInfo = new HashMap<String, VWCircleInfo>();

		for (VWCircleInfo vwcircleInfo : listVWCircleInfo) {
			logger.info("loadConfiguration :::::::::::::::vwcircleInfo::" + vwcircleInfo);
			if (vwcircleInfo != null && vwcircleInfo.getMsisdnPrefix() != null) {
				mapVWCircleInfo.put(vwcircleInfo.getMsisdnPrefix(), vwcircleInfo);
			}
		}
		MData.mapVWCircleInfo.clear();
		MData.mapVWCircleInfo.putAll(mapVWCircleInfo);
		listOperator=daoService.findAllEnabledOperator();
		
		List<OperatorCircles> listOperatorCircles = daoService.findEnableOperatorCircles();
		Map<Integer, Map<String, OperatorCircles>> mapCircleCodeWiseOperatorCircle = new HashMap<Integer, Map<String, OperatorCircles>>();
		Map<Integer, OperatorCircles> mapCircleIdWiseOperatorCircles = new HashMap<Integer, OperatorCircles>();
		for (OperatorCircles operatorCircles : listOperatorCircles) {
			logger.info("loadConfiguration :::::::::::::::OperatorCircles::" + operatorCircles);
			Map<String, OperatorCircles> map = mapCircleCodeWiseOperatorCircle.get(operatorCircles.getOperatorId());
			if (map == null) {
				map = new HashMap<String, OperatorCircles>();
				mapCircleCodeWiseOperatorCircle.put(operatorCircles.getOperatorId(), map);
			}
			map.put(operatorCircles.getOpCircleCode(), operatorCircles);
			mapCircleIdWiseOperatorCircles.put(operatorCircles.getCircleId(), operatorCircles);
		}
		MData.mapCircleCodeWiseOperatorCircle.clear();
		MData.mapCircleCodeWiseOperatorCircle.putAll(mapCircleCodeWiseOperatorCircle);
		MData.mapCircleIdWiseOperatorCircles.clear();
		MData.mapCircleIdWiseOperatorCircles.putAll( mapCircleIdWiseOperatorCircles);

		listIPPool = daoService.findAllIpPool(true);
		if (listIPPool != null) {
			for (IPPool ippool : listIPPool) {
				try {
					SubnetUtils utils = new SubnetUtils(ippool.getSubnetIpAddress().replaceAll(" ", "").trim());
					utils.setInclusiveHostCount(true);
					ippool.subnetUtils=utils;
				} catch (Exception ex) {
					logger.error("reloadConfiguration:: ippool:: " + ippool + ", exception: " , ex);
				}
			}
		}
		
		List<Aggregator> listAggregator=jpaAggregator.findAll();
		MData.mapIdToAggregator.putAll(listAggregator.stream().collect(
				Collectors.toMap(p -> p.getId(), p -> p)));
		
		List<Country> listCountry=jpaCountry.findAll();
		MData.mapIdToCountry.putAll(listCountry.stream().collect(
				Collectors.toMap(p -> p.getId(), p -> p)));
		
		List<Operator> listOperator=daoService.findAllEnabledOperator();
		MData.mapIdToOperator.putAll(listOperator.stream().collect(
				Collectors.toMap(p -> p.getOperatorId(), p -> p)));		
		
		List<Product> listProduct=jpaProduct.findAll();
		MData.mapIdToProduct.putAll(listProduct.stream().collect(
				Collectors.toMap(p -> p.getId(), p -> p)));
		
		
		loadAdnetworkTypeConfig();
		
	}

	private void loadAdnetworkTypeConfig() {

		List<AdnetworkTypeConfig> listAdnetworkTypeConfig = daoService.findAdnetworkTypeConfig();
		Map<String, AdnetworkTypeConfig> tmpMapAdnetworkTypeConfig = new HashMap<String, AdnetworkTypeConfig>();

		for (AdnetworkTypeConfig adnetworkTypeConfig : listAdnetworkTypeConfig) {

			tmpMapAdnetworkTypeConfig.put(
					MConstants.getAdnetworkTypeConfigKey(adnetworkTypeConfig.getType(),
							adnetworkTypeConfig.getOperatorId(), adnetworkTypeConfig.getAdnetwokId()),
					adnetworkTypeConfig);

			if (adnetworkTypeConfig.getBlockCircleList() != null) {
				for (Integer blockCircleId : adnetworkTypeConfig.getBlockCircleList()) {

					adnetworkTypeConfig.getMapBlockCircle().put(blockCircleId,
							MData.mapCircleIdWiseOperatorCircles.get(blockCircleId));
				}
			}

			if (adnetworkTypeConfig.getPrepaidBlockCircles() != null) {
				for (Integer prepiadCircleId : adnetworkTypeConfig.getPrepaidBlockCircles()) {
					adnetworkTypeConfig.getMapPrepaidBlockCircle().put(prepiadCircleId,
							MData.mapCircleIdWiseOperatorCircles.get(prepiadCircleId));
				}
			}
			if (adnetworkTypeConfig.getPostpaidBlockCircles() != null) {
				for (Integer postpaidCircleId : adnetworkTypeConfig.getPostpaidBlockCircles()) {
					adnetworkTypeConfig.getMapPostpaidBlockCircle().put(postpaidCircleId,
							MData.mapCircleIdWiseOperatorCircles.get(postpaidCircleId));
				}
			}
		}
		MData.mapAdnetworkTypeConfig .clear();
		MData.mapAdnetworkTypeConfig.putAll(tmpMapAdnetworkTypeConfig);
	}

	
	public static int findCircleIdByOperatorCircle(int opId, String opCircleCode) {

		try {
			OperatorCircles operatorCircles = MData.mapCircleCodeWiseOperatorCircle.get(opId).get(opCircleCode);
			if (operatorCircles != null) {
				return operatorCircles.getCircleId();
			} else {
				return MData.mapCircleCodeWiseOperatorCircle.get(opId).get(MConstants.DEFAULT_CIRCLE_CODE).getCircleId();
			}
		} catch (Exception ex) {
			logger.debug("findCircleId ", ex);
		}
		return 0;
	}

	public static Integer findCircleIdByMsisdnSeries(String msisdn) {
	
		if(msisdn==null){
			return 0;
			}
		
		try {
			
			VWCircleInfo vwCircleInfo= MData.mapVWCircleInfo.get
					(MUtility.findCircleSeriesCode6Digit(msisdn));
			if(vwCircleInfo==null){
				vwCircleInfo= MData.mapVWCircleInfo.get
							(MUtility.findCircleSeriesCode7Digit(msisdn));
			}
			return vwCircleInfo.getCircleId();
		} catch (Exception ex) {
			logger.warn("circle not found for "+msisdn);
		}
		return 0;
	}
	

//	public void putChargeAndCappingValueInCache(String msisdn, Integer opId, Integer adnetworkCampignId,
//			Integer circleId) {
//
//		try {
//			memCacheService.putChargingLogInCache(MConstants.CHARGED + MUtility.formatMsisdn(msisdn),
//					new Timestamp(System.currentTimeMillis()));
//			
//			VWServiceCampaignDetail vwServiceCampaignDetail =
//					MData.mapCamapignIdToVWServiceCampaignDetail.get(adnetworkCampignId);
//			if(vwServiceCampaignDetail!=null){
//				memCacheService
//				.putCacheCappingValue(MConstants.getCappingKey(opId, vwServiceCampaignDetail.getAdNetworkId(), circleId));	
//			}
//			
//			
//		} catch (Exception ex) {
//			logger.error("putChargeAndCappingValueInCache:  msisdn:: "+msisdn+",opId: "+opId+
//					", adnetworkCampignId: "+adnetworkCampignId+", circleId"+circleId, ex);
//		}
//	}
	
	
		
	
	public  AdnetworkCompaignReportWrapper generateAdnetworkCampaignReport(
			List<LiveReport> tmpReportList,int opId,int noOfPrevDay){
		
		List<LiveReport> reportList=tmpReportList.stream().filter(liveReport->
		ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime())==noOfPrevDay)
		.collect(Collectors.toList());
		
		AdnetworkCompaignReportWrapper adnetworkCompaignReportWrapper=new AdnetworkCompaignReportWrapper();
		adnetworkCompaignReportWrapper.setNoOfPrevDay(noOfPrevDay);
		
		adnetworkCompaignReportWrapper.setReportHour(reportList.stream().mapToInt(LiveReport::getActionHours).distinct().toArray());
		
		Map<Integer,LiveReport> mapRenewal=new HashMap<Integer,LiveReport>();	
		adnetworkCompaignReportWrapper.setMapRenewal(mapRenewal);
		Map<Integer,LiveReport> mapDeactivation=new HashMap<Integer,LiveReport>();
		adnetworkCompaignReportWrapper.setMapDeactivation(mapDeactivation);
		
		for(LiveReport liveReport:reportList){
		
			if(mapRenewal.get(liveReport.getActionHours())==null){
				LiveReport l=new LiveReport();
				mapRenewal.put(liveReport.getActionHours(), l);
			}
			mapRenewal.get(liveReport.getActionHours()).addRenewalCount(liveReport.getRenewalCount());	
			adnetworkCompaignReportWrapper.addTotalRenewalCount(liveReport.getRenewalCount());
			
			mapRenewal.get(liveReport.getActionHours()).addRenewalAmount(liveReport.getRenewalAmount());
			adnetworkCompaignReportWrapper.addTotalRenewalAmount(liveReport.getRenewalAmount());
			
			if(mapDeactivation.get(liveReport.getActionHours())==null){
				LiveReport l=new LiveReport();
				mapDeactivation.put(liveReport.getActionHours(), l);
			}
			mapDeactivation.get(liveReport.getActionHours()).addDeactivation(liveReport.getDctCount());	
			adnetworkCompaignReportWrapper.addTotalDeactivationCount(liveReport.getDctCount());
		}
		
		Map<Integer,AdnetworkCampaignReport> mapAdnetworkCampaignReport=new HashMap<Integer,AdnetworkCampaignReport>(); 
		adnetworkCompaignReportWrapper.setMapAdnetworkCampaignReport(mapAdnetworkCampaignReport);
		Map<Integer,Map<Integer,List<LiveReport>>> map=reportList.stream().filter(liveReport->
		ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime())==noOfPrevDay
		).collect(Collectors.groupingBy(LiveReport::getAdnetworkCampaignId,
				Collectors.groupingBy(LiveReport::getActionHours, Collectors.toList())));
		
		Iterator<Integer> itr= map.keySet().iterator();
		
		while(itr.hasNext()){
			AdnetworkCampaignReport report=new AdnetworkCampaignReport();
			Integer key=itr.next();		
			mapAdnetworkCampaignReport.put(key, report);
			
			 report.setCampaignName(MData.mapCamapignIdToVWServiceCampaignDetail.get(key)!=null?
					 MData.mapCamapignIdToVWServiceCampaignDetail.get(key).getCampaignName():"DEFAULT");
			
			
			report.setMapReport(map.get(key));
			report.setTotalActivationCount(reportList.stream().filter(liveReport->
					liveReport.getAdnetworkCampaignId()==key.intValue()
					).mapToInt(v->v.getConversionCount()).sum());
			
		//	if(opId==MConstants.AIRTEL_OPERATOR_ID){
				
				report.setTotalParkingCount(reportList.stream().filter(liveReport->
				liveReport.getAdnetworkCampaignId()==key.intValue()
				).mapToInt(v->v.getParkingCount()).sum());
				
//			}else{
//			report.setTotalParkingCount(reportList.stream().filter(liveReport->
//			liveReport.getAdnetworkCampaignId()==key.intValue()
//			).mapToInt(v->v.getParkingToActivationCount()).sum());
//		 }
			
			report.setTotalActivationAmount(reportList.stream().filter(liveReport->
			liveReport.getAdnetworkCampaignId()==key.intValue()
			).mapToDouble(v->v.getAmount()).sum());			
		}
		return adnetworkCompaignReportWrapper;
	}

	public String getWasteUrl() {
		return wasteUrl;
	}

	public void setWasteUrl(String wasteUrl) {
		this.wasteUrl = wasteUrl;
	}
	
	public String getWasteUrl(AdNetworkRequestBean adNetworkRequestBean){
		Adnetworks adnetwork = MData.mapAdnetworks.get(adNetworkRequestBean.getAdNetworkId());
		adNetworkRequestBean.adnetworkToken
		.setRedirectToUrl(adnetwork.getWasteUrl()!=null?
				(adnetwork.getWasteUrl().replaceAll("<token>",URLEncoder.encode(adNetworkRequestBean.getToken())))
				:adNetworkRequestBean.adnetworkToken.getRedirectToUrl());
		return adNetworkRequestBean.adnetworkToken.getRedirectToUrl();
	}	
}
