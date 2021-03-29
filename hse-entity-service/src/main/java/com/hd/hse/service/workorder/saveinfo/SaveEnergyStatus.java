package com.hd.hse.service.workorder.saveinfo;

import java.util.Map;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.entity.base.EnergyIsolation;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkApprovalPersonRecord;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * 更改能量隔离单的状态
 * 
 * Created by liuyang on 2016年2月26日
 */
public class SaveEnergyStatus extends AbstractCheckListener {
	@Override
	public Object action(String action, Object... object) throws HDException {
		Map<String, Object> map = objectCast(object[0]);
		EnergyIsolation energy = (EnergyIsolation) UtilTools.judgeMapValue(map,
				EnergyIsolation.class, true);
		Object approvalObject = UtilTools.judgeMapValue(map,
				WorkApprovalPermission.class, false);
		if (((WorkApprovalPermission) approvalObject).getIsend() == 1) {
			// 如果是isend的话改变能量隔离单的状态为作业中
			energy.setStatus(IWorkOrderStatus.APPR);
			String[] sqls = new String[] { "", "" };
			String sql = "update ud_zyxk_nlgld set status = '"
					+ energy.getStatus()
					+ "' where UD_ZYXK_NLGLDID = "
					+ energy.getUd_zyxk_nlgldid() + ";";
			sqls[0] = sql;
			String sql2 = "update ud_zyxk_nlgldline set type = '设置' where UD_ZYXK_NLGLDID = "
					+ energy.getUd_zyxk_nlgldid() + ";";
			sqls[1] = sql2;
			try {
				dao.executeUpdate(connection, sqls);
			} catch (Exception e) {
				logger.error(e);
				throw new HDException("保存能量隔离单状态失败！");
			}
		}
		return null;
	}

}
