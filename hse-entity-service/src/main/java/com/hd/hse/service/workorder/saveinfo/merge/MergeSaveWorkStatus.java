package com.hd.hse.service.workorder.saveinfo.merge;

import java.util.List;
import java.util.Map;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * ClassName: MergeSaveWorkStatus (合并会签，更改作业票状态)<br/>
 * date: 2015年9月8日 <br/>
 * 
 * @author lxf
 * @version
 */
public class MergeSaveWorkStatus extends AbstractCheckListener {

	public Object action(String action, Object... args) throws HDException {
		// TODO: implement
		// Map<String, Object> map = objectCast(object[0]);
		//
		// WorkApprovalPermission approval = (WorkApprovalPermission) UtilTools
		// .judgeMapValue(map, WorkApprovalPermission.class, false);
		//
		// Map<String, Object> cloneMap = objectCast(object[1]);// 克隆集合对象
		//
		// // 公共配置走的流程，根据配置环节点修改作业票的状态
		// // if (approval != null &&
		// !StringUtils.isEmpty(approval.getStatus())) {
		// // // 如果环节点的status字段不为空，则表示该环节点具有将作业票的状态修改成status的值的功能。
		// // // 克隆对象
		// // WorkOrder cloneWorkOrder = cloneObject(map, cloneMap);
		// // // --公共实现
		// // updateCommonStatus(cloneWorkOrder,approval.getStatus());
		// // saveWorkStatus(cloneWorkOrder);
		// // return null;
		// // }
		//
		// // 最终环节点走的流程
		// if (approval != null && approval.getIsend() != 1) {
		// return null;
		// }
		// // 克隆对象
		// WorkOrder cloneWorkOrder = cloneObject(map, cloneMap);
		// // --子类实现
		// setWorkAttributes(action, cloneWorkOrder, cloneMap);
		// saveWorkStatus(cloneWorkOrder);

		Map<String, Object> mapParas = objectCast(args[0]);
		WorkApprovalPermission approvalObject = (WorkApprovalPermission) UtilTools
				.judgeMapValue(mapParas, WorkApprovalPermission.class, false);
		// 此处要判断环节点是否是最终刷卡人，如果是校验气体检测时效判断
		String strid = approvalObject.getStrisend();
		if (strid != null && strid.length() > 0) {
			String[] strisend = strid.split(",");
			String[] strzysqid = approvalObject.getStrzysqid().split(",");
			if (strisend.length != strzysqid.length) {
				throw new AppException("读取数据库，组织数据异常,请联系管理员");
			}
			int i = 0;
			for (String isend : strisend) {
				if (isend != null
						&& (isend.equalsIgnoreCase("1") || isend
								.equalsIgnoreCase("'1'"))) {
					// 此时需要校验
					if (mapParas.containsKey(WorkOrder.class.getName())) {
						Object obj = mapParas.get(WorkOrder.class.getName());
						// 验证集合
						if (obj instanceof List) {
							@SuppressWarnings("unchecked")
							List<SuperEntity> listSuper = (List<SuperEntity>) obj;
							for (SuperEntity superEntity : listSuper) {
								String workId = superEntity.getAttribute(
										superEntity.getPrimaryKey()).toString();
								if (("'" + workId + "'")
										.equalsIgnoreCase(strzysqid[i])) {
									setWorkAttributes(action,
											(WorkOrder) superEntity, null);
									saveWorkStatus((WorkOrder) superEntity);
								}
							}
						}
					}
				}
				i++;
			}
		}

		return null;
	}

	/**
	 * saveWorkStatus:(修改作业票的状态). <br/>
	 * date: 2015年9月08日 <br/>
	 * 
	 * @author lxf
	 * @param workOrder
	 * @throws HDException
	 */
	private void saveWorkStatus(WorkOrder workOrder) throws HDException {
		try {
			workOrder.setIsupload(0);
			dao.updateEntity(connection, workOrder, new String[]{"status",
					"spstatus", "isupload", "yyqcount"});
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("更新作业申请中的状态字段的值失败！");
		}
	}

	/**
	 * setWorkAttributes:(子类修改作业票的状态). <br/>
	 * date: 2015年9月08日 <br/>
	 * 
	 * @author lxf
	 * @param action
	 * @param workOrder
	 * @param map
	 */
	public void setWorkAttributes(String action, WorkOrder workOrder,
			Map<String, Object> map) {
		workOrder.setStatus(IWorkOrderStatus.APPR);
		workOrder.setSpstatus(IWorkOrderStatus.SPSTATUS_SPAPPR);
	}

	/**
	 * updateCommonStatus:(公共的修改作业票的状态). <br/>
	 * date: 2015年9月08日 <br/>
	 * 
	 * @author lxf
	 * @param cloneWorkOrder
	 * @param status
	 */
	private void updateCommonStatus(WorkOrder cloneWorkOrder, String status) {
		cloneWorkOrder.setStatus(status);
		cloneWorkOrder.setSpstatus(status);
	}
	/**
	 * cloneObject:(克隆对象，用于缓存保存数据). <br/>
	 * date: 2015年9月08日 <br/>
	 * 
	 * @author lxf
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
