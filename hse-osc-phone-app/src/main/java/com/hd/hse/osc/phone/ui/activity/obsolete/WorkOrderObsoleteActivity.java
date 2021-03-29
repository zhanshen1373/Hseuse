package com.hd.hse.osc.phone.ui.activity.obsolete;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.dialog.UpZYPProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.custom.ExamineListView;
import com.hd.hse.common.module.phone.ui.activity.NaviFrameActivity;
import com.hd.hse.common.module.phone.ui.activity.PaintSignatureActivity;
import com.hd.hse.common.module.phone.ui.custom.AlertDialogListView;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.entity.base.Domain;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.osc.phone.R;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.service.workorder.checkrules.CheckControllerActionListener;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkOrderInfo;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: WorkOrderObsoleteActivity (作业票作废)<br/>
 * date: 2015年1月23日 <br/>
 *
 * @author wenlin
 */
public class WorkOrderObsoleteActivity extends NaviFrameActivity {

    public static Logger logger = LogUtils
            .getLogger(WorkOrderObsoleteActivity.class);
    public final static String WORK_ORDER = "workorder";
    /**
     * 作废成功
     */
    public final static int OBSOLETESUCESS = 0x1111;

    /**
     * PAGE_TYPE:TODO(作废界面类型).
     */
    private final static String PAGE_TYPE = "PGTYPE010";

    /**
     * mWorkerOrder:TODO(作业票实体).
     */
    public WorkOrder mWorkOrder;

    /**
     * curApproveNode:TODO(当前审批环节点).
     */
    private WorkApprovalPermission curApproveNode;

    /**
     * action:TODO(后台业务处理).
     */
    private BusinessAction action;

    /**
     * actLsr:TODO(后台业务处理).
     */
    private AbstractCheckListener actLsr;

    /**
     * askTask:TODO(异步任务).
     */
    private BusinessAsyncTask askTask;

    /**
     * prgDialog:TODO(耗时等待).
     */
    private ProgressDialog prgDialog;

    /**
     * tvInvalidRs:TODO(作废原因).
     */
    private TextView evInvalidRs;

    /**
     * ibRsMore:TODO(更过作废原因).
     */
    private TextView ibRsMore;

    /**
     * tvInvalidTime:TODO(作废时间).
     */
    private TextView tvInvalidTime;

    /**
     * bt_save:TODO(保存).
     */
    // private Button bt_save;

    /**
     * lsvEaxmine:TODO(审批环节点).
     */
    private ExamineListView<WorkApprovalPermission> lsvEaxmine;

    private AlertDialogListView mAlertDialoglst;

    private Image img;
    private boolean isSQ; // 是否电子签名

    public WorkOrderObsoleteActivity() {
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hd_hse_osc_phone_app_invalid);

