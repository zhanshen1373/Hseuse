/**
 * Project Name:hse-osc-app
 * File Name:WorkTaskSrv.java
 * Package Name:com.hd.hse.osc.business.workorder
 * Date:2014年9月28日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.dly.business.workorder;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.common.PositionCard;
import com.hd.hse.service.workorder.WorkTaskDBSrv;
import com.hd.hse.system.SystemProperty;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * ClassName:WorkTaskSrv (作业延期作业票列表数据查询服务).<br/>
 * Date: 2014年9月28日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class WorkTaskSrv extends WorkTaskDBSrv {

	@Override
	protected String getWorkTaskExtWhere() throws HDException {
		return super.getWorkTaskExtWhere();
	}

	@Override
	protected String getWorkOrderExtWhere() throws HDException {
		// 查询非连续延期缓冲时间是否开启
		BaseDao dao = new BaseDao();
		String sql = "select * from sys_relation_info where sys_type = '"
				+ IRelativeEncoding.DELAYCONFIGUR + "'";
		RelationTableName delayconfigur = (RelationTableName) dao.executeQuery(
				sql, new EntityResult(RelationTableName.class));
		// 非连续延期缓冲时间
		int delayTime = 0;
		boolean isQy = false;
		if (delayconfigur != null && delayconfigur.getIsqy() == 1
				&& !StringUtils.isEmpty(delayconfigur.getInput_value())
				&& NumberUtils.isNumber(delayconfigur.getInput_value())) {
			isQy = true;
			delayTime = Integer.valueOf(delayconfigur.getInput_value());
		}

		String dateTime = SystemProperty.getSystemProperty().getSysDateTime();
		PositionCard positionCard = SystemProperty.getSystemProperty()
				.getPositionCard();
		StringBuilder sbWhere = new StringBuilder();
		sbWhere.append("zysq.status = '").append(IWorkOrderStatus.APPR)
				.append("'");// 作业中
		sbWhere.append(" and (ifnull(zysq.[yqcount],0)-ifnull(zysq.[yyqcount],0)) >= 1");// 可延期次数
		if (positionCard != null)
			sbWhere.append(" and zysq.zylocation = '")
					.append(positionCard.getLocation()).append("'");// 位置卡
		sbWhere.append(" and ");
		// 增加非连续延期的判断
		if (isQy) {
			sbWhere.append("(");
		}
		sbWhere.append("(zysq.sjendtime > '").append(dateTime).append("' )");// 作业实际结束时间
		// 增加非连续延期的判断
		if (isQy) {
			sbWhere.append(" or ( (ifnull(zysq.ispause , 0)= 1)"
					+ "and ( ( julianday( 'now' , 'localtime' ) - julianday(zysq.pausetime))*24*60<"
					+ delayTime + ") ))");
		}
		sbWhere.append(" and ifnull(zysq.SPSTATUS,'') !='SPAPPR'");
		sbWhere.append(" order by zysq.zystarttime desc");// 排序
		return sbWhere.toString();
	}

	/**
	 * TODO
	 * 
	 * @see com.hd.hse.service.workorder.WorkTaskDBSrv#getWorkOrderCols()
	 */
	@Override
	protected String[] getWorkOrderCols() throws HDException {
		String[] generalCols = super.getWorkOrderCols();
		// 延期次数、已延期次数
		String[] specialCols = new String[] { "ifnull(zysq.yqcount,0)",
				"ifnull(zysq.yyqcount,0)",
				"'可延期'||(ifnull(zysq.yqcount,0)-ifnull(zysq.yyqcount,0))||'次' as statusname " };
		return (String[]) ArrayUtils.addAll(generalCols, specialCols);
	}

}
