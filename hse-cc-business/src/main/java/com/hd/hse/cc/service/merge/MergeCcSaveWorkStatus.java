package com.hd.hse.cc.service.merge;

import java.util.Map;

import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.saveinfo.merge.MergeSaveWorkStatus;

/**
 * 合并审批 更改作业票状态
 * ClassName: MergeCcSaveWorkStatus ()<br/>
 * date: 2015年9月14日  <br/>
 *
 * @author lxf
 * @version 
 */
public class MergeCcSaveWorkStatus extends MergeSaveWorkStatus {
	/**
	 * TODO
	 * 
	 * @see com.hd.hse.service.workorder.saveinfo.SaveWorkStatus#setWorkAttributes(com.hd.hse.entity.workorder.WorkOrder)
	 */
	@Override
	public void setWorkAttributes(String action, WorkOrder workOrder,
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		if (IActionType.ACTION_TYPE_MERGECLOSE.equals(action)) {
			// 关闭
			if (IWorkOrderStatus.SPSTATUS_CQCLOSE.equalsIgnoreCase(workOrder
					.getSpstatus())) {
				// 关闭时，已经超期了
				workOrder.setStatus(IWorkOrderStatus.CQCLOSE);
			} else {
				workOrder.setStatus(IWorkOrderStatus.CLOSE);
				workOrder.setSpstatus(IWorkOrderStatus.CLOSE);
			}
		} 
	}
}
