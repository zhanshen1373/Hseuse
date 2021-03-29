/**
 * Project Name:hse-common-module-phone
 * File Name:PageSlidingTabsView.java
 * Package Name:com.hd.hse.common.module.custom
 * Date:2015年1月4日
 * Copyright (c) 2015, fulibo@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.module.phone.custom;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hd.hse.common.component.phone.custom.PagerSlidingTabStrip;
import com.hd.hse.common.component.phone.custom.PagerSlidingTabStrip.PagerSlidingTabAdapter;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.DensityUtil;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.R;

/**
 * ClassName:PageSlidingTabsView ().<br/>
 * Date:     2015年1月4日  <br/>
 * @author   flb
 * @version 
 * @see     
 */
@SuppressWarnings("all")
public class PageSlidingTabView extends RelativeLayout{

	/*****************常量***********************/
	private static final String textName = "contypedesc";
	private static final String resName = "naviIconId";
	private static final String flag = "contype";
	private static final String isFinished = "iscomplete";
	
	private static final int NORMAL = 0;
	private static final int SELECTED = 1;
	private static final int CURRENT = 2;
	
	private Context mContext;
	private List<SuperEntity> datas;
	private PagerSlidingTabStrip tabs;
	private PageSlidingAdapter adapter;
	private int currentPosition = 0;
	
	private IEventListener listener;
	
	public PageSlidingTabView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public PageSlidingTabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public PageSlidingTabView(Context context) {
		super(context);
		initView(context);
	}
	
	private void initView(Context context) {
		this.mContext = context;
		tabs = new PagerSlidingTabStrip(context);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		tabs.setLayoutParams(params);
		addView(tabs);
	}
	
	public void setDataList(List data){
		this.datas = data;
		adapter = new PageSlidingAdapter();
		int bmpHeight = getResources().getDrawable(((int[])((SuperEntity) datas.get(0)).getAttribute(resName))[NORMAL]).getIntrinsicHeight();
		tabs.setPagerSlidingAdapter(adapter, bmpHeight);
		tabs.setOnItemClickListener(new IEventListener() {
			
			@Override
			public void eventProcess(int eventType, Object... objects) throws HDException {
				if(IEventType.NAVIGATION_ITME_SINGLE_CLICK == eventType){
					//切换本次本选中的item
					Integer tempPosition = (Integer) objects[0];
					if(currentPosition != tempPosition){
						currentPosition = tempPosition;
						tabs.notifiyDataSetChanged();
						//TODO 对外提供点击事件
						if(listener != null){
							listener.eventProcess(IEventType.NAVIGATION_ITME_SINGLE_CLICK, datas.get(currentPosition));
						}
					}else{
						//TODO 点击同一个Item
					}
				}
			}
		});
	}
	
	private class PageSlidingAdapter implements PagerSlidingTabAdapter{

		@Override
		public SuperEntity getItem(int position) {
			return (SuperEntity) datas.get(position);
		}

		@Override
		public int getTabCount() {
			return datas.size();
		}

		@Override
		public View getView(int position) {
			TextView tv = new TextView(mContext);
			SuperEntity entity = getItem(position);
			String sname = (String) entity.getAttribute(textName);
			int[] resIds = (int[]) entity.getAttribute(resName); 
			Integer isCompleted = (Integer) entity.getAttribute(isFinished);
			
			//控制显示参数
			float textSize = (int) mContext.getResources().getDimension(R.dimen.text_size_xsmall);
			System.out.println(textSize);
			if(currentPosition == position){
				tv.setCompoundDrawablesWithIntrinsicBounds(0, resIds[CURRENT], 0, 0);
				tv.setTextColor(getResources().getColor(R.color.hd_hse_common_bule_title_text_color));
				tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
			}else{
				if(isCompleted != null && isCompleted.equals(1)){
					tv. setCompoundDrawablesWithIntrinsicBounds(0, resIds[SELECTED], 0, 0);
				}else{
					tv. setCompoundDrawablesWithIntrinsicBounds(0, resIds[NORMAL], 0, 0);
				}
				tv.setTextColor(Color.BLACK);
				tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
			}
			
			tv.setCompoundDrawablePadding(5);
			tv.setBackgroundColor(Color.TRANSPARENT);
			tv.setSingleLine(true);
			tv.setGravity(Gravity.CENTER);
			tv.setText(sname);
			return tv;
		}

		@Override
		public boolean isFinished(int position) {
			Integer isCompleted = (Integer) datas.get(position).getAttribute(isFinished);
			if(isCompleted != null && isCompleted.equals(1)){
				return true;
			}else{
				return false;
			}
		}
	}
	
	public void setFinished(SuperEntity entity){
		//entity.setAttribute(isFinished, 1);
		int tempIndex = -1;
		for (int i = 0; i < datas.size(); i++) {
			Integer sId = (Integer) datas.get(i).getAttribute(flag);
			if(sId != null && sId.equals(entity.getAttribute(flag))){
				tempIndex = i;
				break;
			}
		}
		
		if(tempIndex != -1){
			datas.set(tempIndex, entity);
			tabs.notifiyDataSetChanged();
		}
	}
	/**
	 * noyifyAll:(整个刷新导航栏). <br/>
	 * date: 2015年3月3日 <br/>
	 *
	 * @author zhaofeng
	 * @param list
	 */
	public void noyifyAllDatas(List<SuperEntity> list){
		datas = list;
		tabs.notifiyDataSetChanged();
	}
	public SuperEntity getCurrentItem(){
		return adapter.getItem(currentPosition);
	}
	
	public void setCurrentItem(SuperEntity entity){
		Integer eid = (Integer) entity.getAttribute(flag);
		for (int i = 0; i < datas.size(); i++) {
			Integer sId = (Integer) datas.get(i).getAttribute(flag);
			if(eid != null && eid.equals(sId)){
				currentPosition = i;
				break;
			}
		}
		
		tabs.setCurrentItem(currentPosition);
	}
	
	public void setOnclickListener(IEventListener listener){
		this.listener = listener;
	}
}