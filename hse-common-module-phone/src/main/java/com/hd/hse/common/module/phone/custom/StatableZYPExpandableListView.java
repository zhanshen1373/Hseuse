/**
 * Project Name:hse-common-module-phone
 * File Name:SelectableZYPExpandableListView.java
 * Package Name:com.hd.hse.common.module.custom
 * Date:2015年1月4日
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.module.phone.custom;

import org.apache.log4j.Logger;

import android.content.Context;
import android.util.AttributeSet;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.PhoneEventType;

/**
 * ClassName:SelectableZYPExpandableListView ().<br/>
 * Date: 2015年1月4日 <br/>
 * 
 * @author xuxinwen
 * @version
 * @see
 */
public class StatableZYPExpandableListView extends BaseZYPExpandableListView  {

	private static Logger logger = LogUtils.getLogger(StatableZYPExpandableListView.class);
	
	private final String TAG = "StatableZYPExpandableListView";
	private final boolean DEBUG = true;
	
	public StatableZYPExpandableListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public StatableZYPExpandableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public StatableZYPExpandableListView(Context context) {
		super(context);
	}
	
	@Override
	protected void onCreateGroupView(GroupHolder viewHolder, int groupPosition) {
		viewHolder.icon.setClickable(false);
	}
	
	@Override
	protected BaseZYPListRow onCreateChildView() {
		// TODO 更改标志图片
		return new StatableZYPListRow(getContext());
	}
	
	@Override
	protected void onItemClick(Object... args)  {
		if(mEventListener != null){
			try {
				mEventListener.eventProcess(PhoneEventType.ZYPLIST_ITEM_CLICK, args);
			} catch (HDException e) {
				logger.error(e);
			}
		}
	}
	
}
