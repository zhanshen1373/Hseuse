/**
 * Project Name:hse-common-component-phone
 * File Name:StepCheckPartAdapter.java
 * Package Name:org.hse.common.component.phone.adapter
 * Date:2014年12月24日
 * Copyright (c) 2014, fulibo@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.component.phone.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.component.phone.adapter.BaseStepCheckExpanableListAdapter.ViewHolder;
import com.hd.hse.common.component.phone.custom.EditableDialogManager;
import com.hd.hse.common.component.phone.custom.EditableDialogManager.EditableDialogListener;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;

/**
 * ClassName:StepCheckPartAdapter ().<br/>
 * Date: 2014年12月24日 <br/>
 * 
 * @author flb
 * @version
 * @see 批量 样式: 文字描述--状态图片
 */
@SuppressWarnings("all")
public class StepCheckPartAdapter<T extends SuperEntity> extends BaseStepCheckAdapter<T> implements OnClickListener {

	private static final String TAG = StepCheckPartAdapter.class.getSimpleName();
	private static Logger logger = LogUtils.getLogger(StepCheckPartAdapter.class);

	public StepCheckPartAdapter(Context mContext, ListView mListView, List<T> mOriginalValues, List<String> attrs, String pcAttr) {
		super(mContext, mListView, mOriginalValues, attrs, pcAttr);
	}

	@Override
	public void onClick(View v) {
		Integer position = (Integer) v.getTag();
		T bean = mOriginalValues.get(position);
		boolean isSelected = (boolean) bean.getAttribute(isSelectedAttr);
		if (isSelected) {
			bean.setAttribute(isSelectedAttr, false);
		} else {
			bean.setAttribute(isSelectedAttr, true);
		}
		notifyDataSetChanged();

		if (mIEventListener != null) {
			try {
				mIEventListener.eventProcess(IEventType.ACTION_POINTER_CHECK_STATUS, position);
			} catch (HDException e) {
				// TODO 异常处理
			}
		}
	}

	@Override
	protected int getItemResourceId() {
		return R.layout.hd_hse_component_list_item_step_check_sample_2;
	}
	
	@Override
	public View getItemView(int position, View convertView) {
		LinearLayout rootView = ViewHolder.getView(convertView, R.id.step_check_root_ll);
		TextView leftTV = ViewHolder.getView(convertView, R.id.step_check_left_tv);
		ImageView rightIV = ViewHolder.getView(convertView, R.id.step_check_right_iv);
		ImageView pcIV = ViewHolder.getView(convertView, R.id.step_check_left_iv_pc);
		
		rootView.setTag(position);
		leftTV.setTag(position);
		rightIV.setTag(position);
		
		final T bean = getItem(position);
		String desc = (String) bean.getAttribute(attrs.get(0));
		Integer status = (Integer) bean.getAttribute(attrs.get(1));
		Integer pcStatus = (Integer) bean.getAttribute(pcAttr);
		boolean isSelected = (boolean) bean.getAttribute(isSelectedAttr);
		
		leftTV.setTag(R.string.hd_hse_component_list_item_attribute_order, 0);
		
		if (desc != null) {
			leftTV.setText(desc);
		} else {
			leftTV.setText("");
		}
		
		//显示左侧标记
		if(pcStatus == null || pcStatus == 0){
			pcIV.setVisibility(View.GONE);
		}else{
			pcIV.setVisibility(View.VISIBLE);
		}
		
		//对于选择结果的显示
		if(status == null || status == -1){  //无结果数值
			rightIV.setVisibility(View.INVISIBLE);
			rightIV.setImageResource(iconResLight[0]);
		}else{  //有结果数值
			rightIV.setVisibility(View.VISIBLE);
			rightIV.setImageResource(iconResLight[status]);
		}
		
		//设置条目背景
		if (isSelected) {
			rootView.setBackgroundResource(R.drawable.hd_component_step_check_item_bg_selected_current);
			leftTV.setTextColor(mContext.getResources().getColor(R.color.step_check_text_color_selected));
		} else {
			if(bean.isModified()){
				rootView.setBackgroundResource(R.drawable.hd_component_step_check_item_bg_selected);
				leftTV.setTextColor(mContext.getResources().getColor(R.color.step_check_text_color_selected));
			}else{
				if(pcStatus == null || pcStatus == 0){
					rootView.setBackgroundResource(R.drawable.hd_component_step_check_item_bg_normal);
				}else{
					rootView.setBackgroundResource(R.drawable.hd_component_step_check_item_bg_normal);
				}
				leftTV.setTextColor(mContext.getResources().getColor(R.color.step_check_text_color_normal));
			}
		}
		
		leftTV.setOnLongClickListener(this);
		leftTV.setOnClickListener(this);
		rightIV.setOnClickListener(this);
		rootView.setOnClickListener(this);
		
		return convertView;
	}
}