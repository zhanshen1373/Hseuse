/***********************************************************************
 * Module:  CheckWorkOrderStatus.java
 * Author:  Administrator
 * Purpose: Defines the Class CheckWorkOrderStatus
 ***********************************************************************/

package com.hd.hse.service.workorder.checkrules;

import java.util.Map;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.entity.workorder.WorkOrder;

/**
 * 状态为审批中(INPRG)状态的票才可以签批 查询pad端数据库库
 * 
 * @pdOid b41179e7-de49-4666-b423-2a3857d7d232
 */
public class CheckWorkOrderStatus extends AbstractCheckListener {
	/**
	 * @param action
	 * @param args
	 * @exception HDException
	 * @pdOid 81a4b5e1-40a1-44c0-ae2e-f0219d0da8a0
	 */
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		String zysqid = "";// 票的id
		Map<String, Object> mapParas = objectCast(args[0]);
		if (mapParas.containsKey(WorkOrder.class.getName())) {
			Object obj = mapParas.get(WorkOrder.class.getName());
			if (obj instanceof SuperEntity) {
				WorkOrder workorder = (WorkOrder) obj;
				zysqid = workorder.getUd_zyxk_zysqid();
			}
			if (zysqid.length() == 0) {
				throw new AppException(UNKNOW_ERROR);
			}
			if (action.equalsIgnoreCase(IActionType.ACTION_TYPE_CANSIGN)) {
				checkStatus(zysqid, IWorkOrderStatus.WAPPR,
						IWorkOrderStatus.CAN, IWorkOrderStatus.NULLIFY,
						IWorkOrderStatus.CLOSE, IWorkOrderStatus.CQCLOSE);
			} else {
				checkStatus(zysqid, IWorkOrderStatus.WAPPR,
						IWorkOrderStatus.CAN, IWorkOrderStatus.NULLIFY,
						IWorkOrderStatus.CLOSE, IWorkOrderStatus.CQCLOSE,IWorkOrderStatus.APPR);
			}

		}
		return super.action(action, args);
	}

	/**
	 * checkStatus:(检查作业票的状态). <br/>
	 * date: 2014年10月18日 <br/>
	 * 
	 * @author ZhangJie
	 * @param zysqid
	 * @throws HDException
	 */
	private void checkStatus(String zysqid, String... zypstatus)
			throws HDException {
		// TODO Auto-generated method stub
		String sql = "select status from ud_zyxk_zysq where ud_zyxk_zysqid ='"
				+ zysqid + "'";
		Map<String, Object> map = getMapResult(sql);
		String status = map.get("status").toString();
		for (String string : zypstatus) {
			if(string.equalsIgnoreCase(status))
			{
				throw new AppException("本状态下作业票，不能进行该操作！");
			}
		}
	}

}