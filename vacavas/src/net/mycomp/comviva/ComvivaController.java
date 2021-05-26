package net.mycomp.comviva;

import java.sql.Timestamp;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import net.common.service.IDaoService;
import net.common.service.SubscriberRegService;
import net.persist.bean.LiveReport;
import net.persist.bean.SubscriberReg;
import net.persist.bean.VWServiceCampaignDetail;
import net.process.bean.CGToken;
import net.util.MData;

@Controller
@RequestMapping("ord")
public class ComvivaController {

	@Autowired
	private IDaoService daoService;
	@Autowired
	private ComvivaUtill comvivaUtill;
	@Autowired
	private JMSComvivaService jmsComvivaService;
	@Autowired
	private SubscriberRegService subscriberRegService;

	private static final Logger logger = Logger.getLogger(ComvivaController.class);

	@Value("${comviva.cg.parameter}")
	private String cgParameter;
	@Value("${comviva.cg.url}")
	private String cgURLPrefix;
	
	@Value("${comviva.image.url}")
	private String imagePath;

	@RequestMapping("hecallback")
	public ModelAndView heCallback(HttpServletRequest request,ModelAndView modelAndView) {
		//http://192.241.167.189:8080/vacavas/sys/ord/hecallback?
		//MSISDN=96569687736&Reason=DATA&productId=S-GamShpEwMY2&transID=test0400
		ComvivaCGTrans comvivaCGTrans = new ComvivaCGTrans(true);
		ComvivaServiceConfig comvivaServiceConfig = null;
		try {
			logger.debug("query str received from he callback="+request.getQueryString());
			comvivaCGTrans.setQueryStr(request.getQueryString());
			comvivaCGTrans.setMsisdn(request.getParameter("MSISDN"));
			comvivaCGTrans.setReason(request.getParameter("Reason"));
			comvivaCGTrans.setProductId(request.getParameter("productId"));
			comvivaCGTrans.setToken(request.getParameter("transID"));
			if(Objects.nonNull(comvivaCGTrans.getToken())) {
				CGToken cgToken = new CGToken(comvivaCGTrans.getToken());
				VWServiceCampaignDetail vwserviceCampaignDetail = MData
						.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
				comvivaServiceConfig = ComvivaConstant.mapServiceIdToComvivaServiceConfig.get(vwserviceCampaignDetail.getServiceId());
			}
			//MSISDN=<msisdn>&productID=<product_id>&pName=<product_name>&pPrice=<price>&pVal=<validity>&CpId=<cp_id>
			//&CpPwd=<cp_pwd>&CpName=<cp_name>&ismID=<ism_id>&transID=<trans_id>&reqMode=<req_mode>&reqType=<req_type>&cpBgColor=
			//&sRenewalPrice=<renew_price>&sRenewalValidity=<renew_validity>&Wap_mdata=<wap_mdata>
			String cgURLParam =  cgParameter.replaceAll("<msisdn>", comvivaCGTrans.getMsisdn())
					.replaceAll("<product_id>", comvivaServiceConfig.getComvivaProductId())
					.replaceAll("<product_name>", comvivaServiceConfig.getProductName())
					.replaceAll("<price>", Objects.toString(comvivaServiceConfig.getPrice()))
					.replaceAll("<validity>", Objects.toString(comvivaServiceConfig.getValidity()))
					.replaceAll("<cp_id>", Objects.toString(comvivaServiceConfig.getCpId()))
					.replaceAll("<cp_pwd>", Objects.toString(comvivaServiceConfig.getCpPwd()))
					.replaceAll("<cp_name>", Objects.toString(comvivaServiceConfig.getCpName()))
					.replaceAll("<ism_id>", Objects.toString(comvivaServiceConfig.getIsmId()))
					.replaceAll("<trans_id>", Objects.toString(comvivaCGTrans.getToken()))
					.replaceAll("<req_mode>", Objects.toString(comvivaServiceConfig.getRequestMode()))
					.replaceAll("<req_type>", Objects.toString(comvivaServiceConfig.getRequestType()))
					.replaceAll("<renew_price>", Objects.toString(comvivaServiceConfig.getPrice()))
					.replaceAll("<renew_validity>", Objects.toString(comvivaServiceConfig.getValidity()))
					.replaceAll("<wap_mdata>", Objects.toString(imagePath.replaceAll("<image>", comvivaServiceConfig.getLpImage())));
			
			comvivaCGTrans.setCgURL(cgURLPrefix+cgURLParam);
			String encryptedCgUrl = cgURLPrefix+comvivaUtill.encrypt(cgURLParam);
			comvivaCGTrans.setEncryptedCgURL(encryptedCgUrl);
			modelAndView.setView(new RedirectView(encryptedCgUrl));
		} catch (Exception e) {
			logger.error("error while processing he callback",e);
		}finally {
			daoService.saveObject(comvivaCGTrans);
		}
		return modelAndView;		
	}

