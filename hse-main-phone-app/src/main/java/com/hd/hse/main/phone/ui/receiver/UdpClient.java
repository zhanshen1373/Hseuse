/**
 * Project Name:hse-main-phone-app
 * File Name:UdpClient.java
 * Package Name:com.hd.hse.main.phone.ui.receiver
 * Date:2015年9月17日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.main.phone.ui.receiver;

import org.apache.log4j.Logger;
import org.ddpush.im.v1.client.appuser.Message;
import org.ddpush.im.v1.client.appuser.UDPClientBase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.entity.common.PushMessage;
import com.hd.hse.main.phone.ui.service.Util;

import android.content.Context;

/**
 * ClassName:UdpClient ().<br/>
 * Date: 2015年9月17日 <br/>
 * 
 * @author LiuYang
 * @version
 * @see
 */
public class UdpClient extends UDPClientBase {
	private Context context;
	private static Logger logger = LogUtils.getLogger(UdpClient.class);
	
	public static final String MSGTYPE_NOTIFICATION = "msgType01";
	public static final String MSGTYPE_WORK = "msgType02";
	
	/**
	 * GNBM_COLSE:TODO(功能编码：到期关闭).
	 */
	public static final String GNBM_COLSE = "gnbm_close";
	/**
	 * GNMB_QTJC:TODO(功能编码：气体检测).
	 */
	public static final String GNBM_QTJC = "gnbm_qtjc";

	public UdpClient(byte[] uuid, int appid, String serverAddr, int serverPort)
			throws Exception {
		super(uuid, appid, serverAddr, serverPort);

	}

	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public boolean hasNetworkConnection() {
		return true;
	}

	@Override
	public void onPushMessage(Message msg) {
		if (msg.getCmd() == 0x20 && context != null) {
			String str = null;
			try {
				try {
					str = new String(msg.getData(), 5, msg.getContentLength(),
							"UTF-8");
				} catch (Exception e) {
					logger.error(msg);
					ToastUtils.toast(context, "赋值消息内容报错");
					str = Util
							.convert(msg.getData(), 5, msg.getContentLength());
				}
				PushMessage pushMessage = messageChange(str);
				if (pushMessage != null) {
					new HDNotification(context, pushMessage);
				}
			} catch (Exception e) {
				ToastUtils.toast(context, "转化消息报错");
				logger.error(msg);
			}
		}
	}

	@Override
	public void trySystemSleep() {

	}

	/**
	 * 
	 * messageChange:(消息解析). <br/>
	 * date: 2015年10月16日 <br/>
	 *
	 * @author LiuYang
	 * @param str
	 * @return
	 */
	private PushMessage messageChange(String str) {
		PushMessage msg = null;
		try {
			JSONObject json = new JSONObject(str);
			JSONArray array= json.getJSONArray("UD_PAD_ALERT");
			JSONObject jsonItem = array.getJSONObject(0);
			msg = PushMessage.parse(jsonItem);
		} catch (JSONException e) {
			logger.error(e);
		}
		return msg;
	}

}
