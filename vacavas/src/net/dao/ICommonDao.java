package net.dao;


import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.persist.bean.AdnetworkChurnData;
import net.persist.bean.AdnetworkOperatorConfig;
import net.persist.bean.AdnetworkToken;
import net.persist.bean.AdnetworkTypeConfig;
import net.persist.bean.Adnetworks;
import net.persist.bean.BlockCircle;
import net.persist.bean.BlockSeries;
import net.persist.bean.CampaignDetails;
import net.persist.bean.Circleid;
import net.persist.bean.IPPool;
import net.persist.bean.LiveReport;
import net.persist.bean.Operator;
import net.persist.bean.OperatorCircles;
import net.persist.bean.SubscriberReg;
import net.persist.bean.VWAdnetworkOperatorConfig;
import net.persist.bean.VWCallbackDump;
import net.persist.bean.VWCircleInfo;
import net.persist.bean.VWServiceCampaignDetail;
import net.process.bean.AggReport;

public interface ICommonDao {

	public boolean saveObject(Object object);
	public boolean updateObject(Object object);
	public VWCircleInfo findCircleInfoByMsisdnSeries(String msisdn,String defaultCircle);
	public List<CampaignDetails> findEnableCampaignDetails();
	public SubscriberReg searchSubscriber(String msisdn);	
	public List<AdnetworkOperatorConfig> findAdnetworkOperatorConfig();
	public BlockSeries findBlockSeries(List<String> msisdnSeries);
	public List<VWCircleInfo> findAllCircleInfo();
	public List<BlockSeries> findAllBlockSeries();
	public AdnetworkToken findAdnetworkTokenById(Integer tokenId);
	public List<Adnetworks> findAllEnableAdnetworks();
	public Integer findNextAutoIncrementId(String tableName, String dbName);
	public boolean generateLiveReport(LiveReport liveReport);
	public List<BlockCircle> findBlockCircle();
	public List<IPPool> findAllIpPool(boolean status);
	public Map<String,List<LiveReport>> findCurrentDateReport(List<Integer> opIdList);
	public VWCircleInfo findCircleIdByName(String circleName);
	public List<AdnetworkTypeConfig> findAdnetworkTypeConfig();
	public List<Circleid> findCircleid();
	public List<OperatorCircles> findEnableOperatorCircles();	

	public SubscriberReg searchSubscriberByLcId(String lifeCycleId); 
	public List<Operator> findAllOperator();
	public List<Operator> findAllEnabledOperator();
	public Map<Integer, Double> findOperatorWiseActivationAndChurnCount();
	public Map<String, List<LiveReport>> findHourlyReport(Integer opId,Integer productId);
	public List<VWServiceCampaignDetail> findEnableVWServiceCampaignDetail(); 
	public boolean createChurnData(AdnetworkChurnData adnetworkChurnData);
	public Map<String, Boolean> findBlockedPubId();
	public List<VWServiceCampaignDetail> findEnableSmartVWServiceCampaignDetail();
	public List<LiveReport> findDateWiseReport(Integer opId,Integer month,String  year);

	public Map<String,List<LiveReport>> findOredooKuwaitCGNotificationReport(
			Timestamp fromDate, Timestamp toDate);
	public List<LiveReport> findAggReport(AggReport aggReport);
	public List<LiveReport> findAggReportByProduct(AggReport aggReport);
	public Map<Integer, Integer> findSubscriberActiveBase(AggReport aggReport);

	public List<LiveReport> findSwaziAggReport(AggReport aggReport); 

	public List<Integer> findValidationExpiredSubscriberIdForRenewal(
			List<Integer> operatorId, Integer status);
	public List<VWAdnetworkOperatorConfig> findAllAdnConfig();
	public AdnetworkOperatorConfig findAdnetworkOperatorConfigById(Integer adnopconfigid);
	public List<Integer> findValidationExpiredSubscriberIdForRenewal(
			List<Integer> operatorId, Integer status,
			Integer lastRenewalRetryNoOfDay);

	public List<Integer> findValidSubscriberId(List<Integer> operatorId,
			Integer status);

	public List<Integer> findValidationExpiredSubscriberIdForRenewalForFirstTrueMove(
			List<Integer> operatorId, Integer status); 
	public List<Integer> findValidationExpiredSubscriberIdForTrueMoveSecondRenewal(
			List<Integer> operatorId, Integer status); 
	public List<VWCallbackDump> findVWCallbackDump(AggReport aggReport);
	public long findVWCallbackDumpCount(AggReport aggReport);
	public LiveReport getlastupdatedliveReport();

}
