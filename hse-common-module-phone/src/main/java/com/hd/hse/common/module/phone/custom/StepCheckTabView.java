/**
 * Project Name:hse-common-module-phone
 * File Name:StepCheckTabView.java
 * Package Name:com.hd.hse.common.module.phone.custom
 * Date:2015年1月27日
 * Copyright (c) 2015, fulibo@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.module.phone.custom;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hd.hse.common.component.phone.custom.StepCheckTabStrip;
import com.hd.hse.common.component.phone.custom.StepCheckTabStrip.StepCheckTabStripAdapter;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;

/**
 * ClassName:StepCheckTabView ().<br/>
 * Date:     2015年1月27日  <br/>
 * @author   flb
 * @version 
 * @see     
 */
@SuppressWarnings("all")
public class StepCheckTabView extends RelativeLayout{

	/******************常量***********************/
//	private static final String KEY = "ud_zyxk_zysqpdascid";
	private static final String KEY = "sname";
	
	private Context mContext;
	private List<PDAWorkOrderInfoConfig> mSourceValues;
	private StepCheckTabStrip tabs;
	private StepCheckTabAdapter adapter;
	private int currentPosition = 0;
	
	private IEventListener mListener;
	
	public StepCheckTabView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public StepCheckTabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public StepCheckTabView(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		this.mContext = context;
		tabs = new StepCheckTabStrip(context);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		tabs.setLayoutParams(lp);
		addView(tabs);
	}
	
	public void setDataList(List<PDAWorkOrderInfoConfig> datas){
		this.mSourceValues = datas;
		adapter = new StepCheckTabAdapter();
		tabs.setStepCheckTabStripAdapter(adapter);
		tabs.setOnItemClickListener(new IEventListener() {
			
			@Override
			public void eventProcess(int eventType, Object... objects) throws HDException {
				if(IEventType.TOP_CIRCLE_CHECKED == eventType){
					Integer temp = (Integer)objects[0];
					if(currentPosition != temp){
						//TODO 修改上一个和当前状态
						mSourceValues.get(currentPosition).setModified(false);
						currentPosition = temp;
						mSourceValues.get(currentPosition).setModified(true);
						tabs.notifyDataSetChanged();
						if(mListener != null){
							mListener.eventProcess(IEventType.TOP_CIRCLE_CHECKED, mSourceValues.get(currentPosition));
						}
					}
				}
			}
		});
	}
	
	
	private class StepCheckTabAdapter implements StepCheckTabStripAdapter{

		@Override
		public PDAWorkOrderInfoConfig getItem(int position) {
			return mSourceValues.get(position);
		}

		@Override
		public int getTabCount() {
			return mSourceValues.size();
		}

		@Override
		public View getView(int position) {
			TextView titleTV = new TextView(mContext);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
			titleTV.setPadding(10, 10, 10, 10);
			titleTV.setLayoutParams(lp);
			titleTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_small));
			
			PDAWorkOrderInfoConfig item = getItem(position);
			titleTV.setText(item.getSname());
			
			if(item.isModified()){
				titleTV.setTextColor(Color.WHITE);
				titleTV.setBackgroundResource(R.drawable.shape_step_check_nav_checked);
			}else{
				titleTV.setTextColor(getResources().getColor(R.color.hd_hse_common_title_blue_underline));
				titleTV.setBackgroundColor(Color.TRANSPARENT);
			}
			return titleTV;
		}
	}
	
	//TODO shape_step_check_nav_checked.xml
	public PDAWorkOrderInfoConfig getCurrentItem(){
		return adapter.getItem(currentPosition);
	}
	
	public void setCurrentItem(PDAWorkOrderInfoConfig entity){
		System.out.println(entity);
		
		entity.setModified(true);
		String key = (String) entity.getAttribute(KEY);
		int index = 0;
		
		for(int i=0; i< mSourceValues.size(); i++){
			if(key.equals(mSourceValues.get(i).getAttribute(KEY))){
				index = i;
				break ;
			}
		}
		
		mSourceValues.set(index, entity);
		tabs.notifyDataSetChanged();
	}
	
	public void setOnIEventListener(IEventListener listener){
		this.mListener = listener;
	}
}