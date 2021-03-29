/***********************************************************************
 * Module:  SaveWorkRealStartTime.java
 * Author:  zhaofeng
 * Purpose: Defines the Class SaveWorkRealStartTime
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
 * 签发界面，最终审核人审核后，更改作业票的实际开始时间，
 * 
 * @pdOid 4c1810d3-2257-4ca1-94b8-02086cc05f96
 */
public class SaveWorkRealStartTime extends AbstractCheckListener {
	/**
	 * @param action
	 * @param object
	 * @pdOid 550d2483-8c7f-4ff0-86c1-706b445445e2
	 */
	public Object action(String action, Object... object) throws HDException {
		// TODO: implement
		Map<String, Object> map = objectCast(object[0]);
		Object workObject = UtilTools.judgeMapValue(map, WorkOrder.class, true);
		Object approvalObject = UtilTools.judgeMapValue(map,
				WorkApprovalPermission.class, false);
		saveWorkRealStartTime((WorkOrder) workObject,
				approvalObject == null ? null
						: (WorkApprovalPermission) approvalObject);
		return null;
	}

	private void saveWorkRealStartTime(WorkOrder workOrder,
			WorkApprovalPermission approvalPermission) throws HDException {
		try {
			if (approvalPermission == null
					|| approvalPermission.getIsend() == 1) {
				workOrder.setSjstarttime(UtilTools.getSysCurrentTime());
				dao.updateEntity(connection, workOrder,
						new String[] { "sjstarttime" });
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("更新作业申请中的实际开始时间字段的值失败！");
		}
	}

}