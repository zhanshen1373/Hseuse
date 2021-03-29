/**
 * Project Name:hse-common-module-phone
 * File Name:ZYPOperate.java
 * Package Name:com.hd.hse.common.module.phone.custom
 * Date:2015年1月13日
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.module.phone.custom;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ViewMeasureUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.entity.workorder.WorkOrder;

/**
 * ClassName:ZYPOperate ().<br/>
 * Date:     2015年1月13日  <br/>
 * @author   xuxinwen
 * @version  
 * @see 	 
 */
public class ZYPOperatePopWindow {
	
	private final String TAG = "ZYPOperatePopWindow";
	private final boolean DEBUG = true;
	
	/************************************************/
	//配置信息。
	private final static int CONF_DEFAULT_BUTTON_NUMBER = 1;
	
	/************************************************/
	
	private Context mContext;
	
	//PopupWindow 设置的 View的跟节点。
	private LinearLayout mViewContainer;
	
	private ViewGroup mViewRoot;
	
	private PopupWindow mPopWin;
	
	private ImageView mHeaderPointer;
	private ImageView mFooterPointer;
	
	private int mButtonCount=0;
	
	//指针自己宽度的一半
	private int mPointerSelfHorizontalOffSet;
	
	private List<ButtonHolder> mButtonHolders = new ArrayList<ButtonHolder>();
	
	/*
	 * 需要什么参数：
	 * 用在ListView 上的PopWindow 需要一个 anchorView
	 * 垂直偏移量，水平方向指针的位置。
	 */
	
	public ZYPOperatePopWindow(Context context) {
		super();
		
		mContext = context;
		
		init();
	}

	private void init() {
		
		initUIComponent();
		
		initPopWindow();
	}

	private void initPopWindow() {
		/*	窗口的初始化	*/
		mPopWin = new PopupWindow(mViewRoot);
		mPopWin.setOutsideTouchable(true);
		mPopWin.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//		mPopWin.setFocusable(true);
		mPopWin.setBackgroundDrawable(new BitmapDrawable());
	}

	class ButtonHolder{
		FrameLayout frame;
		TextView content;
	}
	
	private void initUIComponent() {
		/*	组件的初始化	*/ 
		mViewRoot = (ViewGroup) View.inflate(mContext, R.layout.hse_common_module_phone_zyplist_popup1, null);
		mViewContainer = (LinearLayout) mViewRoot.findViewById(R.id.hd_hse_common_module_phone_zyplist_pop_container);
		mHeaderPointer = (ImageView) mViewRoot.findViewById(R.id.hse_common_module_phone_zyplist_pop_header_pointer);
		mFooterPointer = (ImageView) mViewRoot.findViewById(R.id.hse_common_module_phone_zyplist_pop_footer_pointer);
		
		//默认初始化  button，因为在实际使用中，可能两个 button出现的机会最大
		//所以默认情况下初始化 2个button。
		for(int i=0; i<CONF_DEFAULT_BUTTON_NUMBER; i++){
			
			if(i>0){
				appendView();
			}else{
				// 添加第一个 View，前边没有分隔线。
				View _view = View.inflate(mContext, R.layout.hse_common_module_phone_pop_button, null);
				final ButtonHolder _holder = new ButtonHolder();
				_holder.frame = (FrameLayout) _view.findViewById(R.id.hd_hse_common_module_phone_pop_button_frame);
				_holder.content = (TextView) _view.findViewById(R.id.hd_hse_common_module_phone_pop_button_content);
				mButtonHolders.add(_holder);
				
				_view.setLayoutParams(generateLayoutParms());
				mViewContainer.addView(_view);
				mButtonCount++;
			}
			
		}
		
		caclPointerSelfOffSet();
	}
	
	/**
	 * 添加一个带分隔线的View
	 * appendView:(). <br/>
	 * date: 2015年1月14日 <br/>
	 *
	 * @author xuxinwen
	 */
	private void appendView(){
		View _view = View.inflate(mContext, R.layout.hse_common_module_phone_pop_button, null);
		
		final ButtonHolder _holder = new ButtonHolder();
		_holder.frame = (FrameLayout) _view.findViewById(R.id.hd_hse_common_module_phone_pop_button_frame);
		_holder.content = (TextView) _view.findViewById(R.id.hd_hse_common_module_phone_pop_button_content);
		mButtonHolders.add(_holder);
		
		_view.setLayoutParams(generateLayoutParms());
		//添加分隔线
		mViewContainer.addView(generateDivider());
		// 添加View
		mViewContainer.addView(_view);
		mButtonCount++;
	}
	
	private void removeLastView(){
		/* 执行前条件判断  */
		if(mViewContainer.getChildCount() < 3){
			return;
		}
		
		
	}
	
	private LinearLayout.LayoutParams generateLayoutParms(){
		LinearLayout.LayoutParams _result = new LinearLayout.LayoutParams(0, 
				LayoutParams.WRAP_CONTENT, 1);
		
		return _result;
	}
	
	private View generateDivider(){
		View _result = new TextView(mContext);
		
		LinearLayout.LayoutParams _lp = new LinearLayout.LayoutParams(
				(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, mContext.getResources().getDisplayMetrics()), 
				LayoutParams.MATCH_PARENT);
		_result.setLayoutParams(_lp);
		
		_result.setBackgroundResource(R.color.hd_hse_common_module_pop_divider);
		
		return _result;
	}
	
