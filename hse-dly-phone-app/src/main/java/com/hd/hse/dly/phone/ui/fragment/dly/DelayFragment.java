/**
 * Project Name:hse-dly-phone-app
 * File Name:DelayFragment.java
 * Package Name:com.hd.hse.dly.phone.ui.fragment.dly
 * Date:2015年2月9日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 * <p>
 * <p>
 * Project Name:hse-dly-phone-app
 * File Name:DelayFragment.java
 * Package Name:com.hd.hse.dly.phone.ui.fragment.dly
 * Date:2015年2月9日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 */
/**
 * Project Name:hse-dly-phone-app
 * File Name:DelayFragment.java
 * Package Name:com.hd.hse.dly.phone.ui.fragment.dly
 * Date:2015年2月9日
 * Copyright (c) 2015, zhaofeng@ushayden.com All Rights Reserved.
 *
 */

package com.hd.hse.dly.phone.ui.fragment.dly;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.custom.ShowSignDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.EnsureClickListener;
import com.hd.hse.common.component.phone.util.DateTimePickDialogUtil;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.custom.ExamineListView;
import com.hd.hse.common.module.phone.ui.activity.PaintSignatureActivity;
import com.hd.hse.common.module.phone.ui.activity.ReadCardExamineActivity;
import com.hd.hse.common.module.phone.ui.utils.ImageUtils;
import com.hd.hse.common.module.ui.model.fragment.NaviFrameFragment;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.ITableName;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.dao.result.MapResult;
import com.hd.hse.dly.phone.R;
import com.hd.hse.dly.phone.ui.activity.workorder.WorkOrderActivity;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkApprovalPersonRecord;
import com.hd.hse.entity.workorder.WorkDelay;
import com.hd.hse.entity.workorder.WorkMeasureReview;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.service.workorder.checkrules.CheckControllerActionListener;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:DelayFragment (延期对应的Frament).<br/>
 * Date:     2015年2月9日  <br/>
 * @author zhaofeng
 * @version
 * @see
 */

/**
 * ClassName: DelayFragment (延期对应的Frament)<br/>
 * date: 2015年2月9日 <br/>
 *
 * @author zhaofeng
 * @version
 */
