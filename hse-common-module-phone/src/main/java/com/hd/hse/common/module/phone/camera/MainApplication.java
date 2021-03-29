/**
 * Project Name:hse-common-module-phone
 * File Name:MainApplication.java
 * Package Name:com.hd.hse.common.module.phone.camera
 * Date:2015年3月31日
 * Copyright (c) 2015, fulibo@ushayden.com All Rights Reserved.
 */

package com.hd.hse.common.module.phone.camera;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.hd.hse.common.logger.LogUtils;
import com.tencent.bugly.crashreport.CrashReport;

import org.apache.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ClassName:MainApplication ().<br/>
 * Date:     2015年3月31日  <br/>
 *
 * @author flb
 * @see
 */
public class MainApplication extends Application {

    public static Logger logger = LogUtils
            .getLogger(MainApplication.class);
    private static final String TAG = "MainApplication";
    private Thread.UncaughtExceptionHandler mDefaultExHandler;
    private CameraManager mCameraManager;

    private Thread.UncaughtExceptionHandler mExHandler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            if (mCameraManager != null) {
                Log.e(TAG, "Uncaught exception! Closing down camera safely firsthand");
                mCameraManager.forceCloseCamera();
            }

            mDefaultExHandler.uncaughtException(thread, ex);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        mDefaultExHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(mExHandler);

        if (Build.VERSION.SDK_INT >= 24) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }


        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setCrashHandleCallback(new CrashReport.CrashHandleCallback() {
            public Map<String, String> onCrashHandleStart(int crashType, String errorType,
                                                          String errorMessage, String errorStack) {
                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                map.put("Key", "Value");
                return map;
            }

            @Override
            public byte[] onCrashHandleStart2GetExtraDatas(int crashType, String errorType,
                                                           String errorMessage, String errorStack) {
                try {
                    logger.error("错误类型:" + errorType + "\n" + "错误路径:" + errorStack + "\n" + "错误信息:" + errorMessage);
                    return "Extra data.".getBytes("UTF-8");
                } catch (Exception e) {
                    logger.error("错误类型:" + errorType + "\n" + "错误路径:" + errorStack + "\n" + "错误信息:" + errorMessage);
                    return null;
                }
            }

        });
        CrashReport.initCrashReport(getApplicationContext(), "7854fef860", false, strategy);
    }

    public void setCameraManager(CameraManager camMan) {
        mCameraManager = camMan;
    }


}

