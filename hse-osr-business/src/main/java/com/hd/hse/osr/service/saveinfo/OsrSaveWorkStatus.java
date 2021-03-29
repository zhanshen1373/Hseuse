/**
 * Project Name:hse-osr-app
 * File Name:OsrSaveWorkStatus.java
 * Package Name:com.hd.hse.osr.service.saveinfo
 * Date:2014年11月28日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.osr.service.saveinfo;

import java.util.Map;

import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.entity.workorder.WorkMeasureReview;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.saveinfo.SaveWorkStatus;
import com.hd.hse.utils.UtilTools;

/**
 * ClassName: OsrSaveWorkStatus ()<br/>
 * date: 2014年11月28日 <br/>
 * 
 * @author zhaofeng
 * @version
 */
public class OsrSaveWorkStatus extends SaveWorkStatus {

	/**
	 * TODO
	 * 
	 * @see com.hd.hse.service.workorder.saveinfo.SaveWorkStatus#setWorkAttributes(com.hd.hse.entity.workorder.WorkOrder)
	 */
	@Override
	public void setWorkAttributes(String action, WorkOrder workOrder,
			Map<String, Object> map) {
		// TODO Auto-generated method stub

		WorkMeasureReview cloneMeasureReview = null;
		if (map != null)
			cloneMeasureReview = (WorkMeasureReview) map
					.get(WorkMeasureReview.class.getName());
		if (cloneMeasureReview != null
				&& IWorkOrderStatus.REVIEW_STATUS_FINISH
						.equals(cloneMeasureReview.getStatus())){
			workOrder.setSpstatus(IWorkOrderStatus.SPSTATUS_REVIEW);
		}
		if(IActionType.ACTION_TYPE_RECHECKGAS.equals(action)){//复查气体检测
			workOrder.setSpstatus(IWorkOrderStatus.SPSTATUS_REVIEW);
		}
	}

}
