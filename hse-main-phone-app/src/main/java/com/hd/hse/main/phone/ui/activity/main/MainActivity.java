package com.hd.hse.main.phone.ui.activity.main;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.AysncTaskMessage;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.MessageDialog;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.camera.CameraCaptureActivity;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.common.module.phone.ui.activity.LocationSwCard;
import com.hd.hse.common.module.phone.ui.activity.SystemApplication;
import com.hd.hse.common.module.phone.ui.event.homepage.IAppModuleClick;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.dc.business.common.weblistener.MessageEvent;
import com.hd.hse.dc.business.common.weblistener.MsgIsReadListener;
import com.hd.hse.dc.business.common.weblistener.down.GainRemoteApprInfo;
import com.hd.hse.dc.business.listener.common.GetVersionInfo;
import com.hd.hse.dc.phone.ui.activity.vision.VersionUp;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.common.PushMessage;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.entity.workorder.RemoteApprInfo;
import com.hd.hse.entity.workorder.RemoteApprLine;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.main.business.main.MainActionListener;
import com.hd.hse.main.business.remoteappr.CheckRemoteApprBusiness;
import com.hd.hse.main.business.remoteappr.DisagreeRemoteApprBusiness;
import com.hd.hse.main.business.remoteappr.RemoteApprBusiness;
import com.hd.hse.main.business.remoteappr.RemoteApprBusiness.UpRemoteApprInfoListener;
import com.hd.hse.main.phone.R;
import com.hd.hse.main.phone.ui.activity.MessageListAdapter;
import com.hd.hse.main.phone.ui.activity.View.MainActivity_Linearlayout;
import com.hd.hse.main.phone.ui.activity.remoteappr.RemoteApprDisagreeBusiness;
import com.hd.hse.main.phone.ui.activity.remoteappr.RemoteApprInfoBrowse;
import com.hd.hse.main.phone.ui.activity.remoteappr.RemoteapprDialog;
import com.hd.hse.main.phone.ui.adapter.remoteappr.RemoteApprListAdapter;
import com.hd.hse.main.phone.ui.receiver.HDNotification;
import com.hd.hse.main.phone.ui.receiver.UdpClient;
import com.hd.hse.main.phone.ui.service.LockService;
import com.hd.hse.main.phone.ui.service.NotificationService;
import com.hd.hse.padinterface.PadInterfaceRequest;
import com.hd.hse.service.checkdata.CheckDataConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.service.workorder.WorkTaskDBSrv;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.system.SystemProperty;

import org.apache.log4j.Logger;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

//import com.hd.hse.cc.phone.ui.activity.close.WorkOrderCloseActivity;
//import com.hd.hse.osr.phone.ui.activity.workorder.WorkOrderActivity;

public class MainActivity extends BaseFrameActivity implements OnClickListener {
    /**
     * logger:TODO(????????????).
     */
    private static Logger logger = LogUtils.getLogger(MainActivity.class);
    // private ViewPager viewPager;
    /**
     * currentItem:TODO(????????????).
     */
    // private int currentItem = 0; // ????????????????????????
    private ScheduledExecutorService scheduledExecutorService;
    private int[] rid = new int[]{R.id.textView1, R.id.textView2,
            R.id.textView3, R.id.textView4, R.id.textView5, R.id.textView6,
            R.id.textView7, R.id.textView8};

    /**
     * listAppModule:TODO(????????????????????????).
     */
    private List<AppModule> listAppModule;

    /**
     * actionBarMainMenu:TODO().
     */
    private ActionBarMainMenu actionBarMainMenu;

    /**
     * metrics:TODO(???????????????).
     */
    private DisplayMetrics metrics;

    /**
     * refresh:TODO(?????????????????????).
     */
    private View refresh;
    private TextView nameTV;
    private TextView departmentTV;

    private ListView notifiListView;
    private ListView waiteWorkListView;

    private List<PushMessage> notificationList;
    private List<PushMessage> waiteWorkList;

    private MessageListAdapter waiteWorkListAdapter;

    private Handler handler;
    private final int initdatasuccess = 0x1551;
    private final int initdatafail = 0x1552;
    private final int updatesuccess = 0x1553;
    private final int updatefail = 0x1554;
    private CheckDataConfig checkDataConfig = new CheckDataConfig();
    private TextView tvErrorReminder;

    private ProgressDialog dialog;
    private RemoteApprListAdapter adapter;
    private List<RemoteApprInfo> remoteApprInfos;
    private TextView tvAppr;

