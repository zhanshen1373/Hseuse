package com.hd.hse.dc.business.listener.basicdata;

import org.apache.log4j.Logger;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.common.weblistener.down.InitListener;

public class BasicDataInit extends InitListener {
	private static Logger logger = LogUtils.getLogger(BasicDataInit.class);

	@Override
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		try {
			 super.action(action, args);
		} catch (HDException e) {
			getLogger().error(e.getMessage(), e);
			this.sendMessage(IMessageWhat.ERROR, 9, 9, e.getMessage());
		}
		return 1;
	}

	@Override
	public boolean getInit() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getWhere() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getFuction() {
		// TODO Auto-generated method stub
		return INITDATA;
	}

	@Override
	public Logger getLogger() {
		// TODO Auto-generated method stub
		return logger;
	}

}
