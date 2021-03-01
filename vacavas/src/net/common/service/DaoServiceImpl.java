package net.common.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import net.dao.ICommonDao;
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

@Service("daoService")
public class DaoServiceImpl implements IDaoService {

	@Autowired
	@Qualifier("commonDao")
	private ICommonDao commonDao;

	private static final Logger logger = Logger.getLogger(DaoServiceImpl.class);

	@Override
	public boolean saveObject(Object object) {
		try {
			return commonDao.saveObject(object);
		} catch (Exception ex) {
			logger.error("saveObject:: exception:: "+object +" , ",ex);
		}
		return false;
	}

	@Override
	public boolean updateObject(Object object) {
		try {
			return commonDao.updateObject(object);
		} catch (Exception ex) {
			logger.error("updateObject:: "+object , ex);
		}
		return false;
	}

	@Override
	public VWCircleInfo findCircleInfoByMsisdnSeries(String msisdn, String defaultCircle) {
		try {
			return commonDao.findCircleInfoByMsisdnSeries(msisdn, defaultCircle);
		} catch (Exception ex) {
			logger.error("findCircleInfoByMsisdnSeries:: exception : " + ex);
		}
		return null;
	}

	@Override
	public SubscriberReg searchSubscriber(String msisdn) {
		try {
			return commonDao.searchSubscriber(msisdn);
		} catch (Exception ex) {
			logger.info("searchSubscriber:: not found : " + ex+", msisdn:: "+msisdn);
		}
		return null;
	}

	/*
	 * @Override public ProSubsPackResp callSubscribePackageProcedure(
	 * AdNetworkRequestBean adNetworkRequestBean, SearchCampaign searchCampaign)
	 * { try { return
	 * commonDao.callSubscribePackageProcedure(adNetworkRequestBean,
	 * searchCampaign); } catch (Exception ex) { logger.error(
	 * "callSubscribePackageProcedure:: exception : "+ex); } return null; }
	 */

	

	public List<AdnetworkOperatorConfig> findAdnetworkOperatorConfig() {
		try {
			return commonDao.findAdnetworkOperatorConfig();
		} catch (Exception ex) {
			logger.error("findAdnetworkOperatorConfig:: exception : " , ex);
		}
		return null;
	}

	@Override
	public List<CampaignDetails> findEnableCampaignDetails() {
		try {
			return commonDao.findEnableCampaignDetails();
		} catch (Exception ex) {
			logger.error("findEnableCampaignDetails:: exception : " + ex);
		}
		return null;
	}

	@Override
	public BlockSeries findBlockSeries(List<String> msisdnSeries) {

		try {
			return commonDao.findBlockSeries(msisdnSeries);
		} catch (Exception ex) {
			logger.error("findBlockSeries:: exception : " + ex);
		}
		return null;
	}

	@Override
	public List<VWCircleInfo> findAllCircleInfo() {

		try {
			return commonDao.findAllCircleInfo();
		} catch (Exception ex) {
			logger.error("findAllCircleInfo:: exception : " + ex);
		}
		return null;
	}

	@Override
	public List<BlockSeries> findAllBlockSeries() {
		
		try {
			return commonDao.findAllBlockSeries();
		} catch (Exception ex) {
			logger.error("findAllBlockSeries:: exception : " + ex);
		}
		return null;
	}

	
	@Override
	public AdnetworkToken findAdnetworkTokenById(Integer tokenId) {
		
		try {
			return commonDao.findAdnetworkTokenById(tokenId);
		} catch (Exception ex) {
			logger.error("findAdnetworkTokenById:: exception : tokenId: "+tokenId+" ," + ex);
		}
		return null;
	}

	@Override
	public List<Adnetworks> findAllEnableAdnetworks() {
		try {
			return commonDao.findAllEnableAdnetworks();
		} catch (Exception ex) {
			logger.error("findAllEnableAdnetworks:: exception : " + ex);
		}
		return null;
	}

	@Override
	public Integer findNextAutoIncrementId(String tableName, String dbName) {
		try {
			return commonDao.findNextAutoIncrementId(tableName,dbName);
		} catch (Exception ex) {
			logger.error("findAllEnableAdnetworks:: exception : " + ex);
		}
		return null;
	}

	@Override
	public boolean generateLiveReport(LiveReport liveReport) {
		
		try {
			
			return commonDao.generateLiveReport(liveReport);
		} catch (Exception ex) {
			logger.error("generateLiveReport:: exception : " +" ,liveReport:: "+liveReport,ex);
		}
		return false;
	}

