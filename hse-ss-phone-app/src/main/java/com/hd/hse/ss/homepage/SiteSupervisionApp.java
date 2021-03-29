package com.hd.hse.ss.homepage;

import android.content.Context;
import android.content.Intent;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.event.homepage.AbstractAppModule;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.ss.activity.SupervisionListActivity;

public class SiteSupervisionApp extends AbstractAppModule {
	public SiteSupervisionApp(Context context) {
		super(context);
	}

	@Override
	public void appModuleOnClick(AppModule aModule) throws HDException {
		super.appModuleOnClick(aModule);
		Intent intent = new Intent(getParent(), SupervisionListActivity.class);
		getParent().startActivity(intent);
	}
}
