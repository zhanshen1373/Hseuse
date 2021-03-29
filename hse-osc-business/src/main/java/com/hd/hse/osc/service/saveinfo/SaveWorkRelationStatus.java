/**
 * Project Name:hse-entity-service
 * File Name:SaveWorkRelationStatus.java
 * Package Name:com.hd.hse.service.workorder.saveinfo
 * Date:2014年10月24日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.osc.service.saveinfo;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.constant.IWorkOrderZypClass;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * ClassName: SaveWorkRelationStatus (退回&作废连带修改状态)<br/>
 * date: 2014年10月24日 <br/>
 * 
 * @author zhaofeng
 * @version
 */
public class SaveWorkRelationStatus extends AbstractCheckListener {

	RelationTableName relationTableName;

	@Override
	public Object action(String action, Object... object) throws HDException {
		Map<String, Object> map = objectCast(object[0]);
		WorkOrder workOrder = (WorkOrder) UtilTools.judgeMapValue(map,
				WorkOrder.class, true);
		WorkApprovalPermission approvalPermission = (WorkApprovalPermission) UtilTools
				.judgeMapValue(map, WorkApprovalPermission.class, false);
		if (approvalPermission == null || approvalPermission.getIsend() == 1) {
			WorkOrder cloneWorkOrder = null;// 克隆对象
			// 克隆
			try {
				Map<String, Object> cloneMap = objectCast(object[1]);//
				cloneWorkOrder = (WorkOrder) workOrder.clone();
				cloneMap.put(WorkOrder.class.getName(), cloneWorkOrder);
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
				throw new HDException("系统错误，请联系管理员！");
			}

			if (IActionType.ACTION_TYPE_RETURN.equals(action)) {
				cloneWorkOrder.setStatus(IWorkOrderStatus.WAPPR);
				cloneWorkOrder.setSpstatus(IWorkOrderStatus.WAPPR);
			} else if (IActionType.ACTION_TYPE_NULLIFY.equals(action)) {
				cloneWorkOrder.setStatus(IWorkOrderStatus.NULLIFY);
				cloneWorkOrder.setSpstatus(IWorkOrderStatus.NULLIFY);
			}
			saveWorkRelationStatus(cloneWorkOrder, approvalPermission);
			saveEnergyIsolationStatus(cloneWorkOrder);
		}
		return null;
	}

	private void saveWorkRelationStatus(WorkOrder workOrder,
			WorkApprovalPermission approvalPermission) throws HDException {
		try {
			if (workOrder.getStatus() == null)
				throw new HDException("获取作业票的【status】属性值为空！");
			if (workOrder.getSpstatus() == null)
				throw new HDException("获取作业票的【spstatus】属性值为空！");
			if (workOrder.getUd_zyxk_zysqid() == null)
				throw new HDException("获取作业票的【ud_zyxk_zysqid】属性值为空！");
			StringBuilder sbSql = new StringBuilder();
			sbSql.append("update ud_zyxk_zysq set status='")
					.append(workOrder.getStatus()).append("',spstatus='")
					.append(workOrder.getSpstatus()).append("' ");
			//更新isupload字段为0 2017/3/7 yn
			sbSql.append(",isupload = 0 ");
			// 更新作废原因 开始
			sbSql.append(",zfcause = ");
			sbSql.append(" case ").append(
					" when zfcause is null or zfcause =''");
			sbSql.append(" then '").append(workOrder.getZfcause()).append("' ");
			sbSql.append(" else zfcause ").append("end ");
			// 更新作废原因 结束
			sbSql.append(" where ud_zyxk_zysqid='")
					.append(workOrder.getUd_zyxk_zysqid()).append("'");
			if (IWorkOrderZypClass.ZYPCLASS_ZYDP
					.equals(workOrder.getZypclass())) {
				relationTableName = getRelationTableName(IRelativeEncoding.BIGANDSPECIAL);
				if (relationTableName != null) {
					sbSql.append(" or parent_id='")
							.append(workOrder.getUd_zyxk_zysqid()).append("'");
					if (!StringUtils.isEmpty(relationTableName.getSys_value())) {
						String sqlZypType = UtilTools
								.convertToSqlString(relationTableName
										.getSys_value());
						sbSql.append("  and zypclass in ").append(sqlZypType)
								.append("  ");
					}
				}

			} else if (IWorkOrderZypClass.ZYPCLASS_DHZY.equals(workOrder
					.getZypclass()) && isRelationAction(IRelativeEncoding.DHYD)) {
				sbSql.append(" or dhzy_id='")
						.append(workOrder.getUd_zyxk_zysqid()).append("'");
			}
			sbSql.append(";");
			dao.executeUpdate(connection, sbSql.toString());
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("大小票关系-更新作业申请中的状态字段的值失败！");
		}
	}

	// 更新能量隔离状态
	private void saveEnergyIsolationStatus(WorkOrder workOrder)
			throws HDException {
		if (workOrder.getUd_zyxk_zysqid() == null)
			throw new HDException("获取作业票的【ud_zyxk_zysqid】属性值为空！");
		try {
			String sql = "update ud_zyxk_nlgld set status = 'JCAPPR' where UD_ZYXK_ZYSQID = '"
					+ workOrder.getUd_zyxk_zysqid() + "';";
			dao.executeUpdate(connection, sql);
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			throw new HDException("大小票关系-更新能量隔离状态的值失败！");
		}
	}
}
