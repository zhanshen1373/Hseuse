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

public class CheckHoistingInfo extends AbstractCheckListener {
	@Override
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		String zysqid = "";// 票的id
		Map<String, Object> mapParas = objectCast(args[0]);
		WorkApprovalPermission approvalObject = (WorkApprovalPermission) UtilTools
				.judgeMapValue(mapParas, WorkApprovalPermission.class, false);
		if (approvalObject == null || approvalObject.getIsend() == 1) {// 最终审核人检查
			if (mapParas.containsKey(WorkOrder.class.getName())) {
				Object obj = mapParas.get(WorkOrder.class.getName());
				if (obj instanceof SuperEntity) {
					WorkOrder workorder = (WorkOrder) obj;
					zysqid = workorder.getUd_zyxk_zysqid();
					if (zysqid.length() == 0) {
						throw new AppException(UNKNOW_ERROR);
					}
					String zyqClass = workorder.getZypclass();
					if (zyqClass != null && zyqClass.equals("zylx05")) {
						List<SuperEntity> configs = workorder
								.getChild(PDAWorkOrderInfoConfig.class
										.getName());
						boolean hasHoisting = false;
						for (SuperEntity superEntity : configs) {
							if ((int) superEntity.getAttribute("contype") == IConfigEncoding.DWDC_INFO_TYPE) {
								hasHoisting = true;
								break;
							}
						}
						if (hasHoisting) {
							checkValueAndPerson(zysqid);
						}
					}
				}
			}
		}
		return super.action(action, args);
	}
	/**
	 * 只是校验了数据库是否录入了吊装货物名称（可能会全部校验）
	 * @param zysqid
	 * @throws HDException
	 */
	private void checkValueAndPerson(String zysqid) throws HDException {
		String sql = "select dzwjname from ud_zyxk_dzzy where ud_zyxk_zysqid = '"
				+ zysqid + "';";
		
		Map<String, Object> map = getMapResult(sql);
		if (map.get("dzwjname") == null) {
			throw new HDException("吊物吊车信息未完成");
		}
	}
}