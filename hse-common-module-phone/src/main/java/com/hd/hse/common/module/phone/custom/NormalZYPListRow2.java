/**
 * Project Name:hse-common-module-phone
 * File Name:NormalZYPListRow2.java
 * Package Name:com.hd.hse.common.module.phone.custom
 * Date:2015年1月15日
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.module.phone.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.text.InputFilter.LengthFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.PhoneEventType;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.entity.workorder.WorkOrder;

/**
 * ClassName:NormalZYPListRow2 ().<br/>
 * Date: 2015年1月15日 <br/>
 * 
 * @author xuxinwen
 * @version
 * @see
 */
public class NormalZYPListRow2 extends BaseZYPListRow {

	private final String TAG = "NormalZYPListRow2";
	private final boolean DEBUG = true;
	private boolean pag;
	private String zypid;
	private String qxzypid;

	public boolean isPag() {
		return pag;
	}

	public void setPag(boolean pag) {
		this.pag = pag;
	}

	/**
	 * 是否点击了多选,在点击某个item起作用
	 */
	private boolean flag = false;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/**
	 * 点击了多选就要起作用
	 */
	private boolean bz = false;

	public boolean isBz() {
		return bz;
	}

	public void setBz(boolean bz) {
		this.bz = bz;

	}

	private WorkOrder workorder = new WorkOrder();
	private TextView tv;
	/**
	 * isChoose:TODO(是否支持选择).
	 */
	private boolean isChoose = false;

	/**
	 * isChoose:(获取是否支持选择). <br/>
	 * date: 2015年8月26日 <br/>
	 * 
	 * @author lxf
	 * @return
	 */
	public boolean isChoose() {
		return isChoose;
	}

	/**
	 * setChoose:(设置是否选择). <br/>
	 * date: 2015年8月26日 <br/>
	 * 
	 * @author lxf
	 * @param isChoose
	 */
	public void setChoose(boolean isChoose) {
		this.isChoose = isChoose;
	}

