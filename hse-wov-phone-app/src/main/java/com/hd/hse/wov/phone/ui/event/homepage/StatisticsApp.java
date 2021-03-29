/**
 * Project Name:hse-wov-phone-app
 * File Name:StatisticsApp.java
 * Package Name:com.hd.hse.wov.phone.ui.event.homepage
 * Date:2015年11月17日
 * Copyright (c) 2015, liuyang@ushayden.com All Rights Reserved.
 */

package com.hd.hse.wov.phone.ui.event.homepage;

import android.content.Context;
import android.content.Intent;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.event.homepage.AbstractAppModule;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.wov.phone.ui.statistics.StatisticsListActivity;
import com.hd.hse.wov.phone.ui.statistics.StatisticsOldListActivity;

import java.util.List;

/**
 * ClassName:StatisticsApp ().<br/>
 * Date: 2015年11月17日 <br/>
 *
 * @author LiuYang
 * @see
 */
public class StatisticsApp extends AbstractAppModule {
    public StatisticsApp(Context context) {
        super(context);
    }

    BaseDao dao;
    private boolean isqy;

    @Override
    public void appModuleOnClick(AppModule aModule) throws HDException {
        super.appModuleOnClick(aModule);


        dao = new BaseDao();

        String sql = "select * from sys_relation_info ";
        List<RelationTableName> appAnlsCfg = null;
        Intent intent = null;
        try {
            appAnlsCfg = (List<RelationTableName>) dao.executeQuery(sql,
                    new EntityListResult(RelationTableName.class));
            for (int i = 0; i < appAnlsCfg.size(); i++) {
                if (appAnlsCfg.get(i).getAttribute("sys_type").
                        equals("ISAPPANLSCFG")) {
                    if ((int)appAnlsCfg.get(i).getAttribute("isqy") == 1) {
                        isqy = true;
                        break;
                    }
                }
            }
        } catch (DaoException e) {
            e.printStackTrace();
            throw new DaoException("查询信息失败！");
        }
        if (isqy) {
            intent = new Intent(getParent(), StatisticsListActivity.class);
        } else {
            intent = new Intent(getParent(), StatisticsOldListActivity.class);
        }


//        Intent intent = new Intent(getParent(), StatisticsListActivity.class);
        getParent().startActivity(intent);
    }
}
