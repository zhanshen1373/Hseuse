/**
 * Project Name:hse-main-phone-app
 * File Name:Util.java
 * Package Name:com.hd.hse.main.phone.ui.service
 * Date:2015年9月21日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.main.phone.ui.service;

import java.security.MessageDigest;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * ClassName:Util ().<br/>
 * Date: 2015年9月21日 <br/>
 * 
 * @author LiuYang
 * @version
 * @see
 */
public class Util {
	public static char convertDigit(int value) {
		value &= 0x0f;
		if (value >= 10)
			return ((char) (value - 10 + 'a'));
		else
			return ((char) (value + '0'));
	}

	public static String convert(final byte bytes[]) {

		StringBuffer sb = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			sb.append(convertDigit((int) (bytes[i] >> 4)));
			sb.append(convertDigit((int) (bytes[i] & 0x0f)));
		}
		return (sb.toString());

	}

	public static String convert(final byte bytes[], int pos, int len) {

		StringBuffer sb = new StringBuffer(len * 2);
		for (int i = pos; i < pos + len; i++) {
			sb.append(convertDigit((int) (bytes[i] >> 4)));
			sb.append(convertDigit((int) (bytes[i] & 0x0f)));
		}
		return (sb.toString());

	}

	public static byte[] md5Byte(String encryptStr) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(encryptStr.getBytes("UTF-8"));
		return md.digest();
	}

	public static String md5(String encryptStr) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(encryptStr.getBytes("UTF-8"));
		byte[] digest = md.digest();
		StringBuffer md5 = new StringBuffer();
		for (int i = 0; i < digest.length; i++) {
			md5.append(Character.forDigit((digest[i] & 0xF0) >> 4, 16));
			md5.append(Character.forDigit((digest[i] & 0xF), 16));
		}

		encryptStr = md5.toString();
		return encryptStr;
	}

	public static boolean hasNetwork(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			return true;
		}
		return false;
	}

	/**
	 * isAppRunning:(判断程序是否运行). <br/>
	 * date: 2015年7月21日 <br/>
	 * 
	 * @author lxf
	 * @param context
	 * @return
	 */
	public static Boolean isAppRunning(Context context) {
		boolean isAppRunning = false;
		String pkgname;
		pkgname = context.getPackageName();
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(100);
		for (RunningTaskInfo info : list) {
			if (info.topActivity.getPackageName().equals(pkgname)
					&& info.baseActivity.getPackageName().equals(pkgname)) {
				isAppRunning = true;
				break;
			}
		}
		return isAppRunning;
	}
}
