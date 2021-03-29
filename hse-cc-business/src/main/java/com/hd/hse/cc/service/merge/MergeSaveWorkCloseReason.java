package com.hd.hse.cc.service.merge;

import java.util.List;
import java.util.Map;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * 合并审批 更改作业申请中的关闭原因字段 ClassName: MergeSaveWorkCloseReason ()<br/>
 * date: 2015年9月14日 <br/>
 * 
 * @author lxf
 * @version
 */
public class MergeSaveWorkCloseReason extends AbstractCheckListener {
	public Object action(String action, Object... object) throws HDException {
		// TODO: implement
		Map<String, Object> mapParas = objectCast(object[0]);
		// Object workObject = UtilTools.judgeMapValue(mapParas,
		// WorkOrder.class, true);
		Object approvalObject = UtilTools.judgeMapValue(mapParas,
				WorkApprovalPermission.class, false);
		// 此时需要校验
		if (mapParas.containsKey(WorkOrder.class.getName())) {
			Object obj = mapParas.get(WorkOrder.class.getName());
			// 验证集合
			if (obj instanceof List) {
				@SuppressWarnings("unchecked")
				List<SuperEntity> listSuper = (List<SuperEntity>) obj;
				for (SuperEntity superEntity : listSuper) {
					saveWorkCloseReason((WorkOrder) superEntity,
							approvalObject == null ? null
									: (WorkApprovalPermission) approvalObject);
				}
			}
		}

		return null;
	}

	private void saveWorkCloseReason(WorkOrder workOrder,
			WorkApprovalPermission approvalPermission) throws HDException {
		try {
			if (approvalPermission == null
					|| approvalPermission.getIndex() == 0) {
				dao.updateEntity(connection, workOrder, new String[] {
						"gbtype", "gbsm" });
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new HDException("更新作业申请中的关闭原因字段的值失败！");
		}
	}
}
