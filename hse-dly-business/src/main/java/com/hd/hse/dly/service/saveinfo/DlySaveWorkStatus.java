/**
 * Project Name:hse-dly-app
 * File Name:DlySaveWorkStatus.java
 * Package Name:com.hd.hse.dly.service.saveinfo
 * Date:2014年11月28日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.dly.service.saveinfo;

import java.util.Map;

import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.saveinfo.SaveWorkStatus;

/**
 * ClassName: DlySaveWorkStatus ()<br/>
 * date: 2014年11月28日 <br/>
 * 
 * @author zhaofeng
 * @version
 */
public class DlySaveWorkStatus extends SaveWorkStatus {

	/**
	 * TODO
	 * 
	 * @see com.hd.hse.service.workorder.saveinfo.SaveWorkStatus#setWorkAttributes(com.hd.hse.entity.workorder.WorkOrder)
	 */
	@Override
	public void setWorkAttributes(String action, WorkOrder workOrder,
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		workOrder.setSpstatus(IWorkOrderStatus.SPSTATUS_DELAY);
		if (workOrder.getYyqcount() != null) {
			workOrder.setYyqcount(workOrder.getYyqcount() + 1);
		} else {
			workOrder.setYyqcount(1);
		}
		// 把暂停状态改为非暂停
		workOrder.setIspause(0);
		// 把暂停时间置空
		workOrder.setPausetime("");

	}
	@Override
	public String[] needUpdateValue() {
		return new String[] { "status",
				"spstatus", "isupload", "yyqcount","ispause","pausetime"};
	}

}
