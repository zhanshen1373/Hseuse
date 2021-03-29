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

public class GainRouteUrl extends GainPCDataListener {
	private static Logger logger = LogUtils.getLogger(GainNewServiceUrl.class);
	private Map<String, Object> map;
	private String retStr;
	private String workTaskId; // 作业任务ID

	public GainRouteUrl(String workTaskId) {
		this.workTaskId = workTaskId;
	}

	@Override
	public Object action(String action, Object... args) throws HDException {
		try {
			retStr = "";
			super.action(action, args);
			this.sendMessage(IMessageWhat.END, 100, 100, "", retStr);
		} catch (HDException e) {
			this.sendMessage(IMessageWhat.ERROR, 100, 100, e.getMessage());
		}
		return retStr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterDataInfo(Object pcdata, Object... obj) throws HDException {
		super.afterDataInfo(pcdata, obj);
		if (pcdata instanceof PadInterfaceResponse) {
			PadInterfaceResponse response = (PadInterfaceResponse) pcdata;
			setWritelog("pc报错：" + response.getExceptionmsg());
			throw new HDException("未获取到该文件");
		} else if (pcdata instanceof String) {
			retStr = pcdata.toString();
		} else {
			retStr = null;
		}
	}

	@Override
	public Object initParam() throws HDException {
		map = new HashMap<String, Object>();
		map.put(PadInterfaceRequest.KEYZYPSTRID, this.workTaskId);
		return map;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public String getMethodType() {
		return PadInterfaceContainers.GET_ROUTE;
	}

}
