package com.hd.hse.cc.phone.ui.activity.interrupt;

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

public class WorkOrderInterruptActivity extends BaseCloseOrCancelActivity {

    public static Logger logger = LogUtils
            .getLogger(WorkOrderInterruptActivity.class);
    /**
     * actionBar:TODO(?????????).
     */
    // private HSEActionBarBack actionBar;

    /**
     * PAGE_TYPE:TODO(??????????????????).
     */
    private final static String PAGE_TYPE = "PGTYPE305";

    private final static String PauseStatus = "PauseStatus";
    /**
     * workOrder:TODO(?????????).
     */
    private WorkOrder workOrder;
    /**
     * workInfoConfig:TODO(????????????).
     */
    private PDAWorkOrderInfoConfig workInfoConfig;
    /**
     * busAction:TODO(????????????????????????).
     */
    private BusinessAction busAction;
    /**
     * actionLsr:TODO().
     */
    private AbstractCheckListener actionLsr;

    /**
     * asyTask:TODO(??????????????????).
     */
    private BusinessAsyncTask asyTask;
    // ?????????????????????
    private IQueryWorkInfo quertWorkInfo = new QueryWorkInfo();

    private ProgressDialog prgDialog;
    /**
     * wap:TODO(????????????).
     */
    public WorkApprovalPermission wap;
    /**
     * wap:TODO(??????????????????).
     */
    private WorkApprovalPermission currentWAP;

    private List<WorkApprovalPermission> examineDatas = null;

    private Image image;
    private boolean isSQ; // ??????????????????
    //key???zysqid,value?????????????????????map
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
        // ?????????????????????
        setCustomActionBar(eventListent, new String[]{IActionBar.TV_BACK,
                IActionBar.IBTN_LEVELTWO_MORE, IActionBar.TV_TITLE});