	@Override
	public List<BlockCircle> findBlockCircle() {
		
		try {
			return commonDao.findBlockCircle();
		} catch (Exception ex) {
			logger.error("findBlockCircle:: exception : " + ex);
		}
		return null;
	}

//	@Override
//	public List<PostpaidBlockCircle> findPostpaidBlockCircle() {
//		
//		try {
//			return commonDao.findPostpaidBlockCircle();
//		} catch (Exception ex) {
//			logger.error("findPostpaidBlockCircle:: exception : " + ex);
//		}
//		return null;
//	}

	@Override
	public List<IPPool> findAllIpPool(boolean status) {
		
		try {
			return commonDao.findAllIpPool(status);
		} catch (Exception ex) {
			logger.error("findAllIpPool:: exception : " + ex);
		}
		return null;
	}
	
	
	@Override
	public Map<String,List<LiveReport>> findCurrentDateReport(List<Integer> opIdList) {
		try {
			return commonDao.findCurrentDateReport(opIdList);
		} catch (Exception ex) {
			logger.error("findCurrentDateReport:: exception : " + ex);
		}
		return null;
	}

	
	
	@Override
	public VWCircleInfo findCircleIdByName(String circleName) {
		try {
			return commonDao.findCircleIdByName(circleName);
		} catch (Exception ex) {
			logger.error("findCircleIdByName:: exception : " + ex);
		}
		return null;
	}

	

	@Override
	public List<AdnetworkTypeConfig> findAdnetworkTypeConfig() {
		try {
			return commonDao.findAdnetworkTypeConfig();
		} catch (Exception ex) {
			logger.error("findAdnetworkTypeConfig:: exception : " , ex);
		}
		return null;
	}

	@Override
	public List<Circleid> findCircleid() {
		try {
			return commonDao.findCircleid();
		} catch (Exception ex) {
			logger.error("findCircleid:: exception : " , ex);
		}
		return null;
	}

	@Override
	public List<OperatorCircles> findEnableOperatorCircles() {
		try {
			return commonDao.findEnableOperatorCircles();
		} catch (Exception ex) {
			logger.error("findEnableOperatorCircles:: exception : " , ex);
		}
		return null;
	}

	
	
	@Override
	public SubscriberReg searchSubscriberByLcId(String lifeCycleId) {
		try {
			return commonDao.searchSubscriberByLcId(lifeCycleId);
		} catch (Exception ex) {
			logger.error("searchSubscriberByLcId:: exception : " , ex);
		}
		return null;
	}

	@Override
	public List<Operator> findAllOperator() {
		try {
			return commonDao.findAllOperator();
		} catch (Exception ex) {
			logger.error("findAllOperator:: exception : " , ex);
		}
		return null;
	}

	@Override
	public Map<Integer, Double> findOperatorWiseActivationAndChurnCount() {
		try {
			return commonDao.findOperatorWiseActivationAndChurnCount();
		} catch (Exception ex) {
			logger.error("findAllOperator:: exception : " , ex);
		}
		return null;
	}

	@Override
	public Map<String, List<LiveReport>> findHourlyReport(Integer opId,Integer productId) {
		try {
			return commonDao.findHourlyReport(opId, productId);
		} catch (Exception ex) {
			logger.error("findHourlyReport:: exception : " , ex);
		}
		return null;
	}

	@Override
	public List<VWServiceCampaignDetail> findEnableVWServiceCampaignDetail() {
		try {
			return commonDao.findEnableVWServiceCampaignDetail();
		} catch (Exception ex) {
			logger.error("findEnableVWServiceCampaignDetail:: exception : " , ex);
		}
		return null;
	}

	@Override
	public List<Operator> findAllEnabledOperator() {
		try {
			return commonDao.findAllEnabledOperator();
		} catch (Exception ex) {
			logger.error("findAllEnabledOperator:: exception : " , ex);
		}
		return null;
	}

	@Override
	public boolean createChurnData(AdnetworkChurnData adnetworkChurnData) {
		try {
			return commonDao.createChurnData(adnetworkChurnData);
		} catch (Exception ex) {
			logger.error("createChurnData:: exception : " , ex);
		}
		return false;
	}

	@Override
	public Map<String, Boolean> findBlockedPubId() {
		
		try {
			return commonDao.findBlockedPubId();
		} catch (Exception ex) {
			logger.error("findBlockedPubId:: exception : " , ex);
		}
		return null;
	}

	@Override
	public List<VWServiceCampaignDetail> findEnableSmartVWServiceCampaignDetail() {

		try {
			return commonDao.findEnableSmartVWServiceCampaignDetail();
		} catch (Exception ex) {
			logger.error("findEnableSmartVWServiceCampaignDetail:: exception : " , ex);
		}
		return null;
	}
	@Override
	public List<LiveReport> findDateWiseReport(Integer opId,Integer month,String  year) {
		try {
			return commonDao.findDateWiseReport(opId,month,year);
		} catch (Exception ex) {
			logger.error("findDateWiseReport:: exception : " + ex);
		}
		return null;
	}

	

