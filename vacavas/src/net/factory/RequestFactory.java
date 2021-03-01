package net.factory;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.common.service.CommonService;
import net.common.service.IpCheckService;
import net.persist.bean.AdnetworkToken;
import net.persist.bean.IPPool;
import net.persist.bean.VWServiceCampaignDetail;
import net.process.bean.AdNetworkRequestBean;
import net.process.bean.CGToken;
import net.util.MConstants;
import net.util.MData;
import net.util.MUtility;
import net.util.OperatorMsisdnHeader;
import net.util.ParamMissingException;

@Service("requestFactory")
public class RequestFactory {

	private static final Logger logger = Logger.getLogger(RequestFactory.class);
	private List<String> listTokenParamName = Arrays.asList("ref", "kp", "p5");

	@Autowired
	private IpCheckService ipCheckService;

	public AdNetworkRequestBean createRequestBean(HttpServletRequest request, Integer opId,
			Map<String, String> requestMap) throws Exception {

		Enumeration<String> en = request.getHeaderNames();
		StringBuilder headersStr = new StringBuilder();
		String referersite = request.getHeader("referer");
		logger.debug("campapaign :::::::::::::referersite:::" + referersite);
		String refUrl = null;
		if (referersite != null) {
			refUrl = MUtility.getDomainName(referersite);
		}
		Map<String, String> headerMap = new HashMap<String, String>();
		while (en.hasMoreElements()) {
			String key = en.nextElement();
			headersStr.append(key + "=" + request.getHeader(key) + " ,");
			headerMap.put(key, request.getHeader(key));
		}

		if (request.getParameter("transid") != null) {
			requestMap = parseQueryString(request.getParameter("transid"));
		}

		if (request.getParameter("q") != null) {
			requestMap = parseQueryString(request.getParameter("q"));
		}

		AdNetworkRequestBean adNetworkRequestBean = new AdNetworkRequestBean();
		adNetworkRequestBean.setHeaderMap(headerMap);

		adNetworkRequestBean.setAdNetworkId(MUtility.toInt(getHttpParamter(request, "adid", requestMap), 0));
		adNetworkRequestBean.setCmpid(MUtility.toInt(getHttpParamter(request, MConstants.CMPID, requestMap), 0));
		adNetworkRequestBean.setSourceIp(request.getRemoteAddr());
		adNetworkRequestBean.setUserAgent("");
		adNetworkRequestBean.setQueryString(request.getQueryString());
		adNetworkRequestBean.setHeadersStr(headersStr.toString());
		adNetworkRequestBean.setToken(computeToken(request, requestMap));
		adNetworkRequestBean.setReqTime(new Timestamp(System.currentTimeMillis()));
		adNetworkRequestBean.setPubId(getHttpParamter(request, "pubid", requestMap));
		adNetworkRequestBean.setReferer(refUrl);
		adNetworkRequestBean
				.setSmartCampaignGroupId(MUtility.toInt(getHttpParamter(request, "smcmpgid", requestMap), 0));

		setCampaignIdAndMsisdn(adNetworkRequestBean, request);
		adNetworkRequestBean.vwserviceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail
				.get(adNetworkRequestBean.getCmpid());
		if (adNetworkRequestBean.vwserviceCampaignDetail == null) {
			adNetworkRequestBean.vwserviceCampaignDetail = MData.mapCamapignIdToVWServiceCampaignDetail.entrySet()
					.stream()
					.filter(map -> map.getValue().getServiceId() == MConstants.WASTE_SERVICE_ID
							&& map.getValue().getAdNetworkId() == adNetworkRequestBean.getAdNetworkId())
					.findFirst().get().getValue();
		}
		adNetworkRequestBean.setOpId(adNetworkRequestBean.vwserviceCampaignDetail.getOpId());
		adNetworkRequestBean.setCmpid(adNetworkRequestBean.vwserviceCampaignDetail.getCampaignId());
		createAdnetworkToken(adNetworkRequestBean, request, requestMap);

		return adNetworkRequestBean;
	}

	private AdnetworkToken createAdnetworkToken(AdNetworkRequestBean adNetworkRequestBean, HttpServletRequest request,
			Map<String, String> requestMap) {

		AdnetworkToken adnetworkToken = new AdnetworkToken();
		int tokenId = CommonService.adnetwrokTokenAtomicInteger.getAndIncrement();
		adnetworkToken.setAdnetworkId(adNetworkRequestBean.getAdNetworkId());
		adnetworkToken.setOpId(adNetworkRequestBean.getOpId());
		adnetworkToken.setTokenId(tokenId);

		adnetworkToken.setCampaignId(adNetworkRequestBean.getCmpid());
		adnetworkToken.setPubId(adNetworkRequestBean.getPubId());
		adnetworkToken.setSource(adNetworkRequestBean.getSourceIp());
		adnetworkToken.setToken(adNetworkRequestBean.getToken());
		adnetworkToken.setQueryStr(adNetworkRequestBean.getQueryString());
		adnetworkToken.setHeaders(adNetworkRequestBean.getHeadersStr());
		long time = System.currentTimeMillis();
		adnetworkToken.setReqTime(new Timestamp(time));
		adnetworkToken.setReqTimeLong(time);
		adnetworkToken.setStatus(0);
		adnetworkToken.setMsisdn(adNetworkRequestBean.getMsisdn());
		adnetworkToken.setReferer(adNetworkRequestBean.getReferer());
		adnetworkToken.setParam1(getHttpParamter(request, "param1", requestMap));
		adnetworkToken.setParam2(getHttpParamter(request, "param2", requestMap));
		CGToken cgToken = new CGToken(adnetworkToken.getReqTimeLong(), adnetworkToken.getTokenId(),
				adNetworkRequestBean.vwserviceCampaignDetail.getCampaignId());
		adnetworkToken.setTokenToCg(cgToken.getCGToken());
		adNetworkRequestBean.adnetworkToken = adnetworkToken;
		return adnetworkToken;
	}

