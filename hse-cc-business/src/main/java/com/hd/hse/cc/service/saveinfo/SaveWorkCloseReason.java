/***********************************************************************
 * Module:  SaveWorkCloseReason.java
 * Author:  zhaofeng
 * Purpose: Defines the Class SaveWorkCloseReason
 ***********************************************************************/

package com.hd.hse.cc.service.saveinfo;

import java.util.Map;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * 更改作业申请中的关闭原因字段
 * 
 * @pdOid c5b4183c-b035-4ad9-97a3-37b2113ff105
 */
public class SaveWorkCloseReason extends AbstractCheckListener {
	/**
	 * @param action
	 * @param objects
	 * @pdOid bbd995d5-fec5-4a39-b792-25a99e9edddd
	 */
	public Object action(String action, Object... object) throws HDException {
		// TODO: implement
		Map<String, Object> map = objectCast(object[0]);
		Object workObject = UtilTools.judgeMapValue(map, WorkOrder.class, true);
		Object approvalObject = UtilTools.judgeMapValue(map,
				WorkApprovalPermission.class, false);
		saveWorkCloseReason((WorkOrder) workObject,
				approvalObject == null ? null
						: (WorkApprovalPermission) approvalObject);
		return null;
	}

	private void saveWorkCloseReason(WorkOrder workOrder,
			WorkApprovalPermission approvalPermission) throws HDException {
		try {
			if (approvalPermission == null
					|| approvalPermission.getIndex() == 0) {
				dao.updateEntity(connection, workOrder,
						new String[] { "gbtype","gbsm" });
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("更新作业申请中的关闭原因字段的值失败！");
		}
	}

}