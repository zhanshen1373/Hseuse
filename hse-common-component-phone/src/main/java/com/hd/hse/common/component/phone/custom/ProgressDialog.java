/**
 * Project Name:hse-cbs-app
 * File Name:ProgressDialog.java
 * Package Name:com.hd.hse.cbs.ui.common.custom
 * Date:2014年9月11日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 */

package com.hd.hse.common.component.phone.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.TextView;

import com.hd.hse.common.component.phone.R;
import com.hd.hse.entity.time.ServerDateManager;

/**
 * ClassName:ProgressDialog (耗时等待对话框).<br/>
 * Date: 2014年9月11日 <br/>
 *
 * @author lg
 * @see
 */
public class ProgressDialog extends Dialog {

    private Context context;

    private TextView txtMsg;
    private boolean isCancel = false;
    private boolean isStop;
    private Handler hd;
    private TextView netSpeed;
    private TextView totalNet;
    private long totalRxBytes;
    private long initialTime;
    private long nowTotalRxBytes;

    public ProgressDialog(Context context, String msg) {
        this(context, R.style.ProgressDialog, msg);
    }

    public ProgressDialog(Context context, boolean cancelable, String msg) {
        this(context, cancelable, R.style.ProgressDialog, msg);
    }

    public ProgressDialog(Context context, int theme, String msg) {
        this(context, false, theme, msg);
    }

    public ProgressDialog(final Context context, boolean cancelable, int theme,
                          String msg) {
        super(context, theme);
        this.context = context;
        setContentView(R.layout.hd_hse_main_app_common_progress);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        // 不退出
        setCancelable(cancelable);
        if (cancelable) {
            this.setOnCancelListener(new OnCancelListener() {

                @Override
                public void onCancel(DialogInterface arg0) {
                    isCancel = true;
                    Intent intent = new Intent("com.hd.hse.phone.DIALOG_CLOSE");
                    ProgressDialog.this.context.sendBroadcast(intent);
                }
            });
        }
        // 禁用点击外框区域消失框
        setCanceledOnTouchOutside(true);
        txtMsg = (TextView) this.findViewById(R.id.hd_cbs_common_progress_txt);
        netSpeed = (TextView) this.findViewById(R.id.hd_cbs_common_progress_netspeed);
        totalNet = (TextView) this.findViewById(R.id.hd_cbs_common_progress_totalnet);
        if (txtMsg != null) {
            txtMsg.setText(msg);
        }

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                hd.removeMessages(0);
            }
        });


        hd = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {

                    if (isStop()) {
                        hd.removeMessages(0);
                    } else {
                        netSpeed.setText(getNetSpeed(context.getApplicationInfo().uid) + "");
                        totalNet.setText((nowTotalRxBytes - totalRxBytes) + "kb");

                        hd.sendEmptyMessageDelayed(0, 1000);
                    }


                }
            }
        };
    }

    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;

    public String getNetSpeed(int uid) {
        nowTotalRxBytes = getTotalRxBytes(uid);
        long nowTimeStamp = ServerDateManager.getCurrentTimeMillis();
        if (lastTimeStamp == 0) {
            lastTimeStamp = initialTime;
        }
        if (lastTotalRxBytes == 0) {
            lastTotalRxBytes = totalRxBytes;
        }

        long dxTime = nowTimeStamp - lastTimeStamp;
        if (dxTime == 0) {
            dxTime = 1000;
        }

        double speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (dxTime));//毫秒转换


        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        return String.valueOf(speed) + " kb/s";
    }

    //getApplicationInfo().uid
    public long getTotalRxBytes(int uid) {

        return TrafficStats.getUidRxBytes(uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);//转为KB
    }

    /**
     * showMsg:(提示信息). <br/>
     * date: 2014年9月11日 <br/>
     *
     * @param msg
     * @author lg
     */
    public void showMsg(String msg) {
        txtMsg.setText(msg);
        totalRxBytes = getTotalRxBytes(context.getApplicationInfo().uid);
        initialTime = ServerDateManager.getCurrentTimeMillis();

        hd.sendEmptyMessageDelayed(0, 1000);

    }

    @Override
    public void setCancelable(boolean flag) {
        // TODO Auto-generated method stub
        super.setCancelable(flag);
    }

    public boolean isCancel() {
        return isCancel;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }
}
