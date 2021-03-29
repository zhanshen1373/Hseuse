/**
 * 
 */
package com.hd.hse.main.phone.ui.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.SystemApplication;
import com.hd.hse.constant.IActionType;
import com.hd.hse.main.business.main.MainActionListener;
import com.hd.hse.system.SystemProperty;

/**
 * 启动MainActivity时，创建LockService实例
 * 
 * @author liyich
 * 
 */
public class LockService extends Service {

	/*
	 * 
	 */
	private boolean mIsLock = false;
	/*
	 * 锁屏间隔时间
	 */
	private int mExpectedCount;
	/*
	 * 锁屏间隔时间
	 */
	private int mCount;

	private boolean hasReceviersRegistered;

	/*
	 * 成员变量
	 */
	private BroadcastReceiver mReceiverLock = new BroadcastReceiver() {

		@SuppressLint("ShowToast")
		@Override
		public void onReceive(Context context, Intent intent) {
			// ACTION_OFF lock
			mIsLock = true;
			Log.i("MyLockService", "Lock screen");

		}
	};

	/*
	 * 成员变量
	 */
	private BroadcastReceiver mReceiverUnlock = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i("MyLockService", "Unlock screen");
			// unlock
			mCount = mExpectedCount;
			mIsLock = false;
		}
	};

	/*
	 * 成员变量
	 */
	private BroadcastReceiver mReceiverCount = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// if the screen is locked.
			Log.i("MyLockService", " mReceiverCount code and " + "value is "
					+ mCount);
			if (mIsLock) {

				mCount--;
				// if reach the expected time,exit main-app
				if (mCount <= 0) {
					// stop the service
					LockService.this.stopSelf();
					// exit the main app
					MainActionListener actionListener = new MainActionListener();
					BusinessAction action = new BusinessAction(actionListener);
					try {
						// 后台业务处理
						action.action(IActionType.MAIN_LOGOUT);
						// 前台处理
						SystemApplication.getInstance().exit();
					} catch (HDException e) {
						// TODO Auto-generated catch block
					}
					Log.i("MyLockService", "Exit successfully and "
							+ "the value is " + mCount);
				}
			}
		}

	};

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("MyLockService", "startCommand");
		// register receiver
		if (intent == null) {
			// 被系统启动了，把自己关掉
			stopService(new Intent(this, this.getClass()));
			hasReceviersRegistered = false;
			return 0;
		}

		Float f = SystemProperty.getTimeoutExiteTime();
		mExpectedCount = (int) f.floatValue();
		mCount = mExpectedCount;

		// register lockFilter
		IntentFilter lockFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		lockFilter.setPriority(1000);
		this.registerReceiver(mReceiverLock, lockFilter);

		// register unLcokFilter
		IntentFilter unlockFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		unlockFilter.setPriority(1000);
		this.registerReceiver(mReceiverUnlock, unlockFilter);

		// register countFilter
		IntentFilter countFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
		countFilter.setPriority(1000);
		this.registerReceiver(mReceiverCount, countFilter);

		hasReceviersRegistered = true;
		Log.i("MyLockService", "register successfully");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		if (hasReceviersRegistered) {
			unregisterReceiver(mReceiverLock);
			unregisterReceiver(mReceiverUnlock);
			unregisterReceiver(mReceiverCount);
			Log.i("MyLockService", "unregisterReceiver");
		}
		super.onDestroy();
	}
}
