/***********************************************************************
 * Module:  SaveWorkInvalidReason.java
 * Author:  zhaofeng
 * Purpose: Defines the Class SaveWorkInvalidReason
 ***********************************************************************/

package com.hd.hse.osc.service.saveinfo;

import java.util.Map;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * 更改作业票的作废原因。。。。
 * 
 * @pdOid 714e51d6-bb01-4976-8f00-3cdf3ca71209
 */
public class SaveWorkInvalidReason extends AbstractCheckListener {
	/**
	 * @param action
	 * @param objects
	 * @pdOid d2db014f-7ecd-47a4-a5bf-c554c70a5fd3
	 */
	public Object action(String action, Object... object) throws HDException {
		// TODO: implement
		Map<String, Object> map = objectCast(object[0]);
		Object workObject = UtilTools.judgeMapValue(map, WorkOrder.class, true);
		Object approvalObject = UtilTools.judgeMapValue(map,
				WorkApprovalPermission.class, false);
		saveWorkInvalidReason((WorkOrder) workObject,
				approvalObject == null ? null
						: (WorkApprovalPermission) approvalObject);
		return null;
	}

	private void saveWorkInvalidReason(WorkOrder workOrder,
			WorkApprovalPermission approvalPermission) throws HDException {
		try {
			if (approvalPermission == null
					|| approvalPermission.getIndex() == 0) {
				workOrder.setZfpersontime(UtilTools.getSysCurrentTime());// 作废时间
				dao.updateEntity(connection, workOrder, new String[] {
						"zfcause", "zfpersontime" });
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("更新作业申请中的作废原因失败！");
		}
	}

}