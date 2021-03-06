package com.hd.hse.osc.phone.ui.activity.workorder;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.hd.hse.business.listener.AbstractActionListener;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.MessageDialog;
import com.hd.hse.common.component.phone.dialog.UpZYPProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.camera.CameraCaptureActivity;
import com.hd.hse.common.module.phone.ui.activity.PhotoManagerActicity;
import com.hd.hse.common.module.phone.ui.activity.WorkOrderDetailActivity;
import com.hd.hse.common.module.phone.ui.module.activity.WorkOrderBaseActivity;
import com.hd.hse.common.module.phone.ui.module.frament.CounterMegerSingnFramment;
import com.hd.hse.common.module.phone.ui.module.frament.CounterSignFramment;
import com.hd.hse.common.module.phone.ui.module.frament.CounterSignFramment.OnSignListener;
import com.hd.hse.common.module.ui.model.fragment.EnergyIsolationFragment;
import com.hd.hse.common.module.ui.model.fragment.GasesCheckFragment;
import com.hd.hse.common.module.ui.model.fragment.NaviFrameFragment;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.workorder.RemoteAppr;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.osc.business.workorder.WorkTaskActionListener;
import com.hd.hse.osc.phone.R;
import com.hd.hse.osc.phone.ui.activity.obsolete.WorkOrderObsoleteActivity;
import com.hd.hse.osc.phone.ui.activity.remoteappr.RemoteApprActivity;
import com.hd.hse.osc.phone.ui.fragment.environment.EnvironmentHarmJudgeFragment;
import com.hd.hse.osc.phone.ui.fragment.harmdistinguish.HarmDistinguishListFragment;
import com.hd.hse.osc.phone.ui.fragment.hoisting.HoistingInfoFragment;
import com.hd.hse.osc.phone.ui.fragment.measure.MeasureFragmentosc;
import com.hd.hse.osc.phone.ui.fragment.personalprotect.PersonalProtectEquipment;
import com.hd.hse.osc.phone.ui.fragment.tempelectricity.TempEleFragment;
import com.hd.hse.service.workorder.queryinfo.QueryWorkOrderInfo;
import com.hd.hse.system.SystemProperty;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: WorkOrderActivity (???????????????????????????)<br/>
 * date: 2015???1???21??? <br/>
 */
