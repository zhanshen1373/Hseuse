/***********************************************************************
 * Module:  SaveWorkRealEndTime.java
 * Author:  zhaofeng
 * Purpose: Defines the Class SaveWorkRealEndTime
 ***********************************************************************/

package com.hd.hse.service.workorder.saveinfo;

import java.util.Map;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkDelay;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * 更改作业申请的实际结束时间，主要在关闭取消，延期等操作下
 * 
 * @pdOid 77a5bb62-f88f-42c1-b6d5-4152bd62a534
 */
public abstract class SaveWorkRealEndTime extends AbstractCheckListener {
	/**
	 * @param action
	 * @param object
	 * @pdOid 9554a5f9-810f-4f6a-bf1c-23edf02c9e4b
	 */
	public Object action(String action, Object... object) throws HDException {
		// TODO: implement
		Map<String, Object> map = objectCast(object[0]);
		WorkOrder workObject = (WorkOrder) UtilTools.judgeMapValue(map,
				WorkOrder.class, true);
		WorkApprovalPermission approval = (WorkApprovalPermission) UtilTools
				.judgeMapValue(map, WorkApprovalPermission.class, false);

		if (approval != null && approval.getIsend() != 1) {
			return null;
		}
		WorkOrder cloneWorkOrder = null;
		// 克隆
		try {
			Map<String, Object> cloneMap = objectCast(object[1]);//
			if (cloneMap.containsKey(WorkOrder.class.getName())) {
				cloneWorkOrder = (WorkOrder) cloneMap.get(WorkOrder.class
						.getName());
			} else {
				cloneWorkOrder = (WorkOrder) workObject.clone();
				cloneMap.put(WorkOrder.class.getName(), cloneWorkOrder);
			}
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("系统错误，请联系管理员！");
		}
		WorkDelay workDelay = (WorkDelay) UtilTools.judgeMapValue(map,
				WorkDelay.class, false);
		setWorkAttributes(action, cloneWorkOrder,workDelay);
		saveWorkRealEndTime(cloneWorkOrder);
		return null;
	}

	private void saveWorkRealEndTime(
			WorkOrder workOrder) throws HDException {
		try {
//			if (IActionType.ACTION_TYPE_CANCEL.equalsIgnoreCase(action)) {
//				// 取消
//				workOrder.setSjendtime(UtilTools.getSysCurrentTime());
//			} else if (IActionType.ACTION_TYPE_CLOSE.equalsIgnoreCase(action)) {
//				// 取消--大港有特殊业务（欲留）
//				workOrder.setSjendtime(UtilTools.getSysCurrentTime());
//			} else if (IActionType.ACTION_TYPE_DELAYSIGN
//					.equalsIgnoreCase(action)) {
//				WorkDelay workDelay = (WorkDelay) UtilTools.judgeMapValue(map,
//						WorkDelay.class, true);
//				workOrder.setSjendtime(workDelay.getYqendtime());
//			}
			
			dao.updateEntity(connection, workOrder,
					new String[] { "sjendtime" });

		} catch (DaoException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("更新作业申请中的实际结束时间字段的值失败！");
		}
	}
	
	/**
	 * setWorkAttributes:(). <br/>
	 * date: 2014年11月28日 <br/>
	 *
	 * @author zhaofeng
	 * @param workOrder
	 * @param superEntity
	 */
	public abstract void setWorkAttributes(String action,WorkOrder workOrder,SuperEntity superEntity)throws HDException;

}