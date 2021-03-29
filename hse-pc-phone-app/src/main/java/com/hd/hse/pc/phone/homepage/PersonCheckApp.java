package com.hd.hse.pc.phone.homepage;

import android.content.Context;
import android.content.Intent;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.ui.event.homepage.AbstractAppModule;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.pc.phone.activity.PersonCheckMode;
import com.hd.hse.pc.phone.activity.ReadPersonCardActivity;

/**
 * 人员检查模块入口
 * Created by liuyang on 2016年9月22日
 */
public class PersonCheckApp extends AbstractAppModule {
    public PersonCheckApp(Context context) {
        super(context);
    }

    /**
     * 大连项目，存在二维码扫描功能，其他项目只有刷卡功能
     */
    private boolean qyQR = true;
    public static final String QYQR = "isdl";

    @Override
    public void appModuleOnClick(AppModule aModule) throws HDException {
        super.appModuleOnClick(aModule);
        Intent intent = null;
        if (qyQR) {
            intent = new Intent(getParent(), PersonCheckMode.class);
        } else {
            intent = new Intent(getParent(), ReadPersonCardActivity.class);
        }
        getParent().startActivity(intent);
    }
}
