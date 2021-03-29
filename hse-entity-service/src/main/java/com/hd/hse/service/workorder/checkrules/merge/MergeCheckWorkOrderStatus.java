package com.hd.hse.service.workorder.checkrules.merge;

import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

import java.util.List;
import java.util.Map;

/**
 * 合并会签 状态为审批中(INPRG)状态的票才可以签批 查询pad端数据库库 ClassName: MergeCheckWorkOrderStatus
 * ()<br/>
 * date: 2015年9月6日 <br/>
 * 
 * @author lxf
 * @version
 */
public class MergeCheckWorkOrderStatus extends AbstractCheckListener {
	public Object action(String action, Object... args) throws HDException {
		String zysqid = "";// 票的id
		Map<String, Object> mapParas = objectCast(args[0]);
		WorkApprovalPermission approval = (WorkApprovalPermission) UtilTools
				.judgeMapValue(mapParas, WorkApprovalPermission.class, false);
		if (approval != null) {
			zysqid = approval.getStrzysqid();
			if (zysqid.length() == 0) {
				throw new AppException(UNKNOW_ERROR);
			}
			if (action.equalsIgnoreCase(IActionType.ACTION_TYPE_MERGE_SIGN)) {
				checkStatus(zysqid, IWorkOrderStatus.WAPPR,
						IWorkOrderStatus.CAN, IWorkOrderStatus.NULLIFY,
						IWorkOrderStatus.CLOSE, IWorkOrderStatus.CQCLOSE,
						IWorkOrderStatus.APPR);
			}
		}
		return super.action(action, args);
	}

	/**
	 * checkStatus:(检查作业票的状态). <br/>
	 * date: 2015年9月6日 <br/>
	 * 
	 * @author lxf
	 * @param zysqid
	 * @throws HDException
	 */
	private void checkStatus(String zysqid, String... zypstatus)
			throws HDException {
		// TODO Auto-generated method stub
		String sql = "select status from ud_zyxk_zysq where ud_zyxk_zysqid in ("
				+ zysqid + ")";
		List<Map<String, Object>> listmap = getListMapResult(sql);
		String status;
		for (Map<String, Object> map : listmap) {
			status = map.get("status").toString();
			for (String string : zypstatus) {
				if (string.equalsIgnoreCase(status)) {
					//throw new AppException("本状态下作业票，不能进行该操作！");2017/3/yn 防止有两个最终刷卡人的情况第二个最终刷卡人不能刷卡
				}
			}
		}
	}
}
