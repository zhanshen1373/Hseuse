package com.hd.hse.common.component.phone.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;


public class CustomListView extends ListView {

	/**
	 * isAutoHeight:TODO(是否自使用高度).
	 */
	private boolean autoHeight = false;
	
	/**
	 * setAutoHeight:(设置是否自使用高度). <br/>
	 * date: 2014年11月5日 <br/>
	 *
	 * @author lxf
	 * @param autoHeight
	 */
	public void setAutoHeight(boolean autoHeight){
		this.autoHeight=autoHeight;
	}

	public CustomListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

//	/**
//	 * 重写该方法，达到使ListView适应ScrollView的效果
//	 */
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		// TODO Auto-generated method stub
//		int expandSpec=heightMeasureSpec;
//		if (autoHeight) {
//			 expandSpec = MeasureSpec.makeMeasureSpec(
//					Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//		}
//		super.onMeasure(widthMeasureSpec, expandSpec);
//
//	}

}
