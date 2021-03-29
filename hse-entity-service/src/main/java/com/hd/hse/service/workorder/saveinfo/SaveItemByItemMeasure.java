/**
 * Project Name:hse-entity-service
 * File Name:SaveItemByItemMeasure.java
 * Package Name:com.hd.hse.service.workorder.saveinfo
 * Date:2014年10月24日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.service.workorder.saveinfo;

import java.util.List;
import java.util.Map;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.constant.ITableName;
import com.hd.hse.dao.SequenceGenerator;
import com.hd.hse.entity.base.MeasureReviewSub;
import com.hd.hse.entity.base.MultitermMeasureAffirm;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.workorder.WorkApplyMeasure;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * ClassName: SaveItemByItemMeasure ()<br/>
 * date: 2014年10月24日 <br/>
 * 
 * @author zhaofeng
 * @version
 */
public abstract class SaveItemByItemMeasure extends AbstractCheckListener {
	
	private WorkApprovalPermission cloneApprovalPermission;

	@SuppressWarnings("unchecked")
	@Override
	public Object action(String action, Object... object) throws HDException {
		// TODO Auto-generated method stub
		Map<String, Object> map = objectCast(object[0]);
		PDAWorkOrderInfoConfig workConfig = (PDAWorkOrderInfoConfig) UtilTools
				.judgeMapValue(map, PDAWorkOrderInfoConfig.class, true);
		if (workConfig.getConlevel() == null || workConfig.getConlevel() == 0)
			return null;// 非逐条
		List<SuperEntity> measureList = (List<SuperEntity>) UtilTools
				.judgeMapListValue(map, MeasureReviewSub.class, false);
		if (measureList == null)
			measureList = (List<SuperEntity>) UtilTools.judgeMapListValue(map,
					WorkApplyMeasure.class, false);
		WorkOrder workOrder = (WorkOrder) UtilTools.judgeMapValue(map,
				WorkOrder.class, true);

		WorkApprovalPermission approvalPermission = (WorkApprovalPermission) UtilTools
				.judgeMapValue(map, WorkApprovalPermission.class, false);
		try {
			if (null != approvalPermission) {
				Map<String, Object> clone = objectCast(object[1]);
				cloneApprovalPermission = (WorkApprovalPermission) approvalPermission
						.clone();
				clone.put(WorkApprovalPermission.class.getName(),
						cloneApprovalPermission);
			}
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("系统出错，请联系管理员！", e);
		}
		MultitermMeasureAffirm paraMeasureAffirm = new MultitermMeasureAffirm();

		// 子类实现
		setWorkOrderParams(paraMeasureAffirm, workOrder, measureList.get(0));

		// String tableid = "";
		// String tablename = "";
		// if (IConfigEncoding.SP.equals(workConfig.getPscode())) {
		// tablename = ITableName.UD_ZYXK_ZYSQ;
		// workOrder = (WorkOrder) UtilTools.judgeMapValue(map,
		// WorkOrder.class, true);
		// measureList = (List<SuperEntity>) UtilTools.judgeMapListValue(map,
		// WorkApplyMeasure.class, true);
		// tableid = workOrder.getUd_zyxk_zysqid();
		// } else if (IConfigEncoding.FC.equals(workConfig.getPscode())) {
		// tablename = ITableName.UD_ZYXK_ZYCSFC;
		// workOrder = (WorkOrder) UtilTools.judgeMapValue(map,
		// WorkOrder.class, true);
		// measureList = (List<SuperEntity>) UtilTools.judgeMapListValue(map,
		// MeasureReviewSub.class, true);
		// if (measureList.get(0).getAttribute("zycsfcnum") == null)
		// throw new HDException("获取措施的编码字段【zycsfcnum】");
		// tableid = measureList.get(0).getAttribute("zycsfcnum").toString();
		// } else if (IConfigEncoding.YQ.equals(workConfig.getPscode())) {
		// tablename = ITableName.UD_ZYXK_ZYYQ;
		// workOrder = (WorkOrder) UtilTools.judgeMapValue(map,
		// WorkOrder.class, false);
		// measureList = (List<SuperEntity>) UtilTools.judgeMapListValue(map,
		// MeasureReviewSub.class, true);
		// if (measureList.get(0).getAttribute("zycsfcnum") == null)
		// throw new HDException("获取措施的编码字段【zycsfcnum】");
		// tableid = measureList.get(0).getAttribute("zycsfcnum").toString();
		// }
		MultitermMeasureAffirm[] multitermMeasureAffirms = new MultitermMeasureAffirm[measureList
				.size()];
		String now = UtilTools.getSysCurrentTime();
		if (cloneApprovalPermission != null)
			cloneApprovalPermission.setSptime(now);
		for (int i = 0; i < multitermMeasureAffirms.length; i++) {
			if (measureList.get(i).getAttribute("isselect") == null)
				throw new HDException("措施的选择状态字段【isselect】获取失败！");
			if (measureList.get(i).getAttribute("precautionid") == null)
				throw new HDException("措施编码字段【precautionid】获取失败！");
			MultitermMeasureAffirm multitermMeasureAffirm = new MultitermMeasureAffirm();
			multitermMeasureAffirm.setUd_zyxk_zysqid(workOrder == null ? null
					: workOrder.getUd_zyxk_zysqid());
			multitermMeasureAffirm.setTableid(paraMeasureAffirm.getTableid());
			multitermMeasureAffirm.setUd_type(workConfig.getPscode());
			multitermMeasureAffirm.setOpid(measureList.get(i)
					.getAttribute("precautionid").toString());
			multitermMeasureAffirm.setStatus(measureList.get(i)
					.getAttribute("isselect").toString());
			multitermMeasureAffirm.setSptime(now);
			multitermMeasureAffirm.setDatasource("PDA");
			multitermMeasureAffirm.setTablename(paraMeasureAffirm
					.getTablename());
			if (approvalPermission != null) {
				multitermMeasureAffirm.setOpcode(approvalPermission
						.getPersonid());
				multitermMeasureAffirm.setOpname(approvalPermission
						.getPersondesc());
				multitermMeasureAffirm.setSpnode(approvalPermission
						.getSpfield());
				multitermMeasureAffirm.setSpnode_name(approvalPermission
						.getSpfield_desc());
				multitermMeasureAffirm.setTabcode(approvalPermission
						.getZylocation());
			}
			multitermMeasureAffirms[i] = multitermMeasureAffirm;
		}
		saveItemByItemMeasure(multitermMeasureAffirms);
		// 是否是措施，如果是措施，审核一次数量减1；
		return null;
	}

	private void saveItemByItemMeasure(
			MultitermMeasureAffirm[] multitermMeasureAffirms)
			throws HDException {
		try {
			SequenceGenerator.genPrimaryKeyValue(multitermMeasureAffirms);
			StringBuilder sBuilder = new StringBuilder();
			for (MultitermMeasureAffirm m : multitermMeasureAffirms) {
				sBuilder.append(m.getUd_id() + ",");
			}
			if (cloneApprovalPermission != null) {
				cloneApprovalPermission.setUd_onemeasure_person_ids(sBuilder.toString());
			}
			dao.insertEntities(connection, multitermMeasureAffirms);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("保存逐条措施信息失败！");
		}
	}

	/**
	 * setWorkOrderParams:(逐条措施保存前设置参数). <br/>
	 * date: 2014年11月28日 <br/>
	 * 
	 * @author zhaofeng
	 * @param workOrder
	 * @param map
	 */
	public abstract void setWorkOrderParams(
			MultitermMeasureAffirm paraMeasureAffirm, WorkOrder workOrder,
			SuperEntity measure) throws HDException;

}
