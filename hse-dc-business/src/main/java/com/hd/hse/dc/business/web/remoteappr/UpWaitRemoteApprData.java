package com.hd.hse.dc.business.web.remoteappr;

import com.google.gson.Gson;
import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.common.weblistener.AbsWebListener;
import com.hd.hse.entity.workorder.RemoteAppr;
import com.hd.hse.entity.workorder.RemoteApprAll;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 上传待远程审批数据
 * 
 * @author yn
 * 
 */
public class UpWaitRemoteApprData extends AbsWebListener {
	private static Logger logger = LogUtils
			.getLogger(UpWaitRemoteApprData.class);
	protected HashMap<String, Object> hasmap = null;
	private ArrayList<RemoteAppr> remoteApprs;

	public UpWaitRemoteApprData(ArrayList<RemoteAppr> remoteApprs) {
		this.remoteApprs = remoteApprs;
	}

	@Override
	public Object action(String action, Object... args) throws HDException {

		Object obj;
		try {
			super.action(action, args);
			beforUpDataInfo(args);
			obj = upDataInfo(args);
			afterUpDataInfo(obj, args);
			this.sendMessage(IMessageWhat.END, 100, 100, "");
		} catch (HDException e) {
			setWritelog(e.getMessage());
			this.sendMessage(IMessageWhat.ERROR, 100, 100, e.getMessage());
			throw e;
		}
		return obj;
	}

	private void beforUpDataInfo(Object[] args) {
		hasmap = new HashMap<String, Object>();

	}

	private Object upDataInfo(Object[] args) throws HDException {
		RemoteApprAll apprAll = new RemoteApprAll();
		apprAll.setUD_ZYXK_REMOTEAPPROVE(remoteApprs);
		Gson gson = new Gson();
		hasmap.put(PadInterfaceRequest.KEYDATA, gson.toJson(apprAll));
		Object obj = null;
		try {
			obj = sClient.action(getMethodType(), hasmap);
		} catch (HDException e) {
			throw e;
		}
		return obj;
	}

	private void afterUpDataInfo(Object obj, Object[] args) {
		// 上传成功删除对应的审批记录

	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public String getMethodType() {
		return PadInterfaceContainers.METHOD_COMMON_UPTABLE;
	}

	@Override
	public Object initParam() throws HDException {
		return null;
	}
}
