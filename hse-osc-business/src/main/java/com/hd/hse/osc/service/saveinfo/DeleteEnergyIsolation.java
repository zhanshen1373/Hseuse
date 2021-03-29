package com.hd.hse.osc.service.saveinfo;

import java.util.Map;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

public class DeleteEnergyIsolation extends AbstractCheckListener {
	@Override
	public Object action(String action, Object... object) throws HDException {
		Map<String, Object> map = objectCast(object[0]);
		WorkOrder workOrder = (WorkOrder) UtilTools.judgeMapValue(map,
				WorkOrder.class, true);
		if (workOrder != null) {
			String sql = "delete from ud_zyxk_nlgldline where ud_zyxk_nlgldid = ("
					+ "select ud_zyxk_nlgldid from ud_zyxk_nlgld where nlgldnum = ("
					+ "select nlgldnum from ud_zyxk_zysq where ud_zyxk_zysqid = '"
					+ workOrder.getUd_zyxk_zysqid() + "'))";
			dao.executeUpdate(connection, sql);
			sql = "delete from ud_zyxk_nlgld where nlgldnum = (select nlgldnum from ud_zyxk_zysq where ud_zyxk_zysqid = '"
					+ workOrder.getUd_zyxk_zysqid()
					+ "');";
			dao.executeUpdate(connection, sql);
		}
		return null;
	}
}
