/**
 * Project Name:hse-entity-service
 * File Name:SaveReviewOpeNum.java
 * Package Name:com.hd.hse.service.workorder.saveinfo
 * Date:2014年11月4日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 *
 */
package com.hd.hse.service.workorder.saveinfo;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.entity.base.MeasureReviewSub;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkMeasureReview;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * ClassName: SaveReviewOpeNum ()<br/>
 * date: 2014年11月4日 <br/>
 * 
 * @author zhaofeng
 * @version
 */
public class SaveReviewOpeNum extends AbstractCheckListener {

	@Override
	public Object action(String action, Object... object) throws HDException {
		// TODO Auto-generated method stub
		WorkMeasureReview cloneMeasureReview = null;// 克隆对象
		Map<String, Object> map = objectCast(object[0]);
		WorkMeasureReview measureReview = (WorkMeasureReview) UtilTools
				.judgeMapValue(map, WorkMeasureReview.class, true);
		WorkOrder workOrder = (WorkOrder) UtilTools.judgeMapValue(map,
				WorkOrder.class, true);
		// 克隆
		try {
			Map<String, Object> cloneMap = objectCast(object[1]);//
			cloneMeasureReview = (WorkMeasureReview) measureReview.clone();
			cloneMap.put(WorkMeasureReview.class.getName(), cloneMeasureReview);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("系统错误，请联系管理员！");
		}

		Object configObject = UtilTools.judgeMapValue(map,
				PDAWorkOrderInfoConfig.class, true);
		Object approvalObject = UtilTools.judgeMapValue(map,
				WorkApprovalPermission.class, false);
		saveReviewOpeNum(workOrder, cloneMeasureReview,
				(PDAWorkOrderInfoConfig) configObject,
				approvalObject == null ? null
						: (WorkApprovalPermission) approvalObject);
		return null;
	}

	/**
	 * saveReviewOpeNum:(措施复查和延期中的措施审核). <br/>
	 * date: 2014年11月4日 <br/>
	 * 
	 * @author zhaofeng
	 * @param measureReview
	 * @param pdaConfig
	 * @param approval
	 * @throws HDException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void saveReviewOpeNum(WorkOrder workOrder,
			WorkMeasureReview measureReview, PDAWorkOrderInfoConfig pdaConfig,
			WorkApprovalPermission approval) throws HDException {
		if (approval != null && approval.getIsend() != 1)
			return;// 非最终审核人，不允许修改状态
		try {
			if (pdaConfig.getConlevel() != null && pdaConfig.getConlevel() == 1) {

				String cstype = pdaConfig.getCstype() == null ? "" : UtilTools
						.convertToSqlString(pdaConfig.getCstype());
				String sql = "select isconfirm from ud_zyxk_zycsfcline where zycsfcnum='"
						+ measureReview.getZycsfcnum()
						+ "' and ifnull(cstype,'')  ";
						if("()".equals(cstype)){
							sql += " = '' ;";
						}else{
							sql += (" in " + cstype+ ";");
						}
//						+ "' and ','||ifnull(cstype,'')||',' like '%,"
//						+ cstype
//						+ ",%';";
				// 逐条
				List<MeasureReviewSub> measureList = (List) dao.executeQuery(
						connection, sql, new EntityListResult(
								MeasureReviewSub.class));
				boolean isconfirm = false;
				for (int i = 0; i < measureList.size(); i++) {
					if (!StringUtils.isEmpty(measureList.get(i).getIsconfirm())
							&& "1".equals(measureList.get(i).getIsconfirm())) {
						//
						isconfirm = true;
					} else {
						isconfirm = false;
						break;
					}
				}
				if (isconfirm) {
					if (measureReview.getCssavenum() == null
							|| !measureReview.getCssavenum().contains(
									pdaConfig.getDycode())) {
						if(measureReview.getCsnum()==null){
							measureReview.setCsnum(pdaConfig.getChildCount());
						}
						// 表示措施已经完成
						measureReview.setCsnum(measureReview.getCsnum() - 1);
						measureReview.setCssavenum(measureReview.getCssavenum()
								+ "," + pdaConfig.getDycode());
						// // 更新数量
						dao.updateEntity(connection, measureReview,
								new String[] { "csnum", "cssavenum" });
					}
				}
			} else if (approval == null || approval.getIsend() == 1) {
				if (measureReview.getCssavenum() == null
						|| !measureReview.getCssavenum().contains(
								pdaConfig.getDycode())) {
					if(measureReview.getCsnum()==null){
						measureReview.setCsnum(pdaConfig.getChildCount());
					}
					// 保存数据
					measureReview.setCsnum(measureReview.getCsnum() - 1);
					measureReview.setCssavenum(measureReview.getCssavenum()
							+ "," + pdaConfig.getDycode());
					// // 更新数量
					dao.updateEntity(connection, measureReview, new String[] {
							"csnum", "cssavenum" });
				}
			}
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			throw new HDException("更新状态失败！", e);
		}

	}
}
