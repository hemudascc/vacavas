package net.dao;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
import net.util.MConstants;
import net.util.SubscriptionMode;

public class CommonDaoImpl extends NamedParameterJdbcTemplate implements ICommonDao {

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	private static final Logger logger = Logger.getLogger(CommonDaoImpl.class);

	@Autowired
	public CommonDaoImpl(DataSource dataSource) {
		super(dataSource);
	}
	
	@Transactional
	@Override
	public boolean saveObject(Object object) {
		
		entityManager.persist(object);
		return true;
	}

	@Transactional
	@Override
	public boolean updateObject(Object object) {
		entityManager.merge(object);
		return true;
	}

	@Override
	public VWCircleInfo findCircleInfoByMsisdnSeries(String msisdn, String defaultCircle) {

		VWCircleInfo vwcircleInfo = null;
		Query query = entityManager.createNamedQuery("VWCircleInfo.findCircleInfoByMsisdnSeries", VWCircleInfo.class);

		query.setParameter(1, msisdn);
		query.setMaxResults(1);
		List<VWCircleInfo> list = query.getResultList();
		if (list != null && list.size() > 0) {
			vwcircleInfo = list.get(0);
		}

		if (vwcircleInfo == null) {
			query = entityManager.createNamedQuery("VWCircleInfo.findDefaultCircleInfo", VWCircleInfo.class);
			query.setParameter(1, defaultCircle);
			query.setMaxResults(1);
			vwcircleInfo = (VWCircleInfo) query.getSingleResult();
		}

		return vwcircleInfo;
	}

	@Override
	public List<CampaignDetails> findEnableCampaignDetails() {
		Query query = entityManager.createNamedQuery("CampaignDetails.findEnableCampaignDetails", CampaignDetails.class);
		query.setParameter("status", true);
		return query.getResultList();
	}

	@Override
	public SubscriberReg searchSubscriber(String msisdn) {

		Query query = entityManager.createNamedQuery("SubscriberReg.findSubscriberRegByMsisdn", SubscriberReg.class);
		query.setParameter("msisdn", msisdn);
		SubscriberReg subscriberReg = (SubscriberReg) query.getSingleResult();
		return subscriberReg;
	}

	

	
	

	@Override
	public List<AdnetworkOperatorConfig> findAdnetworkOperatorConfig() {
	
		String queryStr = "select b.* from tb_adnetwork_operator_config b";
		
		Query query = entityManager.createNativeQuery(queryStr, AdnetworkOperatorConfig.class);
		return query.getResultList();
	}

	@Override
	public BlockSeries findBlockSeries(List<String> msisdnSeries) {
		BlockSeries blockSeries = null;
		Query query = entityManager.createNamedQuery("BlockSeries.findBlockSeries", BlockSeries.class);
		query.setParameter("seriesNo", msisdnSeries);
		query.setParameter("status", true);
		List<BlockSeries> list = query.getResultList();
		if (list != null && list.size() > 0) {
			blockSeries = list.get(0);
		}
		return blockSeries;
	}

	@Override
	public List<VWCircleInfo> findAllCircleInfo() {
		Query query = entityManager.createNamedQuery("VWCircleInfo.findAllCircleInfo", VWCircleInfo.class);
		return query.getResultList();
	}

	@Override
	public VWCircleInfo findCircleIdByName(String circleName) {
		Query query = entityManager.createNamedQuery("VWCircleInfo.findDefaultCircleInfo", VWCircleInfo.class);
		query.setParameter("circle", circleName);
		query.setMaxResults(1);
		return (VWCircleInfo)query.getSingleResult();
	}
	
	@Override
	public List<BlockSeries> findAllBlockSeries() {

		Query query = entityManager.createNamedQuery("BlockSeries.findAllBlockSeries", BlockSeries.class);
		query.setParameter("status", true);
		return query.getResultList();
	}

	

	
	@Override
	public AdnetworkToken findAdnetworkTokenById(Integer tokenId) {
		
		Query query = entityManager.createNamedQuery("AdnetworkTokens.findAdnetworkTokensById"
				, AdnetworkToken.class);
		query.setParameter("tokenId", tokenId);	
		
		return (AdnetworkToken)query.getSingleResult();
	}

	@Override
	public List<Adnetworks> findAllEnableAdnetworks() {
		
		Query query = entityManager.createNamedQuery("Adnetworks.findAllEnableAdnetworks"
				, Adnetworks.class);
		query.setParameter("status", true);		
		return query.getResultList();		
	}
	
	@Override
	public Integer findNextAutoIncrementId(String tableName, String dbName) {

		String sqlString = "SELECT AUTO_INCREMENT FROM  INFORMATION_SCHEMA.TABLES "
				+ " WHERE TABLE_SCHEMA =:dbName  AND   TABLE_NAME   = :tableName ;";
		Map<String, String> map = new HashMap<String, String>();
		map.put("dbName", dbName);
		map.put("tableName", tableName);
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(map);
		return queryForObject(sqlString, sqlParameterSource, Integer.class);
	}

