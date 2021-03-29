/**
 * Project Name:hse-scw-app
 * File Name:ScwSaveWorkStatus.java
 * Package Name:com.hd.hse.scw.service.saveinfo
 * Date:2014年11月28日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.scw.service.saveinfo;

import java.util.Map;

import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.saveinfo.SaveWorkStatus;

/**
 * ClassName: ScwSaveWorkStatus ()<br/>
 * date: 2014年11月28日  <br/>
 *
 * @author zhaofeng
 * @version 
 */
public class ScwSaveWorkStatus extends SaveWorkStatus {

	/**
	 * TODO 
	 * @see com.hd.hse.service.workorder.saveinfo.SaveWorkStatus#setWorkAttributes(String, com.hd.hse.entity.workorder.WorkOrder)
	 */
	@Override
	public void setWorkAttributes(String action, WorkOrder workOrder,
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		workOrder.setSpstatus(IWorkOrderStatus.SPSTATUS_JJB);
	}

}