public class DelayFragment extends NaviFrameFragment implements
        EnsureClickListener {

    /**
     * WORKAPPROVALPERMISSION:TODO(由刷卡界面回调，存放数据的key值).
     */
    public static final String WORKAPPROVALPERMISSION = "workApprovalPermission";
    /**
     * timeText:TODO(时间选择器弹出框上，选择时间的显示器).
     */
    private EditText timeText;
    /**
     * 审批环节ListView
     */
    private ExamineListView appListView;
    /**
     * 延期总次数，可延期次数
     */
    private TextView delayCount, mayDelayCount;
    /**
     * 延期开始时间，延期结束时间
     */
    private EditText startTime, endTime;

    /**
     * dialog:TODO(时间选择器-对应的弹出框).
     */
    private Dialog dialog;

    /**
     * workOrder:TODO(作业票信息).
     */
    private WorkOrder workOrder;
    /**
     * curWorkOrderInfoConfig:TODO(延期界面，配置信息).
     */
    private PDAWorkOrderInfoConfig curWorkOrderInfoConfig;
    /**
     * measureReview:TODO(复查信息).
     */
    private WorkMeasureReview measureReview;
    /**
     * workDelay:TODO(延期信息).
     */
    private WorkDelay workDelay;
    /**
     * iQueryWorkInfo:TODO(后台服务-工具查询类).
     */
    private IQueryWorkInfo iQueryWorkInfo;
    /**
     * personRecord:TODO(审批记录-对象).
     */
    private WorkApprovalPersonRecord personRecord;
    /**
     * applist:TODO(审批环节点-集合).
     */
    private List<WorkApprovalPermission> applist;
    /**
     * curApproveNode:TODO(当前点中的环节点).
     */
    private WorkApprovalPermission curApproveNode;
    /**
     * prgDialog:TODO(进度条).
     */
    private ProgressDialog prgDialog;
    /**
     * asyTask:TODO(异步任务).
     */
    private BusinessAsyncTask asyTask;
    /**
     * action:TODO(后台业务处理).
     */
    private BusinessAction action;

    /**
     * actLsr:TODO(后台业务处理).
     */
    private AbstractCheckListener actLsr;

    private Image img;
    private boolean isSQ; // 是否手写签名
    private String tempStartTime;

    /**
     * TODO 初始化-全局对象实例化一次
     *
     * @see com.hd.hse.common.module.ui.model.fragment.NaviFrameFragment#init()
     */
    @Override
    protected void init() {
        // TODO Auto-generated method stub
        actLsr = new CheckControllerActionListener();
        action = new BusinessAction(actLsr);
        asyTask = new BusinessAsyncTask(action, asyCallBack);
        if (iQueryWorkInfo == null)
            iQueryWorkInfo = new QueryWorkInfo();
    }

    /**
     * TODO 刷新数据-对于多次加载重复操作的
     *
     * @see com.hd.hse.common.module.ui.model.fragment.NaviFrameFragment#refreshData()
     */
    @Override
    public void refreshData() {
        // TODO Auto-generated method stub
        if (null != workDelay) {
            personRecord.setTableid(workDelay.getUd_zyxk_zyyqid());
            BaseDao dao = new BaseDao();
            String sql = "select pausetime from ud_zyxk_zysq where ud_zyxk_zysqid = '"
                    + workOrder.getUd_zyxk_zysqid() + "'";
            String pausetime = null;
            try {
                WorkOrder mWorkOrder = (WorkOrder) dao.executeQuery(sql,
                        new EntityResult(WorkOrder.class));
                pausetime = mWorkOrder.getPausetime();
            } catch (DaoException e1) {
                e1.printStackTrace();
            }

            if (workDelay.getYqstarttime() == null) {
                try {
                    if (pausetime != null && !"".equals(pausetime)
                            && compareCurrentDate(workOrder.getSjendtime())) {

                        String nowTime = ServerDateManager.getCurrentTime();
                        startTime.setText(nowTime);
                    } else {
                        startTime.setText(workOrder.getSjendtime());
                    }
                } catch (ParseException e) {
                    startTime.setText(workOrder.getSjendtime());
                    e.printStackTrace();
                }

            } else {
                startTime.setText(workDelay.getYqstarttime());
            }
            tempStartTime = startTime.getText().toString().trim();
            if (workDelay.getYqendtime() == null) {
                try {
                    if (pausetime != null && !"".equals(pausetime)
                            && compareCurrentDate(workOrder.getSjendtime())) {

                        endTime.setText(ServerDateManager.getCurrentTime());
                    } else {
                        endTime.setText(workOrder.getSjendtime());
                    }
                } catch (ParseException e) {
                    endTime.setText(workOrder.getSjendtime());
                    e.printStackTrace();
                }
            } else {
                endTime.setText(workDelay.getYqendtime());
            }
            delayCount.setText(workOrder.getYqcount() == null ? "0" : workOrder
                    .getYqcount().toString().trim());
            mayDelayCount.setText(String.valueOf(workOrder.getYqcount()
                    - workOrder.getYyqcount()));
            // 如果为空时，初始化加载数据；反之使用缓冲中的数据
            if (appListView.getDate() == null)
//                if (workOrder.getZypclass().equals("zylx89")) {
//                    applist = null;
//                }
            appListView.setData(applist);
            appListView.setWorkOrder(workOrder);
        }
    }

    /**
     * TODO 初始化界面-执行一次
     *
     * @see com.hd.hse.common.module.ui.model.fragment.NaviFrameFragment#initView(android.view.LayoutInflater,
     *      android.view.ViewGroup)
     */
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(
                R.layout.hd_hse_common_module_phone_activity_yqmain, null);
        startTime = (EditText) view
                .findViewById(R.id.hd_hse_dly_app_startTime_ET);
        startTime.setFocusable(false);
        endTime = (EditText) view.findViewById(R.id.hd_hse_dly_app_endTime_ET);
        endTime.setFocusable(false);
        delayCount = (TextView) view
                .findViewById(R.id.hd_hse_dly_app_delaycount_tv);
        mayDelayCount = (TextView) view
                .findViewById(R.id.hd_hse_dly_app_maydelaycount_tv);
        appListView = (ExamineListView) view
                .findViewById(R.id.hd_hse_phone_dly_examine_elv);
        appListView.setFragment(this);
        appListView.setHandler(mHandler);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ServerDateManager.getCurrentDate());
        dialog = new DatePickerDialog(getActivity(), dateListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        calendar.get(Calendar.HOUR_OF_DAY);
        calendar.get(Calendar.MONTH);

        startTime.setOnClickListener(timeOnClick);
        endTime.setOnClickListener(timeOnClick);

        return view;
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ShowSignDialog.HANDLER_WHAT:
                    appListView.setCurrentEntity((WorkApprovalPermission) msg.obj);
                    break;

                default:
                    break;
            }
        }

    };

    /**
     * 日期选择器-变更触发事件-显示器对应的时间
     */
    private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month,
                              int dayOfMonth) {
            // Calendar月份是从0开始,所以month要加1
            timeText.setText("你选择了" + year + "年" + (month + 1) + "月"
                    + dayOfMonth + "日");
        }
    };

    /**
     * TODO 跳转到当前Fragment时，由activity赋值
     *
     * @see com.hd.hse.common.module.ui.model.fragment.NaviFrameFragment#initData(java.util.List)
     */
    @Override
    public void initData(List<Object> data) {
        // TODO Auto-generated method stub
        workOrder = (WorkOrder) data.get(0);
        curWorkOrderInfoConfig = (PDAWorkOrderInfoConfig) data.get(1);
        // measureReview = (WorkMeasureReview) data.get(2);
        if (iQueryWorkInfo == null)
            iQueryWorkInfo = new QueryWorkInfo();
        // 获取延期信息
        try {
            measureReview = iQueryWorkInfo.queryReviewInfo(workOrder,
                    curWorkOrderInfoConfig, null);
            workDelay = (WorkDelay) iQueryWorkInfo.queryDelayInfo(workOrder,
                    measureReview == null ? "" : measureReview.getZycsfcnum(),
                    null);
        } catch (HDException e) {
            // TODO Auto-generated catch block
            ToastUtils.imgToast(getActivity(),
                    R.drawable.hd_hse_common_msg_wrong, "获取延期信息失败！");
            return;
        }

        // 获取审批环节点信息-如果不为空，则使用缓冲中的数据
        if (applist != null)
            return;
        try {
            personRecord = new WorkApprovalPersonRecord();
            personRecord.setTablename(ITableName.UD_ZYXK_ZYYQ);
            personRecord.setTableid(workDelay.getUd_zyxk_zyyqid());
            applist = iQueryWorkInfo.queryApprovalPermission(workOrder,
                    curWorkOrderInfoConfig, personRecord, null);
        } catch (HDException e) {
            // TODO Auto-generated catch block
            ToastUtils.imgToast(getActivity(),
                    R.drawable.hd_hse_common_msg_wrong, "获取延期审批环节点失败！");
            return;
        }
    }

    /**
     * 弹出时间选择器 timeOnClick:TODO(时间控件的点击事件).
     */
    OnClickListener timeOnClick = new OnClickListener() {
        @Override
        public void onClick(View view) {
            timeText = (EditText) view;
            String str = null;
            if (timeText.getText() != null) {
                str = timeText.getText().toString().trim();
            }
            DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                    getActivity(), str);
            //加入时间选择确定的监听，监听开始时间是否符合
            dateTimePicKDialog.setOnEnsureClickListener(DelayFragment.this);
            dateTimePicKDialog.dateTimePicKDialog(timeText);
        }
    };

    /**
     * AppSaveClick:(表示审核保存方法). <br/>
     * date: 2014年10月25日 <br/>
     *
     * @author lxf
     * @param eventType
     * @param objects
     * @throws HDException
     */
    private void appSaveClick(int eventType, Object... objects)
            throws HDException {
        if (null == measureReview) {
            ToastUtils.imgToast(getActivity(),
                    R.drawable.hd_hse_common_msg_right, "读取延期措施信息出错，请联系管理员!");
            return;
        }

        Map<String, Object> mapParam = new HashMap<String, Object>();
        // 1.作业票
        mapParam.put(WorkOrder.class.getName(), workOrder);
        // 2.pda配置信息
        mapParam.put(PDAWorkOrderInfoConfig.class.getName(),
                curWorkOrderInfoConfig);
        // 3.审批环节点
        if (objects != null && objects.length > 0 && objects[0] != null
                && objects[0] instanceof WorkApprovalPermission) {
            // 审批环节点
            curApproveNode = (WorkApprovalPermission) objects[0];
            mapParam.put(WorkApprovalPermission.class.getName(), curApproveNode);
        }
        // 4.措施复查信息
        mapParam.put(WorkMeasureReview.class.getName(), measureReview);
        // 5.延期信息
        // 设置延期时间
        if (workDelay == null) {
            workDelay = new WorkDelay();
        }
        // 赋值时间
        workDelay.setYqstarttime(startTime.getText().toString());
        workDelay.setYqendtime(endTime.getText().toString());
        workDelay.setZycsfcnum(measureReview.getZycsfcnum());
        mapParam.put(WorkDelay.class.getName(), workDelay);
        // 6.审批记录保存的信息
        personRecord.setTableid(workDelay.getUd_zyxk_zyyqid());
        personRecord.setDycode(curWorkOrderInfoConfig.getDycode());
        mapParam.put(WorkApprovalPersonRecord.class.getName(), personRecord);

        // 异步保存数据
        prgDialog = new ProgressDialog(getActivity(), "保存信息。。。");
        // prgDialog.show();
        asyTask.execute(IActionType.ACTION_TYPE_DELAYSIGN, mapParam);
    }

    /**
     * asyCallBack:TODO(保存异步回调函数).
     */
    private AbstractAsyncCallBack asyCallBack = new AbstractAsyncCallBack() {

        @Override
        public void start(Bundle msgData) {
            // TODO Auto-generated method stub
            // prgDialog = new ProgressDialog(getActivity(), "保存信息。。。");

        }

        @Override
        public void processing(Bundle msgData) {
            // TODO Auto-generated method stub
            prgDialog.show();
        }

        @Override
        public void error(Bundle msgData) {
            // TODO Auto-generated method stub
            // 退回
            if (msgData.containsKey(IActionType.ACTION_TYPE_DELAYSIGN)) {
                prgDialog.dismiss();
                ToastUtils.imgToast(getActivity(),
                        R.drawable.hd_hse_common_msg_wrong,
                        msgData.getString(IActionType.ACTION_TYPE_DELAYSIGN));
            }

        }

        @Override
        public void end(Bundle msgData) {
            // TODO Auto-generated method stub
            if (isSQ) {
                PaintSignatureActivity.saveImageToDB(false, curApproveNode,
                        workOrder, null, img);
            } else if (curWorkOrderInfoConfig != null) {
                if (img != null) {
                    ImageUtils mImageUtils = new ImageUtils();
                    mImageUtils.saveImage(curWorkOrderInfoConfig,
                            curApproveNode, workOrder, img);
                }
            }
            prgDialog.dismiss();
            refresh();
            if (msgData.containsKey(IActionType.ACTION_TYPE_DELAYSIGN)) {
                ToastUtils.imgToast(getActivity(),
                        R.drawable.hd_hse_common_msg_right,
                        msgData.getString(IActionType.ACTION_TYPE_DELAYSIGN));
                if (curApproveNode != null && curApproveNode.getIsend() == 1) {
                    ((PDAWorkOrderInfoConfig) getNaviCurrentEntity())
                            .setIsComplete(1);
                    ((WorkOrderActivity) getActivity()).setNaviBarIsCompLete();
                }
                if (IWorkOrderStatus.REVIEW_STATUS_FINISH.equals(measureReview
                        .getStatus())) {
                    // 延期结束，开始和结束时间不可编辑
                    startTime.setEnabled(false);
                    endTime.setEnabled(false);
                }
            }
        }
    };

    /**
     * refresh:(刷新界面). <br/>
     * date: 2014年10月29日 <br/>
     *
     * @author lxf
     */
    @SuppressWarnings("unchecked")
    private void refresh() {
        // if (null != appListView && null != curApproveNode) {
        if (null != appListView) {
            // 刷新审批环节点状态
            appListView.setCurrentEntity(curApproveNode);
        }
        if (null != workOrder) {
            int kycount = workOrder.getYqcount() - workOrder.getYyqcount();
            mayDelayCount.setText(kycount + "");
        }
    }

    /**
     * TODO 关闭刷卡界面-触发该事件
     *
     * @see android.app.Fragment#onActivityResult(int, int,
     *      android.content.Intent)
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        // super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IEventType.READCARD_TYPE) {
            // 由刷卡界面关闭触发的事件
            if (data == null)
                return;
            isSQ = false;
            if (resultCode == IEventType.TPQM_TYPE) {
                isSQ = true;
                img = (Image) data
                        .getSerializableExtra(PaintSignatureActivity.IMAGE);
            }
            if (resultCode == IEventType.READCARD_TYPE) {
                img = (Image) data
                        .getSerializableExtra(ReadCardExamineActivity.IMAGE);
            }
            try {
                appSaveClick(requestCode,
                        new Object[]{data
                                .getSerializableExtra(WORKAPPROVALPERMISSION)});
            } catch (HDException e) {
                // TODO Auto-generated catch block
                ToastUtils.imgToast(getActivity(),
                        R.drawable.hd_hse_common_msg_wrong, "延期信息保存失败,请检查！");
                return;
            }
        }
    }

    /**
     * 时间比较,date1>date2 return true
     *
     * @throws ParseException
     */
    private boolean compareCurrentDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = ServerDateManager.getCurrentDate();
        Date d2 = sdf.parse(date);
        return now.after(d2);
    }

    @Override
    public void OnEnsureClick(View view) {
        // 判断view是不是开始时间startTime
        if (view == startTime) {
            // 从关系表查询delayinterval，若启用。判断时间间隔，
            BaseDao dao = new BaseDao();
            String sql = "select Input_value, isqy from sys_relation_info where sys_type ='DELAYINTERVAL'";
            try {
                HashMap<String, Object> result = (HashMap<String, Object>) dao
                        .executeQuery(sql, new MapResult());
                if (result.containsKey("isqy")) {
                    int isqy = (int) (result.get("isqy"));
                    if (isqy == 1) {
                        String sStartTime = ((EditText) view).getText()
                                .toString().trim();
                        Calendar c = Calendar.getInstance();
                        c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .parse(sStartTime));
                        long lStartTime = c.getTimeInMillis();
                        if (result.containsKey("input_value")) {
                            try {
                                if (null == result.get("input_value")) {
                                    return;
                                }
                                int delayinterval = Integer.parseInt((String) result.get("input_value"));
                                if (lStartTime - ServerDateManager.getCurrentTimeMillis() > delayinterval * 60 * 1000) {
                                    ToastUtils.toast(getActivity(),
                                            "延期开始时间与当前时间间隔大于" + delayinterval
                                                    + "分钟");
                                    ((EditText) view).setText(tempStartTime);
                                    return;
                                }
                            } catch (ClassCastException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }
            } catch (DaoException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tempStartTime = ((EditText) view).getText().toString().trim();

        }

    }

}
