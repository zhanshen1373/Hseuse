/**
 * Project Name:hse-osc-app
 * File Name:OscSaveItemByItemMeasure.java
 * Package Name:com.hd.hse.osc.service.saveinfo
 * Date:2014年11月28日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.osc.service.saveinfo;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.ITableName;
import com.hd.hse.entity.base.MultitermMeasureAffirm;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.saveinfo.SaveItemByItemMeasure;

/**
 * ClassName: OscSaveItemByItemMeasure (作业审核-逐条措施保存)<br/>
 * date: 2014年11月28日  <br/>
 *
 * @author zhaofeng
 * @version 
 */
public class OscSaveItemByItemMeasure extends SaveItemByItemMeasure {

	@Override
	public void setWorkOrderParams(MultitermMeasureAffirm paraMeasureAffirm,
			WorkOrder workOrder, SuperEntity measure) throws HDException {
		// TODO Auto-generated method stub
		paraMeasureAffirm.setTablename(ITableName.UD_ZYXK_ZYSQ);
		paraMeasureAffirm.setTableid(workOrder.getUd_zyxk_zysqid());
	}
}
