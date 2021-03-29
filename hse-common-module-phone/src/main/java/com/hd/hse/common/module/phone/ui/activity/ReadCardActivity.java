/**
 * Project Name:hse-common-module
 * File Name:ReadCardActivity.java
 * Package Name:com.hd.hse.common.module.ui.activity
 * Date:2014年11月6日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 */

package com.hd.hse.common.module.phone.ui.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hd.hse.business.util.SYSConstant;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.module.phone.business.readcard.NFCReader;
import com.hd.hse.common.module.phone.business.readcard.ReadCardThread;

/**
 * ClassName:ReadCardActivity (配置读卡activity的超类).<br/>
 * Date:     2014年11月6日  <br/>
 *
 * @author ZhangJie
 * @see
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
public abstract class ReadCardActivity extends BaseActivity {
    /**
     * 独立线程读卡
     */
    // 是否初始化完成标志
    public boolean flag = false;
    // 刷卡线程
    public ReadCardThread myReadCardThread;
    // 刷卡handler
    public Handler myhandler;
    // 是否结束刷卡
    public boolean isend = false;
    /**
     * NFC读卡功能
     */
    //默认的读卡设备
    private NfcAdapter nfcAdapter;
    //意图拦截器
    private PendingIntent pendingIntent;
    //意图拦截类型列表
    private IntentFilter[] intentFiltersArray;
    //是否开启了nfc功能
    private boolean nfcenable = false;

    public Object getNfcAdapter() {
        return nfcAdapter;
    }

    public boolean getNfcEnable() {
        return nfcAdapter.isEnabled();
    }

    public String cardnum = "";
    /**
     * 需要拦截的卡
     */
    private String[][] techListsArray = new String[][]{
            new String[]{NfcV.class.getName()},
            new String[]{MifareClassic.class.getName(), NfcA.class.getName(), Ndef.class.getName()},
            new String[]{IsoDep.class.getName(), NfcA.class.getName(), NdefFormatable.class.getName()},
            new String[]{NfcF.class.getName()},
            new String[]{NfcA.class.getName()}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取默认的NFC控制器
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        //NFC拦截器
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        intentFiltersArray = new IntentFilter[]{ndef};
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**满足过滤条件的标签及数据格式，继续处理，不满足的会弹出选择对应的应用*/
        if (NFCReader.nfcReadCardEnable()) {
            if (nfcAdapter != null) {
                if (nfcAdapter.isEnabled()) {
                    nfcenable = true;
                    nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray);
                    //读卡号
                    cardnum = getIntent().getStringExtra("nfcmsg");
                    getIntent().putExtra("nfcmsg", "");
                } else {
                    ToastUtils.toast(ReadCardActivity.this, "NFC刷卡功能未开启");
                }

            }
        } else {
            // 表示开启寻卡功能
            isend = false;
            StartReadCardThread();
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (nfcAdapter != null) {
            nfcenable = true;
            nfcAdapter.disableForegroundDispatch(this);
            if (cardnum != null && cardnum.length() != 0) {
                getIntent().removeExtra("nfcmsg");
            }
        } else if (null != myReadCardThread) {
            myReadCardThread.setStop();
            isend = true;// 表示关闭寻卡功能
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String id = NFCReader.readCardId(tagFromIntent.getId(), true);
            intent.putExtra("nfcmsg", id);
        }
    }

    ;


    @SuppressLint("HandlerLeak")
    class MyHandler extends Handler {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bd = new Bundle();
            bd = msg.getData();
            String info = bd.getString(SYSConstant.KEYCARDCODE);
            if (!info.trim().equals("")) {
                myReadCardThread.setStop();
                actionByCardID(info);
            }
        }
    }

    /**
     * StartReadCardThread:(开启读卡线程). <br/>
     * date: 2014年9月12日 <br/>
     *
     * @author lxf
     */
    public void StartReadCardThread() {
        myhandler = new MyHandler();
        myReadCardThread = new ReadCardThread(myhandler);
        myReadCardThread.start();
    }

    /**
     * returnCardID:(获得卡号，完成子类的业务). <br/>
     * date: 2014年11月6日 <br/>
     *
     * @return
     * @author ZhangJie
     */
    public abstract void actionByCardID(String cardid);

}

