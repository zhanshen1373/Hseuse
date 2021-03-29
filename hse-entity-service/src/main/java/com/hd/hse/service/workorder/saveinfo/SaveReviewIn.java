/***********************************************************************
 * Module:  SaveReviewIn.java
 * Author:  zhaofeng
 * Purpose: Defines the Class SaveReviewIn
 ***********************************************************************/

package com.hd.hse.service.workorder.saveinfo;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IActionType;
import com.hd.hse.dao.SequenceGenerator;
import com.hd.hse.entity.base.GasDetection;
import com.hd.hse.entity.base.MeasureReviewSub;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkApprovalPersonRecord;
import com.hd.hse.entity.workorder.WorkMeasureReview;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * 保存复查信息。。。
 * 
 * @pdOid 7f50e01a-acf4-4bc9-87be-c0ad019cd2bf
 */
public class SaveReviewIn extends AbstractCheckListener {
	/**
	 * @param action
	 * @param object
	 * @throws HDException
	 * @pdOid 7cc6da18-0c11-4b41-b5ed-31f35f49b4ea
	 */
	@SuppressWarnings("unchecked")
	public Object action(String action, Object... object) throws HDException {
		// TODO: implement
		Map<String, Object> map = objectCast(object[0]);
		Object workObject = UtilTools.judgeMapValue(map, WorkOrder.class, true);
		WorkMeasureReview measureObject = (WorkMeasureReview) UtilTools
				.judgeMapValue(map, WorkMeasureReview.class, true);
		WorkApprovalPersonRecord record = (WorkApprovalPersonRecord) UtilTools
				.judgeMapValue(map, WorkApprovalPersonRecord.class, false);
		if (record == null) {
			record = new WorkApprovalPersonRecord();
			map.put(WorkApprovalPersonRecord.class.getName(), record);
		}
		record.setTablename(measureObject.getDBTableName().toUpperCase());
		record.setTableid(measureObject.getZycsfcnum());// 在接下来的保存中使用
//		if (IActionType.JJB_GUARDIAN_CHANGE.equals(action)
//				|| IActionType.JJB_WORK_PERSON_CHANGE.equals(action)) {
//			// 走交接班
//			WorkMeasureReview clonMeasureReview = null;
//			Map<String, Object> cloneMap = objectCast(object[1]);
//			try {
//				clonMeasureReview = (WorkMeasureReview) measureObject.clone();
//				cloneMap.put(WorkMeasureReview.class.getName(),
//						clonMeasureReview);
//				saveJJBReviewIn(clonMeasureReview);
//			} catch (CloneNotSupportedException e) {
//				// TODO Auto-generated catch block
//				logger.error(e.getMessage(), e);
//				throw new HDException("系统出错，请联系管理员！", e);
//			}
//			return null;
//		}
		Object subsObject = UtilTools.judgeMapListValue(map,
				MeasureReviewSub.class, true);
		Object configObject = UtilTools.judgeMapValue(map,
				PDAWorkOrderInfoConfig.class, true);
		Object approvalObject = UtilTools.judgeMapValue(map,
				WorkApprovalPermission.class, false);
		saveReviewIn((PDAWorkOrderInfoConfig) configObject,
				(WorkApprovalPermission) approvalObject,
				(WorkOrder) workObject, (List<MeasureReviewSub>) subsObject);
		return null;
	}

	/**
	 * saveReviewIn:(更新措施表中的信息). <br/>
	 * date: 2014年11月7日 <br/>
	 * 
	 * @author zhaofeng
	 * @param workOrder
	 * @param childSubs
	 * @throws HDException
	 */
	private void saveReviewIn(PDAWorkOrderInfoConfig workConfig,
			WorkApprovalPermission approvalPermission, WorkOrder workOrder,
			List<MeasureReviewSub> childSubs) throws HDException {
		try {
			// StringBuilder whshibString = new StringBuilder();
			// whshibString.append(",");
			String isConfirm = "0";
			if (workConfig.getConlevel() != null
					&& workConfig.getConlevel() == 1) {
				// 逐条审核
				isConfirm = "1";
			} else if (approvalPermission == null
					|| approvalPermission.getIsend() == 1) {
				isConfirm = "1";
			}
			for (int i = 0; i < childSubs.size(); i++) {
				childSubs.get(i).setIsconfirm(isConfirm);
				if (childSubs.get(i).getCheckresult() == 2) {
					// 表示不适用
					childSubs.get(i).setValue(0);
				} else if (childSubs.get(i).getCheckresult() == 0) {
					// 表示否
					childSubs.get(i).setValue(0);
				} else {
					// 表示是
					childSubs.get(i).setValue(1);
				}
				// String strWhshib = childSubs.get(i).getHazardid();
				// if (!StringUtils.isEmpty(strWhshib)
				// && (StringUtils.isEmpty(workOrder.getWhshib()) || !workOrder
				// .getWhshib().contains("," + strWhshib + ","))
				// && !whshibString.toString().contains(
				// "," + strWhshib + ",")) {
				// whshibString.append(strWhshib).append(",");
				// }
			}
			dao.updateEntities(connection,
					childSubs.toArray(new MeasureReviewSub[childSubs.size()]),
					new String[] { "isselect", "value", "checkresult",
							"description", "isconfirm" });

			// if (workOrder != null && whshibString.toString().length() > 1) {
			// whshibString.delete(whshibString.length() - 1,
			// whshibString.length()).delete(0, 1);
			// workOrder.setWhshib((workOrder.getWhshib()==null?"":workOrder.getWhshib()+",")+whshibString.toString());
			// dao.updateEntity(connection, workOrder,
			// new String[] { "whshib" });
			// }
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			throw new HDException("保存复查信息失败！");
		}
	}

//	/**
//	 * saveJJBReviewIn:(交接班信息保存). <br/>
//	 * date: 2014年11月7日 <br/>
//	 * 
//	 * @author zhaofeng
//	 * @param measureReview
//	 * @throws HDException
//	 */
//	private void saveJJBReviewIn(WorkMeasureReview measureReview)
//			throws HDException {
//		if (StringUtils.isEmpty(measureReview.getZycsfcnum())) {
//			// 新增
//			try {
//				SequenceGenerator
//						.genPrimaryKeyValue(new SuperEntity[] { measureReview });
//				dao.insertEntity(connection, measureReview);
//			} catch (DaoException e) {
//				// TODO Auto-generated catch block
//				logger.error(e.getMessage(), e);
//				throw new HDException("保存数据失败！", e);
//			}
//		} else {
//			// 不操作
//		}
//	}

}