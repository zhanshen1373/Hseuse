package com.hd.hse.dc.phone.ui.event.homepage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.LocationSwCard;
import com.hd.hse.common.module.phone.ui.event.homepage.AbstractAppModule;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.dc.phone.ui.activity.upload.UpLoadActivity;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.system.SystemProperty;

/**
 * TODO 作业票上传(界面app) ClassName: WorkOrderUpdate ()<br/>
 * date: 2015年4月15日 <br/>
 * 
 * @author wenlin
 * @version
 */
public class WorkOrderUpdate extends AbstractAppModule {

	public WorkOrderUpdate() {
		// TODO Auto-generated constructor stub
	}

	public WorkOrderUpdate(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void appModuleOnClick(AppModule aModule) throws HDException {
		super.appModuleOnClick(aModule);
		Activity parent = (Activity) getParent();
		Intent intent = new Intent(parent, UpLoadActivity.class);
		parent.startActivity(intent);
	}
}
