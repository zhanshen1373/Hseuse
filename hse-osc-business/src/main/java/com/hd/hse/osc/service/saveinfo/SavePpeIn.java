/***********************************************************************
 * Module:  SavePpeIn.java
 * Author:  zhaofeng
 * Purpose: Defines the Class SavePpeIn
 ***********************************************************************/

package com.hd.hse.osc.service.saveinfo;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.workorder.WorkGuardEquipment;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;

/**
 * ClassName: SavePpeIn (个人防护装备信息保存)<br/>
 * date: 2014年10月21日 <br/>
 * 
 * @author ZhangJie
 * @version
 */
public class SavePpeIn extends AbstractCheckListener {
	/**
	 * @param action
	 * @param object
	 * @throws HDException
	 * @pdOid f3c3d842-263f-443d-a369-7c82ef20b676
	 */
	public Object action(String action, Object... object) throws HDException {
		Map<String, Object> mapParas = objectCast(object[0]);
		if (mapParas.containsKey(WorkGuardEquipment.class.getName())) {
			Object obj = mapParas.get(WorkGuardEquipment.class.getName());
			if (obj instanceof List<?>) {
				@SuppressWarnings("unchecked")
				List<WorkGuardEquipment> ppeList = (List<WorkGuardEquipment>) obj;
				if (ppeList.isEmpty()) {
					return super.action(action, object);
				}
				if (mapParas.containsKey(WorkOrder.class.getName())) {
					Object objworkorder = mapParas.get(WorkOrder.class
							.getName());
					if (objworkorder instanceof WorkOrder) {
						savePpeIn(ppeList, (WorkOrder) objworkorder);
					} else {
						savePpeIn(ppeList, null);
					}
				} else {
					savePpeIn(ppeList, null);
				}
			} else {
				throw new HDException(UNKNOW_ERROR);
			}
		}
		return super.action(action, object);
	}

	/**
	 * saveHarmIn:(保存ppe信息). <br/>
	 * date: 2014年10月21日 <br/>
	 * 
	 * @author ZhangJie
	 * @param hazardList
	 * @param workOrder
	 * @throws HDException
	 */
	private void savePpeIn(List<WorkGuardEquipment> hazardList,
			WorkOrder workOrder) throws HDException {
		try {
			WorkGuardEquipment[] waPpes = new WorkGuardEquipment[hazardList
					.size()];
			String whPpeString = "";
			for (int i = 0; i < waPpes.length; i++) {
				waPpes[i] = hazardList.get(i);
				waPpes[i].setIsupload(0);
				//value字段展示ispadselect
				waPpes[i].setValue(waPpes[i].getIspadselect());
				if (!StringUtils.isEmpty(waPpes[i].getGrfhzbnum())
						&& (1 == (waPpes[i].getIspadselect() == null ? 0
								: waPpes[i].getIspadselect()) || 1 == waPpes[i]
								.getIsselect()))
					whPpeString += (waPpes[i].getGrfhzbnum() + ",");
			}
			// 保存ppe表信息
			dao.updateEntities(connection, waPpes, new String[] { "isselect",
					"value", "ispadselect","description","isupload" });
			// 保存作业票ppe字段信息
			if (workOrder != null && !StringUtils.isEmpty(whPpeString)) {
				whPpeString = whPpeString
						.substring(0, whPpeString.length() - 1);
				workOrder.setGrfhzb(whPpeString);
				dao.updateEntity(connection, workOrder,
						new String[] { "grfhzb" });
			}
		} catch (DaoException e) {
			logger.error(e.getMessage(), e);
			throw new HDException("更新个人防护装备信息失败！");
		}
	}
}