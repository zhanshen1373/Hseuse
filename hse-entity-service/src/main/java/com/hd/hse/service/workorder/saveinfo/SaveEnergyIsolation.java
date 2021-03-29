package com.hd.hse.service.workorder.saveinfo;

import java.util.Map;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.base.EnergyIsolation;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkApprovalPersonRecord;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

public class SaveEnergyIsolation extends AbstractCheckListener {
	@Override
	public Object action(String action, Object... object) throws HDException {
		Map<String, Object> map = objectCast(object[0]);
		Object energyObject = UtilTools.judgeMapValue(map,
				EnergyIsolation.class, true);
		Object approvalObject = UtilTools.judgeMapValue(map,
				WorkApprovalPermission.class, false);
		Object workObj = UtilTools.judgeMapValue(map, WorkOrder.class, true);
		WorkApprovalPersonRecord record = (WorkApprovalPersonRecord) UtilTools
				.judgeMapValue(map, WorkApprovalPersonRecord.class, true);
		saveEnergyIsolation((EnergyIsolation) energyObject);
		return null;
	}

	private void saveEnergyIsolation(EnergyIsolation energyIsolation)
			throws HDException {
		try {
			if (energyIsolation.getHazard() == null || energyIsolation.getHazard().equals("")) {
				throw new HDException("危害识别不能为空！");
			}
			if (energyIsolation.getGlasset() != null
					&& !energyIsolation.getGlasset().equals("")) {
				String sql = "update ud_zyxk_nlgld set glasset = '"
						+ energyIsolation.getGlasset()
						+ "' where UD_ZYXK_NLGLDID = "
						+ energyIsolation.getUd_zyxk_nlgldid();
				dao.executeUpdate(connection, sql);
			} else {
				throw new HDException("隔离系统/设备不能为空！");
			}
		} catch (DaoException e) {
			logger.error(e);
			throw new HDException("保存失败");
		}
	}

}
