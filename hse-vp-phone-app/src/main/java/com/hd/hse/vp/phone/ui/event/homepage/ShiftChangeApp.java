package com.hd.hse.vp.phone.ui.event.homepage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.LocationListActivity;
import com.hd.hse.common.module.phone.ui.activity.LocationSwCard;
import com.hd.hse.common.module.phone.ui.event.homepage.AbstractAppModule;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.system.SystemProperty;

/**
 * ClassName: ShiftChangeApp (位置卡维护)<br/>
 * date: 2015年9月2日 <br/>
 * 
 * @author lxf
 * @version
 */
public class ShiftChangeApp extends AbstractAppModule {
	public String currentName = "位置卡维护";

	public ShiftChangeApp(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void appModuleOnClick(AppModule aModule) throws HDException {
		super.appModuleOnClick(aModule);
//		if (isRelation(IRelativeEncoding.REUSINGLOCATIONCARD)
//				&& SystemProperty.getSystemProperty().getPositionCard() != null) {
			Activity parent = (Activity) getParent();
			Intent intent = new Intent(parent, LocationListActivity.class);
			parent.startActivity(intent);
//		} else {
//			// 位置卡刷卡
//			Activity parent = (Activity) getParent();
//			Intent intent = new Intent(parent, LocationSwCard.class);
//			Bundle mBundle = new Bundle();
//			// 传递目标跳转activity类
//			mBundle.putSerializable(LocationSwCard.SER_KEY_TARGETCLASS,
//					LocationListActivity.class);
//			intent.putExtras(mBundle);
//			parent.startActivity(intent);
//		}
	}
}
