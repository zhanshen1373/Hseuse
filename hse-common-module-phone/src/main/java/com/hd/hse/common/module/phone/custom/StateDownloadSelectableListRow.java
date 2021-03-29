/**
 * Project Name:hse-common-module-phone
 * File Name:StateDownloadSelectableListRow.java
 * Package Name:com.hd.hse.common.module.phone.custom
 * Date:2015年3月2日
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.module.phone.custom;

import android.content.Context;
import android.util.AttributeSet;

/**
 * ClassName:StateDownloadSelectableListRow ().<br/>
 * Date:     2015年3月2日  <br/>
 * @author   xuxinwen
 * @version  
 * @see 	 
 */
public class StateDownloadSelectableListRow extends SelectableZYPListRow {

	public StateDownloadSelectableListRow(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		
	}

	public StateDownloadSelectableListRow(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	public StateDownloadSelectableListRow(Context context) {
		super(context);
		
	}
	
	@Override
	protected boolean showWorkOrderState() {
		return true;
	}

	@Override
	protected boolean separateDownloadAndNot() {
		return true;
	}
}

