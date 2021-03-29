/**
 * Project Name:hse-common-component-phone
 * File Name:PaintSignatureDialog.java
 * Package Name:com.hd.hse.common.component.phone.custom
 * Date:2015年11月2日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.component.phone.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hd.hse.common.component.phone.R;

/**
 * ClassName:PaintSignatureDialog (手签dialog).<br/>
 * Date: 2015年10月8日 <br/>
 * 
 * @author LiuYang
 * @version
 * @see
 */
public class PaintSignatureDialog extends Dialog {
	public interface OnCustomClickListener {
		public void onCustomClick(Bitmap bitmap);
	}
	private OnCustomClickListener onConfirmBtnClickListener;

	private PaintView paintView;

	public PaintSignatureDialog(Context context,
			OnCustomClickListener onConfirmBtnClickListener) {
		super(context, R.style.PaintSignatureDialog);
		this.onConfirmBtnClickListener = onConfirmBtnClickListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hd_hse_common_component_dialog_paint_signature);
		paintView = (PaintView) findViewById(R.id.paintview);
		Button okBtn = (Button) findViewById(R.id.ok_btn);	//确定按钮
		Button redoBtn = (Button) findViewById(R.id.redo_btn);	//重写按钮
		Button escBtn = (Button) findViewById(R.id.esc_btn);	//取消按钮
		okBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				onConfirmBtnClickListener.onCustomClick(paintView.getBitmap());
				PaintSignatureDialog.this.dismiss();
			}
		});

		redoBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				paintView.redo();
			}
		});

		escBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				PaintSignatureDialog.this.dismiss();
			}
		});

	}

	@Override
	protected void onStart() {
		super.onStart();
		paintView.redo();
	}
}


