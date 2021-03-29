/**
 * Project Name:hse-common-component-phone
 * File Name:ViewMeasureUtils.java
 * Package Name:com.hd.hse.common.component.util
 * Date:2015年1月5日
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.component.phone.util;

import android.view.View;
import android.view.View.MeasureSpec;

/**
 * ClassName:ViewMeasureUtils ().<br/>
 * Date:     2015年1月5日  <br/>
 * @author   xuxinwen
 * @version  
 * @see 	 
 */
public class ViewMeasureUtils {
	public static int getMeasuredHeight(View v){
		measure(v);
		return v.getMeasuredHeight();
	}
	public static int getMeasuredWidth(View v){
		measure(v);
		return v.getMeasuredWidth();
	}
	
	private static void measure(View v){
		int _measureSize = MeasureSpec.makeMeasureSpec(
				Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		
		v.measure(_measureSize, _measureSize);
	}
	
	public static int getMeasuredHeightWithFixWidth(View v, int width){
		int _mh = MeasureSpec.makeMeasureSpec(
				Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		int _mw = MeasureSpec.makeMeasureSpec(
				width, MeasureSpec.EXACTLY);
		v.measure(_mw, _mh);
		return v.getMeasuredHeight();
	}
}

