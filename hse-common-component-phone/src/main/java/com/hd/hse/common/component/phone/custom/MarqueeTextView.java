/**
 * Project Name:hse-common-component-phone
 * File Name:MarqueeTextView.java
 * Package Name:com.hd.hse.common.component.phone.custom
 * Date:2015年11月26日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.component.phone.custom;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 带有跑马灯效果的TextView
 * TextView只有在获取焦点的时候才能进行跑马灯滚动效果
 * 欺骗Android让系统认为当前这个TextView是处在获取焦点的状态，这样就可以实现textview的滚动显示
 * ClassName:MarqueeTextView ().<br/>
 * Date: 2015年11月26日 <br/>
 * 
 * @author LiuYang
 * @version
 * @see
 */
public class MarqueeTextView extends TextView {
	public MarqueeTextView(Context context) {
		this(context, null);
	}

	public MarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		setFocusable(true);
		setFocusableInTouchMode(true);

		setSingleLine();
		setEllipsize(TextUtils.TruncateAt.MARQUEE);
		setMarqueeRepeatLimit(-1);
	}

	public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		setFocusable(true);
		setFocusableInTouchMode(true);

		setSingleLine();
		setEllipsize(TextUtils.TruncateAt.MARQUEE);
		setMarqueeRepeatLimit(-1);
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		if (focused) {
			super.onFocusChanged(focused, direction, previouslyFocusedRect);
		}
	}

	@Override
	public void onWindowFocusChanged(boolean focused) {
		if (focused) {
			super.onWindowFocusChanged(focused);
		}
	}

	@Override
	public boolean isFocused() {
		return true;
	}
}