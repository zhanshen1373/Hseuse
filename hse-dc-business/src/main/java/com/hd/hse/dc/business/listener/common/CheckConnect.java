package com.hd.hse.dc.business.listener.common;

import org.apache.log4j.Logger;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.common.weblistener.down.GainPCDataListener;
import com.hd.hse.padinterface.PadInterfaceContainers;

/**
 * ClassName: CheckConnect (检查网络)<br/>
 * date: 2015年3月19日  <br/>
 *
 * @author lxf
 * @version 
 */
public class CheckConnect extends GainPCDataListener {
	private static Logger logger = LogUtils.getLogger(CheckConnect.class);
	@Override
	public void beforDataInfo(Object... obj) throws HDException {
		//重载 不要验证
	}
	@Override
	public Object initParam() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Logger getLogger() {
		// TODO Auto-generated method stub
		return logger;
	}

	@Override
	public String getMethodType() {
		// TODO Auto-generated method stub
		return PadInterfaceContainers.METHOD_COMMON_TEST;
	}

}
