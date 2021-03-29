/**
 * Project Name:hse-dly-app
 * File Name:QueryDlyInfo.java
 * Package Name:com.hd.hse.dly.queryinfo
 * Date:2014年12月5日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.dly.queryinfo;

import java.util.List;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.constant.ITableName;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.entity.base.MeasureReviewSub;
import com.hd.hse.entity.base.MultitermMeasureAffirm;
import com.hd.hse.entity.workorder.WorkMeasureReview;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.queryinfo.IQueryCallEventListener;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;

/**
 * ClassName: QueryDlyInfo ()<br/>
 * date: 2014年12月5日  <br/>
 *
 * @author zhaofeng
 * @version 
 */
public class QueryDlyInfo {

	IQueryWorkInfo queryInfo;

	public QueryDlyInfo() {
		queryInfo = new QueryWorkInfo();
	}

	public QueryDlyInfo(IConnection connection) {
		queryInfo = new QueryWorkInfo(connection);
	}

	
	/**
	 * queryItemByItemInfo:(延期-逐条措施信息查询). <br/>
	 * date: 2014年12月5日 <br/>
	 *
	 * @author zhaofeng
	 * @param workOrder
	 * @param measure
	 * @param measureSub
	 * @param callEventListener
	 * @return
	 * @throws HDException
	 */
	public List<MultitermMeasureAffirm> queryItemByItemInfo(
			WorkOrder workOrder, WorkMeasureReview measure,
			MeasureReviewSub measureSub,
			IQueryCallEventListener callEventListener) throws HDException {
		MultitermMeasureAffirm item = new MultitermMeasureAffirm();
		item.setUd_zyxk_zysqid(workOrder.getUd_zyxk_zysqid());
		item.setUd_type(IConfigEncoding.YQ);
		item.setTablename(ITableName.UD_ZYXK_ZYYQ);
		item.setTableid(measure.getZycsfcnum());
		item.setOpid(measureSub.getPrecautionid());
		return queryInfo.queryItemByItemInfo(item, callEventListener);
	}
	
}
