package net.mycomp.controller;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.common.service.CommonService;
import net.common.service.IDaoService;
import net.persist.bean.LiveReport;
import net.process.bean.AggReport;
import net.util.MData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("config")
public class ConfigController {

	@Autowired
	private IDaoService daoService;

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
	
	@RequestMapping("menu")
	public String showMenu(){
		return "config/menu";
	}
	
	@RequestMapping("/aggstats")	
	public ModelAndView aggReport(@ModelAttribute(value="AggReport") AggReport aggReport,BindingResult result) {
	
		ModelAndView modelAndView=new ModelAndView("config/agg_report");
		
		modelAndView.addObject("operatorList",CommonService.listOperator);
		modelAndView.addObject("adnetworksList",MData.mapAdnetworks);		
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
	
}
