
package net.mycomp.controller;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.common.service.BlockSeriesRedisCacheService;
import net.common.service.CommonService;
import net.common.service.IDaoService;
import net.common.service.RedisCacheService;
import net.persist.bean.AdnetworkOperatorConfig;
import net.persist.bean.Adnetworks;
import net.persist.bean.LiveReport;
import net.persist.bean.Operator;
import net.persist.bean.Product;
import net.persist.bean.Service;
import net.persist.bean.VWAdnetworkOperatorConfig;
import net.persist.bean.VWCallbackDump;
import net.process.bean.AdnetworkCompaignReportWrapper;
import net.process.bean.AggReport;
import net.process.request.OperatorRequestService;
import net.util.MConstants;
import net.util.MData;
import net.util.MUtility;

@Controller
@RequestMapping(value = "rcerpaotrt")
public class MisController {

	private static  Logger logger = Logger.getLogger(MisController.class);

	@Autowired
	private IDaoService daoService;


	@Autowired
	private CommonService commonService;
	
	@Autowired
	private OperatorRequestService operatorRequestService;
	@Autowired
	private BlockSeriesRedisCacheService blockSeriesRedisCacheService;
	
	@Autowired
	private RedisCacheService redisCacheService;
	
	@InitBinder
	public void binder(WebDataBinder binder) {binder.registerCustomEditor(Timestamp.class,
	    new PropertyEditorSupport() {
	        public void setAsText(String value) {
	            try {
	                Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
	                setValue(new Timestamp(parsedDate.getTime()));
	            } catch (ParseException e) {
	                setValue(null);
	            }
	        }
	    });
	}
	

	
	@RequestMapping("/currentstats")
	public ModelAndView currentDateReport(@RequestParam(value="opids", required=false) List<Integer> opIdList) {
		
		
		Map<String,List<LiveReport>> mapReport = daoService.findCurrentDateReport(opIdList);
		Map<Integer,List<LiveReport>> mapLiveReport=mapReport.get("currentDateReportList").stream().
				collect(Collectors.groupingBy(LiveReport::getOperatorId,Collectors.toList()));
		
		Timestamp  currentTime = new Timestamp(System.currentTimeMillis());
		Iterator<Integer> itr=mapLiveReport.keySet().iterator();
		while(itr.hasNext()){
			Integer key=itr.next();
		List<LiveReport> listCurrDateReport=mapLiveReport.get(key);
		LiveReport liveReport = new LiveReport();
		liveReport.setNetworkName("Total");
		liveReport.setOperatorId(key);
		for (LiveReport lp : listCurrDateReport) {
			liveReport.setReportDate(lp.getReportDate());
			liveReport.setClickCount(liveReport.getClickCount() + lp.getClickCount());
			liveReport.setDuplicateClickCount(liveReport.getDuplicateClickCount() + lp.getDuplicateClickCount());
			liveReport.setReverseClickCount(liveReport.getReverseClickCount() + lp.getReverseClickCount());
			liveReport.setBlockClickCount(liveReport.getBlockClickCount() + lp.getBlockClickCount());
			liveReport
					.setAlreadySubscribedCount(liveReport.getAlreadySubscribedCount() + lp.getAlreadySubscribedCount());
			liveReport.setValidClickCount(liveReport.getValidClickCount() + lp.getValidClickCount());
			liveReport.setConversionCount(liveReport.getConversionCount() + lp.getConversionCount());
			liveReport.setSendConversionCount(liveReport.getSendConversionCount() + lp.getSendConversionCount());
			liveReport.setSendManualConversionCount(liveReport.getSendManualConversionCount() + lp.getSendManualConversionCount());
			
			liveReport.setGraceConversionCount(liveReport.getGraceConversionCount()+lp.getGraceConversionCount());
			liveReport.setGraceSendConversionCount(liveReport.getGraceSendConversionCount()+lp.getGraceSendConversionCount());
			
			liveReport.setAmount(liveReport.getAmount() + lp.getAmount());
			if (lp.getSpend() != null) {
				liveReport.setSpend(liveReport.getSpend() + lp.getSpend());
			}
			liveReport.setChurnDctCount(liveReport.getChurnDctCount() + lp.getChurnDctCount());
			liveReport.setRenewalCount(liveReport.getRenewalCount() + lp.getRenewalCount());
			liveReport.setRenewalSentCount(liveReport.getRenewalSentCount() + lp.getRenewalSentCount());
			liveReport.setRenewalAmount(liveReport.getRenewalAmount() + lp.getRenewalAmount());
			long hour=MUtility.getHourDiffrence(lp.getLastActivationTime(),currentTime);
			if(hour>=1||hour==-1){
				lp.setLastActivationColoumnColor("alert");
			}			
			hour=MUtility.getHourDiffrence(lp.getLastClickTime(),currentTime);
			if(hour>=1||hour==-1){
				lp.setLastClickColoumnColor("alert");
			}			
		}
		listCurrDateReport.add(liveReport);
	}
		logger.debug("currentDateReport:: map:: " + mapLiveReport);
		ModelAndView modelAndView = new ModelAndView("currdate_report");
		modelAndView.addObject("mapLiveReport", mapLiveReport);
		
		
		Map<Integer,List<LiveReport>> mapMTDChurn=mapReport.get("currentMTDChurn").stream().
				collect(Collectors.groupingBy(LiveReport::getOperatorId,Collectors.toList()));
		modelAndView.addObject("mapMTDChurn", mapMTDChurn);
		
		Map<Integer,List<LiveReport>> mapLastDayChurn=mapReport.get("lastDayChurn").stream().
				collect(Collectors.groupingBy(LiveReport::getOperatorId,Collectors.toList()));
		modelAndView.addObject("mapLastDayChurn", mapLastDayChurn);
		
		
		modelAndView.addObject("mapOperator", CommonService.mapOperator);
		
		
		return modelAndView;
	}
	
	
	
	
	@RequestMapping("/hourlyreport")
	//public ModelAndView findHourlyReport(@RequestParam(name="opid",required=false) Integer opId){
    public ModelAndView findHourlyReport(@ModelAttribute(value="AggReport") AggReport aggReport,
    		BindingResult result,ModelAndView modelAndView){	
		//LocalDate.now()
		
//		if(aggReport.getOpid()==null){
//			opId=listOperator.get(0).getOperatorId();
//		}
		
		logger.info("findHourlyReport:::::::: aggReport:: "+aggReport);
		
		modelAndView.setViewName("hourly_report");
		modelAndView.addObject("listAggregator",MData.mapIdToAggregator.values().stream()
				.collect(Collectors.toList()));
		
		if(aggReport.getAggregatorId()!=null){
			modelAndView.addObject("operatorList", MData.mapIdToOperator.values().stream()
			.filter(p -> p.getAggregatorId().intValue()==aggReport.getAggregatorId())
			.collect(Collectors.toList()));
		}
		
		if(aggReport.getOpid()!=null){
			Map<Integer,Product> mapProduct=new HashMap<Integer,Product>();
			
			for(Service service:MData.mapServiceIdToService.values()){
				if(aggReport.getOpid().intValue()==service.getOpId()){
				  mapProduct.put(service.getProductId(),MData.mapIdToProduct.get(service.getProductId()));
				}
			}
			modelAndView.addObject("productList", mapProduct.values().stream()
					.collect(Collectors.toList()));
		}
		
	
		Map<String,List<LiveReport>> reportMap=new HashMap<String,List<LiveReport>>();
		
		if(aggReport.getOpid()!=null){
		  reportMap=daoService.findHourlyReport(aggReport.getOpid(),aggReport.getProductId());
		}
		
		if(reportMap==null||reportMap.isEmpty()){
			return modelAndView;
		}
		
		modelAndView.addObject("currentDateActivationCount",
				reportMap.get("report").stream().filter(liveReport->
		ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime() 
				)==0
				).mapToInt(v->v.getConversionCount()).sum());
		
