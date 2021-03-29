package com.hd.hse.main.phone.ui.activity.login;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.AysncTaskMessage;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.adapter.OptionsAdapter;
import com.hd.hse.common.component.phone.custom.DrawEditText;
import com.hd.hse.common.component.phone.custom.DrawEditText.IOnLayoutEvent;
import com.hd.hse.common.component.phone.custom.DrawEditText.ITouchOnClick;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.business.readcard.NFCReader;
import com.hd.hse.common.module.phone.ui.activity.ReadCardActivity;
import com.hd.hse.common.module.phone.ui.activity.SystemApplication;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.dc.business.listener.common.GetVersionInfo;
import com.hd.hse.dc.phone.ui.activity.vision.VersionUp;
import com.hd.hse.entity.base.Licence;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.common.RecentlyLoginPerson;
import com.hd.hse.entity.common.SysConfig;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.main.business.login.LoginActionListener;
import com.hd.hse.main.business.login.LoginCheckUpConfig;
import com.hd.hse.main.phone.R;
import com.hd.hse.main.phone.ui.activity.main.MainActivity;
import com.hd.hse.main.phone.ui.receiver.ReceiverManager;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.petrochina.login.AppPortSDK;
import cn.com.petrochina.login.EncryptParamsSDK;

public class LoginActivity extends ReadCardActivity implements OnClickListener,
        Callback {
    private static Logger logger = LogUtils.getLogger(LoginActivity.class);
    /**
     * action:TODO(后台业务处理).
     */
    private BusinessAction action;

    /**
     * actionLsnr:TODO(特殊业务动作处理).
     */
    private LoginActionListener actionLsnr;

    private BroadcastReceiver receiver;


    public LoginActionListener getActionLsnr() {
        return actionLsnr;
    }

    public void setActionLsnr(LoginActionListener actionLsnr) {
        this.actionLsnr = actionLsnr;
    }

    /**
     * asyncTask:TODO(登录异步处理).
     */
    private BusinessAsyncTask asyncTask;

    /**
     * prsDlg:TODO(登录等待).
     */
    private ProgressDialog prsDlg;
    private ProgressDialog sjDlg;
    private int screenX = 0, screenY = 0;
    private View parent;
    // 用户名
    private DrawEditText userName;
    // 密码
    private DrawEditText passWord;
    // 登录按钮
    private TextView btn;
    // 网络配置
    private TextView configSite;
    // 网络连接情况
    private TextView internet;
    // 展示所有下拉选项的ListView
    private ListView listView = null;
    // 记住密码
    private CheckBox checkboxrememberpwdBox;
    // 自动登录
    private CheckBox checkboxAutoLogin;
    // imei
    private TextView tvImei;
    // PopupWindow对象
    private PopupWindow selectPopupWindow = null;
    // 自定义Adapter
    private OptionsAdapter optionsAdapter = null;
    // 下拉框选项数据源
    private ArrayList<String> datas = new ArrayList<String>();
    ;
    // 下拉框依附组件宽度，也将作为下拉框的宽度
    private int pwidth;
    // 用来处理选中或者删除下拉项消息
    private Handler handler;
    /**
     * lstRcvr:TODO(广播收听).
     */
    private List<BroadcastReceiver> lstRcvr;

    /**
     * lstPdIntent:TODO(定时任务).
     */
    private List<PendingIntent> lstPdIntent;
    /**
     * 连接设置防止多点击
     *
     * @param savedInstanceState
     */
    private boolean isWebSite = false;

    /**
     * 是否是从安全退出按钮退出的
     */
    private boolean isLogout = false;

    private HashMap<String, String> lastuserinfo = new HashMap<>();
    private List<RecentlyLoginPerson> rlpList;
    private AppPortSDK appPortSDK;
    private String encryptParams, loginId, loginName, domain, subAccounts, version;

    public static final int APP_PORT = 10861;
//    10768;  应用映射的安全端口(辽阳)
    //    10861;  //大连安全端口
    private static boolean unLock = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.hd_hse_main_app_layout_login);

        isLogout = false;

        initView();
        overridePendingTransition(R.anim.hd_hse_common_zoomin,
                R.anim.hd_hse_common_zoomout);
        TextWatcher watcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {

                autoShowPassword(arg0);

            }

        };
        userName.addTextChangedListener(watcher);
        userName.setiTouchOnClick(new useNameTouchClick());
        userName.setiOnLayoutEvent(new useNameOnLayoutEvent());
        initialize();

        // 启动后台任务
        startTasks();


    }


    private void autoShowPassword(Editable arg0) {

        // TODO Auto-generated method stub
        String currentPassword = "";
        String[] str = {"usercode", "password", "autologin"};

        List<RecentlyLoginPerson> rlpList;
        try {
            rlpList = (List) action.queryEntities(RecentlyLoginPerson.class,
                    str, "usercode= '" + arg0.toString() + "'"
                            + "order by createdate desc");

            if (rlpList.size() > 0) {
                currentPassword = rlpList.get(0).getPassword();
                int autologin = rlpList.get(0).getAutologin();

                passWord.setText(currentPassword);
                if (currentPassword != null
                        && !StringUtils.isEmpty(currentPassword)) {
                    checkboxrememberpwdBox.setChecked(true);
                } else {
                    checkboxrememberpwdBox.setChecked(false);
                }

                if (autologin == 0) {
                    checkboxAutoLogin.setChecked(false);
                } else {
                    checkboxAutoLogin.setChecked(true);
                }

            } else {
                passWord.setText("");
                checkboxrememberpwdBox.setChecked(false);
                checkboxAutoLogin.setChecked(false);
            }

        } catch (HDException e) {

            e.printStackTrace();
        }

    }

    /**
     * 判断是否需要自动登录
     * <p>
     * Created by Administrator on 2016年3月30日
     *
     * @param isLogout2
     */
    private void isAutoLog(boolean isLogout2) {
        getLastUserInfo();

        if (!isLogout2) {
            // 不是退出状态,进行自动登录，lastuserinfo.size()=2，证明上次人员有记住过密码
            if (lastuserinfo.size() == 3) {
                userName.setText(lastuserinfo.get("usercode"));
                passWord.setText(lastuserinfo.get("password"));
                checkboxrememberpwdBox.setChecked(true);
                if (lastuserinfo.get("autologin").equals("1")) {
                    checkboxAutoLogin.setChecked(true);
                    btn.performClick();
                } else {
                    checkboxAutoLogin.setChecked(false);
                }

            }

        } else {
            // 是退出状态
            if (cardnum == null) {
                if (lastuserinfo.size() == 3) {
                    userName.setText(lastuserinfo.get("usercode"));
                    passWord.setText(lastuserinfo.get("password"));
                    checkboxrememberpwdBox.setChecked(true);
                } else if (lastuserinfo.size() == 2) {
                    userName.setText(lastuserinfo.get("usercode"));
                    checkboxrememberpwdBox.setChecked(false);
                }
                if (lastuserinfo.get("autologin") != null
                        && lastuserinfo.get("autologin").equals("1")) {
                    checkboxAutoLogin.setChecked(true);
                } else {
                    checkboxAutoLogin.setChecked(false);
                }
            }
        }

    }


    private void initView() {

        userName = (DrawEditText) findViewById(R.id.hd_hse_main_applogin_username_et01);
        passWord = (DrawEditText) findViewById(R.id.hd_hse_main_applogin_password_et01);
        btn = (TextView) findViewById(R.id.hd_hse_main_applogin_submit_b01);
        configSite = (TextView) findViewById(R.id.hd_hse_main_applogin_configsite_tv01);
        internet = (TextView) findViewById(R.id.hd_hse_main_login_station_tv);
        checkboxrememberpwdBox = (CheckBox) findViewById(R.id.hd_hse_main_applogin_remeberpwd_cb);
        checkboxAutoLogin = (CheckBox) findViewById(R.id.hd_hse_main_applogin_autologin);
        tvImei = (TextView) findViewById(R.id.hd_hse_main_login_imei);
        tvImei.setOnClickListener(clickImei);
        // 取消记住密码的勾选，自动登录勾选也取消了
        checkboxrememberpwdBox
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton arg0,
                                                 boolean arg1) {
                        if (!arg1) {
                            checkboxAutoLogin.setChecked(false);
                        }
                    }
                });
        // 1、勾了自动登录，记住密码自动勾选
        checkboxAutoLogin
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton arg0,
                                                 boolean arg1) {
                        // TODO Auto-generated method stub
                        if (arg1) {
                            checkboxrememberpwdBox.setChecked(true);
                        }
                    }
                });
        View logo = findViewById(R.id.hd_hse_login_logo);
        View main = findViewById(R.id.hd_hse_main_applogin_main);
        parent = main;
        // 背景图片压缩处理
        setBackground(logo, R.drawable.hd_hse_phone_logo);
        setBackground(main, R.drawable.hd_hse_main_app_login_bg);
        // 监听隐藏键盘
        main.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hideInputKeyboard(view);
            }
        });
        // 网络设置监听
        configSite.setOnClickListener(configLsn);
        // 登录监听
        btn.setOnClickListener(loginLsn);

    }

    boolean isLoginning = false;

    /**
     * calculation:(计算userName相对屏幕的坐标). <br/>
     * date: 2015年5月5日 <br/>
     *
     * @author zhaofeng
     */
    private void calculation(View view) {
        if (screenX == 0 || screenY == 0) {
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            screenX = location[0];
            screenY = location[1] + view.getHeight() - 3;
        }
    }


    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<String>();

        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * TODO 表示开启寻卡功能
     *
     * @see android.app.Fragment#onResume()
     */
    @Override
    public void onResume() {
        super.onResume();


        /* 卸载apk代码，需要试一下(辽阳)
        boolean avilible = isAvilible(this, "petrochina.lysh.zyxk");
        boolean avl = isAvilible(this, "com.hd.hse.main.phone");

        if (avilible && avl) {
            Uri packageURI = Uri.parse("package:com.hd.hse.main.phone");
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
            startActivity(uninstallIntent);
            return;
        }

        if (userName.getText().toString() != null &&
                !userName.getText().toString().equals("")) {
            //卸载之后

        } else {
            //卸载之前
            Log.e("dc", "uo");
            userName.setText("ww");
        }
             */

        appPortSDK = new AppPortSDK(this);
        //安全端口是否开启
        boolean b = false;
        try {
            b = appPortSDK.verifyAppPortUsed(APP_PORT, getPackageName());
//            ToastUtils.toast(LoginActivity.this,"安全端口没有开启");
            initEncryptParams();
        } catch (Exception e) {
            e.printStackTrace();
        }


        isWebSite = false;
        passWord.setText("");
        if (!isLoginning) {
            if (cardnum != null && cardnum.length() != 0) {
                // 去掉前几位（大连项目特殊需求）
                IQueryRelativeConfig relationConfig = new QueryRelativeConfig();
                RelationTableName config = new RelationTableName();
                config.setSys_type(IRelativeEncoding.ISSUBSTRING);
                RelationTableName relation = relationConfig
                        .getRelativeObj(config);
                int num = 0;
                if (relation != null
                        && !StringUtils.isEmpty(relation.getInput_value())
                        && NumberUtils.isNumber(relation.getInput_value())) {
                    num = Integer.valueOf(relation.getInput_value());
                }
                if (NFCReader.getIsPositived()) {
                    cardnum = cardnum.substring(0, cardnum.length() - num);
                } else {
                    cardnum = cardnum.substring(num);
                }
            }
            actionByCardID(cardnum);
        }


        String logindesc = "";
        String[] configStr = {"sysurl", "enable", "syscode"};
        try {
            List<SysConfig> configList = (List) action.queryEntities(
                    SysConfig.class, configStr, "syscode in('inurl','outurl')");
            if (configList != null && configList.size() > 0) {
                SysConfig sysconfig;
                for (int i = 0; i < configList.size(); i++) {
                    sysconfig = configList.get(i);

                    if (sysconfig.getEnable() == 1) {
                        if ("inurl".equals(sysconfig.getSyscode())) {
                            logindesc = "内网模式";
                        } else {
                            logindesc = "外网模式";
                        }
                    }
                }
            }
        } catch (HDException e) {
            logger.error(e.getMessage());
        }


        internet.setText(logindesc);

        // 是否自动登录
        isAutoLog(isLogout);


        //辽阳石化自动登录
        if (b && encryptParams != null) {

            /*
            if (StringUtils.isEmpty(userName.getText().toString())) {
                ToastUtils.toast(LoginActivity.this, "请输入用户名后再登录！");
                return;
            }

            PersonCard user = new PersonCard();
            user.setPersonid(userName.getText().toString());
            user.setPassword(passWord.getText().toString());
            user.setRememberPWD(checkboxrememberpwdBox.isChecked());
            user.setAutoLogin(checkboxAutoLogin.isChecked());
            //传过去的是移动大厅的
            user.setEncryptParams(encryptParams);
            // 登录验证
            loginCheck(user);
*/
        }


    }

    private void initEncryptParams() {

        EncryptParamsSDK encryptParamsSDK = new EncryptParamsSDK(this, getIntent());
        // 获取全部信息
        encryptParams = encryptParamsSDK.getContent();
        if (!TextUtils.isEmpty(encryptParams)) {
            // 获取用户ID
            loginId = encryptParamsSDK.getLoginId();
            // 获取用户名
            loginName = encryptParamsSDK.getLoginName();
            // 获取域名
            domain = encryptParamsSDK.getDomain();
            // 获取子账户
            subAccounts = encryptParamsSDK.getSubAccounts();
            // 获取大厅版本类型
            version = encryptParamsSDK.getVersion();

            //得到信息后展示出来
            userName.setText(loginId);
            //刷卡应该提示的是用户名或密码错误
        }

        if (TextUtils.isEmpty(encryptParams))
            try {
//                ToastUtils.toast(LoginActivity.this,"未获取到用户信息");
                appPortSDK.goToAppHall(getPackageName());
            } catch (Exception e) {
                e.printStackTrace();
            }

    }


    /**
     * TODO 表示关闭寻卡功能
     *
     * @see android.app.Fragment#onPause()
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * initialize:(初始化). <br/>
     * date: 2014年9月3日 <br/>
     *
     * @author lg
     */
    private void initialize() {
        if (action == null) {
            if (actionLsnr == null)
                actionLsnr = new LoginActionListener();
            action = new BusinessAction(actionLsnr);
        }
        if (asyncTask == null) {
            asyncTask = new BusinessAsyncTask(action,
                    new AbstractAsyncCallBack() {

                        @Override
                        public void start(Bundle msgData) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void processing(Bundle msgData) {
                            // TODO Auto-generated method stub
                            if (msgData.containsKey(IActionType.LOGIN_LOGIN)) {
                                prsDlg.showMsg(msgData
                                        .getString(IActionType.LOGIN_LOGIN));
                                prsDlg.setStop(false);

                            }
                        }

                        /**
                         * TODO 登录失败，只处理登录验证失败的信息，基础数据更新的错误提示不处理
                         *
                         * @see com.hd.hse.business.task.AbstractAsyncCallBack#error(android.os.Bundle)
                         */
                        @Override
                        public void error(Bundle msgData) {
                            // TODO Auto-generated method stub
                            if (msgData.containsKey(IActionType.LOGIN_LOGIN)) {
                                unLock = true;
                                isLoginning = false;
                                prsDlg.dismiss();
                                prsDlg.setStop(true);
                                String msg = msgData
                                        .getString(IActionType.LOGIN_LOGIN);
                                ToastUtils.toast(LoginActivity.this, msg);
                                //取消登录后，自动重新登录,只针对辽阳
//                                if (encryptParams != null) {
//                                    onResume();
//                                }
                            }
                        }

                        /**
                         * TODO 登录成功，只处理登录成功的信息
                         *
                         * @see com.hd.hse.business.task.AbstractAsyncCallBack#end(android.os.Bundle)
                         */
                        @Override
                        public void end(Bundle msgData) {
                            // TODO Auto-generated method stub
                            if (msgData.containsKey(IActionType.LOGIN_LOGIN)) {
                                unLock = true;
                                // 登陆成功
                                isLoginning = false;
                                // Intent intent = new
                                // Intent(LoginActivity.this,
                                // MainActivity.class);
                                // startActivity(intent);
                                // 关系表查询是否判断imei
                                IQueryRelativeConfig relation = new QueryRelativeConfig();
                                RelationTableName relationEntity = new RelationTableName();
                                relationEntity
                                        .setSys_type(IRelativeEncoding.ISLICENCE);
                                boolean islicence = relation
                                        .isHadRelative(relationEntity);
                                // Log.e("islicence", islicence+"");
                                // 校验licence
                                if (!islicence || LicenceCheck()) {
                                    JumpNextPage();
                                } else {
                                    ToastUtils.toast(LoginActivity.this,
                                            "此手持设备未取得北京海顿公司通信授权！！");
                                }
                                prsDlg.setStop(true);
                                prsDlg.dismiss();
                            }
                        }
                    });
        }
    }

    private OnClickListener clickImei = new OnClickListener() {
        @Override
        public void onClick(View v) {
            TelephonyManager tm = (TelephonyManager) LoginActivity.this
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            AlertDialog.Builder builder = new Builder(LoginActivity.this);
            builder.setMessage("IMEI: " + imei);
            builder.setNegativeButton("确   定",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        }
    };

    /**
     * onLoginClick:(登录按钮监听). <br/>
     * date: 2014年9月4日 <br/>
     *
     * @author zl
     * @param arg0
     */
    private OnClickListener loginLsn = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            if (LoginCheckUpConfig.isCheckUp) {
                checkVersionUp("click");
            } else {

                if (StringUtils.isEmpty(userName.getText().toString())) {
                    ToastUtils.toast(LoginActivity.this, "请输入用户名后再登录！");
                    return;
                }
                if (isLoginning) {
                    // 表示不能多次点击登录
                    return;
                }

                PersonCard user = new PersonCard();
                user.setPersonid(userName.getText().toString());
                user.setPassword(passWord.getText().toString());
                user.setRememberPWD(checkboxrememberpwdBox.isChecked());
                user.setAutoLogin(checkboxAutoLogin.isChecked());

                // 登录验证
                loginCheck(user);
            }
        }
    };

    /**
     * loginCheck:(登录验证). <br/>
     * date: 2014年9月12日 <br/>
     *
     * @param user
     * @author lxf
     */
    private void loginCheck(PersonCard user) {
        if (null != user) {
            try {
                isLoginning = true;
                // 异步处理
                asyncTask.execute(IActionType.LOGIN_LOGIN, user);
                prsDlg = new ProgressDialog(this, true, "本地登录验证");
                prsDlg.show();
                if (receiver == null) {
                    IntentFilter filter = new IntentFilter();
                    filter.addAction("com.hd.hse.phone.DIALOG_CLOSE");
                    receiver = new MyReceiver();
                    registerReceiver(receiver, filter);
                }
            } catch (HDException e) {
                ToastUtils.toast(this, e.getMessage());
            }
        }
    }

    /**
     * ClassName: useNameTouchClick (创建下拉菜单监听事件)<br/>
     * date: 2015年3月4日 <br/>
     *
     * @author zhaofeng
     * @version LoginActivity
     */
    private class useNameTouchClick implements ITouchOnClick {

        @Override
        public void touchOnClick(View view) {
            hideInputKeyboard(view);
            if (flag) {
                // 显示PopupWindow窗口
                popupWindwShowing(view);
            }
        }

    }

    /**
     * ClassName: useNameOnLayoutEvent (useName布局监听事件)<br/>
     * date: 2015年5月5日 <br/>
     *
     * @author zhaofeng
     * @version LoginActivity
     */
    private class useNameOnLayoutEvent implements IOnLayoutEvent {

        @Override
        public void onLayoutListener(View view) {
            // TODO Auto-generated method stub
            // userName布局结束后，计算相对屏幕的坐标
            calculation(view);
        }

    }

    /**
     * 网络设置监听 onConfigClick:(). <br/>
     * date: 2014年9月4日 <br/>
     *
     * @author HD
     * @param v
     */
    private OnClickListener configLsn = new OnClickListener() {

        @Override
        public void onClick(View arg0) {
            if (isWebSite) {
                return;
            }
            isWebSite = true;
            Intent intent = new Intent(LoginActivity.this,
                    LoginConfigActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.hd_hse_common_zoomin,
                    R.anim.hd_hse_common_zoomout); // Activity切换动画
        }
    };

    /**
     * 没有在onCreate方法中调用initWedget()，而是在onWindowFocusChanged方法中调用，
     * 是因为initWedget()中需要获取PopupWindow浮动下拉框依附的组件宽度，在onCreate方法中是无法获取到该宽度的
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        while (!flag) {
            initWedget();
            flag = true;
        }

    }

    /**
     * 初始化界面控件
     */
    private void initWedget() {
        // 初始化Handler,用来处理消息
        handler = new Handler(LoginActivity.this);
        // 获取下拉框依附的组件宽度
        int width = userName.getWidth();
        pwidth = width;
        // 初始化PopupWindow
        initPopuWindow();
    }


    /**
     * 获取上次登录人的信息
     * <p>
     * Created by wangdanfeng on 2016年3月31日
     */
    private void getLastUserInfo() {
        // TODO Auto-generated method stub
        lastuserinfo.clear();
        String[] str = {"usercode", "password", "autologin"};
        List<RecentlyLoginPerson> rlpList;
        try {
            rlpList = (List) action.queryEntities(RecentlyLoginPerson.class,
                    str, "1=1 order by createdate desc");
            if (rlpList.size() > 0) {
                String lastusercode = rlpList.get(0).getUsercode();
                String lastuserpassword = rlpList.get(0).getPassword();
                int lastuserisautologin = rlpList.get(0).getAutologin();
                lastuserinfo.put("usercode", lastusercode);
                lastuserinfo.put("autologin", "" + lastuserisautologin);
                if (lastuserpassword != null
                        && !StringUtils.isEmpty(lastuserpassword)) {
                    lastuserinfo.put("password", lastuserpassword);
                }

            }

        } catch (HDException e) {

            e.printStackTrace();
        }

    }

    /**
     * 初始化填充Adapter所用List数据 从数据库中获取最近5个登录用户
     *
     * @throws SQLException
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void initDatas() {
        datas.clear();
        String[] str = {"usercode", "password"};
        try {
            rlpList = (List) action.queryEntities(RecentlyLoginPerson.class,
                    str, "1=1 order by sysorder asc");
            int count = rlpList.size();
            if (count > 0) {
                if (count > 5) {
                    count = 5;
                }
                for (int i = 0; i < count; i++) {
                    datas.add(rlpList.get(i).getUsercode());
                }
            } else {
                datas.add("");
            }
        } catch (HDException e) {
            ToastUtils.toast(this, "系统初始化出错，请联系系统管理员！");
        }
    }

    /**
     * 初始化PopupWindow
     */
    private void initPopuWindow() {
        // PopupWindow浮动下拉框布局
        View loginwindow = (View) this.getLayoutInflater().inflate(
                R.layout.hd_hse_main_app_login_options, null);
        listView = (ListView) loginwindow
                .findViewById(R.id.hd_hse_main_app_option_lv01);
        // 设置自定义Adapter
        optionsAdapter = new OptionsAdapter(this, handler, datas);
        listView.setAdapter(optionsAdapter);
        selectPopupWindow = new PopupWindow(loginwindow, pwidth,
                LayoutParams.WRAP_CONTENT, true);
        selectPopupWindow.setOutsideTouchable(true);
        // 这一句是为了实现弹出PopupWindow后，当点击屏幕其他部分及Back键时PopupWindow会消失，
        selectPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        selectPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        selectPopupWindow
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    /**
     * 显示PopupWindow窗口
     *
     * @param popupwindow
     */
    public void popupWindwShowing() {
        initDatas();
        // 将selectPopupWindow作为parent的下拉框显示，并指定selectPopupWindow在Y方向上向上偏移3pix，
        selectPopupWindow.showAsDropDown(userName, 0, -3);
    }

    public void popupWindwShowing(View view) {
        initDatas();
        // 将selectPopupWindow作为parent的下拉框显示，并指定selectPopupWindow在Y方向上向上偏移3pix，
        selectPopupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, screenX,
                screenY);
    }

    /**
     * 处理Hander消息
     */
    public boolean handleMessage(Message message) {
        Bundle data = message.getData();
        switch (message.what) {
            case 1:
                // 选中下拉项，下拉框消失
                int selIndex = data.getInt("selIndex");
                userName.setText(datas.get(selIndex));
                selectPopupWindow.dismiss();
                // 将光标移至最后
                userName.setSelection(userName.getText().length());
                if (rlpList != null && rlpList.size() != 0) {
                    if (rlpList.get(selIndex).getPassword() != null
                            && !rlpList.get(selIndex).getPassword().equals("")) {
                        passWord.setText(rlpList.get(selIndex).getPassword());
                        checkboxrememberpwdBox.setChecked(true);
                    } else {
                        checkboxrememberpwdBox.setChecked(false);
                    }
                }


                break;
            case 2:
                // 移除下拉项数据
                int delIndex = data.getInt("delIndex");
                datas.remove(delIndex);
                // 刷新下拉列表
                optionsAdapter.notifyDataSetChanged();
                break;
        }
        return false;
    }

    public void onClick(View arg0) {
        // TODO Auto-generated method stub

    }

    /**
     * startTasks:(启动后台任务). <br/>
     * date: 2014年11月5日 <br/>
     *
     * @author lg
     */
    private void startTasks() {
        lstRcvr = ReceiverManager.registerReceivers(this);
        lstPdIntent = ReceiverManager.startTasks(this);
    }

    /**
     * destroyTasks:(后台任务注销). <br/>
     * date: 2014年11月5日 <br/>
     *
     * @author lg
     */
    private void destroyTasks() {
        ReceiverManager.endTasks(this, lstPdIntent);
        ReceiverManager.unRegisterReceivers(this, lstRcvr);
    }

    /**
     * TODO
     *
     * @see android.app.Activity#onDestroy()
     */
    @Override
    protected void onDestroy() {
        destroyTasks();
        super.onDestroy();
    }


    @Override
    public void actionByCardID(String cardID) {

        if (cardnum != null && cardnum.length() != 0) {
            if (LoginCheckUpConfig.isCheckUp) {
                checkVersionUp(cardID);
            } else {

                PersonCard user = new PersonCard();
                user.setPcardnum(cardID);
                user.setRememberPWD(checkboxrememberpwdBox.isChecked());
                user.setAutoLogin(checkboxAutoLogin.isChecked());
                if (unLock) {
                    unLock = false;
                    loginCheck(user);
                }

            }

        }
    }

    private void checkVersionUp(final String cardID) {
        GetVersionInfo getInfo = new GetVersionInfo(LoginActivity.this);
        BusinessAction action = new BusinessAction(getInfo);
        BusinessAsyncTask asyncTask = new BusinessAsyncTask(action,
                new AbstractAsyncCallBack() {

                    @Override
                    public void start(Bundle msgData) {

                    }

                    @Override
                    public void processing(Bundle msgData) {
                        sjDlg.showMsg("正在查询升级信息...");
                    }

                    @Override
                    public void error(Bundle msgData) {
                        sjDlg.dismiss();
                        logger.error(msgData);
                        ToastUtils.toast(LoginActivity.this, "连接服务器失败！");
                    }

                    @Override
                    public void end(Bundle msgData) {
                        AysncTaskMessage msg = (AysncTaskMessage) msgData
                                .getSerializable("p");
                        sjDlg.dismiss();
                        if (msg.getReturnResult() instanceof Map) {
                            Map<String, String> hasmap = (HashMap<String, String>) msg
                                    .getReturnResult();
                            //获取网络时间
                            if (hasmap
                                    .containsKey(PadInterfaceRequest.KEYSERVERDATE)) {
                                Long severDate = Long.parseLong(hasmap
                                        .get(PadInterfaceRequest.KEYSERVERDATE));
                                ServerDateManager.setServerDate(severDate);
                            } else {
                                //没有获取到服务器时间把服务器时间置空
                                ServerDateManager.setServerDate(null);
                            }

                            if (hasmap
                                    .containsKey(PadInterfaceRequest.KEYPADVERSION)) {
                                String newVersion = hasmap
                                        .get(PadInterfaceRequest.KEYPADVERSION);
                                PackageManager packageManger = LoginActivity.this
                                        .getPackageManager();

                                String[] versionList = LoginActivity.this
                                        .getResources()
                                        .getStringArray(
                                                R.array.hd_hse_version_list);
                                boolean needUpdate = true;
                                for (String v : versionList) {
                                    if (v.equals(newVersion)) {
                                        needUpdate = false;
                                        break;
                                    }
                                }
                                if (needUpdate) {
//                                        actionBarMainMenu.setVersionUp(true);


                                    // 进行升级提示
                                    VersionUp vp = new VersionUp(
                                            LoginActivity.this);
                                    vp.getVersionUpgradeInfo();

                                } else {

                                    if (!cardID.equals("click")) {
                                        PersonCard user = new PersonCard();
                                        user.setPcardnum(cardID);
                                        user.setRememberPWD(checkboxrememberpwdBox.isChecked());
                                        user.setAutoLogin(checkboxAutoLogin.isChecked());

                                        if (unLock) {
                                            unLock = false;
                                            loginCheck(user);
                                        }


                                    } else {

                                        if (StringUtils.isEmpty(userName.getText().toString())) {
                                            ToastUtils.toast(LoginActivity.this, "请输入用户名后再登录！");
                                            return;
                                        }
                                        if (isLoginning) {
                                            // 表示不能多次点击登录
                                            return;
                                        }

                                        PersonCard user = new PersonCard();
                                        user.setPersonid(userName.getText().toString());
                                        user.setPassword(passWord.getText().toString());
                                        user.setRememberPWD(checkboxrememberpwdBox.isChecked());
                                        user.setAutoLogin(checkboxAutoLogin.isChecked());


                                        if (unLock) {
                                            unLock = false;
                                            loginCheck(user);
                                        }
                                        // 登录验证
//                                        loginCheck(user);


                                    }

                                }
                            }
                        }
                    }
                });
        try {

            asyncTask.execute(null, new Object[]{});
            sjDlg = new ProgressDialog(this, true, "升级验证");
            sjDlg.show();
        } catch (HDException e) {
            logger.error(e);
        }
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        onPause();
        super.finish();
        SystemApplication.getInstance().exitProcess();
    }

    /**
     * JumpNextPage:(跳转到下一个页面). <br/>
     * date: 2015年4月23日 <br/>
     *
     * @author lxf
     */
    public void JumpNextPage() {
        isLogout = true;
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            if (prsDlg != null && prsDlg.isCancel()
                    && actionLsnr instanceof LoginActionListener) {
                ((LoginActionListener) actionLsnr).setIsCancel();
            }
        }

    }

    /**
     * licence验证
     */
    private boolean LicenceCheck() {
        BaseDao dao = new BaseDao();
        String sql = "select * from ud_sy_licence";
        try {
            List<Licence> allLicence = (List<Licence>) dao.executeQuery(sql,
                    new EntityListResult(Licence.class));
            if (allLicence != null && allLicence.size() > 0) {
                // 表不为空，查询imei是否存在
                TelephonyManager tm = (TelephonyManager) this
                        .getSystemService(Context.TELEPHONY_SERVICE);
                String imei = tm.getDeviceId();
                String imeiSql = "select * from ud_sy_licence where upper(imeia)= upper('"
                        + imei + "') or upper(imeib)= upper('" + imei + "')";
                List<Licence> licences = (List<Licence>) dao.executeQuery(
                        imeiSql, new EntityListResult(Licence.class));
                if (licences != null && licences.size() > 0) {
                    return true;
                }
            } else {
                return true;
            }
        } catch (DaoException e) {
            e.printStackTrace();
            return true;
        }

        return false;
    }

}