        setCustomMenuBar(new String[]{IActionBar.ET_SEARCH,
                IActionBar.ITEM_PHOTOGTAPH, IActionBar.ITEM_PHOTOMANAGER,
                IActionBar.ITEM_UPWORKORDER});
        // ?????????????????????
        setActionBartitleContent(workOrder.getZypclassname(), true);
        // ?????????????????????
        setWorkInfo(workOrder);
        // ????????????????????????????????????
        setPopDetailWorkerOrer(workOrder);
        // ??????????????????
        setCurrentNaviTouchEntity(workInfoConfig);
    }

    /**
     * TODO ????????????????????? initParam:(). date: 2014???10???25???
     *
     * @author zhulei
     */
    private void initParam() {
        workOrder = (WorkOrder) getIntent().getSerializableExtra(
                TaskTabulationActivity.WORK_ORDER);
        // ????????????????????????
        actionLsr = new CheckControllerActionListener();
        busAction = new BusinessAction(actionLsr);
        asyTask = new BusinessAsyncTask(busAction, auditCallBack);
    }

    /**
     * TODO ?????????????????????????????? initTopView:(). date: 2014???10???25???
     *
     * @author zhulei
     */
    private void initInfo() {
        if (workInfoConfig == null) {
            workInfoConfig = new PDAWorkOrderInfoConfig();
            workInfoConfig.setContype(10);
            workInfoConfig.setPscode(IConfigEncoding.ZD);
            workInfoConfig.setContypedesc("??????");
            workInfoConfig.setSname("??????");
            workInfoConfig.setDycode(PAGE_TYPE);
        }
        try {
            // ?????????????????????
            workOrder = quertWorkInfo.queryWorkInfo(workOrder, null);
            // ??????????????????
            examineDatas = quertWorkInfo.queryApprovalPermission(workOrder,
                    workInfoConfig, null, null);
        } catch (HDException e) {
            ToastUtils.imgToast(WorkOrderInterruptActivity.this,
                    R.drawable.hd_hse_common_msg_wrong, e.getMessage());
        }


        setData(examineDatas);
        setiEventLsn(eventListent);
        examineListView.setWorkOrder(workOrder);
        hjdmap = new HashMap<>();
        hjdmap.put(workOrder.getUd_zyxk_zysqid(), examineDatas.size());
        SystemProperty.getSystemProperty().setHjdmp(hjdmap);
        pagetypemap = new HashMap<>();
        pagetypemap.put(WorkOrderInterruptActivity.PauseStatus, WorkOrderInterruptActivity.PAGE_TYPE);
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
                    // ???????????????
                    // ???????????????
                    upWorkOrder();
                    break;
                case IEventType.DOWN_WORK_LIST_LOAD:
                    // ?????????????????????
                    if (WorkOrderInterruptActivity.this != null) {
                        WorkOrderInterruptActivity.this.finish();
                    }
                    break;
                case IEventType.DOWN_WORK_LIST_SHOW:
                    // ?????????????????????
                    break;
            }

        }
    };

    public void saveModifiedData(WorkApprovalPermission workApprovalPermission)
            throws HDException {
        HashMap<String, Object> paras = new HashMap<String, Object>();
        // ???????????????
        paras.put(WorkOrder.class.getName(), workOrder);
        // ??????????????????
        if (workApprovalPermission != null) {
            paras.put(WorkApprovalPermission.class.getName(),
                    workApprovalPermission);
        }
        paras.put(PDAWorkOrderInfoConfig.class.getName(), workInfoConfig);
        prgDialog = new ProgressDialog(this, "????????????????????????");
        prgDialog.show();
        asyTask.execute(IActionType.ACTION_TYPE_INTERRUPT, paras);
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
                ToastUtils.imgToast(WorkOrderInterruptActivity.this,
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
                // ????????????
                ImageUtils mImageUtils = new ImageUtils();
                mImageUtils.saveImage(workInfoConfig,
                        (WorkApprovalPermission) currentWAP, mWorkOrder, image);
            }
            prgDialog.showMsg("?????????");
            examineListView.setCurrentEntity(currentWAP);
            prgDialog.dismiss();
            //????????????

            upzyp = new UpZYPProgressDialog(WorkOrderInterruptActivity.this);
            upzyp.setGetDataSourceListener(eventListent);

            QueryWorkOrderInfo mQueryWorkOrderInfo = new QueryWorkOrderInfo();
            String[] ud_zyxk_zysqids = null;
            // ????????????????????????????????????ud_zyxk_zysqids
            if (!getIntent().getBooleanExtra("isJoin", false)) {
                ud_zyxk_zysqids = new String[1];
                ud_zyxk_zysqids[0] = mWorkOrder.getUd_zyxk_zysqid();
            }

            if (currentWAP.getIsend() == 1) {
                try {
                    List<WorkOrder> data = mQueryWorkOrderInfo
                            .queryWorkOrder(ud_zyxk_zysqids);

                    if (null != data && data.size() > 0) {
                        upzyp.execute("??????", "???????????????????????????????????????.....", "", data);
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
            // ??????????????????????????????????????????
            if (resultCode == IEventType.READCARD_TYPE) {
                image = (Image) intent
                        .getSerializableExtra(ReadCardExamineActivity.IMAGE);
            }

            if (currentWAP != null && currentWAP.getPersonid() != null) {
                try {
                    saveModifiedData(currentWAP);
                } catch (HDException e) {
                    ToastUtils.imgToast(WorkOrderInterruptActivity.this,
                            R.drawable.hd_hse_common_msg_wrong, e.getMessage());
                }
            }
        }
    }

    /**
     * upzyp:TODO(???????????????).
     */
    private UpZYPProgressDialog upzyp = null;

    /**
     * ???????????????
     */
    private void upWorkOrder() {

        upzyp = new UpZYPProgressDialog(this);
        upzyp.setGetDataSourceListener(eventListent);

        QueryWorkOrderInfo mQueryWorkOrderInfo = new QueryWorkOrderInfo();
        String[] ud_zyxk_zysqids = null;
        // ????????????????????????????????????ud_zyxk_zysqids
        if (!getIntent().getBooleanExtra("isJoin", false)) {
            ud_zyxk_zysqids = new String[1];
            ud_zyxk_zysqids[0] = mWorkOrder.getUd_zyxk_zysqid();
        }

        try {
            List<WorkOrder> data = mQueryWorkOrderInfo
                    .queryWorkOrder(ud_zyxk_zysqids);
            if (null != data && data.size() > 0) {
                upzyp.execute("??????", "???????????????????????????????????????.....", "", data);
            } else {
                ToastUtils.toast(getBaseContext(), "????????????????????????");
            }
        } catch (HDException e) {
            e.printStackTrace();
        }

    }

}
