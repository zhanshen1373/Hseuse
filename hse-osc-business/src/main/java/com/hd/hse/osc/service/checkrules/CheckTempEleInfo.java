package com.hd.hse.osc.service.checkrules;

import java.util.List;
import java.util.Map;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

public class CheckTempEleInfo extends AbstractCheckListener {
	@Override
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		String zysqid = "";// 票的id
		Map<String, Object> mapParas = objectCast(args[0]);
		WorkApprovalPermission approvalObject = (WorkApprovalPermission) UtilTools
				.judgeMapValue(mapParas, WorkApprovalPermission.class, false);
		if (approvalObject == null || approvalObject.getIsend() == 1) {//最终审核人检查
			if (mapParas.containsKey(WorkOrder.class.getName())) {
				Object obj = mapParas.get(WorkOrder.class.getName());
				if (obj instanceof SuperEntity) {
					WorkOrder workorder = (WorkOrder) obj;
					zysqid = workorder.getUd_zyxk_zysqid();
					if (zysqid.length() == 0) {
						throw new AppException(UNKNOW_ERROR);
					}
					String zyqClass = workorder.getZypclass();
					if(zyqClass!=null&&zyqClass.equals("zylx07"))
					{
						List<SuperEntity> configs = workorder.getChild(PDAWorkOrderInfoConfig.class
								.getName());
						boolean hasELE = false;
						for (SuperEntity superEntity : configs) {
							if ((int) superEntity.getAttribute("contype") == IConfigEncoding.TEMPELE_TYPE) {
								hasELE = true;
								break;
							}
						}
						if (hasELE) {
							checkValueAndPerson(zysqid);
						}
					}
				}
			}
		}
		return super.action(action, args);
	}
	
	private void checkValueAndPerson(String zysqid) throws HDException {
		String sql = "select gzdy from ud_zyxk_lsydzy where ud_zyxk_zysqid = '" + zysqid + "';";
//		TempEleZy tempEleZy = (TempEleZy) dao.executeQuery(sql, new EntityResult(TempEleZy.class));
		Map<String, Object> map = getMapResult(sql);
		if (map.get("gzdy") == null) {
			throw new HDException("临时用电作业未完成");
		}
//		if (tempEleZy == null || tempEleZy.getGzdy() == null || tempEleZy.getGzdy().equals("")) {
//			throw new HDException("临时用电作业未完成");
//		}
		sql = "select ud_zyxk_zysqid from ud_zyxk_ydasset where ud_zyxk_zysqid = '" + zysqid +"';";
//		List<TempEleAsset> assets = (List<TempEleAsset>) dao.executeQuery(sql, new EntityListResult(TempEleAsset.class));
//		if (assets == null || assets.size() == 0) {
//			throw new HDException("临时用电设备清单为空");
//		}
		map = getMapResult(sql);
		if (map.get("ud_zyxk_zysqid") == null) {
			throw new HDException("临时用电设备清单为空");
		}
	}
}
