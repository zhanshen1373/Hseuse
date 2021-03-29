/**
 * Project Name:hse-common-component-phone
 * File Name:WrapContentHeightViewPager.java
 * Package Name:com.hd.hse.common.component.phone.custom
 * Date:2016年1月13日
 * Copyright (c) 2016, zhaofeng@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.component.phone.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * ClassName:WrapContentHeightViewPager ().<br/>
 * Date:     2016年1月13日  <br/>
 * @author   LiuYang
 * @version  
 * @see 	 
 */
public class WrapContentHeightViewPager extends ViewPager {
	 
    public WrapContentHeightViewPager(Context context) {
        super(context);
    }
 
    public WrapContentHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
 
        int height = 0;
        //下面遍历所有child的高度
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) //采用最大的view的高度。
                height = h;
        }
 
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                MeasureSpec.EXACTLY);
 
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

