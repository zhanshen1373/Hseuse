/**
 * Project Name:hse-common-component-phone
 * File Name:StepCheckTabStrip.java
 * Package Name:com.hd.hse.common.component.phone.custom
 * Date:2015年1月27日
 * Copyright (c) 2015, fulibo@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.component.phone.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.DensityUtil;
import com.hd.hse.common.exception.HDException;

/**
 * ClassName:StepCheckTabStrip ().<br/>
 * Date:     2015年1月27日  <br/>
 * @author   flb
 * @version 
 * @see  
 */
public class StepCheckTabStrip extends HorizontalScrollView{

	public StepCheckTabStrip(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		setFillViewport(true);
		setWillNotDraw(false);
		
		this.setBackgroundColor(Color.WHITE);
		tabsContainer = new LinearLayout(context);
		tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		addView(tabsContainer, params);
		
		DisplayMetrics dm = getResources().getDisplayMetrics();
		scrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);
		underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);
//		tabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabPadding, dm);
//		llytLeftRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, llytLeftRightPadding, dm);
//		llytTopPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, llytTopPadding, dm);
//		llytBottomPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, llytBottomPadding, dm);
		
		underlinePaint = new Paint();
		underlinePaint.setAntiAlias(true);
		underlineColor = getResources().getColor(R.color.hd_hse_common_title_blue_underline);
		underlinePaint.setColor(underlineColor);
		underlinePaint.setStrokeWidth(underlineHeight);
		//lxf
//		defaultlytParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
//		tabsContainer.setPadding(llytLeftRightPadding, llytTopPadding, llytLeftRightPadding, llytBottomPadding);
		tabsContainer.setPadding(0, 0, 0, llytBottomPadding);
	}

	public StepCheckTabStrip(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public StepCheckTabStrip(Context context) {
		this(context, null);
	}
	
	private int lastScrollX = 0;
	private IEventListener mIEventListener;
	
	private LinearLayout tabsContainer;
	private int tabCount;
	
	private Paint underlinePaint;
	private int currentPosition = 0;
	
//	private int underlineColor = 0x550000FF;
	private int underlineColor;
	private int underlineHeight = 2;
	private int scrollOffset = 52;
	private int llytLeftRightPadding = 0;
	
//	private int tabPadding = 24;
//	private int llytLeftRightPadding = 4;
//	private int llytTopPadding = 2;
	private int llytBottomPadding = 2;
	
//	private LinearLayout.LayoutParams defaultlytParams;

	@Override
	protected void onDraw(Canvas canvas) {
		if(isInEditMode() || tabCount == 0){
			return ;
		}
		
		final int height = getHeight();
		canvas.drawRect(0, height - underlineHeight, tabsContainer.getWidth(), height, underlinePaint);
	}
	
	public interface StepCheckTabStripAdapter{
		public int getTabCount();
		public Object getItem(int position);
		public View getView(int position);
	}
	
	private StepCheckTabStripAdapter adapter;
	public void setStepCheckTabStripAdapter(StepCheckTabStripAdapter adapter){
		this.adapter = adapter;
		notifyDataSetChanged();
	}
	
	public void notifyDataSetChanged(){
		tabsContainer.removeAllViews();
		tabCount = adapter.getTabCount();
		for (int i = 0; i < tabCount; i++) {
			View tab = adapter.getView(i);
			addTab(i, tab);
		}
		invalidate();
	}
	
	private void addTab(final int position, View tab){
		tab.setFocusable(true);
		tab.setClickable(true);
		
		tab.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(currentPosition != position){
					scrollToChild(position, 0);
				}
				
				if(mIEventListener != null){
					try{
						mIEventListener.eventProcess(IEventType.TOP_CIRCLE_CHECKED, position);;
					}catch(HDException ex){
						//TODO 异常处理
						ex.printStackTrace();
					}
				}
			}
		});
		
		LinearLayout.MarginLayoutParams marginParams = new LinearLayout.MarginLayoutParams(LinearLayout.MarginLayoutParams.WRAP_CONTENT,LinearLayout.MarginLayoutParams.WRAP_CONTENT);
		int margin = (int) getResources().getDimension(R.dimen.hd_common_component_nav_two_gap);
		marginParams.leftMargin = margin;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(marginParams);
		params.gravity = 0;
		tabsContainer.addView(tab, position, params);
	}
	
	private void scrollToChild(int position, int offset){
		if(tabCount == 0){
			return ;
		}
		
		int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset - llytLeftRightPadding;
		if(position > 0 || offset > 0){
			newScrollX -= scrollOffset;
		}
		
		if(newScrollX != lastScrollX){
			lastScrollX = newScrollX;
			if(position == 0){
				smoothScrollTo(0, 0);
			}else{
				smoothScrollTo(newScrollX, 0);
			}
		}
		currentPosition = position;
	}
	
	public void setOnItemClickListener(IEventListener listener){
		this.mIEventListener = listener;
	}
}