	@Override
	@Transactional
	public boolean generateLiveReport(LiveReport liveReport) {
		
		String queryStr="INSERT INTO tb_live_report"
				+ "(operator_id,report_date,adnetwork_campaign_id,pub_id,circle_id,type,click_count,valid_click_count,"
				+ "reverse_click_count,block_click_count,already_subscribed_count,amount,"
				+ "conversion_count,send_conversion_count,send_manual_conversion_count,sent_auto_conversion_count,grace_conversion_count,"
				+ "grace_send_conversion_count,status,duplicate_conversion_count,duplicate_click_count,"
				+ "mismatch_op_conversion_count,dct_count,dct_churn_count,duplcate_dct_count,dct_send_count,"
				+ "renewal_count,renewal_amount,renewal_sentcount,last_activation_time,last_click_time,action_hours,"
				//+ "renewal_count_of_zero_price_activation_after_1days,"
				//+ "renewal_amount_of_zero_price_activation_after_1days,"
				//+ "renewal_of_zero_price_activation_after_2days,renewal_amount_of_zero_price_activation_after_2days,"
				//+ "renewal_of_zero_price_activation_after_more_3days,renewal_amount_of_zero_price_activation_after_more_3days,"
				+ "park_to_activation_count,park_to_activation_amount,service_id,subscription_failed,mode"
				+ ",pin_validation_count,pin_send_count)"
				+ " VALUES(:operator_id,date(:report_date),:adnetwork_campaign_id,:pub_id,:circle_id,"
				+ ":type,:click_count,"
				+ ":valid_click_count,:reverse_click_count,:block_click_count,"
				+ ":already_subscribed_count,:amount,:conversion_count, "
				+ ":send_conversion_count,:send_manual_conversion_count,:sent_auto_conversion_count,:grace_conversion_count,:grace_send_conversion_count,"
				+ ":status,:duplicate_conversion_count,:duplicate_click_count,"
				+ ":mismatch_op_conversion_count,:dct_count,:dct_churn_count,:duplcate_dct_count"
				+ ",:dct_send_count,:renewal_count,:renewal_amount,:renewal_sentcount,"
				+ ":last_activation_time,:last_click_time,(hour(:action_hours))+1,"
				
				//+  ":renewal_count_of_zero_price_activation_after_1days,"
				//+ ":renewal_amount_of_zero_price_activation_after_1days,"
				//+ ":renewal_of_zero_price_activation_after_2days,"
				//+ ":renewal_amount_of_zero_price_activation_after_2days,"
				//+ ":renewal_of_zero_price_activation_after_more_3days,"
				//+ ":renewal_amount_of_zero_price_activation_after_more_3days,"
				
				+ ":park_to_activation_count,:park_to_activation_amount,"
				+ ":service_id,:subscription_failed,:mode"
				+ ",:pin_validation_count,:pin_send_count)"
				+ " ON DUPLICATE KEY UPDATE "
				+ " click_count=click_count+:click_count2,"
				+ " valid_click_count=valid_click_count+:valid_click_count2,"
				+ " reverse_click_count=reverse_click_count+:reverse_click_count2,"
				+ " block_click_count=block_click_count+:block_click_count2,"
				+ " already_subscribed_count=already_subscribed_count+:already_subscribed_count2,"
				+ " conversion_count=conversion_count+:conversion_count2,"
				+ " send_conversion_count=send_conversion_count+:send_conversion_count2,"
				+ " send_manual_conversion_count=send_manual_conversion_count+:send_manual_conversion_count2,"
				+ " sent_auto_conversion_count=sent_auto_conversion_count+:send_auto_conversion_count2,"				
				+ " grace_conversion_count=grace_conversion_count+:grace_conversion_count2,"
				+ " grace_send_conversion_count=grace_send_conversion_count+:grace_send_conversion_count2,"
				+ " duplicate_conversion_count=duplicate_conversion_count+:duplicate_conversion_count2,"
				+ " amount=amount+:amount2,"
				+ " duplicate_click_count=duplicate_click_count+:duplicate_click_count2,"
				+ " mismatch_op_conversion_count=mismatch_op_conversion_count+:mismatch_op_conversion_count2,"
				+ "dct_count=dct_count+:dct_count2,dct_churn_count=dct_churn_count+:dct_churn_count2"
				+ ",duplcate_dct_count=duplcate_dct_count+:duplcate_dct_count2"
				+ ",dct_send_count=dct_send_count+:dct_send_count2"
				+ ",renewal_count=renewal_count+:renewal_count2,renewal_amount=renewal_amount+:renewal_amount2"
				+ ",renewal_sentcount=renewal_sentcount+:renewal_sentcount2"
				+ ",last_activation_time=if(:last_activation_time2 is not null,:last_activation_time3,last_activation_time)"
				+ ",last_click_time=if(:last_click_time2 is not null,:last_click_time3,last_click_time),"
			//	+  "renewal_count_of_zero_price_activation_after_1days=renewal_count_of_zero_price_activation_after_1days+:renewal_count_of_zero_price_activation_after_1days2,"
			//	+ "renewal_amount_of_zero_price_activation_after_1days=renewal_amount_of_zero_price_activation_after_1days+:renewal_amount_of_zero_price_activation_after_1days2,"
			//	+ "renewal_of_zero_price_activation_after_2days=renewal_of_zero_price_activation_after_2days+:renewal_of_zero_price_activation_after_2days2"
			//	+ ",renewal_amount_of_zero_price_activation_after_2days=renewal_amount_of_zero_price_activation_after_2days+:renewal_amount_of_zero_price_activation_after_2days2,"
			//	+ "renewal_of_zero_price_activation_after_more_3days=renewal_of_zero_price_activation_after_more_3days+:renewal_of_zero_price_activation_after_more_3days2,"
			//	+ "renewal_amount_of_zero_price_activation_after_more_3days=renewal_amount_of_zero_price_activation_after_more_3days+:renewal_amount_of_zero_price_activation_after_more_3days2"
				+"park_to_activation_count=park_to_activation_count+:park_to_activation_count2,"
				+ "park_to_activation_amount=park_to_activation_amount+:park_to_activation_amount2,"
				+ "subscription_failed=subscription_failed+:subscription_failed2,"
				+ "pin_validation_count=pin_validation_count+:pin_validation_count2,"
				+ "pin_send_count=pin_send_count+:pin_send_count2";
	
		Query query = entityManager.createNativeQuery(queryStr);
		query.setParameter("operator_id", liveReport.getOperatorId());
		query.setParameter("report_date", liveReport.getReportDate());
		query.setParameter("adnetwork_campaign_id", liveReport.getAdnetworkCampaignId());
		query.setParameter("pub_id", liveReport.getPubId());
		query.setParameter("circle_id", liveReport.getCircleId());		
		query.setParameter("type", liveReport.getType());
		query.setParameter("click_count", liveReport.getClickCount());
		query.setParameter("valid_click_count", liveReport.getValidClickCount());
		query.setParameter("reverse_click_count", liveReport.getReverseClickCount());
		query.setParameter("block_click_count", liveReport.getBlockClickCount());
		query.setParameter("already_subscribed_count", liveReport.getAlreadySubscribedCount());
		query.setParameter("amount", liveReport.getAmount());
		query.setParameter("conversion_count", liveReport.getConversionCount());
		query.setParameter("send_conversion_count", liveReport.getSendConversionCount());
		query.setParameter("send_manual_conversion_count", liveReport.getSendManualConversionCount());
		query.setParameter("sent_auto_conversion_count", liveReport.getSendAutoConversionCount());
		query.setParameter("grace_conversion_count", liveReport.getGraceConversionCount());
		query.setParameter("grace_send_conversion_count", liveReport.getGraceSendConversionCount());
		query.setParameter("status", true);
		query.setParameter("duplicate_conversion_count", liveReport.getDuplicateConversionCount());
		query.setParameter("duplicate_click_count", liveReport.getDuplicateClickCount());
		query.setParameter("mismatch_op_conversion_count", liveReport.getMismatchOperatorConversionCount());
		query.setParameter("service_id", liveReport.getServiceId());
		query.setParameter("dct_count", liveReport.getDctCount());
		query.setParameter("dct_churn_count", liveReport.getChurnDctCount());
		
		query.setParameter("duplcate_dct_count", liveReport.getDuplcateDctCount());
		query.setParameter("dct_send_count", liveReport.getDctSendCount());
		
		query.setParameter("renewal_count", liveReport.getRenewalCount());
		query.setParameter("renewal_amount", liveReport.getRenewalAmount());
		query.setParameter("renewal_sentcount", liveReport.getRenewallSentCount());
		
//		query.setParameter("renewal_count_of_zero_price_activation_after_1days", liveReport.getRenewalCountOfZeroPriceActivationAfter1Days());
//		query.setParameter("renewal_amount_of_zero_price_activation_after_1days", liveReport.getRenewalAmountOfZeroPriceActivationAfter1Days());
//		query.setParameter("renewal_of_zero_price_activation_after_2days", liveReport.getRenewalCountOfZeroPriceActivationAfter2Days());
//		query.setParameter("renewal_amount_of_zero_price_activation_after_2days", liveReport.getRenewalAmountOfZeroPriceActivationAfter2Days());
//		query.setParameter("renewal_of_zero_price_activation_after_more_3days", liveReport.getRenewalCountOfZeroPriceActivationEqualOrMore3Days());
//		query.setParameter("renewal_amount_of_zero_price_activation_after_more_3days", liveReport.getRenewalAmountOfZeroPriceActivationEqualOrMore3Days());
		
		query.setParameter("park_to_activation_count", liveReport.getParkingToActivationCount());
		query.setParameter("park_to_activation_amount", liveReport.getParkToActivationAmount());
		
		
		
		query.setParameter("last_activation_time", liveReport.getLastActivationTime());
		query.setParameter("last_activation_time2", liveReport.getLastActivationTime());
		query.setParameter("last_activation_time3", liveReport.getLastActivationTime());
		
		query.setParameter("last_click_time", liveReport.getLastClickTime());
		query.setParameter("last_click_time2", liveReport.getLastClickTime());
		query.setParameter("last_click_time3", liveReport.getLastClickTime());
		
		
		
		query.setParameter("click_count2", liveReport.getClickCount());
		query.setParameter("valid_click_count2", liveReport.getValidClickCount());
		query.setParameter("reverse_click_count2", liveReport.getReverseClickCount());
		query.setParameter("block_click_count2", liveReport.getBlockClickCount());
		query.setParameter("already_subscribed_count2", liveReport.getAlreadySubscribedCount());		
		query.setParameter("conversion_count2", liveReport.getConversionCount());
		query.setParameter("send_conversion_count2", liveReport.getSendConversionCount());
		query.setParameter("send_manual_conversion_count2", liveReport.getSendManualConversionCount());
		query.setParameter("send_auto_conversion_count2", liveReport.getSendAutoConversionCount());
		query.setParameter("grace_conversion_count2", liveReport.getGraceConversionCount());
		query.setParameter("grace_send_conversion_count2", liveReport.getGraceSendConversionCount());
		query.setParameter("duplicate_conversion_count2", liveReport.getDuplicateConversionCount());
		query.setParameter("amount2", liveReport.getAmount());
		query.setParameter("duplicate_click_count2", liveReport.getDuplicateClickCount());
		query.setParameter("mismatch_op_conversion_count2", liveReport.getMismatchOperatorConversionCount());
		
		query.setParameter("dct_count2", liveReport.getDctCount());
		query.setParameter("dct_churn_count2", liveReport.getChurnDctCount());
		
		query.setParameter("duplcate_dct_count2", liveReport.getDuplcateDctCount());
		query.setParameter("dct_send_count2", liveReport.getDctSendCount());
		
		query.setParameter("renewal_count2", liveReport.getRenewalCount());
		query.setParameter("renewal_amount2", liveReport.getRenewalAmount());
		query.setParameter("renewal_sentcount2", liveReport.getRenewallSentCount());
		query.setParameter("action_hours", liveReport.getReportDate());
		
//		query.setParameter("renewal_count_of_zero_price_activation_after_1days2", liveReport.getRenewalCountOfZeroPriceActivationAfter1Days());
//		query.setParameter("renewal_amount_of_zero_price_activation_after_1days2", liveReport.getRenewalAmountOfZeroPriceActivationAfter1Days());
//		query.setParameter("renewal_of_zero_price_activation_after_2days2", liveReport.getRenewalCountOfZeroPriceActivationAfter2Days());
//		query.setParameter("renewal_amount_of_zero_price_activation_after_2days2", liveReport.getRenewalAmountOfZeroPriceActivationAfter2Days());
//		query.setParameter("renewal_of_zero_price_activation_after_more_3days2", liveReport.getRenewalCountOfZeroPriceActivationEqualOrMore3Days());
//		query.setParameter("renewal_amount_of_zero_price_activation_after_more_3days2", liveReport.getRenewalAmountOfZeroPriceActivationEqualOrMore3Days());		
		
		query.setParameter("park_to_activation_count2", liveReport.getParkingToActivationCount());
		query.setParameter("park_to_activation_amount2", liveReport.getParkToActivationAmount());
		
		
		query.setParameter("subscription_failed", liveReport.getSubscriptionFailed());
		query.setParameter("subscription_failed2", liveReport.getSubscriptionFailed());
		query.setParameter("mode", liveReport.getMode());
		
		query.setParameter("pin_validation_count", liveReport.getPinValidationCount());
		query.setParameter("pin_validation_count2", liveReport.getPinValidationCount());
		query.setParameter("pin_send_count", liveReport.getPinSendCount());
		query.setParameter("pin_send_count2", liveReport.getPinSendCount());
		
		logger.debug("generateLiveReport::queryStr:  "+query.toString());
		return query.executeUpdate()>0;		
				
	}

