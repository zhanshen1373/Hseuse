/***********************************************************************
 * Module:  WorkToTask.java
 * Author:  Administrator
 * Purpose: Defines the Class WorkToTask
 ***********************************************************************/

package com.hd.hse.osc.service.checkrules;

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
 * 1，小票签批时，大票必须是签批状态（服务器端验证）
 * 
 * @pdOid 5e3bb628-2fd7-470b-9de2-f6a42ebdd318
 */
public class CheckWorkToTask extends AbstractCheckListener {
	/**
	 * @param action
	 * @param args
	 * @exception HDException
	 * @pdOid 58d0d3f4-7499-40df-8e42-8776da93bbca
	 */
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		boolean relationAction = isRelationAction(IRelativeEncoding.BIGANDSPECIAL);
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
				relationCheckWorkSign(workorder);
			}
		}
		return super.action(action, args);
	}

	/**
	 * relationCheckWork:(关联票验证). <br/>
	 * date: 2014年10月20日 <br/>
	 * 
	 * @author ZhangJie
	 * @param workorder
	 * @param checktype
	 * @throws HDException
	 */
	private void relationCheckWorkSign(WorkOrder workorder) throws HDException {
		String zypclass = workorder.getZypclass();// 作业票类型
		String parent_id = workorder.getParent_id();
		// 检测票类型,如果是小票，那么就判断大票的状态
		if (!zypclass.equalsIgnoreCase(IWorkOrderZypClass.ZYPCLASS_ZYDP)) {
			String sql = "select dhzy_id,status,sjendtime from ud_zyxk_zysq where ud_zyxk_zysqid ='"
					+ parent_id + "' order by sjendtime desc";
			List<Map<String, Object>> list = getListMapResult(sql);
			for (Map<String, Object> map : list) {
				String status = map.get("status").toString();
				if (!status.equalsIgnoreCase(IWorkOrderStatus.APPR)) {
					throw new HDException("该票关联的大票还没有签批");
				} else {
					return;
				}
			}
			// 到服务器校验关联的大票是否已经签批
			WebServiceAssist wsa = new WebServiceAssist();
			String action = PadInterfaceContainers.METHOD_ZYP_GETSTATUS;// 服务器端获得票的状态
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put(PadInterfaceRequest.KEYZYPSTRID, workorder.getParent_id());
			Object obj = wsa.action(action, hm);
			if (obj instanceof String) {
				if (!obj.toString().equalsIgnoreCase(IWorkOrderStatus.APPR)) {
					throw new HDException("该票关联的大票还没有签批");
				}
			}
		}
	}

}