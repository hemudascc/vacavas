package net.jpa.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.persist.bean.SubscriberReg;


public interface JPASubscriberReg extends JpaRepository<SubscriberReg, Long>{

	
	@Query("select b from SubscriberReg b where b.msisdn=:msisdn and  b.productId =:productId")
   SubscriberReg findSubscriberRegByMsisdnAndProductId(@Param("msisdn")String msisdn,@Param("productId")
    Integer productId);
	
	
@Query("select b from SubscriberReg b where b.subscriberId=:subscriberId")
    SubscriberReg findSubscriberRegById(@Param("subscriberId")Integer subscriberId);

//
//	@Query("select b from SubscriberReg b where b.msisdn=:msisdn and b.serviceId=:serviceId")
//    SubscriberReg findSubscriberRegByMsisdnAndServiceId(@Param("msisdn")String msisdn,
//    		@Param("serviceId")Integer serviceId);
//	
//	@Query("select b from SubscriberReg b where b.msisdn=:msisdn and b.status=:status")
//    List<SubscriberReg> findSubscriberRegByMsisdnAndStatus(@Param("msisdn")String msisdn,
//    		@Param("status")Integer status);
//	
	@Query("select b from SubscriberReg b where b.msisdn=:msisdn")
    List<SubscriberReg> findSubscriberRegByMsisdn(@Param("msisdn")String msisdn);
//	
//	@Query("select b from SubscriberReg b where b.operatorId=:operatorId and "
//			+ " b.status=:status and date(b.validityTo)>=date(now())")
//    List<SubscriberReg> findValidSubscriber(@Param("operatorId")Integer operatorId,
//    		@Param("status")Integer status);
//	
//	@Query("select b from SubscriberReg b where b.operatorId=:operatorId and b.status=:status "
//			+ " and date(b.validityTo)<=date(now())"
//			+ " and (b.lastRenewalRetryDate=null or  date(b.lastRenewalRetryDate)<=date(now()))")
//    List<SubscriberReg> findValidationExpiredSubscriberForRenewal(@Param("operatorId")Integer operatorId,
//    		@Param("status")Integer status);
//	
//	
//	@Query("select b from SubscriberReg b where b.operatorId=:operatorId and "
//			+ "b.status=:status and date(b.validityTo)=date(:time) and b.unsubDate=null "
//			+ "and TIMESTAMPDIFF(DAY,b.subDate,b.validityTo)=3")
//    List<SubscriberReg> findValidationBeforeExpiredSubscriber(@Param("operatorId")Integer operatorId,
//    		@Param("status")Integer status,@Param("time")Timestamp time);
//	
//	
//	@Query("select b from SubscriberReg b where b.msisdn=:msisdn and  b.serviceId in (:serviceIds)")
//    List<SubscriberReg> findSubscriberRegByMsisdnAndServiceId(@Param("msisdn")String msisdn,@Param("serviceIds")List<Integer> serviceIds);
	
	@Query("select b from SubscriberReg b where b.msisdn=:msisdn and b.serviceId=:serviceId")
    SubscriberReg findSubscriberRegByMsisdnAndServiceId(@Param("msisdn")String msisdn,
    		@Param("serviceId")Integer serviceId);
	
	@Query("select b from SubscriberReg b where b.msisdn=:msisdn and b.status=:status")
    List<SubscriberReg> findSubscriberRegByMsisdnAndStatus(@Param("msisdn")String msisdn,
    		@Param("status")Integer status);
	
	@Query("select b from SubscriberReg b where b.param1=:param1 ")
    List<SubscriberReg> findSubscriberRegByParam1(@Param("param1")String param1);
	
	
	@Query("SELECT b FROM SubscriberReg b where b.operatorId=:operatorId and b.status=:status")
	  List<SubscriberReg> findSubscriberRegBypIdAnsStatus(@Param("operatorId")Integer operatorId,
			  @Param("status")Integer status);
	
	@Query("select b from SubscriberReg b where b.operatorId in (:operatorIds) and b.status=:status "
			+ " and date(b.validityTo)<=date(now()) "
			+ " and (b.lastRenewalRetryDate is null or  date(b.lastRenewalRetryDate)<date(now())) "
			+ " and b.subscriberId>:subscriberId order by b.subscriberId asc")
    List<SubscriberReg> findValidationExpiredSubscriberForRenewal(@Param("operatorIds")List<Integer> operatorId,
    		@Param("status")Integer status,@Param("subscriberId")Integer subscriberId
    		,Pageable  pageable);
	
	
	@Query("select b from SubscriberReg b where b.operatorId in (:operatorIds) and b.status=:status "
			+ " and date(b.validityTo)=date(:ts)   and  b.productId =:productId order by b.subscriberId asc")
    List<SubscriberReg> findSubscriberForRenewalAlert(@Param("operatorIds")List<Integer> operatorId,
    		@Param("status")Integer status,@Param("productId")Integer productId,@Param("ts")Timestamp ts);
	
	

	@Query("select b from SubscriberReg b where b.operatorId in (:operatorIds) and b.status=:status "
			+ " and date(b.validityTo)>=date(now()) "
			+" order by b.subscriberId asc")
    List<SubscriberReg> findValidSubscriberByOperator(@Param("operatorIds")List<Integer> operatorId,
    		@Param("status")Integer status);
	
}




