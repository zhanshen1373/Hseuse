/**
 * Project Name:hse-main-app
 * File Name:ReceiverManager.java
 * Package Name:com.hd.hse.main.ui.receiver
 * Date:2014年11月4日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.main.phone.ui.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.constant.IActionType;
import com.hd.hse.entity.common.SysActionAgeConfig;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:ReceiverManager (广播监听管理).<br/>
 * Date: 2014年11月4日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class ReceiverManager {

	/**
	 * STARTTIME:TODO(启动时间).
	 */
	private final static long STARTTIME = 10 * 60 * 1000;

	/**
	 * registerReceivers:(注册). <br/>
	 * date: 2014年11月4日 <br/>
	 * 
	 * @author lg
	 * @param context
	 * @return
	 */
	public static List<BroadcastReceiver> registerReceivers(Context context) {
		if (context == null)
			return null;
		List<BroadcastReceiver> lstRcvr = new ArrayList<BroadcastReceiver>();
		// 后台任务注册
		CronReceiver cronRcver = new CronReceiver();
		lstRcvr.add(cronRcver);
		IntentFilter cronFlter = new IntentFilter(IActionType.BACK_SERVICE_AUTO_UPDATE_STATUS);
		context.registerReceiver(cronRcver, cronFlter);
		return lstRcvr;
	}

	/**
	 * unRegisterReceivers:(注销). <br/>
	 * date: 2014年11月4日 <br/>
	 * 
	 * @author lg
	 * @param context
	 * @param lstRcvr
	 */
	public static void unRegisterReceivers(Context context,
			List<BroadcastReceiver> lstRcvr) {
		if (context == null || lstRcvr == null || lstRcvr.isEmpty()) {
			return;
		}
		for (BroadcastReceiver rcver : lstRcvr) {
			context.unregisterReceiver(rcver);
		}
	}

	/**
	 * startTasks:(启动任务). <br/>
	 * date: 2014年11月5日 <br/>
	 * 
	 * @author lg
	 * @param context
	 * @return
	 */
	public static List<PendingIntent> startTasks(Context context) {
		if (context == null)
			return null;
		IQueryWorkInfo qry = new QueryWorkInfo();
		List<PendingIntent> lstPdIntent = new ArrayList<PendingIntent>();
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		try {
			SysActionAgeConfig config = qry
					.querySysActionAgeConfig(IActionType.BACK_SERVICE_AUTO_UPDATE_STATUS);
			if (config != null) {
				long intervalTime = 30 * 60 * 1000;// 默认30分钟
				if (config.getSx() != null && config.getSx() > 0) {
					intervalTime = (long) (config.getSx() * 60 * 1000);
				}
				// 后台任务
				Intent intent = new Intent(IActionType.BACK_SERVICE_AUTO_UPDATE_STATUS);
				// 系统中如果有这个pendingIntent 取消
				PendingIntent pending = PendingIntent.getBroadcast(context, 0,
						intent, PendingIntent.FLAG_NO_CREATE);
				if (pending != null) {
					am.cancel(pending);
				}
				pending = PendingIntent.getBroadcast(context, 0, intent, 0);
				am.setRepeating(AlarmManager.RTC_WAKEUP, ServerDateManager.getCurrentTimeMillis()+STARTTIME,
						intervalTime, pending);
				lstPdIntent.add(pending);
			}
		} catch (HDException e) {
			// TODO Auto-generated catch block
			LogUtils.getLogger(ReceiverManager.class).error(e.getMessage(), e);
			// do nothing
		} catch (Exception e) {
			LogUtils.getLogger(ReceiverManager.class).error(e.getMessage(), e);
		}
		return lstPdIntent;
	}

	/**
	 * endTasks:(结束任务). <br/>
	 * date: 2014年11月5日 <br/>
	 * 
	 * @author lg
	 * @param context
	 * @param lstPdIntent
	 */
	public static void endTasks(Context context, List<PendingIntent> lstPdIntent) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		if (context == null || lstPdIntent == null || lstPdIntent.isEmpty()) {
			return;
		}
		for (PendingIntent pi : lstPdIntent) {
			am.cancel(pi);
		}
	}
}
