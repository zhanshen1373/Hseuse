package com.hd.hse.common.module.phone.camera;

import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.AutoFocusMoveCallback;
import android.os.Handler;

import com.hd.hse.entity.time.ServerDateManager;

@SuppressWarnings("all")
public class FocusManager implements AutoFocusCallback, AutoFocusMoveCallback {

	public final static String TAG = "FocusManager";
	
    public interface FocusListener {
        public void onFocusStart(boolean smallAdjust);
        public void onFocusReturns(boolean smallAdjust, boolean success);
    }
	
    private int mFocusKeepTimeMs = 3000;
    private long mLastFocusTimestamp = 0;
    
    private Handler mHandler;
    private CameraManager mCameraManager;
    private FocusListener mListener;
    private boolean mIsFocusing;
    
    public void setFocusListener(FocusListener listener){
    	this.mListener = listener;
    }
    
    public FocusManager(CameraManager mCameraManager){
    	mHandler = new Handler();
    	this.mCameraManager = mCameraManager;
    	mIsFocusing = false;
    	
    	mCameraManager.setAutoFocusMoveCallback(this);
    	Camera.Parameters params = mCameraManager.getParameters();
    	if(params.getSupportedFocusModes().contains("auto")){
    		params.setFocusMode("auto");
    	}
    	
    	mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				checkFocus();
			}
		}, 1000);
    }
    

	public void checkFocus() {
		long time = ServerDateManager.getCurrentTimeMillis();
		if(time - mLastFocusTimestamp > mFocusKeepTimeMs && !mIsFocusing){
			refocus();
		}else if(time - mLastFocusTimestamp > mFocusKeepTimeMs * 2){
			refocus();
		}
	}
    
	public void refocus() {
		if(mCameraManager.doAutofocus(this)){
			mIsFocusing = true;
			if(mListener != null){
				mListener.onFocusStart(false);
			}
		}
	}

	@Override
	public void onAutoFocusMoving(boolean start, Camera camera) {
        if (mIsFocusing && !start) {
            mLastFocusTimestamp = ServerDateManager.getCurrentTimeMillis();
        }

        if (start) {
            if (mListener != null) {
                mListener.onFocusStart(true);
            }
        } else {
            if (mListener != null) {
                mListener.onFocusReturns(true, false);
            }
        }
        mIsFocusing = start;
    }

	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		mLastFocusTimestamp = ServerDateManager.getCurrentTimeMillis();
		mIsFocusing = false;
		if(mListener != null){
			mListener.onFocusReturns(false, success);
		}
	}
}
