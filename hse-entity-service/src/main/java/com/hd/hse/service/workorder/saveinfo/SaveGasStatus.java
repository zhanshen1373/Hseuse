/***********************************************************************
 * Module:  SaveGasStatus.java
 * Author:  zhaofeng
 * Purpose: Defines the Class SaveGasStatus
 ***********************************************************************/

package com.hd.hse.service.workorder.saveinfo;

import java.util.Map;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.entity.base.GasDetection;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * 更新气体检测的状态。。。
 * 
 * @pdOid f56e692d-3466-4436-bf29-ea9f6aa4b030
 */
public class SaveGasStatus extends AbstractCheckListener {
	/**
	 * @param action
	 * @param object
	 * @throws HDException
	 * @pdOid 67f52ffc-eefd-49f4-af81-4efb02d813a8
	 */
	public Object action(String action, Object... object) throws HDException {
		// TODO: implement
		Map<String, Object> map = objectCast(object[0]);
		Object gasObject = UtilTools.judgeMapValue(map, GasDetection.class,
				true);
		Object approvalObject = UtilTools.judgeMapValue(map,
				WorkApprovalPermission.class, false);
		saveGasStatus((GasDetection) gasObject, approvalObject == null ? null
				: (WorkApprovalPermission) approvalObject);
		// 是否是措施，如果是措施，审核一次数量减1；
		return null;
	}

	private void saveGasStatus(GasDetection gasDetection,
			WorkApprovalPermission approvalPermission) throws HDException {
		try {
			if (approvalPermission == null
					|| approvalPermission.getIsend() == 1) {
				gasDetection.setWritenbypda(IWorkOrderStatus.GAS_STATUS_FINISH);
				dao.updateEntity(connection, gasDetection,
						new String[] { "writenbypda" });
			}
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			throw new HDException("更新气体检测状态信息失败！");
		}
	}

}