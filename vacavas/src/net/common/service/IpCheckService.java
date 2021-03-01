package net.common.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import net.persist.bean.IPPool;


@Service(value="ipCheckService")
public class IpCheckService implements IIpCheckService{

	private static final Logger logger = Logger.getLogger(IpCheckService.class);
	
	@Override
	public IPPool findOperatorByIp(String ip) {
		IPPool ippool=null;
		try{
		 ippool=  CommonService.listIPPool.stream().filter(p->p!=null&&
				p.getSubnetUtils()!=null&&p.getSubnetUtils().getInfo().
     			isInRange(ip)).findFirst().orElse(null);
		 
		}catch(Exception ex){
			logger.error("findOperatorByIp::ip:: "+ip+" ,ippool "+ippool);
			ippool=null;
		}
		return ippool;
	}
}
