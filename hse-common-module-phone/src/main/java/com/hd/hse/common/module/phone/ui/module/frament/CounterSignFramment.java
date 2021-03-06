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
 * TODO ????????????Fragment ClassName: CounterSignFramment ()<br/>
 * date: 2015???1???26??? <br/>
 *
 * @author wenlin
 */
public class CounterSignFramment extends NaviFrameFragment {

    private static Logger logger = LogUtils
            .getLogger(CounterSignFramment.class);
    /**
     * CounterSignView:TODO(??????).
     */
    private View counterSignView;

    /**
     * sighLst:TODO(??????List).
     */
    private ListView sighLst;

    /**
     * signLsvAdapter:TODO(?????????).
     */
    private SignListViewAdapter signLsvAdapter;

    /**
     * workAPLst:TODO(???????????????).
     */
    public List<SuperEntity> workAPLst;

    /**
     * mWorkOrder:TODO(???????????????).
     */
    private WorkOrder mWorkOrder;

    //??????????????????????????????
    private List<WorkOrder> loadmWorkOrder = new ArrayList<>();

    protected List<WorkOrder> workOrders;

    /**
     * currentApp:TODO(??????????????????).
     */
    private SuperEntity currentAppPsm;

    /**
     * prgDialog:TODO(?????????????????????).
     */
    private ProgressDialog prgDialog;

    /**
     * busAction:TODO(????????????).
     */
    private BusinessAction busAction;

    /**
     * busAsyTask:TODO(????????????).
     */
    private BusinessAsyncTask busAsyTask;
    /**
     * ??????????????????????????????
     */
    private boolean isClick = true;