        initParam();
        initView();
        initUiPage();
        initExamineView();
    }

    /**
     * initParam:(初始化参数). <br/>
     * date: 2015年1月23日 <br/>
     *
     * @author wenlin
     */
    private void initParam() {

        // 作业票
        if (mWorkOrder == null)
            mWorkOrder = (WorkOrder) getIntent().getSerializableExtra(
                    WORK_ORDER);

        // 初始化异步业务
        actLsr = new CheckControllerActionListener();
        action = new BusinessAction(actLsr);
        askTask = new BusinessAsyncTask(action, askCallBack);
    }

    /**
     * initView:(初始化界面). <br/>
     * date: 2015年1月23日 <br/>
     *
     * @author wenlin
     */
    private void initView() {
        evInvalidRs = (TextView) findViewById(R.id.hd_hse_osc_phone_app_invalid_et1);
        // 选择按钮
        ibRsMore = (TextView) findViewById(R.id.hd_hse_osc_phone_app_invalid_ib1);
        // 作废时间
        tvInvalidTime = (TextView) findViewById(R.id.hd_hse_osc_phone_app_invalid_tv_time);
        tvInvalidTime.setText(mWorkOrder.getZfpersontime());
        // 保存按钮
        // bt_save = (Button)
        // findViewById(R.id.hd_hse_osc_app_phone_obsolete_bt_save);
        // 设置导航栏
        setCustomActionBar(eventLst, new String[]{IActionBar.TV_BACK,
                IActionBar.TV_TITLE, IActionBar.IBTN_LEVELTWO_MORE});
        // 设置工具栏
        setCustomMenuBar(new String[]{IActionBar.ITEM_PHOTOGTAPH,
                IActionBar.ITEM_PHOTOMANAGER, IActionBar.ITEM_UPWORKORDER});

        if (null != mWorkOrder.getZypclassname()) {
            // 设置导航栏标题
            setActionBartitleContent(mWorkOrder.getZypclassname(), true);
        } else {
            setActionBartitleContent("", false);
            ToastUtils.imgToast(WorkOrderObsoleteActivity.this,
                    R.drawable.hd_hse_common_msg_wrong, "作业票类型为空，请联系管理员！");
        }

        IQueryWorkInfo mQueryWork = new QueryWorkInfo();
        setQueryWorkInfo(mQueryWork);
        setWorkInfo(mWorkOrder);

        PDAWorkOrderInfoConfig config = new PDAWorkOrderInfoConfig();
        config.setPscode(IConfigEncoding.SP);
        config.setContype(IConfigEncoding.ZY_TYPE);
        config.setContypedesc("作废");
        setCurrentNaviTouchEntity(config);

        // 设置作业票详细信息弹出框
        setPopDetailWorkerOrer(mWorkOrder);

        ibRsMore.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mAlertDialoglst = new AlertDialogListView(
                        WorkOrderObsoleteActivity.this, initChooseDatas(),
                        ListView.CHOICE_MODE_SINGLE);
                mAlertDialoglst.setPopWinTitle("作废原因");
                mAlertDialoglst.setOnClickListener(eventLst);
            }
        });

        // bt_save.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // try {
        // // 保存作废信息
        // auditOnClick(IEventType.EXAMINE_SAVE_ClICK, null);
        // } catch (HDException e) {
        // logger.error(e.getMessage(), e);
        // }
        // }
        // });
    }

    /**
     * initUiPage:(初始化界面). <br/>
     * date: 2015年1月23日 <br/>
     *
     * @author wenlin
     */
    private void initUiPage() {
        if ((evInvalidRs.getText().toString() != null && !StringUtils
                .isEmpty(evInvalidRs.getText().toString()))
                || !StringUtils.isEmpty(mWorkOrder.getZfpersontime())) {
            // 作废原因不可用
            evInvalidRs.setEnabled(false);
            // 更多选项按钮隐藏
            ibRsMore.setVisibility(View.INVISIBLE);
            // 设置作废时间
            tvInvalidTime.setText(mWorkOrder.getZfpersontime());
        }
    }

    /**
     * initExamineView:(初始化审批环节). <br/>
     * date: 2015年1月23日 <br/>
     *
     * @author wenlin
     */
    @SuppressWarnings("unchecked")
    private void initExamineView() {
        // 审批环节点
        lsvEaxmine = (ExamineListView<WorkApprovalPermission>) findViewById(R.id.hd_hse_osc_app_phone_obsolete_examinelistview);
        // 查询对象
        IQueryWorkInfo mQuery = new QueryWorkInfo();
        // 配置信息
        PDAWorkOrderInfoConfig workConfig = new PDAWorkOrderInfoConfig();
        // 设置页面类型
        workConfig.setDycode(PAGE_TYPE);
        // 查询当前作业票作废审批环节
        List<WorkApprovalPermission> lstWorkAP = null;
        try {
            lstWorkAP = mQuery.queryApprovalPermission(mWorkOrder, workConfig,
                    null, null);
        } catch (HDException e) {
            logger.error(e.getMessage(), e);
        }
        // 无审批环节点则出现保存按钮
        // if(lstWorkAP == null || lstWorkAP.size() < 1){
        // bt_save.setVisibility(View.VISIBLE);
        // }
        lsvEaxmine.setItemEditTextEms(10);
        lsvEaxmine.setData(lstWorkAP);
        lsvEaxmine.setIEventListener(eventLst);
        lsvEaxmine.setWorkOrder(mWorkOrder);
    }

    /**
     * initChooseDatas:(查询作废原因域值). <br/>
     * date: 2015年1月23日 <br/>
     *
     * @return
     * @author wenlin
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private List<Domain> initChooseDatas() {
        List<Domain> chooseList = null;
        try {
            chooseList = (List) action.queryEntities(Domain.class,
                    new String[]{"value", "description"},
                    "domainid = 'DGSH_WSSYY'");
        } catch (HDException e) {
            logger.error(e.getMessage(), e);
            ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                    "查询作废原因域值出错！请联系管理员！");
        }
        String chooseData = evInvalidRs.getText().toString();
        if (chooseList != null) {
            for (int i = 0; i < chooseList.size(); i++) {
                if (chooseData.contains(chooseList.get(i).getDescription())) {
                    chooseList.get(i).setIsselected(1);
                }
            }
        }
        return chooseList;
    }

    public IEventListener eventLst = new IEventListener() {

        @Override
        public void eventProcess(int eventType, Object... arg1)
                throws HDException {
            // 选择域值
            if (IEventType.POPUPWINDOW_CHOICE_MULTIPLEORSINGLE == eventType) {
                Domain domain = (Domain) arg1[0];
                evInvalidRs.setText(domain.getDescription());
                evInvalidRs.setHint(domain.getValue());
                mWorkOrder.setZfcause(domain.getDescription());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(evInvalidRs.getWindowToken(), 0);
            } else if (IEventType.ACTIONBAR_ITEM_UP_CLICK == eventType) {
                // 作业票上传
                upWorkOrder();
            } else if (IEventType.DOWN_WORK_LIST_LOAD == eventType) {
                if (WorkOrderObsoleteActivity.this != null) {
                    setResult(OBSOLETESUCESS);
                    WorkOrderObsoleteActivity.this.finish();
                }

            } else if (IEventType.DOWN_WORK_LIST_SHOW == eventType) {
                // finish();
            }
        }
    };

    /**
     * TODO 获取返回值
     *
     * @see android.app.Activity#onActivityResult(int, int,
     * android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if (requestCode == IEventType.READCARD_TYPE && intent != null) {
            WorkApprovalPermission workApprovalPermission = (WorkApprovalPermission) intent
                    .getSerializableExtra("workApprovalPermission");
            isSQ = false;
            if (resultCode == IEventType.TPQM_TYPE) {
                isSQ = true;
                img = (Image) intent
                        .getSerializableExtra(PaintSignatureActivity.IMAGE);
            }
            if (workApprovalPermission != null
                    && workApprovalPermission.getPersonid() != null) {
                try {
                    curApproveNode = workApprovalPermission;
                    auditOnClick(0, workApprovalPermission);
                } catch (HDException e) {
                    ToastUtils.imgToast(WorkOrderObsoleteActivity.this,
                            R.drawable.hd_hse_common_msg_wrong, e.getMessage());
                }
            }
        }
    }

    /**
     * auditOnClick:(作废审核或保存). <br/>
     * date: 2015年1月23日 <br/>
     *
     * @param eventType
     * @param workApprovalPermission 审批环节
     * @throws HDException
     * @author wenlin
     */
    private void auditOnClick(int eventType,
                              WorkApprovalPermission workApprovalPermission) throws HDException {
        // 是否填写作废原因
        validate();
        // 异步保存数据
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put(WorkOrder.class.getName(), mWorkOrder);
        // 配置信息
        PDAWorkOrderInfoConfig config = new PDAWorkOrderInfoConfig();
        config.setPscode(IConfigEncoding.SP);
        mapParam.put(PDAWorkOrderInfoConfig.class.getName(), config);

        if (workApprovalPermission != null) {
            // 审批环节点
            mapParam.put(WorkApprovalPermission.class.getName(),
                    workApprovalPermission);
        }
        // 异步保存数据
        prgDialog = new ProgressDialog(WorkOrderObsoleteActivity.this,
                "作废信息保存中...");
        prgDialog.show();
        askTask.execute(IActionType.ACTION_TYPE_NULLIFY, mapParam);
    }

    /**
     * validate:(是否填写作废原因). <br/>
     * date: 2015年1月23日 <br/>
     *
     * @throws AppException
     * @author wenlin
     */
    private void validate() throws AppException {
        String invalidRs = evInvalidRs.getText().toString();
        if (StringUtils.isEmpty(invalidRs)) {
            throw new AppException("请输入作废原因！");
        }
    }

    private AbstractAsyncCallBack askCallBack = new AbstractAsyncCallBack() {

        @Override
        public void start(Bundle msgData) {
            // TODO Auto-generated method stub

        }

        @Override
        public void processing(Bundle msgData) {
            // TODO Auto-generated method stub
            if (msgData.containsKey(IActionType.ACTION_TYPE_NULLIFY)) {
                prgDialog.showMsg(msgData
                        .getString(IActionType.ACTION_TYPE_NULLIFY));
            }
        }

        @Override
        public void error(Bundle msgData) {
            // TODO Auto-generated method stub
            if (msgData.containsKey(IActionType.ACTION_TYPE_NULLIFY)) {
                prgDialog.dismiss();
                ToastUtils.imgToast(WorkOrderObsoleteActivity.this,
                        R.drawable.hd_hse_common_msg_wrong,
                        msgData.getString(IActionType.ACTION_TYPE_NULLIFY));
            }
        }

        @Override
        public void end(Bundle msgData) {
            if (isSQ) {
                PaintSignatureActivity.saveImageToDB(false, curApproveNode,
                        mWorkOrder, null, img);
            }
            prgDialog.dismiss();
            // TODO Auto-generated method stub
            ToastUtils.imgToast(WorkOrderObsoleteActivity.this,
                    R.drawable.hd_hse_common_msg_right,
                    msgData.getString(IActionType.ACTION_TYPE_NULLIFY));
            // 刷新界面
            initUiPage();
            // 刷新审批环节点
            lsvEaxmine.setCurrentEntity(curApproveNode);
        }
    };

    /**
     * upzyp:TODO(上传作业票).
     */
    private UpZYPProgressDialog upzyp = null;

    /**
     * 上传作业票
     */
    public void upWorkOrder() {
        if (upzyp == null) {
            upzyp = new UpZYPProgressDialog(this);
            upzyp.setGetDataSourceListener(eventLst);
        }
        QueryWorkOrderInfo mQueryWorkOrderInfo = new QueryWorkOrderInfo();
        String[] ud_zyxk_zysqids = null;
        // 判断是否合并审批，并赋值ud_zyxk_zysqids

        ud_zyxk_zysqids = new String[1];
        ud_zyxk_zysqids[0] = mWorkOrder.getUd_zyxk_zysqid();

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
