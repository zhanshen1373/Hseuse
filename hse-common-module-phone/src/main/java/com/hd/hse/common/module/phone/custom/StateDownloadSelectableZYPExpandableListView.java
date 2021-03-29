/**
 * Project Name:hse-common-module-phone
 * File Name:StateDownloadSelectableZYPExpandableListView.java
 * Package Name:com.hd.hse.common.module.phone.custom
 * Date:2015年3月2日
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.module.phone.custom;

import android.content.Context;
import android.util.AttributeSet;

/**
 * ClassName:StateDownloadSelectableZYPExpandableListView ().<br/>
 * Date:     2015年3月2日  <br/>
 * @author   xuxinwen
 * @version  
 * @see 	 
 */
public class StateDownloadSelectableZYPExpandableListView extends
		SelectableZYPExpandableListView {

	public StateDownloadSelectableZYPExpandableListView(Context context,
			AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	public StateDownloadSelectableZYPExpandableListView(Context context,
			AttributeSet attrs) {
		super(context, attrs);
		
	}

	public StateDownloadSelectableZYPExpandableListView(Context context) {
		super(context);
		
	}

	@Override
	protected BaseZYPListRow onCreateChildView() {
		return new StateDownloadSelectableListRow(getContext());
	}
	
}

