package com.hd.hse.service.workorder.saveinfo;

import java.util.Map;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

public class SetWorkOrderUpState extends AbstractCheckListener {
	@Override
	public Object action(String action, Object... object) throws HDException {
		Map<String, Object> map = objectCast(object[0]);
		WorkOrder workOrder = (WorkOrder) UtilTools.judgeMapValue(map,
				WorkOrder.class, true);
		PDAWorkOrderInfoConfig workConfig = (PDAWorkOrderInfoConfig) UtilTools.judgeMapValue(map,
				PDAWorkOrderInfoConfig.class, true);
		WorkApprovalPermission approval = (WorkApprovalPermission) UtilTools.judgeMapValue(map,
				WorkApprovalPermission.class, false);
		// 是否允许异步审批
		String sql = "SELECT * FROM sys_relation_info WHERE ifnull(sys_type,'')='ISASYNCAPPR' AND isqy='1' AND dr!=1";
		Object obj = dao.executeQuery(connection, sql, new EntityResult(RelationTableName.class));
		boolean isAsyncAppr = obj != null;
		if (isAsyncAppr) {
			if (workConfig.getContype() == IConfigEncoding.GAS_TYPE) {
				if (approval.getIsend() == 1) {
					workOrder.setNeedupload(1);
					workOrder.setIsupload(0);
					dao.updateEntity(connection, workOrder, new String[] {
							"needupload", "isupload" });
				}
			} else {
				workOrder.setNeedupload(1);
				workOrder.setIsupload(0);
				dao.updateEntity(connection, workOrder, new String[] {
						"needupload", "isupload" });
			}
		}
		return super.action(action, object);
	}
}
