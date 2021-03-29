/**
 * Project Name:hse-main-app
 * File Name:OnSiteChkApp.java
 * Package Name:com.hd.hse.main.ui.event.homepage
 * Date:2014年9月23日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.cc.phone.ui.event.homepage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hd.hse.cc.phone.ui.activity.worktask.TaskTabulationActivity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.LocationSwCard;
import com.hd.hse.common.module.phone.ui.event.homepage.AbstractAppModule;
import com.hd.hse.entity.sys.AppModule;

/**
 * ClassName: CloseCancelChkApp (关闭取消APP)<br/>
 * date: 2015年1月20日 <br/>
 *
 * @author zhulei
 * @version
 */
public class CloseCancelChkApp extends AbstractAppModule {

	public CloseCancelChkApp(Context context) {
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
}
