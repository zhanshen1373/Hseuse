package com.hd.hse.osc.service.saveinfo;

import java.util.List;
import java.util.Map;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.SequenceGenerator;
import com.hd.hse.entity.tempele.TempEleAsset;
import com.hd.hse.entity.tempele.TempEleZy;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

public class SaveTempEleInfo extends AbstractCheckListener {
	@SuppressWarnings("unchecked")
	@Override
	public Object action(String action, Object... args) throws HDException {
		Map<String, Object> map = (Map<String, Object>) args[0];
		TempEleZy tempEleZy = (TempEleZy) UtilTools.judgeMapValue(map,
				TempEleZy.class, true);
		List<TempEleAsset> assets = (List<TempEleAsset>) UtilTools
				.judgeMapListValue(map, TempEleAsset.class, false);
		WorkOrder order = (WorkOrder) UtilTools.judgeMapValue(map, WorkOrder.class, true);
		saveEleTinfo(order, tempEleZy, assets);
		return null;
	}

	private void saveEleTinfo(WorkOrder order, TempEleZy tempEleZy, List<TempEleAsset> assets)
			throws DaoException {
		try {
			if (tempEleZy != null) {
				dao.updateEntity(connection, tempEleZy, new String[] {
						"lsydyt", "gzdy", "dyjrd", "sbfh" });
			}
			if (assets != null && assets.size() != 0) {
				SequenceGenerator.genPrimaryKeyValue(assets.toArray(new SuperEntity[assets.size()]));
				String sql = "delete from ud_zyxk_ydasset where ud_zyxk_zysqid = '" + order.getUd_zyxk_zysqid() + "';";
				dao.executeUpdate(connection, sql);
				dao.insertEntities(connection,
						assets.toArray(new SuperEntity[assets.size()]));
			}
		} catch (DaoException e) {
			throw new DaoException("保存临时用电信息失败!");
		}
	}
}
