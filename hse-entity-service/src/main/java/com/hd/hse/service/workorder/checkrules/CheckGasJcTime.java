/**
 * Project Name:hse-entity-service
 * File Name:CheckGasJcTime.java
 * Package Name:com.hd.hse.service.workorder.checkrules
 * Date:2014年11月11日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.service.workorder.checkrules;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.entity.base.GasDetection;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.utils.UtilTools;

/**
 * ClassName: CheckGasJcTime (气体检测时间校验，当前气体检测时间不能早于最后一次气体检测时间)<br/>
 * date: 2014年11月11日 <br/>
 * 
 * @author zhaofeng
 * @version
 */
public class CheckGasJcTime extends AbstractCheckListener {

	@Override
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		Map<String, Object> mapParas = objectCast(args[0]);
		WorkOrder workOrder = (WorkOrder) UtilTools.judgeMapValue(mapParas,
				WorkOrder.class, true);
		GasDetection gasDetection = (GasDetection) UtilTools.judgeMapValue(
				mapParas, GasDetection.class, true);
		WorkApprovalPermission approvalObject = (WorkApprovalPermission) UtilTools
				.judgeMapValue(mapParas, WorkApprovalPermission.class, false);
		checkGasJcTime(workOrder, gasDetection, approvalObject);
		return super.action(action, args);
	}

	/**
	 * checkGasJcTime:(检测时间校验). <br/>
	 * date: 2014年11月11日 <br/>
	 * 
	 * @author zhaofeng
	 * @param workOrder
	 * @param gasDetection
	 * @param approvalObject
	 * @throws HDException
	 */
	private void checkGasJcTime(WorkOrder workOrder, GasDetection gasDetection,
			WorkApprovalPermission approvalObject) throws HDException {
		try {
			StringBuilder jcBuilder = new StringBuilder();
			jcBuilder
					.append("select max(jctime) as jctime from ud_zyxk_qtjc where ");
			jcBuilder.append(" ud_zyxk_zysqid='")
					.append(workOrder.getUd_zyxk_zysqid()).append("';");
			Map<String, Object> map = getMapResult(jcBuilder.toString());
			if (map != null && map.size() > 0 && map.get("jctime") != null) {
				String jctime = map.get("jctime").toString();
				SimpleDateFormat formart = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date jcDate = formart.parse(jctime);
				if (gasDetection.getJctime() != null) {
					Date objJcDate = formart.parse(gasDetection.getJctime());
					if (jcDate.compareTo(objJcDate) > 0) {
						throw new HDException("当前的气体检测时间，不能小于之前的气体检测时间！");
					}
				}
			} else if (isRelationAction(IRelativeEncoding.QTJCSJZYHQ)) {
				// 如果没有做过气体检测，且会签已经开始，就认为是补录气体检测的值，
				// 补录的气体检测的检测时间不能超过会签开始的时间（即会签界面的第一个审核人的审核时间）
				checkJcTimePasreAuditTime(workOrder, gasDetection);
			}
		} catch (DaoException e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new HDException("数据库查询失败！", e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("气体检测时间格式不正确，不能审核！", e);
		}
	}

	/**
	 * checkJcTimePasreAuditTime:(气体检测时间和会签的审批时间，进行比较). <br/>
	 * date: 2015年6月17日 <br/>
	 *
	 * @author zhaofeng
	 * @param workOrder
	 * @param gasDetection
	 * @throws HDException
	 */
	private void checkJcTimePasreAuditTime(WorkOrder workOrder,
			GasDetection gasDetection) throws HDException {
		StringBuilder sbSql = new StringBuilder();
		sbSql.append(
				"select min(sptime) as sptime from ud_zyxk_zyspryjl jl where jl.type='")
				.append(IConfigEncoding.SP).append("' and jl.dycode='")
				.append(IConfigEncoding.SP_SIGN_NUM)
				.append("' and jl.ud_zyxk_zysqid='")
				.append(workOrder.getUd_zyxk_zysqid()).append("';");
		try {
			Map<String, Object> map = getMapResult(sbSql.toString());
			if (map != null && map.size() > 0 && map.get("sptime") != null) {
				// 说明了已经做了会签操作
				String sptime = map.get("sptime").toString();
				SimpleDateFormat formart = new SimpleDateFormat(
						"yyyy-mm-dd HH:mm");
				Date spDate = formart.parse(sptime);
				if (gasDetection.getJctime() != null) {
					Date objJcDate = formart.parse(gasDetection.getJctime());
					if (spDate.compareTo(objJcDate) < 0) {
						throw new HDException("当前的气体检测时间，不能大于会签的审批时间（"+sptime+"）!");
					}
				}
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("数据库查询失败！", e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("气体检测时间格式不正确，不能审核！", e);
		}
	}

}
