/**
 * Project Name:hse-main-phone-app
 * File Name:CombinationUIUtils.java
 * Package Name:com.hd.hse.main.phone.ui.activity.welcome
 * Date:2015年1月22日
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.module.ui.model.fragment;


import android.content.Context;
import android.util.Log;
import android.view.View.OnSystemUiVisibilityChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import androidx.viewpager.widget.ViewPager;

import com.hd.hse.common.module.phone.R;

/**
 * 内部工具类，该类应该对外隐藏，
 * ClassName:CombinationUIUtils ().<br/>
 * Date:     2015年1月22日  <br/>
 * @author   xuxinwen
 * @version  
 * @see 	 
 */
public class CombinationUI {
	private final String TAG = this.getClass().getSimpleName();
	private final boolean DEBUG = true;
	
	/**
	 * 绑定 ViewPager 和实现类型为 RadioGroup的indictor，此方法需在 ViewPager
	 * 设置了 Adapter 之后调用，也就是确定了数据数量之后调用，
	 * bindIndictor2ViewPager:(). <br/>
	 * date: 2015年1月22日 <br/>
	 *
	 * @author xuxinwen
	 * @param ctx
	 * @param indictor
	 * @param viewPager
	 */
	public void bindIndictor2ViewPager(Context ctx, final RadioGroup indictor, final ViewPager viewPager){
		/*
		 * 待解决的问题，
		 * 1、第一次显示出来后，什么时刻同步Indictor与ViewPager的状态。
		 */
		
		//清空所有子 View
		indictor.removeAllViews();
		
		// 动态创建 RadioButton
		int _num = viewPager.getAdapter().getCount();
		
		for(int i=0; i<_num; i++){
			if (DEBUG)
				Log.w(TAG, "添加了一个RadioButton："+i);
			RadioButton _radio = new RadioButton(ctx);
			_radio.setButtonDrawable(R.drawable.hd_hse_main_phone_app_dot_bg);
			_radio.setId(i);
			
			RadioGroup.LayoutParams _lp = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
			_lp.rightMargin = 5;
			_radio.setLayoutParams(_lp);
			
			indictor.addView(_radio);
			
		}
		indictor.clearCheck();
		indictor.check(0);
		
		viewPager.setOnSystemUiVisibilityChangeListener(new OnSystemUiVisibilityChangeListener() {
			
			@Override
			public void onSystemUiVisibilityChange(int visibility) {
				if (DEBUG)
					Log.w(TAG, "onSystemUiVisibilityChange visibility:"+visibility);
				
			}
		});
		
		
		/*
		 * 是否添加点击 Indictor ViewPager翻动的效果，
		 */
		indictor.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				viewPager.setCurrentItem(checkedId, true);
			}
		});
		
		/*
		 * ViewPager 翻页时同步 Indictor的状态，可惜的是这些都是在 View的管理类中写的
		 * 这些应该写到 UI 控件自己内部。
		 */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				if (DEBUG)
					Log.w(TAG, "onPageSelected position:"+arg0);
				
				indictor.check(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				if (DEBUG)
					Log.w(TAG, "onPageScrolled arg0:"+arg0+"  arg1:"+arg1+
							"  arg2:"+arg2);
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				if (DEBUG)
					Log.w(TAG, "onPageScrollStateChanged arg0:"+arg0);
				
			}
		});
		
		// ViewPager 指针的代码。
	}
}

