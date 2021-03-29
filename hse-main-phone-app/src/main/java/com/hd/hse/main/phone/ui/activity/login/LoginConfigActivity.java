/**
 * Project Name:hse-cbs-app
 * File Name:LoginConfig.java
 * Package Name:com.hd.hse.cbs.ui.activity.login
 * Date:2014年9月4日
 * Copyright (c) 2014, zhulei@ushayden.com All Rights Reserved.
 */
package com.hd.hse.main.phone.ui.activity.login;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.ui.activity.BaseActivity;
import com.hd.hse.dc.business.listener.common.CheckConnect;
import com.hd.hse.entity.common.SysConfig;
import com.hd.hse.main.phone.R;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.system.SystemProperty;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: LoginConfig ()<br/>
 * date: 2014年9月4日 <br/>
 *
 * @author zl
 */
public class LoginConfigActivity extends BaseActivity implements
        OnClickListener {
    private static Logger logger = LogUtils.getLogger(LoginConfigActivity.class);
    private BusinessAction action = new BusinessAction();
    // 网络设置保存
    private TextView configSave;
    // 网络设置取消
    private TextView configCancel;
    // 外网IP地址
    private EditText outSiteIp;
    // 外网端口号
    private EditText outSitePort;
    // 内网IP地址
    private EditText innerSiteIp;
    // 内网端口号
    private EditText innerSitePort;
    private RadioButton configRadioOut;
    private RadioButton configRadioInner;
    private RelativeLayout childView;
    private TextView testBtn;
    private TextView sucTV;
    private TextView failTV;
    private TextView lodingTV;
    // 判断输入的IP是否符合条件的正则表达式
    private String ipCheckStr = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\."
            + "(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";
    private String cols[] = {"sysurl", "enable"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 无title
        setContentView(R.layout.hd_hse_main_app_layout_config);
        initView();
        initdate();
        configRadioInner
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @SuppressLint("ResourceAsColor")
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        Resources resource = (Resources) getBaseContext()
                                .getResources();
                        ColorStateList black = (ColorStateList) resource
                                .getColorStateList(R.color.hd_hse_common_alerttext_black);
                        configRadioOut.setChecked(false);
                        configRadioInner.setChecked(isChecked);

                        outSiteIp.setTextColor(R.color.hd_hse_common_grey);
                        outSitePort.setTextColor(R.color.hd_hse_common_grey);
                        configRadioOut.setTextColor(R.color.hd_hse_common_grey);
                        outSiteIp.setFocusableInTouchMode(false);
                        outSitePort.setFocusableInTouchMode(false);
                        outSiteIp.setFocusable(false);
                        outSitePort.setFocusable(false);

                        innerSitePort.setFocusableInTouchMode(isChecked);
                        innerSiteIp.setFocusableInTouchMode(isChecked);
                        innerSiteIp.setTextColor(black);
                        innerSitePort.setTextColor(black);
                        configRadioInner.setTextColor(black);
                        innerSiteIp.setFocusable(true);

//						testBtn.setVisibility(View.VISIBLE);
                        sucTV.setVisibility(View.GONE);
                        failTV.setVisibility(View.GONE);
                        lodingTV.setVisibility(View.GONE);
                    }
                });
        configRadioOut
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @SuppressLint("ResourceAsColor")
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        Resources resource = (Resources) getBaseContext()
                                .getResources();
                        ColorStateList black = (ColorStateList) resource
                                .getColorStateList(R.color.hd_hse_common_alerttext_black);
                        configRadioInner.setChecked(false);
                        configRadioOut.setChecked(isChecked);
                        innerSiteIp.setTextColor(R.color.hd_hse_common_grey);
                        innerSitePort.setTextColor(R.color.hd_hse_common_grey);
                        configRadioInner.setTextColor(R.color.hd_hse_common_grey);
                        innerSiteIp.setFocusableInTouchMode(false);
                        innerSitePort.setFocusableInTouchMode(false);
                        innerSiteIp.setFocusable(false);
                        innerSitePort.setFocusable(false);

                        outSiteIp.setTextColor(black);
                        outSitePort.setTextColor(black);
                        configRadioOut.setTextColor(black);
                        outSiteIp.setFocusableInTouchMode(true);
                        outSitePort.setFocusableInTouchMode(true);
                        outSiteIp.setFocusable(true);

