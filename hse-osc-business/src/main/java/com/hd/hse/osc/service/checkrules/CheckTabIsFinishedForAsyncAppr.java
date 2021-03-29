/**
 * Project Name:hse-entity-service
 * File Name:CheckTabIsFinished.java
 * Package Name:com.hd.hse.service.workorder.checkrules
 * Date:2014年10月30日
 * Copyright (c) 2014, zhangjie1@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.osc.service.checkrules;

import java.util.List;
import java.util.Map;

import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * ClassName:CheckTabIsFinished (校验会签前所有的危害，PPE、措施等是否操作完成).<br/>
 * Date: 2014年10月30日 <br/>
 * 
 * @author ZhangJie
 * @version
 * @see
 */
public class CheckTabIsFinishedForAsyncAppr extends AbstractCheckListener {

	public CheckTabIsFinishedForAsyncAppr() {
		// TODO Auto-generated constructor stub

	}

	public CheckTabIsFinishedForAsyncAppr(IConnection connection) {
		super(connection);

	}

	public Object action(String action, Object... args) throws HDException {
		Map<String, Object> mapParas = objectCast(args[0]);
		// 会签-最终确认人
		Object objApproval = UtilTools.judgeMapValue(mapParas,
				WorkApprovalPermission.class, false);
		WorkApprovalPermission approval = (WorkApprovalPermission) objApproval;
		// 是否允许异步审批
		String sql = "SELECT * FROM sys_relation_info WHERE ifnull(sys_type,'')='ISASYNCAPPR' AND isqy='1' AND dr!=1";
		Object obj = dao.executeQuery(connection, sql, new EntityResult(
				RelationTableName.class));
		boolean isAsyncAppr = obj != null;
		// 没有审批环节点或最终审批人时，校验
		if (isAsyncAppr && (approval == null || approval.getIsend() == 1)) {
			@SuppressWarnings("unchecked")
			List<PDAWorkOrderInfoConfig> listPDAConfigInfo = (List<PDAWorkOrderInfoConfig>) UtilTools
					.judgeMapListValue(mapParas, PDAWorkOrderInfoConfig.class,
							false);
			// 判断导航栏菜单
			if (listPDAConfigInfo != null && listPDAConfigInfo.size() > 0) {
				for (PDAWorkOrderInfoConfig pdaWorkOrderInfoConfig : listPDAConfigInfo) {
					if (pdaWorkOrderInfoConfig.getContype().equals(
							IConfigEncoding.HARM_TYPE))// 如果包含危害
					{
						WorkOrder workorder = (WorkOrder) UtilTools
								.judgeMapValue(mapParas, WorkOrder.class, true);
						checkHazardIsFinished(workorder);
					}
					if (pdaWorkOrderInfoConfig.getContype().equals(
							IConfigEncoding.PPE_TYPE)) {
						WorkOrder workorder = (WorkOrder) UtilTools
								.judgeMapValue(mapParas, WorkOrder.class, true);
						checkPpeIsFinished(workorder);
					}
					if (pdaWorkOrderInfoConfig.getContype().equals(
							IConfigEncoding.MEASURE_TYPE)) {
						WorkOrder workorder = (WorkOrder) UtilTools
								.judgeMapValue(mapParas, WorkOrder.class, true);
						checkMeasureIsFinished(workorder);
					}
					if (pdaWorkOrderInfoConfig.getContype().equals(
							IConfigEncoding.ENERGY_TYPE)) {
						WorkOrder workorder = (WorkOrder) UtilTools
								.judgeMapValue(mapParas, WorkOrder.class, true);
						checkEnergyIsFinished(workorder);
					}
				}
			}
		}
		return super.action(action, args);
	}

	/**
	 * checkHazardIsFinished:(危害是否确认完成). <br/>
	 * date: 2014年10月30日 <br/>
	 * 
	 * @author ZhangJie
	 * @param workorder
	 * @throws AppException
	 */
	private void checkHazardIsFinished(WorkOrder workorder) throws AppException {
		String cssavefied = "";
		cssavefied = workorder.getCssavefied();
		if (cssavefied == null
				|| !cssavefied.contains(String
						.valueOf(IConfigEncoding.HARM_TYPE))) {
			throw new AppException("危害未确认");
		}
	}

	/**
	 * checkPpeIsFinished:(个人防护装备是否确认完成). <br/>
	 * date: 2014年10月30日 <br/>
	 * 
	 * @author ZhangJie
	 * @param workorder
	 * @throws AppException
	 */
	private void checkPpeIsFinished(WorkOrder workorder) throws AppException {
		String cssavefied = "";
		cssavefied = workorder.getCssavefied();
		if (cssavefied == null
				|| !cssavefied.contains(String
						.valueOf(IConfigEncoding.PPE_TYPE))) {
			throw new AppException("个人防护装备未确认");
		}
	}

	/**
	 * checkMeasureIsFinished:(措施是否确认完成). <br/>
	 * date: 2014年10月30日 <br/>
	 * 
	 * @author ZhangJie
	 * @param workorder
	 * @throws AppException
	 */
	private void checkMeasureIsFinished(WorkOrder workorder)
			throws AppException {
		String cssavefied = "";
		cssavefied = workorder.getCssavefied();
		if (cssavefied == null
				|| !cssavefied.contains(String
						.valueOf(IConfigEncoding.MEASURE_TYPE))) {
			throw new AppException("措施未确认完成");
		}
	}

	/**
	 * checkMeasureIsFinished:(能量隔离是否确认完成). <br/>
	 * date: 2014年10月30日 <br/>
	 * 
	 * @author LiuYang
	 * @param workorder
	 * @throws AppException
	 */
	private void checkEnergyIsFinished(WorkOrder workorder) throws AppException {
		String cssavefied = "";
		cssavefied = workorder.getCssavefied();
		if (cssavefied == null
				|| !cssavefied.contains(String
						.valueOf(IConfigEncoding.ENERGY_TYPE))) {
			throw new AppException("能量隔离未确认完成");
		}
	}
}