	public NormalZYPListRow2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public NormalZYPListRow2(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public NormalZYPListRow2(Context context) {
		super(context);

	}

	@Override
	public void onClick(View v) {
		/*
		 * 此处点击后 NormalZYPListRow2 不知道要干什么，具体的操作要留给 使用
		 * NormalZYPListRow的类来实现。不能用虚函数来实现，应该用接口 的方法来实现。
		 */
		if (isFlag()) {

			choiceModeSelect(v);
			
			/**
			 * 新加代码
			 */
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

		} else {

			if (!isChoose) {
				if (mEventListener != null) {
					try {
						int _offSet = v.getLeft() + v.getWidth() / 2;
						int _index = (int) v.getTag();

						if (DEBUG) // TODO
							Log.w(TAG,
									"NormalZYPListRow2 里的item被点击后，抛出去的 NormalZYPListRow2"
											+ "实体是：" + NormalZYPListRow2.this);

						mEventListener.eventProcess(
								PhoneEventType.ZYPLIST_ITEM_CLICK,
								mData.get(_index), // 对应的实体。
								NormalZYPListRow2.this, // 要依附的 View
								_offSet, // 指针的位置。
								mGroupPosition);

					} catch (HDException e) {
						// TODO
					}
				}
			} else {
				/*
				 * 点击事件根据 Mode 的不同，表现出不同的行为
				 */
				choiceModeClick(v);

				if (mEventListener != null) {
					try {
						int _index = (int) v.getTag();

						mEventListener.eventProcess(
								PhoneEventType.ZYPLIST_ITEM_CLICK,
								mGroupPosition, // 当前所属组。
								mChildPosition, // 组中位置
								_index, // 该行中位置
								mData.get(_index) // 对应的实体。
								);

					} catch (HDException e) {
						// TODO
					}
				}
			}

		}

	}

	// /// 为选择新增的2015-08-26

	/**
	 * 记录该控件选择状态的集合。
	 */
	private boolean[] mChoiceState;

	/**
	 * 在一次设置数据之后是否调用了设置状态的方法。
	 */
	private boolean isChoiceStatesSynced;

	public void setChoiceStates(boolean[] states) {
		if (states.length != mData.size()) {
			throw new IllegalArgumentException("setChoiceStates--states:"
					+ states.length + "与mData.size()：" + mData.size() + "不同");
		}

		isChoiceStatesSynced = true;
		mChoiceState = states;
		syncChoiceStateToUI();

	}

	@Override
	public void setData(List<SuperEntity> data) {
		/*
		 * 这里有一个隐含的bug，因为状态和数据是分离设置的 可能会出现数量不同步的问题，在设置状态数据的
		 * 时候会判断是否和数据数量相同.可能会出现不同 步的情况是，设置了状态之后又改变了数据的数量。 这里用此标记来避免这个问题。
		 */
		isChoiceStatesSynced = false;
		super.setData(data);
	}

	private void syncChoiceStateToUI() {
		ViewHolder _holder;
		int _dataMaxIndex = mData.size();

		/*
		 * 这里循环数用哪一个是个问题，是用 状态的数量，还是用 数据的数量。 解决方法是，在设置 状态的数组时，判断一下数量是否与数据的数量相同
		 * 不相同的话直接抛出异常。
		 */
		if (DEBUG)
			Log.w(TAG, "syncChoiceStateToUI--_dataMaxIndex:" + _dataMaxIndex);

		for (int i = 0; i < _dataMaxIndex; i++) {
			_holder = mHolders[i];

			if (mChoiceState[i]) {

				// item被选了
				if (DEBUG)
					Log.w(TAG, "syncChoiceStateToUI--第 " + i + " 个" + "是选中状态");
				// Toast.makeText(getContext(), " 第" + i + "个是选中状态", 0).show();
				if (isBz()) {
					//打开多选
				
					if (isPag()) {
						_holder.state
								.setBackgroundResource(R.drawable.dx_nochoice);
//						Toast.makeText(getContext(), pag+"选中打开", 0).show();
						// 好像有问题
						_holder.state.setVisibility(View.VISIBLE);
						mChoiceState[i] = false;

					} else {
						_holder.state
								.setBackgroundResource(R.drawable.hd_hse_common_module_zyplist_selected);
						// 好像有问题
						_holder.state.setVisibility(View.VISIBLE);
//						Toast.makeText(getContext(), pag+"选中打开", 0).show();
					}
				} else {
					// 关闭多选
					mChoiceState[i] = false;
					setPag(true);
					_holder.state.setVisibility(View.INVISIBLE);
                    list.clear();
//					Toast.makeText(getContext(), isPag()+"选中关闭", 0).show();
				}
			} else {
				// item没被选
				if (DEBUG)
					// Toast.makeText(getContext(), " 第" + i + "个是未选中状态", 0)
					// .show();
					Log.w(TAG, "syncChoiceStateToUI--第 " + i + " 个" + "是未选中状态");

				if (isBz()) {
					// 点了多选
//					Toast.makeText(getContext(), "没选中打开", 0).show();
					_holder.state.setBackgroundResource(R.drawable.dx_nochoice);
					_holder.state.setVisibility(View.VISIBLE);
				} else {
					// 关闭多选
					_holder.state.setVisibility(View.INVISIBLE);
					setPag(true);
//					Toast.makeText(getContext(), "没选中关闭", 0).show();
				}

			}
		}
	}

	private void clearChoiceState() {
		/*
		 * 此处的循环参数可能比实际的 mData 的size 要大， 但此处应该用这个循环参数。
		 */
		for (int i = 0; i < CTRL_ITEM_COUNT; i++) {
			mHolders[i].state.setVisibility(View.INVISIBLE);
		}
	}

	private void choiceModeSelect(View v) {

		if (DEBUG)
			Log.d(TAG, "choiceModeClick");

		if (!isChoiceStatesSynced) {
			if (DEBUG)
				Log.d(TAG, "选择的状态还没有同步，不进行响应。");

			return;
		}

		// 点击选中， 内部处理不往外抛。
		int _index = (int) v.getTag();
		
		tv = (TextView) v
				.findViewById(R.id.hse_common_module_phone_grid_item_workorder_zypid);

		if (mChoiceState[_index]) {
			if (DEBUG)
				Log.d(TAG, "该行第：" + _index + " 已经选中，隐藏");

			// 已经选中，隐藏
			v.findViewById(R.id.hse_common_module_phone_grid_item_state)
					.setBackgroundResource(R.drawable.dx_nochoice);
			v.findViewById(R.id.hse_common_module_phone_grid_item_state)
					.setVisibility(View.VISIBLE);
			mChoiceState[_index] = false;
			 
			qxzypid=(String) tv.getText();
//			Toast.makeText(getContext(), qxzypid+"weizhi", 0).show();
			list.remove(qxzypid);
			// Toast.makeText(getContext(), list.size()+"removew", 0).show();
			if (iEventListener != null) {
				try {

					iEventListener.eventProcess(IEventType.ACCEPT, list // 对应的实体。
							);

				} catch (HDException e) {
					e.printStackTrace();
				}
			}
		} else {
			if (DEBUG)
				Log.d(TAG, "该行第：" + _index + "没有被选中， 显示");

			zypid = (String) tv.getText();
			list.add(zypid);
            setPag(false);
//            tag=false;
			// Toast.makeText(getContext(), list.size()+"add", 0).show();
			// 没有被选中， 显示
			v.findViewById(R.id.hse_common_module_phone_grid_item_state)
					.setBackgroundResource(
							R.drawable.hd_hse_common_module_zyplist_selected);
			v.findViewById(R.id.hse_common_module_phone_grid_item_state)
					.setVisibility(View.VISIBLE);
			mChoiceState[_index] = true;
			if (iEventListener != null) {
				try {
					iEventListener.eventProcess(IEventType.ACCEPT, list // 对应的实体。
							);

				} catch (HDException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void choiceModeClick(View v) {
		if (DEBUG)
			Log.d(TAG, "choiceModeClick");

		if (!isChoiceStatesSynced) {
			if (DEBUG)
				Log.d(TAG, "选择的状态还没有同步，不进行响应。");

			return;
		}

		// 点击选中， 内部处理不往外抛。
		int _index = (int) v.getTag();

		if (mChoiceState[_index]) {
			if (DEBUG)
				Log.d(TAG, "该行第：" + _index + " 已经选中，隐藏");

			// 已经选中，隐藏
			v.findViewById(R.id.hse_common_module_phone_grid_item_state)
					.setVisibility(View.INVISIBLE);
			mChoiceState[_index] = false;
		} else {
			if (DEBUG)
				Log.d(TAG, "该行第：" + _index + "没有被选中， 显示");

			// 没有被选中， 显示
			v.findViewById(R.id.hse_common_module_phone_grid_item_state)
					.setVisibility(View.VISIBLE);
			mChoiceState[_index] = true;
		}

	}
}
