/***********************************************************************
 * Module:  SaveMeasureIn.java
 * Author:  zhaofeng
 * Purpose: Defines the Class SaveMeasureIn
 ***********************************************************************/

package com.hd.hse.osc.service.saveinfo;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.workorder.WorkApplyMeasure;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * 保存措施信息
 * 
 * @pdOid dd4ea53c-fdf9-4e88-a74b-54de09cd68f9
 */
public class SaveMeasureIn extends AbstractCheckListener {
	/**
	 * @param action
	 * @param object
	 * @throws HDException
	 * @pdOid 5ca54cee-572c-40f8-b67d-bb33ac957d4d
	 */
	@SuppressWarnings("unchecked")
	public Object action(String action, Object... object) throws HDException {
		// TODO: implement
		Map<String, Object> map = objectCast(object[0]);
		PDAWorkOrderInfoConfig workConfig = (PDAWorkOrderInfoConfig) UtilTools
				.judgeMapValue(map, PDAWorkOrderInfoConfig.class, true);
		// if (workConfig.getConlevel() != null && workConfig.getConlevel() ==
		// 1)
		// return null;// 逐条

		Object measureListObject = UtilTools.judgeMapListValue(map,
				WorkApplyMeasure.class, false);
		if (measureListObject == null || ((List) measureListObject).size() == 0)
			return null;
		Object workObject = UtilTools.judgeMapValue(map, WorkOrder.class, true);

		Object approvalObject = UtilTools.judgeMapValue(map,
				WorkApprovalPermission.class, false);
		saveMeasureIn((WorkOrder) workObject,
				(List<WorkApplyMeasure>) measureListObject,
				approvalObject == null ? null
						: (WorkApprovalPermission) approvalObject, workConfig);
		// 是否是措施，如果是措施，审核一次数量减1；
		return null;
	}

	private void saveMeasureIn(WorkOrder workOrder,
			List<WorkApplyMeasure> measureList,
			WorkApprovalPermission approvalPermission,
			PDAWorkOrderInfoConfig workConfig) throws HDException {
		try {
			WorkApplyMeasure[] waMeasures = (WorkApplyMeasure[]) measureList
					.toArray(new WorkApplyMeasure[measureList.size()]);

			String isConfirm = "0";
			if (workConfig.getConlevel() != null
					&& workConfig.getConlevel() == 1) {
				//逐条审核
				isConfirm = "1";
			}else if(approvalPermission == null || approvalPermission.getIsend() == 1){
				isConfirm = "1";
			}
			for (int i = 0; i < waMeasures.length; i++) {
				if (waMeasures[i].getCheckresult() != null
						&& waMeasures[i].getCheckresult() != -1)
					waMeasures[i].setIsconfirm(isConfirm);
				if (waMeasures[i].getCheckresult() == 2) {
					// 表示不适用
					waMeasures[i].setValue(0);
				} else if (waMeasures[i].getCheckresult() == 0) {
					// 表示否
					waMeasures[i].setValue(0);
				} else {
					// 表示是
					waMeasures[i].setValue(1);
				}
				waMeasures[i].setIsupload(0);
				// String strWhshib = waMeasures[i].getHazardid();
				// if (!StringUtils.isEmpty(strWhshib)
				// && (StringUtils.isEmpty(workOrder.getWhshib()) || !workOrder
				// .getWhshib().contains("," + strWhshib + ","))
				// && !whshibString.toString().contains(
				// "," + strWhshib + ",")) {
				// whshibString.append(strWhshib).append(",");
				// }
			}
			dao.updateEntities(connection, waMeasures, new String[] {
					"isselect", "value", "checkresult", "description",
					"isconfirm", "isupload" });
			// if (workOrder != null && whshibString.toString().length() > 1) {
			// whshibString.delete(whshibString.length() - 1,
			// whshibString.length()).delete(0, 1);
			// workOrder.setWhshib((workOrder.getWhshib()==null?"":workOrder.getWhshib()+",")+whshibString.toString());
			// dao.updateEntity(connection, workOrder,
			// new String[] { "whshib" });
			// }
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("更新措施信息失败！");
		}
	}

}