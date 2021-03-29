/**
 * Project Name:hse-dly-app
 * File Name:DlySaveWorkRealEndTime.java
 * Package Name:com.hd.hse.dly.service.saveinfo
 * Date:2014年11月28日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.dly.service.saveinfo;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.workorder.WorkDelay;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.saveinfo.SaveWorkRealEndTime;

/**
 * ClassName: DlySaveWorkRealEndTime ()<br/>
 * date: 2014年11月28日  <br/>
 *
 * @author zhaofeng
 * @version 
 */
public class DlySaveWorkRealEndTime extends SaveWorkRealEndTime {

	/**
	 * TODO 
	 * @see com.hd.hse.service.workorder.saveinfo.SaveWorkRealEndTime#setWorkAttributes(com.hd.hse.entity.workorder.WorkOrder, com.hd.hse.common.entity.SuperEntity)
	 */
	@Override
	public void setWorkAttributes(String action,WorkOrder workOrder, SuperEntity superEntity)throws HDException {
		// TODO Auto-generated method stub
		if(superEntity == null)
			throw new HDException("系统错误，请联系管理员！");
		if(!(superEntity instanceof WorkDelay))
			throw new HDException("系统错误，请联系管理员！");
		WorkDelay workDelay = (WorkDelay) superEntity;
		workOrder.setSjendtime(workDelay.getYqendtime());
	}

}
