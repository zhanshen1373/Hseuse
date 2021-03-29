/***********************************************************************
 * Module:  SaveWorkTabOpeNum.java
 * Author:  zhaofeng
 * Purpose: Defines the Class SaveWorkTabOpeNum
 ***********************************************************************/

package com.hd.hse.osc.service.saveinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.workorder.WorkApplyMeasure;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * 更改作业申请中标记“个人防护”，‘危害识别’，‘措施’操作的字段
 * 
 * @pdOid 67dc5ec4-8241-49cf-9cdb-4d12a635a771
 */
public class SaveWorkTabOpeNum extends AbstractCheckListener {
	/**
	 * @param action
	 * @param object
	 * @pdOid f7931976-f977-4ba4-ae20-ad82542b6f29
	 */
	public Object action(String action, Object... object) throws HDException {
		// TODO: implement
		WorkOrder cloneWorkOrder = null;// 克隆对象
		Map<String, Object> map = objectCast(object[0]);
		WorkOrder workOrder = (WorkOrder) UtilTools.judgeMapValue(map,
				WorkOrder.class, true);

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

		Object configObject = UtilTools.judgeMapValue(map,
				PDAWorkOrderInfoConfig.class, true);
		Object approvalObject = UtilTools.judgeMapValue(map,
				WorkApprovalPermission.class, false);
		saveWorkTabOpeNum(cloneWorkOrder,
				(PDAWorkOrderInfoConfig) configObject,
				approvalObject == null ? null
						: (WorkApprovalPermission) approvalObject);
		// 是否是措施，如果是措施，审核一次数量减1；
		return null;
	}

	private void saveWorkTabOpeNum(WorkOrder workOrder,
			PDAWorkOrderInfoConfig workConfig, WorkApprovalPermission approval)
			throws HDException {
		try {
			if (workConfig.getContype() == IConfigEncoding.MEASURE_TYPE) {
				// 走措施
				if (workOrder.getCsnum() == null
						|| (workOrder.getCsnum() == 0 && StringUtils
								.isEmpty(workOrder.getCssavenum()))) {
					workOrder.setCsnum(workConfig.getChildCount());
					dao.updateEntity(connection, workOrder,
							new String[] { "csnum" });
				}
				if (approval != null && approval.getIsend() != 1)
					return;// 非最终审核人，不允许修改状态
				if (workConfig.getConlevel() != null
						&& workConfig.getConlevel() == 1) {
					// 走逐条或者逐条批量
					String cstype = workConfig.getCstype() == null ? ""
							: workConfig.getCstype();
					String newcstype = UtilTools.convertToSqlString(cstype);
					String sql = "select isconfirm from ud_zyxk_zysq2precaution where ud_zyxk_zysqid='"
							+ workOrder.getUd_zyxk_zysqid()
							+ "' and ifnull(cstype,'')  ";
					if ("()".equals(newcstype)) {
						sql += " = '' ;";
					} else {
						sql += (" in " + newcstype + ";");
					}
					List<WorkApplyMeasure> measureList = (List) dao
							.executeQuery(
									connection,
									sql,
									new EntityListResult(WorkApplyMeasure.class));
					boolean isconfirm = false;
					for (int i = 0; i < measureList.size(); i++) {
						if (!StringUtils.isEmpty(measureList.get(i)
								.getIsconfirm())
								&& "1".equals(measureList.get(i).getIsconfirm())) {
							//
							isconfirm = true;
						} else {
							isconfirm = false;
							break;
						}
					}
					if (isconfirm
							&& (workOrder.getCssavenum() == null || !workOrder
									.getCssavenum().contains(
											workConfig.getDycode()))) {
						// 表示措施已经完成
						workOrder.setCsnum(workOrder.getCsnum() - 1);
						workOrder.setCssavenum(workOrder.getCssavenum() + ","
								+ workConfig.getDycode());
						if (workOrder.getCsnum() == 0) {
							// 更新数量和标记完成
							if (workOrder.getCssavefied() == null) {
								workOrder.setCssavefied(workConfig.getContype()
										.toString());
							} else {
								workOrder.setCssavefied(workOrder
										.getCssavefied()
										+ ","
										+ workConfig.getContype());
							}
							dao.updateEntity(connection, workOrder,
									new String[] { "csnum", "cssavefied",
											"cssavenum" });
						} else {
							// 更新数量
							dao.updateEntity(connection, workOrder,
									new String[] { "csnum", "cssavenum" });
						}
					}
				} else {
					if ((approval == null || approval.getIsend() == 1)
							&& (workOrder.getCssavenum() == null || !workOrder
									.getCssavenum().contains(
											workConfig.getDycode()))) {
						workOrder.setCssavenum(workOrder.getCssavenum() + ","
								+ workConfig.getDycode());
						workOrder.setCsnum(workOrder.getCsnum() - 1);
						if (workOrder.getCsnum() == 0) {
							// 更新数量和标记完成
							if (workOrder.getCssavefied() == null) {
								workOrder.setCssavefied(workConfig.getContype()
										.toString());
							} else {
								workOrder.setCssavefied(workOrder
										.getCssavefied()
										+ ","
										+ workConfig.getContype());
							}
							dao.updateEntity(connection, workOrder,
									new String[] { "csnum", "cssavefied",
											"cssavenum" });
						} else {
							// 更新数量
							dao.updateEntity(connection, workOrder,
									new String[] { "csnum", "cssavenum" });
						}
					}
				}
			} else {
				if (approval == null || approval.getIsend() == 1) {
					// 非措施
					if (workConfig.getContype() != IConfigEncoding.HARM_TYPE
							|| workOrder.getWhtype() == null
							|| workOrder.getWhtype().equals("")) { // 非危害或者是危害不分类
						if (workOrder.getCssavefied() == null) {
							workOrder.setCssavefied(workConfig.getContype()
									.toString());
						} else {
							if (!("," + workOrder.getCssavefied() + ",").contains("," + workConfig.getContype() + ",")) {
								workOrder.setCssavefied(workOrder.getCssavefied()
										+ "," + workConfig.getContype());
							}
						}
						dao.updateEntity(connection, workOrder,
								new String[] { "cssavefied" });
					} else { // 危害识别分类
						String[] whtypes = workOrder.getWhtype().split(",");
						List<String> temps = new ArrayList<>();
						for (int i = 0; i < whtypes.length; i++) {
							if (!whtypes[i].equals(workConfig.getCstype())) {
								temps.add(whtypes[i]);
							}
						}
						String whtype = "";
						for (int i = 0; i < temps.size(); i++) {
							whtype = whtype + temps.get(i) + ",";
						}
						workOrder.setWhtype(whtype);
						// 判断危害识别是不是全部审核过了
						if (temps.size() == 0) { // 危害全部审核过了
							if (workOrder.getCssavefied() == null) {
								workOrder.setCssavefied(workConfig.getContype()
										.toString());
							} else {
								workOrder.setCssavefied(workOrder
										.getCssavefied()
										+ ","
										+ workConfig.getContype());
							}
							dao.updateEntity(connection, workOrder,
									new String[] { "cssavefied", "whtype" });
						} else { // 未全部审核
							dao.updateEntity(connection, workOrder,
									new String[] { "whtype" });
						}
					}
				}
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("更新作业申请中的操作标记字段的值失败！");
		}
	}

}