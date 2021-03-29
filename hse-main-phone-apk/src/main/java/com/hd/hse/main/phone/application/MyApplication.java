package com.hd.hse.main.phone.application;

import com.hd.hse.common.module.phone.camera.MainApplication;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.main.business.login.LoginCheckUpConfig;
import com.hd.hse.main.phone.FlavorConfig;

/**
 * created by yangning on 2018/9/19 17:37.
 */
public class MyApplication extends MainApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //根据渠道设置是否需要时间同步服务器
        ServerDateManager.setIsServerDate(FlavorConfig.isServerDate);
        //根据渠道设置登陆校验升级
        LoginCheckUpConfig.setIsCheckUp(FlavorConfig.isCheckUp);
    }
}
