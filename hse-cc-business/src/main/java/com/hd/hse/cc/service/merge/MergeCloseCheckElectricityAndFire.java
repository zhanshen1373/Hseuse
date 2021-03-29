package com.hd.hse.cc.service.merge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.constant.IWorkOrderZypClass;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.service.workorder.checkrules.WebServiceAssist;
import com.hd.hse.utils.UtilTools;

/**
 * 合并审批 火票关闭时，所有电票必须都为关闭状态(pad端校验通过后，服务器端校验除pad校验外的票，)（电票存火票id） ClassName:
 * MergeCloseCheckElectricityAndFire ()<br/>
 * date: 2015年9月14日 <br/>
 * 
 * @author lxf
 * @version
 */
public class MergeCloseCheckElectricityAndFire extends AbstractCheckListener {

	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		// 电火票是否强制关联
		boolean relationAction = isRelationAction(IRelativeEncoding.DHYD);
		if (relationAction) {
			Map<String, Object> mapParas = objectCast(args[0]);
			WorkApprovalPermission approvalObject = (WorkApprovalPermission) UtilTools
					.judgeMapValue(mapParas, WorkApprovalPermission.class,
							false);
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
					if (isend != null && isend.equalsIgnoreCase("1")) {
						// 此时需要校验
						if (mapParas.containsKey(WorkOrder.class.getName())) {
							Object obj = mapParas
									.get(WorkOrder.class.getName());
							// 验证集合
							if (obj instanceof List) {
								@SuppressWarnings("unchecked")
								List<SuperEntity> listSuper = (List<SuperEntity>) obj;
								for (SuperEntity superEntity : listSuper) {
									if (superEntity
											.getAttribute(
													superEntity.getPrimaryKey())
											.toString()
											.equalsIgnoreCase(strzysqid[i])) {
										relationCheckWorkClose(
												(WorkOrder) superEntity,
												approvalObject.getStrzysqid());
									}
								}
							}
						}
					}
					i++;
				}
			}
		}
		return super.action(action, args);
	}

	/**
	 * relationCheckWork:(关闭关联票验证). <br/>
	 * date: 2015年9月14日 <br/>
	 * 
	 * @author lxf
	 * @param workorder
	 * @param checktype
	 * @throws HDException
	 */
	private void relationCheckWorkClose(WorkOrder workorder, String str)
			throws HDException {
		String zypclass = workorder.getZypclass();// 作业票类型
		String zysqid = workorder.getUd_zyxk_zysqid();
		boolean ishave = false;
		// 检测票类型,如果是火票，那么就判断电票的状态
		if (zypclass.equalsIgnoreCase(IWorkOrderZypClass.ZYPCLASS_DHZY)) {
			// 检验pad数据库的电票状态 dhzy_id
			String sql = "select ud_zyxk_zysqid,status from ud_zyxk_zysq where dhzy_id ='"
					+ zysqid + "'";
			List<Map<String, Object>> list = getListMapResult(sql);
			StringBuilder sbZypid = new StringBuilder();
			for (Map<String, Object> map : list) {
				String ud_zyxk_zysqid = map.get("ud_zyxk_zysqid").toString();
				// 如果本票中包含临时跳过
				if (str.contains(ud_zyxk_zysqid)) {
					// 表示包含临时用电
					ishave = true;
					continue;
				}
				String status = map.get("status").toString();
				if (!status.equalsIgnoreCase(IWorkOrderStatus.INPRG)
						&& !status.equalsIgnoreCase(IWorkOrderStatus.APPR)) {
					// 表示不校验ID，实际上筛选出已关闭的作业票
					sbZypid.append(",'").append(ud_zyxk_zysqid).append("'");
				}
			}
			if (ishave) {
				return;
			} else {
				String excludezypid = "";
				if (sbZypid.length() > 0) {
					excludezypid = sbZypid.substring(1);
				}
				// 到服务器校验关联的电票状态
				WebServiceAssist wsa = new WebServiceAssist();
				String action = PadInterfaceContainers.METHOD_ZYP_GETLSYDSTATUS;// 服务器端获得票的状态
				HashMap<String, Object> hm = new HashMap<String, Object>();
				hm.put(PadInterfaceRequest.KEYZYPSTRID,
						workorder.getUd_zyxk_zysqid());// 火票的id
				if (excludezypid.length() > 0) {
					hm.put(PadInterfaceRequest.KEYEXCLUDEZYPSTRID, excludezypid);// 不需要在pc端验证的作业票id
				}
				Object obj = wsa.action(action, hm);
				if (obj instanceof String) {
					if (!obj.toString().equalsIgnoreCase(
							PadInterfaceContainers.METHOD_SUCCESS)) {
						throw new HDException(obj.toString() + "未关闭");
					}
				}
			}
		}
	}
}
