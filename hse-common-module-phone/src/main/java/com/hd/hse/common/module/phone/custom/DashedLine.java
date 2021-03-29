/**
 * Project Name:hse-common-module-phone
 * File Name:DashedLine.java
 * Package Name:com.hd.hse.common.module.custom
 * Date:2015�?�?�?
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 *
*/

package com.hd.hse.common.module.phone.custom;

import com.hd.hse.common.module.phone.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * ClassName:DashedLine ().<br/>
 * Date:     2015�?�?�? <br/>
 * @author   xuxinwen
 * @version  
 * @see 	 
 */
public class DashedLine extends View {

	private final String TAG = "DashedLine";
	private final boolean DEBUG = true;
	
	private int mLineColor;
	private float mLineWidth;
	
	public DashedLine(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		getAttrs(context, attrs);
		
		init();
	}

	public DashedLine(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		getAttrs(context, attrs);
		
		init();
	}

	public DashedLine(Context context) {
		super(context);
		init();
	}

	private void getAttrs(Context context, AttributeSet attrs) {
		TypedArray _ta = context.obtainStyledAttributes(attrs,R.styleable.hse); 
		mLineColor = _ta.getColor(R.styleable.hse_lineColor, Color.DKGRAY); 
		mLineWidth = _ta.getDimension(R.styleable.hse_lineWidth, 3);
		mOffLength = _ta.getDimension(R.styleable.hse_lineOffLength, 5);
		mOnLength = _ta.getDimension(R.styleable.hse_lineOnLength, 5);
		
		_ta.recycle();
	}
	
	private void init() {
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mLineColor);
        mPaint.setStrokeWidth(mLineWidth);
		
        mPath = new Path();  
        PathEffect effects = new DashPathEffect(new float[]{mOnLength, mOffLength},2);
        mPaint.setPathEffect(effects);
	}
	
	private float mOnLength;
	private float mOffLength;
	private Paint mPaint;
	private Path mPath;
  
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        // 控件的高度
        int _y = getHeight()/2;
        int _x = getWidth();
        mPath.moveTo(0, _y);
        mPath.lineTo(_x, _y);   
        canvas.drawPath(mPath, mPaint);
    }
}

