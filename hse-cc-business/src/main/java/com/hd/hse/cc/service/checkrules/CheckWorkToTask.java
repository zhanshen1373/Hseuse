/***********************************************************************
 * Module:  WorkToTask.java
 * Author:  Administrator
 * Purpose: Defines the Class WorkToTask
 ***********************************************************************/

package com.hd.hse.cc.service.checkrules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IActionType;
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
 * 4，大票关闭时，所有小票必须都为关闭状态(服务器端验证)5，大票取消时，同关闭操作
 * 
 * @pdOid 5e3bb628-2fd7-470b-9de2-f6a42ebdd318
 */
public class CheckWorkToTask extends AbstractCheckListener {
	
	RelationTableName relationTableName;
	/**
	 * @param action
	 * @param args
	 * @exception HDException
	 * @pdOid 58d0d3f4-7499-40df-8e42-8776da93bbca
	 */
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
//		boolean relationAction = isRelationAction(IRelativeEncoding.BIGANDSPECIAL);
		relationTableName = getRelationTableName(IRelativeEncoding.BIGANDSPECIAL);
		if (relationTableName!=null) {
			Map<String, Object> mapParas = objectCast(args[0]);
			Object obj = UtilTools.judgeMapValue(mapParas, WorkOrder.class,
					false);
			WorkOrder workorder = (WorkOrder) obj;
			Object objApproval = UtilTools.judgeMapValue(mapParas,
					WorkApprovalPermission.class, false);
			WorkApprovalPermission approval = (WorkApprovalPermission) objApproval;
			// 没有审批环节点或最终审批人时，校验
			if (approval == null || approval.getIsend() == 1) {
				if (action.equalsIgnoreCase(IActionType.ACTION_TYPE_CLOSE))// 关闭
				{
					relationCheckWorkClose(workorder);
				} else if (action
						.equalsIgnoreCase(IActionType.ACTION_TYPE_CANCEL))// 取消
				{
					relationCheckWorkCancel(workorder);
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
	private void relationCheckWorkClose(WorkOrder workorder) throws HDException {
		String zypclass = workorder.getZypclass();// 作业票类型
		String zysqid = workorder.getUd_zyxk_zysqid();
		// 检测票类型,如果是大票，那么就判断小票的状态
		if (zypclass.equalsIgnoreCase(IWorkOrderZypClass.ZYPCLASS_ZYDP)) {
			// 检验pad数据库的小票状态
			String sql = "select ud_zyxk_zysqid,status from ud_zyxk_zysq where parent_id ='"
					+ zysqid + "'";
			if(!StringUtils.isEmpty(relationTableName.getSys_value())){
				String sqlZypType = UtilTools.convertToSqlString(relationTableName.getSys_value());
				sql += "  and zypclass in "+sqlZypType+" and zypclass!='"+IWorkOrderZypClass.ZYPCLASS_ZYDP+"'  ;";
			}
			List<Map<String, Object>> list = getListMapResult(sql);
			StringBuilder sbZypid = new StringBuilder();
			for (Map<String, Object> map : list) {
				String ud_zyxk_zysqid = map.get("ud_zyxk_zysqid").toString();
				String status = map.get("status").toString();
				if (!status.equalsIgnoreCase(IWorkOrderStatus.INPRG)
						&& !status.equalsIgnoreCase(IWorkOrderStatus.APPR)) {
					// throw new HDException("本机存在审批中或作业中的小票");
					sbZypid.append(",'").append(ud_zyxk_zysqid).append("'");
				}
				/*
				 * if (status.equalsIgnoreCase(IWorkOrderStatus.INPRG) ||
				 * status.equalsIgnoreCase(IWorkOrderStatus.APPR)) { throw new
				 * HDException("本机存在审批中或作业中的小票"); }
				 */
			}
			String excludezypid = "";
			if (sbZypid.length() > 0) {
				excludezypid = sbZypid.substring(1);
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
			//增加需要校验的作业票
			hm.put(PadInterfaceRequest.KEYCLUDEZYPCLASSSTR, relationTableName.getSys_value()==null?"":relationTableName.getSys_value());// 不需要在pc端验证的作业票id
			Object obj = wsa.action(action, hm);
			if (obj instanceof String) {
				if (!obj.toString().equalsIgnoreCase(
						PadInterfaceContainers.METHOD_SUCCESS)) {
					throw new HDException(obj.toString() + "未关闭");
				}
			}
		}
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
	private void relationCheckWorkCancel(WorkOrder workorder)
			throws HDException {
		String zypclass = workorder.getZypclass();// 作业票类型
		String zysqid = workorder.getUd_zyxk_zysqid();
		// 检测票类型,如果是大票，那么就判断小票的状态
		if (zypclass.equalsIgnoreCase(IWorkOrderZypClass.ZYPCLASS_ZYDP)) {
			// 检验pad数据库的小票状态
			String sql = "select ud_zyxk_zysqid,status from ud_zyxk_zysq where parent_id ='"
					+ zysqid + "'";
			if(!StringUtils.isEmpty(relationTableName.getSys_value())){
				String sqlZypType = UtilTools.convertToSqlString(relationTableName.getSys_value());
				sql += "  and zypclass in "+sqlZypType+" and zypclass!='"+IWorkOrderZypClass.ZYPCLASS_ZYDP+"'  ;";
			}
			List<Map<String, Object>> list = getListMapResult(sql);
			StringBuilder sbZypid = new StringBuilder();
			for (Map<String, Object> map : list) {
				String ud_zyxk_zysqid = map.get("ud_zyxk_zysqid").toString();
				String status = map.get("status").toString();
				if (!status.equalsIgnoreCase(IWorkOrderStatus.INPRG)
						&& !status.equalsIgnoreCase(IWorkOrderStatus.APPR)) {
					// throw new HDException("本机存在审批中或作业中的小票");
					sbZypid.append(",'").append(ud_zyxk_zysqid).append("'");
				}
			}
			String excludezypid = "";
			if (sbZypid.length() > 0) {
				excludezypid = sbZypid.substring(1);
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
			//增加需要校验的作业票
			hm.put(PadInterfaceRequest.KEYCLUDEZYPCLASSSTR, relationTableName.getSys_value()==null?"":relationTableName.getSys_value());// 不需要在pc端验证的作业票id
			Object obj = wsa.action(action, hm);
			if (obj instanceof String) {
				if (!obj.toString().equalsIgnoreCase(
						PadInterfaceContainers.METHOD_SUCCESS)) {
					throw new HDException(obj.toString() + "未取消");
				}
			}
		}
	}

}