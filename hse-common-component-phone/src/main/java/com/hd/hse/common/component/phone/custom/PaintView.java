/**
 * Project Name:hse-common-component-phone
 * File Name:PaintView.java
 * Package Name:com.hd.hse.common.component.phone.custom
 * Date:2015年10月8日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.common.component.phone.custom;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * ClassName:PaintView ().<br/>
 * Date: 2015年10月8日 <br/>
 * 
 * @author LiuYang
 * @version
 * @see
 */
public class PaintView extends View {
	private Paint mPaint;

	private Bitmap mBitmap;
	private Canvas mCanvas;
	private Path mPath;
	private Paint mBitmapPaint;
	private int w, h;
	private boolean hasDraw = false;

	public PaintView(Context context) {
		super(context);
	}

	public PaintView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPath = new Path();
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(0xFF000000);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(10);
	}

	public PaintView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		this.w = w;
		this.h = h;
		mBitmap = creartNewBitmap(w, h);
		mCanvas = new Canvas(mBitmap);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(0x00000000);

		canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

		canvas.drawPath(mPath, mPaint);
	}

	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 4;

	private void touch_start(float x, float y) {
		mPath.reset();
		mPath.moveTo(x, y);
		mX = x;
		mY = y;
	}

	private void touch_move(float x, float y) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			mX = x;
			mY = y;
		}
	}

	private void touch_up() {
		mPath.lineTo(mX, mY);
		// commit the path to our offscreen
		mCanvas.drawPath(mPath, mPaint);
		// kill this so we don't double draw
		mPath.reset();
		hasDraw = true;
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touch_start(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			touch_move(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			touch_up();
			invalidate();
			break;
		}
		return true;
	}

	/**
	 * 放弃重画
	 */
	public void redo() {
		if (w != 0 && h != 0) {
			mBitmap = creartNewBitmap(w, h);
			mCanvas = new Canvas(mBitmap);
			invalidate();
		}
	}

	private Bitmap creartNewBitmap(int w, int h) {
		Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		int[] pix = new int[w * h];
		// 给bitmap创建一个白色的背景色，防止压缩的时候透明色被压缩成黑色
		for (int y = 0; y < h; y++)
			for (int x = 0; x < w; x++) {
				int index = y * w + x;
				int r = ((pix[index] >> 16) & 0xff) | 0xff;
				int g = ((pix[index] >> 8) & 0xff) | 0xff;
				int b = (pix[index] & 0xff) | 0xff;
				pix[index] = 0xff000000 | (r << 16) | (g << 8) | b;
			}
		bitmap.setPixels(pix, 0, w, 0, 0, w, h);
		return bitmap;
	}

	/**
	 * getBitmap:(获取绘制的图像). <br/>
	 * date: 2015年10月8日 <br/>
	 * 
	 * @author LiuYang
	 * @return
	 */
	public Bitmap getBitmap() {
		return comp(mBitmap);
		// return mBitmap;
	}

	public boolean hasDraw() {
		return this.hasDraw;
	}

	private Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		float hh = 200f;// 这里设置高度为200f
		float ww = 300f;// 这里设置宽度为300f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		int bew = 1, beh = 1;
		if (w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			bew = (int) (w / ww);
		}
		if (h > hh) {// 如果高度高的话根据宽度固定大小缩放
			beh = (int) (h / hh);
		}
		be = beh < bew ? bew : beh;
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	private Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}
}
