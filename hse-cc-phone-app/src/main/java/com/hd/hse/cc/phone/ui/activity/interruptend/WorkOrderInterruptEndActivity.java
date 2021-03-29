package com.hd.hse.cc.phone.ui.activity.interruptend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.cc.phone.R;
import com.hd.hse.cc.phone.ui.activity.worktask.BaseCloseOrCancelActivity;
import com.hd.hse.cc.phone.ui.activity.worktask.TaskTabulationActivity;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.dialog.UpZYPProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.ui.activity.PaintSignatureActivity;
import com.hd.hse.common.module.phone.ui.activity.ReadCardExamineActivity;
import com.hd.hse.common.module.phone.ui.utils.ImageUtils;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.service.workorder.checkrules.CheckControllerActionListener;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkOrderInfo;
import com.hd.hse.system.SystemProperty;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

public class WorkOrderInterruptEndActivity extends BaseCloseOrCancelActivity {

    public static Logger logger = LogUtils
            .getLogger(WorkOrderInterruptEndActivity.class);
    /**
     * actionBar:TODO(标题栏).
     */
    // private HSEActionBarBack actionBar;

    /**
     * PAGE_TYPE:TODO(中断结束类型).
     */
    private final static String PAGE_TYPE = "PGTYPE306";

    private final static String PauseStatus = "PauseStatus";
    /**
     * workOrder:TODO(作业票).
     */
    private WorkOrder workOrder;
    /**
     * workInfoConfig:TODO(配置信息).
     */
    private PDAWorkOrderInfoConfig workInfoConfig;
    /**
     * busAction:TODO(后台业务处理对象).
     */
    private BusinessAction busAction;
    /**
     * actionLsr:TODO().
     */
    private AbstractCheckListener actionLsr;

    /**
     * asyTask:TODO(后台异步任务).
     */
    private BusinessAsyncTask asyTask;
    // 作业票查询服务
    private IQueryWorkInfo quertWorkInfo = new QueryWorkInfo();

    private ProgressDialog prgDialog;
    /**
     * wap:TODO(审批环节).
     */
    public WorkApprovalPermission wap;
    /**
     * wap:TODO(当前审批环节).
     */
    private WorkApprovalPermission currentWAP;

    private List<WorkApprovalPermission> examineDatas = null;

