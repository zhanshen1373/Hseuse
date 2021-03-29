package com.hd.hse.dc.business.common.weblistener;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.padinterface.PadInterfaceResponse;


public abstract class EachListener extends AbsWebListener {

	@Override
	public Object action(String action, Object... args) throws HDException {
		Object obj;
		try {
			super.action(action, args);
			beforDataInfo(args);
			obj = getDataInfo(args);
			afterDataInfo(obj, args);
		} catch (HDException e) {
			setWritelog(e.getMessage());
			throw e;
		}
		return obj;
	}

	/**
	 * beforDown:(下载前动作). <br/>
	 * date: 2015年3月19日 <br/>
	 * 
	 * @author lxf
	 * @param obj
	 * @throws HDException
	 */
	public void beforDataInfo(Object... obj) throws HDException {
		if (!(initParam() instanceof HashMap)) {
			throw new HDException("initParam()必须设置为HashMap对象;");
		}
	}

	/**
	 * down:(下载动作). <br/>
	 * date: 2015年3月19日 <br/>
	 * 
	 * @author Administrator
	 * @param obj
	 * @throws HDException
	 */
	@SuppressWarnings("unchecked")
	public Object getDataInfo(Object... obj) throws HDException {
		String methodtype = getMethodType();
		if (StringUtils.isEmpty(methodtype)) {
			throw new HDException("请注册要调用方法的");
		}
		Object retobj = sClient.action(methodtype,
				(HashMap<String, Object>) initParam());
		if (retobj instanceof PadInterfaceResponse) {
			PadInterfaceResponse response = (PadInterfaceResponse) retobj;
			if (null != response && response.getFailedList().size() > 0) {
				StringBuilder msg = new StringBuilder();
				for (Map<String, String> listMap : response.getFailedList()) {
					if (listMap.containsKey(PadInterfaceRequest.KEYRETURNMESSAGE)) {
						msg.append(
								listMap.get(PadInterfaceRequest.KEYRETURNMESSAGE)
										.toString()).append(";");
					}
				}
				setWritelog(msg.toString());
				throw new HDException("{msg}" + response.getSucessfulList().size()
						+ "条;失败:" + response.getFailedList().size() + "条;");
			}
		}
		if(retobj==null){
			retobj="[]";
		}
		setWriteDubuglog(retobj.toString());
		return retobj;
	}

	/**
	 * afterdown:(下载后的动作). <br/>
	 * date: 2015年3月19日 <br/>
	 * 
	 * @author lxf
	 * @param obj
	 */
	public void afterDataInfo(Object pcdata, Object... obj) throws HDException {

	}


}
