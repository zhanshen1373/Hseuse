/**
 * Project Name:hse-common-module-phone
 * File Name:LocationProgressDialog.java
 * Package Name:com.hd.hse.common.module.phone.ui.custom
 * Date:2015年3月31日
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.module.phone.ui.custom;

import com.hd.hse.common.module.phone.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * ClassName:LocationProgressDialog ().<br/>
 * Date:     2015年3月31日  <br/>
 * @author   xuxinwen
 * @version  
 * @see 	 
 */
public class LocationProgressDialog extends Dialog implements android.view.View.OnClickListener {

	public LocationProgressDialog(Context context) {
		super(context);
		
		initView();
	}

	private TextView tvProgressMsg;
	private TextView btnCancle;
	
	private void initView() {
		Window _window = getWindow();
		_window.setBackgroundDrawable(new ColorDrawable(0x00ffffff));
		
		setContentView(R.layout.hd_hse_common_module_location_progress_dialog);
		
		btnCancle = (TextView) findViewById(R.id.hd_hse_common_module_cancle);
		tvProgressMsg = (TextView) findViewById(R.id.hd_hse_common_module_progress_message);
		
		btnCancle.setOnClickListener(this);
		
		this.setCancelable(false);
	}
	
	public void setProgressMsg(String proMsg){
		tvProgressMsg.setText(proMsg);
	}
	
	private DialogInterface.OnClickListener mCancleClickListener;
	
	public void setOnCancleClickListener(OnClickListener l){
		mCancleClickListener = l;
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.hd_hse_common_module_cancle){
			if(mCancleClickListener != null){
				mCancleClickListener.onClick(this, 0);
			}
		}
	}
	
}