	@Override
	public List<BlockCircle> findBlockCircle() {
		
		Query query = entityManager.createNamedQuery("BlockCircle.findBlockCircle"
				, BlockCircle.class);
		query.setParameter("status", true);	
		
		return query.getResultList();	
	}

//	@Override
//	public List<PostpaidBlockCircle> findPostpaidBlockCircle() {
//		Query query = entityManager.createNamedQuery("PostpaidBlockCircle.findPostpaidBlockCircle"
//				, PostpaidBlockCircle.class);
//		query.setParameter("status", true);		
//		return query.getResultList();	
//	}

	@Override
	public List<IPPool> findAllIpPool(boolean status) {
		Query query = entityManager.createNamedQuery("IPPool.findAllEnable");
		query.setParameter("status", status);
		return query.getResultList();
	}
	
	
	
	@Override
	public Map<String,List<LiveReport>> findCurrentDateReport(List<Integer> opIdList) {
		
		Map<String,List<LiveReport>> map=new HashMap<String,List<LiveReport>>();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		 String  queryStr="select if(vwlive.operator_id is not null,vwlive.operator_id,0) as operatorId,"
     + "if(vwlive.operator_name is not null,vwlive.operator_name,'Other') as operatorName,vwlive.report_date as reportDate,"
     + "if(vwlive.network_name is null,'Other',vwlive.network_name) as networkName,"
     + "vwlive.adnetwork_campaign_id as adnetworkCampaignId,"
     + "COALESCE(sum(vwlive.click_count),0) as clickCount,COALESCE(sum(vwlive.duplicate_click_count),0) as duplicateClickCount,"
     + "COALESCE(sum(vwlive.reverse_click_count),0) as reverseClickCount,"
     + "COALESCE(sum(vwlive.block_click_count),0) as blockClickCount,"
     + "COALESCE(sum(vwlive.already_subscribed_count),0) as alreadySubscribedCount,COALESCE(sum(vwlive.valid_click_count),0) as validClickCount,"
     + "COALESCE(sum(vwlive.conversion_count),0) as conversionCount,"
     + "COALESCE(sum(vwlive.send_conversion_count),0) as sendConversionCount,"
       +"COALESCE(sum(vwlive.amount),0) as amount,round(sum(cpa.cpa_value*(vwlive.send_conversion_count)/67),2)"
       + " as spend,COALESCE(sum(dct_count),0) as dctCount,COALESCE(sum(dct_churn_count),0) as churnDctCount"
       +",COALESCE(sum(renewal_count),0) as renewalCount,COALESCE(sum(renewal_sentcount),0) as renewalSentCount"
       + ",COALESCE(sum(renewal_amount),0) as renewalAmount"
       +",COALESCE(sum(grace_conversion_count),0) as graceConversionCount,"
       + "COALESCE(sum(grace_send_conversion_count),0) as graceSendConversionCount,"
       + "COALESCE(sum(send_manual_conversion_count),0) as sendManualConversionCount,"
       + "max(last_activation_time) as lastActivationTime,"
       + "max(last_click_time) as lastClickTime "  
      // + "COALESCE(sum(renewal_count_of_zero_price_activation_after_1days),0) as renewalCountOfZeroPriceActivationAfter1Days,"
      // + "COALESCE(sum(renewal_amount_of_zero_price_activation_after_1days),0) as renewalAmountOfZeroPriceActivationAfter1Days,"
	//	+ "COALESCE(sum(renewal_of_zero_price_activation_after_2days),0) as renewalCountOfZeroPriceActivationAfter2Days"
	//	+ ",COALESCE(sum(renewal_amount_of_zero_price_activation_after_2days),0) as renewalAmountOfZeroPriceActivationAfter2Days,"
	//	+ "COALESCE(sum(renewal_of_zero_price_activation_after_more_3days),0) as renewalCountOfZeroPriceActivationEqualOrMore3Days"
	//	+ ",COALESCE(sum(renewal_amount_of_zero_price_activation_after_more_3days),0) as renewalAmountOfZeroPriceActivationEqualOrMore3Days "
       + " from vw_live_report_monthly vwlive left join tb_adnetwork_cpa_config cpa "
       + " on vwlive.adnetworkid =cpa.adnetwork_id and cpa.operator_id=vwlive.operator_id where  date(report_date)=date(now()) ";
      if(opIdList!=null&&opIdList.size()>0){
    	  queryStr+=" and  vwlive.operator_id in (:opIdList) ";
    	  parameters.put("opIdList", opIdList);
      }
      
      queryStr+= " group by 1,3,4 order by 1,3 ";
		 logger.debug("findCurrentDateReport:: queryStr:: "+queryStr);
		 
		 List<LiveReport> list = query(queryStr, parameters,
					new BeanPropertyRowMapper<LiveReport>(LiveReport.class));
		 map.put("currentDateReportList",list);
		  queryStr="select if(vwlive.operator_id is not null,vwlive.operator_id,'0') as operatorId,"
		 		+ " sum(conversion_count) as conversionCount,sum(dct_churn_count) as churnDctCount"		 		
		 		+ " from tb_live_report vwlive  where month(report_date)=month(now()) group by 1";
		 List<LiveReport> currentMTDChurn = query(queryStr, parameters,
					new BeanPropertyRowMapper<LiveReport>(LiveReport.class));
		 map.put("currentMTDChurn",currentMTDChurn);
		 
		 queryStr="select if(vwlive.operator_id is not null,vwlive.operator_id,'0') as operatorId,"		 		
			 		+ " sum(conversion_count) as conversionCount,sum(dct_churn_count) as churnDctCount"		 		
			 		+ " from tb_live_report vwlive  "
			 		+ " where date(report_date)=date(adddate(now(),interval -1 day)) group by 1";
		 List<LiveReport> lastDayChurn = query(queryStr, parameters,
					new BeanPropertyRowMapper<LiveReport>(LiveReport.class));
		 map.put("lastDayChurn",lastDayChurn);
		 
		 
//		 queryStr="select if(vwlive.operator_id is not null,vwlive.operator_id,'0') as operatorId,ifnull(sum(conversion_count),0) as "
//		 		+ "conversionCount,ifnull(sum(dct_churn_count),0) as churnDctCount from tb_live_report vwlive where date(vwlive.report_date) "
//		 		+ "between date(adddate(now(),interval -3 day)) "
//		 		+ "and date(adddate(now(),interval -1 day))  and vwlive.operator_id="+MConstants.AIRTEL_OPERATOR_ID;
//		 List<LiveReport> last3DayChurn = query(queryStr, parameters,
//					new BeanPropertyRowMapper<LiveReport>(LiveReport.class));
//		 map.put("last3DayChurn",last3DayChurn);
		 
		 return map;
	}


