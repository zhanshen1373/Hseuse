/**
 * Project Name:hse-entity-service
 * File Name:WebServiceAssist.java
 * Package Name:com.hd.hse.service.workorder.checkrules
 * Date:2014年10月20日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.service.workorder.checkrules;


import org.apache.log4j.Logger;

import com.hd.hse.business.listener.MultiMethodForCheck;
import com.hd.hse.business.webservice.WebConfig;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.system.SystemProperty;

/**
 * ClassName:WebServiceAssist (访问webservice的入口).<br/>
 * Date:     2014年10月20日  <br/>
 * @author   ZhangJie
 * @version  
 * @see 	 
 */
public class WebServiceAssist extends MultiMethodForCheck {
	public WebServiceAssist() {
	}
	private static Logger logger = LogUtils.getLogger(WebServiceAssist.class);
	@Override
	public Object action(String action, Object... args) throws HDException {
		
		return super.action(action, args);
	}
	
	
	@Override
	public Object initParam() {
		return null;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public WebConfig getWebConfigParam() {
		return SystemProperty.getSystemProperty().getWebDataConfig();
	}

}