		modelAndView.addObject("currentDateActivationAmount",
				reportMap.get("report").stream().filter(liveReport->
		ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime() 
				)==0
				).mapToDouble(v->v.getAmount()).sum());
		
		
		modelAndView.addObject("currentDateRenewalCount",
				reportMap.get("report").stream().filter(liveReport->
		ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime() 
				)==0
				).mapToInt(v->v.getRenewalCount()).sum());
		
		modelAndView.addObject("currentDateRenewalAmount",
				reportMap.get("report").stream().filter(liveReport->
		ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime() 
				)==0
				).mapToDouble(v->v.getRenewalAmount()).sum());
		
		modelAndView.addObject("currentDateParkingCount",
				reportMap.get("report").stream().filter(liveReport->
				ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime()
				)==0
				).mapToInt(v->v.getParkingCount()).sum());
		
		
		
		
		modelAndView.addObject("currentDateTotalRevenue",
				reportMap.get("report").stream().filter(liveReport->
				ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime()
				)==0
				).mapToDouble(v->v.getAmount()+v.getRenewalAmount()).sum());
		
		modelAndView.addObject("currentDateDctCount",
				reportMap.get("report").stream().filter(liveReport->
				ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime()
				)==0
				).mapToInt(v->v.getDctCount()).sum());
		
		
		
		modelAndView.addObject("currentDateReport",
				reportMap.get("report").stream().filter(liveReport->
				ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime())==0)
				.collect(Collectors.toList()));
		
		
		
		
		modelAndView.addObject("yesterdayDateActivationCount",
				reportMap.get("report").stream().filter(liveReport->
				ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime()
				)==-1
				).mapToInt(v->v.getConversionCount()).sum());
		
		modelAndView.addObject("yesterdayDateActivationAmount",
				reportMap.get("report").stream().filter(liveReport->
				ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime()
				)==-1
				).mapToDouble(v->v.getAmount()).sum());
			
		modelAndView.addObject("yesterdayDateRenewalCount",
				reportMap.get("report").stream().filter(liveReport->
				ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime()
				)==-1
				).mapToInt(v->v.getRenewalCount()).sum());
		
		modelAndView.addObject("yesterdayDateRenewalAmount",
				reportMap.get("report").stream().filter(liveReport->
				ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime()
				)==-1
				).mapToDouble(v->v.getRenewalAmount()).sum());
		
		modelAndView.addObject("yesterdayDateParkingCount",
				reportMap.get("report").stream().filter(liveReport->
				ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime()
				)==-1
				).mapToInt(v->v.getParkingCount()).sum());
		
		
			
			modelAndView.addObject("currentDateParkToActCount",
					reportMap.get("report").stream().filter(liveReport->
					ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime()
					)==0
					).mapToInt(v->v.getParkingToActivationCount()).sum());
			
			modelAndView.addObject("currentDateParkToActAmount",
					reportMap.get("report").stream().filter(liveReport->
					ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime()
					)==0
					).mapToDouble(v->v.getParkToActivationAmount()).sum());
			
			modelAndView.addObject("yesterdayDateParkToActCount",
					reportMap.get("report").stream().filter(liveReport->
					ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime()
					)==-1
					).mapToInt(v->v.getParkingToActivationCount()).sum());
			
			modelAndView.addObject("yesterdayDateParkToActAmount",
					reportMap.get("report").stream().filter(liveReport->
					ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime()
					)==-1
					).mapToDouble(v->v.getParkToActivationAmount()).sum());
			
		
		
		modelAndView.addObject("yesterdayDateTotalRevenue",
				reportMap.get("report").stream().filter(liveReport->
				ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime()
				)==-1
				).mapToDouble(v->v.getAmount()+v.getRenewalAmount()).sum());
		
		modelAndView.addObject("yesterdayDateDctCount",
				reportMap.get("report").stream().filter(liveReport->
				ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime()
				)==-1
				).mapToInt(v->v.getDctCount()).sum());
	
		modelAndView.addObject("yesterdayDateReport",
				reportMap.get("report").stream().filter(liveReport->
				ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime())==-1
				).collect(Collectors.groupingBy(LiveReport::getAdnetworkCampaignId,
						Collectors.groupingBy(LiveReport::getActionHours, Collectors.toList()))));
		
		modelAndView.addObject("yesterdayRenewalCount",
				reportMap.get("report").stream().filter(liveReport->
		ChronoUnit.DAYS.between(LocalDate.now(),liveReport.getReportDate().toLocalDateTime() 
				)==0
				).mapToInt(v->v.getRenewalCount()).sum());
		
		List<AdnetworkCompaignReportWrapper> listadnetworkCompaignReportWrapper=new ArrayList<AdnetworkCompaignReportWrapper>();
		listadnetworkCompaignReportWrapper.add(commonService.generateAdnetworkCampaignReport(
				reportMap.get("report"),aggReport.getOpid(),0));
		listadnetworkCompaignReportWrapper.add(commonService.generateAdnetworkCampaignReport(reportMap.get("report")
				,aggReport.getOpid(),-1));
		
		modelAndView.addObject("listadnetworkCompaignReportWrapper",listadnetworkCompaignReportWrapper);
	
		return modelAndView;
	}	
	
	
	@RequestMapping("/aggstats")	
	public ModelAndView aggReport(@ModelAttribute(value="AggReport") AggReport aggReport,BindingResult result) {
		
		ModelAndView modelAndView=new ModelAndView("agg_report");
		LiveReport lastupdatedLiveReport = daoService.getlastupdatedliveReport();
		logger.info("lastupdatedLiveReport Time :   "+lastupdatedLiveReport.getLastClickTime()+"   lastupdatedLiveReport : "+lastupdatedLiveReport);
		modelAndView.addObject("lastupdatedLiveReport",lastupdatedLiveReport);
	
		modelAndView.addObject("mapAggregator",MData.mapIdToAggregator);
		modelAndView.addObject("mapOperator",MData.mapIdToOperator);
		modelAndView.addObject("mapProduct",MData.mapIdToProduct);
		modelAndView.addObject("productId",aggReport.getProductId());
		
		modelAndView.addObject("listAggregator",MData.mapIdToAggregator.values().stream()
				.collect(Collectors.toList()));
		
		if(aggReport.getAggregatorId()!=null){
			modelAndView.addObject("operatorList", MData.mapIdToOperator.values().stream()
			.filter(p -> p.getAggregatorId().intValue()==aggReport.getAggregatorId())
			.collect(Collectors.toList()));
		}
		
		if(aggReport.getOpid()!=null){
			Map<Integer,Product> mapProduct=new HashMap<Integer,Product>();
			
			for(Service service:MData.mapServiceIdToService.values()){
				if(aggReport.getOpid().intValue()==service.getOpId()){
				  mapProduct.put(service.getProductId(),MData.mapIdToProduct.get(service.getProductId()));
				}
			}
			modelAndView.addObject("productList", mapProduct.values().stream()
					.collect(Collectors.toList()));
		}
		
		modelAndView.addObject("adnetworksList", MData.mapAdnetworks.values().stream()
				.collect(Collectors.toList()));
		
		List<LiveReport> list = daoService.findAggReport(aggReport);
		modelAndView.addObject("report",list);
		Map<Integer,List<LiveReport>> map=null;
	   if(list!=null){
		 map=list.stream()
	        .collect(Collectors.groupingBy(p->p.getOperatorId(),Collectors.toList()
	        ));
	   }		
		modelAndView.addObject("reportMap",map);
		Map<Integer,Integer> mapActiveBase=daoService.findSubscriberActiveBase(aggReport);
		modelAndView.addObject("mapActiveBase",mapActiveBase);
		return modelAndView;
	}
	
	@RequestMapping("/aggstats/by-product")
	public ModelAndView aggReportByProduct(@ModelAttribute(value="AggReport") AggReport aggReport,BindingResult result) {
		
		ModelAndView modelAndView=new ModelAndView("agg_report_product");
		LiveReport lastupdatedLiveReport = daoService.getlastupdatedliveReport();
		logger.info("lastupdatedLiveReport Time :   "+lastupdatedLiveReport.getLastClickTime()+"   lastupdatedLiveReport : "+lastupdatedLiveReport);
		modelAndView.addObject("lastupdatedLiveReport",lastupdatedLiveReport);
		modelAndView.addObject("mapAggregator",MData.mapIdToAggregator);
		modelAndView.addObject("mapOperator",MData.mapIdToOperator);
		modelAndView.addObject("mapProduct",MData.mapIdToProduct);
		modelAndView.addObject("productId",aggReport.getProductId());
		
		modelAndView.addObject("listAggregator",MData.mapIdToAggregator.values().stream()
				.collect(Collectors.toList()));
		
		if(aggReport.getAggregatorId()!=null){
			modelAndView.addObject("operatorList", MData.mapIdToOperator.values().stream()
					.filter(p -> p.getAggregatorId().intValue()==aggReport.getAggregatorId())
					.collect(Collectors.toList()));
		}
		
		/*
		 * if(aggReport.getOpid()!=null){
		 */Map<Integer,Product> mapProduct=new HashMap<Integer,Product>();
			/*
			 * for(Service service:MData.mapServiceIdToService.values()){
			 * if(aggReport.getOpid().intValue()==service.getOpId()){
			 */	mapProduct.putAll(MData.mapIdToProduct);
				/*}
			}*/
			modelAndView.addObject("productList", mapProduct.values().stream()
					.collect(Collectors.toList()));
			/*
			 * }
			 */
		modelAndView.addObject("adnetworksList", MData.mapAdnetworks.values().stream()
				.collect(Collectors.toList()));
		
		List<LiveReport> list = daoService.findAggReportByProduct(aggReport);
		modelAndView.addObject("report",list);
		Map<Integer,List<LiveReport>> map=null;
		if(list!=null){
			map=list.stream()
					.collect(Collectors.groupingBy(p->p.getOperatorId(),Collectors.toList()
							));
		}		
		modelAndView.addObject("reportMap",map);
		Map<Integer,Integer> mapActiveBase=daoService.findSubscriberActiveBase(aggReport);
		modelAndView.addObject("mapActiveBase",mapActiveBase);
		return modelAndView;
	}
	
	
	@RequestMapping("/block")
	@ResponseBody
	public String isBlock(@RequestParam("key")String key){
		//List<String> keys=new ArrayList<String>();
		  
		 List<String> keys=Arrays.asList(new String[]{
				  MUtility.find7DigitMobileNo(key),
				  MUtility.find11DigitMobileNo(key)					
				  });
		boolean isBlock=false;
		boolean dctBlock=false;
		String detail=null;
		try {
			isBlock = blockSeriesRedisCacheService.isBlockSeries(keys);
			 detail=blockSeriesRedisCacheService.getBlockSeriesSizeAndKeyValue(keys);
			 dctBlock=redisCacheService.getObjectCacheValue(MConstants.DCT_BLOCK_PREFIX+key)!=null?true:false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return "isBlock:: "+keys+" ,"+isBlock+" <BR> Block series detail:: "+detail+"<br> dct block: "+dctBlock;
		
	}
	

	@RequestMapping("adnoconfig")
	public ModelAndView findAdnoConfig(){
		ModelAndView modelAndView=new ModelAndView("adnoconfig");
		List<VWAdnetworkOperatorConfig> list=daoService.findAllAdnConfig();
		modelAndView.addObject("list",list);
		modelAndView.addObject("lastreloadtime",commonService.lastreloadConfigTime);
		return modelAndView;
	}
	
	@RequestMapping("updateadnopconfig")
	public ModelAndView updateadnopconfig(HttpServletRequest request,@RequestParam(value="adnopconfigid")
	 Integer adnopconfigid,@RequestParam(value="skipno",defaultValue="0")Integer skipno){
		AdnetworkOperatorConfig adnetworkOperatorConfig=daoService.
				findAdnetworkOperatorConfigById(adnopconfigid);
		adnetworkOperatorConfig.setSkipNumber(skipno);
		daoService.updateObject(adnetworkOperatorConfig);
		logger.info("updateadnopconfig:: query string "+request.getQueryString());		
		ModelAndView modelAndView=new ModelAndView("redirect:/sys/rcerpaotrt/adnoconfig");
		return modelAndView;
	}
	
	@RequestMapping("/reload")
	public ModelAndView reload(){
		commonService.loadConfiguration();
		ModelAndView modelAndView=new ModelAndView("redirect:/sys/rcerpaotrt/adnoconfig");
		return modelAndView;
	}


    
	@RequestMapping("callbackdump")	
	public ModelAndView callbackDumpReport(@ModelAttribute(value="AggReport") AggReport aggReport,BindingResult result,@RequestParam(value = "pageNo", required = false, defaultValue = "0") String pageNo ) {
	    long totalUsersCount; // number of rows in Database
		int lastPageNo;
		ModelAndView modelAndView=new ModelAndView("callback_dump_report");
		modelAndView.addObject("mapAggregator",MData.mapIdToAggregator);
		modelAndView.addObject("mapOperator",MData.mapIdToOperator);
		modelAndView.addObject("mapProduct",MData.mapIdToProduct);
		modelAndView.addObject("productId",aggReport.getProductId());
		modelAndView.addObject("listAggregator",MData.mapIdToAggregator.values().stream()
				.collect(Collectors.toList()));
		logger.info("aggReport:  "+aggReport);
			aggReport.setPageNo(aggReport.getPageNo()*MConstants.PAGE_SIZE);
		if(aggReport.getAggregatorId()!=null){
			modelAndView.addObject("operatorList", MData.mapIdToOperator.values().stream()
			.filter(p -> p.getAggregatorId().intValue()==aggReport.getAggregatorId())
			.collect(Collectors.toList()));
		}
		
		
		if(aggReport.getOpid()!=null){
			Map<Integer,Product> mapProduct=new HashMap<Integer,Product>();			
			for(Service service:MData.mapServiceIdToService.values()){
				if(aggReport.getOpid().intValue()==service.getOpId()){
				  mapProduct.put(service.getProductId(),MData.mapIdToProduct.get(service.getProductId()));
				}
			}
			modelAndView.addObject("productList", mapProduct.values().stream()
					.collect(Collectors.toList()));
		}   
        totalUsersCount = daoService.findVWCallbackDumpCount(aggReport); //total no of users
        if (totalUsersCount % MConstants.PAGE_SIZE != 0)
            lastPageNo = (int)(totalUsersCount / MConstants.PAGE_SIZE) + 1; // get last page No (zero based)
        else
            lastPageNo = (int)(totalUsersCount / MConstants.PAGE_SIZE);
        logger.info("totalUsersCount:  "+totalUsersCount);
        logger.info("lastPageNo:  "+lastPageNo);
        modelAndView.addObject("lastPageNo", lastPageNo);
		List<VWCallbackDump> list = daoService.findVWCallbackDump(aggReport);
		modelAndView.addObject("reportList",list);
	
		return modelAndView;
	}
	
	@RequestMapping(value="/cappinfo")
	@ResponseBody
	public String cappingDetail(@RequestParam(name="adnetworkid")Integer adnetworkId,
			@RequestParam(name="opid")Integer operatorId,
			@RequestParam(name="productid")Integer productId){
		String info="";
		
		try{
		AdnetworkOperatorConfig adnConfig=MData.mapAdnetworkOpConfig.
				get(operatorId).
				get(adnetworkId);
		info+="<BR> adnConfig: "+adnConfig;
		Adnetworks adnetworks=MData.mapAdnetworks.get(adnConfig.getAdNetworkId());
		Operator operator = MData.mapIdToOperator.get(operatorId);
		Product product = MData.mapIdToProduct.get(productId);
		info+="<BR> Operator Name: "+operator.getOperatorName();
		info+="<BR> Product Name: "+product.getProductName();
		info+="<BR> Adnetwork Name: "+adnetworks.getNetworkName();
		Timestamp ts=operatorRequestService.getTimeByOperator(operatorId);
		info+="<BR> Cuurent IST Time: "+new Timestamp(System.currentTimeMillis());
		info+="<BR> Cuurent Operator Time: "+ts;
		boolean iscapped=redisCacheService.isCappingOver(adnConfig, operator,product
				,ts);
		info+="<BR> is capping over : "+iscapped;
		info+="<BR><BR>Configuration:: <BR> Date: "+MConstants.sdfDDMMyyyy.format(ts.toLocalDateTime());
		info+="<BR> Date Hour: "+MConstants.sdfDDMMyyyyHH.format(ts.toLocalDateTime());
		info+="<BR> Product per day Capping SET in configuration : "+product.getProductWiseCappingPerDay();
		info+="<BR> Product per hour Capping SET in configuration : "+product.getProductWiseHourlyClickCappingRedirectToCg();
		info+="<BR> Operator per day Capping SET  : "+operator.getPerDaiilyConversionCapping();
		info+="<BR> Operator per hour Capping SET  : "+operator.getPerhourConversionCapping();
		
	 	info+="<BR> Adnetwork Operator per day Capping SET in configuration : "+adnConfig.getPerDaiilyConversionCapping();
		info+="<BR> Adnetwork Operator per hour  Capping SET in configuration : "+adnConfig.getPerhourConversionCapping();
		info+="<BR><BR>current value in caheche ";
		info+=redisCacheService.getConversionCapping(adnetworkId, operatorId,productId,ts);
		}catch(Exception ex){
			logger.error("cappingDetail:: ",ex);
			info+=ex.toString();
		}
		return String.valueOf(info);
	}
	@RequestMapping("cache/value")
	@ResponseBody
	public Object getCacheValue(HttpServletRequest request){
		return redisCacheService.getObjectCacheValue(request.getParameter("key"));
	}
	
	@ExceptionHandler
	@ResponseBody
	public String error(Exception ex){
		logger.error("error:: ",ex);
		return ex.getMessage();
	}
}