	@Override
	public List<AdnetworkTypeConfig> findAdnetworkTypeConfig() {		
		Query query = entityManager.createNamedQuery("AdnetworkTypeConfig.findAdnetworkTypeConfig");
		query.setParameter("status", true);
		return query.getResultList();
	}

	@Override
	public List<Circleid> findCircleid() {		
		Query query = entityManager.createNamedQuery("Circleid.findCircleid");		
		return query.getResultList();
	}

	@Override
	public List<OperatorCircles> findEnableOperatorCircles() {
		Query query = entityManager.createNamedQuery("OperatorCircles.findEnableOperatorCircles");	
		query.setParameter("status", true);
		return query.getResultList();
	}
	

	
	@Override
	public SubscriberReg searchSubscriberByLcId(String lifeCycleId) {
		TypedQuery<SubscriberReg>
		query = entityManager.createNamedQuery("SubscriberReg.searchSubscriberByLcId",SubscriberReg.class);
		query.setParameter("lifeCycleId", lifeCycleId);
		return query.getSingleResult();
	}

	@Override
	public List<Operator> findAllOperator() {
		TypedQuery<Operator>
		query = entityManager.createNamedQuery("Operator.findAllOperator",Operator.class);	
		return query.getResultList();
	}

	@Override
	public Map<Integer, Double> findOperatorWiseActivationAndChurnCount() {
		String queryStr="select operator_id,"
				+ "ifnull((sum(dct_churn_count)*100)/sum(conversion_count),0) as churnpercentage"
				+ " from tb_live_report where date(report_date)=date(now())";
		Query query=entityManager.createNativeQuery(queryStr);
		List<Object[]> list=query.getResultList();
		Map<Integer,Double> map=new HashMap<Integer,Double>();
		list.stream().forEach((record) -> {
	        Integer opId = ((BigInteger) record[0]).intValue();
	        Double percentage = ((BigDecimal) record[1]).doubleValue();
	       map.put(opId, percentage);
	});
		return map;		
	}

