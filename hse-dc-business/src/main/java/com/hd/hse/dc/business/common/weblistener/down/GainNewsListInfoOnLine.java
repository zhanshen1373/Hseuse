/**
 * Project Name:hse-dc-business
 * File Name:GainNewsListInfoOnLine.java
 * Package Name:com.hd.hse.dc.business.common.weblistener.down
 * Date:2015年9月22日
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
import com.hd.hse.dc.business.common.result.BaseWebResult;
import com.hd.hse.dc.business.common.result.EntityListWebResult;
import com.hd.hse.entity.common.PushMessage;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.system.SystemProperty;

/**
 * ClassName:GainNewsListInfoOnLine ().<br/>
 * Date: 2015年9月22日 <br/>
 * 
 * @author LiuYang
 * @version
 * @see
 */
public class GainNewsListInfoOnLine extends DataListListener {

	private Logger logger = LogUtils.getLogger(GainNewsListInfoOnLine.class);
	private Map<String, Object> map = new HashMap<String, Object>();

	@Override
	public Object action(String action, Object... args) throws HDException {
		Object obj = null;
		try {
			obj = super.action(action, args);
			this.sendMessage(IMessageWhat.END, 100, 100, "获取成功", obj);
		} catch (Exception e) {
			setWritelog("获取数据报错:", e);
			this.sendMessage(IMessageWhat.ERROR, 9, 9, e.getMessage());
		}
		return obj;
	}

//	@Override
//	public BaseWebResult getResultChangeType() {
//		try {
//			return new EntityListWebResult(getEntityClass(),
//					getAnalyzeDataAdapter());
//		} catch (HDException e) {
//			e.printStackTrace();
//		}
//		return super.getResultChangeType();
//	}

	@Override
	public Class<?> getEntityClass() {
		return PushMessage.class;
	}

	@Override
	public String[] getColumns() {
		return null;
	}

	@Override
	public Object initParam() throws HDException {
		map.put(PadInterfaceRequest.KEYPERSONID, SystemProperty
				.getSystemProperty().getLoginPerson().getPersonid());
		map.put(PadInterfaceRequest.KEY_MSG_STATUS, 1);
		return map;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public String getMethodType() {
		return PadInterfaceContainers.METHOD_ZYPONLINE_NEWSLIST;
	}

}
