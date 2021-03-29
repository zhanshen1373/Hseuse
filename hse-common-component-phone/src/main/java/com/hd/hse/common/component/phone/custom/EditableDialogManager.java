/**
 * Project Name:hse-common-component
 * File Name:EditableDialog.java
 * Package Name:com.hd.hse.common.component.custom
 * Date:2014年10月14日
 * Copyright (c) 2014, fulibo@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.component.phone.custom;

import com.hd.hse.common.component.phone.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * ClassName:EditableDialog ().<br/>
 * Date:     2014年10月14日  <br/>
 * @author   flb
 * @version  
 * @see 	 
 */
public class EditableDialogManager {
	
	private AlertDialog dialog   =  null;
	private View  rootView       =  null; 
	private TextView editText    =  null;
	private Integer index        =  null;
	
	/**
	 * 
	 * showDialog:(). <br/>
	 * date: 2014年10月14日 <br/>
	 *
	 * @author flb    
	 * @param  text    要显示的文本
	 * @param  isFlag  是否可编辑 true代表可编辑, false代表不可编辑
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public AlertDialog showDialog(Context ctx, String content, boolean isFlag){
		AlertDialog.Builder builder = new Builder(ctx);
		dialog = builder.create();
		
		if(isFlag){
			rootView = View.inflate(ctx, R.layout.hd_hse_common_component_check_text_dialog_style_one, null);
			
			editText = new StepCheckEditText(ctx);
			editText.setMovementMethod(ScrollingMovementMethod.getInstance());
			((StepCheckEditText)editText).setHasBracketsText(content);
			TextView confirmTV = (TextView) rootView.findViewById(R.id.hd_hse_common_component_step_check_text_dialog_tv_confirm);
			confirmTV.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(listener != null){
						String content = editText.getText().toString().trim();
						listener.onConfirmClick(content);
					}
					
					dismissDialog();
				}
			});
		}else{
			rootView = View.inflate(ctx, R.layout.hd_hse_common_component_check_text_dialog_style_two, null);
			
			editText = new TextView(ctx);
			editText.setText(content);
			editText.setMovementMethod(ScrollingMovementMethod.getInstance());
		}
		dialog.setView(rootView, 0, 0, 0, 0);
		
		RelativeLayout containerRL = (RelativeLayout) rootView.findViewById(R.id.hd_hse_common_component_step_check_text_dialog_rl_container);
		TextView cancelTV = (TextView) rootView.findViewById(R.id.hd_hse_common_component_step_check_text_dialog_tv_cancel);
		
		editText.setBackgroundDrawable(null);
		editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, ctx.getResources().getDimensionPixelOffset(R.dimen.hd_hse_common_component_phone_editable_dialog_text));
		editText.setTextColor(ctx.getResources().getColor(R.color.hd_hse_common_alerttext_black));
		containerRL.addView(editText);
		cancelTV.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener != null){
					String content = editText.getText().toString().trim();
					listener.onCancelClick(content);
				}
				
				dismissDialog();
			}
		});
		
		dialog.show();
		return dialog;
	}
	
	public void dismissDialog(){
		if(dialog != null && dialog.isShowing()){
			dialog.dismiss();
			dialog = null;
		}
	}
	
	public Integer getIndex(){
		return index;
	}
	
	public interface EditableDialogListener{
		public void onConfirmClick(String content);
		public void onCancelClick(String content);
	}
	
	private EditableDialogListener listener;
	
	public void setOnEditableDialogListener(EditableDialogListener listen){
		listener = listen;
	}
}