	@Override
	public Map<String, List<LiveReport>> findHourlyReport(Integer opId,Integer productId) {
	Map<String,List<LiveReport>> map=new HashMap<String,List<LiveReport>>();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		String queryStr="SELECT a.operator_id AS operatorId,a.report_date AS reportDate,a.action_hours AS actionHours,"
				+ "a.adnetwork_campaign_id AS adnetworkCampaignId,b.campaign_name AS adnetworkCampaignName,"
				+"c.product_id AS productId,c.service_id AS serviceId,"
				+ "SUM(a.conversion_count) AS conversionCount,SUM(a.amount) AS amount,"
				+ "SUM(a.renewal_count) AS renewalCount, SUM(a.renewal_amount) AS renewalAmount,"
				+ " a.grace_conversion_count AS parkingCount, "  //grace_conversion_count-BSNL ALL
				+ " SUM(a.dct_count) AS dctCount FROM tb_live_report a "
				+ " LEFT JOIN vw_service_campaign_detail b ON a.adnetwork_campaign_id=b.campaign_id "
				+" left join tb_service c on a.service_id=c.service_id "
				+ " WHERE DATE(report_date)>=DATE(NOW() - INTERVAL 1 DAY)"
				+ " and a.operator_id="+opId+" ";
		
			if(productId!=null){
				queryStr+=" and c.product_id="+productId;
			}			
			queryStr+= " GROUP BY 1,2,3,4 ORDER BY 1,2,3 DESC";

		logger.info("findHourlyReport:: queryStr:: "+queryStr+" , opId: "+opId+" ,productId: "+productId );
		 List<LiveReport> report = query(queryStr, parameters,
					new BeanPropertyRowMapper<LiveReport>(LiveReport.class));
		 map.put("report",report);		 
		 return map;		
	}


//	@Override
//	public Map<String, List<LiveReport>> findHourlyReport(Integer opId,Integer productId) {
//	Map<String,List<LiveReport>> map=new HashMap<String,List<LiveReport>>();
//		
//		Map<String, Object> parameters = new HashMap<String, Object>();
////		String queryStr="SELECT operator_id as operatorId,report_date as reportDate"
////				+ ",action_hours as actionHours,SUM(conversion_count) as conversionCount,"
////				+"a.adnetwork_campaign_id AS adnetworkCampaignId,b.campaign_name AS adnetworkCampaignName,"
////				+"sum(amount) as amount,"
////				+ "SUM(renewal_count) as renewalCount, "
////				+ "SUM(renewal_amount) as renewalAmount, "
////				+ "SUM(if(type='543132' or type='543119' or type='543124',conversion_count,0)) as parkingActivationCount, "
////				+ "SUM(renewal_count_of_zero_price_activation_after_1days) as renewalCountOfZeroPriceActivationAfter1Days, "
////				+ "SUM(renewal_amount_of_zero_price_activation_after_1days) as renewalAmountOfZeroPriceActivationAfter1Days, "
////				+ "SUM(dct_count) as dctCount "				
////				+ " FROM tb_live_report WHERE DATE(report_date)>=DATE(NOW() - INTERVAL 1 DAY) "
////				+ " GROUP BY 1,2,3 ORDER BY 1,2,3 desc";
////		String queryStr="SELECT a.operator_id AS operatorId,a.report_date AS reportDate,a.action_hours AS actionHours,"
////				+ "a.adnetwork_campaign_id AS adnetworkCampaignId,b.campaign_name AS adnetworkCampaignName,"
////				+ "SUM(a.conversion_count) AS conversionCount,SUM(a.amount) AS amount,"
////				+ "SUM(a.renewal_count) AS renewalCount, SUM(a.renewal_amount) AS renewalAmount,"
////				+ "SUM(IF(a.type='543132' OR a.type='543119' OR a.type='543124',a.conversion_count,"//Airtel 
////				+ " if(a.type='"+MConstants.GRACE+"',a.grace_conversion_count"  //grace_conversion_count-BSNL ALL
////				+ ",0))) AS parkingActivationCount,"
////				+ " "
////				+ "SUM(a.renewal_count_of_zero_price_activation_after_1days) AS renewalCountOfZeroPriceActivationAfter1Days,"
////				+ " SUM(a.renewal_amount_of_zero_price_activation_after_1days) AS renewalAmountOfZeroPriceActivationAfter1Days,"
////				+ " SUM(a.dct_count) AS dctCount FROM tb_live_report a "
////				+ "LEFT JOIN tb_campaign_details b ON a.adnetwork_campaign_id=b.campaign_id "
////				+ " WHERE DATE(report_date)>=DATE(NOW() - INTERVAL 1 DAY) and a.operator_id="+opId+"  GROUP BY 1,2,3,4 ORDER BY 1,2,3 DESC";
//		
//		String queryStr="SELECT a.operator_id AS operatorId,a.report_date AS reportDate,a.action_hours AS actionHours,"
//				+ "a.adnetwork_campaign_id AS adnetworkCampaignId,b.campaign_name AS adnetworkCampaignName,"
//				+"b.product_id AS productId,"
//				+ "SUM(a.conversion_count) AS conversionCount,SUM(a.amount) AS amount,"
//				+ "SUM(a.renewal_count) AS renewalCount, SUM(a.renewal_amount) AS renewalAmount,"
//				//+ "SUM(IF(a.type='543132' OR a.type='543119' OR a.type='543124',a.conversion_count,"//Airtel 
//				+ " a.grace_conversion_count AS parkingCount, "  //grace_conversion_count-BSNL ALL
//				
//				//+ " AS graceConversionCount,"
//				//+ " "
//				//+ "SUM(a.renewal_count_of_zero_price_activation_after_1days) AS renewalCountOfZeroPriceActivationAfter1Days,"
//				//+ " SUM(a.renewal_amount_of_zero_price_activation_after_1days) AS renewalAmountOfZeroPriceActivationAfter1Days,"
//				//+ "SUM(a.park_to_activation_count) AS parkingToActivationCount,"
//				//+ " SUM(a.park_to_activation_amount) AS parkToActivationAmount,"				
//				+ " SUM(a.dct_count) AS dctCount FROM tb_live_report a "
//				+ " LEFT JOIN vw_service_campaign_detail b ON a.adnetwork_campaign_id=b.campaign_id "
//				+ " WHERE DATE(report_date)>=DATE(NOW() - INTERVAL 1 DAY)"
//				+ " and a.operator_id="+opId+" ";
//			if(productId!=null){
//				queryStr+=" and b.product_id="+productId;
//			}			
//			queryStr+= " GROUP BY 1,2,3,4 ORDER BY 1,2,3 DESC";
//
//		logger.info("findHourlyReport:: queryStr:: "+queryStr);
//		 List<LiveReport> report = query(queryStr, parameters,
//					new BeanPropertyRowMapper<LiveReport>(LiveReport.class));
//		 map.put("report",report);		 
//		 return map;		
//	}

	@Override
	public List<VWServiceCampaignDetail> findEnableVWServiceCampaignDetail() {
		TypedQuery<VWServiceCampaignDetail>
		query = entityManager.createNamedQuery("VWServiceCampaignDetail.findEnableVWServiceCampaignDetail",VWServiceCampaignDetail.class);
		query.setParameter("campaignDetailsStatus", true);
		query.setParameter("serviceStatus", true);
		return query.getResultList();
	}

	@Override
	public List<Operator> findAllEnabledOperator() {
		TypedQuery<Operator>
		query = entityManager.createNamedQuery("Operator.findAllEnabledOperator",Operator.class);	
		return query.getResultList();
	}

	@Override
	@Transactional
	public boolean createChurnData(AdnetworkChurnData adnetworkChurnData) {
		String queryStr="INSERT INTO tb_adnetwork_churn(adnetwork_campaign_id,pub_id,circle_name,device,"
				+ "activation_count,churn_count,churn_date,hour)"
				+ " VALUES (:adnetwork_campaign_id,:pub_id,:circle_name,:device,"
				+ ":activation_count,:churn_count,:churn_date,hour(:hour))"
				+ " ON DUPLICATE KEY UPDATE "
				+ " activation_count=activation_count+:activation_count2,"
				+ " churn_count=churn_count+:churn_count2";
		Query query = entityManager.createNativeQuery(queryStr);
		query.setParameter("adnetwork_campaign_id", adnetworkChurnData.getAdnetworkCampaignId());
		query.setParameter("pub_id", adnetworkChurnData.getPubId());
		query.setParameter("circle_name", adnetworkChurnData.getCircleName());
		query.setParameter("device", adnetworkChurnData.getDevice());
		query.setParameter("activation_count", adnetworkChurnData.getActivationCount());
		query.setParameter("churn_count", adnetworkChurnData.getChurnCount());
		query.setParameter("churn_date", adnetworkChurnData.getChurnDate());
		query.setParameter("hour", adnetworkChurnData.getChurnDate());
		query.setParameter("activation_count2", adnetworkChurnData.getActivationCount());
		query.setParameter("churn_count2", adnetworkChurnData.getChurnCount());
		return query.executeUpdate()>0;
	}

	@Override
	public Map<String, Boolean> findBlockedPubId() {
		String queryStr="select pub_id,status from tb_blocked_pub_id where status=1";
		Query query = entityManager.createNativeQuery(queryStr);
		List<Object[]> list=query.getResultList();
		Map<String,Boolean> map=new HashMap<String,Boolean>();
		list.stream().forEach((record) -> {
	        String  pubId = ((String) record[0]);
	        Boolean status = ((Boolean) record[1]).booleanValue();
	       map.put(pubId, status);
	});
		return map;
	}

