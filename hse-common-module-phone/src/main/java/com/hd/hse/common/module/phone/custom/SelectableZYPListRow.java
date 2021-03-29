/**
 * Project Name:hse-common-module-phone
 * File Name:ZYPListRow.java
 * Package Name:com.hd.hse.common.module.custom
 * Date:2014年12月26日
 * Copyright (c) 2014, xuxinwen@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.module.phone.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.PhoneEventType;
import com.hd.hse.common.module.phone.R;

import java.util.List;

/**
 * ClassName:ZYPListRow ().<br/>
 * Date: 2014年12月26日 <br/>
 * 
 * @author xuxinwen
 * @version
 * @see
 */
public class SelectableZYPListRow extends BaseZYPListRow {

	private final String TAG = "ZYPListRow";
	private boolean DEBUG = true;

	
	@SuppressLint("NewApi")
	public SelectableZYPListRow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public SelectableZYPListRow(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SelectableZYPListRow(Context context) {
		super(context);
		init();
	}

	private void init(){}
	
	/**
	 * 记录该控件选择状态的集合。
	 */
	private boolean[] mChoiceState;
	

	/**
	 * 在一次设置数据之后是否调用了设置状态的方法。
	 */
	private boolean isChoiceStatesSynced;
	
	public void setChoiceStates(boolean[] states) {
		if(states.length != mData.size()){
			throw new IllegalArgumentException("setChoiceStates--states:"+states.length
					+"与mData.size()："+mData.size()+"不同");
		}
		
		isChoiceStatesSynced = true;
		mChoiceState = states;
		syncChoiceStateToUI();
	}
	
	@Override
	public void setData(List<SuperEntity> data) {
		/*
		 * 这里有一个隐含的bug，因为状态和数据是分离设置的
		 * 可能会出现数量不同步的问题，在设置状态数据的
		 * 时候会判断是否和数据数量相同.可能会出现不同
		 * 步的情况是，设置了状态之后又改变了数据的数量。
		 * 这里用此标记来避免这个问题。
		 */
		isChoiceStatesSynced = false;
		super.setData(data);
	}

	private void syncChoiceStateToUI() {
		ViewHolder _holder;
		int _dataMaxIndex = mData.size();
		
		/*
		 * 这里循环数用哪一个是个问题，是用 状态的数量，还是用 数据的数量。
		 * 解决方法是，在设置 状态的数组时，判断一下数量是否与数据的数量相同
		 * 不相同的话直接抛出异常。
		 */
		if (DEBUG) Log.w(TAG, "syncChoiceStateToUI--_dataMaxIndex:"+_dataMaxIndex);
		
		for (int i = 0; i < _dataMaxIndex; i++) {
			_holder = mHolders[i];
			
			if (mChoiceState[i]) {
				
				if (DEBUG) Log.w(TAG, "syncChoiceStateToUI--第 "+i+" 个"+"是选中状态");
				
				_holder.state.setVisibility(View.VISIBLE);
			} else {
				if (DEBUG) Log.w(TAG, "syncChoiceStateToUI--第 "+i+" 个"+"是未选中状态");
				
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
	
	/**
	 * 这个方法是从父类继承来的方法，所响应的是每个 item 的点击事件，
	 * 所有的item设置的都是该方法的实例，这个方法是根据子类的不同而
	 * 不同的。本类中的item点击就直接被自己消费掉了。
	 * TODO 
	 * @see com.hd.hse.common.module.phone.custom.BaseZYPListRow#onClick(android.view.View)
	 */
	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		if (DEBUG) Log.v(TAG, "第" + (int) v.getTag() + "个控件被点击了");

		/*
		 * 点击事件根据 Mode 的不同，表现出不同的行为
		 */
		choiceModeClick(v);
		
		if (mEventListener != null) {
			try {
				int _index = (int) v.getTag();
				
				mEventListener.eventProcess(PhoneEventType.ZYPLIST_ITEM_CLICK,
						mGroupPosition, // 当前所属组。
						mChildPosition, // 组中位置
						_index,			// 该行中位置
						mData.get(_index) // 对应的实体。
						); 

			} catch (HDException e) {
				// TODO
			}
		}
	}

	private void choiceModeClick(View v) {
		if (DEBUG) Log.d(TAG, "choiceModeClick");
		
		if(!isChoiceStatesSynced){
			if (DEBUG) Log.d(TAG, "选择的状态还没有同步，不进行响应。");
			
			return; 
		}
		
		
		// 点击选中， 内部处理不往外抛。
		int _index = (int) v.getTag();
		
		if (mChoiceState[_index]) {
			if (DEBUG) Log.d(TAG, "该行第："+_index+" 已经选中，隐藏");
			
			// 已经选中，隐藏
			v.findViewById(R.id.hse_common_module_phone_grid_item_state)
					.setVisibility(View.INVISIBLE);
			mChoiceState[_index] = false;
		} else {
			if (DEBUG) Log.d(TAG, "该行第："+_index+"没有被选中， 显示");
			
			// 没有被选中， 显示
			v.findViewById(R.id.hse_common_module_phone_grid_item_state)
					.setVisibility(View.VISIBLE);
			mChoiceState[_index] = true;
		}
	}

	
}
