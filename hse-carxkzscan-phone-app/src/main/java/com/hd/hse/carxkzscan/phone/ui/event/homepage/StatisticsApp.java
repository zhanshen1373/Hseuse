package com.hd.hse.carxkzscan.phone.ui.event.homepage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.hd.hse.carxkzscan.phone.ui.worktask.TaskTabulationActivity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.event.homepage.AbstractAppModule;
import com.hd.hse.entity.sys.AppModule;

public class StatisticsApp extends AbstractAppModule {

    public StatisticsApp(Context context) {
        super(context);
    }

    @Override
    public void appModuleOnClick(AppModule aModule) throws HDException {
        super.appModuleOnClick(aModule);
        // 位置卡刷卡
        Activity parent = (Activity) getParent();
        Intent intent = new Intent(parent, TaskTabulationActivity.class);
        parent.startActivity(intent);
    }


}
