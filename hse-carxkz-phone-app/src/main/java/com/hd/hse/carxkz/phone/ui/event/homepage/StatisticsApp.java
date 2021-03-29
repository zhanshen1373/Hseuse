package com.hd.hse.carxkz.phone.ui.event.homepage;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hd.hse.carxkz.phone.ui.worktask.TaskTabulationActivity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.activity.LocationSwCard;
import com.hd.hse.common.module.phone.ui.event.homepage.AbstractAppModule;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.sys.AppModule;

import java.util.List;

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
