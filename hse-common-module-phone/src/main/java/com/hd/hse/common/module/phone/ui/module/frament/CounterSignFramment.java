package com.hd.hse.common.module.phone.ui.module.frament;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.adapter.SignListViewAdapter;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.ui.activity.NaviFrameActivity;
import com.hd.hse.common.module.phone.ui.activity.PaintSignatureActivity;
import com.hd.hse.common.module.phone.ui.activity.ReadCardExamineActivity;
import com.hd.hse.common.module.phone.ui.utils.ImageUtils;
import com.hd.hse.common.module.ui.model.fragment.NaviFrameFragment;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.dao.factory.ConnectionSourceManager;
import com.hd.hse.dao.source.IConnectionSource;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.CheckControllerActionListener;
import com.hd.hse.utils.UtilTools;

/**
 * TODO 会签审批Fragment ClassName: CounterSignFramment ()<br/>
 * date: 2015年1月26日 <br/>
 *
 * @author wenlin
 */
public class CounterSignFramment extends NaviFrameFragment {

    private static Logger logger = LogUtils
            .getLogger(CounterSignFramment.class);
    /**
     * CounterSignView:TODO(布局).
     */
    private View counterSignView;

    /**
     * sighLst:TODO(会签List).
     */
    private ListView sighLst;

    /**
     * signLsvAdapter:TODO(适配器).
     */
    private SignListViewAdapter signLsvAdapter;

    /**
     * workAPLst:TODO(审批环节点).
     */
    public List<SuperEntity> workAPLst;

    /**
     * mWorkOrder:TODO(作业票信息).
     */
    private WorkOrder mWorkOrder;

    //装载作业票信息的集合
    private List<WorkOrder> loadmWorkOrder = new ArrayList<>();

    protected List<WorkOrder> workOrders;

    /**
     * currentApp:TODO(当前审批环节).
     */
    private SuperEntity currentAppPsm;

    /**
     * prgDialog:TODO(异步任务提示框).
     */
    private ProgressDialog prgDialog;

    /**
     * busAction:TODO(业务处理).
     */
    private BusinessAction busAction;

    /**
     * busAsyTask:TODO(异步任务).
     */
    private BusinessAsyncTask busAsyTask;
    /**
     * 是否可以点击刷卡按钮
     */
    private boolean isClick = true;

    /**
     * mRequestCode:TODO(请求码).
     */
    private int mRequestCode = 69;

    private boolean isTPQM;
    private Image image;