	@Override
	public Map<String,List<LiveReport>> findOredooKuwaitCGNotificationReport(
			Timestamp fromDate, Timestamp toDate) {
		try {
			return commonDao.findOredooKuwaitCGNotificationReport(
					 fromDate,  toDate);
		} catch (Exception ex) {
			logger.error("findOredooKuwaitCGNotificationReport:: exception : " + ex);
		}
		return null;
	}

	@Override
	public List<LiveReport> findAggReport(AggReport aggReport) {
		try {
			return commonDao.findAggReport(aggReport);
		} catch (Exception ex) {
			logger.error("findAggReport:: exception : " , ex);
		}
		return null;
	}

	@Override
	public Map<Integer, Integer> findSubscriberActiveBase(AggReport aggReport) {
		try {
			return commonDao.findSubscriberActiveBase(aggReport);
		} catch (Exception ex) {
			logger.error("findSubscriberActiveBase:: exception : " , ex);
		}
		return null;
	}

	
	@Override
	public List<LiveReport> findSwaziAggReport(AggReport aggReport) {
		try {
			return commonDao.findSwaziAggReport(aggReport);
		} catch (Exception ex) {
			logger.error("findSwaziAggReport:: exception : " ,ex);
		}
		return null;
	}

	@Override
	public List<Integer> findValidationExpiredSubscriberIdForRenewal(
			List<Integer> operatorId, Integer status) {
		try {
			return commonDao.findValidationExpiredSubscriberIdForRenewal( operatorId,  status);
		} catch (Exception ex) {
			logger.error("findValidationExpiredSubscriberForRenewal:: exception : " ,ex);
		}
		return null;
	}

	@Override
	public AdnetworkOperatorConfig findAdnetworkOperatorConfigById(
			Integer adnopconfigid) {
		try {
			return commonDao.findAdnetworkOperatorConfigById(adnopconfigid);
		} catch (Exception ex) {
			logger.error("findAdnetworkOperatorConfigById:: not found : " ,ex);
		}
		return null;
	}

	@Override
	public List<VWAdnetworkOperatorConfig> findAllAdnConfig() {
		try {
			return commonDao.findAllAdnConfig();
		} catch (Exception ex) {
			logger.error("findAllAdnConfig:: not found : " ,ex);
		}
		return null;
		
	}


	@Override
	public List<Integer> findValidationExpiredSubscriberIdForRenewal(
			List<Integer> operatorId, Integer status,
			Integer lastRenewalRetryNoOfDay) {
		try {
			return commonDao.findValidationExpiredSubscriberIdForRenewal(
					 operatorId,  status,
					 lastRenewalRetryNoOfDay);
		} catch (Exception ex) {
			logger.error("findInappLiveReportAggReport::  : " ,ex);
		}
		return null;
	}

	@Override
	public List<Integer> findValidSubscriberId(List<Integer> operatorId,
			Integer status) {
		try {
			return commonDao.findValidSubscriberId(
					 operatorId,  status);
		} catch (Exception ex) {
			logger.error("findValidSubscriberId::  : " ,ex);
		}
		return null;
	}

	@Override
	public List<Integer> findValidationExpiredSubscriberIdForTrueMoveSecondRenewal(
			List<Integer> operatorId, Integer status) {
		try {
			return commonDao.findValidationExpiredSubscriberIdForTrueMoveSecondRenewal(
					 operatorId,  status);
		} catch (Exception ex) {
			logger.error("findValidationExpiredSubscriberIdForTrueMoveSecondRenewal::  : " ,ex);
		}
		return null;
	}	

	@Override
	public List<Integer> findValidationExpiredSubscriberIdForRenewalForFirstTrueMove(
			List<Integer> operatorId, Integer status) {
		try {
			return commonDao.findValidationExpiredSubscriberIdForRenewalForFirstTrueMove(
					 operatorId,  status);
		} catch (Exception ex) {
			logger.error("findValidationExpiredSubscriberIdForRenewalForFirstTrueMove::  : " ,ex);
		}
		return null;
	}	
	@Override
	public List<VWCallbackDump> findVWCallbackDump(AggReport aggReport) {
		try {
			return commonDao.findVWCallbackDump(aggReport);
		} catch (Exception ex) {
			logger.error("findVWCallbackDump:: exception : " , ex);
		}
		return null;
	}
}
