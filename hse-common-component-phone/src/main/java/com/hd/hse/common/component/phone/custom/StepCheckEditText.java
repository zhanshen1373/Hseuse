/**
 * Project Name:EditTextTest
 * File Name:MyEditText.java
 * Package Name:com.example.edittexttest
 * Date:2014年11月25日
 * Copyright (c) 2014, fulibo@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.component.phone.custom;

import java.util.ArrayList;
import java.util.List;

import com.hd.hse.common.component.phone.util.MyStringUtils;

import android.content.Context;
import android.os.SystemClock;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.EditText;

/**
 * 实现只能编辑字符串中 括号 内部的内容的  EditText，使用该控件必须要调用
 *  setHasBracketsText 方法， 来设置 显示的 Text。
 * 
 * ClassName:StepCheckEditText ().<br/>
 * Date:     2014年11月25日  <br/>
 * @author   fulibo
 * @version  
 * @see 	 
 */
public class StepCheckEditText extends EditText {
	
	private final boolean DEBUG = true;
	private final String TAG = "StepCheckEditText";
	
	List<Integer> left = new ArrayList<Integer>();
	List<Integer> right = new ArrayList<Integer>();
	
	//标记是否调用了  setHasBracketsText 设置文本。
	private boolean isNoBrackets = true;
	// 上次点击事件 抬起的时间。
	private long previousTapUpTime;
	private boolean isSetTextCalled;
	
	public StepCheckEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public StepCheckEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public StepCheckEditText(Context context) {
		super(context);
		init();
	}

	private void init() {
		addTextChangedListener(new TextWatcherImpl());
		
		setFilters(new InputFilter[]{new DigitInputFilter()});
		
		setOnFocusChangeListener(new OnFocusChangeListenerImpl());
	}


	/**
	 * 内部使用函数， 找出字符串中所有的 括号。
	 * findAllBrackets:(). <br/>
	 * date: 2014年9月29日 <br/>
	 *
	 * @author fulibo
	 * @param 
	 */
	private void findAllBrackets(String s){
		if(s.contains("(")){
			//英文括号
			MyStringUtils.findAll(s.toString(), ")", right);
			MyStringUtils.findAll(s.toString(), "(", left);
		}else{
			// 中文括号。
			MyStringUtils.findAll(s.toString(), "）", right);
			MyStringUtils.findAll(s.toString(), "（", left);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_DEL){
			
			if (DEBUG) {
				Log.i(TAG, "删除键被按下了。");
			}
			
			if(!isNoBrackets){
			
				if(!isCursorInBrackets()){
					// 如果光标没在 括号内， 删除键不起作用。
					if (DEBUG) {
						Log.i(TAG, "光标没在括号内，删除键不起作用。");
					}
					return true;
				}
				
				Editable text = getText();
				if(text != null){
					char c = text.charAt(getSelectionStart()-1);
					if(c == '(' || c == '（'){
						return true;
					}
				}
			
			}else{
				return true;
			}
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		System.out.println("onTouchEvent");
		boolean passSystem = true;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			passSystem= true;
			
			// 判断是否为双击，如果是 不传递给父类。
			long duration = SystemClock.uptimeMillis() - previousTapUpTime;
			 if (duration <= ViewConfiguration.getDoubleTapTimeout()) {
				 // 是双击事件。
				 passSystem = false;
			 }
			 previousTapUpTime =  SystemClock.uptimeMillis();
			break;
			
		case MotionEvent.ACTION_MOVE:
			passSystem= false;
			break;
			
		case MotionEvent.ACTION_UP:
			
			passSystem= false;
			break;
		}
		
		if (passSystem) {
			System.out.println("父类执行。");
			super.onTouchEvent(event);
			
			MotionEvent upEvent = MotionEvent.obtain(event);
			upEvent.setAction(MotionEvent.ACTION_UP);
			super.onTouchEvent(upEvent);
		}
		
		//-----------------------------------------------
		if(!isNoBrackets){
			
			if (!isCursorInBrackets()) {
				// 光标没有在 括号里，需要调整一下位置。
				if (DEBUG) {
					Log.i(TAG, "用户点击事件后，光标没在括号内，重置光标位置");
				}
				
				// 找出 距离现在光标位置最近的右括号的位置，将光标设置到这。
				setSelection(right.get(findTheNearestRightBracket()));
			}
		}
		//-----------------------------------------------------
		return true;
	}
	
