/**
 * Project Name:hse-entity-service
 * File Name:CheckGasValue.java
 * Package Name:com.hd.hse.service.workorder.checkrules
 * Date:2014年10月18日
 * Copyright (c) 2014, zhangjie1@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.osc.service.checkrules;

import java.util.Map;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * ClassName:CheckGasValue (验证最近一次气体检测值是否合格,是否最终确认).<br/>
 * Date: 2014年10月18日 <br/>
 * 
 * @author ZhangJie
 * @version
 * @see
 */
public class CheckGasValue extends AbstractCheckListener {

	public CheckGasValue() {
	}

	@Override
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		String zysqid = "";// 票的id
		Map<String, Object> mapParas = objectCast(args[0]);
		WorkApprovalPermission approvalObject = (WorkApprovalPermission) UtilTools
				.judgeMapValue(mapParas, WorkApprovalPermission.class, false);
		if (approvalObject == null || approvalObject.getIsend() == 1) {// 最终审核人检查
			if (mapParas.containsKey(WorkOrder.class.getName())) {
				Object obj = mapParas.get(WorkOrder.class.getName());
				if (obj instanceof SuperEntity) {
					WorkOrder workorder = (WorkOrder) obj;
					zysqid = workorder.getUd_zyxk_zysqid();
					if (zysqid.length() == 0) {
						throw new AppException(UNKNOW_ERROR);
					}
					Integer isqtjc = workorder.getIsqtjc();
					if (isqtjc != null && isqtjc == 1) {
						checkValueAndPerson(zysqid);
					}
				}
			}
		}
		return super.action(action, args);
	}

	/**
	 * checkValueAndPerson:(气体检测值是否合格，是否最终确认). <br/>
	 * date: 2014年10月18日 <br/>
	 * 
	 * @author ZhangJie
	 * @param zysqid
	 * @throws HDException
	 */
	private void checkValueAndPerson(String zysqid) throws HDException {
		// TODO Auto-generated method stub
		String sql = "select ishg,writenbypda,ifnull(isonlyoxygenbelowgrade,0)as isonlyoxygenbelowgrade from ud_zyxk_qtjc where ud_zyxk_zysqid='"
				+ zysqid + "' order by jctime desc";
		Map<String, Object> map = getMapResult(sql);
		if (!map.isEmpty()) {
			String ishg = map.get("ishg") == null ? "" : map.get("ishg")
					.toString();
			String status = map.get("writenbypda") == null ? "" : map.get(
					"writenbypda").toString();
			String isonlyoxygenbelowgrade = map.get("isonlyoxygenbelowgrade") == null ? ""
					: map.get("isonlyoxygenbelowgrade").toString();
			if (ishg.length() == 0
					|| (ishg.equalsIgnoreCase(QTJC_ISHG_VALUE_0) && isonlyoxygenbelowgrade
							.equalsIgnoreCase(QTJC_ISHG_VALUE_0))) {
				throw new AppException("气体检测结果不合格");
			}
			if (!IWorkOrderStatus.GAS_STATUS_FINISH.equalsIgnoreCase(status)) {
				throw new AppException("气体检测没有最终确认人");
			}
		} else {
			throw new AppException("气体检测没有完成");
		}
	}
}
