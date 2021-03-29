/**
 * Project Name:hse-osc-app
 * File Name:WorkTaskSrv.java
 * Package Name:com.hd.hse.osc.business.workorder
 * Date:2014年9月28日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.osr.business.workorder;

import java.util.HashMap;
import java.util.List;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.constant.IWorkOrderZypClass;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.dao.result.MapResult;
import com.hd.hse.entity.common.PositionCard;
import com.hd.hse.service.workorder.WorkTaskDBSrv;
import com.hd.hse.system.SystemProperty;

/**
 * ClassName:WorkTaskSrv (现场复查作业票列表数据查询服务).<br/>
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
		String xmbm = "";
		BaseDao dao = new BaseDao();
		String sql = "select xmbm from ud_sy_xmbs";
		HashMap<String, String> result = (HashMap<String, String>) dao
				.executeQuery(sql, new MapResult());
		if (result != null && result.containsKey("xmbm")) {
			xmbm=result.get("xmbm");
		} else {
			throw new HDException("没有找到项目标识，请初始化后重试");
		}
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
		//兰州加进车票
		if (!xmbm.equals("LZSH")) {
			sbWhere.append(" and zysq.zypclass not in('")
			.append(IWorkOrderZypClass.ZYPCLASS_JCP).append("')");
		}
		
		sbWhere.append(" order by zysq.zystarttime desc");// 排序
		return sbWhere.toString();
	}
}
