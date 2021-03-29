/***********************************************************************
 * Module:  CheckElectricityAndFire.java
 * Author:  Administrator
 * Purpose: Defines the Class CheckElectricityAndFire
 ***********************************************************************/

package com.hd.hse.dly.service.checkrules;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.constant.IWorkOrderZypClass;
import com.hd.hse.entity.workorder.WorkDelay;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.service.workorder.checkrules.WebServiceAssist;
import com.hd.hse.utils.UtilTools;

/**
 * 6，电票延期时，延期时间必须<最后一张火票结束时间(实际)
 * 
 * @pdOid e07e45e6-b2f3-40c4-8349-1de5ad500009
 */
public class CheckElectricityAndFire extends AbstractCheckListener {
	/**
	 * @param action
	 * @param args
	 * @exception HDException
	 * @pdOid 7ba7e6b3-02db-46ca-aea8-ae40869ed402
	 */
	public Object action(String action, Object... args) throws HDException {
		// TODO Auto-generated method stub
		// 电火票是否强制关联
		boolean relationAction = isRelationAction(IRelativeEncoding.DHYD);
		if (relationAction) {
			Map<String, Object> mapParas = objectCast(args[0]);
			Object obj = UtilTools.judgeMapValue(mapParas, WorkOrder.class,
					false);
			WorkOrder workorder = (WorkOrder) obj;

			Object yqObj = UtilTools.judgeMapValue(mapParas, WorkDelay.class,
					false);
			WorkDelay workDelay = (WorkDelay) yqObj;
			relationCheckWorkDelay(workorder, workDelay);
		}
		return super.action(action, args);
	}

	/**
	 * relationCheckWork:(延期关联票验证). <br/>
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
		String dhzy_id = workorder.getDhzy_id();
		Calendar dhzy_yqtime = ParseToCalendar(workDelay.getYqendtime());// 延期时间
		// 电票延期时，延期时间必须<最后一张火票结束时间(实际)
		if (zypclass.equalsIgnoreCase(IWorkOrderZypClass.ZYPCLASS_LSYDZY)) {
			// 检验pad数据库的火票时间
			String sql = "select ud_zyxk_zysqid,status,sjendtime from ud_zyxk_zysq where ud_zyxk_zysqid ='"
					+ dhzy_id + "' order by sjendtime desc";
			List<Map<String, Object>> list = getListMapResult(sql);
			for (Map<String, Object> map : list) {
				Calendar sjendtime = ParseToCalendar(map.get("sjendtime")
						.toString());// 实际结束时间
				if (sjendtime.compareTo(dhzy_yqtime) < 0) {
					SimpleDateFormat df = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss", Locale.CHINA);
					throw new HDException("延期超出火票结束时间"
							+ df.format(sjendtime.getTime()));
				}
			}
			/** 如果pad端已经校验无需服务器端校验 */
			if (list.size() > 0) {
				return;
			}
			if (dhzy_id == null || dhzy_id.length() == 0) {
				return;
			}
			// 到服务器校验关联的电票状态
			WebServiceAssist wsa = new WebServiceAssist();
			String action = PadInterfaceContainers.METHOD_ZYP_GETSJENDTIME;// 服务器端获得票的状态
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put(PadInterfaceRequest.KEYZYPSTRID, dhzy_id);// 火票的id
			Object obj = wsa.action(action, hm);
			if (obj instanceof String) {
				if (obj.toString().equalsIgnoreCase("")) {
					throw new HDException("服务器端未找到关联的火票");
				} else {
					if (ParseToCalendar(obj.toString()).compareTo(dhzy_yqtime) < 0) {
						throw new HDException("延期超出火票结束时间" + obj.toString());
					}
				}
			}
		}
	}
}