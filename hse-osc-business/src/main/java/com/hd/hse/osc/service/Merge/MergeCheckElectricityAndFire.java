package com.hd.hse.osc.service.Merge;

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
 * ClassName: MergeCheckElectricityAndFire (电票签批时，火票必须已经签批（服务器端验证）)<br/>
 * date: 2015年9月6日 <br/>
 * 
 * @author lxf
 * @version
 */
public class MergeCheckElectricityAndFire extends AbstractCheckListener {
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
				String strtzysqidtemp=approvalObject.getStrzysqid();
				String[] strzysqid =strtzysqidtemp.split(",");// approvalObject.getStrzysqid().split(",");
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
							Object obj = mapParas
									.get(WorkOrder.class.getName());
							// 验证集合
							if (obj instanceof List) {
								@SuppressWarnings("unchecked")
								List<SuperEntity> listSuper = (List<SuperEntity>) obj;
								for (SuperEntity superEntity : listSuper) {
									String workId = superEntity.getAttribute(
											superEntity.getPrimaryKey())
											.toString();
								/*	if (("'" + workId + "'")
											.equalsIgnoreCase(strzysqid[i])) {*/
									if (strtzysqidtemp.contains("'" + workId + "'")) {
										relationCheckWorkSign(
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
	 * relationCheckWorkSign:(签批关联票验证). <br/>
	 * date: 2015年9月6日 <br/>
	 * 
	 * @author lxf
	 * @param workorder
	 * @throws HDException
	 */
	private void relationCheckWorkSign(WorkOrder workorder, String strid)
			throws HDException {
		String zypclass = workorder.getZypclass();// 作业票类型
		String dhzy_id = workorder.getDhzy_id();
		if (dhzy_id == null || dhzy_id.length() == 0) {
			return;
		}
		// 检测票类型,如果是电票，那么就判断火票的状态
		if (zypclass.equalsIgnoreCase(IWorkOrderZypClass.ZYPCLASS_LSYDZY)) {

			// 首先判断合并审批的票中是否包含动火票
			if (strid.contains("'" + dhzy_id + "'")) {
				// 表示包含大票
				return;
			}
			String sql = "select dhzy_id,status,sjendtime from ud_zyxk_zysq where ud_zyxk_zysqid ='"
					+ dhzy_id + "' order by sjendtime desc";
			List<Map<String, Object>> list = getListMapResult(sql);
			for (Map<String, Object> map : list) {
				String status = map.get("status").toString();
				if (!status.equalsIgnoreCase(IWorkOrderStatus.APPR)) {
					throw new HDException("该票关联的火票还没有签批");
				} else {
					return;
				}
			}
			// 到服务器校验关联的火票是否已经签批
			WebServiceAssist wsa = new WebServiceAssist();
			String action = PadInterfaceContainers.METHOD_ZYP_GETSTATUS;// 服务器端获得票的状态
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put(PadInterfaceRequest.KEYZYPSTRID, workorder.getDhzy_id());
			Object obj = wsa.action(action, hm);
			if (obj instanceof String) {
				if (!obj.toString().equalsIgnoreCase(IWorkOrderStatus.APPR)) {
					throw new HDException("该票关联的火票还没有签批");
				}
			}
		}
	}
}