	@Override
	public List<VWServiceCampaignDetail> findEnableSmartVWServiceCampaignDetail() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		String queryStr="select b.* from vw_smart_service_campaign_detail b";
		 List<VWServiceCampaignDetail> list = query(queryStr, parameters,
					new BeanPropertyRowMapper<VWServiceCampaignDetail>(VWServiceCampaignDetail.class));
		 return list;
	}
	
	@Override
	public List<LiveReport> findDateWiseReport(Integer opId,Integer month,String  year) {
		
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		//Map<String,List<LiveReport>> map=new HashMap<String,List<LiveReport>>();
		
		//Map<String, Object> parameters = new HashMap<String, Object>();
		 String  queryStrr="SELECT report_date AS reportDate,operator_name AS operatorName,SUM(conversion_count) AS "
		 		+ "conversionCount,SUM(amount) AS amount,SUM(renewal_count) AS renewalCount,SUM(renewal_amount) AS "
		 		+ "renewalAmount FROM `vw_live_report_monthly` WHERE operator_id="+opId+" AND MONTH(report_date)="+month+" AND"
		 		+ " YEAR(report_date)=YEAR('"+year+"') GROUP BY 1,2 ORDER BY 1 DESC";
		 logger.debug("findCurrentDateReport:: queryStrr:: "+queryStrr);
		 System.out.println(":::::::::::::"+queryStrr);
		 List<LiveReport> list = query(queryStrr, parameters,
					new BeanPropertyRowMapper<LiveReport>(LiveReport.class));
		 
		 return list;
	}

	

	@Override
	public Map<String,List<LiveReport>> findOredooKuwaitCGNotificationReport(
			Timestamp fromDate, Timestamp toDate) {
		Map<String, Object> parameters = new HashMap<String, Object>();
      
		Map<String,List<LiveReport>> map=new HashMap<String,List<LiveReport>>();
		
		String queryStr="SELECT date(request_time) as  reportDateStr,"
				+ "charge_amount as amount,count(1) as conversionCount "
				+ "  FROM  tb_oredoo_kuwait_notification WHERE action_key='ACT' "
				+ " and campaign_id=43 and (send_to_adnetwork=1 or charge_amount=0) ";
		
		if(fromDate!=null){
			 queryStr+= " and date(request_time)>=date(:fromDate)";
			 parameters.put("fromDate",fromDate);
		}
	
		if(toDate!=null){
			 queryStr+= " and date(request_time)<=date(:toDate)";
			 parameters.put("toDate",toDate);
		}
		
		queryStr+= " group by 1,2 order by 1 desc ";
		 logger.debug("findOredooKuwaitCGNotificationReport:: queryStr:: "+queryStr);
		List<LiveReport> list = query(queryStr, parameters,
				new BeanPropertyRowMapper<LiveReport>(LiveReport.class));
	
		map.put("oredooReport",list);
		
		queryStr="select "
		    + "date(vwlive.report_date) as reportDateStr,"   
		    + "vwlive.adnetwork_campaign_id as adnetworkCampaignId,"
		    + "COALESCE(sum(vwlive.click_count),0) as clickCount,"
		    +"COALESCE(sum(vwlive.valid_click_count),0) as validClickCount,"
		    + "COALESCE(sum(vwlive.duplicate_click_count),0) as duplicateClickCount,"
		    + "COALESCE(sum(vwlive.reverse_click_count),0) as reverseClickCount"
		    + " from vw_live_report_monthly vwlive "
		    + "  where vwlive.adnetwork_campaign_id=43  ";
         queryStr+= " group by 1,2 order by 1 desc";
		 logger.debug("findOredooKuwaitCGNotificationReport:: queryStr:: "+queryStr);
		 list = query(queryStr, parameters,
					new BeanPropertyRowMapper<LiveReport>(LiveReport.class));
		 map.put("currentDateReportList",list);
		return map;		
	}	
	
	
