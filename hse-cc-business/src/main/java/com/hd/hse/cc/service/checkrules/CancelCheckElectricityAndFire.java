/***********************************************************************
 * Module:  CheckElectricityAndFire.java
 * Author:  Administrator
 * Purpose: Defines the Class CheckElectricityAndFire
 ***********************************************************************/

package com.hd.hse.cc.service.checkrules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.constant.IWorkOrderZypClass;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.service.workorder.checkrules.WebServiceAssist;
import com.hd.hse.utils.UtilTools;

/**
 * 5，火票取消时，同关闭操作
 * 
 * @pdOid e07e45e6-b2f3-40c4-8349-1de5ad500009
 */
public class CancelCheckElectricityAndFire extends AbstractCheckListener {
	/**
	 * @param action
	 * @param args
	 * @exception HDException
	 * @pdOid 7ba7e6b3-02db-46ca-aea8-ae40869ed402
	 */
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		// 电火票是否强制关联
		boolean relationAction = isRelationAction(IRelativeEncoding.DHYD);
		if (relationAction) {
			Map<String, Object> mapParas = objectCast(args[0]);
			Object obj = UtilTools.judgeMapValue(mapParas, WorkOrder.class,
					false);
			WorkOrder workorder = (WorkOrder) obj;
			Object objApproval = UtilTools.judgeMapValue(mapParas,
					WorkApprovalPermission.class, false);
			WorkApprovalPermission approval = (WorkApprovalPermission) objApproval;
			// 没有审批环节点或最终审批人时，校验
			if (approval == null || approval.getIsend() == 1) {
				relationCheckWorkCancel(workorder);
			}
		}
		return super.action(action, args);
	}

	/**
	 * relationCheckWork:(取消关联票验证). <br/>
	 * date: 2014年10月20日 <br/>
	 * 
	 * @author ZhangJie
	 * @param workorder
	 * @param checktype
	 * @throws HDException
	 */
	private void relationCheckWorkCancel(WorkOrder workorder)
			throws HDException {
		String zypclass = workorder.getZypclass();// 作业票类型
		String zysqid = workorder.getUd_zyxk_zysqid();
		// 检测票类型,如果是火票，那么就判断电票的状态
		if (zypclass.equalsIgnoreCase(IWorkOrderZypClass.ZYPCLASS_DHZY)) {
			// 检验pad数据库的电票状态 dhzy_id
			String sql = "select ud_zyxk_zysqid,status from ud_zyxk_zysq where dhzy_id ='"
					+ zysqid + "'";
			List<Map<String, Object>> list = getListMapResult(sql);
			StringBuilder sbZypid = new StringBuilder();
			for (Map<String, Object> map : list) {
				String ud_zyxk_zysqid = map.get("ud_zyxk_zysqid").toString();
				String status = map.get("status").toString();
				/*
				 * if (status.equalsIgnoreCase(IWorkOrderStatus.INPRG)||status.
				 * equalsIgnoreCase(IWorkOrderStatus.APPR)) { throw new
				 * HDException("本机存在审批中或作业中的电票"); }
				 */
				if (!status.equalsIgnoreCase(IWorkOrderStatus.INPRG)
						&& !status.equalsIgnoreCase(IWorkOrderStatus.APPR)) {
					// throw new HDException("本机存在审批中或作业中的小票");
					sbZypid.append(",'").append(ud_zyxk_zysqid).append("'");
				}
			}
			String excludezypid = "";
			if (sbZypid.length() > 0) {
				excludezypid = sbZypid.substring(1);
			}
			// 到服务器校验关联的电票状态
			WebServiceAssist wsa = new WebServiceAssist();
			String action = PadInterfaceContainers.METHOD_ZYP_GETLSYDSTATUS;// 服务器端获得票的状态
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put(PadInterfaceRequest.KEYZYPSTRID,
					workorder.getUd_zyxk_zysqid());// 火票的id
			if (excludezypid.length() > 0) {
				hm.put(PadInterfaceRequest.KEYEXCLUDEZYPSTRID, excludezypid);// 不需要在pc端验证的作业票id
			}
			Object obj = wsa.action(action, hm);
			if (obj instanceof String) {
				if (!obj.toString().equalsIgnoreCase(
						PadInterfaceContainers.METHOD_SUCCESS)) {
					throw new HDException(obj.toString() + "未取消");
				}
			}
		}
	}

}