	private void caclPointerSelfOffSet() {
		mPointerSelfHorizontalOffSet = ViewMeasureUtils.getMeasuredWidth(mFooterPointer)/2;
	}
	
	private WorkOrder mWorkOrder;
	
	/**
	 * 
	 * show:(). <br/>
	 * date: 2015年1月13日 <br/>
	 *
	 * @author xuxinwen
	 * 
	 * @param anchorView 弹出的 PopupWindow 所依附的View，如果verticalOffSet为0，PopupWindow
	 * 					会贴着anchorView的下边界弹出。
	 * 
	 * @param verticalOffSet  垂直方向的偏移量。PopupWindow与anchorView长度相同，垂直方向可以改变
	 * 					，默认是贴着anchorView的下边界弹出。
	 * 
	 * @param pointTo	PopupWindow中有指针，这个值指定指针的水平偏移位置，
	 * 
	 * @param asDropDown   决定PopupWindow是向上弹还是向下弹。
	 */
	@SuppressLint("NewApi")
	public void show(View anchorView, int verticalOffSet, int pointTo, PopWinButton[] buttons, WorkOrder workOrder){
		mWorkOrder = workOrder;
		
		// 取得 anchorView 的宽度，设置给 PopupWindow
		final int _width = anchorView.getWidth();
		mPopWin.setWidth(_width);
		
		// 根据 PopWinButton 数组来初始化Button
		
		// 获得需要几个 Button
		final int _len = buttons.length;
		
		// 比较需要的与已经添加进来的Button的数量关系
		if(_len > mButtonCount){
			// 如果需要的数量大于已有的数量
			final int _shortCount = _len-mButtonCount;
			
			// 循环添加不足的控件
			for(int i=0; i<_shortCount; i++){
				appendView();
			}
		}else {
			final int _overCount = mButtonCount-_len;
			for (int i = 0; i < _overCount; i++) {
				//移除控件
				mViewContainer.removeView(mViewContainer.getChildAt(mViewContainer.getChildCount()-1));
				//移除分割线
				mViewContainer.removeView(mViewContainer.getChildAt(mViewContainer.getChildCount()-1));
				//移除viewholder
				mButtonHolders.remove(mButtonHolders.size()-1);
				mButtonCount--;
			}
			
		}
		
		//给每个 Button 设置相应的展示数据
		for(int i=0; i<_len; i++){
			final ButtonHolder _holder = mButtonHolders.get(i); 
			final PopWinButton _button = buttons[i];
			
			//设置 Button的图片
			_holder.content.setCompoundDrawablesRelativeWithIntrinsicBounds(_button.drawableId, 0, 0, 0);
			// 设置 Button的文字
			_holder.content.setText(_button.text);
			// 设置 Button的文字大小
			_holder.content.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimension(R.dimen.text_size_large));
			
			_holder.frame.setOnClickListener(new ClickListener(_button.eventType));
		}
		
		// 计算 PopWindow 的高度， 第一次测量有问题，可能是图片没设置进来的问题
		final int _popupWindowHeight = ViewMeasureUtils.getMeasuredHeight(mViewRoot); 
		
		// 决定 是向上还是向下弹
		if(anchorView.getTop() < _popupWindowHeight){
			// 向下弹
			
			// 调整 指针
			mFooterPointer.setVisibility(View.GONE);
			mHeaderPointer.setVisibility(View.VISIBLE);
			
			adjustPointerPosition(pointTo, mHeaderPointer);
			
			mPopWin.showAsDropDown(anchorView, 0, verticalOffSet
					-mContext.getResources().getDimensionPixelOffset(R.dimen.hd_hse_common_module_phone_zyplist_row_paddingBottom	//被依附控件的 paddingBottom
							)); 
			
		}else{
			// 向上弹
			
			// 调整 指针
			mFooterPointer.setVisibility(View.VISIBLE);
			mHeaderPointer.setVisibility(View.GONE);
			
			adjustPointerPosition(pointTo, mFooterPointer);
			
			mPopWin.showAsDropDown(anchorView, 0, 
					-(anchorView.getHeight()	//依附的控件的高度
							+_popupWindowHeight		// PopupWindow 自身的高度 
							+verticalOffSet		// 外部传递进来的偏移量。
							-mContext.getResources().getDimensionPixelOffset(R.dimen.hd_hse_common_module_phone_zyplist_row_paddingTop) //被依附控件的 paddingTop
							-((BaseZYPListRow)anchorView).getSelectIconRadiu()
							)
							);
		}
		
	}

	private void adjustPointerPosition(int pointTo, ImageView pointerView) {
		Matrix _matrix = new Matrix();
		_matrix.setTranslate(pointTo-mPointerSelfHorizontalOffSet, 0);
		pointerView.setImageMatrix(_matrix);
	}
	
	public boolean isShowing(){
		return mPopWin.isShowing();
	}
	
	public void dimiss(){
		mPopWin.dismiss();
	}
	
	class ClickListener implements OnClickListener{
		
		private int mEventType;
		
		public ClickListener(int eventType){
			mEventType = eventType;
		}
		
		@Override
		public void onClick(View v) {
			if(mEventListener != null){
				try {
					mEventListener.eventProcess(mEventType, mWorkOrder);
				} catch (HDException e) {
					//TODO
				}
			}
		}
	}
	
	private IEventListener mEventListener;
	
	public void setEventListener(IEventListener listener){
		mEventListener = listener;
	}
}

