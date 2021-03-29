/**
 * Project Name:hse-test-demo
 * File Name:DrawEditText.java
 * Package Name:com.example.hse_test_demo
 * Date:2015年3月4日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.component.phone.custom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.hd.hse.common.component.phone.R;

/**
 * ClassName:DrawEditText (重写编辑框，实现个性化显示).<br/>
 * Date: 2015年3月4日 <br/>
 *
 * @author zhaofeng
 * @version
 * @see
 */
public class DrawEditText extends EditText {

	private int radiusX = 5; // 圆角矩形的X轴圆角半径
	private int radiusY = 5; // 圆角矩形的轴圆角半径
	private int color = 0; // 背景颜色
	private Paint paint = null; // 背景画笔
	private int drawbackWidth = 0; // 所画背景的宽度
	private Drawable leftDrawable = null; // 左Drawable
	private Drawable rightDrawable = null; // 右Drawable
	private Bitmap leftBitmap = null; // 左Bitmap
	private float touchX = 0.0f; // 触摸控件的下拉图标的位置

	/**
	 * iTouchOnClick:TODO(监听接口).
	 */
	private ITouchOnClick iTouchOnClick;

	public void setiTouchOnClick(ITouchOnClick iTouchOnClick) {
		this.iTouchOnClick = iTouchOnClick;
	}

	/**
	 * iOnLayoutEvent:TODO(布局监听接口).
	 */
	private IOnLayoutEvent iOnLayoutEvent;

	public void setiOnLayoutEvent(IOnLayoutEvent iOnLayoutEvent) {
		this.iOnLayoutEvent = iOnLayoutEvent;
	}

	/**
	 * Creates a new instance of DrawEditText.
	 *
	 * @param context
	 */
	public DrawEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initAttribute();
	}

	public DrawEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initAttribute();
	}

	public DrawEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initAttribute();
	}

	/**
	 * TODO 绘制背景颜色
	 *
	 * @see android.widget.TextView#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (drawbackWidth != 0) {
			if (paint == null) {
				paint = new Paint();
				paint.setAntiAlias(true);
				paint.setColor(color);
			}
			// 画宽度为drawbackWidth的背景色
			RectF rect = new RectF(0.0f, 0.0f, drawbackWidth * 1.0f,
					getHeight() * 1.0f);
			canvas.drawRoundRect(rect, radiusX, radiusY, paint);
			canvas.drawRect(radiusX, 0, drawbackWidth, getHeight(), paint);
			canvas.drawBitmap(leftBitmap, 0, 0, paint);
			canvas.save();
			canvas.restore();
		}

	}

	/**
	 * initAttribute:(初始化控件属性). <br/>
	 * date: 2015年3月4日 <br/>
	 *
	 * @author zhaofeng
	 */
	private void initAttribute() {
		Drawable[] drawables = getCompoundDrawables();
		if (drawables[0] != null) {
			leftDrawable = drawables[0];
			drawbackWidth = drawables[0].getIntrinsicWidth();
			leftBitmap = drawableToBitmap(leftDrawable);

			Resources resources = getResources();
			//
			color = resources.getColor(R.color.hd_hse_login_edite_back_color);
			//
			// radiusX =
			// Float.floatToIntBits(radiusX*resources.getDisplayMetrics().density);
			// radiusY =
			// Float.floatToIntBits(radiusY*resources.getDisplayMetrics().density);

			invalidate();
		}

		if (drawables[2] != null) {
			rightDrawable = drawables[2];
		}
	}

	/**
	 * TODO 测量控件的大小事件
	 *
	 * @see android.widget.TextView#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (rightDrawable != null) {
			touchX = getMeasuredWidth() - rightDrawable.getIntrinsicWidth();
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
							int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		if (iOnLayoutEvent != null)
			iOnLayoutEvent.onLayoutListener(this);
	}

	/**
	 * drawableToBitmap:(将Drawable转化成Bitmap). <br/>
	 * date: 2015年3月4日 <br/>
	 *
	 * @author zhaofeng
	 * @param drawable
	 * @return
	 */
	public Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;

	}

	/**
	 * TODO 触摸事件
	 *
	 * @see android.widget.TextView#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (iTouchOnClick == null) {
			return super.onTouchEvent(event);
		}
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// 按下
				if (touchX <= event.getX()) {
					// 弹出历史记录人
					iTouchOnClick.touchOnClick(this);
					if (0 > 1) {
						Log.e("下拉菜单测试----", "测试成功！");
					}
					return false;
				}
				break;

			case MotionEvent.ACTION_UP:
				// 抬起
				break;

			case MotionEvent.ACTION_MOVE:
				// 移动
				break;
			default:
				break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * ClassName: ITouchOnClick (接口类)<br/>
	 * date: 2015年3月4日 <br/>
	 *
	 * @author zhaofeng
	 * @version DrawEditText
	 */
	public interface ITouchOnClick {
		/**
		 * touchOnClick:(接口方法). <br/>
		 * date: 2015年3月4日 <br/>
		 *
		 * @author zhaofeng
		 * @param view
		 */
		public void touchOnClick(View view);
	}

	/**
	 * ClassName: IOnLayoutEvent (监听控件布局接口)<br/>
	 * date: 2015年5月5日 <br/>
	 *
	 * @author zhaofeng
	 * @version DrawEditText
	 */
	public interface IOnLayoutEvent {
		/**
		 * onLayoutListener:(监听控件布局方法). <br/>
		 * date: 2015年5月5日 <br/>
		 *
		 * @author zhaofeng
		 */
		public void onLayoutListener(View view);
	}

}
