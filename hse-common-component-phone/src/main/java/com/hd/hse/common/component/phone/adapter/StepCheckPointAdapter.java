/**


* 
* 
* 
* 
* 
* 
* 
* 
* Project Name:hse-common-component-phone
* File Name:StepCheckPointAdapter.java
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

import com.hd.hse.common.component.phone.R;

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

import com.hd.hse.common.component.phone.adapter.BaseStepCheckAdapter.ViewHolder;
import com.hd.hse.common.component.phone.custom.EditableDialogManager;
import com.hd.hse.common.component.phone.custom.EditableDialogManager.EditableDialogListener;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;

/**
 * "逐条"措施审批列表适配器(手机版)
 * ClassName:StepCheckPointAdapter ().<br/>
 * Date:     2014年12月24日  <br/>
 * 
 * @author   flb
 * @version  
 * @see 
 * 
 * 逐条
 * 样式: 文字描述--审核人--状态图片
 */
@SuppressWarnings("all")
public class StepCheckPointAdapter<T extends SuperEntity> extends BaseStepCheckAdapter<T> implements OnClickListener{

	private static final String TAG  = StepCheckPointAdapter.class.getSimpleName();
	private static Logger logger     = LogUtils.getLogger(StepCheckPointAdapter.class);
	
	/**
	 * oldcheckresult:TODO(记录开始的操作结果).
	 */
	private String oldcheckresult    = "oldcheckresult";
	private int    selected          = -1;   //当前被选中的Item的位置信息

	public StepCheckPointAdapter(Context mContext, ListView mListView, List<T> mOriginalValues, List<String> attrs, String pcAttr){
		super(mContext, mListView, mOriginalValues, attrs, pcAttr);
	}
	
	@Override
	public void nextItem() {
		int tempSelected = selected + 1;
		if(tempSelected < mOriginalValues.size()){
			moveToDest(tempSelected);
		}
	}

	@Override
	public void previousItem() {
		int tempSelected;
		if(selected == -1){
			tempSelected = mOriginalValues.size() - 1;
		}else{
			tempSelected = selected - 1;
		}
		if(tempSelected >= 0){
			moveToDest(tempSelected);
		}
	}
	
	@Override
	public List<T> getCurrentSelectedValues() {
		if(selected >= 0 && selected < mOriginalValues.size()){
			List<T> selectedData = new ArrayList<T>();
			selectedData.add(mOriginalValues.get(selected));
			return selectedData;
		}
		return null;
	}
	
	/**
	 * 点击条目, 修改条目被选择状态
	 */
	@Override
	public void onClick(View v) {
		Integer position = (Integer) v.getTag();
		moveToDest(position);
	}
	
	//将背景移动到目标位置，并刷新界面
	private void moveToDest(int position){
		T bean = null;
		boolean isSelected = false;
		if(selected == position){  //就在原条目点击
			bean = mOriginalValues.get(selected);
			isSelected = (boolean) bean.getAttribute(isSelectedAttr);
			if(isSelected){
				bean.setAttribute(isSelectedAttr, false);
			}else{
				bean.setAttribute(isSelectedAttr, true);
			}
		}else{                     //不在原条目点击
			if(selected != -1){    //不是第一次点击，之前有被选中的条目
				bean = mOriginalValues.get(selected);
				bean.setAttribute(isSelectedAttr, false);
				if(bean.isModified()){
					bean.setModified(false);
					bean.setAttribute(attrs.get(1),bean.getAttribute(oldcheckresult));
				}
			}
			bean = mOriginalValues.get(position);
			bean.setAttribute(isSelectedAttr, true);
			selected = position;
		}
		
		//刷新界面
		notifyDataSetChanged();
		
		if(mIEventListener != null){
			try {
				mIEventListener.eventProcess(IEventType.ACTION_POINTER_CHECK_STATUS, position);
			} catch (HDException e) {
				//TODO 异常处理
			}
		}
		
		//移动位置
		if(position > (mVisibleItemCount + mFirstVisibleItem - 1)){ //向下翻页
			mListView.setSelection(position);          //置顶
		}else if(position < mFirstVisibleItem){        //向上翻页
			int moveToPosition = position - mVisibleItemCount + 1;
			if(moveToPosition >= 0){
				mListView.setSelection(moveToPosition);
			}else{
				mListView.setSelection(0);
			}
		}
	}
	
	@Override
	protected int getItemResourceId() {
		return R.layout.hd_hse_component_list_item_step_check_sample_1;
	}

	@Override
	public View getItemView(int position, View convertView) {
		LinearLayout rootView = ViewHolder.getView(convertView, R.id.step_check_root_ll);
		TextView leftTV = ViewHolder.getView(convertView, R.id.step_check_left_tv);
		TextView centerTV = ViewHolder.getView(convertView, R.id.step_check_center_tv);
		ImageView rightIV = ViewHolder.getView(convertView, R.id.step_check_right_iv);
		ImageView pcIV = ViewHolder.getView(convertView, R.id.step_check_left_iv_pc);
		
		rootView.setTag(position);
		leftTV.setTag(position);
		centerTV.setTag(position);
		rightIV.setTag(position);
		
		final T bean = getItem(position);
		String desc = (String) bean.getAttribute(attrs.get(0));
		Integer status = (Integer) bean.getAttribute(attrs.get(1));
		String name = (String) bean.getAttribute(attrs.get(2));
		Integer pcStatus = (Integer) bean.getAttribute(pcAttr);
		boolean isSelected = (boolean) bean.getAttribute(isSelectedAttr);
		
		leftTV.setTag(R.string.hd_hse_component_list_item_attribute_order, 0);
		centerTV.setTag(R.string.hd_hse_component_list_item_attribute_order, 2);
		
		//左侧文本描述
		if(desc != null){
			leftTV.setText(desc);
		}else{
			leftTV.setText("");
		}
		
		//中间文本描述
		if(name != null){
			centerTV.setText(name);
		}else{
			centerTV.setText("");
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
		
		//设置条目背景和文字颜色
		if(isSelected){  //当前这批次被选中，但是还没有赋予状态数值
			rootView.setBackgroundResource(R.drawable.hd_component_step_check_item_bg_selected_current);
			leftTV.setTextColor(mContext.getResources().getColor(R.color.step_check_text_color_selected));
		}else{  
			if(bean.isModified()){   //历史批次被选中，已经被赋予状态值
				rootView.setBackgroundResource(R.drawable.hd_component_step_check_item_bg_selected);
				leftTV.setTextColor(mContext.getResources().getColor(R.color.step_check_text_color_selected));
			}else{    
				if(pcStatus == null || pcStatus == 0){  //非来自PC端数据，且始终没有被选中
					rootView.setBackgroundResource(R.drawable.hd_component_step_check_item_bg_normal);
				}else{  //来自PC端数据
					rootView.setBackgroundResource(R.drawable.hd_component_step_check_item_bg_normal);
				}
				leftTV.setTextColor(mContext.getResources().getColor(R.color.step_check_text_color_normal));
			}
		}
		
		leftTV.setOnLongClickListener(this);
		centerTV.setOnLongClickListener(signClickListener);
		
		leftTV.setOnClickListener(this);
		centerTV.setOnClickListener(this);
		rightIV.setOnClickListener(this);
		rootView.setOnClickListener(this);
		
		return convertView;
	}
}