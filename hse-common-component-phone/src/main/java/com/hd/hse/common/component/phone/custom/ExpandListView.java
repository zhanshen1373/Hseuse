/**
 * Project Name:hse-common-component-phone
 * File Name:WrapListView.java
 * Package Name:com.hd.hse.common.component.phone.custom
 * Date:2015年1月21日
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.component.phone.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * ClassName:WrapListView ().<br/>
 * Date: 2015年1月21日 <br/>
 * 
 * @author xuxinwen
 * @version
 * @see
 */
public class ExpandListView extends ListView {

	public ExpandListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ExpandListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ExpandListView(Context context) {
		super(context);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int _expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
		MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, _expandSpec);
	}
}
