package com.hd.hse.common.module.phone.camera;

import com.hd.hse.common.module.phone.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class FocusHudRing extends HudRing {
	private CameraManager mCamManager;
	private FocusManager mFocusManager;

	public FocusHudRing(Context context) {
		super(context);
	}

	public FocusHudRing(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FocusHudRing(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		setFocusImage(true);
	}

	public void setFocusImage(boolean success) {
		if (success) {
			setImageResource(R.drawable.hud_focus_ring_success);
		} else {
			setImageResource(R.drawable.hud_focus_ring_fail);
		}
	}

	public void setManagers(CameraManager camMan, FocusManager focusMan) {
		mCamManager = camMan;
		mFocusManager = focusMan;
	}

	public void setPosition(float x, float y) {
		setX(x - getWidth() / 2.0f);
		setY(y - getHeight() / 2.0f);
		applyFocusPoint();
	}

	private void applyFocusPoint() {
		ViewGroup parent = (ViewGroup) getParent();
		if (parent == null)
			return;

		float centerPointX = getY() + getHeight() / 2.0f;
		float centerPointY = parent.getWidth() - (getX() + getWidth() / 2.0f);

		centerPointX *= 1000.0f / parent.getHeight();
		centerPointY *= 1000.0f / parent.getWidth();

		centerPointX = (centerPointX - 500.0f) * 2.0f;
		centerPointY = (centerPointY - 500.0f) * 2.0f;

		if (mCamManager != null) {
			mCamManager.setFocusPoint((int) centerPointX, (int) centerPointY);
		}
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		super.onTouch(view, motionEvent);

		if (motionEvent.getActionMasked() == MotionEvent.ACTION_UP) {
			applyFocusPoint();

			if (mFocusManager != null) {
				mFocusManager.refocus();
			}
		}

		return true;
	}
}
