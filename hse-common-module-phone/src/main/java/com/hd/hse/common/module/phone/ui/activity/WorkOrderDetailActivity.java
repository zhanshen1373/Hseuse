/**
 * Project Name:hse-wov-app
 * File Name:WorkTaskDetailActivity.java
 * Package Name:com.hd.hse.wov.ui.activity.worktask
 * Date:2014年11月6日
 * Copyright (c) 2014, fulibo@ushayden.com All Rights Reserved.
 */

package com.hd.hse.common.module.phone.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.MessageDialog;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.dialog.UpOnlineZypProgressDialog;
import com.hd.hse.common.component.phone.dialog.UpZYPProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.service.workorder.checkrules.CheckControllerActionListener;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkOrderInfo;
import com.hd.hse.system.SystemProperty;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ClassName:WorkTaskDetailActivity ().<br/>
 * Date: 2014年11月6日 <br/>
 *
 * @author flb
 * @see 此为作业票详情界面,使用时, 需要传入一个作业票对象
 */
public class WorkOrderDetailActivity extends NaviFrameActivity implements
        IEventListener {

    private Timer myTimer;
    private TimerTask myTimerTask;
    private final int TIMEOUT = 10000;
    private static final String TAG = "WorkOrderDetailActivity";
    private static Logger logger = LogUtils
            .getLogger(WorkOrderDetailActivity.class);
    public static final String WORK_ORDER = "args";
    public static final String CAR_KXZ = "xkz";
    public static int REQUESTCODE = 1;
    private Context context = null;
    private ProgressDialog progressDialog = null;
    private WebView webView = null;
    /**
     * actionBarTitleName:TODO(导航栏标题).
     */
    protected String actionBarTitleName;
    private WorkOrder workOrder = null;
    private String[] actionBar;
    private AbstractCheckListener actLsr = null;
    private BusinessAction action = null;
    private BusinessAsyncTask asyTask = null;
    protected Intent intent;
    // 错误提示页面
    private View errView;
    private TextView errMsg;
    // 拼接URL
    String url = null;
    private boolean isLocal;
    public static final int CODE = 13;

    private boolean IsShowItemRecord;
    /**
     * listAppModule:TODO(主功能更菜单模块).
     */
    private List<AppModule> listAppModule;

    public static final int RESULTCODE = 0x12;
    public static final String FROMWORKORDERACTIVITY = "fromWorkOrderActivity";
    private boolean isfromWorkOrderActivity = false;
    private List<WorkOrder> lworkorder;

    @Override
    public void initActionBar() {
        super.initActionBar();
        if (actionBar == null)
            return;

        // 设置导航栏信息
        setCustomActionBar(iEventListener, actionBar);
        // 设置导航栏标题
        setActionBartitleContent(actionBarTitleName, false);

        listAppModule = SystemProperty.getSystemProperty()
                .getMainAppModulelist("SJ");

        for (int i = 0; i < listAppModule.size(); i++) {

            if (listAppModule.get(i).getCode().equals("hse-ss-phone-app")) {
                IsShowItemRecord = true;
            }

        }

        if (isLocal) {
            if (("管理人员").equals(flag)) {
                if (IsShowItemRecord) {
                    setCustomMenuBar(new String[]{IActionBar.ITEM_RETURN,
                            IActionBar.ITEM_RECORD, IActionBar.ITEM_WATCHPIC,
                            IActionBar.ZYLL_ZYFH});
                } else {
                    setCustomMenuBar(new String[]{IActionBar.ITEM_RETURN,
                            IActionBar.ITEM_WATCHPIC, IActionBar.ZYLL_ZYFH});
                }
//                setCustomMenuBar(new String[]{IActionBar.ITEM_RETURN,
//                        IActionBar.ITEM_RECORD, IActionBar.ITEM_WATCHPIC,
//                        IActionBar.ZYLL_ZYFH});
            } else {

                if (IsShowItemRecord) {
                    setCustomMenuBar(new String[]{IActionBar.ITEM_RETURN,
                            IActionBar.ITEM_RECORD, IActionBar.ITEM_WATCHPIC});
                } else {
                    setCustomMenuBar(new String[]{IActionBar.ITEM_RETURN, IActionBar.ITEM_WATCHPIC});
                }

//                setCustomMenuBar(new String[]{IActionBar.ITEM_RETURN,
//                        IActionBar.ITEM_RECORD, IActionBar.ITEM_WATCHPIC});
            }

        } else {
            if (("管理人员").equals(flag)) {

                if (IsShowItemRecord) {
                    setCustomMenuBar(new String[]{IActionBar.ITEM_RECORD,
                            IActionBar.ITEM_WATCHPIC, IActionBar.ZYLL_ZYFH});
                } else {
                    setCustomMenuBar(new String[]{IActionBar.ITEM_WATCHPIC, IActionBar.ZYLL_ZYFH});
                }

//                setCustomMenuBar(new String[]{IActionBar.ITEM_RECORD,
//                        IActionBar.ITEM_WATCHPIC, IActionBar.ZYLL_ZYFH});
            } else {

                if (IsShowItemRecord) {
                    setCustomMenuBar(new String[]{IActionBar.ITEM_RECORD,
                            IActionBar.ITEM_WATCHPIC});
                } else {
                    setCustomMenuBar(new String[]{
                            IActionBar.ITEM_WATCHPIC});
                }
//                setCustomMenuBar(new String[]{IActionBar.ITEM_RECORD,
//                        IActionBar.ITEM_WATCHPIC});
            }

        }
    }

    IEventListener iEventListener = new IEventListener() {

        public void eventProcess(int arg0, Object... objects)
                throws HDException {
            // PhoneEventType.ZYPLIST_ITEM_CLICK, objects
            if (IEventType.ACTIONBAR_RETURN_CLICK == arg0) {
                // 退回
                sendBackWorkOrder();
            } else if (IEventType.ACTIONBAR_RECORD_CLICK == arg0) {
                startRecordActivity();
            } else if (IEventType.ACTIONBAR_ITEM_WATCH_PIC == arg0) {
                // 图片浏览

                List<Image> imageList = null;
                QueryWorkInfo workInfo = new QueryWorkInfo();
                imageList = workInfo.queryPhoto(workOrder, null);
                Intent intent = new Intent(getApplicationContext(),
                        WatchPicture.class);
                intent.putExtra(WatchPicture.IMAGEENTITY,
                        (Serializable) imageList);
                intent.putExtra(WatchPicture.WORKORDERID,
                        workOrder.getUd_zyxk_zysqid());
                startActivity(intent);

            } else if (IEventType.ACTIONBAR_ZYFH == arg0) {

                new AlertDialog.Builder(WorkOrderDetailActivity.this)
                        .setMessage("确定选中票证符合要求？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface arg0,
                                                        int arg1) {
                                        // TODO Auto-generated method stub
                                        lworkorder = new ArrayList<>();
                                        workOrder.setIsaccord("1");

                                        if (workOrder.isDownloaded()) {
                                            IConnectionSource conSrc = null;
                                            IConnection con = null;

                                            try {
                                                conSrc = ConnectionSourceManager
                                                        .getInstance()
                                                        .getJdbcPoolConSource();
                                                con = conSrc.getConnection();
                                                BaseDao dao = new BaseDao();

                                                dao.executeUpdate(
                                                        con,
                                                        "update ud_zyxk_zysq set isaccord = '1' where ud_zyxk_zysqid = '"
                                                                + workOrder
                                                                .getUd_zyxk_zysqid()
                                                                + "'");
                                                con.commit();
                                            } catch (Exception e) {
                                                // TODO Auto-generated catch
                                                // block
                                                e.printStackTrace();
                                            } finally {
                                                if (con != null) {
                                                    try {
                                                        conSrc.releaseConnection(con);
                                                    } catch (SQLException e) {

                                                    }
                                                }
                                            }
                                        }
                                        lworkorder.add(workOrder);
                                        if (lworkorder.size() > 0) {
                                            upWorkOrder(lworkorder);
                                        } else {
                                            ToastUtils.toast(
                                                    getApplicationContext(),
                                                    "无法获取作业票");
                                        }

                                    }
                                })

                        .show();

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        init();
    }

    private void init() {
        errView = View
                .inflate(context, R.layout.hd_hse_common_error_page, null);
        errMsg = (TextView) errView.findViewById(R.id.hd_hse_common_err_desc);
        errView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                refresh();
            }
        });
        intent = getIntent();
        workOrder = (WorkOrder) intent.getSerializableExtra(WORK_ORDER);
        isfromWorkOrderActivity = intent.getBooleanExtra(FROMWORKORDERACTIVITY,
                false);

        if (workOrder != null) {
            actionBarTitleName = workOrder.getZypclassname();
            QueryWorkInfo workInfo = new QueryWorkInfo();
            //判断是否启用作业票浏览报表按级别显示
            QueryRelativeConfig relativeConfig = new QueryRelativeConfig();
            boolean isLevel = relativeConfig
                    .isHadRelative(IRelativeEncoding.RQURL4LVL);
            try {
                if (isLevel)
                    url = workInfo.queryReportFormLevelUrl(workOrder);
                else
                    url = workInfo.queryReportFormUrl(workOrder);
            } catch (HDException e) {
                logger.error(e.getMessage(), e);
                // Log.e(TAG, "url查询失败!");
            }

            if (isLocal(workInfo, workOrder)) {
                isLocal = true;
            } else {
                isLocal = false;
            }
            actionBar = new String[]{IActionBar.TV_BACK,
                    IActionBar.IBTN_LEVELTWO_MORE, IActionBar.TV_TITLE};
        } else {
            actionBarTitleName = "车辆许可证浏览";
            actionBar = new String[]{IActionBar.TV_BACK, IActionBar.TV_TITLE};
        }


        UpdateTitleAUrl();


        refresh();

        // 业务处理
        actLsr = new CheckControllerActionListener();
        action = new BusinessAction(actLsr);
        asyTask = new BusinessAsyncTask(action, asyCallBack);
    }

    protected void UpdateTitleAUrl() {

    }

    private void refresh() {
        final ProgressDialog proDialog = new ProgressDialog(context, "加载中。。。");
        proDialog.show();
        if (webView != null) {
            webView.removeAllViews();
        }
        webView = new WebView(this);
        // 设置缩放支持
        WebSettings settings = webView.getSettings();
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);

        settings.setDefaultTextEncodingName("UTF-8");
        settings.setRenderPriority(RenderPriority.HIGH);
        settings.setJavaScriptEnabled(true);

        WebViewClient wClient = new WebViewClient() {
            // @Override
            // public boolean shouldOverrideUrlLoading(WebView view, String url)
            // {
            // webView.loadUrl(url);
            // return true;
            // }
            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                proDialog.dismiss();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String flingUrl) {

                proDialog.dismiss();
                webView.addView(errView, LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT);
                super.onReceivedError(view, errorCode, description, flingUrl);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // myTimer = new Timer();
                // myTimerTask = new TimerTask() {
                //
                // @Override
                // public void run() {
                // if(webView.getProgress() < 100){
                // if (progressDialog != null && progressDialog.isShowing()) {
                // progressDialog.dismiss();
                // }
                // ToastUtils.imgToast(context,
                // R.drawable.hd_hse_common_msg_wrong,"加载页面超时！");
                // if(myTimer != null){
                // myTimer.cancel();
                // myTimer.purge();
                // }
                // }
                // }
                // };
                // myTimer.schedule(myTimerTask, TIMEOUT, 1);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        };

        webView.setWebViewClient(wClient);
        webView.loadUrl(url);

        setContentView(webView);
    }

    /**
     * 判断是否是本地作业票
     *
     * @param workInfo
     * @param workOrder
     * @return
     */
    private boolean isLocal(QueryWorkInfo workInfo, WorkOrder workOrder) {
        try {
            if (workInfo.queryWorkInfo(workOrder, null) != null) {
                return true;
            }
        } catch (HDException e) {
            logger.error(e.getMessage());
            return false;
        }
        return false;
    }

    private AbstractAsyncCallBack asyCallBack = new AbstractAsyncCallBack() {

        @Override
        public void start(Bundle msgData) {

        }

        @Override
        public void processing(Bundle msgData) {

        }

        @Override
        public void end(Bundle msgData) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            /*
             * ToastUtils.imgToast(context, R.drawable.hd_hse_common_msg_right,
             * msgData.getString(IActionType.ACTION_TYPE_RETURN));
             */
            upWorkOrder();
            setResult(REQUESTCODE, intent);
        }

        @Override
        public void error(Bundle msgData) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            ToastUtils.imgToast(context, R.drawable.hd_hse_common_msg_wrong,
                    msgData.getString(IActionType.ACTION_TYPE_RETURN));
        }
    };

    @Override
    public void eventProcess(int eventType, Object... objects)
            throws HDException {
        switch (eventType) {
            case IEventType.ACTIONBAR_RETURN_CLICK:
                sendBackWorkOrder();
                break;
            case IEventType.ACTIONBAR_RECORD_CLICK:
                startRecordActivity();
                break;
            case IEventType.DOWN_WORK_LIST_LOAD:
                // 作业票上传成功
                if (isfromWorkOrderActivity) {
                    setResult(RESULTCODE);
                }
                if (WorkOrderDetailActivity.this != null) {
                    setResult(CODE);
                    WorkOrderDetailActivity.this.finish();
                }
                break;
            case IEventType.DOWN_WORK_LIST_SHOW:
                // 作业票上传失败
                break;
        }
    }

    private void sendBackWorkOrder() {
        MessageDialog.Builder builder = new MessageDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定退回作业票？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Map<String, Object> mapParam = new HashMap<String, Object>();
                mapParam.put(WorkOrder.class.getName(), workOrder);
                // 防止作业票退回报系统异常
                PDAWorkOrderInfoConfig pdaConfig = null;
                pdaConfig = new PDAWorkOrderInfoConfig();
                pdaConfig.setPscode(IConfigEncoding.SP);

                mapParam.put(PDAWorkOrderInfoConfig.class.getName(), pdaConfig);

                try {
                    progressDialog = new ProgressDialog(context, "作业票退回中...");
                    progressDialog.show();

                    asyTask.execute(IActionType.ACTION_TYPE_RETURN, mapParam);
                } catch (HDException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.createWarm().show();
    }

    private void startRecordActivity() {
        // Toast.makeText(WorkOrderDetailActivity.this, "问题登记",
        // Toast.LENGTH_SHORT).show();
        try {
            List<AppModule> appModules = SystemProperty.getSystemProperty()
                    .getMainAppModulelist("SJ");
            boolean hasSS = false;
            for (AppModule appModule : appModules) {
                if (appModule.getCode().equals("hse-ss-phone-app")) {
                    // 有现场监督模块
                    hasSS = true;
                    break;
                }
            }
            Class<?> clazz = null;
            if (hasSS) {
                clazz = Class
                        .forName("com.hd.hse.ss.activity.SupervisionDetailActivity");
            } else {
                clazz = Class
                        .forName("com.hd.hse.wov.phone.ui.dailyrecord.DailyRecordActivity");
            }
            Intent intent = new Intent(WorkOrderDetailActivity.this, clazz);
            intent.putExtra(WorkOrder.class.getName(), workOrder);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            logger.error(e);
            ToastUtils.imgToast(WorkOrderDetailActivity.this,
                    R.drawable.hd_hse_common_msg_wrong, "加载报错，请联系管理员！");
        }
    }

    /**
     * upzyp:TODO(上传作业票).
     */
    private UpZYPProgressDialog upzyp = null;

    /**
     * 上传作业票
     */
    private void upWorkOrder() {
        if (upzyp == null) {
            upzyp = new UpZYPProgressDialog(this);
            upzyp.setGetDataSourceListener(this);
        }
        QueryWorkOrderInfo mQueryWorkOrderInfo = new QueryWorkOrderInfo();
        String[] ud_zyxk_zysqids = null;
        // 判断是否合并审批，并赋值ud_zyxk_zysqids

        ud_zyxk_zysqids = new String[1];
        ud_zyxk_zysqids[0] = workOrder.getUd_zyxk_zysqid();

        try {
            List<WorkOrder> data = mQueryWorkOrderInfo
                    .queryWorkOrder(ud_zyxk_zysqids);
            if (null != data && data.size() > 0) {
                upzyp.execute("上传", "上传作业票信息，请耐心等待.....", "", data);
            } else {
                ToastUtils.toast(getBaseContext(), "请完成操作后重试");
            }
        } catch (HDException e) {
            e.printStackTrace();
        }

    }

    /**
     * upzyp:TODO(上传作业票).
     */
    private UpOnlineZypProgressDialog upzypFh = null;

    /**
     * 上传作业票
     */
    private void upWorkOrder(List<WorkOrder> workOrderList) {
        upzypFh = new UpOnlineZypProgressDialog(this, workOrderList);
        upzypFh.setGetDataSourceListener(this);
        upzypFh.execute("上传", "上传作业票信息，请耐心等待.....", "", workOrderList);

    }

}
