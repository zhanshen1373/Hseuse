package com.hd.hse.dc.business.web.cbs;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hd.hse.business.listener.InitListener;
import com.hd.hse.business.webservice.WebConfig;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.system.SystemProperty;

/**
 * ClassName: CBSPersonUpdate (承包商人员更新)<br/>
 * date: 2014年8月28日  <br/>
 *
 * @author lxf
 * @version 
 */
public class CBSPersonUpdate extends InitListener {
	private static Logger logger = LogUtils.getLogger(CBSPersonUpdate.class);
	@Override
	public Object action(String action,Object... args) throws HDException {
		 return super.action(action,args);
	}
	
	@Override
	public boolean getInit() {
		return false;
	}

	@Override
	public WebConfig getWebConfigParam() {
		return SystemProperty.getSystemProperty().getWebDataConfig();
	}

	@Override
	public Object initParam() {
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put(PadInterfaceRequest.KEYPERSONID, SystemProperty.getSystemProperty().getLoginPerson().getPersonid());
		//hashmap.put(PadInterfaceRequest.KEYDEPTNUM, SystemProperty.getSystemProperty().getLoginPerson().getDepartment());
		String dept=SystemProperty.getSystemProperty().getLoginPerson().getDepartment();
		if(!StringUtils.isEmpty(dept))
		{
			hashmap.put(PadInterfaceRequest.KEYDEPTNUM,dept);
		}
		return hashmap;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}



	@Override
	public String getWhere() {
		//
		return " SYS_CBSPEOPLE=1";
	}
}