    protected SuperEntity currentWPP;
    private OnSignListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.listener = (OnSignListener) activity;
        } catch (ClassCastException e) {
            logger.error(activity.getClass().getName()
                    + "必须实现OnSpotFinalPersonSignListener 接口", null);
        }

    }

    public CounterSignFramment() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initData(List<Object> data) {
        if (null != data && data.size() > 1) {
            // 获取数据
            this.workAPLst = (List<SuperEntity>) data.get(0);
            this.mWorkOrder = (WorkOrder) data.get(1);
        } else {
            this.mWorkOrder = (WorkOrder) data.get(0);
            workAPLst = new ArrayList<SuperEntity>();
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        counterSignView = inflater.inflate(
                R.layout.hd_hse_common_module_phone_countersignfragment,
                container, false);
        sighLst = (ListView) counterSignView
                .findViewById(R.id.hd_hse_common_module_phone_countersign_lv);

        return counterSignView;
    }

    @Override
    protected void init() {
        // 会签条目适配器

        loadmWorkOrder.add(mWorkOrder);
        if (getActionType().equals(IActionType.ACTION_TYPE_SIGN)) {
            signLsvAdapter = new SignListViewAdapter(workAPLst, loadmWorkOrder, getActivity(), null);
        } else if (getActionType().equals(IActionType.ACTION_TYPE_MERGE_SIGN)) {
            signLsvAdapter = new SignListViewAdapter(workAPLst, workOrders, getActivity(), null);
        }
        sighLst.setAdapter(signLsvAdapter);
        // 设置监听
        signLsvAdapter.setOnClickListener(eventLst);

        busAction = new BusinessAction(new CheckControllerActionListener());
        busAsyTask = new BusinessAsyncTask(busAction, appCallBack);
    }

    private IEventListener eventLst = new IEventListener() {

        @Override
        public void eventProcess(int eventType, final Object... arg1)
                throws HDException {
            if (!isClick) {
                return;
            }
            isClick = false;
            // QueryRelativeConfig config = new QueryRelativeConfig();
            // RelationTableName tableName = new RelationTableName();
            // tableName.setSys_type(IRelativeEncoding.SXQM);
            if (IEventType.SIGN_CLICK == eventType) {
                // if (!config.isHadRelative(tableName)) {
                // PaintSignatureDialog dialog = new PaintSignatureDialog(
                // getActivity(), new OnCustomClickListener() {
                //
                // @Override
                // public void onCustomClick(final Bitmap bitmap) {
                // saveSignImage(bitmap, (SuperEntity) arg1[0]);
                // }
                // });
                // dialog.setOnDismissListener(new OnDismissListener() {
                //
                // @Override
                // public void onDismiss(DialogInterface arg0) {
                // isClick = true;
                // }
                // });
                // dialog.show();
                // } else {
                Intent intent = getNextIntent(arg1[0]);
                startActivityForResult(intent, mRequestCode);
                // }
            }
        }
    };

    /**
     * getNextIntent:(获取跳转到下一个界面的Intent). <br/>
     * date: 2015年9月8日 <br/>
     *
     * @param obj
     * @return
     * @author lxf
     */
    public Intent getNextIntent(Object obj) {
        // 当前点击所在环节点
        SuperEntity currentWPP = (SuperEntity) obj;

        Intent intent = new Intent();

        if (currentWPP.getAttribute("isdzqm") != null
                && currentWPP.getAttribute("isdzqm").equals("1")) {
            // 图片签名
            intent.setClass(getActivity(), PaintSignatureActivity.class);
        } else {
            // 开启刷卡
            intent.setClass(getActivity(), ReadCardExamineActivity.class);
            // intent.setClass(getActivity(), PaintSignatureActivity.class);
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(ReadCardExamineActivity.WORKAPPROVALPERMISSION,
                currentWPP);
        bundle.putSerializable(ReadCardExamineActivity.WORKORDER, mWorkOrder);
        intent.putExtras(bundle);
        return intent;
    }

    /**
     * TODO 获取返回值
     *
     * @see android.app.Fragment#onActivityResult(int, int,
     * android.content.Intent)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        // isClick = true;
        if (requestCode == mRequestCode
                && (resultCode == IEventType.READCARD_TYPE || resultCode == IEventType.TPQM_TYPE)) {
            // 获取刷卡后的审批环节
            currentAppPsm = (SuperEntity) data.getExtras().get(
                    ReadCardExamineActivity.WORKAPPROVALPERMISSION);
            isTPQM = false;
            if (resultCode == IEventType.TPQM_TYPE) {
                isTPQM = true;
                image = (Image) data.getExtras().get(
                        PaintSignatureActivity.IMAGE);
                Object obj = data.getExtras().get(
                        PaintSignatureActivity.WORKORDER);
                if (obj instanceof List) {
                    workOrders = (List<WorkOrder>) obj;
                } else {
                    mWorkOrder = (WorkOrder) obj;
                }
            }
            // 获取刷卡审批，拍照，照片信息
            if (resultCode == IEventType.READCARD_TYPE) {
                image = (Image) data
                        .getSerializableExtra(ReadCardExamineActivity.IMAGE);
                Object obj = data.getExtras().get(
                        PaintSignatureActivity.WORKORDER);
                if (obj instanceof List) {
                    workOrders = (List<WorkOrder>) obj;
                } else {
                    mWorkOrder = (WorkOrder) obj;
                }
            }
            try {
                // 校验当前审批环节刷卡人权限
                // lxf 2015-08-28 在读卡界面里做了校验
                // SwipingCardUtils.swipingCard(mWorkOrder, currentAppPsm);
                // 启用异步保存刷卡人信息
                saveCurrentAPInfo();
            } catch (HDException e) {
                logger.error(e.getMessage(), e);
            } finally {
                isClick = true;
            }

        }
        isClick = true;
        if (resultCode == IEventType.REMOTEAPPR) {
            // 上传待远程审批信息成功，改变作业票状态为“待远程审批”，
            try {
                saveRemoteApprWorkOrderState();
            } catch (DaoException e) {
                logger.error(e);
                ToastUtils.imgToast(getActivity(),
                        R.drawable.hd_hse_common_msg_wrong, "数据库连接异常");
                return;
            } catch (SQLException e) {
                logger.error(e);
                ToastUtils.imgToast(getActivity(),
                        R.drawable.hd_hse_common_msg_wrong, "保存作业票状态失败");
                return;
            } catch (HDException e) {
                logger.error(e);
                ToastUtils.imgToast(getActivity(),
                        R.drawable.hd_common_message_error, e.getMessage());
            }
            // 设置当前导航标记
            ((PDAWorkOrderInfoConfig) getNaviCurrentEntity()).setIsComplete(1);
            // 设置导航已完成已完成标记
            ((NaviFrameActivity) getActivity())
                    .setNaviFinish(getNaviCurrentEntity());
            //isClick = false;
        }

    }

    /**
     * 保存远程审批票状态
     *
     * @throws SQLException
     * @throws HDException
     */
    private void saveRemoteApprWorkOrderState() throws SQLException,
            HDException {
        WorkOrder[] workOrderList = null;
        if (mWorkOrder != null) {
            workOrderList = new WorkOrder[1];
            workOrderList[0] = mWorkOrder;
        } else if (workOrders != null) {
            workOrderList = new WorkOrder[workOrders.size()];
            for (int i = 0; i < workOrderList.length; i++) {
                workOrderList[i] = workOrders.get(i);
            }
        } else {
            throw new HDException("获取作业票失败");
        }
        for (WorkOrder workOrder : workOrderList) {
            workOrder.setStatus(IWorkOrderStatus.REMOTE);
            workOrder.setSpstatus(IWorkOrderStatus.SPSTATUS_SPAPPR);
            workOrder.setSjstarttime(UtilTools.getSysCurrentTime());
            workOrder.setNeedupload(1);
            workOrder.setIsupload(0);
        }
        IConnectionSource conSrc = null;
        IConnection con = null;
        try {
            conSrc = ConnectionSourceManager.getInstance()
                    .getJdbcPoolConSource();
            con = conSrc.getConnection();
            BaseDao dao = new BaseDao();
            dao.updateEntities(con, workOrderList, new String[]{"status",
                    "spstatus", "sjstarttime", "needupload", "isupload"});
            con.commit();

        } catch (SQLException e) {
            throw e;

        } catch (DaoException e) {
            throw e;
        } finally {
            if (con != null) {
                try {
                    conSrc.releaseConnection(con);
                } catch (SQLException e) {

                }
            }
        }

    }

    /**
     * TODO 保存当前审批环节信息 saveCurrentAPInfo:(). <br/>
     * date: 2015年1月30日 <br/>
     *
     * @throws HDException
     * @author wenlin
     */
    public void saveCurrentAPInfo() throws HDException {

        prgDialog = new ProgressDialog(getActivity(), "保存信息...");
        prgDialog.show();

        Map<String, Object> mapParam = new HashMap<String, Object>();
        Map<String, Object> mapParamTemp = getMapParamInfo(mWorkOrder);
        if (mapParamTemp != null && mapParamTemp.size() > 0) {
            mapParam.putAll(mapParamTemp);
        }
        // pda配置信息
        mapParam.put(PDAWorkOrderInfoConfig.class.getName(),
                getNaviCurrentEntity());
        // 审批环节信息
        mapParam.put(WorkApprovalPermission.class.getName(), currentAppPsm);
        // 启动异步保存
        busAsyTask.execute(getActionType(), mapParam);
    }

    /**
     * getActionType:(获取动作编码). <br/>
     * date: 2015年9月8日 <br/>
     *
     * @return
     * @author lxf
     */
    public String getActionType() {

        return IActionType.ACTION_TYPE_SIGN;
    }

    /**
     * getMapParamInfo:(获取业务数据参数). <br/>
     * date: 2015年9月8日 <br/>
     *
     * @param mwoWorkOrder
     * @return
     * @author lxf
     */
    public Map<String, Object> getMapParamInfo(WorkOrder mWorkOrder) {
        Map<String, Object> mapParam = new HashMap<String, Object>();
        // 作业票信息
        if (mWorkOrder != null) {
            mapParam.put(WorkOrder.class.getName(), mWorkOrder);
        }
        return mapParam;
    }

    @Override
    public void refreshData() {
        // TODO Auto-generated method stub

    }

    private AbstractAsyncCallBack appCallBack = new AbstractAsyncCallBack() {

        @Override
        public void start(Bundle msgData) {
            // TODO Auto-generated method stub

        }

        @Override
        public void processing(Bundle msgData) {
            if (msgData.containsKey(IActionType.ACTION_TYPE_SIGN)) {
                prgDialog.showMsg(msgData
                        .getString(IActionType.ACTION_TYPE_SIGN));
            }
            if (msgData.containsKey(IActionType.ACTION_TYPE_MERGE_SIGN)) {
                prgDialog.showMsg(msgData
                        .getString(IActionType.ACTION_TYPE_MERGE_SIGN));
            }
        }

        @Override
        public void error(Bundle msgData) {
            if (msgData.containsKey(IActionType.ACTION_TYPE_SIGN)) {
                ToastUtils.imgToast(getActivity(),
                        R.drawable.hd_hse_common_msg_wrong,
                        msgData.getString(IActionType.ACTION_TYPE_SIGN));
                prgDialog.dismiss();
            }
            if (msgData.containsKey(IActionType.ACTION_TYPE_MERGE_SIGN)) {
                ToastUtils.imgToast(getActivity(),
                        R.drawable.hd_hse_common_msg_wrong,
                        msgData.getString(IActionType.ACTION_TYPE_MERGE_SIGN));
                prgDialog.dismiss();
            }
        }

        @Override
        public void end(Bundle msgData) {

            prgDialog.dismiss();

            if (null != sighLst) {
                // 更新适配器数据源数据并界面刷新
                if (currentWPP != null
                        && currentWPP.getAttribute("personid") != null
                        && !currentWPP.getAttribute("personid").equals("")
                        && currentWPP.getAttribute("persondesc") != null
                        && !currentWPP.getAttribute("persondesc").equals("")) {
                    currentAppPsm.setAttribute("personid",
                            currentWPP.getAttribute("personid") + ","
                                    + currentAppPsm.getAttribute("personid"));
                    currentAppPsm.setAttribute("persondesc",
                            currentWPP.getAttribute("persondesc") + ","
                                    + currentAppPsm.getAttribute("persondesc"));
                    currentWPP = null;
                }
                signLsvAdapter.setCurrentWAPermission(currentAppPsm);
            }
            // 刷新导航栏完成标识
            // 多个最终刷卡人时，只对最后一个最终刷卡人进行判断
            if (null != sighLst && currentAppPsm != null
                    && currentAppPsm.getAttribute("isend").equals(1)
                    && workAPLst.get(workAPLst.size() - 1) == currentAppPsm) {
                // 设置当前导航标记
                ((PDAWorkOrderInfoConfig) getNaviCurrentEntity())
                        .setIsComplete(1);
                // 设置导航已完成已完成标记
                ((NaviFrameActivity) getActivity())
                        .setNaviFinish(getNaviCurrentEntity());
                if (listener != null) {
                    listener.OnEndSign();
                }

            }

            if (isTPQM && image != null) {
                PaintSignatureActivity.saveImageToDB(false,
                        (WorkApprovalPermission) currentAppPsm, mWorkOrder,
                        workOrders, image);
            }
            // 保存刷卡时拍的照片
            if (!isTPQM && image != null) {
                PDAWorkOrderInfoConfig mPDAWorkOrderInfoConfig = (PDAWorkOrderInfoConfig) getNaviCurrentEntity();
                if (mPDAWorkOrderInfoConfig != null) {
                    ImageUtils mImageUtils = new ImageUtils();
                    if (mWorkOrder != null) {
                        mImageUtils.saveImage(mPDAWorkOrderInfoConfig,
                                (WorkApprovalPermission) currentAppPsm,
                                mWorkOrder, image);
                    } else if (workOrders != null) {
                        mImageUtils.saveImage(mPDAWorkOrderInfoConfig,
                                (WorkApprovalPermission) currentAppPsm,
                                workOrders);
                    }

                }
            }

            // 判断该换节点是不是现场最终数卡人
            if (((WorkApprovalPermission) currentAppPsm)
                    .getIs_spot_final_person() == 1) {
                //判断是不是还有现场最终刷卡人未审批(现场最终刷卡人不唯一时，都完成审批后方可显示“远程审批”按钮)
                boolean flag = false;
                for (SuperEntity superEntity : workAPLst) {
                    WorkApprovalPermission permission = (WorkApprovalPermission) superEntity;
                    if (permission.getIs_spot_final_person() == 1
                            && (permission.getPersondesc() == null || "".equals(permission.getPersondesc()))) {
                        flag = true;
                        break;
                    }
                }
                // 回调
                if (!flag && listener != null) {
                    listener.OnSpotFinalPersonSign();
                }
            }
            ToastUtils.imgToast(getActivity(),
                    R.drawable.hd_hse_phone_message_true, "保存成功！");
        }
    };

    public interface OnSignListener {
        void OnSpotFinalPersonSign();

        void OnEndSign();
    }

}