	@RequestMapping("cgcallback")
	public ModelAndView cgCallback(HttpServletRequest request,ModelAndView modelAndView) {
		//MSISDN=69945504&Result=CLOSE&Reason=Consent_Closed_by_user&productId=S-ArabViEwMY2&transID=108299638&TPCGID=&Songname=null&FLOW=DATAFLOW
		ComvivaCGCallback comvivaCGCallback = new ComvivaCGCallback(true);
		ComvivaServiceConfig comvivaServiceConfig = null;
		LiveReport liveReport=null;
		SubscriberReg subscriberReg = null;
		modelAndView.setView(new RedirectView("http://192.241.167.189:8080/vacavas/sys/sub?adid=1&evid=17&ref=gameshop"));
		try {
			comvivaCGCallback.setQueryStr(request.getQueryString());
			comvivaCGCallback.setMsisdn(request.getParameter("MSISDN"));
			comvivaCGCallback.setResult(request.getParameter("Result"));
			comvivaCGCallback.setReason(request.getParameter("Reason"));
			comvivaCGCallback.setProductId(request.getParameter("productId"));
			comvivaCGCallback.setToken(request.getParameter("transID"));
			comvivaCGCallback.setTpCgId(request.getParameter("TPCGID"));
			comvivaCGCallback.setSongName(request.getParameter("Songname"));
			comvivaCGCallback.setFlow(request.getParameter("FLOW"));
			CGToken cgToken = new CGToken(comvivaCGCallback.getToken());
			VWServiceCampaignDetail vwserviceCampaignDetail = MData
					.mapCamapignIdToVWServiceCampaignDetail.get(cgToken.getCampaignId());
			comvivaServiceConfig = ComvivaConstant.mapServiceIdToComvivaServiceConfig.get(vwserviceCampaignDetail.getServiceId());
			liveReport = new LiveReport(vwserviceCampaignDetail.getOpId(), new Timestamp(System.currentTimeMillis()),
					cgToken.getCampaignId(), vwserviceCampaignDetail.getServiceId(), vwserviceCampaignDetail.getProductId());
			if(Objects.nonNull(comvivaCGCallback.getResult()) && "SUCCESS".equalsIgnoreCase(comvivaCGCallback.getResult())) {
				subscriberReg = subscriberRegService.findOrCreateSubscriberByAct(comvivaCGCallback.getMsisdn(), 
						null, liveReport);
				String portalURL = comvivaServiceConfig.getPortalURL()
						.replaceAll("<subid>", Objects.toString(subscriberReg.getSubscriberId()));
				modelAndView.setView(new RedirectView(portalURL));
			}
		} catch (Exception e) {
			logger.error("error while processing cg callback",e);
		}finally {
			jmsComvivaService.saveComvivaCallback(comvivaCGCallback);
		}
		return modelAndView;
	}
	
	@RequestMapping("notification")
	@ResponseBody
	public String notification(HttpServletRequest request) {
		logger.error("cgNotificaton:::::::: "+request.getQueryString());
		return "SUCCESS";
	}
	
}
