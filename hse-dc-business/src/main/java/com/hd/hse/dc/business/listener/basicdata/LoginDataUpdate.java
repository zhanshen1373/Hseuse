package com.hd.hse.dc.business.listener.basicdata;

import org.apache.log4j.Logger;

import com.hd.hse.common.logger.LogUtils;

public class LoginDataUpdate extends BasicDataInit {
	private static Logger logger = LogUtils.getLogger(LoginDataUpdate.class);

	@Override
	public boolean getInit() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String getWhere() {
		// TODO Auto-generated method stub
		return " sys_logindateupdate=1";
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