//	@Override
//	public List<LiveReport> findAggReport(AggReport aggReport) {
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		
//		String  queryStr="select if(vwlive.operator_id is not null,vwlive.operator_id,0) as operatorId,"
//			     + "if(vwlive.operator_name is not null,vwlive.operator_name,'Other') as operatorName,";
//		if(aggReport.getReportType()!=null&&aggReport.getReportType().equalsIgnoreCase(MConstants.MONTHLY_REPORT_TYPE)){	     
//		queryStr+=  "concat(MONTHNAME(vwlive.report_date),'-',year(vwlive.report_date)) as reportDateStr,";
//		}else{
//			queryStr+=  "vwlive.report_date as reportDateStr,";
//		}
//		queryStr+=" if(vwlive.network_name is null,'Other',vwlive.network_name) as networkName,"
//			     + "vwlive.adnetwork_campaign_id as adnetworkCampaignId," 
//			     + "vwlive.adnetworkid as adnetworkId,"
//			     + "vwlive.aggregator_id as aggregatorId,"
//			     + "vwscd.product_id as productId,"
//				 +" COALESCE(sum(vwlive.click_count),0) as clickCount,"
//				 +" COALESCE(sum(vwlive.valid_click_count),0) as validClickCount,"
//			     + "COALESCE(sum(vwlive.conversion_count),0) as conversionCount,"
//			     + "COALESCE(sum(vwlive.send_conversion_count),0) as sendConversionCount,"
//			     +"COALESCE(sum(vwlive.amount),0) as amount,"
//			     //+ "round(sum(cpa.cpa_value*(vwlive.send_conversion_count)/67),2) as spend,"
//			     + "COALESCE(sum(dct_count),0) as dctCount,COALESCE(sum(dct_churn_count),0) as churnDctCount"
//			     +",COALESCE(sum(renewal_count),0) as renewalCount,"
//			     + "COALESCE(sum(renewal_sentcount),0) as renewalSentCount"
//			     + ",COALESCE(sum(renewal_amount),0) as renewalAmount"
//			     + ",COALESCE(sum(grace_conversion_count),0) as graceConversionCount"
//			     + ",COALESCE(sum(dct_churn_count),0) as churnDctCount " 
//			     + ",COALESCE(sum(dct_count),0) as dctCount " 
//			     //+ ",COALESCE(sum(subscription_failed),0) as subscriptionFailed " 
//			    		     
//			     + " from vw_live_report_monthly vwlive left join vw_service_campaign_detail vwscd "
//			     + " on vwlive.adnetworkid =vwscd.ad_network_id and vwscd.op_id=vwlive.operator_id "
//			     + " and vwlive.service_id =vwscd.service_id "
//			   //  +" left join tb_operators op on vwlive.operator_id=op.operator_id "
//			     + " where  1=1 ";
//		
//			if(aggReport.getFromTime()!=null){
//				 queryStr+= " and date(report_date)>=date(:fromTime)";
//				 parameters.put("fromTime",aggReport.getFromTime());
//			}//date(report_date)=date(now())
//		
//			if(aggReport.getToTime()!=null){
//				 queryStr+= " and date(report_date)<=date(:toTime)";
//				 parameters.put("toTime",aggReport.getToTime());
//			}
//			if(aggReport.getFromTime()==null&&aggReport.getToTime()==null){
//				queryStr+= " and date(report_date)=date(now())";
//			}
//			
//			if(aggReport.getAggregatorId()!=null){
//				queryStr+= " and vwlive.aggregator_id=:aggregator_id";
//				parameters.put("aggregator_id",aggReport.getAggregatorId());
//			}
//			
//			if(aggReport.getAdnetworkId()!=null&&aggReport.getAdnetworkId()>0){
//				queryStr+= " and vwlive.adnetworkid=:adnetworkid";
//				parameters.put("adnetworkid",aggReport.getAdnetworkId());
//			}
//			if(aggReport.getOpid()!=null&&aggReport.getOpid()>0){
//				queryStr+= " and vwlive.operator_id=:operator_id";
//				parameters.put("operator_id",aggReport.getOpid());
//			}
//			if(aggReport.getProductId()!=null){
//				queryStr+= " and vwscd.product_id=:product_id";
//				parameters.put("product_id",aggReport.getProductId());
//			}
//			
//		   queryStr+= " group by 1,3 order by 1 asc,3 asc";
//		   logger.info("query str: "+queryStr+" ,parameters:: "+parameters);
//		 List<LiveReport> list = query(queryStr, parameters,new BeanPropertyRowMapper<LiveReport>(LiveReport.class));
//		 return list;
//	}
	
	
	@Override
	public List<LiveReport> findAggReport(AggReport aggReport) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		String  queryStr="select if(vwlive.operator_id is not null,vwlive.operator_id,0) as operatorId,"
			     + "if(vwlive.operator_name is not null,vwlive.operator_name,'Other') as operatorName,";
		if(aggReport.getReportType()!=null&&aggReport.getReportType().equalsIgnoreCase(MConstants.MONTHLY_REPORT_TYPE)){	     
		queryStr+=  "concat(MONTHNAME(vwlive.report_date),'-',year(vwlive.report_date)) as reportDateStr,";
		}else{
			queryStr+=  "vwlive.report_date as reportDateStr,";
		}
		
		queryStr+=" if(vwlive.network_name is null,'Other',vwlive.network_name) as networkName,"
			     + "vwlive.adnetwork_campaign_id as adnetworkCampaignId," 
			     + "vwlive.adnetworkid as adnetworkId,"
			     + "vwlive.aggregator_id as aggregatorId,"
			     + "vwlive.product_id as productId,"
				 +" COALESCE(sum(vwlive.click_count),0) as clickCount,"
				 +" COALESCE(sum(vwlive.valid_click_count),0) as validClickCount,"
			     + "COALESCE(sum(vwlive.conversion_count),0) as conversionCount,"
			     + "COALESCE(sum(vwlive.send_conversion_count),0) as sendConversionCount,"
			     +"COALESCE(sum(vwlive.amount),0) as amount,"
			     + "COALESCE(sum(dct_count),0) as dctCount,COALESCE(sum(dct_churn_count),0) as churnDctCount"
			     +",COALESCE(sum(renewal_count),0) as renewalCount,"
			     + "COALESCE(sum(renewal_sentcount),0) as renewalSentCount"
			     + ",COALESCE(sum(renewal_amount),0) as renewalAmount"
			     + ",COALESCE(sum(grace_conversion_count),0) as graceConversionCount"
			     + ",COALESCE(sum(dct_churn_count),0) as churnDctCount " 
			     + ",COALESCE(sum(dct_count),0) as dctCount " 
			     
                 + ",COALESCE(sum(if(mode='"+SubscriptionMode.SMS.getMode()+"',conversion_count,0)),0) as smsConversionCount "
                 + ",COALESCE(sum(if(mode='"+SubscriptionMode.SMS.getMode()+"',amount,0)),0) as smsConversionAmount "
                 + ",COALESCE(sum(if(mode='"+SubscriptionMode.SMS.getMode()+"',renewal_count,0)),0) as smsRenwalCount "
                 + ",COALESCE(sum(if(mode='"+SubscriptionMode.SMS.getMode()+"',renewal_amount,0)),0) as smsRenewalAmount "
                 + ",COALESCE(sum(if(mode='"+SubscriptionMode.SMS.getMode()+"',grace_conversion_count,0)),0) as smsGraceCount "
                  
			     + " from vw_live_report_monthly vwlive   "
			    // + " on vwlive.adnetworkid =vwscd.ad_network_id and vwscd.op_id=vwlive.operator_id "
			    // + " and vwlive.service_id =vwscd.service_id "			  
			     + " where  1=1 ";
		
			if(aggReport.getFromTime()!=null){
				 queryStr+= " and date(report_date)>=date(:fromTime)";
				 parameters.put("fromTime",aggReport.getFromTime());
			}//date(report_date)=date(now())
		
			if(aggReport.getToTime()!=null){
				 queryStr+= " and date(report_date)<=date(:toTime)";
				 parameters.put("toTime",aggReport.getToTime());
			}
			if(aggReport.getFromTime()==null&&aggReport.getToTime()==null){
				queryStr+= " and date(report_date)=date(now())";
			}
			
			if(aggReport.getAggregatorId()!=null){
				queryStr+= " and vwlive.aggregator_id=:aggregator_id";
				parameters.put("aggregator_id",aggReport.getAggregatorId());
			}
			
			if(aggReport.getAdnetworkId()!=null&&aggReport.getAdnetworkId()>0){
				queryStr+= " and vwlive.adnetworkid=:adnetworkid";
				parameters.put("adnetworkid",aggReport.getAdnetworkId());
			}
			
			if(aggReport.getOpid()!=null&&aggReport.getOpid()>0){
				queryStr+= " and vwlive.operator_id=:operator_id";
				parameters.put("operator_id",aggReport.getOpid());
			}
			
			if(aggReport.getProductId()!=null){
				queryStr+= " and vwlive.product_id=:product_id";
				parameters.put("product_id",aggReport.getProductId());
			}
			
		   queryStr+= " group by 1,3 order by 1 asc,3 asc";
		   logger.info("query str: "+queryStr+" ,parameters:: "+parameters);
		 List<LiveReport> list = query(queryStr, parameters,new BeanPropertyRowMapper<LiveReport>(LiveReport.class));
		 return list;
	}


	@Override
	public List<LiveReport> findSwaziAggReport(AggReport aggReport) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		String  queryStr="select if(vwlive.operator_id is not null,vwlive.operator_id,0) as operatorId,"
			     + "if(vwlive.operator_name is not null,vwlive.operator_name,'Other') as operatorName,";
		
			queryStr+=  "vwlive.report_date as reportDate,";
		
		
		queryStr+=" if(vwlive.network_name is null,'Other',vwlive.network_name) as networkName,"
			    + "vwlive.service_id as serviceId,"   
			    + "COALESCE(sum(vwlive.click_count),0) as clickCount,"
			    + "COALESCE(sum(vwlive.valid_click_count),0) as validClickCount,"
			    + "COALESCE(sum(vwlive.duplicate_click_count),0) as duplicateClickCount,"
			    + "COALESCE(sum(vwlive.block_click_count),0) as blockClickCount,"
			    + "COALESCE(sum(vwlive.already_subscribed_count),0) as alreadySubscribedCount,"
			     + "COALESCE(sum(vwlive.conversion_count),0) as conversionCount,"
			     + "COALESCE(sum(vwlive.send_conversion_count),0) as sendConversionCount,"
			     +"COALESCE(sum(vwlive.amount),0) as amount,"
			   //  + "round(sum(cpa.cpa_value*(vwlive.send_conversion_count)/67),2) as spend"
			     + " COALESCE(sum(dct_count),0) as dctCount"
			     +",COALESCE(sum(renewal_count),0) as renewalCount"
			     + ",COALESCE(sum(renewal_amount),0) as renewalAmount"
			     + ",COALESCE(sum(grace_conversion_count),0) as graceConversionCount"
			     + ",COALESCE(sum(subscription_failed),0) as subscriptionFailed " 
			    // + ",COALESCE(sum(dct_count),0) as dctCount " 
			     + " from vw_live_report_monthly vwlive  "
			     + " where  1=1 ";
		
			if(aggReport.getFromTime()!=null){
				 queryStr+= " and date(report_date)>=date(:fromTime)";
				 parameters.put("fromTime",aggReport.getFromTime());
			}//date(report_date)=date(now())
		
			if(aggReport.getToTime()!=null){
				 queryStr+= " and date(report_date)<=date(:toTime)";
				 parameters.put("toTime",aggReport.getToTime());
			}
			if(aggReport.getFromTime()==null&&aggReport.getToTime()==null){
				queryStr+= " and date(report_date)=date(now())";
			}
			
