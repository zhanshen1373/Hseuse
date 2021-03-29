package com.hd.hse.dly.phone.ui.event.homepage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.LocationSwCard;
import com.hd.hse.common.module.phone.ui.event.homepage.AbstractAppModule;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.dly.phone.ui.activity.worktask.TaskTabulationActivity;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.system.SystemProperty;

public class WorkDelayApp extends AbstractAppModule {
	public String currentName = "现场复查";

	public WorkDelayApp(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void appModuleOnClick(AppModule aModule) throws HDException {
		super.appModuleOnClick(aModule);
		Activity parent = (Activity) getParent();
		Intent intent = new Intent(parent, LocationSwCard.class);
		Bundle mBundle = new Bundle();
		// 传递目标跳转activity类
		mBundle.putSerializable(LocationSwCard.SER_KEY_TARGETCLASS,
				TaskTabulationActivity.class);
		intent.putExtras(mBundle);
		parent.startActivity(intent);
	}
}
