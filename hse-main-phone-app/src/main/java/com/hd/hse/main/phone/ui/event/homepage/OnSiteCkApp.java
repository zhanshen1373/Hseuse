package com.hd.hse.main.phone.ui.event.homepage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.LocationSwCard;
import com.hd.hse.common.module.phone.ui.activity.SystemApplication;
import com.hd.hse.common.module.phone.ui.event.homepage.AbstractAppModule;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.main.phone.ui.activity.main.MainActivity;
import com.hd.hse.system.SystemProperty;

/**
 * ClassName: OnSiteCkApp (主界面APP)<br/>
 * date: 2015年1月22日 <br/>
 * 
 * @author wenlin
 * @version
 */
public class OnSiteCkApp extends AbstractAppModule {

	public OnSiteCkApp(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void appModuleOnClick(AppModule aModule) throws HDException {
		super.appModuleOnClick(aModule);
		if (LocationSwCard.mTimer != null) {
			LocationSwCard.mTimer.cancel();
			LocationSwCard.mTimer = null;//
		}
//		Activity parent = (Activity) getParent();
//		Intent intent = new Intent(parent, MainActivity.class);
		// 回到主界面时，销毁前个activity,重新加载主界面activity
		//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		SystemApplication.getInstance().popActivity();
		// 清空刷卡位置
		SystemProperty.getSystemProperty().setPositionCard(null);
		//parent.startActivity(intent);
	}
}
