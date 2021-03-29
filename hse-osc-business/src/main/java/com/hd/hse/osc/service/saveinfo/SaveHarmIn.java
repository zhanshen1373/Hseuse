/***********************************************************************
 * Module:  SaveHarmIn.java
 * Author:  zhangjie
 * Purpose: Defines the Class SaveHarmIn
 ***********************************************************************/

package com.hd.hse.osc.service.saveinfo;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.base.HazardNotify;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;

/**
 * ClassName: SaveHarmIn (现场核查-保存危害信息)<br/>
 * date: 2014年10月21日 <br/>
 * 
 * @author ZhangJie
 * @version
 */
public class SaveHarmIn extends AbstractCheckListener {
	/**
	 * TODO
	 * 
	 * @see com.hd.hse.service.workorder.checkrules.AbstractCheckListener#action(String,
	 *      Object[])
	 */
	public Object action(String action, Object... object) throws HDException {
		Map<String, Object> mapParas = objectCast(object[0]);
		if (mapParas.containsKey(HazardNotify.class.getName())) {
			Object obj = mapParas.get(HazardNotify.class.getName());
			if (obj instanceof List<?>) {
				@SuppressWarnings("unchecked")
				List<HazardNotify> hazardList = (List<HazardNotify>) obj;
				if (hazardList.isEmpty()) {
					return super.action(action, object);
				}
				if (mapParas.containsKey(HazardNotify.class.getName()))

				{
					Object objworkorder = mapParas.get(WorkOrder.class
							.getName());
					if (objworkorder instanceof WorkOrder) {
						saveHarmIn(hazardList, (WorkOrder) objworkorder);
					} else {
						saveHarmIn(hazardList, null);
					}
				} else {
					saveHarmIn(hazardList, null);
				}
			} else {
				throw new HDException(UNKNOW_ERROR);
			}
		}
		return super.action(action, object);
	}

	/**
	 * saveHarmIn:(保存危害信息). <br/>
	 * date: 2014年10月21日 <br/>
	 * 
	 * @author ZhangJie
	 * @param hazardList
	 * @param workOrder
	 * @throws HDException
	 */
	private void saveHarmIn(List<HazardNotify> hazardList, WorkOrder workOrder)
			throws HDException {
		try {
			HazardNotify[] waHazards = new HazardNotify[hazardList.size()];
			String whshibString = "";
			for (int i = 0; i < waHazards.length; i++) {
				waHazards[i] = hazardList.get(i);
				waHazards[i].setIsupload(0);
				if (!StringUtils.isEmpty(waHazards[i].getHazardid())
						&& (1 == (waHazards[i].getIspadselect() == null ? 0
								: waHazards[i].getIspadselect()) || 1 == waHazards[i]
								.getIsselect()))
					whshibString += (waHazards[i].getHazardid() + ",");
			}
			// 保存危害表信息
			dao.updateEntities(connection, waHazards, new String[] {
					"isselect", "ispadselect" ,"description", "isupload"});
			// 保存作业票危害字段信息
			if (workOrder != null && !StringUtils.isEmpty(whshibString)) {
				whshibString = whshibString.substring(0,
						whshibString.length() - 1);
				workOrder.setWhshib(whshibString);
				dao.updateEntity(connection, workOrder,
						new String[] { "whshib" });
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("更新措施信息失败！");
		}
	}
}
