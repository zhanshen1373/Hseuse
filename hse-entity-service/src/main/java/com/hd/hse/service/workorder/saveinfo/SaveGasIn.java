/***********************************************************************
 * Module:  SaveGasIn.java
 * Author:  zhaofeng
 * Purpose: Defines the Class SaveGasIn
 ***********************************************************************/

package com.hd.hse.service.workorder.saveinfo;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.util.Log;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.dao.SequenceGenerator;
import com.hd.hse.entity.base.GasDetectSub;
import com.hd.hse.entity.base.GasDetection;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkApprovalPersonRecord;
import com.hd.hse.entity.workorder.WorkDelay;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * 保存气体检测的信息
 * 
 * @pdOid 3cfb6eaa-d2e7-4af6-92c5-60135e2a1994
 */
public class SaveGasIn extends AbstractCheckListener {
	/**
	 * @param action
	 * @param object
	 * @throws HDException
	 * @pdOid b6a3009a-8516-4000-a32b-f04020a22bd4
	 */
	public Object action(String action, Object... object) throws HDException {
		// TODO: implement
		Map<String, Object> map = objectCast(object[0]);
		Object gasObject = UtilTools.judgeMapValue(map, GasDetection.class,
				true);
		Object approvalObject = UtilTools.judgeMapValue(map,
				WorkApprovalPermission.class, false);
		Object workObj = UtilTools.judgeMapValue(map, WorkOrder.class, true);
		WorkApprovalPersonRecord record = (WorkApprovalPersonRecord) UtilTools
				.judgeMapValue(map, WorkApprovalPersonRecord.class, true);
		saveGasIn((GasDetection) gasObject, (WorkOrder) workObj,
				approvalObject == null ? null
						: (WorkApprovalPermission) approvalObject);
		if (record == null) {
			record = new WorkApprovalPersonRecord();
			map.put(WorkApprovalPersonRecord.class.getName(), record);
		}
		record.setTablename(((GasDetection) gasObject).getDBTableName()
				.toUpperCase());
		record.setTableid(((GasDetection) gasObject).getUd_zyxk_qtjcid());// 在接下来的保存中使用
		// 是否是措施，如果是措施，审核一次数量减1；
		return null;
	}

	private void saveGasIn(GasDetection gasDetection, WorkOrder workOrder,
			WorkApprovalPermission approvalPermission) throws HDException {
		try {
			List<GasDetectSub> childList = (List) gasDetection
					.getChild(GasDetectSub.class.getName());
			// 是否合格校验
			if (childList != null && childList.size() > 0) {
				boolean isPass = true;
				for (int i = 0; i < childList.size(); i++) {
					GasDetectSub childGas = childList.get(i);
					if (childGas.getQtvalue() != null
							&& !StringUtils.isEmpty(childGas.getQtvalue()
									.toString())) {

						if (!UtilTools
								.isRangeIn(
										childGas.getMaxvalue() == null ? ""
												: childGas.getMaxvalue()
														.toString(),
										childGas.getQtvalue(),
										childGas.getMinvalue() == null ? ""
												: childGas.getMinvalue()
														.toString(),
										(childGas.getIsbj() == null
												|| StringUtils.isEmpty(childGas
														.getIsbj().toString()) || childGas
												.getIsbj() == 0) ? false : true)) {
							childGas.setIsPass(0);
							isPass = false;

						} else {
							childGas.setIsPass(1);
						}
					}else {
						childGas.setIsPass(-1);
					}
				}
				if (isPass) {
					gasDetection.setIshg(1);
				} else {
					gasDetection.setIshg(0);
				}
				if (StringUtils.isEmpty(gasDetection.getUd_zyxk_qtjcid())) {
					gasDetection.setUd_zyxk_zysqid(workOrder
							.getUd_zyxk_zysqid());
					gasDetection
							.setWritenbypda(IWorkOrderStatus.GAS_STATUS_PROCEED);
					SequenceGenerator.genPrimaryKeyValue(gasDetection,
							GasDetectSub.class);
					List<SuperEntity> gasLineList = gasDetection
							.getChild(GasDetectSub.class.getName());
					dao.insertEntity(connection, gasDetection);
					dao.insertEntities(connection, gasLineList);
				} else {
					if (approvalPermission.getIsQtjcDirector() == 1) {
						gasDetection.setIsonlyoxygenbelowgrade(1);
						List<SuperEntity> gasLineList = gasDetection
								.getChild(GasDetectSub.class.getName());
						dao.updateEntity(connection, gasDetection,
								new String[] { "ishg", "jcdept", "jcmethod",
										"jctime", "jclocation_desc",
										"isonlyoxygenbelowgrade" });
						dao.updateEntities(connection, gasLineList
								.toArray(new SuperEntity[gasLineList.size()]),
								new String[] { "qtvalue", "qttype" });
					} else {
						gasDetection.setIsonlyoxygenbelowgrade(0);
						List<SuperEntity> gasLineList = gasDetection
								.getChild(GasDetectSub.class.getName());
						dao.updateEntity(connection, gasDetection,
								new String[] { "ishg", "jcdept", "jcmethod",
										"jctime", "jclocation_desc" });
						dao.updateEntities(connection, gasLineList
								.toArray(new SuperEntity[gasLineList.size()]),
								new String[] { "qtvalue", "qttype" });
					}

				}
			}
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			throw new HDException("气体检测数据保存失败！", e);
		}
	}

}