package com.hd.hse.dc.business.common.weblistener.down;

import com.google.gson.Gson;
import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.entity.workorder.RemoteApprInfo;
import com.hd.hse.entity.workorder.RemoteApprInfoAll;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.padinterface.PadInterfaceResponse;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GainRemoteApprInfo extends GainPCDataListener {
	private static Logger logger = LogUtils.getLogger(GainRemoteApprInfo.class);
	private Map<String, Object> map;
	private String personid;
	private ArrayList<RemoteApprInfo> remoteApprInfos;

	public GainRemoteApprInfo(String personid) {
		this.personid = personid;
	}

	@Override
	public Object action(String action, Object... args) throws HDException {
		try {
			remoteApprInfos = null;
			super.action(action, args);
			this.sendMessage(IMessageWhat.END, 100, 100, "", remoteApprInfos);
		} catch (HDException e) {
			this.sendMessage(IMessageWhat.ERROR, 100, 100, e.getMessage());
		}
		return remoteApprInfos;
	}

	@Override
	public void afterDataInfo(Object pcdata, Object... obj) throws HDException {
		super.afterDataInfo(pcdata, obj);
		if (pcdata instanceof PadInterfaceResponse) {
			PadInterfaceResponse response = (PadInterfaceResponse) pcdata;
			setWritelog("pc报错：" + response.getExceptionmsg());
			throw new HDException("未获取到远程审批数据");
		}
		Gson gson = new Gson();
		RemoteApprInfoAll all = new RemoteApprInfoAll();
		all = gson.fromJson(pcdata.toString(), RemoteApprInfoAll.class);
		remoteApprInfos = (ArrayList<RemoteApprInfo>) all
				.getUD_ZYXK_REMOTEAPPROVE();

	}

	@Override
	public Object initParam() throws HDException {
		map = new HashMap<String, Object>();
		map.put(PadInterfaceRequest.KEYPERSONID, this.personid);
		return map;
	}

	@Override
	public Logger getLogger() {
		// TODO Auto-generated method stub
		return logger;
	}

	@Override
	public String getMethodType() {
		return PadInterfaceContainers.GET_REMOTE_APPR;
	}

}
