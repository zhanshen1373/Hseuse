/**
 * Project Name:hse-osr-app
 * File Name:OsrSaveItemByItemMeasure.java
 * Package Name:com.hd.hse.osr.service.saveinfo
 * Date:2014年11月28日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.osr.service.saveinfo;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.ITableName;
import com.hd.hse.entity.base.MultitermMeasureAffirm;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.saveinfo.SaveItemByItemMeasure;

/**
 * ClassName: OsrSaveItemByItemMeasure ()<br/>
 * date: 2014年11月28日  <br/>
 *
 * @author zhaofeng
 * @version 
 */
public class OsrSaveItemByItemMeasure extends SaveItemByItemMeasure {

	/**
	 * TODO 
	 * @see com.hd.hse.service.workorder.saveinfo.SaveItemByItemMeasure#setWorkOrderParams(com.hd.hse.entity.base.MultitermMeasureAffirm, com.hd.hse.entity.workorder.WorkOrder, com.hd.hse.common.entity.SuperEntity)
	 */
	@Override
	public void setWorkOrderParams(MultitermMeasureAffirm paraMeasureAffirm,
			WorkOrder workOrder, SuperEntity measure) throws HDException {
		// TODO Auto-generated method stub
		if (measure.getAttribute("zycsfcnum") == null)
			throw new HDException("获取措施的编码字段【zycsfcnum】");
		paraMeasureAffirm.setTablename(ITableName.UD_ZYXK_ZYCSFC);
		paraMeasureAffirm.setTableid(measure.getAttribute("zycsfcnum").toString());
	}

}