//			if(aggReport.getAdnetworkId()!=null&&aggReport.getAdnetworkId()>0){
//				queryStr+= " and vwlive.adnetworkid=:adnetworkid";
//				parameters.put("adnetworkid",aggReport.getAdnetworkId());
//			}
			
			if(aggReport.getOpid()!=null&&aggReport.getOpid()>0){
				queryStr+= " and vwlive.operator_id=:operator_id";
				parameters.put("operator_id",aggReport.getOpid());
			}
			
		   queryStr+= " group by 1,3,5 order by 1 asc,3 asc";
		   logger.info("query str: "+queryStr);
		 List<LiveReport> list = query(queryStr, parameters,new BeanPropertyRowMapper<LiveReport>(LiveReport.class));
		 return list;
	}

	
	@Override
	public Map<Integer, Integer> findSubscriberActiveBase(AggReport aggReport) {
		String queryStr="select operator_id,count(1)"				
				+ " from tb_subscribers_reg where status="+MConstants.SUBSCRIBED+
				" and status_descp='"+MConstants.SUBSCRIBED_DESC+"' group by 1 ";
		logger.info("findSubscriberActiveBase:::queryStr::  "+queryStr);
		Query query=entityManager.createNativeQuery(queryStr);
		List<Object[]> list=query.getResultList();
		Map<Integer,Integer> map=new HashMap<Integer,Integer>();
		list.stream().forEach((record) -> {			
	        Integer opId = (Integer) record[0];
	        Integer count = ((BigInteger) record[1]).intValue();
	       map.put(opId, count);
	});

		return map;		
	}


	
	@Override
	public List<Integer> findValidationExpiredSubscriberIdForRenewal(
			List<Integer> operatorId, Integer status) {
		
		
		String sqlString ="select b.subscriber_id from tb_subscribers_reg b "
				+ " where b.operator_id in (:operatorId) and b.status=:status "
				+ " and date(b.validity_to)<=date(now())"
				+ " and (b.last_renewal_retry_date is null or  date(b.last_renewal_retry_date)<date(now())) "
				+ " order by b.subscriber_id desc";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("operatorId", operatorId);
		map.put("status", status);
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(map);
		return queryForList(sqlString, sqlParameterSource,Integer.class);
	}
	
	
	@Override
	public AdnetworkOperatorConfig findAdnetworkOperatorConfigById(
			Integer adnopconfigid) {
		TypedQuery<AdnetworkOperatorConfig>
		query = entityManager.createNamedQuery("AdnetworkOperatorConfig.findAdnetworkOperatorConfigById",AdnetworkOperatorConfig.class);
		query.setParameter("adnetworkOperatorConfigId", adnopconfigid);
		query.setMaxResults(1);
		return query.getSingleResult();
	}


	@Override
	public List<VWAdnetworkOperatorConfig> findAllAdnConfig() {
		TypedQuery<VWAdnetworkOperatorConfig>
		query = entityManager.createNamedQuery("VWAdnetworkOperatorConfig.findAllAdnConfig",VWAdnetworkOperatorConfig.class);
		
		return query.getResultList();
	}



	@Override
	public List<Integer> findValidationExpiredSubscriberIdForRenewal(
			List<Integer> operatorId, Integer status,
			Integer lastRenewalRetryNoOfDay) {
		String sqlString ="select b.subscriber_id from tb_subscribers_reg b "
				+ " where b.operator_id in (:operatorId) and b.status=:status "
				+ " and date(b.validity_to)<date(now())"
				+ " and (b.last_renewal_retry_date is null"
				+ " or  date(b.last_renewal_retry_date)<DATE_ADD(NOW() , INTERVAL -:lastRenewalRetryNoOfDay Day)"
				+ ")  order by b.subscriber_id desc";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("operatorId", operatorId);
		map.put("status", status);
		map.put("lastRenewalRetryNoOfDay", lastRenewalRetryNoOfDay);
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(map);
		return queryForList(sqlString, sqlParameterSource,Integer.class);
	}

	@Override
	public List<Integer> findValidSubscriberId(List<Integer> operatorId,
			Integer status) {

		
		String sqlString="select b.subscriber_id from tb_subscribers_reg b where "
				+ " b.operator_id=:operatorId and "
				+ " b.status=:status and date(b.validity_to)>=date(now())";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("operatorId", operatorId);
		map.put("status", status);
		logger.info("findValidSubscriberId:: sqlString"+sqlString+",  data: "+map);	
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(map);
		return queryForList(sqlString, sqlParameterSource,Integer.class);
		
	}

	@Override
	public List<Integer> findValidationExpiredSubscriberIdForTrueMoveSecondRenewal(
			List<Integer> operatorId, Integer status) {
		
		String sqlString ="select b.subscriber_id from tb_subscribers_reg b "
				+ " where b.operator_id in (:operatorId) and b.status=:status "
				+ " and date(b.sub_date)!=date(now()) "
				+ " and (b.last_renewal_retry_date is null"
				+ " or  date(b.last_renewal_retry_date)<=date(now())) "
				+ " order by b.subscriber_id desc";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("operatorId", operatorId);
		map.put("status", status);
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(map);
		return queryForList(sqlString, sqlParameterSource,Integer.class);
	
	}

	@Override
	public List<Integer> findValidationExpiredSubscriberIdForRenewalForFirstTrueMove(
			List<Integer> operatorId, Integer status) {
		
		
		String sqlString ="select b.subscriber_id from tb_subscribers_reg b "
				+ " where b.operator_id in (:operatorId) and b.status=:status "
				+ " and date(b.sub_date)!=date(now()) "
				+ " and date(b.validity_to)<=date(now())"
				+ " and (b.last_renewal_retry_date is null or  date(b.last_renewal_retry_date)<date(now())) "
				+ " order by b.subscriber_id desc";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("operatorId", operatorId);
		map.put("status", status);
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(map);
		return queryForList(sqlString, sqlParameterSource,Integer.class);
	}	
	@Override
	public List<VWCallbackDump> findVWCallbackDump(AggReport aggReport) {
         Map<String, Object> parameters = new HashMap<String, Object>();
		
		String  queryStr="select b.* from vw_callback_dump b where 1=1 ";
		
		if(!StringUtils.isEmpty(aggReport.getMsisdn())){
			 queryStr+= " and b.msisdn=:msisdn ";
			 parameters.put("msisdn",aggReport.getMsisdn());
		}
		
		if(aggReport.getAggregatorId()!=null){
			queryStr+= " and b.aggregator_id=:aggregator_id " ;
			parameters.put("aggregator_id",aggReport.getAggregatorId());
		}
		
			if(aggReport.getFromTime()!=null){
				 queryStr+= " and date(b.create_time)>=date(:fromTime) ";
				 parameters.put("fromTime",aggReport.getFromTime());
			}//date(create_time)=date(now())
		
			if(aggReport.getToTime()!=null){
				 queryStr+= " and date(b.create_time)<=date(:toTime) ";
				 parameters.put("toTime",aggReport.getToTime());
			}
			if(aggReport.getFromTime()==null&&aggReport.getToTime()==null){
				queryStr+= " and date(b.create_time)=date(now()) ";
			}
			
			if(aggReport.getOpid()!=null&&aggReport.getOpid()>0){
				queryStr+= " and b.operator_id=:operator_id";
				parameters.put("operator_id",aggReport.getOpid());
			}
			
			if(aggReport.getProductId()!=null){
				queryStr+= " and b.product_id=:product_id";
				parameters.put("product_id",aggReport.getProductId());
			}
			
			if(parameters.isEmpty()){
				queryStr+= " order by 1 desc limit 100";
			}
		
		   logger.info("query str: "+queryStr+" ,parameters:: "+parameters);
		 List<VWCallbackDump> list = query(queryStr, parameters,
				 new BeanPropertyRowMapper<VWCallbackDump>(VWCallbackDump.class));
		 return list;
	}
}