	private String computeToken(HttpServletRequest request, Map<String, String> requestMap)
			throws ParamMissingException {

		for (String key : listTokenParamName) {
			String token = getHttpParamter(request, key, requestMap);
			if (token != null) {
				return token;
			}
		}
		throw new ParamMissingException("adnetwork token missing exception");
	}

	/*
	 * public void setCampaignIdAndMsisdn(AdNetworkRequestBean adNetworkRequestBean,
	 * HttpServletRequest request){ int operatorId=0;
	 * 
	 * for(OperatorMsisdnHeader operatorMsisdnHeader:OperatorMsisdnHeader.values()){
	 * String msisdn=request.getHeader(operatorMsisdnHeader.getMsisdnHeaderName());
	 * if(msisdn==null){
	 * msisdn=request.getParameter(operatorMsisdnHeader.getMsisdnHeaderName()); }
	 * 
	 * if(msisdn!=null){ adNetworkRequestBean.setMsisdn(msisdn);
	 * operatorId=operatorMsisdnHeader.isOpIdentifierHeader()?operatorMsisdnHeader.
	 * getOpId():0; break; } }
	 * 
	 * 
	 * if(adNetworkRequestBean.getCmpid()==0){
	 * 
	 * final int opId=findOperatoId(adNetworkRequestBean.getSourceIp(), operatorId);
	 * VWServiceCampaignDetail vwServiceCampaignDetail=
	 * MData.mapSmartCamapignIdToVWServiceCampaignDetail.
	 * get(adNetworkRequestBean.getAdNetworkId()).
	 * stream().filter(a->(a.getOpId().intValue()==opId&&
	 * a.getSmartCampaignGroupId().intValue()
	 * ==adNetworkRequestBean.getSmartCampaignGroupId())) .findFirst().orElse(null);
	 * 
	 * if(vwServiceCampaignDetail!=null){
	 * adNetworkRequestBean.setCmpid(vwServiceCampaignDetail.getCampaignId()); } } }
	 */

	public void setCampaignIdAndMsisdn(AdNetworkRequestBean adNetworkRequestBean,
			HttpServletRequest request){	
		     int operatorId=0;
				/*
				 * for(OperatorMsisdnHeader operatorMsisdnHeader:OperatorMsisdnHeader.values()){
				 * String msisdn=request.getHeader(operatorMsisdnHeader.getMsisdnHeaderName());
				 * if(msisdn==null){
				 * msisdn=request.getParameter(operatorMsisdnHeader.getMsisdnHeaderName()); }
				 * 
				 * if(msisdn!=null){ adNetworkRequestBean.setMsisdn(msisdn);
				 * operatorId=operatorMsisdnHeader.isOpIdentifierHeader()?operatorMsisdnHeader.
				 * getOpId():0; break; } }
				 */
		    adNetworkRequestBean.setMsisdn(request.getParameter("msisdn"));
			if(adNetworkRequestBean.getCmpid()==0){
				
				final int opId=findOperatoId(adNetworkRequestBean.getSourceIp(), operatorId);
				VWServiceCampaignDetail vwServiceCampaignDetail=
						MData.mapSmartCamapignIdToVWServiceCampaignDetail.
				get(adNetworkRequestBean.getAdNetworkId()).
				stream().filter(a->(a.getOpId().intValue()==opId&&
				a.getSmartCampaignGroupId().intValue()
				==adNetworkRequestBean.getSmartCampaignGroupId()))
				.findFirst().orElse(null);
				
				if(vwServiceCampaignDetail!=null){
				adNetworkRequestBean.setCmpid(vwServiceCampaignDetail.getCampaignId());
				}
			}
	}
	
	private int findOperatoId(String ip, int operatorId) {
		if (operatorId == 0) {
			IPPool ipPool = ipCheckService.findOperatorByIp(ip);
			if (ipPool != null) {
				operatorId = ipPool.getOpId();
			}
		}
		return operatorId;
	}

	private String getHttpParamter(HttpServletRequest request, String param, Map<String, String> requestMap) {
		String value = request.getParameter(param);
		if (value != null) {
			return value;
		}
		if (requestMap != null) {
			return requestMap.get(param);
		}
		return null;
	}

	private Map<String, String> parseQueryString(String queryStr) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			queryStr = "?" + MUtility.getBase64DecodedString(queryStr);
			List<NameValuePair> params = URLEncodedUtils.parse(new URI(queryStr), "UTF-8");

			for (NameValuePair param : params) {
				map.put(param.getName(), param.getValue());
			}
		} catch (Exception ex) {
		}
		return map;
	}

}