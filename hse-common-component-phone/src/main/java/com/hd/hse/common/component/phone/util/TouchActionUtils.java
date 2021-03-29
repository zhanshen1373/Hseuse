package com.hd.hse.common.component.phone.util;

import android.os.SystemClock;
import android.view.ViewConfiguration;

public class TouchActionUtils {
	private long mLastDownTStamp;
	
	/**
	 * 该方法在接受到 DOWN 事件后调用，会返回这次点击是否造成了
	 * 双击事件。此方法的判断完全依据时间间隔，所以不必传递任何参数
	 * @return
	 */
	public boolean isDoubleTap() {
		boolean _result = false;

		// 判断是否为双击，如果是 不传递给父类。
		long duration = SystemClock.uptimeMillis() - mLastDownTStamp;
		if (duration <= ViewConfiguration.getDoubleTapTimeout()) {
			// 是双击事件。
			_result = true;
		}
		mLastDownTStamp = SystemClock.uptimeMillis();

		return _result;
	}
}
