package net.common.service;

import net.persist.bean.IPPool;

public interface IIpCheckService {

	public IPPool findOperatorByIp(String ip);
}
