/**
 * Project Name:hse-common-module-phone
 * File Name:ZYPListRow.java
 * Package Name:com.hd.hse.common.module.custom
 * Date:2014年12月26日
 * Copyright (c) 2014, xuxinwen@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.module.phone.custom;

import org.apache.log4j.Logger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.PhoneEventType;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.entity.workorder.WorkOrder;

/**
 * ClassName:ZYPListRow ().<br/>
 * Date: 2014年12月26日 <br/>
 * 
 * @author xuxinwen
 * @version
 * @see
 */
public class StatableZYPListRow extends BaseZYPListRow {

	private static Logger logger = LogUtils.getLogger(StatableZYPListRow.class);
	
	private final String TAG = "StatableZYPListRow";
	private boolean DEBUG = true;

	
	@SuppressLint("NewApi")
	public StatableZYPListRow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public StatableZYPListRow(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public StatableZYPListRow(Context context) {
		super(context);
		init();
	}

	private void init(){}
	
	private void syncChoiceStateToUI() {
		ViewHolder _holder;
		int _dataMaxIndex = mData.size();
		
		
		if (DEBUG) Log.w(TAG, "syncChoiceStateToUI--_dataMaxIndex:"+_dataMaxIndex);
		
		for (int i = 0; i < _dataMaxIndex; i++) {
			_holder = mHolders[i];
			
			if (((WorkOrder)mData.get(i)).isDownloaded()) {
				
				if (DEBUG) Log.w(TAG, "syncChoiceStateToUI--第 "+i+" 个"+"是已下载状态");
				
				_holder.state.setVisibility(View.VISIBLE);
			} else {
				if (DEBUG) Log.w(TAG, "syncChoiceStateToUI--第 "+i+" 个"+"是已下载状态");
				
				_holder.state.setVisibility(View.INVISIBLE);
			}
		}
	}

	private void clearChoiceState() {
		/*
		 * 此处的循环参数可能比实际的 mData 的size 要大，
		 * 但此处应该用这个循环参数。
		 */
		for (int i = 0; i < CTRL_ITEM_COUNT; i++) {
			mHolders[i].state.setVisibility(View.INVISIBLE);
		}
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		if (DEBUG) Log.v(TAG, "第" + (int) v.getTag() + "个控件被点击了");

		/*
		 * 此处点击后 NormalZYPListRow 不知道要干什么，具体的操作要留给
		 * 使用 NormalZYPListRow的类来实现。不能用虚函数来实现，应该用接口
		 * 的方法来实现。
		 */
		if (mEventListener != null) {
			try {
				int _index = (int) v.getTag();

				mEventListener.eventProcess(PhoneEventType.ZYPLIST_ITEM_CLICK,
						mData.get(_index) // 对应的实体。
						); 

			} catch (HDException e) {
				logger.error(e);
			}
		}
	}

	

}
