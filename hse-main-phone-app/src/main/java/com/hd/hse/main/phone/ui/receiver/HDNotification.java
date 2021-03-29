/**
 * Project Name:hse-main-phone-app
 * File Name:HDNotification.java
 * Package Name:com.hd.hse.main.phone.ui.receiver
 * Date:2015年9月17日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.main.phone.ui.receiver;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.hd.hse.entity.common.PushMessage;
import com.hd.hse.main.phone.R;
import com.hd.hse.main.phone.ui.activity.main.MainActivity;

/**
 * ClassName:HDNotification ().<br/>
 * Date: 2015年9月17日 <br/>
 * 
 * @author LiuYang
 * @version
 * @see
 */
public class HDNotification {
	private static NotificationManager manager;
	private Notification notification;

	@SuppressLint("NewApi")
	public HDNotification(Context context, PushMessage msg) {
		if (manager == null) {
			manager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
		}
		String msgtitle;
		if (msg.getXxlx().equals(UdpClient.MSGTYPE_NOTIFICATION)) {
			msgtitle = "通知类";
		} else {
			msgtitle = "操作类";
		}
		Intent intent = new Intent(context, MainActivity.class);

		PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		notification = new Notification.Builder(context)
				.setSmallIcon(R.drawable.hd_hse_phone_logo)
				.setTicker(msgtitle).setContentTitle(msgtitle)
				.setContentText(msg.getXxnr()).setContentIntent(pIntent)
				.setNumber(1).build();
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		manager.notify(msg.getUd_zyxk_xxjlid(), notification);
	}
	
	/**
	 * ClearNotifiaction:(退出应用程序时清除消息). <br/>
	 * date: 2015年7月23日 <br/>
	 * 
	 * @author lxf
	 */
	public static void clearAllNotification() {
		if (manager == null) {
			return;
		}
		manager.cancelAll();
	}
	
	public static void clearNotification(int ud_zyxk_xxjlid) {
		if (manager == null) {
			return;
		}
		manager.cancel(ud_zyxk_xxjlid);
	}

}
