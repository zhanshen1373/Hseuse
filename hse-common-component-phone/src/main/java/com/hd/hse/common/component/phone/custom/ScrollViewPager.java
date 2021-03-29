package com.hd.hse.common.component.phone.custom;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * 该ViewPager主要用于解决ScrollView与ViewPager的滑动冲突
 * 
 * Created by wangdanfeng on 2016年3月1日
 */
public class ScrollViewPager extends ViewPager {
	 /** 触摸时按下的点 **/  
    PointF downP = new PointF();  
    /** 触摸时当前的点 **/  
    PointF curP = new PointF();  
	public ScrollViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downP.x=event.getX();
			downP.y=event.getY();
			break;

		case MotionEvent.ACTION_MOVE:
			  //此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰  
			curP.x=event.getX();
			curP.y=event.getY();
			if(Math.abs(curP.x-downP.x)>=Math.abs(curP.y-downP.y)){
			    getParent().requestDisallowInterceptTouchEvent(true);  
			}else {
				 getParent().requestDisallowInterceptTouchEvent(false);  
			}
        
			break;
		case MotionEvent.ACTION_UP:

			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}
//	   @Override
//	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		   int height = 0;
//	        //下面遍历所有child的高度
//	        for (int i = 0; i < getChildCount(); i++) {
//	            View child = getChildAt(i);
//	            child.measure(widthMeasureSpec,
//	                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//	            int h = child.getMeasuredHeight();
//	            if (h > height) //采用最大的view的高度。
//	                height = h;
//	        }
//	 
//	        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
//	                MeasureSpec.EXACTLY);
//	 
//	        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//	    }
}
