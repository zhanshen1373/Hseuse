package com.hd.hse.osr.phone.ui.event.homepage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.LocationSwCard;
import com.hd.hse.common.module.phone.ui.event.homepage.AbstractAppModule;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.osr.phone.ui.activity.worktask.TaskTabulationActivity;
import com.hd.hse.system.SystemProperty;

public class OnSiteReChkApp extends AbstractAppModule {
	public String currentName = "现场复查";

	public OnSiteReChkApp(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void appModuleOnClick(AppModule aModule) throws HDException {
		super.appModuleOnClick(aModule);
		// 位置卡刷卡
		Activity parent = (Activity) getParent();
		Intent intent = new Intent(parent, LocationSwCard.class);
		Bundle mBundle = new Bundle();
		// 传递目标跳转activity类
		mBundle.putSerializable(LocationSwCard.SER_KEY_TARGETCLASS,
				TaskTabulationActivity.class);
		intent.putExtras(mBundle);
		parent.startActivity(intent);
	}

	// /**
	// * TODO 现场复查按钮点击事件处理：先刷位置卡，通过后根据位置显示作业任务数据
	// * @see
	// com.hd.hse.main.ui.event.homepage.AbstractAppModule#appModuleOnClick()
	// */
	// @Override
	// public void appModuleOnClick() throws HDException {
	// super.appModuleOnClick();
	// // 位置卡刷卡页
	// Activity parent = (Activity)getParent();
	// Intent intent = new Intent(parent, LocationSwCard.class);
	// Bundle mBundle = new Bundle();
	// // 传递目标跳转activity类
	// mBundle.putSerializable(LocationSwCard.SER_KEY_TARGETCLASS,
	// TaskTabulationActivity.class);
	// mBundle.putString(LocationSwCard.SER_KEY_APPNAME, currentName);
	// intent.putExtras(mBundle);
	// parent.startActivity(intent);
	// }
}
