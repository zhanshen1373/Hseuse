/***********************************************************************
 * Module:  WorkToTask.java
 * Author:  Administrator
 * Purpose: Defines the Class WorkToTask
 ***********************************************************************/

package com.hd.hse.dly.service.checkrules;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.constant.IWorkOrderZypClass;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.workorder.WorkDelay;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.service.workorder.checkrules.WebServiceAssist;
import com.hd.hse.utils.UtilTools;

/**
 * 6，小票延期时，延期时间必须<大票的结束时间(实际)
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
			// 没有审批环节点或最终审批人时，校验
			// --赵锋 所说有环节点都校验
			if (action.equalsIgnoreCase(IActionType.ACTION_TYPE_DELAYSIGN))// 延期
			{
				// 延期表
				Object yqObj = UtilTools.judgeMapValue(mapParas,
						WorkDelay.class, false);
				WorkDelay workDelay = (WorkDelay) yqObj;
				relationCheckWorkDelay(workorder, workDelay);
			}
		} else {
			return super.action(action, args);
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
	private void relationCheckWorkDelay(WorkOrder workorder, WorkDelay workDelay)
			throws HDException {
		String zypclass = workorder.getZypclass();// 作业票类型
		String parent_id = workorder.getParent_id();
		Calendar yqtime = ParseToCalendar(workDelay.getYqendtime());// 延期时间
		// 检测票类型,如果是小票，那么就判断大票实际结束时间
		if (!zypclass.equalsIgnoreCase(IWorkOrderZypClass.ZYPCLASS_ZYDP)) {
			// 检验pad数据库的大票时间
			String sql = "select status,sjendtime from ud_zyxk_zysq where ud_zyxk_zysqid ='"
					+ parent_id + "' ";
//			if(!StringUtils.isEmpty(relationTableName.getSys_value())){
//				String sqlZypType = UtilTools.convertToSqlString(relationTableName.getSys_value());
//				sql += "  and zypclass in "+sqlZypType+" and zypclass!='"+IWorkOrderZypClass.ZYPCLASS_ZYDP+"'  ";
//			}
			sql += " order by sjendtime desc;";
			List<Map<String, Object>> list = getListMapResult(sql);
			for (Map<String, Object> map : list) {
				Calendar sjendtime = ParseToCalendar(map.get("sjendtime")
						.toString());// 实际结束时间
				if (sjendtime.compareTo(yqtime) < 0) {
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss", Locale.CHINA);
					throw new HDException("延期超出大票结束时间"
							+ df.format(sjendtime.getTime()));
				}
			}
			/** 如果pad端已经校验无需服务器端校验 */
			if (list.size() > 0) {
				return;
			}
			// 到服务器校验关联的大票
			WebServiceAssist wsa = new WebServiceAssist();
			String action = PadInterfaceContainers.METHOD_ZYP_GETSJENDTIME;// 服务器端获得票的状态
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put(PadInterfaceRequest.KEYZYPSTRID, parent_id);
			Object obj = wsa.action(action, hm);
			if (obj instanceof String) {
				if (obj.toString().equalsIgnoreCase("")) {
					throw new HDException("服务器端未找到关联的大票");
				} else {
					if (ParseToCalendar(obj.toString()).compareTo(yqtime) < 0) {
						throw new HDException("延期超出大票结束时间" + obj.toString());
					}
				}
			}
		}
	}

}