/**
 * Project Name:hse-common-module-phone
 * File Name:GasesCheckFragment.java
 * Package Name:com.hd.hse.common.module.ui.model.fragment
 * Date:2015年1月19日
 * Copyright (c) 2015, xuxinwen@ushayden.com All Rights Reserved.
 */

package com.hd.hse.common.module.ui.model.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.listener.AbstractActionListener;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.component.phone.adapter.TableContentPagerAdapter;
import com.hd.hse.common.component.phone.adapter.TableTitleAdapter;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.custom.ShowSignDialog;
import com.hd.hse.common.component.phone.dialog.UpZYPProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.DateTimePickDialogUtil;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.custom.ExamineListView;
import com.hd.hse.common.module.phone.ui.activity.NaviFrameActivity;
import com.hd.hse.common.module.phone.ui.activity.PaintSignatureActivity;
import com.hd.hse.common.module.phone.ui.activity.ReadCardExamineActivity;
import com.hd.hse.common.module.phone.ui.utils.ImageUtils;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.constant.ITableName;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.constant.IWorkOrderZypClass;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.dao.result.ListListResult;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.entity.base.Domain;
import com.hd.hse.entity.base.GasDetectSub;
import com.hd.hse.entity.base.GasDetection;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkApprovalPersonRecord;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.service.workorder.checkrules.CheckControllerActionListener;
import com.hd.hse.service.workorder.queryinfo.IQueryCallEventListener;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.system.SystemProperty;

import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ClassName:GasesCheckFragment ().<br/>
 * Date: 2015年1月19日 <br/>
 *
 * @author xuxinwen
 * @see
 */
public class GasesCheckFragment extends NaviFrameFragment {

    private static Logger logger = LogUtils.getLogger(GasesCheckFragment.class);

    private final String TAG = this.getClass().getSimpleName();
    private final boolean DEBUG = true;

    // 时间的格式
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    // 表示新增
    private static final int QUERY_APPROVAL_TYPE_ADD = 0;
    // 表示未完成
    private static final int QUERY_APPROVAL_TYPE_UNDONE = 1;
    // 表示费用
    private static final int QUERY_APPROVAL_TYPE_REUSE = 2;

    // 是否在跑异步任务
    boolean isCheckTaskRunning = false;
    /**
     * isTPQM:TODO(电子签名).
     */
    private boolean isTPQM = false;
    // 图片对象
    private Image image;

    /**
     * isfuyong:TODO(是否可以复用).
     */
    private boolean isfuyong = false;
    /**
     * isloadPro:TODO(是否来自自用是，查询环节点信息).
     */
    private boolean isSourceFuyong = false;

    // 存储 气体浓度类型和值的 控件
    private List<GaseViewPairs> gaseDetectRowHolder = new ArrayList<GaseViewPairs>();

    // 气体检测信息。
    private GasDetection mDataToSave;

    // 新增要用的气体检测信息
    private GasDetection mDataToAdd;

    // 要显示的 气体检测信息。
    private GasDetection mDataToDisplay;

    // 历史记录表头。
    private ArrayList<String> mDataHistoryHeaders = new ArrayList<String>();

    // 检测方式
    private List<SuperEntity> dataDetectMethodList;

    // 气体浓度的 信息
    private List<List<SuperEntity>> dataGasesListList;

    // 环节点列表，（去除了装置主任）
    private List<WorkApprovalPermission> queryApprovalPermission;
    /**
     * 装置主任审批环节点
     */
    private WorkApprovalPermission zzzrApprovalPermission;

    // 复用按钮是否已点击
    private boolean isReuseBtnClicked = false;

    // 新增数据
    private TextView btn_add;
    // PC同步
    private TextView btn_sync;

    // 复用按钮
    private TextView btn_reuse;

    // 用于控制显示隐藏。
    private LinearLayout ll_control_visible;
    // 用于动态增加气体检测条目
    private TableLayout tl_modify_gases_item;
    // 设置检测位置
    private TextView tv_check_location;
    // 设置检测时间
    private Button btn_check_time;
    // 设置检测方式
    private Spinner spinner_check_pattern;
    // 设置检测单位
    private Button btn_check_company;
    // 气体检查
    @SuppressWarnings("rawtypes")
    private ExamineListView examinelv_examine;
    // 检测浓度第一行，
    private Spinner spinner_row1_gase_type;
    private EditText et_row1_gase_value;
    private TextView tv_row1_gase_result;

    private RadioGroup checkRadioGroup;

    // 历史记录表头
    private ListView gHistoryTitle;

    // 历史记录内容
    private ViewPager gHistoryContent;

    // 历史记录 Indictor
    private RadioGroup gHistoryRadioGroup;

    private TextView tv_check_isok;

    private DisplayMetrics mDisplayMetrics = new DisplayMetrics();

    // TODO
    // 气体检测值允许录入文字描述
    private static String param = "QTJCVALUEISTEXT";
    private static boolean flag = false; // pc端可配置标记

    @SuppressWarnings("unused")
    private String SP_LAST_CHECK_TIME = "lastCheckTime";

    // private WorkTaskActionListener actionLsnr;
    /**
     * action:TODO(后台业务处理).
     */
    private BusinessAction actionQtjc;

    /**
     * actionLsnr:TODO(特殊业务动作处理).
     */
    private AbstractActionListener actionLsnr;

    /**
     * asyTask:TODO(异步任务).
     */
    private BusinessAsyncTask asyTaskQtjc;

    /**
     * mWorkOrder:TODO(表示票对象).
     */
    private WorkOrder mWorkOrder;

    /**
     * mPDAWorkOrderInfoConfig:TODO(表示气体检测配置信息对象).
     */
    private PDAWorkOrderInfoConfig mPDAWorkOrderInfoConfig;

    // 查询数据的 工具类。
    private IQueryWorkInfo queryWorkInfo;
    /**
     * 是否可以在只有氧气不合格的情况下继续作业
     */
    private boolean isCanContinue = false;
    /**
     * 受限空间级别,特殊受限空间
     */
    private String tssxkj = "SXKJ02";
    /**
     * 是否继续作业控件
     */
    private LinearLayout liContinueWork;
    private CheckBox checkboxContinueWork;
    // 氧气的类型
    private String oxygenType = "UDQTTYPE01|01";
    private int isqy = -1;

    //点击复用按钮但是走的是改变后的新增逻辑
    private boolean clickfy;


    @Override
    public void refreshData() {
        // TODO 查询历史记录。
        queryHistory();
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        // 每次创建时执行
        View _view = inflater.inflate(
                R.layout.hd_hse_common_module_phone_frag_gases_check, null);
        getActivity().getWindowManager().getDefaultDisplay()
                .getMetrics(mDisplayMetrics);
        findViewById(_view);
        btn_check_time.setOnClickListener(new ButtonsOnClickListener());
        btn_add.setOnClickListener(new ButtonsOnClickListener());
        return _view;
    }

