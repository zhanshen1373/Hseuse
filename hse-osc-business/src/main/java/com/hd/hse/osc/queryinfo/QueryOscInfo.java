/**
 * Project Name:hse-osc-app
 * File Name:QueryOscInfo.java
 * Package Name:com.hd.hse.osc.queryinfo
 * Date:2014年12月5日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.osc.queryinfo;

import java.util.List;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.constant.ITableName;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.entity.base.MultitermMeasureAffirm;
import com.hd.hse.entity.workorder.WorkApplyMeasure;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.queryinfo.IQueryCallEventListener;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;

/**
 * ClassName: QueryOscInfo ()<br/>
 * date: 2014年12月5日 <br/>
 * 
 * @author zhaofeng
 * @version
 */
public class QueryOscInfo {

	IQueryWorkInfo queryInfo;

	public QueryOscInfo() {
		queryInfo = new QueryWorkInfo();
	}

	public QueryOscInfo(IConnection connection) {
		queryInfo = new QueryWorkInfo(connection);
	}

	/**
	 * queryItemByItemInfo:(现场核查中-获取逐条记录中的信息). <br/>
	 * date: 2014年12月5日 <br/>
	 *
	 * @author zhaofeng
	 * @param workOrder
	 * @param measure
	 * @param callEventListener
	 * @return
	 * @throws HDException
	 */
	public List<MultitermMeasureAffirm> queryItemByItemInfo(
			WorkOrder workOrder, WorkApplyMeasure measure,
			IQueryCallEventListener callEventListener) throws HDException {
		MultitermMeasureAffirm item = new MultitermMeasureAffirm();
		item.setUd_zyxk_zysqid(workOrder.getUd_zyxk_zysqid());
		item.setUd_type(IConfigEncoding.SP);
		item.setTablename(ITableName.UD_ZYXK_ZYSQ);
		item.setTableid(workOrder.getUd_zyxk_zysqid());
		item.setOpid(measure.getPrecautionid());
		return queryInfo.queryItemByItemInfo(item, callEventListener);
	}
}
