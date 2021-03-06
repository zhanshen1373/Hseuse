package com.hd.hse.common.module.phone.ui.module.activity;

import android.content.Intent;
import android.os.Bundle;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.listener.AbstractActionListener;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.ui.activity.NaviFrameActivity;
import com.hd.hse.common.module.ui.model.fragment.EnergyIsolationFragment;
import com.hd.hse.common.module.ui.model.fragment.GasesCheckFragment;
import com.hd.hse.common.module.ui.model.fragment.NaviFrameFragment;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkGuardEquipment;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.service.workorder.checkrules.CheckControllerActionListener;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.utils.ServiceActivityUtils;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName: WorkOrderBaseActivity (?????????ppe????????? ??????????????????)<br/>
 * date: 2015???2???6??? <br/>
 */
public class WorkOrderBaseActivity extends NaviFrameActivity {

    private static Logger logger = LogUtils
            .getLogger(WorkOrderBaseActivity.class);
    /**
     * ???????????????
     */
    public static final String WORK_ORDER = "workOrder";
    /**
     * WORK_ORDERLIST:TODO(??????).
     */
    public static final String WORK_ORDERLIST = "workOrderList";
    /**
     * quertWorkInfo:TODO(?????????????????????).
     */
    private IQueryWorkInfo quertWorkInfo = new QueryWorkInfo();
    /**
     * ?????????
     */
    public WorkOrder workOrder;

    /**
     * workOrderList:TODO(???????????????).
     */
    public List<WorkOrder> workOrderList;

    /**
     * preStatus:TODO(????????????????????????).
     */
    private String preStatus;
    /**
     * preYqCount:TODO(?????????????????????).
     */
    private int preYyqCount;
    /**
     * listPDAConfigInfo:TODO().
     */
    public List<SuperEntity> listPDAConfigInfo = null;

    /**
     * priviousNaviTouchEntity:TODO(??????????????????????????????????????????).
     */
    public PDAWorkOrderInfoConfig priviousNaviTouchEntity;

    /**
     * action:TODO(??????????????????).
     */
    public BusinessAction action;

    /**
     * actLsr:TODO(??????????????????).
     */
    public AbstractCheckListener actLsr;

    /**
     * asyTask:TODO(????????????).
     */
    public BusinessAsyncTask asyTask;

    /**
     * prgDialog:TODO(????????????).
     */
    public ProgressDialog prgDialog;

    /**
     * ?????????????????????
     */
    private boolean isJoin;

    /**
     * iEnventListener:TODO(????????????).
     */
    public IEventListener getiEnventListener() {
        return null;
    }

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public List<SuperEntity> getListPDAConfigInfo() {
        return listPDAConfigInfo;
    }

    /**
     * title:TODO(??????????????????).
     */
    private String title = "";
    /*********** ??????ID **************/
    // ????????????
    private static final int[] HARM_DIST = new int[]{
            R.drawable.hd_hse_common_nav_harm_dist_normal,
            R.drawable.hd_hse_common_nav_harm_dist_finished,
            R.drawable.hd_hse_common_nav_harm_dist_selected};
    // ????????????
    private static final int[] STEP_CHECK = new int[]{
            R.drawable.hd_hse_common_nav_step_check_normal,
            R.drawable.hd_hse_common_nav_step_check_finished,
            R.drawable.hd_hse_common_nav_step_check_selected};
    // ????????????
    private static final int[] NAV_PROTECT = new int[]{
            R.drawable.hd_hse_common_nav_protect_normal,
            R.drawable.hd_hse_common_nav_protect_finished,
            R.drawable.hd_hse_common_nav_protect_selected};
    // ????????????
    private static final int[] GAS_CHECK = new int[]{
            R.drawable.hd_hse_common_nav_gas_check_normal,
            R.drawable.hd_hse_common_nav_gas_check_finished,
            R.drawable.hd_hse_common_nav_gas_check_selected};

    /**
     * ???????????? Created by LiuYang on 2016/2/16
     */
    private static final int[] ENERGY_ISOLATION = new int[]{
            R.drawable.hd_hse_common_nav_gas_check_normal,
            R.drawable.hd_hse_common_nav_gas_check_finished,
            R.drawable.hd_hse_common_nav_gas_check_selected};
    // ??????
    private static final int[] NAV_SIGN = new int[]{
            R.drawable.hd_hse_common_nav_sign_normal,
            R.drawable.hd_hse_common_nav_sign_finished,
            R.drawable.hd_hse_common_nav_sign_selected};