//						testBtn.setVisibility(View.VISIBLE);
                        sucTV.setVisibility(View.GONE);
                        failTV.setVisibility(View.GONE);
                        lodingTV.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Test();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        configSave = (TextView) findViewById(R.id.hd_cbs_config_sure_b);
        configCancel = (TextView) findViewById(R.id.hd_cbs_config_cancel_b);
        outSiteIp = (EditText) findViewById(R.id.hd_cbs_config_outsite_et01);
        outSitePort = (EditText) findViewById(R.id.hd_cbs_config_outsite_et02);
        innerSiteIp = (EditText) findViewById(R.id.hd_cbs_config_innersite_et01);
        innerSitePort = (EditText) findViewById(R.id.hd_cbs_config_innersite_et02);
        configRadioOut = (RadioButton) findViewById(R.id.hd_cbs_config_outsite_rb);
        configRadioInner = (RadioButton) findViewById(R.id.hd_cbs_config_innersite_rb);
        childView = (RelativeLayout) findViewById(R.id.hd_cbs_login_config_child_linerl);
        testBtn = (TextView) findViewById(R.id.hd_hse_config_test_bt);
        sucTV = (TextView) findViewById(R.id.hd_hse_config_suc_tv);
        failTV = (TextView) findViewById(R.id.hd_hse_config_fail_tv);
        lodingTV = (TextView) findViewById(R.id.hd_hse_config_loding_tv);
        configSave.setOnClickListener(onConfigSaveClick);
        configCancel.setOnClickListener(onConfigCancalClick);
        testBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Test();
            }
        });
        //隐藏键盘
        childView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                hideInputKeyboard(v);
            }
        });
    }

    /**
     * 初始化数据 initdate:(). <br/>
     * date: 2014年9月5日 <br/>
     *
     * @author zl
     */
    @SuppressLint("ResourceAsColor")
    private void initdate() {
        String[] configStr = {"sysurl", "enable", "syscode"};
        try {
            List<SysConfig> configList = (List) action.queryEntities(
                    SysConfig.class, configStr, "syscode in('inurl','outurl')");
            for (int i = 0; i < configList.size(); i++) {
                SysConfig sConfig = configList.get(i);
                String url = sConfig.getSysurl();
                String port = "";
                String ip = "";
                if (url != null && url.split(":").length == 2) {
                    port = url.split(":")[1];
                    ip = url.split(":")[0];
                }
                int enable = sConfig.getEnable();
                if ("inurl".equals(sConfig.getSyscode())) {
                    innerSiteIp.setText(ip);
                    innerSitePort.setText(port);
                    if (enable == 1) {
                        configRadioInner.setChecked(true);
                    } else {
                        innerSiteIp
                                .setTextColor(R.color.hd_hse_common_describetext_gray);
                        innerSitePort
                                .setTextColor(R.color.hd_hse_common_describetext_gray);
                        configRadioInner
                                .setTextColor(R.color.hd_hse_common_describetext_gray);
                        innerSiteIp.setFocusable(false);
                        innerSitePort.setFocusable(false);
                    }

                }
                if ("outurl".equals(sConfig.getSyscode())) {
                    outSiteIp.setText(ip);
                    outSitePort.setText(port);
                    if (enable == 1) {
                        configRadioOut.setChecked(true);
                    } else {
                        outSiteIp
                                .setTextColor(R.color.hd_hse_common_describetext_gray);
                        outSitePort
                                .setTextColor(R.color.hd_hse_common_describetext_gray);
                        configRadioOut
                                .setTextColor(R.color.hd_hse_common_describetext_gray);
                        outSiteIp.setFocusableInTouchMode(false);
                        outSitePort.setFocusableInTouchMode(false);
                    }
                }
            }
        } catch (HDException e) {
            // TODO Auto-generated catch block
            logger.error(e);
            ToastUtils.toast(this, "网络配置加载出错，请联系系统管理员！");
        }
    }

    /**
     * 网络设置保存按钮监听 onConfigSaveClick:(). <br/>
     * date: 2014年9月5日 <br/>
     *
     * @author zl
     * @param v
     */
    private OnClickListener onConfigSaveClick = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            EditText editTextIp, editTextPort;
            if (configRadioInner.isChecked()) {
                editTextIp = innerSiteIp;
                editTextPort = innerSitePort;
            } else {
                editTextIp = outSiteIp;
                editTextPort = outSitePort;
            }
            String outIp = editTextIp.getText().toString();
            String outPort = editTextPort.getText().toString();
            boolean p = true;
            if (outPort != null && !"".equals(outPort)) {
                int port = Integer.parseInt(outPort);
                if (port > 65535)
                    p = false;
            }
            if (outIp != null && !"".equals(outIp) && outPort != null
                    && !"".equals(outPort)) {
                Pattern pattern = Pattern.compile(ipCheckStr);
                Matcher matcher = pattern.matcher(outIp);
                boolean isTrue = matcher.matches();
                if (isTrue && p) {
                    saveConfig();
                } else if (!p) {
                    ToastUtils.imgToast(LoginConfigActivity.this,
                            R.drawable.hd_hse_common_msg_wrong, "端口号应在0~65535之间！");
                    return;
                } else {
                    ToastUtils.imgToast(LoginConfigActivity.this,
                            R.drawable.hd_hse_common_msg_wrong, "地址格式错误！");
                    return;
                }
            } else {
                ToastUtils.imgToast(LoginConfigActivity.this,
                        R.drawable.hd_hse_common_msg_wrong, "地址或端口号不能为空！");
                return;
            }
            LoginConfigActivity.this.finish();
        }
    };

    /**
     * 网络设置取消按钮监听 onConfigCancalClick:(). <br/>
     * date: 2014年9月5日 <br/>
     *
     * @author zl
     * @param v
     */
    private OnClickListener onConfigCancalClick = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            LoginConfigActivity.this.finish();
        }
    };

    private void saveConfig() {
        List<SuperEntity> lstSite = new ArrayList<SuperEntity>();
        SysConfig innerSite = new SysConfig();
        if (configRadioInner.isChecked()) {
            innerSite.setEnable(1);
        } else {
            innerSite.setEnable(0);
        }
        innerSite.setSyscode("inurl");
        String inurl = innerSiteIp.getText().toString() + ":"
                + innerSitePort.getText().toString();
        innerSite.setSysurl(inurl);
        lstSite.add(innerSite);

        SysConfig outSite = new SysConfig();
        if (configRadioOut.isChecked()) {
            outSite.setEnable(1);
        } else {
            outSite.setEnable(0);
        }
        outSite.setSyscode("outurl");
        String outurl = outSiteIp.getText().toString() + ":"
                + outSitePort.getText().toString();
        outSite.setSysurl(outurl);

        lstSite.add(outSite);
        try {
            action.updateEntities(lstSite, cols);
            SystemProperty.getSystemProperty().clearWebConfig();
        } catch (HDException e) {
            // TODO Auto-generated catch block
            ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                    "保存网络设置出错，请联系系统管理员！");
        }
    }


    private void Test() {
        saveConfig();
//		testBtn.setVisibility(View.GONE);
        sucTV.setVisibility(View.GONE);
        failTV.setVisibility(View.GONE);
        lodingTV.setVisibility(View.VISIBLE);
//		prsDlg = new ProgressDialog(this, "尝试连接。。");
//		prsDlg.show();
        new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                super.run();
                try {
                    CheckConnect pcChkUser = new CheckConnect();
                    pcChkUser.action(PadInterfaceContainers.METHOD_COMMON_TEST);

                } catch (HDException e) {
                    // failTV.setVisibility(View.VISIBLE);
                    handler.sendEmptyMessage(0);

                    return;
                }
                handler.sendEmptyMessage(1);
            }
        }.start();
    }

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
//				prsDlg.dismiss();
                    failTV.setVisibility(View.VISIBLE);
                    sucTV.setVisibility(View.GONE);
                    lodingTV.setVisibility(View.GONE);
                    // ToastUtils.imgToast(LoginConfigActivity.this,
                    // R.drawable.hd_hse_common_msg_wrong, "网络连接失败！");
                    break;
                case 1:
//				prsDlg.dismiss();
                    sucTV.setVisibility(View.VISIBLE);
                    lodingTV.setVisibility(View.GONE);
                    failTV.setVisibility(View.GONE);
                    // ToastUtils.imgToast(LoginConfigActivity.this,
                    // R.drawable.hd_hse_common_msg_right, "网络连接成功！");
                    break;
            }
        }

        ;
    };

    /**
     * TODO
     *
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
    }

}
