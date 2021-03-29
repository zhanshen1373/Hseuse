package com.hd.hse.dc.business.listener.basicdata;

import com.hd.hse.common.logger.LogUtils;

import org.apache.log4j.Logger;

public class BasicDataUpdate extends BasicDataInit {
	private static Logger logger = LogUtils.getLogger(BasicDataUpdate.class);
	@Override
	public boolean getInit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getWhere() {
		// TODO Auto-generated method stub
		return " SYS_BASE=1";
	}

	@Override
	public Logger getLogger() {
		// TODO Auto-generated method stub
		return logger;
	}
	@Override
	public String getFuction() {
		// TODO Auto-generated method stub
		return UPDATE;
	}

}