    private MainActivity_Linearlayout ll;
    private LinearLayout dot_linear;
    private ImageView dot1_image;
    private ImageView dot2_image;
    private ImageView dot3_image;
    private Handler hd = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {


                List<WorkOrder> lst = (List<WorkOrder>) msg.obj;
                manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                Intent intent = new Intent();
                PendingIntent broadcast = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

                for (int i = 0; i < lst.size(); i++) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());

                    Notification notification = builder
                            .setContentTitle("???????????? " + lst.get(i).getZyname())
                            .setContentText(lst.get(i).getSddept_desc())
                            .setSubText(lst.get(i).getSjendtime() + "??????????????????!")
                            .setWhen(ServerDateManager.getCurrentTimeMillis())
                            .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.hd_hse_phone_logo))
                            .setSmallIcon(R.drawable.hd_hse_phone_logo) //????????????
                            .setAutoCancel(true)
                            // ???????????????????????????
                            .setContentIntent(broadcast)
                            .build();

                    manager.notify(Integer.parseInt(lst.get(i).getUd_zyxk_zysqid()), notification);

                }


            }
        }
    };
    private boolean isExit;
    private NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create LockService
        EventBus.getDefault().register(this);

        Intent intent = new Intent(MainActivity.this, LockService.class);
        startService(intent);

        // ??????????????????
        Intent notificationService = new Intent(MainActivity.this,
                NotificationService.class);
        startService(notificationService);

        setContentView(R.layout.hd_hse_main_phone_app_layout_main);
        dot_linear = (LinearLayout) findViewById(R.id.linearlayout_dot);

        ll = (MainActivity_Linearlayout) findViewById(R.id.weather_layout);
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // ??????????????????????????????
        refresh = (View) this.findViewById(R.id.refresh);
        refresh.setOnClickListener(new asyncClickRefresh());
        // ?????????????????????
        initTime();
        initParam();

        initRemoteApprData();
        // ??????????????????
        checkVersionUp();
        handler = new Handler() {
            @Override
            public void dispatchMessage(Message msg) {
                super.dispatchMessage(msg);
                if (msg.what == initdatafail) {
                    tvErrorReminder.setVisibility(View.VISIBLE);
                    tvErrorReminder.setTextColor(Color.RED);
                    tvErrorReminder.setText("?????????????????????");
                    tvErrorReminder.setTag(initdatafail);
                } else if (msg.what == updatefail) {
                    tvErrorReminder.setVisibility(View.VISIBLE);
                    tvErrorReminder.setTextColor(Color.parseColor("#FDA531"));
                    tvErrorReminder.setText("????????????????????????");
                    tvErrorReminder.setTag(updatefail);
                } else if (msg.what == initdatasuccess) {
                    tvErrorReminder.setVisibility(View.VISIBLE);
                    tvErrorReminder.setTextColor(Color.parseColor("#278B54"));
                    tvErrorReminder.setText("????????????");
                    tvErrorReminder.setTag(initdatasuccess);
                }
            }
        };
        Loop loop = new Loop();
        new Thread(loop).start();
    }

    /**
     * ???????????????????????????
     */
    private void initRemoteApprData() {
        remoteApprInfos = new ArrayList<RemoteApprInfo>();
        adapter = new RemoteApprListAdapter(MainActivity.this, remoteApprInfos);
        waiteWorkListView.setAdapter(adapter);
        waiteWorkListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent(MainActivity.this,
                        RemoteApprInfoBrowse.class);
                intent.putExtra(RemoteApprInfoBrowse.class.getName(),
                        remoteApprInfos.get(arg2));
                startActivity(intent);
            }
        });

    }

    /**
     * ?????????????????????????????????
     */
    private List<RemoteApprLine> getRemoteApprData() {
        PersonCard personCard = SystemProperty.getSystemProperty()
                .getLoginPerson();

        List<RemoteApprLine> upData = new ArrayList<RemoteApprLine>();

        for (int i = 0; i < remoteApprInfos.size(); i++) {
            if (adapter.getCheckBoxChecked(i)) {
                List<RemoteApprLine> remoteApprLines = remoteApprInfos.get(i)
                        .getUD_ZYXK_REMOTEAPPROVE_LINE();
                for (RemoteApprLine remoteApprLine : remoteApprLines) {
                    remoteApprLine.setApprove_person_name(personCard
                            .getPersonid_desc());
                    remoteApprLine
                            .setApprove_personid(personCard.getPersonid());
                    remoteApprLine.setSPTIME(SystemProperty.getSystemProperty()
                            .getSysDateTime());
                    upData.add(remoteApprLine);
                }
            }
        }

        return upData;

    }

    /**
     * ????????????????????????
     */
    public void checkDb() {

        try {
            //????????????????????????????????????????????????????????????????????????????????????????????????????????????1
            int initdatavalue = checkDataConfig.getinitDataValue();
            int updatevalue = checkDataConfig.getupdateValue();
            if (initdatavalue == 0) {
                handler.sendEmptyMessage(initdatafail);
                return;
            }
            if (initdatavalue == 1) {
                if (updatevalue == 0) {
                    handler.sendEmptyMessage(updatefail);
                } else {
                    handler.sendEmptyMessage(initdatasuccess);
                }
            }
        } catch (HDException e) {

            e.printStackTrace();
            // ToastUtils.toast(getApplicationContext(), "???????????????");
            logger.error(e.getMessage());
        }

    }

    /**
     * initParam:(?????????????????????). <br/>
     * date: 2015???1???9??? <br/>
     *
     * @author lych
     */
    private void initTime() {

        TextView timeTV = (TextView) (findViewById(R.id.time));
        TextView weekTV = (TextView) findViewById(R.id.week);
        timeTV.setText(ServerDateManager.getYear() + " - " + ServerDateManager.getMonth() + " - " + ServerDateManager.getDay());
        weekTV.setText(ServerDateManager.getWeek());
    }

    /**
     * initParam:(?????????????????????). <br/>
     * date: 2015???1???6??? <br/>
     *
     * @author lxf
     */
    private void initParam() {
        initAppModule();


        tvErrorReminder = (TextView) findViewById(R.id.error_reminder);
        tvErrorReminder.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if ((int) v.getTag() == initdatafail) {
                    // ????????????????????????????????????
                    actionBarMainMenu.initData();
                } else if ((int) v.getTag() == updatefail) {
                    // ?????????????????????????????????????????????
                    actionBarMainMenu.updateData();
                }

            }
        });
        notifiListView = (ListView) findViewById(R.id.notification_listview);
        waiteWorkListView = (ListView) findViewById(R.id.waite_work_listview);
        tvAppr = (TextView) findViewById(R.id.hd_hse_main_phone_app_layout_main_tv_appr);
        tvAppr.setOnClickListener(this);

