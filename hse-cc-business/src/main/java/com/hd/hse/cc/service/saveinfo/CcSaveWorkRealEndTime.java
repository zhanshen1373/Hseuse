/**
 * Project Name:hse-cc-app
 * File Name:CcSaveWorkRealEndTime.java
 * Package Name:com.hd.hse.cc.service.saveinfo
 * Date:2014年11月28日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.cc.service.saveinfo;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.saveinfo.SaveWorkRealEndTime;
import com.hd.hse.utils.UtilTools;

/**
 * ClassName: CcSaveWorkRealEndTime ()<br/>
 * date: 2014年11月28日 <br/>
 * 
 * @author zhaofeng
 * @version
 */
public class CcSaveWorkRealEndTime extends SaveWorkRealEndTime {

	/**
	 * TODO
	 * 
	 * @see com.hd.hse.service.workorder.saveinfo.SaveWorkRealEndTime#setWorkAttributes(com.hd.hse.entity.workorder.WorkOrder,
	 *      com.hd.hse.common.entity.SuperEntity)
	 */
	@Override
	public void setWorkAttributes(String action, WorkOrder workOrder,
			SuperEntity superEntity) throws HDException {
		// TODO Auto-generated method stub
		if (IActionType.ACTION_TYPE_CANCEL.equalsIgnoreCase(action)) {
			// 取消
			workOrder.setSjendtime(UtilTools.getSysCurrentTime());
		} else if (IActionType.ACTION_TYPE_CLOSE.equalsIgnoreCase(action)) {
			// 关闭--大港有特殊业务（欲留）
			RelationTableName relation = getRelationTableName(IRelativeEncoding.HCSJPZ);
			int hcsj = 0;
			if (relation != null
					&& !StringUtils.isEmpty(relation.getInput_value())
					&& NumberUtils.isNumber(relation.getInput_value())) {
				hcsj = Integer.valueOf(relation.getInput_value());
			}
			String now = UtilTools.getSysCurrentTime();
			String endtime = "";
			if (!UtilTools.dataTimeCompare(now, workOrder.getSjendtime())) {
				// 当前时间<作业票实际结束时间
				endtime = now;
			} else if (UtilTools.dataTimeCompare(now, workOrder.getSjendtime())
					&& !UtilTools
							.dataTimeCompare(now, UtilTools.toAddMinutes(workOrder.getSjendtime(), hcsj))) {
				// 实际结束时间<当前时间<=作业票实际结束时间+缓冲时间
				endtime = workOrder.getSjendtime();
			} else {
				// 当前时间>作业票实际结束时间+缓冲时间
				endtime = now;
				workOrder.setSpstatus(IWorkOrderStatus.SPSTATUS_CQCLOSE);
			}
			workOrder.setSjendtime(endtime);
		}
	}

}
