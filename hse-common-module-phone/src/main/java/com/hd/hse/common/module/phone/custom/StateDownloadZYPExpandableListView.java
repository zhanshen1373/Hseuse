/**
 * Project Name:hse-common-module-phone
 * File Name:StateDownloadZYPExpandableListView.java
 * Package Name:com.hd.hse.common.module.phone.custom
 * Date:2015年3月2日
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.module.phone.custom;

import android.content.Context;
import android.util.AttributeSet;

/**
 * ClassName:StateDownloadZYPExpandableListView ().<br/>
 * Date:     2015年3月2日  <br/>
 * @author   xuxinwen
 * @version  
 * @see 	 
 */
public class StateDownloadZYPExpandableListView extends
		NormalZYPExpandableListView {

	
	
	public StateDownloadZYPExpandableListView(Context context,
			AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	public StateDownloadZYPExpandableListView(Context context,
			AttributeSet attrs) {
		super(context, attrs);
		
	}

	public StateDownloadZYPExpandableListView(Context context) {
		super(context);
		
	}

	@Override
	protected BaseZYPListRow onCreateChildView() {
		
		return new StateDownloadZYPListRow(getContext());
	}

}