    private Image image;
    private boolean isSQ; // 是否图片签名
    //key为zysqid,value为环节点条数的map
    private HashMap<String, Integer> hjdmap;
    private HashMap<String, String> pagetypemap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setQueryWorkInfo(quertWorkInfo);
    }

    @Override
    protected void initData() {
        // TODO Auto-generated method stub
        super.initData();
        initParam();
        initInfo();
    }

    @Override
    public void initActionBar() {
        super.initActionBar();
        // 设置导航栏信息
        setCustomActionBar(eventListent, new String[]{IActionBar.TV_BACK,
                IActionBar.IBTN_LEVELTWO_MORE, IActionBar.TV_TITLE});

        setCustomMenuBar(new String[]{IActionBar.ET_SEARCH,
                IActionBar.ITEM_PHOTOGTAPH, IActionBar.ITEM_PHOTOMANAGER,
                IActionBar.ITEM_UPWORKORDER});
        // 设置导航栏标题
        setActionBartitleContent(workOrder.getZypclassname(), true);
        // 未拍照设置实体
        setWorkInfo(workOrder);
        // 设置作业票详细信息弹出框
        setPopDetailWorkerOrer(workOrder);
        // 设置基础信息
        setCurrentNaviTouchEntity(workInfoConfig);
    }

    /**
     * TODO 初始化全局对象 initParam:(). date: 2014年10月25日
     *
     * @author zhulei
     */
    private void initParam() {
        workOrder = (WorkOrder) getIntent().getSerializableExtra(
                TaskTabulationActivity.WORK_ORDER);
        // 后台业务处理对象
        actionLsr = new CheckControllerActionListener();
        busAction = new BusinessAction(actionLsr);
        asyTask = new BusinessAsyncTask(busAction, auditCallBack);
    }

    /**
     * TODO 初始化作业票详细信息 initTopView:(). date: 2014年10月25日
     *
     * @author zhulei
     */
    private void initInfo() {
        if (workInfoConfig == null) {
            workInfoConfig = new PDAWorkOrderInfoConfig();
            workInfoConfig.setContype(11);
            workInfoConfig.setPscode(IConfigEncoding.ZDJS);
            workInfoConfig.setContypedesc("中断结束");
            workInfoConfig.setSname("中断结束");
            workInfoConfig.setDycode(PAGE_TYPE);
        }
        try {
            // 查询作业票信息
            workOrder = quertWorkInfo.queryWorkInfo(workOrder, null);
            // 查询审批环节
            examineDatas = quertWorkInfo.queryApprovalPermission(workOrder,
                    workInfoConfig, null, null);
        } catch (HDException e) {
            ToastUtils.imgToast(WorkOrderInterruptEndActivity.this,
                    R.drawable.hd_hse_common_msg_wrong, e.getMessage());
        }


        setData(examineDatas);
        setiEventLsn(eventListent);
        examineListView.setWorkOrder(workOrder);
        hjdmap = new HashMap<>();
        hjdmap.put(workOrder.getUd_zyxk_zysqid(), examineDatas.size());
        SystemProperty.getSystemProperty().setHjdmp(hjdmap);
        pagetypemap = new HashMap<>();
        pagetypemap.put(WorkOrderInterruptEndActivity.PauseStatus, WorkOrderInterruptEndActivity.PAGE_TYPE);
        SystemProperty.getSystemProperty().setPagetypemp(pagetypemap);

    }

    @Override
    protected void initView() {
        // TODO Auto-generated method stub
        super.initView();

        tableLayout.setVisibility(View.GONE);
    }


    private IEventListener eventListent = new IEventListener() {

        @SuppressWarnings("unchecked")
        @Override
        public void eventProcess(int eventType, Object... objects)
                throws HDException {
            switch (eventType) {

                case IEventType.ACTIONBAR_ITEM_UP_CLICK:
                    // 上传作业票
                    // 作业票上传
                    upWorkOrder();
                    break;
                case IEventType.DOWN_WORK_LIST_LOAD:
                    // 上传作业票成功
                    if (WorkOrderInterruptEndActivity.this != null) {
                        WorkOrderInterruptEndActivity.this.finish();
                    }
                    break;
                case IEventType.DOWN_WORK_LIST_SHOW:
                    // 上传作业票失败
                    break;
            }

        }
    };

    public void saveModifiedData(WorkApprovalPermission workApprovalPermission)
            throws HDException {
        HashMap<String, Object> paras = new HashMap<String, Object>();
        // 作业票信息
        paras.put(WorkOrder.class.getName(), workOrder);
        // 审批环节信息
        if (workApprovalPermission != null) {
            paras.put(WorkApprovalPermission.class.getName(),
                    workApprovalPermission);
        }
        paras.put(PDAWorkOrderInfoConfig.class.getName(), workInfoConfig);
        prgDialog = new ProgressDialog(this, "作业中断信息保存");
        prgDialog.show();
        asyTask.execute(IActionType.ACTION_TYPE_INTERRUPTEND, paras);
    }


    private AbstractAsyncCallBack auditCallBack = new AbstractAsyncCallBack() {

        @Override
        public void start(Bundle msgData) {
            // TODO Auto-generated method stub

        }

        @Override
        public void processing(Bundle msgData) {
            // TODO Auto-generated method stub
            if (msgData.containsKey(actionLsr.CHECKACTION)) {
                prgDialog.showMsg(msgData.getString(actionLsr.CHECKACTION));
            }
        }

        @Override
        public void error(Bundle msgData) {
            // TODO Auto-generated method stub
            if (msgData.containsKey(IActionType.ACTION_TYPE_CANCEL)) {
                prgDialog.dismiss();
                ToastUtils.imgToast(WorkOrderInterruptEndActivity.this,
                        R.drawable.hd_hse_common_msg_wrong,
                        msgData.getString(IActionType.ACTION_TYPE_CANCEL));
            }
        }

        @Override
        public void end(Bundle msgData) {
            // TODO Auto-generated method stub
            if (isSQ) {
                PaintSignatureActivity.saveImageToDB(false, currentWAP,
                        workOrder, null, image);
            } else if (image != null && workInfoConfig != null) {
                // 保存拍照
                ImageUtils mImageUtils = new ImageUtils();
                mImageUtils.saveImage(workInfoConfig,
                        (WorkApprovalPermission) currentWAP, mWorkOrder, image);
            }
            prgDialog.showMsg("成功！");
            examineListView.setCurrentEntity(currentWAP);
            prgDialog.dismiss();

            //自动上传

            upzyp = new UpZYPProgressDialog(WorkOrderInterruptEndActivity.this);
            upzyp.setGetDataSourceListener(eventListent);

            QueryWorkOrderInfo mQueryWorkOrderInfo = new QueryWorkOrderInfo();
            String[] ud_zyxk_zysqids = null;
            // 判断是否合并审批，并赋值ud_zyxk_zysqids
            if (!getIntent().getBooleanExtra("isJoin", false)) {
                ud_zyxk_zysqids = new String[1];
                ud_zyxk_zysqids[0] = mWorkOrder.getUd_zyxk_zysqid();
            }

            if (currentWAP.getIsend() == 1) {

                try {
                    List<WorkOrder> data = mQueryWorkOrderInfo
                            .queryWorkOrder(ud_zyxk_zysqids);

                    if (null != data && data.size() > 0) {
                        upzyp.execute("上传", "上传作业票信息，请耐心等待.....", "", data);

                    }
                } catch (HDException e) {
                    e.printStackTrace();
                }
            }


        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == IEventType.READCARD_TYPE && intent != null) {
            currentWAP = (WorkApprovalPermission) intent
                    .getSerializableExtra(ReadCardExamineActivity.WORKAPPROVALPERMISSION);
            isSQ = false;
            if (resultCode == IEventType.TPQM_TYPE) {
                isSQ = true;
                image = (Image) intent
                        .getSerializableExtra(PaintSignatureActivity.IMAGE);
            }
            // 获取刷卡审批，拍照，照片信息
            if (resultCode == IEventType.READCARD_TYPE) {
                image = (Image) intent
                        .getSerializableExtra(ReadCardExamineActivity.IMAGE);
            }

            if (currentWAP != null && currentWAP.getPersonid() != null) {
                try {
                    saveModifiedData(currentWAP);
                } catch (HDException e) {
                    ToastUtils.imgToast(WorkOrderInterruptEndActivity.this,
                            R.drawable.hd_hse_common_msg_wrong, e.getMessage());
                }
            }
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

        upzyp = new UpZYPProgressDialog(this);
        upzyp.setGetDataSourceListener(eventListent);

        QueryWorkOrderInfo mQueryWorkOrderInfo = new QueryWorkOrderInfo();
        String[] ud_zyxk_zysqids = null;
        // 判断是否合并审批，并赋值ud_zyxk_zysqids
        if (!getIntent().getBooleanExtra("isJoin", false)) {
            ud_zyxk_zysqids = new String[1];
            ud_zyxk_zysqids[0] = mWorkOrder.getUd_zyxk_zysqid();
        }

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


}