	/**
	 * 找出距离现在光标位置最近的 右括号的位置。
	 * findTheNearestRightBracket:(). <br/>
	 * date: 2014年10月11日 <br/>
	 *
	 * @author xuxinwen
	 * @return
	 */
	public int findTheNearestRightBracket(){
		int cursor = getSelectionStart();
		ArrayList<Integer> lengths = new ArrayList<Integer>();

		// 记录现在光标位置与每个 右括号的距离。然后找出最小值，将光标位置设置到
		// 这个最小值 的  左边。
		for (int item : right) {
			lengths.add(Math.abs(cursor - item));
		}

		// 找出最小值。
		int len = lengths.size();
		int min = lengths.get(0);
		int minIndex = 0;

		for (int i = 0; i < len; i++) {
			if (lengths.get(i) < min) {
				minIndex = i;
				min = lengths.get(i);
			}
		}

		return minIndex;
	} 
	
	/**
	 * 判断光标是否在两个括号内。
	 * isCursorInBrackets:(). <br/>
	 * date: 2014年10月11日 <br/>
	 *
	 * @author xuxinwen
	 * @return
	 */
	private boolean isCursorInBrackets(){
		
		int cursor = getSelectionStart();
		
		for(int i=0; i<left.size(); i++){
			if(cursor > left.get(i) && cursor <= right.get(i)){
				// 光标 在 某两个 括号内。
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean isTextSelectable() {
		return false;
	}
	
	public interface onEventEndListener {
		public void onEnd(EditText et);
	}
	
	/**
	 * 使用该控件必须要调用的方法。传递的字符串中必须要有括号。
	 * setHasBracketsText:(). <br/>
	 * date: 2014年9月29日 <br/>
	 *
	 * @author xuxinwen
	 * @param text
	 */
	public void setHasBracketsText(String text){
		if(text == null){
			throw new IllegalArgumentException("text不能为空");
		}
		
		isSetTextCalled = true;
		
		if((text.contains("(") || text.contains(")")) 
				&& (text.contains("（") || text.contains("）"))){
			//两种括号都有的　情况．
			setNoBracketsText(text);
			return;
		}
		
		if((!text.contains("(")) && (!text.contains("（"))){
			setNoBracketsText(text);
			return;
		}else{
			// 有括号。
			//设置文本。
			isNoBrackets = false;
			setText(text);
		}
	}
	
	private void setNoBracketsText(String text) {
		isNoBrackets=true;
		setText(text);
	}
	
	private class TextWatcherImpl implements TextWatcher{

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			if(!isNoBrackets){
				findAllBrackets(s.toString());
			}
		}
		
	}
	
	class OnFocusChangeListenerImpl implements OnFocusChangeListener{
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if(!isNoBrackets){
				if(hasFocus){
					setSelection(right.get(0));
				}
			}
		}
	}
	
	private class DigitInputFilter implements InputFilter{

		@Override
		public CharSequence filter(CharSequence source, int start, int end,
				Spanned dest, int dstart, int dend) {
			Log.e("trace", "Comming start...");
			if(isSetTextCalled){
				// 如果是调用了  setText 方法，不进行任何拦截。
				isSetTextCalled = false;
				return null;
			}
			
			
			if(!isNoBrackets){
				// 首先判断光标是否在括号内，如果没有在括号内不可输入。
				if(!isCursorInBrackets()){
					Log.e("trace", "Comming isCursorInBrackets...");
					// 光标没在括号内，不允许输入任何内容。
					return "";
				}
				
//				// 如果输入的是 0 ，判断不能在左括号右边输入。
//				if(TextUtils.equals(source.toString(), "0")){
//					if(dest.charAt(dstart-1) == '(' 
//							|| dest.charAt(dstart-1) == '（' ){
//						return "0";
//					}
//				}
				
				if(source.toString().matches("[A-Za-z0-9,，：:;；。\\.\u4e00-\u9fa5]+")  ){
					//如果是数字，字母，汉字 的话，允许输入。
					
					Log.e("trace", "source:" + source);
					return source;
				}
				
				/*if(TextUtils.isDigitsOnly(source)  ){
					//如果是数字的话，直接返回
					
					return source;
				}*/
				
			}
			
			return "";
			
		}
		
	}
	
}

