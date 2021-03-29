package com.hd.hse.cc.service.merge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.constant.IWorkOrderZypClass;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.service.workorder.checkrules.WebServiceAssist;
import com.hd.hse.utils.UtilTools;

/**
 * 4，合并审批。大票关闭时，所有小票必须都为关闭状态(服务器端验证)5，大票取消时，同关闭操作 ClassName:
 * MergeCheckWorkToTask ()<br/>
 * date: 2015年9月14日 <br/>
 * 
 * @author lxf
 * @version
 */
public class MergeCheckWorkToTask extends AbstractCheckListener {

	/**
	 * @param action
	 * @param args
	 * @exception HDException
	 * @pdOid 58d0d3f4-7499-40df-8e42-8776da93bbca
	 */
	public Object action(String action, Object... args) throws HDException {
		// relationTableName =
		// getRelationTableName(IRelativeEncoding.BIGANDSPECIAL);

		// 判断大小票关系，目前没有判断具体的票，知识判断有没有关系

		boolean relationTableName = isRelationActionType(IRelativeEncoding.BIGANDSPECIAL);
		if (relationTableName) {
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
					if (isend != null && (isend.equalsIgnoreCase("1") || isend
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
									if (("'"+superEntity
											.getAttribute(
													superEntity.getPrimaryKey())
											.toString()+"'")
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
	 * relationCheckWork:(关联票验证). <br/>
	 * date: 2014年10月20日 <br/>
	 * 
	 * @author ZhangJie
	 * @param workorder
	 * @param checktype
	 * @throws HDException
	 */
	private void relationCheckWorkClose(WorkOrder workorder, String strid)
			throws HDException {
		String zypclass = workorder.getZypclass();// 作业票类型
		String zysqid = workorder.getUd_zyxk_zysqid();
		// 检测票类型,如果是大票，那么就判断小票的状态
		if (zypclass.equalsIgnoreCase(IWorkOrderZypClass.ZYPCLASS_ZYDP)) {
			// 检验pad数据库的小票状态
			String sql = "select ud_zyxk_zysqid,status from ud_zyxk_zysq where parent_id ='"
					+ zysqid + "' and ud_zyxk_zysqid not in(" + strid + ") ";
			sql += " and zypclass!='" + IWorkOrderZypClass.ZYPCLASS_ZYDP
					+ "'  ;";
			List<Map<String, Object>> list = getListMapResult(sql);
			StringBuilder sbZypid = new StringBuilder();
			for (Map<String, Object> map : list) {
				String ud_zyxk_zysqid = map.get("ud_zyxk_zysqid").toString();
				String status = map.get("status").toString();
				if (!status.equalsIgnoreCase(IWorkOrderStatus.INPRG)
						&& !status.equalsIgnoreCase(IWorkOrderStatus.APPR)) {
					if (sbZypid.length() > 0) {
						sbZypid.append(",");
					}
					sbZypid.append("'").append(ud_zyxk_zysqid).append("'");
				}
			}
			// 当前一起关闭的表不做校验
			if (strid != null && strid.length() > 0) {
				sbZypid.append(strid);
			}
			String excludezypid = "";
			if (sbZypid.length() > 0) {
//				excludezypid = sbZypid.substring(1, sbZypid.length() - 1);
				excludezypid = sbZypid.toString();
			}
			// 到服务器校验关联的小票状态
			WebServiceAssist wsa = new WebServiceAssist();
			String action = PadInterfaceContainers.METHOD_ZYP_GETTZPSTATUS;// 服务器端获得票的状态
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put(PadInterfaceRequest.KEYZYPSTRID,
					workorder.getUd_zyxk_zysqid());// 大票的id
			if (excludezypid.length() > 0) {
				hm.put(PadInterfaceRequest.KEYEXCLUDEZYPSTRID, excludezypid);// 不需要在pc端验证的作业票id
			}
			// 增加需要校验的作业票
			hm.put(PadInterfaceRequest.KEYCLUDEZYPCLASSSTR,
					IWorkOrderZypClass.ZYPCLASS_ZYDP);// 不需要在pc端验证的作业票id
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
