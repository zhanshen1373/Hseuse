package com.hd.hse.osc.service.saveinfo;

import java.util.List;
import java.util.Map;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.SequenceGenerator;
import com.hd.hse.entity.tempele.TempEleAsset;
import com.hd.hse.entity.tempele.TempEleZy;
import com.hd.hse.entity.workorder.HoistingWork;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;


public class SaveHoistingInfo  extends AbstractCheckListener {
	@SuppressWarnings("unchecked")
	@Override
	public Object action(String action, Object... args) throws HDException {
		Map<String, Object> map = (Map<String, Object>) args[0];
		HoistingWork hoistingWork = (HoistingWork) UtilTools.judgeMapValue(map,
				HoistingWork.class, true);
		WorkOrder order = (WorkOrder) UtilTools.judgeMapValue(map, WorkOrder.class, true);
		saveHoistingInfo(order,hoistingWork );
		return null;
	}

	private void saveHoistingInfo(WorkOrder order, HoistingWork hoistingWork)
			throws DaoException {
		try {
			if (hoistingWork != null) {
				dao.updateEntity(connection, hoistingWork, new String[] {
						"dzwjname", "wjsize", "wjzl", "dzzl","dzgzbj","dzgd","qzjedzh" });
			}
			
		} catch (DaoException e) {
			throw new DaoException("保存吊物吊车信息失败!");
		}
	}
}