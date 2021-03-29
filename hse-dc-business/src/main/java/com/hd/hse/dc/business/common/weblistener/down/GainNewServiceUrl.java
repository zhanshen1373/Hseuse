/**
 * Project Name:hse-dc-business
 * File Name:GainNewServiceUrl.java
 * Package Name:com.hd.hse.dc.business.common.weblistener.down
 * Date:2015年9月21日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.dc.business.common.weblistener.down;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.padinterface.PadInterfaceResponse;

/**
 * ClassName:GainNewServiceUrl ().<br/>
 * Date: 2015年9月21日 <br/>
 * 
 * @author zhaofeng
 * @version
 * @see
 */
public class GainNewServiceUrl extends GainPCDataListener {
	private static Logger logger = LogUtils.getLogger(GainNewServiceUrl.class);
	private Map<String, Object> map;
	private String retStr;

	@Override
	public Object action(String action, Object... args) throws HDException {
		retStr = "";
		super.action(action, args);
		this.sendMessage(IMessageWhat.END, 100, 100, "", retStr);
		return retStr;
	}

	@Override
	public Object initParam() throws HDException {
		map = new HashMap<String, Object>();
		// map.put(PadInterfaceRequest.KEYPERSONID, SystemProperty
		// .getSystemProperty().getLoginPerson().getPersonid());
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterDataInfo(Object pcdata, Object... obj) throws HDException {
		super.afterDataInfo(pcdata, obj);
		if (pcdata instanceof PadInterfaceResponse) {
			PadInterfaceResponse response = (PadInterfaceResponse) pcdata;
			setWritelog("pc报错：" + response.getExceptionmsg());
			throw new HDException("pc端获取消息服务URL失败,请联系管理员");
		} else if (pcdata instanceof Map) {
			map = (Map<String, Object>) pcdata;
			if (map.size() == 0) {
				retStr = null;
			} else {
				if (map.containsKey(PadInterfaceRequest.KEYNEWSSERVERURL))
					retStr = map.get(PadInterfaceRequest.KEYNEWSSERVERURL)
							.toString();
			}
		} else {
			retStr = null;
		}
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public String getMethodType() {
		return PadInterfaceContainers.METHOD_COMMON_GETNEWSSERVERURL;
	}

}
