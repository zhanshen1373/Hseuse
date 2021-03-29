package com.hd.hse.common.component.phone.custom;

import com.hd.hse.common.component.phone.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ClassName: CustomDialog (自定义提示框)<br/>
 * date: 2014年9月9日 <br/>
 * 
 * @author lxf
 * @version
 */
public class MessageDialog extends Dialog {

	public MessageDialog(Context context) {
		super(context);
	}

	public MessageDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private DialogInterface.OnClickListener positiveButtonClickListener;
		private DialogInterface.OnClickListener negativeButtonClickListener;
		
		private TextView messageTv;
		private Button positiveButton;
		private Button negativeButton;
		private TextView titleTv;

		public Builder(Context context) {
			this.context = context;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		/**
		 * Set the Dialog message from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}

		/**
		 * Set the Dialog title from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		/**
		 * Set the Dialog title from String
		 * 
		 * @param title
		 * @return
		 */

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		/**
		 * Set the positive button resource and it's listener
		 * 
		 * @param positiveButtonText
		 * @return
		 */
		public Builder setPositiveButton(int positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(int negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(String negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}

		/**
		 * create:(不带任何标识的提示框). <br/>
		 * date: 2014年9月9日 <br/>
		 * 
		 * @author lxf
		 * @return
		 */
		public MessageDialog create() {
			View layout = getLayout();
//			setTextViewDrawable(R.drawable.hd_hse_phone_message_true);
			return createinfo(layout);
		}
		
		/**
		 * createSuccess:(完成提示). <br/>
		 * date: 2015年1月5日 <br/>
		 *
		 * @author zhaofeng
		 * @return
		 */
		public MessageDialog createSuccess(){
			View layout = getLayout();
			setTextViewDrawable(R.drawable.hd_hse_phone_message_true);
			return createinfo(layout);
		}

		/**
		 * createWarm:(显示带警告的提示框). <br/>
		 * date: 2014年9月9日 <br/>
		 * 
		 * @author lxf
		 * @return
		 */
		public MessageDialog createWarm() {

			View layout = getLayout();
			setTextViewDrawable(R.drawable.hd_hse_phone_message_warn);
			return createinfo(layout);
		}

		/**
		 * createError:(显示带错误的提示框). <br/>
		 * date: 2014年9月9日 <br/>
		 * 
		 * @author lxf
		 * @return
		 */
		public MessageDialog createError() {
			View layout = getLayout();
			setTextViewDrawable(R.drawable.hd_hse_phone_message_error);
			return createinfo(layout);
		}

		/**
		 * setTextViewLeftDraw:(设置图片). <br/>
		 * date: 2015年1月5日 <br/>
		 *
		 * @author zhaofeng
		 * @param resId
		 */
		private void setTextViewDrawable(int resId){
			Drawable left = context.getResources().getDrawable(resId);
			left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());;
			messageTv.setCompoundDrawables(left, null, null, null);
		}
		/**
		 * getLayout:(获取布局文件). <br/>
		 * date: 2014年9月9日 <br/>
		 * 
		 * @author lxf
		 * @return
		 */
		private View getLayout() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(
					R.layout.hd_hse_phone_common_dialog_layout, null);
			messageTv = (TextView)layout.findViewById(R.id.hd_hse_phone_message_tv);
			positiveButton = (Button)layout.findViewById(R.id.hd_hse_phone_message_sure_b);
			negativeButton = (Button)layout.findViewById(R.id.hd_hse_phone_message_cancel_b);
			titleTv = (TextView)layout.findViewById(R.id.hd_hse_phone_message_title);
			return layout;
		}

		/**
		 * createinfo:(创建Dialog对象). <br/>
		 * date: 2014年9月9日 <br/>
		 * 
		 * @author lxf
		 * @param layout
		 * @return
		 */
		private MessageDialog createinfo(View layout) {
			// instantiate the dialog with the custom Theme
			final MessageDialog dialog = new MessageDialog(context,
					R.style.Dialog);

			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			// set the dialog title
			titleTv.setText(title);
			// set the confirm button
			if (positiveButtonText != null) {
				positiveButton
						.setText(positiveButtonText);
				if (positiveButtonClickListener != null) {
					positiveButton
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									positiveButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_POSITIVE);
								}
							});
				}
			} else {
				positiveButton.setVisibility(
						View.GONE);
			}
			// set the cancel button
			if (negativeButtonText != null) {
				negativeButton
						.setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					negativeButton
							.setOnClickListener(new View.OnClickListener() {
								public void onClick(View v) {
									negativeButtonClickListener.onClick(dialog,
											DialogInterface.BUTTON_NEGATIVE);
								}
							});
				}
			} else {
				// if no confirm button just set the visibility to GONE
				negativeButton.setVisibility(
						View.GONE);
			}
			// set the content message
			if (message != null) {
				messageTv.setText(message);
			} else if (contentView != null) {
				// if no message set
				// add the contentView to the dialog body
//				((LinearLayout) layout.findViewById(R.id.message))
//						.removeAllViews();
//				((LinearLayout) layout.findViewById(R.id.message)).addView(
//						contentView, new LayoutParams(
//								LayoutParams.WRAP_CONTENT,
//								LayoutParams.WRAP_CONTENT));
			}
			WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
			DisplayMetrics outMetrics = new DisplayMetrics();
			dialog.getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
			lp.width = outMetrics.widthPixels*3/4;
//			lp.height = outMetrics.heightPixels*3/4;
			dialog.getWindow().setAttributes(lp);
			dialog.setContentView(layout);
			return dialog;
		}
	}
}
