package com.hd.hse.common.component.phone.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 
 * @author lych
 * @date 2015-1-12
 * 
 */
public class ProcessLine extends View {
	private Paint paint;
	/** 圆圈颜色 */
	private int circleColor;
	/** 上线颜色 */
	private int upColor;
	/** 下线颜色 */
	private int downColor;
	/** 是否有虚线 */
	private boolean isDotted;

	/** 画圈还是线,true是画圈，false是画线 */
	private boolean isB;

	public ProcessLine(Context context) {
		this(context, null);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ProcessLine(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.paint = new Paint();
		this.paint.setAntiAlias(true); // 消除锯齿
		// 默认颜色
		// circleColor = getResources().getColor(Color.GREEN);
		// upColor = getResources().getColor(Color.GREEN);
		// downColor = getResources().getColor(Color.GREEN);
		circleColor = Color.GRAY;
		upColor = Color.WHITE;
		downColor = Color.GRAY;
	}

	/**
	 * @param circleColor
	 * @param upColor
	 * @param downColor
	 * @param isDotted
	 */
	public void setColor(int circleColor, int upColor, int downColor,
			boolean isDotted) {
		this.circleColor = circleColor;
		this.upColor = upColor;
		this.downColor = downColor;
		this.isDotted = isDotted;
	}

	/**
	 * @param circleColor
	 * @param upColor
	 * @param downColor
	 */
	public void setColor(int circleColor, int upColor, int downColor) {
		this.circleColor = circleColor;
		this.upColor = upColor;
		this.downColor = downColor;
		this.isDotted = false;
	}

	public void setDrawModel(boolean b) {
		isB = b;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// 返回View的宽度
		int width = getWidth();
		Log.i("MyApp", "" + getWidth());
		// 返回View的高度
		int height = getHeight();
		Log.i("MyApp", "" + getHeight());
		if (isB) {

			// 画上线
			paint.setColor(upColor);
			// 设置线条宽度
			paint.setStrokeWidth(5);
			// //
			canvas.drawLine(width / 2, 0, width / 2, height / 2 - 15, paint);

			// 画下线
			paint.setColor(downColor);
			if (isDotted) {
				canvas.drawLine(width / 2, height / 2 - 20, width / 2,
						height / 2, paint);
				Paint paint1 = new Paint();
				paint1.setStyle(Paint.Style.STROKE);
				paint1.setColor(downColor);
				Path path = new Path();
				path.moveTo(width / 2, height / 2);
				path.lineTo(width / 2, height);
				PathEffect effects = new DashPathEffect(new float[] { 5, 5, 5,
						5 }, 1);
				paint1.setPathEffect(effects);
				canvas.drawPath(path, paint1);
			} else {
				canvas.drawLine(width / 2, height / 2 - 15, width / 2, height,
						paint);
			}

			// 画圆圈
			paint.setColor(circleColor);
			// canvas.drawCircle(width / 2, height / 2 - 20, 16, paint);
			canvas.drawCircle(width / 2, getHeight() / 2, 16, paint);
			paint.setColor(Color.WHITE);
			canvas.drawCircle(width / 2, getHeight() / 2, 12, paint);
			paint.setColor(circleColor);
			// canvas.drawCircle(width / 2, height / 2 - 20, 16, paint);
			canvas.drawCircle(width / 2, getHeight() / 2, 6, paint);

		} else {

			// 颜色
			paint.setColor(upColor);
			// 设置线条宽度
			paint.setStrokeWidth(5);
			//	画一条线
			canvas.drawLine(width / 2, 0, width / 2, height, paint);
		}

		super.onDraw(canvas);
	}
}