public class WorkOrderActivity extends WorkOrderBaseActivity implements
        OnSignListener {

    private static Logger logger = LogUtils.getLogger(WorkOrderActivity.class);

    @Override
    public void setCustomMenuBarInfo() {
        if (!getIntent().getBooleanExtra("isJoin", false)) {
            setCustomMenuBar(new String[]{IActionBar.ITEM_WORKSCAN,
                    IActionBar.ITEM_RETURN, IActionBar.ITEM_CANNEL,
                    IActionBar.ITEM_PHOTOGTAPH, IActionBar.ITEM_PHOTOMANAGER,
                    IActionBar.ITEM_UPWORKORDER});
        } else {
            setCustomMenuBar(new String[]{IActionBar.ITEM_PHOTOGTAPH,
                    IActionBar.ITEM_PHOTOMANAGER, IActionBar.ITEM_UPWORKORDER});
        }
    }

    @Override
    public Class<? extends NaviFrameFragment> getNavFragmentClass(
            Integer contype) {
        Class<? extends NaviFrameFragment> resultClz = null;
        switch (contype) {
            case IConfigEncoding.HARM_TYPE:
                // ??????????????????Fragment
                resultClz = HarmDistinguishListFragment.class;

                break;
            case IConfigEncoding.ENERGY_TYPE:
                resultClz = EnergyIsolationFragment.class;
                break;
            case IConfigEncoding.GAS_TYPE:
                resultClz = GasesCheckFragment.class;
                break;
            case IConfigEncoding.MEASURE_TYPE:
                // resultClz = ???????????? Fragment???????????????
                resultClz = MeasureFragmentosc.class;
                break;
            case IConfigEncoding.PPE_TYPE:
                resultClz = PersonalProtectEquipment.class;
                break;
            case IConfigEncoding.SIGN_TYPE:
                resultClz = CounterSignFramment.class;
                break;
            case IConfigEncoding.YQ_SIGN_TYPE:
                // resultClz = ???????????? Fragment???????????????
                break;
            case IConfigEncoding.MEGER_SIGN_TYPE:
                resultClz = CounterMegerSingnFramment.class;
                break;
            case IConfigEncoding.TEMPELE_TYPE:
                resultClz = TempEleFragment.class;
                break;
            case IConfigEncoding.DWDC_INFO_TYPE:
                resultClz = HoistingInfoFragment.class;
                break;
            case IConfigEncoding.ENVIRONMENTHARMJUDGE:
                resultClz = EnvironmentHarmJudgeFragment.class;
                break;
            default:
                throw new IllegalArgumentException("IConfigEncoding ??????????????????:"
                        + contype);
        }
        return resultClz;

    }

    @Override
    public IEventListener getiEnventListener() {
        // TODO Auto-generated method stub
        return eventLst;
    }

    /**
     * getActionListener:(???????????????). <br/>
     * date: 2015???11???25??? <br/>
     *
     * @return
     * @author lxf
     */
    @Override
    public AbstractActionListener getActionListener() {
        return new WorkTaskActionListener();
    }

    private IEventListener eventLst = new IEventListener() {

        @Override
        public void eventProcess(int eventType, Object... arg1)
                throws HDException {
            // TODO Auto-generated method stub
            if (IEventType.ACTIONBAR_RETURN_CLICK == eventType) {
                sendBackWorkOrder();
            } else if (IEventType.ACTIONBAR_INVAILD_CLICK == eventType) {
                nullifyWorkOrder();
            } else if (IEventType.ACTIONBAR_SEETICKET_CLICK == eventType) {
                Intent intent = new Intent();
                intent.setClass(WorkOrderActivity.this,
                        WorkOrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(WorkOrderDetailActivity.WORK_ORDER,
                        workOrder);
                bundle.putBoolean(
                        WorkOrderDetailActivity.FROMWORKORDERACTIVITY, true);

                intent.putExtras(bundle);
                // ?????????????????????????????????
                startActivityForResult(intent,
                        WorkOrderDetailActivity.REQUESTCODE);
            } else if (IEventType.ACTIONBAR_ITEM_UP_CLICK == eventType) {
                // ???????????????
                upWorkOrder();

            } else if (IEventType.DOWN_WORK_LIST_LOAD == eventType) {
                if (WorkOrderActivity.this != null) {
                    WorkOrderActivity.this.finish();
                }

            } else if (IEventType.DOWN_WORK_LIST_SHOW == eventType) {
                // finish();
            } else if (IEventType.REMOTEAPPR == eventType) {
                // ????????????
                gotoRemoteApprActivity();
            }

        }

    };

    /**
     * ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     */
    private void gotoRemoteApprActivity() {
        List<SuperEntity> workAPLst = null;
        if (mFragmentCache.containsKey(CounterSignFramment.class.getName())) {
            // ??????
            CounterSignFramment counterSignFramment = (CounterSignFramment) mFragmentCache
                    .get(CounterSignFramment.class.getName());
            workAPLst = counterSignFramment.workAPLst;
        } else if (mFragmentCache.containsKey(CounterMegerSingnFramment.class
                .getName())) {
            CounterMegerSingnFramment counterMegerSingnFramment = (CounterMegerSingnFramment) mFragmentCache
                    .get(CounterMegerSingnFramment.class.getName());
            workAPLst = counterMegerSingnFramment.workAPLst;
        }

        ArrayList<RemoteAppr> remoteApprs = null;
        remoteApprs = new ArrayList<RemoteAppr>();
        if (workAPLst != null) {
            WorkApprovalPermission workApprovalPermission = null;
            for (SuperEntity workAP : workAPLst) {
                // ???????????????????????????
                if (((WorkApprovalPermission) workAP).getIs_spot_final_person() == 1) {
                    workApprovalPermission = (WorkApprovalPermission) workAP;
                    break;
                }
            }
            if (workApprovalPermission == null) {
                ToastUtils.toast(getApplicationContext(), "??????????????????????????????");
                return;
            }
            if (workApprovalPermission.getPersonid() == null
                    || workApprovalPermission.getPersonid().equals("")) {
                ToastUtils.toast(getApplicationContext(), "?????????????????????????????????");
                return;
            }
            String zysqid = null;
            String zyptype = null;
            String zyptype_desc = null;
            if (!getIntent().getBooleanExtra("isJoin", false)) {

                zysqid = mWorkOrder.getUd_zyxk_zysqid();
                zyptype = mWorkOrder.getZypclass();
                zyptype_desc = mWorkOrder.getZypclassname();
            } else {
                zysqid = workApprovalPermission.getStrzysqid().replace("'", "");
                if (zysqid == null || "".equals(zysqid)) {
                    ToastUtils
                            .imgToast(WorkOrderActivity.this,
                                    R.drawable.hd_hse_common_msg_wrong,
                                    "???????????????????????????id??????");
                    return;
                }
                String[] zysqids = zysqid.split(",");
                for (int i = 0; i < zysqids.length; i++) {
                    for (int j = 0; j < workOrderList.size(); j++) {
                        if (zysqids[i].equals(workOrderList.get(j)
                                .getUd_zyxk_zysqid())) {
                            if (zyptype == null) {
                                zyptype = workOrderList.get(j).getZypclass();
                            } else {
                                zyptype += ("," + workOrderList.get(j)
                                        .getZypclass());
                            }
                            if (zyptype_desc == null) {
                                zyptype_desc = workOrderList.get(j)
                                        .getZypclassname();
                            } else {
                                zyptype_desc += ("," + workOrderList.get(j)
                                        .getZypclassname());
                            }
                            break;
                        }
                    }
                }

            }
            PersonCard personCard = SystemProperty.getSystemProperty()
                    .getLoginPerson();
            for (SuperEntity workAP : workAPLst) {

                // ??????????????????????????????????????????
                if (((WorkApprovalPermission) workAP).getIs_remote_approve() == 1
                        && (((WorkApprovalPermission) workAP).getPersonid() == null || ""
                        .endsWith(((WorkApprovalPermission) workAP)
                                .getPersonid()))) {
                    RemoteAppr remoteAppr = new RemoteAppr();
                    remoteAppr.setZysqid(zysqid);
                    remoteAppr.setSpnode(((WorkApprovalPermission) workAP)
                            .getSpfield());
                    remoteAppr.setSpnode_desc(((WorkApprovalPermission) workAP)
                            .getSpfield_desc());
                    remoteAppr.setZyptype(zyptype);
                    remoteAppr.setZyptype_desc(zyptype_desc);
                    remoteAppr.setSpot_final_personid(personCard.getPersonid());
                    remoteAppr.setSpot_final_person_name(personCard
                            .getPersonid_desc());
                    remoteAppr.setQxrole(((WorkApprovalPermission) workAP).getQxrole());
                    remoteAppr.setBpermulcard(((WorkApprovalPermission) workAP)
                            .getBpermulcard());
                    remoteAppr.setIsmust(((WorkApprovalPermission) workAP)
                            .getIsmust());
                    remoteApprs.add(remoteAppr);

                }
            }
        }

        if (remoteApprs != null && remoteApprs.size() > 0) {
            Intent intent = new Intent(WorkOrderActivity.this,
                    RemoteApprActivity.class);
            intent.putExtra(RemoteApprActivity.REMOTEAPPRS, remoteApprs);
            startActivityForResult(intent, 0);
        } else {
            ToastUtils.imgToast(getApplicationContext(),
                    R.drawable.hd_common_message_error, "???????????????????????????????????????");
        }

    }

    /**
     * TODO ???????????? nullifyWorkOrder:(). <br/>
     * date: 2015???3???9??? <br/>
     *
     * @author wenlin
     */
    private void nullifyWorkOrder() {
        MessageDialog.Builder builder = new MessageDialog.Builder(this);
        builder.setTitle("??????");
        builder.setMessage("????????????????????????");
        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // ????????????????????????
                Map<String, Object> mapParam = new HashMap<String, Object>();
                mapParam.put(WorkOrder.class.getName(), mWorkOrder);
                actionShowDialog("????????????????????????...",
                        IActionType.ACTION_TYPE_CANNULLIFY, mapParam);
            }
        });
        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.createWarm().show();
    }

    /**
     * sendBackWorkOrder:(???????????????). <br/>
     * date: 2015???1???21??? <br/>
     *
     * @author wenlin
     */
    public void sendBackWorkOrder() {
        MessageDialog.Builder builder = new MessageDialog.Builder(
                WorkOrderActivity.this);
        builder.setTitle("??????");
        builder.setMessage("??????????????????????????????");
        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                Map<String, Object> mapParam = new HashMap<String, Object>();
                mapParam.put(WorkOrder.class.getName(), getWorkOrder());
                PDAWorkOrderInfoConfig pdaConfig = null;

                if (getListPDAConfigInfo() == null
                        || getListPDAConfigInfo().size() == 0) {
                    pdaConfig = new PDAWorkOrderInfoConfig();
                    pdaConfig.setPscode(IConfigEncoding.SP);
                } else {
                    pdaConfig = (PDAWorkOrderInfoConfig) getListPDAConfigInfo()
                            .get(0);
                }
                mapParam.put(PDAWorkOrderInfoConfig.class.getName(), pdaConfig);
                // 1.?????????2.???????????? 3. ??????
                actionShowDialog("??????????????????...", IActionType.ACTION_TYPE_RETURN,
                        mapParam);
            }
        });
        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        builder.createWarm().show();
    }

    @Override
    public void successActionCallback(Bundle msgData) {
        if (msgData.containsKey(IActionType.ACTION_TYPE_CANNULLIFY)) {
            Intent intent = new Intent(WorkOrderActivity.this,
                    WorkOrderObsoleteActivity.class);
            Bundle bundle = new Bundle();
            // ?????????????????????
            bundle.putSerializable(WorkOrderObsoleteActivity.WORK_ORDER,
                    mWorkOrder);
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == WorkOrderObsoleteActivity.OBSOLETESUCESS
                || resultCode == WorkOrderDetailActivity.RESULTCODE) {
            // ????????????????????????,????????????
            if (null != WorkOrderActivity.this) {
                WorkOrderActivity.this.finish();
            }

        } else if (resultCode == IEventType.REMOTEAPPR) {
            // ????????????????????????
            if (!getIntent().getBooleanExtra("isJoin", false)) {
                setCustomMenuBar(new String[]{IActionBar.ITEM_WORKSCAN,
                        IActionBar.ITEM_RETURN, IActionBar.ITEM_CANNEL,
                        IActionBar.ITEM_PHOTOGTAPH,
                        IActionBar.ITEM_PHOTOMANAGER,
                        IActionBar.ITEM_UPWORKORDER});
            } else {
                setCustomMenuBar(new String[]{IActionBar.ITEM_PHOTOGTAPH,
                        IActionBar.ITEM_PHOTOMANAGER,
                        IActionBar.ITEM_UPWORKORDER});
            }
            if (mFragmentCache.containsKey(CounterSignFramment.class.getName())) {
                NaviFrameFragment fragment = mFragmentCache
                        .get(CounterSignFramment.class.getName());
                fragment.onActivityResult(requestCode, resultCode, data);
            } else if (mFragmentCache
                    .containsKey(CounterMegerSingnFramment.class.getName())) {
                NaviFrameFragment fragment = mFragmentCache
                        .get(CounterMegerSingnFramment.class.getName());
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }

    }

    /**
     * touchImages:(????????????). <br/>
     * date: 2016???7???12??? <br/>
     *
     * @author liuyang
     */
    @Override
    public void touchImages() {
        if (null == queryWorkInfo) {
            logger.equals("?????????setQueryWorkInfo()????????????????????????");
            ToastUtils.toast(getBaseContext(), "?????????setQueryWorkInfo()????????????????????????");
            return;
        } else if (null == mWorkOrder) {
            logger.equals("?????????setWorkInfo()?????????????????????");
            ToastUtils.toast(getBaseContext(), "?????????setWorkInfo()?????????????????????");
            return;
        }
        Image image = new Image();
        if (!getIntent().getBooleanExtra("isJoin", false)) {
            image.setTableid(mWorkOrder.getUd_zyxk_zysqid());// ?????????
            image.setZysqid(mWorkOrder.getUd_zyxk_zysqid());
            image.setImagepath(SystemProperty.getSystemProperty()
                    .getRootDBpath()
                    + File.separator
                    + mWorkOrder.getUd_zyxk_zysqid());// ???????????????
        } else {
            try {
                List<WorkOrder> list = queryWorkInfo
                        .queryWorkOrderListForCamera(workOrderList, null);
                if (list != null && list.size() != 0) {
                    StringBuilder orderIds = new StringBuilder();
                    for (WorkOrder w : list) {
                        orderIds.append(w.getUd_zyxk_zysqid()).append(",");
                    }
                    orderIds.setLength(orderIds.length() - 1);
                    image.setTableid(orderIds.toString());
                    image.setZysqid(orderIds.toString());
                    orderIds.setLength(0);
                    for (WorkOrder w : workOrderList) {
                        orderIds.append(w.getUd_zyxk_zysqid()).append(",");
                    }
                    orderIds.setLength(orderIds.length() - 1);
                    image.setChildids(orderIds.toString());
                } else {
                    ToastUtils.toast(getBaseContext(), "???????????????????????????????????????????????????");
                    return;
                }
            } catch (HDException e) {
                logger.error(e.getMessage());
                ToastUtils.toast(getBaseContext(), e.getMessage());
            }
        }
        image.setTablename(mWorkOrder.getDBTableName());// ??????
        image.setCreateuser(SystemProperty.getSystemProperty().getLoginPerson()
                .getPersonid());// ?????????
        image.setCreateusername(SystemProperty.getSystemProperty()
                .getLoginPerson().getPersonid_desc());
        PDAWorkOrderInfoConfig config = (PDAWorkOrderInfoConfig) getNaviCurrentEntity();
        image.setFuncode(config.getPscode());// ????????????
        image.setContype(config.getContype());
        image.setImagename(config.getContypedesc());
        Intent intent = new Intent(getBaseContext(),
                CameraCaptureActivity.class);
        intent.putExtra(CameraCaptureActivity.ENTITY_ARGS, image);
        startActivity(intent);
    }

    /**
     * managerImage:(????????????). <br/>
     * date: 2016???7???12??? <br/>
     *
     * @author wenlin
     */
    @Override
    public void managerImages() {
        PDAWorkOrderInfoConfig config = (PDAWorkOrderInfoConfig) getNaviCurrentEntity();
        try {
            if (null == queryWorkInfo) {
                logger.equals("?????????setQueryWorkInfo()????????????????????????");
                ToastUtils.toast(getBaseContext(),
                        "?????????setQueryWorkInfo()????????????????????????");

            } else if (null == mWorkOrder) {
                logger.equals("?????????setWorkInfo()?????????????????????");
                ToastUtils.toast(getBaseContext(), "?????????setWorkInfo()?????????????????????");
                return;
            }
            List<Image> imageList = null;
            if (!getIntent().getBooleanExtra("isJoin", false)) {
                imageList = queryWorkInfo.queryPhoto(mWorkOrder, config);
            } else {
                imageList = queryWorkInfo.queryPhoto(workOrderList, config);
            }
            Intent intent = new Intent(getApplicationContext(),
                    PhotoManagerActicity.class);
            intent.putExtra(PhotoManagerActicity.IMAGEENTITY,
                    (Serializable) imageList);
            startActivity(intent);
        } catch (HDException e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * upzyp:TODO(???????????????).
     */
    private UpZYPProgressDialog upzyp = null;

    /**
     * ???????????????
     */
    public void upWorkOrder() {
        if (upzyp == null) {
            upzyp = new UpZYPProgressDialog(this);
            upzyp.setGetDataSourceListener(eventLst);
        }
        QueryWorkOrderInfo mQueryWorkOrderInfo = new QueryWorkOrderInfo();
        String[] ud_zyxk_zysqids = null;
        // ????????????????????????????????????ud_zyxk_zysqids
        if (!getIntent().getBooleanExtra("isJoin", false)) {
            ud_zyxk_zysqids = new String[1];
            ud_zyxk_zysqids[0] = mWorkOrder.getUd_zyxk_zysqid();
        } else {
            ud_zyxk_zysqids = new String[workOrderList.size()];
            for (int i = 0; i < workOrderList.size(); i++) {
                ud_zyxk_zysqids[i] = workOrderList.get(i).getUd_zyxk_zysqid();
            }
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

    @Override
    public void OnSpotFinalPersonSign() {
        if (!getIntent().getBooleanExtra("isJoin", false)) {
            setCustomMenuBar(new String[]{IActionBar.ITEM_WORKSCAN,
                    IActionBar.ITEM_RETURN, IActionBar.ITEM_CANNEL,
                    IActionBar.ITEM_PHOTOGTAPH, IActionBar.ITEM_PHOTOMANAGER,
                    IActionBar.ITEM_UPWORKORDER, IActionBar.REMOTE_APPR});
        } else {
            setCustomMenuBar(new String[]{IActionBar.ITEM_PHOTOGTAPH,
                    IActionBar.ITEM_PHOTOMANAGER, IActionBar.ITEM_UPWORKORDER,
                    IActionBar.REMOTE_APPR});
        }

    }

    @Override
    public void OnEndSign() {
        if (!getIntent().getBooleanExtra("isJoin", false)) {
            setCustomMenuBar(new String[]{IActionBar.ITEM_WORKSCAN,
                    IActionBar.ITEM_RETURN, IActionBar.ITEM_CANNEL,
                    IActionBar.ITEM_PHOTOGTAPH, IActionBar.ITEM_PHOTOMANAGER,
                    IActionBar.ITEM_UPWORKORDER});
        } else {
            setCustomMenuBar(new String[]{IActionBar.ITEM_PHOTOGTAPH,
                    IActionBar.ITEM_PHOTOMANAGER, IActionBar.ITEM_UPWORKORDER,});
        }
    }

}
