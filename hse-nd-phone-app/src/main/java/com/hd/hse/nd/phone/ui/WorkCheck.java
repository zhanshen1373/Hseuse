package com.hd.hse.nd.phone.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.LocationSwCard;
import com.hd.hse.common.module.phone.ui.event.homepage.AbstractAppModule;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.wov.phone.ui.activity.worktask.TaskTabulationActivity;

/**
 * Created by dubojian on 2019/1/15.
 */

public class WorkCheck extends AbstractAppModule {


    public WorkCheck(Context context) {
        super(context);
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
        mBundle.putSerializable(LocationSwCard.APP_MODULE, aModule);
        intent.putExtras(mBundle);
        parent.startActivity(intent);

    }

}