    // ??????
    private static final int[] NAV_YQ = new int[]{
            R.drawable.hd_hse_common_nav_yq_normal,
            R.drawable.hd_hse_common_nav_yq_finished,
            R.drawable.hd_hse_common_nav_yq_selected};
    // ????????????
    private static final int[] NAV_ELE = new int[]{
            R.drawable.hd_hse_common_nav_ele_normal,
            R.drawable.hd_hse_common_nav_ele_finished,
            R.drawable.hd_hse_common_nav_ele_selected};
    // ??????????????????
    private static final int[] NAV_DWDC_INFO = new int[]{
            R.drawable.hd_hse_common_nav_diaozhuang_normal,
            R.drawable.hd_hse_common_nav_diaozhuang_finished,
            R.drawable.hd_hse_common_nav_diaozhuang_selected};
    // ??????????????????
    private static final int[] NAV_ENVIROMENT_HARM_JUDGE = new int[]{
            R.drawable.hd_hse_common_nav_enviromentharmjudge_normal,
            R.drawable.hd_hse_common_nav_enviromentharmjudge_finished,
            R.drawable.hd_hse_common_nav_enviromentharmjudge_selected};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        try {
            super.onCreate(savedInstanceState);
            initParam();
            initView();
        } catch (HDException e) {
            logger.error(e);
            ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                    e.getMessage());
            this.finish();
        } catch (Exception e) {
            logger.error(e);
            ToastUtils.toast(this, "????????????,??????????????????!");
            this.finish();
        }
    }

    @Override
    public void finish() {
        try {
            if (isJoin) {
                setResult(RESULT_OK);
            } else {
                workOrder = quertWorkInfo.queryWorkInfo(workOrder, null);
                // ???????????????????????????
                if (workOrder == null) {
                    // ?????????????????????workOrder???null
                    setResult(RESULT_OK);
                } else if (!preStatus.equals(workOrder.getStatus())
                        || (preYyqCount != workOrder.getYyqcount() && workOrder
                        .getYyqcount() >= workOrder.getYqcount())) {
                    setResult(RESULT_OK);
                } else {
                    setResult(RESULT_CANCELED);
                }
            }
        } catch (HDException e) {
            logger.error(e.getMessage(), e);
        }
        super.finish();
    }

    /**
     * initParam:(???????????????). <br/>
     * date: 2015???2???6??? <br/>
     *
     * @author Administrator
     */
    private void initParam() {
        // TODO Auto-generated method stub
        Intent intent = this.getIntent();
        isJoin = intent.getBooleanExtra("isJoin", false);
        workOrder = (WorkOrder) intent.getSerializableExtra(WORK_ORDER);
        Object obj = intent.getSerializableExtra(WORK_ORDERLIST);
        if (obj != null && obj instanceof List) {
            workOrderList = (List<WorkOrder>) obj;
        }
        if (!isJoin) {
            // ???????????????
            title = workOrder.getZypclassname();
            // ????????????????????????
            preStatus = workOrder.getStatus();

            preYyqCount = workOrder.getYyqcount();
        } else {
            title = "????????????";
        }
        // ?????????????????????
        listPDAConfigInfo = workOrder.getChild(PDAWorkOrderInfoConfig.class
                .getName());
        if (null == listPDAConfigInfo || listPDAConfigInfo.size() == 0) {
            // ????????????????????????????????????.
            ToastUtils.toast(this, "???????????????????????????????????????");
        }
        actLsr = new CheckControllerActionListener();
        action = new BusinessAction(actLsr);
        asyTask = new BusinessAsyncTask(action, asyCallBack);
    }

    /**
     * initView:(?????????????????????). <br/>
     * date: 2015???1???21??? <br/>
     *
     * @throws Exception
     * @author wenlin
     */
    private void initView() throws Exception {
        // ??????????????????
        if (isJoin) {
            // ??????????????????
            setCustomActionBar(eventLst, new String[]{IActionBar.TV_BACK,
                    IActionBar.TV_TITLE, IActionBar.IBTN_LEVELTWO_MORE});
            // ?????????????????????
            setCustomMenuBarInfo();
        } else {
            setCustomActionBar(eventLst, new String[]{IActionBar.TV_BACK,
                    IActionBar.TV_TITLE, IActionBar.IBTN_LEVELTWO_MORE});
            // ?????????????????????
            setCustomMenuBarInfo();
            // ????????????????????????????????????
            setPopDetailWorkerOrer(workOrder);
        }
        // ?????????????????????
        setActionBartitleContent(title, !isJoin);

        // ????????????PDAWorkOrderInfoConfig
        if (listPDAConfigInfo != null && listPDAConfigInfo.size() > 0) {
            setNaviData(loadNaviiCon(listPDAConfigInfo));
        }
        // ??????????????????
        setQueryWorkInfo(quertWorkInfo);
        // ?????????????????????
        setWorkInfo(workOrder);

        setNaviItemOnClickListener(eventLst);
        // ??????????????????????????????PDA??????????????????
        getFragmentByPDAConfig((PDAWorkOrderInfoConfig) getNaviCurrentEntity());

        // ???????????????????????????
        distribute();
    }

    /**
     * distribute:(?????????????????????). <br/>
     * date: 2015???3???3??? <br/>
     *
     * @author zhaofeng
     */
    public void distribute() {

    }

    /**
     * setCustomMenuBarInfo:(??????????????????????????????). <br/>
     * date: 2015???2???9??? <br/>
     *
     * @author lxf
     */
    public void setCustomMenuBarInfo() {
        setCustomMenuBar(new String[]{IActionBar.ITEM_PHOTOGTAPH,
                IActionBar.ITEM_PHOTOMANAGER});
    }

    private IEventListener eventLst = new IEventListener() {

        @Override
        public void eventProcess(int eventType, Object... arg1)
                throws HDException {
            if (getiEnventListener() != null) {
                getiEnventListener().eventProcess(eventType, arg1);
            }
            if (IEventType.NAVIGATION_ITME_SINGLE_CLICK == eventType) {
                // ????????????
                if ((Boolean) beforeValidate(eventType, workOrder,
                        (PDAWorkOrderInfoConfig) arg1[0])) {
                    // ??????fragment
                    getFragmentByPDAConfig((PDAWorkOrderInfoConfig) arg1[0]);
                } else {
                    // priviousNaviTouchEntity = (PDAWorkOrderInfoConfig)
                    // arg1[0];
                    setNaviCurrentEntity(priviousNaviTouchEntity);
                }
            }
        }
    };

    /**
     * TODO ??????navi???????????? loadNaviCon:(). <br/>
     * date: 2015???1???27??? <br/>
     *
     * @return
     * @throws Exception
     * @author wenlin
     */
    private List<SuperEntity> loadNaviiCon(List<SuperEntity> listPDAConfigInfo)
            throws Exception {

        for (int i = 0; i < listPDAConfigInfo.size(); i++) {
            SuperEntity entity = listPDAConfigInfo.get(i);
            // ????????????
            Integer contype = (Integer) entity.getAttribute("contype");
            if (contype != null) {
                switch (contype) {
                    case IConfigEncoding.HARM_TYPE:
                        entity.setAttribute("naviIconId", HARM_DIST);
                        break;

                    case IConfigEncoding.MEASURE_TYPE:
                        entity.setAttribute("naviIconId", STEP_CHECK);
                        break;

                    case IConfigEncoding.PPE_TYPE:
                        entity.setAttribute("naviIconId", NAV_PROTECT);

                        break;
                    case IConfigEncoding.GAS_TYPE:
                        entity.setAttribute("naviIconId", GAS_CHECK);
                        break;
                    case IConfigEncoding.ENERGY_TYPE:
                        entity.setAttribute("naviIconId", ENERGY_ISOLATION);
                    case IConfigEncoding.SIGN_TYPE:
                        entity.setAttribute("naviIconId", NAV_SIGN);
                        break;
                    case IConfigEncoding.YQ_SIGN_TYPE:
                        entity.setAttribute("naviIconId", NAV_YQ);
                        break;
                    case IConfigEncoding.MEGER_SIGN_TYPE:
                        entity.setAttribute("naviIconId", NAV_YQ);
                        break;
                    case IConfigEncoding.TEMPELE_TYPE:
                        entity.setAttribute("naviIconId", NAV_ELE);
                        break;
                    case IConfigEncoding.DWDC_INFO_TYPE:
                        entity.setAttribute("naviIconId", NAV_DWDC_INFO);
                        break;
                    case IConfigEncoding.ENVIRONMENTHARMJUDGE:
                        entity.setAttribute("naviIconId", NAV_ENVIROMENT_HARM_JUDGE);
                        break;
                    default:
                        Object obj = entity.getAttribute("contypedesc");
                        String msg = "?????????" + obj != null ? obj.toString() : ""
                                + " ???????????????" + contype.toString();
                        logger.error(msg);
                        throw new Exception(msg);
                }
            }
        }
        return listPDAConfigInfo;

    }

    /**
     * resultClz:TODO(??????navi???????????????fragment???).
     */
    public Class<? extends NaviFrameFragment> resultClz;
    private HashMap<Object, Object> hasmapTab = new HashMap<Object, Object>();
    private NaviFrameFragment currentFragment = null;

    /**
     * getFragmentByPDAConfig:( * ?????? PDAWorkOrderInfoConfig ???????????????????????????
     * Fragment???????????? ??????????????? NaviFrameActivity??????????????? getFragmentByPDAConfig:(). <br/>
     * ). <br/>
     * date: 2015???2???6??? <br/>
     *
     * @param pdaConfig
     * @return
     * @throws HDException
     * @author Administrator
     */
    @SuppressWarnings("unchecked")
    public Class<? extends NaviFrameFragment> getFragmentByPDAConfig(
            PDAWorkOrderInfoConfig pdaConfig) throws HDException {
        Integer contype = pdaConfig.getContype();
        List<Object> dataListTemp = new ArrayList<Object>();
        dataListTemp.add(workOrder);
        if (contype == IConfigEncoding.HARM_TYPE
                || contype == IConfigEncoding.PPE_TYPE
                || contype == IConfigEncoding.GAS_TYPE
                || contype == IConfigEncoding.ENERGY_TYPE
                || contype == IConfigEncoding.TEMPELE_TYPE
                || contype == IConfigEncoding.ENVIRONMENTHARMJUDGE) {
            dataListTemp.add(pdaConfig);
        }
        resultClz = getNavFragmentClass(contype);
        if (resultClz == null) {
            return null;
        }
        switch (contype) {
            case IConfigEncoding.HARM_TYPE:
                // ??????????????????Fragment
                // List<SuperEntity> getlistSuperHarm = 8null;
                // if (!hasmapTab.containsKey(contype)) {
                // getlistSuperHarm = workOrder.getChild(HazardNotify.class
                // .getName());
                // if (null == getlistSuperHarm) {
                // // ?????????????????????
                // getlistSuperHarm = changeToSuperEntity((List) quertWorkInfo
                // .queryHarmInfo(workOrder, pdaConfig, null));
                // }
                // hasmapTab.put(contype, getlistSuperHarm);
                // } else {
                // getlistSuperHarm = (List<SuperEntity>) hasmapTab.get(contype);
                // }
                // dataListTemp.add(getlistSuperHarm);
                // dataListTemp.add(getListAppInfo(contype));
                List<PDAWorkOrderInfoConfig> list = null;
                if (!hasmapTab.containsKey(contype)) {
                    list = quertWorkInfo.queryHarmTabConfigs(workOrder, null);
                    hasmapTab.put(contype, list);
                } else {
                    list = (List<PDAWorkOrderInfoConfig>) hasmapTab.get(contype);
                }
                dataListTemp.add(list);
                break;
            case IConfigEncoding.ENERGY_TYPE:
                resultClz = EnergyIsolationFragment.class;
                break;
            case IConfigEncoding.GAS_TYPE:
                resultClz = GasesCheckFragment.class;
                break;
            case IConfigEncoding.MEASURE_TYPE:
                List<SuperEntity> getlistSuper = null;
                if (!hasmapTab.containsKey(contype)) {
                    getlistSuper = pdaConfig.getChild(PDAWorkOrderInfoConfig.class
                            .getName());
                    hasmapTab.put(contype, getlistSuper);
                } else {
                    getlistSuper = (List<SuperEntity>) hasmapTab.get(contype);
                }
                dataListTemp.add(getlistSuper);
                // switchContentView(resultClz, dataListTemp);
                break;
            case IConfigEncoding.PPE_TYPE:
                List<SuperEntity> getlistSuperPPE = null;
                if (!hasmapTab.containsKey(contype)) {
                    // ???????????????
                    getlistSuperPPE = workOrder.getChild(WorkGuardEquipment.class
                            .getName());
                    if (null == getlistSuperPPE) {
                        // ?????????????????????
                        getlistSuperPPE = changeToSuperEntity((List) quertWorkInfo
                                .queryPpeInfo(workOrder, null));
                    }
                    // ????????????????????????
                    hasmapTab.put(contype, getlistSuperPPE);
                } else {
                    getlistSuperPPE = (List<SuperEntity>) hasmapTab.get(contype);
                }
                dataListTemp.add(getlistSuperPPE);
                dataListTemp.add(getListAppInfo(contype));
                break;
            case IConfigEncoding.SIGN_TYPE:
                // ??????????????????
                // ????????????????????????
                IQueryRelativeConfig relation = new QueryRelativeConfig();
                RelationTableName relationEntity = new RelationTableName();
                relationEntity.setSys_type(IRelativeEncoding.ISASYNCAPPR);
                boolean isAsyncAppr = relation.isHadRelative(relationEntity);
                if (isAsyncAppr) {
                    canSign(resultClz, IConfigEncoding.SIGN_TYPE);
                } else {
                    validateAuthority(IActionType.ACTION_TYPE_CANSIGN);
                }
                // canSign(resultClz);
                break;
            case IConfigEncoding.YQ_SIGN_TYPE:
                // resultClz =
                dataListTemp.add(pdaConfig);
                break;
            case IConfigEncoding.MEGER_SIGN_TYPE:
                // ????????????
                canSign(resultClz, IConfigEncoding.MEGER_SIGN_TYPE);
                break;
            case IConfigEncoding.TEMPELE_TYPE:
                break;
            case IConfigEncoding.ENVIRONMENTHARMJUDGE:
//                ?????????????????????????????????fragment???

                break;
            default:
                validate(contype, workOrder, listPDAConfigInfo);
        }
        if (contype != IConfigEncoding.SIGN_TYPE) {
            switchContentView(resultClz, dataListTemp);
        }
        currentFragment = getFragmentByClass(resultClz);
        if (currentFragment instanceof GasesCheckFragment) {
            // ????????????????????????????????????
            ((GasesCheckFragment) currentFragment)
                    .setActionLsnr(getActionListener());
        }
        // ????????????NAVI??????????????????
        if (contype != IConfigEncoding.SIGN_TYPE) {
            // ??????pda??????
            priviousNaviTouchEntity = pdaConfig;
        }
        return resultClz;
    }

    /**
     * TODO ?????????????????? validateAuthority:(). <br/>
     * date: 2015???2???2??? <br/>
     *
     * @author wenlin
     */
    private void validateAuthority(String actionType) {
        HashMap<String, Object> paras = new HashMap<String, Object>();
        paras.put(WorkOrder.class.getName(), workOrder);
        paras.put(PDAWorkOrderInfoConfig.class.getName(), listPDAConfigInfo);
        actionShowDialog("??????????????????...", IActionType.ACTION_TYPE_CANSIGN, paras);
    }

    /**
     * validate:(??????????????????). <br/>
     * date: 2015???2???9??? <br/>
     *
     * @param objects
     * @author lxf
     */
    public void validate(Object... objects) {
    }

    /**
     * beforeValidate:(????????????????????????). <br/>
     * date: 2015???3???6??? <br/>
     *
     * @param objects
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    public Object beforeValidate(Object... objects) throws HDException {
        return Boolean.TRUE;
    }

    /**
     * setNaviBarIsCompLete:(??????????????????????????????). <br/>
     * date: 2015???1???26??? <br/>
     *
     * @author wenlin
     */
    public void setNaviBarIsCompLete() {
        try {
            // ??????PDA???????????????????????????
            SuperEntity currentNaviTouchEntity = ServiceActivityUtils
                    .getRefreshWorkConfigInfo(workOrder,
                            (PDAWorkOrderInfoConfig) getNaviCurrentEntity());
            // ????????????????????????
            setNaviFinish(currentNaviTouchEntity);
        } catch (HDException e) {
            logger.error(e.getMessage());
            ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                    e.getMessage());
        }
    }

    /**
     * getNavFragmentClass:(??????????????????fragment??????). <br/>
     * date: 2015???2???6??? <br/>
     *
     * @return
     * @author lxf
     */
    public Class<? extends NaviFrameFragment> getNavFragmentClass(
            Integer contype) {
        return null;
    }

    /**
     * TODO ???????????????????????? getListAppInfo:(). <br/>
     * date: 2015???1???30??? <br/>
     *
     * @param controlType
     * @return
     * @throws HDException
     * @author wenlin
     */
    public List<SuperEntity> getListAppInfo(int controlType) throws HDException {
        List<SuperEntity> listAppinfo = null;
        SuperEntity sup = null;
        for (int i = 0; i < listPDAConfigInfo.size(); i++) {
            if (getSuperControlType(listPDAConfigInfo.get(i)) == controlType) {
                sup = (SuperEntity) listPDAConfigInfo.get(i)
                        .getChild(PDAWorkOrderInfoConfig.class.getName())
                        .get(0);
                break;
            }
        }
        if (null != sup) {
            if (!isJoin) {
                listAppinfo = changeToSuperEntity((List) quertWorkInfo
                        .queryApprovalPermission(workOrder, sup, null, null));
            } else {
                // ??????????????????
                List<SuperEntity> listsup = new ArrayList<SuperEntity>();
                // listsup.add(workOrder);
                listsup.addAll(workOrderList);
                listAppinfo = changeToSuperEntity((List) quertWorkInfo
                        .queryApprovalPermission(listsup, sup, null, null));
            }
        }
        return listAppinfo;
    }

    /**
     * TODO ??????????????????????????? changeToSuperEntity:(). date: 2014???10???22???
     *
     * @param list
     * @return
     * @author wenlin
     */
    public List<SuperEntity> changeToSuperEntity(List list) {
        List<SuperEntity> listTemp = list;
        return listTemp;
    }

    /**
     * TODO ??????fragment????????? canSign:(). <br/>
     * date: 2015???1???27??? <br/>
     *
     * @throws HDException
     * @author wenlin
     */
    @SuppressWarnings("unchecked")
    public void canSign(Class<? extends NaviFrameFragment> resultClz,
                        int controlType) throws HDException {
        List<SuperEntity> lstAPP = null;
        if (!hasmapTab.containsKey(controlType)) {
            // try {
            lstAPP = getListAppInfo(controlType);
            // } catch (HDException e) {
            // logger.error(e.getMessage(), e);
            // ToastUtils.toast(WorkOrderBaseActivity.this, e.getMessage());
            // return;
            // }
            hasmapTab.put(controlType, lstAPP);
        } else {
            lstAPP = (List<SuperEntity>) hasmapTab.get(controlType);
        }
        List<Object> data = new ArrayList<Object>();
        data.add(lstAPP);

        if (isJoin) {
            // ????????????
            data.add(workOrderList);
        } else {
            data.add(workOrder);
        }
        // ??????????????????????????????2017/7/25,??????????????????????????????????????????????????????
        boolean flag = false;
        for (SuperEntity superEntity : lstAPP) {
            WorkApprovalPermission permission = (WorkApprovalPermission) superEntity;
            if (permission.getIs_spot_final_person() == 1
                    && permission.getPersondesc() != null
                    && !"".equals(permission.getPersondesc())) {
                flag = true;
            } else if (permission.getIs_spot_final_person() == 1
                    && (permission.getPersondesc() == null
                    || "".equals(permission.getPersondesc()))) {
                flag = false;
                break;
            }
        }
        if (flag) {
            if (!getIntent().getBooleanExtra("isJoin", false)) {
                setCustomMenuBar(new String[]{IActionBar.ITEM_WORKSCAN,
                        IActionBar.ITEM_RETURN, IActionBar.ITEM_CANNEL,
                        IActionBar.ITEM_PHOTOGTAPH,
                        IActionBar.ITEM_PHOTOMANAGER,
                        IActionBar.ITEM_UPWORKORDER, IActionBar.REMOTE_APPR});
            } else {
                setCustomMenuBar(new String[]{IActionBar.ITEM_PHOTOGTAPH,
                        IActionBar.ITEM_PHOTOMANAGER,
                        IActionBar.ITEM_UPWORKORDER, IActionBar.REMOTE_APPR});
            }
        }
        switchContentView(resultClz, data);
    }

    /**
     * getSuperControlType:(??????????????????). <br/>
     * date: 2014???10???23??? <br/>
     *
     * @param sup
     * @return
     * @author lxf
     */
    public int getSuperControlType(SuperEntity sup) {
        int ret = -2;
        if (null != sup) {
            ret = (int) sup.getAttribute("contype");
        }
        return ret;
    }

    public void actionShowDialog(String msg, String actiontype, Object... param) {
        // if(prgDialog==null){
        prgDialog = new ProgressDialog(WorkOrderBaseActivity.this, msg);
        prgDialog.show();
        try {
            asyTask.execute(actiontype, param);
        } catch (HDException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * TODO ????????????????????????????????????????????? successActionCallback:(). <br/>
     * date: 2015???3???9??? <br/>
     *
     * @param msgData
     * @author wenlin
     */
    public void successActionCallback(Bundle msgData) {

    }

    /**
     * actionShowDialogDissmiss:(???????????????). <br/>
     * date: 2015???2???9??? <br/>
     *
     * @author lxf
     */
    public void actionShowDialogDissmiss() {
        if (prgDialog != null) {
            prgDialog.dismiss();
        }
    }

    private AbstractAsyncCallBack asyCallBack = new AbstractAsyncCallBack() {

        @Override
        public void start(Bundle msgData) {
            // TODO Auto-generated method stub
        }

        @Override
        public void processing(Bundle msgData) {
            // TODO Auto-generated method stub

        }

        @Override
        public void error(Bundle msgData) {
            prgDialog.dismiss();
            // ??????
            if (msgData.containsKey(IActionType.ACTION_TYPE_RETURN)) {
                ToastUtils.imgToast(WorkOrderBaseActivity.this,
                        R.drawable.hd_hse_common_msg_wrong,
                        msgData.getString(IActionType.ACTION_TYPE_RETURN));
            }
            if (msgData.containsKey(IActionType.ACTION_TYPE_CANNULLIFY)) {
                ToastUtils.imgToast(WorkOrderBaseActivity.this,
                        R.drawable.hd_hse_common_msg_wrong,
                        msgData.getString(IActionType.ACTION_TYPE_CANNULLIFY));
            }
            // ??????
            if (msgData.containsKey(IActionType.ACTION_TYPE_CANSIGN)) {
                ToastUtils.imgToast(WorkOrderBaseActivity.this,
                        R.drawable.hd_hse_common_msg_wrong,
                        msgData.getString(IActionType.ACTION_TYPE_CANSIGN));
                try {
                    // NAVI?????????????????????
                    setNaviCurrentEntity(priviousNaviTouchEntity);
                    // ?????????fragment
                    getFragmentByPDAConfig((PDAWorkOrderInfoConfig) priviousNaviTouchEntity);

                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

        @Override
        public void end(Bundle msgData) {
            prgDialog.dismiss();

            successActionCallback(msgData);
            // ??????
            if (msgData.containsKey(IActionType.ACTION_TYPE_RETURN)) {
                // ?????????????????????
                upWorkOrder();
            }
            // ????????????????????????
            if (msgData.containsKey(IActionType.ACTION_TYPE_CANSIGN)) {
                try {
                    // ??????fragment
                    canSign(resultClz, IConfigEncoding.SIGN_TYPE);
                } catch (HDException e) {
                    logger.error(e.getMessage(), e);
                    ToastUtils.imgToast(WorkOrderBaseActivity.this,
                            R.drawable.hd_hse_common_msg_wrong, e.getMessage());
                }
            }
        }
    };

    /**
     * ????????????????????????????????????????????????????????????
     */
    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        return false;
    }

    ;

    /**
     * getActionListener:(???????????????). <br/>
     * date: 2015???11???25??? <br/>
     *
     * @return
     * @author lxf
     */
    public AbstractActionListener getActionListener() {
        return null;
    }

    public void upWorkOrder() {
        // TODO Auto-generated method stub

    }

}
