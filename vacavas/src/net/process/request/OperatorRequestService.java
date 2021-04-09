package net.process.request;


import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import net.mycomp.actel.ActelService;
import net.mycomp.altruist.AltruistService;
import net.mycomp.comviva.ComvivaService;
import net.mycomp.macrokiosk.thailand.ThialandService;
import net.mycomp.messagecloud.gateway.MCGService;
import net.mycomp.mobimind.MobimindService;
import net.mycomp.mondiapay.MondiaPayService;
import net.mycomp.tpay.TpayService;
import net.persist.bean.SubscriberReg;
import net.process.bean.AdNetworkRequestBean;
import net.process.bean.DeactivationResponse;
import net.util.MConstants;

@Service("operatorRequestService")
public class OperatorRequestService implements IOperatorService{

	@Autowired
	@Qualifier("defaultOperatorService")
	private IOperatorService defaultOperatorService;

	@Autowired
	@Qualifier("tpayService")
	private TpayService tpayService;

	@Autowired
	@Qualifier("actelService")
	private ActelService actelService;

	@Autowired
	@Qualifier("mobimindService")
	private MobimindService mobimindService;

	@Autowired
	@Qualifier("mcgService")
	private MCGService mcgService;
	@Autowired
	@Qualifier("mondiaPayService")
	private MondiaPayService mondiaPayService;
	/*
	 * @Autowired
	 * 
	 * @Qualifier("oredoKuwaitService") private OredoKuwaitService
	 * oredoKuwaitService;
	 */
	@Autowired
	@Qualifier("comvivaService")
	private ComvivaService comvivaService;
	
	@Autowired
	@Qualifier("altruistService")
	private AltruistService altruistService;

	@Autowired
	@Qualifier("thialandService")
	private ThialandService thialandService;
	
	private IOperatorService findProcessRequest(int opId){

		IOperatorService ioperatorService=null;
		switch(opId){
		case MConstants.TPAY_EGYPT_VODAFONE_OPERATOR_ID:
		case MConstants.TPAY_EGYPT_ORANGE_OPERATOR_ID:
		case MConstants.TPAY_EGYPT_ETISALAT_OPERATOR_ID:
		case MConstants.TPAY_EGYPT_WE_OPERATOR_ID:{
			ioperatorService = tpayService;
			break;
		}
		case MConstants.ACTEL_DU_OPERATOR_ID:{
			ioperatorService = actelService;
			break;
		}
		case MConstants.MOBIMIND_OOREDOO_OPERATOR_ID:
		case MConstants.MOBIMIND_KUWAIT_VIVA_OPERATOR_ID:{
			ioperatorService = mobimindService;
			break;
		}
		case MConstants.MESSAGE_CLOUD_GATEWAY_CH_SWISSCOM_OPERATOR_ID:
		case MConstants.MESSAGE_CLOUD_GATEWAY_CH_SALT_OPERATOR_ID:
		case MConstants.MESSAGE_CLOUD_GATEWAY_CH_SUNRISE_OPERATOR_ID:{
			ioperatorService = mcgService;
			break;
		}
		case MConstants.MONDIA_PAY_MTN_OPERATOR_ID:{
			ioperatorService=mondiaPayService;
			break;
		}

		case MConstants.OREDOO_KUWAIT_OPERATOR_ID:{
			ioperatorService=comvivaService; 
			break; 
		}

		case MConstants.ALTRUIST_ETISALAT_UAE_OPERATOR_ID:{
			ioperatorService=altruistService;
			break;
		}
		case MConstants.MICROKIOSK_AIS_OPERATOR_ID:{
			ioperatorService=thialandService;
			break;
		}
		default:	{
			ioperatorService=defaultOperatorService	;
		}
		}
		return ioperatorService;
	}


	@Override
	public boolean checkBlocking(AdNetworkRequestBean adNetworkRequestBean) {
		return findProcessRequest(adNetworkRequestBean.getOpId()).checkBlocking(adNetworkRequestBean);
	}

	@Override
	public boolean processBilling(ModelAndView modelAndView,AdNetworkRequestBean adNetworkRequestBean) {
		return findProcessRequest(adNetworkRequestBean.getOpId()).processBilling(modelAndView,adNetworkRequestBean);
	}

	@Override
	public boolean isSubscribed(AdNetworkRequestBean adNetworkRequestBean) {
		return findProcessRequest(adNetworkRequestBean.getOpId()).isSubscribed(adNetworkRequestBean);
	}

	@Override
	public DeactivationResponse deactivation(ModelAndView modelAndView,
			SubscriberReg subscriberReg) {
		return findProcessRequest(subscriberReg.getOperatorId()).deactivation(modelAndView, subscriberReg);
	}


	//	@Override
	//	public IOtp sendOtp(ModelAndView modelAndView, String msisdn,Integer operatorId,Integer serviceId) {
	//		return findProcessRequest(operatorId).sendOtp( modelAndView,  msisdn, operatorId, serviceId);
	//	}


	@Override
	public SubscriberReg searchSubscriber(Integer operatorId,String msisdn, Integer serviceId,Integer productId) {
		return findProcessRequest(operatorId).searchSubscriber(operatorId, msisdn, serviceId,productId);
	}	

	@Override
	public boolean checkSub(Integer productId,Integer operatorId,String msisdn) {
		return findProcessRequest(operatorId).checkSub(productId,operatorId, msisdn);
	}


	@Override
	public boolean sendOtp(ModelAndView modelAndView,
			AdNetworkRequestBean adNetworkRequestBean) {
		return findProcessRequest(adNetworkRequestBean.getOpId()).sendOtp( modelAndView,
				adNetworkRequestBean);
	}


	@Override
	public boolean validateOtp(ModelAndView modelAndView, 
			AdNetworkRequestBean adNetworkRequestBean) {
		return findProcessRequest(adNetworkRequestBean.getOpId()).validateOtp( modelAndView,
				adNetworkRequestBean);
	}	
	@Override
	public Timestamp getTimeByOperator(Integer opId) {
		return findProcessRequest(opId).getTimeByOperator(
				opId);
	}	

}