//        dot2_image.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(MainActivity.this,ManagerMainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        dot3_image.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(MainActivity.this,WorkerInitialPageActivity.class);
//                startActivity(intent);
//            }
//        });

        // viewPager = (ViewPager) findViewById(R.id.testViewPager);

        // dotGroupButton = (RadioGroup) findViewById(R.id.dotGroupButton);

        nameTV = (TextView) findViewById(R.id.name);
        PersonCard personCard = SystemProperty.getSystemProperty()
                .getLoginPerson();
        if (personCard != null) {
            String name = personCard.getPersonid_desc();
            // name = name.length() > 4 ? name.substring(0, 3) + "..." : name;
            nameTV.setText(name);
        } else {
            nameTV.setText("");
        }
        departmentTV = (TextView) findViewById(R.id.department);
        if (personCard != null) {
            String department = personCard.getDepartment_desc();
            departmentTV.setText(department);
        } else {
            departmentTV.setText("");
        }
        // ?????????????????????
        setCustomActionBar(true, eventLst, new String[]{IActionBar.IV_LMORE,
                IActionBar.TV_TITLE, IActionBar.TV_MORE});
        // ????????????????????????
        setActionBartitleContent("????????????", false);
        // ?????????????????????
        actionBarMainMenu = new ActionBarMainMenu(this);
        // ????????????
        // viewPager.setOnPageChangeListener(pageChangeListner);
    }

    public void initAppModule() {
        // ????????????????????????
        listAppModule = SystemProperty.getSystemProperty()
                .getMainAppModulelist("SJ");
        // ????????????app????????????
        setNavContent(listAppModule, "hse-main-phone-app");
        // ?????????????????????????????????????????????????????????
        if (listAppModule.size() == 2) {
            for (AppModule app : listAppModule) {
                if (app.getCode().equals("hse-main-phone-app")) {
                    for (AppModule appModule : listAppModule) {
                        if (!appModule.equals("hse-main-phone-app")) {
                            try {
                                IAppModuleClick appClick = newAppModuleClick(appModule
                                        .getClickdealclass());
                                appClick.appModuleOnClick(appModule);
                            } catch (HDException e) {
                                logger.error(e);
                                ToastUtils.imgToast(MainActivity.this,
                                        R.drawable.hd_hse_common_msg_wrong,
                                        e.getMessage());
                            }
                        }
                    }
                }
            }
        }
        if (listAppModule.size() == 1
                && !listAppModule.get(0).getCode().equals("hse-main-phone-app")) {
            try {
                IAppModuleClick appClick = newAppModuleClick(listAppModule.get(
                        0).getClickdealclass());
                appClick.appModuleOnClick(listAppModule.get(0));
            } catch (HDException e) {
                logger.error(e);
                ToastUtils.imgToast(MainActivity.this,
                        R.drawable.hd_hse_common_msg_wrong, e.getMessage());
            }
        }
        dot1_image = new ImageView(this);
        dot2_image = new ImageView(this);
        dot3_image = new ImageView(this);
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setMargins(0, 0, 82, 0);  //????????????
        dot1_image.setLayoutParams(layout);
        dot2_image.setLayoutParams(layout);
        LinearLayout.LayoutParams layoutp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutp.setMargins(0, 0, 0, 0);  //????????????
        dot3_image.setLayoutParams(layoutp);
        dot1_image.setImageResource(R.drawable.yuan1);
        dot2_image.setImageResource(R.drawable.yuan2);
        dot3_image.setImageResource(R.drawable.yuan2);

        if (SystemProperty.getSystemProperty().isBothShouYe()) {

            dot_linear.removeAllViews();
            dot_linear.addView(dot1_image);
            dot_linear.addView(dot2_image);
            dot_linear.addView(dot3_image);

        } else if (SystemProperty.getSystemProperty().isLanZhouMShouYe() ||
                SystemProperty.getSystemProperty().isLanZhouWShouYe()) {

            dot_linear.removeAllViews();
            dot_linear.addView(dot1_image);
            dot_linear.addView(dot3_image);

        } else {
            dot_linear.removeAllViews();
        }


    }

    /**
     * initNotificationView:(????????????????????????????????????). <br/>
     * date: 2015???9???23??? <br/>
     *
     * @author LiuYang ???????????????2017/7/20?????????????????????????????????????????????
     */
    private void initNotificationView() {
        String personid = null;
        PersonCard personCard = SystemProperty.getSystemProperty()
                .getLoginPerson();
        if (personCard != null) {
            personid = personCard.getPersonid();

        } else {
            ToastUtils.toast(getApplicationContext(), "??????personid ??????");
        }
        GainRemoteApprInfo gainRemoteApprInfo = new GainRemoteApprInfo(personid);
        BusinessAction action = new BusinessAction(gainRemoteApprInfo);
        BusinessAsyncTask task = new BusinessAsyncTask(action,
                new AbstractAsyncCallBack() {

                    @Override
                    public void start(Bundle msgData) {

                    }

                    @Override
                    public void processing(Bundle msgData) {

                    }

                    @Override
                    public void error(Bundle msgData) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.cancel();
                        }
                        ToastUtils.toast(getApplicationContext(), "??????????????????????????????");
                    }

                    @Override
                    public void end(Bundle msgData) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.cancel();
                        }
                        AysncTaskMessage msg = (AysncTaskMessage) msgData
                                .getSerializable("p");
                        remoteApprInfos.clear();
                        List<RemoteApprInfo> infos = (List<RemoteApprInfo>) msg
                                .getReturnResult();
                        if (infos != null) {
                            remoteApprInfos.addAll(infos);
                        }
                        if (remoteApprInfos == null
                                || remoteApprInfos.size() == 0) {
                            tvAppr.setVisibility(View.GONE);
                        } else {
                            tvAppr.setVisibility(View.VISIBLE);
                        }
                        adapter.setRemoteApprInfos(remoteApprInfos);
                    }
                });
        try {
            task.execute("");
            if (dialog == null) {
                dialog = new ProgressDialog(MainActivity.this, "??????????????????????????????...");
            }
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        } catch (HDException e) {
            logger.error(e);
            ToastUtils.imgToast(MainActivity.this,
                    R.drawable.hd_hse_common_msg_wrong, "????????????????????????");
        }

    }

    private void checkVersionUp() {
        GetVersionInfo getInfo = new GetVersionInfo(MainActivity.this);
        BusinessAction action = new BusinessAction(getInfo);
        BusinessAsyncTask asyncTask = new BusinessAsyncTask(action,
                new AbstractAsyncCallBack() {

                    @Override
                    public void start(Bundle msgData) {

                    }

                    @Override
                    public void processing(Bundle msgData) {

                    }

                    @Override
                    public void error(Bundle msgData) {
                        logger.error("??????????????????????????????");
                    }

                    @Override
                    public void end(Bundle msgData) {
                        AysncTaskMessage msg = (AysncTaskMessage) msgData
                                .getSerializable("p");
                        if (msg.getReturnResult() instanceof Map) {
                            Map<String, String> hasmap = (HashMap<String, String>) msg
                                    .getReturnResult();
                            if (hasmap
                                    .containsKey(PadInterfaceRequest.KEYPADVERSION)) {
                                String newVersion = hasmap
                                        .get(PadInterfaceRequest.KEYPADVERSION);
                                PackageManager packageManger = MainActivity.this
                                        .getPackageManager();
                                try {
                                    String oldVersion = packageManger
                                            .getPackageInfo(MainActivity.this
                                                    .getPackageName(), 0).versionName;
                                    String[] versionList = MainActivity.this
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
                                        actionBarMainMenu.setVersionUp(true);
                                        // ?????????????????????
                                        setCustomActionBar(true, eventLst,
                                                new String[]{
                                                        IActionBar.IV_LMORE,
                                                        IActionBar.TV_TITLE,
                                                        IActionBar.TV_MORE,
                                                        IActionBar.IMG_REMIND});
                                        // ??????????????????
                                        VersionUp vp = new VersionUp(
                                                MainActivity.this);
                                        vp.getVersionUpgradeInfo();

                                    }
                                } catch (NameNotFoundException e) {
                                    logger.error(e);
                                }
                            }
                        }
                    }
                });
        try {
            asyncTask.execute(null, new Object[]{});
        } catch (HDException e) {
            logger.error(e);
        }
    }

    public void goNextActivity2(Context context, PushMessage msg) {
        String workID = msg.getUd_zyxk_zysqid();
        WorkOrder workOrder = null;
        WorkTaskDBSrv db = new WorkTaskDBSrv();
        try {
            List<WorkOrder> workList = db.queryWorkOrder();
            if (workList != null && !workList.isEmpty()) {
                for (int i = 0; i < workList.size(); i++) {
                    WorkOrder order = (WorkOrder) workList.get(i);
                    if (order.getUd_zyxk_zysqid().equals(workID)) {
                        workOrder = order;
                    }
                }
            }
            if (workOrder == null) {
                ToastUtils.toast(context, "???????????????????????????????????????????????????");
                return;
            }
        } catch (HDException e) {
            e.printStackTrace();
            ToastUtils.toast(context, "???????????????????????????!");
        }
        // ??????????????????????????????
        HDNotification.clearNotification(msg.getUd_zyxk_xxjlid());
        String clazz = "";
        if (msg.getGnbm().equals(UdpClient.GNBM_COLSE)) {
            clazz = "com.hd.hse.cc.phone.ui.event.homepage.CloseCancelChkApp";
        } else if (msg.getGnbm().equals(UdpClient.GNBM_QTJC)) {
            clazz = "com.hd.hse.osr.phone.ui.event.homepage.OnSiteReChkApp";
        }

        try {
            IAppModuleClick appClick = newAppModuleClick(clazz);
            AppModule appModule = new AppModule();
            appModule.setIsswcard(1);
            appClick.appModuleOnClick(appModule);

            setMsgIsRead(msg);
        } catch (HDException e) {
            logger.error(e);
        }
    }

    public void goNextActivity(Context context, PushMessage msg) {
        Intent intent = null;
        String workID = msg.getUd_zyxk_zysqid();
        WorkOrder workOrder = null;
        WorkTaskDBSrv db = new WorkTaskDBSrv();
        try {
            List<WorkOrder> workList = db.queryWorkOrder();
            if (workList != null && !workList.isEmpty()) {
                for (int i = 0; i < workList.size(); i++) {
                    WorkOrder order = (WorkOrder) workList.get(i);
                    if (order.getUd_zyxk_zysqid().equals(workID)) {
                        workOrder = order;
                    }
                }
            }
            if (workOrder == null) {
                ToastUtils.toast(context, "???????????????????????????????????????????????????");
                return;
            }
        } catch (HDException e) {
            e.printStackTrace();
            ToastUtils.toast(context, "???????????????????????????!");
        }
        if (msg.getGnbm().equals(UdpClient.GNBM_COLSE)) {
            // ?????????????????????
            // intent = new Intent(context, WorkOrderCloseActivity.class);

            intent = new Intent(
                    context,
                    getClass("com.hd.hse.cc.phone.ui.activity.close.WorkOrderCloseActivity"));
            intent.putExtra("workorder", workOrder);
        } else if (msg.getGnbm().equals(UdpClient.GNBM_QTJC)) {
            // ????????????????????????
            // intent = new Intent(context, WorkOrderActivity.class);
            intent = new Intent(
                    context,
                    getClass("com.hd.hse.osr.phone.ui.activity.workorder.WorkOrderActivity"));
            Bundle bundle = new Bundle();
            PDAWorkOrderInfoConfig workInfoConfig = new PDAWorkOrderInfoConfig();
            workInfoConfig.setPscode(IConfigEncoding.FC);
            workInfoConfig.setContype(IConfigEncoding.GAS_TYPE);
            workInfoConfig.setDycode(IConfigEncoding.FC_GAS_NUM);
            workInfoConfig.setSname("????????????");
            workInfoConfig.setContypedesc("????????????");
            WorkOrder order = null;
            try {
                queryWorkInfo = new QueryWorkInfo();
                order = queryWorkInfo.querySiteAuditBasicInfo(workOrder,
                        IConfigEncoding.FC, null);
                workOrder = order;
                List<SuperEntity> listPDAConfigInfo = new ArrayList<SuperEntity>();
                listPDAConfigInfo.add(workInfoConfig);
                workInfoConfig.setChild(PDAWorkOrderInfoConfig.class.getName(),
                        listPDAConfigInfo);
                workOrder.clearChild(PDAWorkOrderInfoConfig.class.getName());
                workOrder.setChild(PDAWorkOrderInfoConfig.class.getName(),
                        listPDAConfigInfo);
                bundle.putSerializable("workOrder", workOrder);
                intent.putExtras(bundle);
            } catch (HDException e) {
                logger.error(e.getMessage());
                ToastUtils.imgToast(getApplicationContext(),
                        R.drawable.hd_hse_common_msg_wrong, "????????????????????????????????????");
            }
        }
        setMsgIsRead(msg);
        startActivity(intent);
    }

    private void setMsgIsRead(final PushMessage msg) {
        MsgIsReadListener listener = new MsgIsReadListener(
                msg.getUd_zyxk_xxjlid() + "");
        BusinessAction action = new BusinessAction(listener);
        BusinessAsyncTask task = new BusinessAsyncTask(action,
                new AbstractAsyncCallBack() {

                    @Override
                    public void start(Bundle msgData) {

                    }

                    @Override
                    public void processing(Bundle msgData) {

                    }

                    @Override
                    public void error(Bundle msgData) {
                        ToastUtils.imgToast(MainActivity.this,
                                R.drawable.hd_hse_common_msg_wrong, "?????????????????????");
                    }

                    @Override
                    public void end(Bundle msgData) {
                        waiteWorkList.remove(msg);
                        waiteWorkListAdapter.notifyDataSetChanged();
                    }
                });
        try {
            task.execute("");
        } catch (HDException e) {
            logger.error(e);

        }
    }

    @Override
    protected void onStop() {
        // ???Activity??????????????????????????????
        if (scheduledExecutorService != null)
            scheduledExecutorService.shutdown();
        super.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            logout();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    /**
     * eventLst:TODO(???????????????).
     */
    public IEventListener eventLst = new IEventListener() {

        @Override
        public void eventProcess(int arg0, Object... arg1) throws HDException {
            // TODO Auto-generated method stub
            if (arg0 == IEventType.ACTIONBAR_PHOTOGRAPH_CLICK) {
                Intent intent = new Intent(MainActivity.this,
                        CameraCaptureActivity.class);
                startActivity(intent);
            } else if (arg0 == IEventType.ACTIONBAR_MORE_CLICK) {
                actionBarMainMenu.showAsDropDown((View) arg1[0]);
            }
        }
    };

    private void logout() {
        // TODO Auto-generated method stub
        MessageDialog.Builder builder = new MessageDialog.Builder(this);
        builder.setTitle("??????");
        builder.setMessage("???????????????????");
        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                MainActionListener actionListener = new MainActionListener();
                BusinessAction action = new BusinessAction(actionListener);
                try {
                    // ??????????????????
                    action.action(IActionType.MAIN_LOGOUT);
                    // ????????????
                    // MainActivity.this.finish();
                    HDNotification.clearAllNotification();
                    Intent intent = new Intent(MainActivity.this,
                            NotificationService.class);
                    stopService(intent);
                    SystemApplication.getInstance().exit();
                } catch (HDException e) {
                    // TODO Auto-generated catch block
                    ToastUtils.toast(MainActivity.this, e.getMessage());
                }
                dialog.dismiss();
                // ????????????????????????
            }
        });
        builder.setNegativeButton("??????",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.createWarm().show();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //initAppModule();
        if (SystemProperty.getSystemProperty().isLanZhouMShouYe()) {
            ll.setAbheight(actionBarHeight);
        }

        checkDb();
        QueryRelativeConfig relativeConfig = new QueryRelativeConfig();
        boolean isRemote = relativeConfig
                .isHadRelative(IRelativeEncoding.ISREMOTE);
        if (isRemote) {
            initNotificationView();
        }

    }

    /**
     * ClassName: asyncClickRefresh (?????????????????????)<br/>
     * date: 2015???2???11??? <br/>
     *
     * @author zhaofeng
     * @version MainActivity
     */
    private class asyncClickRefresh implements OnClickListener {

        @Override
        public void onClick(View arg0) {
            QueryRelativeConfig relativeConfig = new QueryRelativeConfig();
            boolean isRemote = relativeConfig
                    .isHadRelative(IRelativeEncoding.ISREMOTE);
            if (isRemote) {
                initNotificationView();
            }
        }
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        if (LocationSwCard.mTimer != null) {
            LocationSwCard.mTimer.cancel();
            LocationSwCard.mTimer = null;
        }
        // ????????????????????????
        SystemProperty.getSystemProperty().setPositionCard(null);
        super.finish();
    }

    /**
     * getClass:(). <br/>
     * date: 2015???6???25??? <br/>
     *
     * @param packagename
     * @return
     * @author lxf
     */
    private Class<?> getClass(String packagename) {

        try {
            return Class.forName(packagename);
        } catch (ClassNotFoundException e) {
            logger.error(e);
            ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                    "????????????????????????????????????");
        }
        return null;
    }

    /**
     * resloveVersionFormat:(??????????????????). <br/>
     * date: 2014???9???19??? <br/>
     *
     * @param code
     * @return
     * @author lxf
     */
    private int resloveVersionFormat(String code) {
        int intcode = Integer.parseInt(code.replace(".", ""));
        return intcode;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.hd_hse_main_phone_app_layout_main_tv_appr) {
            // ????????????
            clickAppr();
        }

    }

    /**
     * ????????????
     */
    private void clickAppr() {
        final List<RemoteApprLine> upData = getRemoteApprData();
        if (upData == null | upData.size() == 0) {
            ToastUtils.toast(getApplicationContext(), "?????????????????????????????????");
            return;
        }

        CheckRemoteApprBusiness checkRemoteApprBusiness = new CheckRemoteApprBusiness(MainActivity.this);
        checkRemoteApprBusiness.checkRemoteAppr(upData);
        checkRemoteApprBusiness.setOnCheckRemoteApprListener(new CheckRemoteApprBusiness.OnCheckRemoteApprListener() {
            @Override
            public void onCheckFail(String failReason) {
                ToastUtils.toast(getApplicationContext(), failReason);
            }

            @Override
            public void onCheckError() {
                ToastUtils.toast(getApplicationContext(), "??????????????????????????????");
            }

            @Override
            public void onCheckSuccess() {
                RemoteapprDialog dialog = new RemoteapprDialog(MainActivity.this, new IEventListener() {
                    @Override
                    public void eventProcess(int eventType, Object... objects) throws HDException {
                        if (eventType == IEventType.REMOTEAPPRDIALOG) {
                            if (objects[0] != null && (boolean) objects[0]) {
                                //??????
                                agreeRemoteAppr(upData, objects[1].toString());
                            } else if (objects[0] != null && !(boolean) objects[0]) {
                                //?????????
                                if (objects[1] != null)
                                    disagreeRemoteAppr(upData, objects[1].toString());
                                else
                                    ToastUtils.toast(getApplicationContext(), "???????????????????????????");
                            } else {
                                ToastUtils.toast(getApplicationContext(), "????????????");
                            }
                        }
                    }
                });
                dialog.show();
            }
        });



       /* RemoteApprBusiness remoteApprBusiness = new RemoteApprBusiness(
                MainActivity.this);
        remoteApprBusiness
                .setUpRemoteApprInfoListener(new UpRemoteApprInfoListener() {
                    @Override
                    public void UpRemoteApprInfoSuccess() {
                        ToastUtils.toast(getApplicationContext(), "??????????????????????????????");
                        initNotificationView();
                    }

                    @Override
                    public void UpRemoteApprInfoError() {
                        ToastUtils.toast(getApplicationContext(), "??????????????????????????????");
                    }
                });
        remoteApprBusiness.upRemoteAppr(upData);*/
    }


    private void agreeRemoteAppr(final List<RemoteApprLine> upData, String agreeReason) {
        for (RemoteApprLine apprLine : upData) {
            apprLine.setDisagree_reason(agreeReason);
        }
        RemoteApprBusiness remoteApprBusiness = new RemoteApprBusiness(
                MainActivity.this);
        remoteApprBusiness
                .setUpRemoteApprInfoListener(new UpRemoteApprInfoListener() {
                    @Override
                    public void UpRemoteApprInfoSuccess() {
                        initNotificationView();
                        ToastUtils.toast(getApplicationContext(), "??????????????????????????????");
                    }

                    @Override
                    public void UpRemoteApprInfoError() {
                        ToastUtils.toast(getApplicationContext(), "??????????????????????????????");
                    }
                });
        remoteApprBusiness.upRemoteAppr(upData);
    }

    private void disagreeRemoteAppr(final List<RemoteApprLine> upData, String disagreeReason) {
        for (RemoteApprLine apprLine : upData) {
            apprLine.setDisagree_reason(disagreeReason);
        }
        DisagreeRemoteApprBusiness remoteApprBusiness = new DisagreeRemoteApprBusiness(
                MainActivity.this);
        remoteApprBusiness
                .setUpRemoteApprInfoListener(new UpRemoteApprInfoListener() {
                    @Override
                    public void UpRemoteApprInfoSuccess() {
                        ToastUtils.toast(getApplicationContext(), "??????????????????????????????");
                        RemoteApprDisagreeBusiness remoteApprDisagreeBusiness = new RemoteApprDisagreeBusiness(upData, MainActivity.this);
                        remoteApprDisagreeBusiness.handleZyp();

                        initNotificationView();
                    }

                    @Override
                    public void UpRemoteApprInfoError() {
                        ToastUtils.toast(getApplicationContext(), "??????????????????????????????");
                    }
                });
        remoteApprBusiness.upRemoteAppr(upData);
    }


    /**
     * ?????????????????????????????????
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getMessageType() == MessageEvent.APP_MODULE_TYPE)
            initAppModule();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        isExit = true;
    }

    class Loop implements Runnable {


        BaseDao baseDao = new BaseDao();

        @Override
        public void run() {
            while (!isExit) {
                long time = ServerDateManager.getCurrentTimeMillis() + 30 * 60 * 1000;
                String simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
                String sql = "select sddept_desc,zyname,sjendtime,ud_zyxk_zysqid from ud_zyxk_zysq where sjendtime<='" + simpleDateFormat + "' and issendmessage is null and status='APPR'";

                List<WorkOrder> lstWorkOrder = null;
                try {
                    lstWorkOrder = (List<WorkOrder>) baseDao.executeQuery(sql,
                            new EntityListResult(WorkOrder.class));
                } catch (DaoException e) {
                    e.printStackTrace();
                }

                List<String> lsq = new ArrayList<>();
                if (lstWorkOrder != null && lstWorkOrder.size() > 0) {

                    Message message = Message.obtain();
                    message.obj = lstWorkOrder;
                    message.what = 0;
                    hd.sendMessage(message);

                    for (int i = 0; i < lstWorkOrder.size(); i++) {
                        String sqlq = "update ud_zyxk_zysq set issendmessage = 1 where ud_zyxk_zysqid='" + lstWorkOrder.get(i).getUd_zyxk_zysqid() + "'";
                        lsq.add(sqlq);
                    }
                }


                if (lsq.size() > 0) {
                    try {
                        execute(lsq);
                    } catch (HDException e) {
                        e.printStackTrace();
                    }

                }

                try {
                    Thread.sleep(1000 * 60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void execute(List<String> listsql) throws HDException {

        IConnectionSource conSrc = null;
        IConnection con = null;

        try {
            conSrc = ConnectionSourceManager.getInstance()
                    .getJdbcPoolConSource();
            con = conSrc.getConnection();
            BaseDao dao = new BaseDao();

            dao.executeUpdate(con, listsql.toArray(new String[listsql.size()]));

            con.commit();
        } catch (SQLException e) {
            logger.error("???????????????????????????", e);
            throw new HDException("???????????????????????????" + e.getMessage());

        } finally {
            if (con != null) {
                try {
                    conSrc.releaseConnection(con);
                } catch (SQLException e) {

                }
            }
        }
    }
}