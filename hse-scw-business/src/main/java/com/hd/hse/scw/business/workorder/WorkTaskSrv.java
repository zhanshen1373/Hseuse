/**
 * Project Name:hse-osc-app
 * File Name:WorkTaskSrv.java
 * Package Name:com.hd.hse.osc.business.workorder
 * Date:2014年9月28日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.scw.business.workorder;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.constant.IWorkOrderZypClass;
import com.hd.hse.entity.common.PositionCard;
import com.hd.hse.service.workorder.WorkTaskDBSrv;
import com.hd.hse.system.SystemProperty;

/**
 * ClassName:WorkTaskSrv (交接班作业票列表数据查询服务).<br/>
 * Date: 2014年9月28日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class WorkTaskSrv extends WorkTaskDBSrv {

	@Override
	protected String getWorkTaskExtWhere() throws HDException {
		// TODO Auto-generated method stub
		return super.getWorkTaskExtWhere();
	}

	@Override
	protected String getWorkOrderExtWhere() throws HDException {
		// TODO Auto-generated method stub
		String dateTime = SystemProperty.getSystemProperty().getSysDateTime();
		PositionCard positionCard = SystemProperty.getSystemProperty()
				.getPositionCard();
		StringBuilder sbWhere = new StringBuilder();
		sbWhere.append("zysq.status = '").append(IWorkOrderStatus.APPR)
				.append("'");// 作业中
		if (positionCard != null)
			sbWhere.append(" and zysq.zylocation = '")
					.append(positionCard.getLocation()).append("'");// 位置卡
		sbWhere.append(" and zysq.sjendtime > '").append(dateTime).append("'");// 作业实际结束时间
		sbWhere.append(" and ifnull(zysq.SPSTATUS,'') !='SPAPPR'");
		// 过滤进车票 add by longgang 2015-6-12
		sbWhere.append(" and zysq.zypclass not in('")
				.append(IWorkOrderZypClass.ZYPCLASS_JCP).append("')");
		sbWhere.append(" order by zysq.zystarttime desc");// 排序
		return sbWhere.toString();
	}

}
