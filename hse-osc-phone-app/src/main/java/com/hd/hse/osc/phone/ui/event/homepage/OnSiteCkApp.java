package com.hd.hse.osc.phone.ui.event.homepage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.LocationSwCard;
import com.hd.hse.common.module.phone.ui.event.homepage.AbstractAppModule;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.osc.phone.ui.activity.worktask.TaskTabulationActivity;

/**
 * ClassName: OnSiteChkApp (现场检查APP)<br/>
 * date: 2015年1月20日 <br/>
 *
 * @author wenlin
 */
public class OnSiteCkApp extends AbstractAppModule {

    public OnSiteCkApp(Context context) {
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