    /**
     * mRequestCode:TODO(?????????).
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
                    + "????????????OnSpotFinalPersonSignListener ??????", null);
        }

    }

    public CounterSignFramment() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initData(List<Object> data) {
        if (null != data && data.size() > 1) {
            // ????????????
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
        // ?????????????????????

        loadmWorkOrder.add(mWorkOrder);
        if (getActionType().equals(IActionType.ACTION_TYPE_SIGN)) {
            signLsvAdapter = new SignListViewAdapter(workAPLst, loadmWorkOrder, getActivity(), null);
        } else if (getActionType().equals(IActionType.ACTION_TYPE_MERGE_SIGN)) {
            signLsvAdapter = new SignListViewAdapter(workAPLst, workOrders, getActivity(), null);
        }
        sighLst.setAdapter(signLsvAdapter);
        // ????????????
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
     * getNextIntent:(?????????????????????????????????Intent). <br/>
     * date: 2015???9???8??? <br/>
     *
     * @param obj
     * @return
     * @author lxf
     */
    public Intent getNextIntent(Object obj) {
        // ???????????????????????????
        SuperEntity currentWPP = (SuperEntity) obj;

        Intent intent = new Intent();

        if (currentWPP.getAttribute("isdzqm") != null
                && currentWPP.getAttribute("isdzqm").equals("1")) {
            // ????????????
            intent.setClass(getActivity(), PaintSignatureActivity.class);
        } else {
            // ????????????
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
     * TODO ???????????????
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
            // ??????????????????????????????
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
            // ??????????????????????????????????????????
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
                // ???????????????????????????????????????
                // lxf 2015-08-28 ??????????????????????????????
                // SwipingCardUtils.swipingCard(mWorkOrder, currentAppPsm);
                // ?????????????????????????????????
                saveCurrentAPInfo();
            } catch (HDException e) {
                logger.error(e.getMessage(), e);
            } finally {
                isClick = true;
            }

        }
        isClick = true;
        if (resultCode == IEventType.REMOTEAPPR) {
            // ????????????????????????????????????????????????????????????????????????????????????
            try {
                saveRemoteApprWorkOrderState();
            } catch (DaoException e) {
                logger.error(e);
                ToastUtils.imgToast(getActivity(),
                        R.drawable.hd_hse_common_msg_wrong, "?????????????????????");
                return;
            } catch (SQLException e) {
                logger.error(e);
                ToastUtils.imgToast(getActivity(),
                        R.drawable.hd_hse_common_msg_wrong, "???????????????????????????");
                return;
            } catch (HDException e) {
                logger.error(e);
                ToastUtils.imgToast(getActivity(),
                        R.drawable.hd_common_message_error, e.getMessage());
            }
            // ????????????????????????
            ((PDAWorkOrderInfoConfig) getNaviCurrentEntity()).setIsComplete(1);
            // ????????????????????????????????????
            ((NaviFrameActivity) getActivity())
                    .setNaviFinish(getNaviCurrentEntity());
            //isClick = false;
        }

    }

    /**
     * ???????????????????????????
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
            throw new HDException("?????????????????????");
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
     * TODO ?????????????????????????????? saveCurrentAPInfo:(). <br/>
     * date: 2015???1???30??? <br/>
     *
     * @throws HDException
     * @author wenlin
     */
    public void saveCurrentAPInfo() throws HDException {

        prgDialog = new ProgressDialog(getActivity(), "????????????...");
        prgDialog.show();

        Map<String, Object> mapParam = new HashMap<String, Object>();
        Map<String, Object> mapParamTemp = getMapParamInfo(mWorkOrder);
        if (mapParamTemp != null && mapParamTemp.size() > 0) {
            mapParam.putAll(mapParamTemp);
        }
        // pda????????????
        mapParam.put(PDAWorkOrderInfoConfig.class.getName(),
                getNaviCurrentEntity());
        // ??????????????????
        mapParam.put(WorkApprovalPermission.class.getName(), currentAppPsm);
        // ??????????????????
        busAsyTask.execute(getActionType(), mapParam);
    }

    /**
     * getActionType:(??????????????????). <br/>
     * date: 2015???9???8??? <br/>
     *
     * @return
     * @author lxf
     */
    public String getActionType() {

        return IActionType.ACTION_TYPE_SIGN;
    }

    /**
     * getMapParamInfo:(????????????????????????). <br/>
     * date: 2015???9???8??? <br/>
     *
     * @param mwoWorkOrder
     * @return
     * @author lxf
     */
    public Map<String, Object> getMapParamInfo(WorkOrder mWorkOrder) {
        Map<String, Object> mapParam = new HashMap<String, Object>();
        // ???????????????
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
                // ?????????????????????????????????????????????
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
            // ???????????????????????????
            // ????????????????????????????????????????????????????????????????????????
            if (null != sighLst && currentAppPsm != null
                    && currentAppPsm.getAttribute("isend").equals(1)
                    && workAPLst.get(workAPLst.size() - 1) == currentAppPsm) {
                // ????????????????????????
                ((PDAWorkOrderInfoConfig) getNaviCurrentEntity())
                        .setIsComplete(1);
                // ????????????????????????????????????
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
            // ???????????????????????????
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

            // ????????????????????????????????????????????????
            if (((WorkApprovalPermission) currentAppPsm)
                    .getIs_spot_final_person() == 1) {
                //???????????????????????????????????????????????????(??????????????????????????????????????????????????????????????????????????????????????????)
                boolean flag = false;
                for (SuperEntity superEntity : workAPLst) {
                    WorkApprovalPermission permission = (WorkApprovalPermission) superEntity;
                    if (permission.getIs_spot_final_person() == 1
                            && (permission.getPersondesc() == null || "".equals(permission.getPersondesc()))) {
                        flag = true;
                        break;
                    }
                }
                // ??????
                if (!flag && listener != null) {
                    listener.OnSpotFinalPersonSign();
                }
            }
            ToastUtils.imgToast(getActivity(),
                    R.drawable.hd_hse_phone_message_true, "???????????????");
        }
    };

    public interface OnSignListener {
        void OnSpotFinalPersonSign();

        void OnEndSign();
    }

}
