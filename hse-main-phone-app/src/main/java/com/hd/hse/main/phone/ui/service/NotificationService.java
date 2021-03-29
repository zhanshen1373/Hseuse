/**
 * Project Name:hse-main-phone-app
 * File Name:NotificationService.java
 * Package Name:com.hd.hse.main.phone.ui.service
 * Date:2015年9月17日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.main.phone.ui.service;

import org.apache.log4j.Logger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.AysncTaskMessage;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.common.weblistener.down.GainNewServiceUrl;
import com.hd.hse.main.phone.ui.receiver.UdpClient;
import com.hd.hse.system.SystemProperty;

/**
 * ClassName:NotificationService ().<br/>
 * Date: 2015年9月17日 <br/>
 * 
 * @author LiuYang
 * @version
 * @see
 */
public class NotificationService extends Service {

	private UdpClient udpClient;
	private String uuid;
	private String pushIP;
	private int pushPort;

	private Logger logger = LogUtils.getLogger(NotificationService.class);

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// 如果由系统启动杀掉自己（由系统启动无法获取到推送服务器的ip和port会崩溃）
		if (intent == null) {
			stopService(new Intent(this, this.getClass()));
			return 0;
		}
		setStart();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		stopUdpClient();
	}

	private void setStart() {
		try {
			GainNewServiceUrl serviceUrl = new GainNewServiceUrl();
			BusinessAction action = new BusinessAction(serviceUrl);
			BusinessAsyncTask task = new BusinessAsyncTask(action, callBack);
			task.execute("");
		} catch (HDException e) {
			logger.error(e);

		}
	}

	private AbstractAsyncCallBack callBack = new AbstractAsyncCallBack() {

		@Override
		public void start(Bundle msgData) {

		}

		@Override
		public void processing(Bundle msgData) {

		}

		@Override
		public void error(Bundle msgData) {
			logger.error("获取历史登陆人或推送服务注册问题");
		}

		@Override
		public void end(Bundle msgData) {
			AysncTaskMessage msg = (AysncTaskMessage) msgData
					.getSerializable("p");
			String pushStr = (String) msg.getReturnResult();
			if (pushStr != null && !"".equals(pushStr)) {
				if (pushStr.contains(":")) {
					String[] str;
					str = pushStr.split(":");
					pushIP = str[0];
					pushPort = Integer.parseInt(str[1]);
				} else {
					pushIP = pushStr;
					pushPort = 8080;
				}
			}
			if (SystemProperty.getSystemProperty().getLoginPerson() != null) {
				uuid = SystemProperty.getSystemProperty().getLoginPerson()
						.getUuid();
			}
			if (pushIP != null && !pushIP.isEmpty() && uuid != null && !uuid.isEmpty()) {
				setUdpClient();
			} else {
				ToastUtils.toast(NotificationService.this, "推送服务启动失败！");
			}
		}
	};

	private void setUdpClient() {
		try {
			if (udpClient != null) {
				udpClient.stop();
			}
			udpClient = new UdpClient(Util.md5Byte(uuid), 1, pushIP, pushPort);
			udpClient.setContext(this);
			udpClient.setHeartbeatInterval(50);
			udpClient.start();
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	public void stopUdpClient() {
		try {
			if (udpClient != null) {
				udpClient.stop();
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

}
