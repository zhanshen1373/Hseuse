/***********************************************************************
 * Module:  SaveWorkStatus.java
 * Author:  zhaofeng
 * Purpose: Defines the Class SaveWorkStatus
 ***********************************************************************/

package com.hd.hse.service.workorder.saveinfo;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 更改作业票的状态，，，
 * 
 * @pdOid 8873a7f1-7a6a-468d-b854-fa818ee570b5
 */
public abstract class SaveWorkStatus extends AbstractCheckListener {
	/**
	 * @param action
	 * @param object
	 * @pdOid 5f8dc8c9-36e4-464b-aba0-098d9141a942
	 */
	public Object action(String action, Object... object) throws HDException {
		// TODO: implement
		Map<String, Object> map = objectCast(object[0]);

		WorkApprovalPermission approval = (WorkApprovalPermission) UtilTools
				.judgeMapValue(map, WorkApprovalPermission.class, false);

		Map<String, Object> cloneMap = objectCast(object[1]);// 克隆集合对象

		// 公共配置走的流程，根据配置环节点修改作业票的状态
		if (approval != null && !StringUtils.isEmpty(approval.getStatus())) {
			// 如果环节点的status字段不为空，则表示该环节点具有将作业票的状态修改成status的值的功能。
			// 克隆对象
			WorkOrder cloneWorkOrder = cloneObject(map, cloneMap);
			// --公共实现
			updateCommonStatus(cloneWorkOrder,approval.getStatus());
			saveWorkStatus(cloneWorkOrder);
			return null;
		}

		// 最终环节点走的流程
		if (approval != null && approval.getIsend() != 1) {
			return null;
		}
		// 克隆对象
		WorkOrder cloneWorkOrder = cloneObject(map, cloneMap);
		// --子类实现
		setWorkAttributes(action, cloneWorkOrder, cloneMap);
		saveWorkStatus(cloneWorkOrder);
		return null;
	}

	/**
	 * saveWorkStatus:(修改作业票的状态). <br/>
	 * date: 2015年4月7日 <br/>
	 * 
	 * @author zhaofeng
	 * @param workOrder
	 * @throws HDException
	 */
	/**
	 * 需要更新字段，子类可根据不同需求重写此方法，
	 * @return
	 */
	public String [] needUpdateValue(){
		return new String[] { "status",
				"spstatus", "isupload", "yyqcount","pausestatus"};
	}
	private void saveWorkStatus(WorkOrder workOrder) throws HDException {
		try {
			workOrder.setIsupload(0);
			dao.updateEntity(connection, workOrder, needUpdateValue() );
			WorkOrder p=workOrder;
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("更新作业申请中的状态字段的值失败！");
		}
	}

	/**
	 * setWorkAttributes:(子类修改作业票的状态). <br/>
	 * date: 2015年4月7日 <br/>
	 * 
	 * @author zhaofeng
	 * @param action
	 * @param workOrder
	 * @param map
	 */
	public abstract void setWorkAttributes(String action, WorkOrder workOrder,
			Map<String, Object> map);


	/**
	 * updateCommonStatus:(公共的修改作业票的状态). <br/>
	 * date: 2015年4月7日 <br/>
	 *
	 * @author zhaofeng
	 * @param cloneWorkOrder
	 * @param status
	 */
	private void updateCommonStatus(WorkOrder cloneWorkOrder,String status){
		cloneWorkOrder.setStatus(status);
		cloneWorkOrder.setSpstatus(status);
	}
	/**
	 * cloneObject:(克隆对象，用于缓存保存数据). <br/>
	 * date: 2015年4月7日 <br/>
	 *
	 * @author zhaofeng
	 * @param map
	 * @param cloneMap
	 * @return
	 * @throws HDException
	 */
	private WorkOrder cloneObject(Map<String, Object> map,
			Map<String, Object> cloneMap) throws HDException {

		WorkOrder workOrder = (WorkOrder) UtilTools.judgeMapValue(map,
				WorkOrder.class, true);
		WorkOrder cloneWorkOrder = null;
		try {
			if (cloneMap.containsKey(WorkOrder.class.getName())) {
				cloneWorkOrder = (WorkOrder) cloneMap.get(WorkOrder.class
						.getName());
			} else {
				cloneWorkOrder = (WorkOrder) workOrder.clone();
				cloneMap.put(WorkOrder.class.getName(), cloneWorkOrder);
			}
			return cloneWorkOrder;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("系统错误，请联系管理员！");
		}
	}
}