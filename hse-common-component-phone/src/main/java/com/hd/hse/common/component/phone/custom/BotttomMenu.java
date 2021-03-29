/**
 * Project Name:hse-common-component-phone
 * File Name:BotttomMenu.java
 * Package Name:com.hd.hse.common.component.phone.custom
 * Date:2015年1月26日
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.component.phone.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;

import org.apache.log4j.Logger;

/**
 * ClassName:BotttomMenu ().<br/>
 * Date:     2015年1月26日  <br/>
 * @author   xuxinwen
 * @version  
 * @see 	 
 */
public class BotttomMenu extends FrameLayout {
	
	private static Logger logger = LogUtils.getLogger(BotttomMenu.class);

	private IEventListener mIEventListener;
	
	private RadioGroup gRadioGroup;
	private TextView gUpward;
	private TextView gDownward;
	private TextView gButton;
	private View gRootView;
	private Context context;
	
	public BotttomMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
	}

	public BotttomMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public BotttomMenu(Context context) {
		super(context);
		this.context = context;
		init();
	}

	private void init() {
		gRootView = View.inflate(getContext(), R.layout.hd_hse_common_component_botttom_menu, null);
		this.addView(gRootView);
		
		gUpward = (TextView) gRootView.findViewById(R.id.hd_hse_common_component_upward);
		gDownward = (TextView) gRootView.findViewById(R.id.hd_hse_common_component_downward);
		gButton = (TextView) gRootView.findViewById(R.id.hd_hse_common_component_button);
		gRadioGroup = (RadioGroup) gRootView.findViewById(R.id.hd_hse_common_component_radiogroup);
		
		clearCheck();
		
		gUpward.setOnClickListener(new InnerClickListener());
		gDownward.setOnClickListener(new InnerClickListener());
		gButton.setOnClickListener(new InnerClickListener());
		
		gRadioGroup.setOnCheckedChangeListener(new InnerCheckedChangeListener());
	}
	
	/**
	 * 给右边按钮设置文本。
	 * setButtonText:(). <br/>
	 * date: 2015年1月27日 <br/>
	 *
	 * @author xuxinwen
	 * @param text
	 */
	public void setButtonText(String text){
		gButton.setText(text);
	}
	
	/**
	 * 清空所有的选中状态。
	 * clearCheck:(). <br/>
	 * date: 2015年1月26日 <br/>
	 *
	 * @author xuxinwen
	 */
	public void clearCheck(){
		gRadioGroup.clearCheck();
	}
	
	/*
	 * 标记 RadioButton 选中改变事件是点击UI触发的还是调用方法触发的。
	 */
	private boolean isClickTriggerCheckedChanged = true;

	/**
	 * 设置对勾的图标是否可见。
	 * 
	 * setSelectVisibable:(). <br/>
	 * date: 2015年3月2日 <br/>
	 *
	 * @author xuxinwen
	 * @param visibable
	 */
	public void setSelectVisibable(boolean visibable){
		if(visibable){
			gRadioGroup.findViewById(R.id.hd_hse_common_component_selected).setVisibility(View.VISIBLE);
		}else{
			gRadioGroup.findViewById(R.id.hd_hse_common_component_selected).setVisibility(View.INVISIBLE);
		}
	}
	
	/**
	 * 选中对勾的图标
	 * checkSelect:(). <br/>
	 * date: 2015年1月26日 <br/>
	 *
	 * @author xuxinwen
	 */
	public void checkSelect(){
		isClickTriggerCheckedChanged = false;
		gRadioGroup.check(R.id.hd_hse_common_component_selected);
	}
	
	
	/**
	 * 设置 差的图标是否可见。
	 * 
	 * setUnSelectVisibable:(). <br/>
	 * date: 2015年3月2日 <br/>
	 *
	 * @author xuxinwen
	 * @param visibable
	 */
	public void setUnSelectVisibable(boolean visibable){
		if(visibable){
			gRadioGroup.findViewById(R.id.hd_hse_common_component_unselected).setVisibility(View.VISIBLE);
		}else{
			gRadioGroup.findViewById(R.id.hd_hse_common_component_unselected).setVisibility(View.INVISIBLE);
		}
	}
	
	/**
	 * 选中 差 的图标
 	 * checkUnSelect:(). <br/>
	 * date: 2015年1月26日 <br/>
	 *
	 * @author xuxinwen
	 */
	public void checkUnSelect(){
		isClickTriggerCheckedChanged = false;
		gRadioGroup.check(R.id.hd_hse_common_component_unselected);
	}
	
	public void setArrowButtonVsibibable(boolean visible){
		setUpwardButtonVisibable(visible);
		setDownwardButtonVisibable(visible);
	}
	
	public void setUpwardButtonVisibable(boolean visible){
		if(visible){
			gUpward.setVisibility(View.VISIBLE);
		}else{
			gUpward.setVisibility(View.INVISIBLE);
		}
	}
	
	public void setDownwardButtonVisibable(boolean visible){
		if(visible){
			gDownward.setVisibility(View.VISIBLE);
		}else{
			gDownward.setVisibility(View.INVISIBLE);
		}
	}
	
	/**
	 * 设置 圈的图标是否可见
	 * 
	 * setCircleVisibable:(). <br/>
	 * date: 2015年3月2日 <br/>
	 *
	 * @author xuxinwen
	 * @param visibable
	 */
	public void setCircleVisibable(boolean visibable){
		if(visibable){
			gRadioGroup.findViewById(R.id.hd_hse_common_component_circle).setVisibility(View.VISIBLE);
		}else{
			gRadioGroup.findViewById(R.id.hd_hse_common_component_circle).setVisibility(View.INVISIBLE);
		}
	}
	
	/**
	 * 选中圈的图标
	 * checkCircle:(). <br/>
	 * date: 2015年1月26日 <br/>
	 *
	 * @author xuxinwen
	 */
	public void checkCircle(){
		isClickTriggerCheckedChanged = false;
		gRadioGroup.check(R.id.hd_hse_common_component_circle);
	}
	
	public void setIEventListener(IEventListener listener){
		mIEventListener = listener;
	}

	class InnerClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			int _id = v.getId();
			int _eventType = -1;
			
			if(_id == R.id.hd_hse_common_component_upward){
				_eventType = IEventType.BOTTOM_UPWARD_CLICK;
			}else if(_id == R.id.hd_hse_common_component_downward){
				_eventType = IEventType.BOTTOM_DOWNWARD_CLICK;
			}else if(_id == R.id.hd_hse_common_component_button){
				_eventType = IEventType.BOTTOM_BUTTON_CLICK;
			}
			
			if(mIEventListener != null && _eventType != -1){
				try {
					mIEventListener.eventProcess(_eventType, new  Object[]{});
				} catch (HDException e) {
					logger.error(e);
					ToastUtils.imgToast(context, R.drawable.hd_common_message_error, e.getMessage());
				}
			}
		}
		
	}
	
	class InnerCheckedChangeListener implements OnCheckedChangeListener{
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (isClickTriggerCheckedChanged) {
				int _eventType = -1;

				if (checkedId == R.id.hd_hse_common_component_selected) {
					_eventType = IEventType.BOTTOM_SELECT_CHECKED;
				} else if (checkedId == R.id.hd_hse_common_component_unselected) {
					_eventType = IEventType.BOTTOM_UNSELECT_CHECKED;
				} else if (checkedId == R.id.hd_hse_common_component_circle) {
					_eventType = IEventType.BOTTOM_CIRCLE_CHECKED;
				}

				if (mIEventListener != null && _eventType != -1) {
					try {
						mIEventListener.eventProcess(_eventType, new  Object[]{});
					} catch (HDException e) {
						logger.error(e);
					}
				}
			}else{
				isClickTriggerCheckedChanged = true;
			}
		}
	}
}

