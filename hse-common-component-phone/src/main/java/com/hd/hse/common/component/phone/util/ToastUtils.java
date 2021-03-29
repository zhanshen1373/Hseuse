/**
 * Project Name:hse-cbs-app
 * File Name:ToastUtils.java
 * Package Name:com.hd.hse.cbs.ui.common.util
 * Date:2014年9月10日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.component.phone.util;

import com.hd.hse.common.component.phone.R;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ClassName:ToastUtils (吐丝).<br/>
 * Date: 2014年9月10日 <br/>
 * 
 * @author lg
 * @version
 * @see
 */
public class ToastUtils {

	/**
	 * toast:(消息提示). <br/>
	 * date: 2014年9月10日 <br/>
	 * 
	 * @author lg
	 * @param context
	 * @param text
	 */
	public static void toast(Context context, CharSequence text) {
		imgToast(context, -1, text);
	}

	/**
	 * imgToast:(消息提示，带图片). <br/>
	 * date: 2014年9月10日 <br/>
	 * 
	 * @author lg
	 * @param context
	 * @param imageResourceId
	 * @param text
	 */
	public static void imgToast(Context context, int imageResourceId,
			CharSequence text) {
		// 创建一个Toast提示消息
		Toast toast = Toast.makeText(context, blockString(text,null), Toast.LENGTH_SHORT);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.hd_hse_common_toast_msg, null);
		toast.setView(view);
		// 设置Toast提示消息在屏幕上的位置
		toast.setGravity(Gravity.CENTER, 0, 0);
		// 设置图片
		if (imageResourceId != -1) {
			ImageView imgView = (ImageView) view
					.findViewById(R.id.hd_hse_common_toast_msg_img);
			imgView.setImageResource(imageResourceId);
		}
		// 设置消息
		TextView txtView = (TextView) view
				.findViewById(R.id.hd_hse_common_toast_msg_txt);
		txtView.setText(text);
		// 显示消息
		toast.show();
	}

	/**
	 * blockString:(截取字符串，减小长度). <br/>
	 * date: 2015年3月6日 <br/>
	 *
	 * @author zhaofeng
	 * @param text
	 * @param len
	 * @return
	 */
	private static CharSequence blockString(CharSequence text, Integer len) {
		if (len != null && text.length() > 24){
			len = 24;
			return text.subSequence(0, len)+"......";
		}else{
			return text;
		}
	}
}
