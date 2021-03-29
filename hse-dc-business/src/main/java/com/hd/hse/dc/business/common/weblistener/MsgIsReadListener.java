/**
 * Project Name:hse-dc-business
 * File Name:MsgIsReadListener.java
 * Package Name:com.hd.hse.dc.business.common.weblistener
 * Date:2015年10月22日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.dc.business.common.weblistener;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.padinterface.PadInterfaceResponse;

/**
 * ClassName:MsgIsReadListener ().<br/>
 * Date:     2015年10月22日  <br/>
 * @author   zhaofeng
 * @version  
 * @see 	 
 */
public class MsgIsReadListener extends AbsWebListener {
	
	private String msgId;
	
	public MsgIsReadListener(String msgId) {
		this.msgId = msgId;
	}
	
	@Override
	public Object action(String action, Object... args) throws HDException {
		Object obj;
		try {
			super.action(action, args);
			beforDataInfo(args);
			obj = getDataInfo(args);
		} catch (HDException e) {
			setWritelog(e.getMessage());
			throw e;
		}
		return obj;
	}
	
	private void beforDataInfo(Object obj) throws HDException {
		if (!(initParam() instanceof HashMap)) {
			throw new HDException("initParam()必须设置为HashMap对象;");
		}
	}
	
	private Object getDataInfo(Object obj) throws HDException {
		String methodType = getMethodType();
		if (methodType == null || methodType.isEmpty()) {
			throw new HDException("请注册要调用方法的");
		}
		@SuppressWarnings("unchecked")
		Object retobj = sClient.action(methodType,
				(HashMap<String, Object>) initParam());
		if (retobj instanceof PadInterfaceResponse) {
			PadInterfaceResponse response = (PadInterfaceResponse) retobj;
			setWritelog("PC错误：" + response.getExceptionmsg());
			throw new HDException("加载失败,请联系管理员");
		}
		if(retobj==null){
			retobj="[]";
		}
		return retobj;
	}

	@Override
	public Object initParam() throws HDException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PadInterfaceRequest.KEY_MSG_ID, msgId);
		return params;
	}

	@Override
	public Logger getLogger() {
		return LogUtils.getLogger(MsgIsReadListener.class);
	}

	@Override
	public String getMethodType() {
		return PadInterfaceContainers.METHOD_COMMON_MSG_ISREAD;
	}

}

