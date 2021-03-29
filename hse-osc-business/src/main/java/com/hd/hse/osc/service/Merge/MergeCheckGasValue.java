package com.hd.hse.osc.service.Merge;

import java.util.List;
import java.util.Map;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

/**
 * ClassName: MergeCheckGasValue (验证最近一次气体检测值是否合格,是否最终确认)<br/>
 * date: 2015年9月6日 <br/>
 * 
 * @author lxf
 * @version
 */
public class MergeCheckGasValue extends AbstractCheckListener {
	@Override
	public Object action(String action, Object... args) throws HDException {

		Map<String, Object> mapParas = objectCast(args[0]);
		WorkApprovalPermission approvalObject = (WorkApprovalPermission) UtilTools
				.judgeMapValue(mapParas, WorkApprovalPermission.class, false);
		// 此处要判断环节点是否是最终刷卡人，如果是校验气体检测时效判断
		String strid = approvalObject.getStrisend();
		if (strid != null && strid.length() > 0) {
			String[] strisend = strid.split(",");
			String[] strzysqid = approvalObject.getStrzysqid().split(",");
			if (strisend.length != strzysqid.length) {
				throw new AppException("读取数据库，组织数据异常,请联系管理员");
			}
			int i = 0;
			for (String isend : strisend) {
				if (isend != null
						&& (isend.equalsIgnoreCase("1") || isend
								.equalsIgnoreCase("'1'"))) {
					// 此时需要校验
					if (mapParas.containsKey(WorkOrder.class.getName())) {
						Object obj = mapParas.get(WorkOrder.class.getName());
						// 验证集合
						if (obj instanceof List) {
							@SuppressWarnings("unchecked")
							List<SuperEntity> listSuper = (List<SuperEntity>) obj;
							for (SuperEntity superEntity : listSuper) {
								String workId = superEntity.getAttribute(
										superEntity.getPrimaryKey()).toString();
								if (("'" + workId + "'")
										.equalsIgnoreCase(strzysqid[i])) {
									Integer isqtjc = ((WorkOrder) superEntity)
											.getIsqtjc();
									if (isqtjc != null && isqtjc == 1) {
										checkValueAndPerson(((WorkOrder) superEntity)
												.getUd_zyxk_zysqid());
									}
								}
							}
						}
					}
				}
				i++;
			}
		}

		return super.action(action, args);
	}

	/**
	 * checkValueAndPerson:(气体检测值是否合格，是否最终确认). <br/>
	 * date: 2015年9月6日 <br/>
	 * 
	 * @author lxf
	 * @param zysqid
	 * @throws HDException
	 */
	private void checkValueAndPerson(String zysqid) throws HDException {
		// TODO Auto-generated method stub
		String sql = "select ishg,writenbypda from ud_zyxk_qtjc where ud_zyxk_zysqid='"
				+ zysqid + "' order by jctime desc";
		Map<String, Object> map = getMapResult(sql);
		if (!map.isEmpty()) {
			String ishg = map.get("ishg") == null ? "" : map.get("ishg")
					.toString();
			String status = map.get("writenbypda") == null ? "" : map.get(
					"writenbypda").toString();
			if (ishg.length() == 0 || ishg.equalsIgnoreCase(QTJC_ISHG_VALUE_0)) {
				throw new AppException("气体检测结果不合格");
			}
			if (!IWorkOrderStatus.GAS_STATUS_FINISH.equalsIgnoreCase(status)) {
				throw new AppException("气体检测没有最终确认人");
			}
		} else {
			throw new AppException("气体检测没有完成");
		}
	}

}