    /**
     * 通用的 初始化 方法 init:(). <br/>
     * date: 2014年10月27日 <br/>
     *
     * @author xuxinwen
     */
    @Override
    protected void init() {
        //是否启用复用数据按钮不带出审批人
        checkIsQyGn();
        // 初始化设置对象
        instantiation();
        if (!flag) {
            et_row1_gase_value.setInputType(InputType.TYPE_CLASS_NUMBER
                    | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            checkRadioGroup.setVisibility(View.GONE);
        } else {
            checkRadioGroup.setVisibility(View.VISIBLE);
        }
        // 查询未完成的信息
        queryUndone();
        // 判断只有审批时才会出现 PC同步
        if (mPDAWorkOrderInfoConfig != null) {
            String _psCode = mPDAWorkOrderInfoConfig.getPscode();
            if (IConfigEncoding.SP.equals(_psCode)) {
                ctrlSyncBtnVisiblity();
            }
        }
        // 设置气体检测复用
        setReuseBtnVisibility();
        if (isfuyong) {
            if (queryApprovalPermission == null) {
                // 处理审批环节点。
                isSourceFuyong = true;
                queryApproval(QUERY_APPROVAL_TYPE_ADD);
            }
        }
        // 初始化气体检测同步代码
        initQTJCParam();
    }

    private void checkIsQyGn() {

        BaseDao baseDao = new BaseDao();
        String sql = "select * from sys_relation_info where sys_type='ISQTJCFYHJD'";
        RelationTableName relationTableName = null;
        try {
            relationTableName = (RelationTableName) baseDao.executeQuery(sql,
                    new EntityResult(RelationTableName.class));
        } catch (DaoException e) {
            e.printStackTrace();
        }
        if (relationTableName != null) {
            isqy = relationTableName.getIsqy();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // 重新启动
        isReuseBtnClicked = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 重新显示
        restoreValues();

    }

    @Override
    public void onPause() {
        super.onPause();
        // 暂停
        cacheValues();
    }

    /**
     * instantiation:(初始化参数对象). <br/>
     * date: 2015年11月27日 <br/>
     *
     * @author lxf
     */
    private void instantiation() {
        // TODO 获取pc端可配置标记
        QueryRelativeConfig config = new QueryRelativeConfig();
        flag = config.isHadRelative(param);
        personCard = new WorkApprovalPersonRecord();
        personCard.setTablename(ITableName.UD_ZYXK_QTJC);
        busAction = new BusinessAction(new CheckControllerActionListener());
        // 异步保存数据
        prgDialog = new ProgressDialog(getActivity(), true, "信息保存中");
        // 设置环节点查询编码。
        queryWorkInfo = new QueryWorkInfo();
        asyTask = new BusinessAsyncTask(busAction, asyncCallBack);

    }

    /**
     * asyncCallBack:TODO(气体检测保存 异步回调函数).
     */
    private AbstractAsyncCallBack asyncCallBack = new AbstractAsyncCallBack() {

        @Override
        public void start(Bundle msgData) {

        }

        @Override
        public void processing(Bundle msgData) {
            String actionType = getActionType();
            if (msgData.containsKey(actionType)) {
                prgDialog.showMsg(msgData.getString(actionType));
            }
        }

        @Override
        public void error(Bundle msgData) {
            isCheckTaskRunning = false;
            String actionType = getActionType();
            if (msgData.containsKey(actionType)) {
                prgDialog.dismiss();
                ToastUtils.imgToast(getActivity(),
                        R.drawable.hd_hse_common_msg_wrong,
                        msgData.getString(actionType));
            }

            mDataToSave.clearChild(GasDetectSub.class.getName());
        }

        @SuppressWarnings("unchecked")
        @Override
        public void end(Bundle msgData) {
            isCheckTaskRunning = false;
            prgDialog.dismiss();

            ToastUtils.imgToast(getActivity(),
                    R.drawable.hd_hse_common_msg_right,
                    msgData.getString(getActionType()));

            if (isTPQM) {
                PaintSignatureActivity.saveImageToDB(false, curApproveNode,
                        mWorkOrder, null, image);
            } else {
                if (mPDAWorkOrderInfoConfig != null) {
                    if (image != null) {
                        ImageUtils mImageUtils = new ImageUtils();
                        mImageUtils.saveImage(mPDAWorkOrderInfoConfig,
                                curApproveNode, mWorkOrder, image);
                    }

                }
            }

            List<GasDetectSub> childList = (List) mDataToSave
                    .getChild(GasDetectSub.class.getName());
            // 是否合格校验
            if (childList != null && childList.size() > 0) {
                // Log.e("childList.size()", childList.size()+"");
                for (int i = 0; i < childList.size(); i++) {
                    GasDetectSub mGasDetectSub = childList.get(i);
                    // Log.e("getIsPass()", mGasDetectSub.getIsPass()+"");
                    if (i == 0) {
                        tv_row1_gase_result.setVisibility(View.VISIBLE);
                        if (mGasDetectSub.getIsPass() == 1) {
                            tv_row1_gase_result.setTextColor(Color.BLACK);
                            tv_row1_gase_result.setText("合格");
                        } else if (mGasDetectSub.getIsPass() == 0) {
                            tv_row1_gase_result.setTextColor(Color.RED);
                            tv_row1_gase_result.setText("不合格");
                        } else if (mGasDetectSub.getIsPass() == -1) {
                            tv_row1_gase_result.setText("");
                        }
                    } else {
                        GaseViewPairs mGaseViewPairs = gaseDetectRowHolder
                                .get(i - 1);
                        mGaseViewPairs.tvResult.setVisibility(View.VISIBLE);
                        if (mGasDetectSub.getIsPass() == 1) {
                            mGaseViewPairs.tvResult.setTextColor(Color.BLACK);
                            mGaseViewPairs.tvResult.setText("合格");
                        } else if (mGasDetectSub.getIsPass() == 0) {
                            mGaseViewPairs.tvResult.setTextColor(Color.RED);
                            mGaseViewPairs.tvResult.setText("不合格");
                        } else if (mGasDetectSub.getIsPass() == -1) {
                            mGaseViewPairs.tvResult.setText("");
                        }
                    }
                }
            }

            if (isCanContinue) {
                // 检验是不是只有氧气检测不合格
                if (childList != null && childList.size() > 0
                        && isOnlyOxygenBelowGrade(childList)) {
                    if (liContinueWork.getVisibility() == View.VISIBLE) {
                        // liContinueWork显示，说明不是第一次校验,checkboxContinueWork不可点击
                        checkboxContinueWork.setEnabled(false);
                    } else {
                        liContinueWork.setVisibility(View.VISIBLE);
                    }
                }
            }
            // TODO 开关
            // 更新 是否合格状态
            if (flag) {
                int checkedId = checkRadioGroup.getCheckedRadioButtonId();
                if (checkedId == R.id.rbtn_yes) {
                    mDataToSave.setIshg(1);
                } else if (checkedId == R.id.rbtn_no) {
                    mDataToSave.setIshg(0);
                }
            }

            if (mDataToSave.getIshg() == null) {
                tv_check_isok.setText("");
            } else if (mDataToSave.getIshg() == 0) {
                tv_check_isok.setTextColor(Color.RED);
                tv_check_isok.setText("否");
            } else if (mDataToSave.getIshg() == 1) {
                tv_check_isok.setTextColor(Color.BLACK);
                tv_check_isok.setText("是");
            }

            setTopUIEnabled(false);
            queryHistory();

            examinelv_examine.setCurrentEntity(curApproveNode);
            if (curApproveNode == null || curApproveNode.getIsend() == 1) {
                // 刷新导航栏状态。

                PDAWorkOrderInfoConfig naviCurrentEntity = (PDAWorkOrderInfoConfig) ((NaviFrameActivity) getActivity())
                        .getNaviCurrentEntity();
                if (null != naviCurrentEntity) {
                    naviCurrentEntity.setIsComplete(1);

                    setNaviCurrentEntity(naviCurrentEntity);
                }
            }

            //执行中断结束操作
            if (mWorkOrder.getPausestatus() != null) {
                if (mWorkOrder.getPausestatus().equals(IWorkOrderStatus.PAUSED)) {
                    if (curApproveNode.getIsend() == 1) {

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        PDAWorkOrderInfoConfig workInfoConfig = new PDAWorkOrderInfoConfig();
                        workInfoConfig.setContype(11);
                        workInfoConfig.setPscode(IConfigEncoding.ZDJS);
                        workInfoConfig.setContypedesc("中断结束");
                        workInfoConfig.setSname("中断结束");
                        workInfoConfig.setDycode("PGTYPE306");

                        IQueryWorkInfo quertWorkInfo = new QueryWorkInfo();
                        List<WorkApprovalPermission> examineDatas = null;
                        try {
                            examineDatas = quertWorkInfo.queryApprovalPermission(mWorkOrder,
                                    workInfoConfig, null, null);
                        } catch (HDException e) {
                            e.printStackTrace();
                        }

                        for (int t = 0; t < examineDatas.size(); t++) {

                            HashMap<String, Object> paras = new HashMap<String, Object>();

                            // 作业票信息
                            paras.put(WorkOrder.class.getName(), mWorkOrder);

                            WorkApprovalPermission workApprovalPermission = examineDatas.get(t);
                            workApprovalPermission.setDefaultpersondesc(curApproveNode.getDefaultpersondesc());
                            workApprovalPermission.setDefaultpersonid(curApproveNode.getDefaultpersonid());
                            workApprovalPermission.setPersondesc(curApproveNode.getPersondesc());
                            workApprovalPermission.setPersonid(curApproveNode.getPersonid());
                            workApprovalPermission.setSptime(curApproveNode.getSptime());
                            // 审批环节信息
                            paras.put(WorkApprovalPermission.class.getName(),
                                    workApprovalPermission);

                            paras.put(PDAWorkOrderInfoConfig.class.getName(), workInfoConfig);

                            BusinessAction busAction = new BusinessAction(new CheckControllerActionListener());

                            final int finalT = t;
                            final List<WorkApprovalPermission> finalExamineDatas = examineDatas;
                            BusinessAsyncTask asyTask = new BusinessAsyncTask(busAction, new AbstractAsyncCallBack() {


                                @Override
                                public void start(Bundle msgData) {

                                }

                                @Override
                                public void processing(Bundle msgData) {

                                }

                                @Override
                                public void end(Bundle msgData) {

                                    if (finalT == finalExamineDatas.size() - 1) {

                                        HashMap<String, Integer> hjdmap = new HashMap<>();
                                        hjdmap.put(mWorkOrder.getUd_zyxk_zysqid(), finalExamineDatas.size());
                                        SystemProperty.getSystemProperty().setHjdmp(hjdmap);
                                        HashMap<String, String> pagetypemap = new HashMap<>();
                                        pagetypemap.put("PauseStatus", "PGTYPE306");
                                        SystemProperty.getSystemProperty().setPagetypemp(pagetypemap);

                                        List<WorkOrder> workOrderList = new ArrayList<>();
                                        workOrderList.add(mWorkOrder);
                                        UpZYPProgressDialog upZYPProgressDialog = new UpZYPProgressDialog(getActivity());
                                        upZYPProgressDialog.execute("上传", "上传作业票信息，请耐心等待.....", "", workOrderList);
                                    }

                                }

                                @Override
                                public void error(Bundle msgData) {

                                }
                            });

                            try {
                                asyTask.execute(IActionType.ACTION_TYPE_INTERRUPTEND, paras);
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } catch (HDException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                }
            }


        }
    };

    /**
     * setActionLsnr:(设置气体检测同步监听类). <br/>
     * date: 2015年11月25日 <br/>
     *
     * @param actionL
     * @author lxf
     */
    public void setActionLsnr(AbstractActionListener actionL) {
        this.actionLsnr = actionL;
    }

    /**
     * initQTJCParam:(初始化气体检测参数). <br/>
     * date: 2014年12月1日 <br/>
     *
     * @author lxf
     */
    private void initQTJCParam() {
        if (actionLsnr != null && actionQtjc == null) {
            actionQtjc = new BusinessAction(actionLsnr);
        }
        if (actionLsnr != null && null == asyTaskQtjc) {
            asyTaskQtjc = new BusinessAsyncTask(actionQtjc,
                    new AbstractAsyncCallBack() {

                        @Override
                        public void start(Bundle msgData) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void processing(Bundle msgData) {
                            // TODO Auto-generated method stub
                            if (msgData
                                    .containsKey(IActionType.QTJC_SYNCHRONOUS)) {
                                prgDialog.showMsg(msgData
                                        .getString(IActionType.QTJC_SYNCHRONOUS));
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
                            if (msgData
                                    .containsKey(IActionType.QTJC_SYNCHRONOUS)) {
                                prgDialog.dismiss();
                                String msg = msgData
                                        .getString(IActionType.QTJC_SYNCHRONOUS);
                                ToastUtils.toast(mActivity, msg);
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
                            if (msgData
                                    .containsKey(IActionType.QTJC_SYNCHRONOUS)) {

                                // // 先刷新左侧导航栏
                                // 刷新导航栏状态。

                                PDAWorkOrderInfoConfig naviCurrentEntity = (PDAWorkOrderInfoConfig) ((NaviFrameActivity) getActivity())
                                        .getNaviCurrentEntity();
                                if (null != naviCurrentEntity) {
                                    naviCurrentEntity.setIsComplete(1);
                                    setNaviCurrentEntity(naviCurrentEntity);
                                }
                                // // 刷新气体检测历史记录列表
                                // if (null != gaseCheckFragment) {
                                // gaseCheckFragment.queryHistory();
                                // }
                                queryUndone(); // 查询未完成数据
                                queryHistory(); // 查询历史列表

                                prgDialog.dismiss();
                            }
                        }
                    });
        }
    }

    /**
     * findViewById:(初始化控件对象). <br/>
     * date: 2015年11月27日 <br/>
     *
     * @param view
     * @author lxf
     */
    private void findViewById(View view) {

        btn_sync = (TextView) view
                .findViewById(R.id.hd_hse_common_module_gases_check_sync_btn);

        ll_control_visible = (LinearLayout) view
                .findViewById(R.id.hd_hse_common_module_gases_check_typein_ll);
        tl_modify_gases_item = (TableLayout) view
                .findViewById(R.id.hd_hse_common_module_gases_check_table_tl);
        tv_check_location = (TextView) view
                .findViewById(R.id.hd_hse_common_module_gases_check_location_tv);
        btn_check_time = (Button) view
                .findViewById(R.id.hd_hse_common_module_gases_check_time_btn);
        spinner_check_pattern = (Spinner) view
                .findViewById(R.id.hd_hse_common_module_gases_check_pattern_spinner);
        btn_check_company = (Button) view
                .findViewById(R.id.hd_hse_common_module_gases_check_company_btn);
        examinelv_examine = (ExamineListView) view
                .findViewById(R.id.hd_hse_common_module_gases_check_examine_examinelv);
        btn_add = (TextView) view
                .findViewById(R.id.hd_hse_common_module_gases_check_add_btn);
        btn_reuse = (TextView) view
                .findViewById(R.id.hd_hse_common_module_gases_check_reuse_btn);
        spinner_row1_gase_type = (Spinner) view
                .findViewById(R.id.hd_hse_common_module_gases_check_concentration_row1_spinner);

        et_row1_gase_value = (EditText) view
                .findViewById(R.id.hd_hse_common_module_gases_check_concentration_row1_value_et);
        tv_row1_gase_result = (TextView) view
                .findViewById(R.id.hd_hse_common_module_gases_check_concentration_row1_value_tv);
        checkRadioGroup = (RadioGroup) view
                .findViewById(R.id.hd_hse_module_gases_check_radio_group);

        radioYesButton = (RadioButton) view.findViewById(R.id.rbtn_yes);

        liContinueWork = (LinearLayout) view
                .findViewById(R.id.hd_hse_common_module_phone_frag_gases_check_li_continue_work);
        checkboxContinueWork = (CheckBox) view
                .findViewById(R.id.hd_hse_common_module_phone_frag_gases_check_checkbox_continue_work);
        checkboxContinueWork
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {

                        if (isChecked) {
                            queryApprovalPermission.add(1,
                                    zzzrApprovalPermission);

                        } else {
                            queryApprovalPermission
                                    .remove(zzzrApprovalPermission);
                            examinelv_examine.setData(queryApprovalPermission);
                        }

                        examinelv_examine.reFresh();
                    }
                });

//        if (!flag) {
//            et_row1_gase_value.setInputType(InputType.TYPE_CLASS_NUMBER
//                    | InputType.TYPE_NUMBER_FLAG_DECIMAL);
//            checkRadioGroup.setVisibility(View.GONE);
//        } else {
//            checkRadioGroup.setVisibility(View.VISIBLE);
//        }

        tv_check_isok = (TextView) view
                .findViewById(R.id.hd_hse_common_module_gases_check_isok_tv);

        gHistoryContent = (ViewPager) view
                .findViewById(R.id.hd_hse_common_module_table_content);

        gHistoryTitle = (ListView) view
                .findViewById(R.id.hd_hse_common_module_table_title);
        gHistoryTitle.setEnabled(false);
        gHistoryTitle.setDivider(getResources().getDrawable(
                R.drawable.divider_10));

        // 设置 历史记录的 Title 为屏幕尺寸的 1/3
        gHistoryTitle.getLayoutParams().width = mDisplayMetrics.widthPixels / 3;

        gHistoryRadioGroup = (RadioGroup) view
                .findViewById(R.id.hd_hse_common_module_radio_group);

    }

    class ButtonsOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.hd_hse_common_module_gases_check_time_btn) {
                // 检测时间
                btnTimeOnClick(v);
            } else if (id == R.id.hd_hse_common_module_gases_check_add_btn) {
                // 增加气体检测 按钮的点击事件
                clickfy = false;
                btnAddNewOnClick();
            } else if (id == R.id.hd_hse_common_module_gases_check_sync_btn) {
                btnSyncOnClick();
            } else if (id == R.id.hd_hse_common_module_gases_check_reuse_btn) {
                // 复用气体检测按钮点击事件
                if (isReuseBtnClicked) {
                    ToastUtils.toast(mActivity, "请不要重复点击");
                } else {


                    BaseDao dao = new BaseDao();
                    GasDetection gasDetection = null;
                    // 1.判断气体检测是否存在
                    StringBuilder sbsql = new StringBuilder();
                    String parentid;
                    WorkOrder workorder = (WorkOrder) mWorkOrder;
                    if (!workorder.getZypclass().equalsIgnoreCase(
                            IWorkOrderZypClass.ZYPCLASS_ZYDP)) {
                        // 表示不是大票
                        parentid = workorder.getParent_id();
                    } else {
                        parentid = workorder.getUd_zyxk_zysqid();
                    }
                    sbsql.append(
                            "select ud_zyxk_qtjcid,ud_zyxk_zysqid,jctime,jclocation,jclocation_desc,yxlimit,ishg,jcdept,jcmethod,sddwxmfzyj,seqnum,datasource,instrumentnum,jcperson,qrperson,audittime,ypfxqrperson,ypcjtime,sddwxmfzperson,writenbypda from ud_zyxk_qtjc ")
                            .append("where ishg = 1 and writenbypda = 'FINISH' ")
                            // 只有完成并且合格的检测结果才能被复用
                            .append("and ud_zyxk_zysqid in ")
                            .append("(select ud_zyxk_zysqid from ud_zyxk_zysq where parent_id='")
                            .append(parentid)
                            .append("' or (ifnull(parent_id,0) = 0 and ud_zyxk_zysqid='")
                            .append(parentid)
                            .append("')) order by jctime desc limit 1;");
                    // 2.取出最新的气体检测
                    try {
                        gasDetection = (GasDetection) dao.executeQuery(
                                sbsql.toString(), new EntityResult(GasDetection.class));
                    } catch (DaoException e) {
                        e.printStackTrace();
                    }
                    if (gasDetection == null) {

                        ToastUtils.toast(getActivity(), "当前无复用数据，请重新输入");
                        return;

                    }

                    if (isqy == 1) {
                        clickfy = true;
                        btnAddNewOnClick();

                    } else {
                        clickfy = false;
                        btnReuseOnClick();
                    }

                    isReuseBtnClicked = true;
                }
            }
        }

    }

    /**
     * 设置 气体检测控件 是否可用。
     * <p>
     * setViewsEnabled:(). <br/>
     * date: 2014年10月21日 <br/>
     *
     * @param enabled
     * @author xuxinwen
     */
    private void setTopUIEnabled(boolean enabled) {
        btn_check_time.setEnabled(enabled);
        spinner_check_pattern.setEnabled(enabled);

        spinner_row1_gase_type.setEnabled(enabled);
        et_row1_gase_value.setEnabled(enabled);

        for (GaseViewPairs item : gaseDetectRowHolder) {
            item.type.setEnabled(enabled);
            item.value.setEnabled(enabled);
        }
        // TODO 显示/隐藏检测结果处理
        if (flag) {
            if (enabled) {
                tv_check_isok.setVisibility(View.GONE);
                checkRadioGroup.setVisibility(View.VISIBLE);
            } else {
                checkRadioGroup.setVisibility(View.GONE);
                tv_check_isok.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 重置气体检测界面的数据。 resetGaseDetect:(). <br/>
     * date: 2014年10月21日 <br/>
     *
     * @author xuxinwen
     */
    private void resetGaseDetect() {
        setTopUIEnabled(true);

        // 将时间设置为当前时间。
        btn_check_time.setText(SystemProperty.getSystemProperty()
                .getSysDateTime());
        // 检测单位
        btn_check_company.setText("");

        et_row1_gase_value.setText("");

        tv_check_isok.setText("");

        if (flag) {
            radioYesButton.setChecked(true);
        }

        for (GaseViewPairs item : gaseDetectRowHolder) {
            item.value.setText("");
        }
    }

    /**
     * btnAddNewOnClick:(点击新增按钮). <br/>
     * date: 2015年11月27日 <br/>
     *
     * @author lxf
     */
    private void btnAddNewOnClick() {
        // 用来保存新增的录入数据，而不是直接在 查询出来的数据集中更改。
        boolean _isRun = true;
        // 隐藏tv_row1_gase_result
        tv_row1_gase_result.setVisibility(View.GONE);
        if (gaseDetectRowHolder != null) {
            for (GaseViewPairs mGaseViewPairs : gaseDetectRowHolder) {
                mGaseViewPairs.tvResult.setVisibility(View.GONE);
            }
        }
        // liContinueWork隐藏，checkboxContinueWork可点击
        liContinueWork.setVisibility(View.GONE);
        checkboxContinueWork.setChecked(false);
        checkboxContinueWork.setEnabled(true);
        // 判断部分代码
        String _psCode = mPDAWorkOrderInfoConfig.getPscode();
        if (IConfigEncoding.SP.equals(_psCode)) {
            // 现场核查 部分
            try {
                WorkOrder _workOrder = queryWorkInfo.queryWorkInfo(mWorkOrder,
                        null);
                // 表示审批和作业中的票可以新增
                if (IWorkOrderStatus.INPRG.equals(_workOrder.getStatus())
                        || IWorkOrderStatus.APPAUDITED.equals(_workOrder
                        .getStatus())) {
                    _isRun = true;
                } else {
                    _isRun = false;
                }
            } catch (HDException e) {
                logger.error(e.getMessage(), e);
                ToastUtils.imgToast(getActivity(),
                        R.drawable.hd_common_message_error, "查询作业票失败，请联系管理员！");
                return;
            }

        } else if (IConfigEncoding.FC.equals(_psCode)) {
            // 复查部分
            _isRun = true;
        } else {
            // 不知道是哪个部分，直接返回。
            return;
        }
        // 执行代码。
        if (_isRun) {
            mDataToSave = new GasDetection();
            if (mDataToAdd != null) {
                // 用于第一次点击新建保存成功后的第二次点击新建// 已经获取过新增的显示数据。
                if (clickfy) {

                    try {
                        queryWorkInfo
                                .queryGasInfo(
                                        mWorkOrder,
                                        new IQueryCallEventListenerImpl(
                                                IQueryCallEventListenerImpl.TYPE_GASE_INFO));
                    } catch (HDException e) {
                        logger.error(e.getMessage(), e);
                    }
                } else {

                    resetGaseDetect();
                    queryApproval(QUERY_APPROVAL_TYPE_ADD);
                }

                // setTopUIData(QUERY_APPROVAL_TYPE_ADD, mDataToAdd);
            } else {
                // 还没获得过新增的数据。
                // 1、调用查询类查询气体检测部分信息。
                try {
                    queryWorkInfo
                            .queryGasInfo(
                                    mWorkOrder,
                                    new IQueryCallEventListenerImpl(
                                            IQueryCallEventListenerImpl.TYPE_GASE_INFO));
                } catch (HDException e) {
                    logger.error(e.getMessage(), e);
                }
            }

        } else {
            ToastUtils.toast(getActivity(), "非审批中，作业不允许气体检测！！！");
        }

    }

    /**
     * cacheValues:(把界面数据存于缓存). <br/>
     * date: 2015年11月27日 <br/>
     *
     * @author lxf
     */
    private void cacheValues() {
        if (gaseDetectRowHolder == null) {
            return;
        }

        int _size = gaseDetectRowHolder.size();
        GaseViewPairs _gaseViewPairs = null;

        for (int i = 0; i < _size; i++) {
            _gaseViewPairs = gaseDetectRowHolder.get(i);
            _gaseViewPairs.selectPosition = _gaseViewPairs.type
                    .getSelectedItemPosition();
            _gaseViewPairs.textValue = _gaseViewPairs.value.getText()
                    .toString();
        }
    }

    /**
     * restoreValues:(取缓存数据，显示与界面). <br/>
     * date: 2015年11月27日 <br/>
     *
     * @author lxf
     */
    private void restoreValues() {
        if (gaseDetectRowHolder == null) {
            return;
        }

        int _size = gaseDetectRowHolder.size();
        GaseViewPairs _gaseViewPairs = null;

        for (int i = 0; i < _size; i++) {
            _gaseViewPairs = gaseDetectRowHolder.get(i);
            _gaseViewPairs.type.setSelection(_gaseViewPairs.selectPosition);
            _gaseViewPairs.value.setText(_gaseViewPairs.textValue);
        }
    }

    /**
     * 查询 审批环节点。 queryApproval:(). <br/>
     * date: 2014年10月24日 <br/>
     *
     * @author xuxinwen
     */
    private void queryApproval(int type) {
        try {


            // 此处应该根据当前获得的检测信息，得到相应的环节点信息。
            WorkApprovalPersonRecord _queryPersonCard = new WorkApprovalPersonRecord();
            switch (type) {
                case QUERY_APPROVAL_TYPE_ADD:
                    // 新增
                    break;
                case QUERY_APPROVAL_TYPE_UNDONE:
                    // 未完成。
                    _queryPersonCard.setTablename(ITableName.UD_ZYXK_QTJC);
                    _queryPersonCard.setTableid(mDataToSave.getUd_zyxk_qtjcid());
                    break;
                case QUERY_APPROVAL_TYPE_REUSE:
                    // 复用

                    _queryPersonCard.setTablename(ITableName.UD_ZYXK_QTJC);
                    _queryPersonCard.setTableid(mDataToSave.getUd_zyxk_qtjcid());

                    break;
                default:
                    break;
            }
            queryWorkInfo.queryApprovalPermission(mWorkOrder,
                    mPDAWorkOrderInfoConfig, _queryPersonCard,
                    new IQueryCallEventListenerImpl(
                            IQueryCallEventListenerImpl.TYPE_APPROVAL));
        } catch (HDException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * synchronousQtjc:(同步气体检测数据). <br/>
     * date: 2015年11月25日 <br/>
     *
     * @param zysqid
     * @author lxf
     */
    public void btnSyncOnClick() {
        // 与 PC 段同步的 代码。
        try {
            if (asyTaskQtjc != null) {
                // 异步处理
                asyTaskQtjc.execute(IActionType.QTJC_SYNCHRONOUS, mWorkOrder);
                prgDialog.showMsg("同步气体检测");
                prgDialog.show();
            }
        } catch (HDException e) {
            ToastUtils.toast(getActivity(), e.getMessage());
        }
    }

    public void btnReuseOnClick() {
        try {
            queryWorkInfo.queryAndMultiplexGasInfo(mWorkOrder,
                    mPDAWorkOrderInfoConfig, new IQueryCallEventListenerImpl(
                            IQueryCallEventListenerImpl.TYPE_MULTIPLEX),
                    queryApprovalPermission, mPDAWorkOrderInfoConfig
                            .getPscode());
        } catch (HDException e) {
            isReuseBtnClicked = false;
            ToastUtils.toast(getActivity(), e.getMessage());
        }
    }

    /**
     * 设置时间的 Button 点击事件。 btnTimeOnClick:(). <br/>
     * date: 2014年10月21日 <br/>
     *
     * @param v
     * @author xuxinwen
     */
    private void btnTimeOnClick(View v) {
        String str = ((TextView) v).getText().toString();

        DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                getActivity(), str);
        dateTimePicKDialog.dateTimePicKDialog(((TextView) v));
    }

    @Override
    public void initData(List<Object> data) {
        // 在这个 Fragment中传递的值的协议是 WorkOrder和 PDAWorkOrderInfoConfig
        if (data.size() < 2) {
            return;
        }
        mWorkOrder = (WorkOrder) data.get(0);
        mPDAWorkOrderInfoConfig = (PDAWorkOrderInfoConfig) data.get(1);
    }

    private WorkApprovalPermission curApproveNode;
    private ProgressDialog prgDialog;
    private BusinessAction busAction;
    @SuppressWarnings("unused")
    private AbstractAsyncCallBack auditCallBack;
    private BusinessAsyncTask asyTask;
    private WorkApprovalPersonRecord personCard;

    /**
     * getActionType:(获取审批或者复查时气体检测动作编码). <br/>
     * date: 2015年11月27日 <br/>
     *
     * @return
     * @author lxf
     */
    private String getActionType() {
        String _psCode = mPDAWorkOrderInfoConfig.getPscode();
        String _actionType = null;

        if (IConfigEncoding.SP.equals(_psCode)) {
            _actionType = IActionType.ACTION_TYPE_GAS;
        } else if (IConfigEncoding.FC.equals(_psCode)) {
            _actionType = IActionType.ACTION_TYPE_RECHECKGAS;
        }

        return _actionType;
    }

    /**
     * 气体检测的时候， Spinner 和 EditText 的对数是不确定的 而且在完成的时候还要保存其中的数据。所以要提供方便再次获取到
     * 这两个控件对的方法，
     * <p>
     * 这里采用的方法是：每次动态创建一对控件的时候，都把他们的引用存到 本类的一个实例中。然后把这个 ViewHolder 存到 List 集合中。
     * 因为不仅要找到控件对，还要能方便的获取某个控件对对应的实体。以便保存相应的 数据。 实际情况是每个 控件对 对应一个 实体的 List ，List
     * 中的每个实体 中对应 Spinner 中的一个条目和对应 EditText 要显示的值。
     * <p>
     * ClassName: GaseViewPairs ()<br/>
     * date: 2014年10月21日 <br/>
     *
     * @author xuxinwen
     * @version GasesCheckFragment
     */
    class GaseViewPairs {
        Spinner type;
        int selectPosition;
        EditText value;
        String textValue;
        TextView tvResult;
    }

    /**
     * 时间选择器对话框的 监听 ClassName: DialogOnClickListener ()<br/>
     * date: 2014年10月21日 <br/>
     *
     * @author xuxinwen
     * @version GasesCheckFragment
     */
    class DialogOnClickListener implements DialogInterface.OnClickListener {

        private TimePicker timePicker;

        public DialogOnClickListener(TimePicker timePicker) {
            this.timePicker = timePicker;
        }

        @SuppressWarnings("deprecation")
        @SuppressLint("SimpleDateFormat")
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Date date = (Date) btn_check_time.getTag();

            date.setHours(timePicker.getCurrentHour());
            date.setMinutes(timePicker.getCurrentMinute());
            date.setSeconds(0);

            btn_check_time.setText(new SimpleDateFormat(DATE_FORMAT)
                    .format(date));
        }

    }

    /**
     * 刷新历史记录页面。异步查询，完成的回调在 IQueryCallEventListenerImpl 类中
     * <p>
     * refreshHistory:(). <br/>
     * date: 2014年10月28日 <br/>
     *
     * @author xuxinwen
     */
    public void queryHistory() {
        try {
            if (mWorkOrder != null) {
                queryWorkInfo.queryHistoryGasInfo(mWorkOrder,
                        new IQueryCallEventListenerImpl(
                                IQueryCallEventListenerImpl.TYPE_HISTORY));
            }
        } catch (HDException e) {
            logger.error(e.getMessage(), e);
        }
    }


    private void setObjectInfo(List<SuperEntity> listSuper, String[] attr,
                               String[] value) {
        if (attr.length != value.length) {
            return;
        }
        int length = attr.length;
        if (listSuper != null && listSuper.size() > 0) {
            for (SuperEntity superEntity : listSuper) {
                for (int i = 0; i < length; i++) {
                    superEntity.setAttribute(attr[i], value[i]);
                }
            }
        }
    }


    /**
     * 把未完成的数据显示出来 设置气体检测的相关数据集，此方法不区分 是 新增还是 未完成。 setGaseInfo:(). <br/>
     * date: 2014年10月28日 <br/>
     *
     * @param objects
     * @author xuxinwen
     */
    private void filterDataOfTopUI(int queryApprovalType, Object... objects) {

        if (objects[0] == null) {
            if (queryApprovalType == QUERY_APPROVAL_TYPE_REUSE) {
                ToastUtils.toast(getActivity(), "当前无复用数据，请重新输入");
            } else {
                Toast.makeText(getActivity(), "查询数据失败！请联系管理员",
                        Toast.LENGTH_SHORT).show();
            }
            return;
        }
        mDataToDisplay = (GasDetection) objects[0];
        // 这里获得的值可能为 null ， 当该数据来自未完成的订单。
        // 获取气体检测方式的动态与
        dataDetectMethodList = mDataToDisplay.getChild(Domain.class.getName());

        GasDetection gasDetection = null;
        if (clickfy) {

            BaseDao dao = new BaseDao();

            StringBuilder sbsql = new StringBuilder();
            String parentid;
            WorkOrder workorder = (WorkOrder) mWorkOrder;
            if (!workorder.getZypclass().equalsIgnoreCase(
                    IWorkOrderZypClass.ZYPCLASS_ZYDP)) {
                // 表示不是大票
                parentid = workorder.getParent_id();
            } else {
                parentid = workorder.getUd_zyxk_zysqid();
            }
            sbsql.append(
                    "select ud_zyxk_qtjcid,ud_zyxk_zysqid,jctime,jclocation,jclocation_desc,yxlimit,ishg,jcdept,jcmethod,sddwxmfzyj,seqnum,datasource,instrumentnum,jcperson,qrperson,audittime,ypfxqrperson,ypcjtime,sddwxmfzperson,writenbypda from ud_zyxk_qtjc ")
                    .append("where ishg = 1 and writenbypda = 'FINISH' ")
                    // 只有完成并且合格的检测结果才能被复用
                    .append("and ud_zyxk_zysqid in ")
                    .append("(select ud_zyxk_zysqid from ud_zyxk_zysq where parent_id='")
                    .append(parentid)
                    .append("' or (ifnull(parent_id,0) = 0 and ud_zyxk_zysqid='")
                    .append(parentid)
                    .append("')) order by jctime desc limit 1;");
            // 2.取出最新的气体检测
            try {
                gasDetection = (GasDetection) dao.executeQuery(
                        sbsql.toString(), new EntityResult(GasDetection.class));
            } catch (DaoException e) {
                e.printStackTrace();
            }


            StringBuilder sql = new StringBuilder();
            sql.append(
                    "select * from ud_zyxk_qtjcline where ud_zyxk_qtjcid='")
                    .append(gasDetection.getUd_zyxk_qtjcid()).append("'");

            List<SuperEntity> gasDetectSubList = null;
            try {

                gasDetectSubList = (List<SuperEntity>) dao
                        .executeQuery(sql.toString(), new EntityListResult(
                                GasDetectSub.class));
            } catch (DaoException e) {
                e.printStackTrace();
            }

            // 设置气体检测作业申请ID
            setObjectInfo(gasDetectSubList, new String[]{
                            "ud_zyxk_zysqid", "jctime"},
                    new String[]{
                            workorder.getUd_zyxk_zysqid(),
                            SystemProperty.getSystemProperty()
                                    .getSysDateTime()});

            if (gasDetectSubList != null && gasDetectSubList.size() > 0) {
                gasDetection.setChild(GasDetectSub.class.getName(),
                        gasDetectSubList);
            }


            IConnectionSource connectionSource = null;
            IConnection connection = null;

            try {
                connectionSource = ConnectionSourceManager.getInstance()
                        .getJdbcPoolConSource();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection = connectionSource.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (gasDetection != null) {

                StringBuilder unDoSbSql = new StringBuilder();
                // unDoSbSql.setLength(0);
                unDoSbSql
                        .append("select aln.domainid as domainid,(case when ifnull(line.qttype,'')='' then aln.domainid||'|'||aln.value else line.qttype end) as qtlx,line.qtvalue as qtvalue,aln.description as description,");
                unDoSbSql
                        .append(" aln.maxvalue as maxvalue,aln.minvalue as minvalue,aln.isbj as isbj from (select domainid,description,value,maxvalue,minvalue,isbj from");
                unDoSbSql
                        .append(" (select domainid,description,value from alndomain where domainid in (select domainid||value from alndomain where domainid = 'UDQTTYPE' order by value)) as alntable left join ");
                unDoSbSql
                        .append(" (select qtname, main_tab.qtlx,qtpz.maxvalue as maxvalue, qtpz.minvalue as minvalue,qtpz.isbj as isbj from ud_zyxk_qtjcpz qtpz inner join");
                unDoSbSql
                        .append(" (select (aln.domainid || aln.value) as qtlx,pz.qtlx as main_lx,pz.qtname as son_lx from ALNDOMAIN aln inner join  UD_ZYXK_QTJCPZ pz  on pz.QTLX=aln.value");
                unDoSbSql
                        .append(" where aln.domainid='UDQTTYPE' order by value ) as main_tab on qtpz.qtlx=main_tab.main_lx and qtpz.qtname=main_tab.son_lx) as alnpz ");
                unDoSbSql
                        .append(" on alntable.domainid=alnpz.qtlx and alntable.description=alnpz.qtname) as aln left join");
                unDoSbSql
                        .append(" (select qttype, qtvalue from ud_zyxk_qtjcline where ud_zyxk_qtjcid");
                unDoSbSql.append(" ='")
                        .append(gasDetection.getUd_zyxk_qtjcid()).append("' )");
                unDoSbSql
                        .append("  as line on (aln.domainid||'|'||aln.value)=line.qttype order by aln.domainid asc,line.qtvalue desc,value asc;");
                List<List<GasDetectSub>> gasLineList = null;
                try {
                    gasLineList = (List<List<GasDetectSub>>) dao
                            .executeQuery(connection, unDoSbSql.toString(),
                                    new ListListResult(GasDetectSub.class,
                                            "domainid"));
                    try {
                        connection.commit();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (DaoException e) {
                    e.printStackTrace();
                } finally {
                    if (connectionSource != null) {
                        try {
                            connectionSource.releaseConnection(connection);
                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
                gasDetection.setListChild(GasDetectSub.class.getName(),
                        (List) gasLineList);

            }


            List<SuperEntity> child = null;
            if (gasDetection != null) {
                child = gasDetection.getChild(GasDetectSub.class.getName());
                dataGasesListList = gasDetection.getListChild(GasDetectSub.class
                        .getName());
            }

            List<List<SuperEntity>> listChild = mDataToDisplay.getListChild(GasDetectSub.class
                    .getName());

            for (int i = 0; i < dataGasesListList.size(); i++) {

                for (int k = 0; k < dataGasesListList.get(i).size(); k++) {

                    for (int w = 0; w < listChild.size(); w++) {
                        for (int j = 0; j < listChild.get(w).size(); j++) {

                            if (dataGasesListList.get(i).get(k).getAttribute("description").equals(
                                    listChild.get(w).get(j).getAttribute("description"))) {

                                dataGasesListList.get(i).get(k).setAttribute("qttype",
                                        listChild.get(w).get(j).getAttribute("qttype"));
                            }


                        }

                    }
                }


                for (int t = 0; t < child.size(); t++) {

                    for (int g = 0; g < dataGasesListList.get(i).size(); g++) {

                        if (child.get(t).getAttribute("qttype").equals(
                                dataGasesListList.get(i).get(g).getAttribute("qttype"))) {
                            if (child.get(t).getAttribute("qtvalue") != null) {
                                dataGasesListList.get(i).get(g).setAttribute("qtvalue",
                                        child.get(t).getAttribute("qtvalue"));
                            } else if (child.get(t).getAttribute("qtsvalue") != null) {
                                dataGasesListList.get(i).get(g).setAttribute("qtsvalue",
                                        child.get(t).getAttribute("qtsvalue"));
                            }
                        }


                    }
                }


            }


        } else {

            // 获取气体检测子表的数据
            dataGasesListList = mDataToDisplay.getListChild(GasDetectSub.class
                    .getName());


        }


        // 处理审批环节点。
        queryApproval(queryApprovalType);
        // 初始化 界面
        d2u_setTopData(gasDetection);
    }

    /**
     * 控制上半部分 界面显示 与否， setTopUIShow:(). <br/>
     * date: 2014年10月25日 <br/>
     *
     * @param isShow
     * @author xuxinwen
     */
    private void ui_setTopUIShow(boolean isShow) {
        if (isShow) {
            ll_control_visible.setVisibility(View.VISIBLE);
        } else {
            ll_control_visible.setVisibility(View.GONE);
        }
    }

    /**
     * 这是一个业务方法， isNewlyAddAction:(). <br/>
     * date: 2015年1月20日 <br/>
     *
     * @return
     * @author xuxinwen
     */
    private boolean isNewlyAddAction() {
        if (mDataToDisplay.getUd_zyxk_qtjcid() == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 针对 SuperEntity 子类通用的 单一显示效果的 Adapter ClassName: SuperEntityAdapter ()<br/>
     * date: 2014年10月20日 <br/>
     *
     * @author xuxinwen
     * @version GasesCheckFragment
     */
    class SuperEntityAdapter extends BaseAdapter {

        public SuperEntityAdapter(List<SuperEntity> list, String textKey) {
            this.data = list;
            this.key = textKey;
        }

        // 要显示 数据对应的 key
        private String key;

        // 数据
        private List<SuperEntity> data;

        /**
         * data.
         *
         * @return the data
         */
        public List<SuperEntity> getData() {
            return this.data;
        }

        /**
         * data.
         *
         * @param data the data to set
         */
        public void setData(List<SuperEntity> data, String textKey) {
            this.data = data;
            this.key = textKey;
        }

        @Override
        public int getCount() {
            if (this.data != null) {
                return this.data.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (this.data != null) {
                this.data.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            SuperEntity item = (SuperEntity) this.data.get(position);

            if (convertView == null) {
                convertView = View.inflate(getActivity(),
                        R.layout.hd_hse_common_module_simple_list_item1, null);

                holder = new ViewHolder();
                holder.tv = (TextView) convertView
                        .findViewById(android.R.id.text1);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            CharSequence text = (CharSequence) item.getAttribute(key);

            holder.tv.setText(text != null ? text : " ");

            return convertView;
        }

        class ViewHolder {
            TextView tv;
        }

    }

    /**
     * 根据实体中的数据 刷新 气体检测的界面显示。
     * <p>
     * setDataOfTopUI:(). <br/>
     * date: 2014年10月21日 <br/>
     *
     * @author xuxinwen
     */
    private void d2u_setTopData(GasDetection gasDetection) {
        if (mDataToDisplay != null) {
            // 显示气体检测录入
            ui_setTopUIShow(true);
            // 这里区分了 是 新增数据，还是未完成数据的查询结果，并对相应的情况作不同的界面处理。
            if (isNewlyAddAction()) {

                if (clickfy) {

                    //检测时间
                    btn_check_time.setText(SystemProperty.getSystemProperty()
                            .getSysDateTime());
                    //检测方式
                    mDataToDisplay.setJcmethod(gasDetection.getJcmethod());
                    if (mDataToDisplay.getJcmethod() != null) {
                        spinner_check_pattern.setAdapter(new ArrayAdapter<>(
                                getActivity(),
                                R.layout.hd_hse_common_module_simple_list_item1,
                                android.R.id.text1, new String[]{mDataToDisplay
                                .getJcmethod()}));
                    }
                    //检测单位
                    mDataToDisplay.setJcdept(gasDetection.getJcdept());
                    mDataToDisplay.setIshg(gasDetection.getIshg());
                } else {

                    // 新增数据
                    // 获得系统当前时间，
                    btn_check_time.setText(SystemProperty.getSystemProperty()
                            .getSysDateTime());
                    // 设置检测方式，
                    if (dataDetectMethodList != null
                            && dataDetectMethodList.size() > 0) {
                        spinner_check_pattern.setAdapter(new SuperEntityAdapter(
                                dataDetectMethodList, "value"));
                    } else {
                        ToastUtils.toast(getActivity(),
                                "动态域表找不到气体检测方式数据，请初始化或联系管理员");
                    }

                }


                // 更新检测位置
                // tv_check_location.setText(mWorkOrder.getJclocation());
            } else {
                // 未完成数据
                btn_check_time.setText(mDataToDisplay.getJctime());
                if (mDataToDisplay.getJcmethod() != null) {
                    spinner_check_pattern.setAdapter(new ArrayAdapter<>(
                            getActivity(),
                            R.layout.hd_hse_common_module_simple_list_item1,
                            android.R.id.text1, new String[]{mDataToDisplay
                            .getJcmethod()}));
                }

                // 更新检测位置
                // tv_check_location.setText(mDataToDisplay.getJclocation_desc());
            }
            // 更新检测位置
            tv_check_location.setText(mWorkOrder.getJclocation_desc());
            // 更新检测单位
            btn_check_company.setText(mDataToDisplay.getJcdept());


            // TODO 开关 是否合格
            d2u_IsUpToStandard();
            // 设置气体见值信息
            d2u_setGaseDetectTable();
            // 表示新增
            if (isNewlyAddAction()) {
                if (clickfy) {
                    // 设置气体检测控件是否 启用
                    setTopUIEnabled(false);
                } else {
                    // 就是新增,清空界面数据
                    resetGaseDetect();
                }

            } else {
                // 设置气体检测控件是否 启用
                setTopUIEnabled(isNewlyAddAction());
            }

        }
    }

    /**
     * d2u_setGaseDetectTable:(设置气体检测 设置值 ). <br/>
     * date: 2015年11月27日 <br/>
     *
     * @author lxf
     */
    private void d2u_setGaseDetectTable() {
        if (dataGasesListList != null && dataGasesListList.size() > 0) {
            // 设置第一行。
            spinner_row1_gase_type.setAdapter(new SuperEntityAdapter(
                    dataGasesListList.get(0), "description"));
            Float qtvalue2 = ((GasDetectSub) dataGasesListList.get(0).get(0))
                    .getQtvalue();
            String qtsvalue2 = ((GasDetectSub) dataGasesListList.get(0).get(0))
                    .getQtsvalue();
            if (!flag) {
                if (qtvalue2 != null) {
                    et_row1_gase_value.setText(qtvalue2.toString());
                }
            } else {
                if (qtsvalue2 != null) {
                    et_row1_gase_value.setText(qtsvalue2);
                }
            }
            // 动态添加行
            ui_dynamicAddRowToTable();
            // 设置气体检测info
            d2u_setGasesInfo();
        } else {
            Log.e(TAG, "没有气体检测信息！");
        }
    }

    /**
     * 气体检测动态添加TableRow部分中，TableLayout 中最原始 TableRow个数。
     */
    private final int CONF_TABLE_MINIMUM_ROW = 2;

    /**
     * 去掉动态添加过的 TableRow ui_clearTableRow:(). <br/>
     * date: 2015年1月20日 <br/>
     *
     * @author xuxinwen
     */
    private void ui_clearTableRow() {
        /*
         * 清空之前动态添加的 行,布局文件 根据 布局文件的不同会对应不同的算法。
		 */
        while (tl_modify_gases_item.getChildCount() > CONF_TABLE_MINIMUM_ROW) {
            tl_modify_gases_item.removeViewAt(tl_modify_gases_item
                    .getChildCount() - 2);
        }
        // 控件对的引用。
        gaseDetectRowHolder.clear();
    }

    /**
     * 根据数据动态添加行， ui_dynamicAddRowToTable:(). <br/>
     * date: 2015年1月20日 <br/>
     *
     * @author xuxinwen
     */
    private void ui_dynamicAddRowToTable() {
        ui_clearTableRow();
        /*
         * 在这里根据数据量动态创建 Spinner 和 EditText 的控件对。 其中 每个 “控件对” 对应的是 一个“实体的 List” 。
		 * 每个实体中 存储的是 Spinner 中的一个条目以及该条目对应的 Value 也就是 应该显示在 EditText 中的数据。
		 */

        // 两级 List ， 外层的 List 的 数量决定了要创建 几对 控件，里层的 List 决定了
        // 某个 Spinner 中的 item的数量
        int len = dataGasesListList.size();
        View v = null;
        GaseViewPairs holder = null;
        for (int i = 1; i < len; i++) {
            v = View.inflate(
                    getActivity(),
                    R.layout.hd_hse_common_module_phone_frag_gases_check_inner_row,
                    null);

            TableLayout.LayoutParams _lp = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);
            _lp.topMargin = 5;
            _lp.bottomMargin = 5;
            v.setLayoutParams(_lp);

            holder = new GaseViewPairs();
            holder.type = (Spinner) v
                    .findViewById(R.id.hd_hse_common_module_gases_check_concentration_row_spinner);
            holder.value = (EditText) v
                    .findViewById(R.id.hd_hse_common_module_gases_check_concentration_value_row_et);
            holder.tvResult = (TextView) v
                    .findViewById(R.id.hd_hse_common_module_gases_check_concentration_value_row_tv);
            if (!flag) {
                holder.value.setInputType(InputType.TYPE_CLASS_NUMBER
                        | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                holder.value.setEms(10);
            }

            holder.type.setTag(holder.value);

            // 添加到 ViewHolder 集合中，
            gaseDetectRowHolder.add(holder);

            // 往 TableLayout 末端添加 TableRow
            tl_modify_gases_item.addView(v,
                    tl_modify_gases_item.getChildCount() - 1);
        }
    }

    /**
     * d2u_setGasesInfo:(设置气体检测info). <br/>
     * date: 2015年11月27日 <br/>
     *
     * @author lxf
     */
    private void d2u_setGasesInfo() {
        int _len = gaseDetectRowHolder.size();
        GaseViewPairs _holder = null;

        List<SuperEntity> _list = null;

        for (int i = 0; i < _len; i++) {
            _holder = gaseDetectRowHolder.get(i);
            _list = dataGasesListList.get(i + 1);
            // 设置 Spinner 的值，
            _holder.type
                    .setAdapter(new SuperEntityAdapter(_list, "description"));
            // 设置气体浓度数值，
            if (!flag) {
                Float qtvalue = ((GasDetectSub) _list.get(0)).getQtvalue();
                _holder.value
                        .setText(qtvalue != null ? qtvalue.toString() : "");
            } else {
                String qtsvalues = ((GasDetectSub) _list.get(0)).getQtsvalue();
                _holder.value.setText(qtsvalues != null ? qtsvalues : "");
            }
        }
    }

    /**
     * 数据与 UI 绑定的方法，是否合格。 d2UIsUpToStandard:(). <br/>
     * date: 2015年1月20日 <br/>
     *
     * @author xuxinwen
     */
    private void d2u_IsUpToStandard() {


        // 设置是否合格。
        if (mDataToDisplay.getIshg() == null) {
            tv_check_isok.setText("");
        } else if (mDataToDisplay.getIshg() == 0) {
            tv_check_isok.setText("否");
        } else if (mDataToDisplay.getIshg() == 1) {
            tv_check_isok.setText("是");
        }


    }

    /**
     * setUndone:(设置未完成的气体加测信息). <br/>
     * date: 2015年11月27日 <br/>
     *
     * @param objects
     * @author lxf
     */
    private void setUndone(Object[] objects) {

        if ((GasDetection) objects[0] != null) {
            // 有未完成的数据。
            mDataToSave = (GasDetection) objects[0];
            filterDataOfTopUI(QUERY_APPROVAL_TYPE_UNDONE, objects);
        }

    }

    @SuppressWarnings("unchecked")
    private void d2u_setDataOfApproval(Object[] objects) {
        if (DEBUG)
            Log.w(TAG, "d2u_setDataOfApproval");
        queryApprovalPermission = (List<WorkApprovalPermission>) objects[0];
        // 判断

        for (int i = 0; i < queryApprovalPermission.size(); i++) {
            WorkApprovalPermission mWorkApprovalPermission = queryApprovalPermission
                    .get(i);

            if (mWorkApprovalPermission.getIsQtjcDirector() == 1) {
                zzzrApprovalPermission = mWorkApprovalPermission;
                queryApprovalPermission.remove(i);
                if (null != mWorkOrder.getSxkjlevel()
                        && tssxkj.equals(mWorkOrder.getSxkjlevel())) {
                    // 判断是不是特殊受限空间作业
                    Log.e("tssxkj", "true");
                    isCanContinue = true;
                } else {
                    Log.e("tssxkj", "false");
                }

                break;

            }
        }

        if (isSourceFuyong) {
            isSourceFuyong = false;
            return;
        }
        examinelv_examine.clearDatas();
        examinelv_examine.setData(queryApprovalPermission);
        examinelv_examine.setWorkOrder(mWorkOrder);
        examinelv_examine.setIEventListener(new IEventListenerImpl());
        examinelv_examine.setHandler(mHandler);
    }

    /**
     * 判断是不是所有气体浓度都录入了数值。 isAllGaseDensityHasValue:(). <br/>
     * date: 2014年11月7日 <br/>
     *
     * @return
     * @author xuxinwen
     */
    private boolean ui_isGaseDensityHasValue() {
        boolean _result = false;

        if (!TextUtils.isEmpty(et_row1_gase_value.getText())) {
            _result = true;
        }

        for (GaseViewPairs item : gaseDetectRowHolder) {
            if (TextUtils.isEmpty(item.value.getText())) {
            } else {
                _result = true;
                break;
            }
        }

        return _result;
    }

    /**
     * 判断气体检测所录入数据是否合法， ui_isGaseDensityValueLegal:(). <br/>
     * date: 2015年3月26日 <br/>
     *
     * @return
     * @author xuxinwen
     */
    private boolean ui_isGaseDensityValueLegal() {
        String _txt = et_row1_gase_value.getText().toString();

        if ((!TextUtils.isEmpty(_txt)) // 文本框有值
                && (_txt.length() == 1) // 只有一个字符
                && (_txt.charAt(0) == '.') // 是小数点
                ) {
            return false;
        }

        for (GaseViewPairs item : gaseDetectRowHolder) {
            _txt = item.value.getText().toString();
            if ((!TextUtils.isEmpty(_txt)) // 文本框有值
                    && (_txt.length() == 1) // 只有一个字符
                    && (_txt.charAt(0) == '.') // 是小数点
                    ) {
                return false;
            }
        }

        return true;
    }

    /**
     * 通用监听处理。 ClassName: IEventListenerImpl ()<br/>
     * date: 2014年10月23日 <br/>
     *
     * @author xuxinwen
     * @version GasesCheckFragment
     */
    class IEventListenerImpl implements IEventListener {

        @Override
        public void eventProcess(int eventType, Object... objects)
                throws HDException {

            if (DEBUG)
                Log.w(TAG, "eventProcess");

            if (IEventType.EXAMINE_TOEXAMINE_ClICK == eventType) {
                if (DEBUG)
                    Log.w(TAG, "EXAMINE_TOEXAMINE_ClICK");

                // TODO 去除语法判断
                if (!flag) {
                    if (!ui_isGaseDensityHasValue()) {
                        Toast.makeText(getActivity(), "数据录入不完全",
                                Toast.LENGTH_SHORT).show();
                        throw new HDException("数据录入不完全");
                    }

                    if (!ui_isGaseDensityValueLegal()) {
                        Toast.makeText(getActivity(), "数据录入不合法，不能只输入小数点",
                                Toast.LENGTH_SHORT).show();
                        throw new HDException("数据录入不合法，不能只输入小数点");
                    }
                }

                examinelv_examine.setFragment(GasesCheckFragment.this);

            }

        }

    }

    // TODO
    private void saveGaseData(WorkApprovalPermission wap) {
        if (isCheckTaskRunning) {
            return;
        }
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put(WorkOrder.class.getName(), mWorkOrder);

        // 审批环节点
        curApproveNode = wap;
        mapParam.put(WorkApprovalPermission.class.getName(), curApproveNode);
        mapParam.put(PDAWorkOrderInfoConfig.class.getName(),
                mPDAWorkOrderInfoConfig);

        // if (mDataToDisplay.getUd_zyxk_qtjcid() == null) {
        if (isNewlyAddAction()) {
            // 表示新增

            u2d_saveGaseDetectState();


        }
        mapParam.put(GasDetection.class.getName(), mDataToSave);
        personCard.setTableid(mDataToSave.getUd_zyxk_qtjcid());
        personCard.setDycode(mPDAWorkOrderInfoConfig.getDycode());
        mapParam.put(WorkApprovalPersonRecord.class.getName(), personCard);
        try {
            isCheckTaskRunning = true;
            asyTask.execute(getActionType(), mapParam);
        } catch (HDException e) {
            logger.error(e.getMessage(), e);
        }


    }

    /**
     * 控制 顶部同步按钮的 可见性。由数据库查询自动决定结果。 ctrlSyncBtnVisiblity:(). <br/>
     * date: 2014年12月1日 <br/>
     *
     * @author xuxinwen
     */
    private void ctrlSyncBtnVisiblity() {
        if (DEBUG)
            Log.w(TAG, "ctrlSyncBtnVisiblity");

        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... params) {
                IQueryRelativeConfig _iQueryRelativeConfig = new QueryRelativeConfig();
                RelationTableName _tableName = new RelationTableName();
                _tableName.setSys_type(IRelativeEncoding.ISASYSCPCQT);
                return _iQueryRelativeConfig.isHadRelative(_tableName);
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    btn_sync.setVisibility(View.VISIBLE);
                    btn_sync.setOnClickListener(new ButtonsOnClickListener());
                } else {
                    btn_sync.setVisibility(View.INVISIBLE);
                    btn_sync.setOnClickListener(null);
                }
            }

        }.execute();
    }

    /**
     * 保存 当前气体检测 界面的数据。 saveGaseDetectState:(). <br/>
     * date: 2014年10月21日 <br/>
     *
     * @author xuxinwen
     */
    private void u2d_saveGaseDetectState() {
        mDataToSave.setJclocation_desc(tv_check_location.getText().toString());
        // 保存时间
        mDataToSave.setJctime(btn_check_time.getText().toString());

        // 保存检测方式
        String value = ((Domain) dataDetectMethodList.get(spinner_check_pattern
                .getSelectedItemPosition())).getValue();
        mDataToSave.setJcmethod(value);

        // 保存检测单位
        mDataToSave.setJcdept(btn_check_company.getText().toString());

        if (flag) {
            int checkedId = checkRadioGroup.getCheckedRadioButtonId();
            if (checkedId == R.id.rbtn_yes) {
                mDataToSave.setIshg(1);
            } else if (checkedId == R.id.rbtn_no) {
                mDataToSave.setIshg(0);
            }
        }

        // 保存各个气体的检测浓度。
        List<SuperEntity> saveData = new ArrayList<SuperEntity>();

        for (int i = 0; i < dataGasesListList.size(); i++) {
            if (i == 0) {
                // 第一个 得到数据源中对应的 实体
                List<SuperEntity> gasesInfo = dataGasesListList.get(i);

                // 得到选中实体
                int index = spinner_row1_gase_type.getSelectedItemPosition();
                GasDetectSub source = (GasDetectSub) gasesInfo.get(index);

                // 创建新的实体， 设置相应信息。
                GasDetectSub des = new GasDetectSub();
                des.setQttype(source.getQttype());

                String qtValue = et_row1_gase_value.getText().toString();

                if (!TextUtils.isEmpty(qtValue)) {
                    if (!flag) {
                        try {
                            des.setQtvalue(Float.valueOf(qtValue));
                        } catch (NumberFormatException e) {
                            // 不能转换成数字的情况是只有小数点的时候
                            logger.error("气体检测录入数据格式有问题：" + qtValue
                                    + " 系统将值设为0");
                            des.setQtvalue(0f);
                        }
                    } else {
                        des.setQtsvalue(qtValue);
                    }
                }

                des.setMaxvalue(source.getMaxvalue());
                des.setMinvalue(source.getMinvalue());
                des.setIsbj(source.getIsbj());

                saveData.add(des);

                continue;
            }

            GaseViewPairs viewPairs = gaseDetectRowHolder.get(i - 1);
            List<SuperEntity> gasesInfo = dataGasesListList.get(i);

            // 得到数据源中对应的 实体
            int index = viewPairs.type.getSelectedItemPosition();
            GasDetectSub source = (GasDetectSub) gasesInfo.get(index);

            // 创建新的实体， 设置相应信息。
            GasDetectSub des = new GasDetectSub();
            des.setQttype(source.getQttype());
            if (!TextUtils.isEmpty(viewPairs.value.getText().toString())) {
                if (!flag) {
                    des.setQtvalue(Float.valueOf(viewPairs.value.getText()
                            .toString()));
                } else {
                    des.setQtsvalue(viewPairs.value.getText().toString());
                }
            }

            des.setMaxvalue(source.getMaxvalue());
            des.setMinvalue(source.getMinvalue());
            des.setIsbj(source.getIsbj());

            saveData.add(des);
        }
        mDataToSave.clearChild(GasDetectSub.class.getName());
        mDataToSave.setChild(GasDetectSub.class.getName(), saveData);

    }


    /**
     * setDataOfHistory:(显示历史气体检测记录). <br/>
     * date: 2015年11月27日 <br/>
     *
     * @param objects
     * @author lxf
     */
    private void setDataOfHistory(Object[] objects) {
        if (objects[0] == null) {
            return;
        }
        ArrayList<Map<String, String>> data = (ArrayList<Map<String, String>>) objects[0];
        // TODO
        /*
         * 这里要处理数据的分离，与用来显示历史记录的控件结合。因为该界面显示历史记录 所使用的控件与横版不同，数据分离要在控件外部完成，
		 */
        mDataHistoryHeaders.clear();

        if (data.size() > 0) {
            // 取得第一组数据的 key，
            Set<String> _keySet = new LinkedHashSet<String>();
            Map<String, String> _item = null;
            int _size = data.size();
            for (int i = 0; i < _size; i++) {
                _item = data.get(i);
                // 将该 item 里的 key 全部添加到 keySet 中，
                _keySet.addAll(_item.keySet());
            }

            Iterator<String> iterator = _keySet.iterator();

            while (iterator.hasNext()) {
                String _next = iterator.next();
                mDataHistoryHeaders.add(_next);
            }

        }


        TableTitleAdapter _tableTitleAdapter = new TableTitleAdapter(
                getActivity(), mDataHistoryHeaders);
        gHistoryTitle.setAdapter(_tableTitleAdapter);

        // 设置 Title ListView 的Divider 实现 item 之间的间距。
        gHistoryTitle.setDivider(new ColorDrawable(0x00000000));
        gHistoryTitle
                .setDividerHeight((int) getActivity()
                        .getResources()
                        .getDimensionPixelOffset(
                                R.dimen.hd_hse_common_module_phone_gases_check_history_list_dividerHeight));

        gHistoryContent.setAdapter(new TableContentPagerAdapter(getActivity(),
                mDataHistoryHeaders, data, gHistoryTitle));

        combinationUI.bindIndictor2ViewPager(getActivity(), gHistoryRadioGroup,
                gHistoryContent);

        // 因为遍历数据中最晚时间与当前更新UI 的操作无关, 等到下一个消息的时候处理
        // Message _message = Message.obtain();
        // _message.what = 0;
        // _message.obj = _data;
        // mHandler.sendMessage(_message);
    }

    /**
     * 此页面中有 ViewPager与Indictor 结合的UI 需要这样一个工具类
     */
    private CombinationUI combinationUI = new CombinationUI();

    private RadioButton radioYesButton;

    /**
     * 查询未完成的订单， 异步查询， queryUndone:(). <br/>
     * date: 2014年10月27日 <br/>
     * TODO
     *
     * @author xuxinwen
     */
    private void queryUndone() {
        // 查询是否有未完成的订单。
        if (mWorkOrder == null) {
            return;
        }
        try {
            // 历史记录部分。
            queryWorkInfo.queryUndoneGasInfo(mWorkOrder,
                    new IQueryCallEventListenerImpl(
                            IQueryCallEventListenerImpl.TYPE_UNDONE));
        } catch (HDException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        WorkApprovalPermission _curr = (WorkApprovalPermission) data
                .getSerializableExtra(ReadCardExamineActivity.WORKAPPROVALPERMISSION);
        btn_check_company.setText(_curr.getDepartmentdesc());
        isTPQM = false;
        if (resultCode == IEventType.TPQM_TYPE) {
            isTPQM = true;
            image = (Image) data
                    .getSerializableExtra(PaintSignatureActivity.IMAGE);
        } else {
            image = (Image) data
                    .getSerializableExtra(ReadCardExamineActivity.IMAGE);
        }
        saveGaseData(_curr);
        // examinelv_examine.setCurrentEntity(_curr);

    }

    /**
     * setReuseBtnVisibility:(设置reuseBtn的可见性). <br/>
     * date: 2015年9月7日 <br/>
     *
     * @author LiuYang
     */
    public void setReuseBtnVisibility() {
        QueryRelativeConfig config = new QueryRelativeConfig();
        RelationTableName tableName = new RelationTableName();
        tableName.setSys_type(IRelativeEncoding.FYQTJC);
        tableName.setSys_value(mWorkOrder.getZypclass());
        isfuyong = config.isHadRelative(tableName);
        if (isfuyong) {
            btn_reuse.setVisibility(View.VISIBLE);
            btn_reuse.setOnClickListener(new ButtonsOnClickListener());
        } else {
            btn_reuse.setVisibility(View.GONE);
        }
    }

    /**
     * 查询结果的处理函数，用于查询结果与界面显示的绑定。 ClassName: IQueryCallEventListenerImpl ()<br/>
     * date: 2015年1月20日 <br/>
     *
     * @author xuxinwen
     * @version GasesCheckFragment_1
     */
    class IQueryCallEventListenerImpl implements IQueryCallEventListener {
        /*
         * 这里为什么会出现自己定义的四个查询类型， 因为四个查询方法使用了同样的接口，我们可以写四个实现类来分别处理，
         * 这样就不会出现自己定义的四个查询类型。
         */
        // 气体检测信息
        public static final int TYPE_GASE_INFO = 0;

        // 历史记录查询
        public static final int TYPE_HISTORY = 1;

        // 为完成查询
        public static final int TYPE_UNDONE = 2;

        // 查询审批环节点。
        public static final int TYPE_APPROVAL = 3;

        // 复用
        public static final int TYPE_MULTIPLEX = 4;

        private int mQueryType;

        public IQueryCallEventListenerImpl(int queryType) {
            mQueryType = queryType;
        }

        @Override
        public void callBackProcess(int type, Object... objects)
                throws HDException {
            if (type == IMessageWhat.END) {
                // 查询完成。
                switch (mQueryType) {
                    case TYPE_GASE_INFO:
                        // 气体检测查询。
                        mDataToAdd = (GasDetection) objects[0];
                        filterDataOfTopUI(QUERY_APPROVAL_TYPE_ADD, objects);
                        break;
                    case TYPE_UNDONE:
                        // 表示为完成的气体加测
                        setUndone(objects);
                        break;
                    case TYPE_APPROVAL:
                        d2u_setDataOfApproval(objects);
                        // TODO 关闭等待 dialog
                        break;
                    case TYPE_HISTORY:
                        // 表示查询历史检测的气体
                        setDataOfHistory(objects);
                        break;
                    case TYPE_MULTIPLEX:
                        // 复用
                        if (objects[0] == null) {
                            ToastUtils.toast(getActivity(), "当前无复用数据，请重新输入");
                            return;
                        }

                        mDataToAdd = (GasDetection) objects[0];
                        // if (mDataToAdd != null
                        // && !mDataToAdd.getWritenbypda().equalsIgnoreCase(
                        // IWorkOrderStatus.GAS_STATUS_FINISH)) {
                        mDataToSave = mDataToAdd;
                        // }
                        filterDataOfTopUI(QUERY_APPROVAL_TYPE_REUSE, objects);
                        if (mDataToAdd != null
                                && mDataToAdd.getWritenbypda() != null
                                && mDataToAdd.getWritenbypda().equals(
                                IWorkOrderStatus.GAS_STATUS_FINISH)) {
                            PDAWorkOrderInfoConfig naviCurrentEntity = (PDAWorkOrderInfoConfig) ((NaviFrameActivity) getActivity())
                                    .getNaviCurrentEntity();
                            if (null != naviCurrentEntity) {
                                naviCurrentEntity.setIsComplete(1);

                                setNaviCurrentEntity(naviCurrentEntity);
                            }
                        }

                        refreshData();


                        // lxf 注释， 如果放开，isend保存时 新增数据
                        // if (mDataToDisplay != null) {
                        // mDataToDisplay.setUd_zyxk_qtjcid(null);
                        // }
                        mDataToAdd = null;


                        break;
                    default:
                        break;
                }
            }
            if (type == IMessageWhat.ERROR) {
                ToastUtils.toast(getActivity(), objects[0].toString());
                switch (mQueryType) {
                    case TYPE_MULTIPLEX:
                        isReuseBtnClicked = false;
                        break;
                    default:
                        break;
                }
            } else {
                // Toast.makeText(mActivity, "正在查询，请稍后", Toast.LENGTH_SHORT)
                // .show();
                // 正在查询
                switch (mQueryType) {
                    case TYPE_GASE_INFO:
                        // 气体检测查询。
                        // TODO 开启等待 dialog

                        break;
                    case TYPE_UNDONE:
                        // TODO 开启等待 dialog

                        break;
                    case TYPE_APPROVAL:

                        break;

                    default:
                        break;
                }
            }
        }
    }

    Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case ShowSignDialog.HANDLER_WHAT:
                    examinelv_examine
                            .setCurrentEntity((WorkApprovalPermission) (msg.obj));
                    queryHistory();
                    break;

                default:
                    break;
            }
        }

        ;
    };

    /**
     * 是否只有氧气不合格
     *
     * @return
     */
    private boolean isOnlyOxygenBelowGrade(List<GasDetectSub> childList) {
        for (GasDetectSub mGasDetectSub : childList) {
            String qtYtpe = mGasDetectSub.getQttype();
            if (qtYtpe.equals(oxygenType)) {
                if (mGasDetectSub.getIsPass() != 0) {
                    return false;
                }
            } else {
                if (mGasDetectSub.getIsPass() == 0) {
                    return false;
                }
            }

        }
        return true;
    }

}
