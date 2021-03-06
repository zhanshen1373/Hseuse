package com.hd.hse.common.module.phone.ui.module.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.MessageDialog;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.PhoneEventType;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.custom.NormalZYPExpandableListView.OnZYPItemClickListener;
import com.hd.hse.common.module.phone.ui.activity.LocationSwCard;
import com.hd.hse.common.module.phone.ui.activity.SystemApplication;
import com.hd.hse.common.module.phone.ui.module.menu.ActionBarMenu;
import com.hd.hse.constant.IActionType;
import com.hd.hse.dc.business.common.weblistener.MessageEvent;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.sys.AppModule;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.WorkTaskDBSrv;
import com.hd.hse.service.workorder.checkrules.CheckControllerActionListener;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.system.SystemProperty;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseListBusActivity extends BaseListActivity {
    public final static int PAUSE_SUCCESS_CODE = 0x1546;
    private static Logger logger = LogUtils
            .getLogger(BaseListBusActivity.class);

    /**
     * ??????????????????????????????????????????Activity ????????????
     */
    public boolean hasCalledStartActivity;
    // ?????????????????????
    private IQueryWorkInfo quertWorkInfo = new QueryWorkInfo();
    private ActionBarMenu actionBarMenu;

    public List<SuperEntity> listEntity = null;

    @Override
    protected void initData() {
        // WorkTaskSrv wts = new WorkTaskSrv();
        WorkTaskDBSrv wts = getWorkTaskObject();
        if (wts == null) {
            ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                    "???????????????getWorkTaskObject()??????");
            return;
        }
//        List<SuperEntity> listEntity = new ArrayList<SuperEntity>();
        listEntity = new ArrayList<SuperEntity>();
        try {
            listEntity = wts.loadWorkTaskList(getSearchStr());
            if (listEntity.size() < 1 || listEntity == null) {
                ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                        "??????????????????");
            }
            // ???????????????,getSearchStr()???????????????
            setDataList(listEntity);
        } catch (HDException e) {
            // TODO Auto-generated catch block
            ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                    e.getMessage());
        }
        // 2015-08-26 lxf ??????????????????setDataList??????????????????
        // super.initData();
    }

    public ActionBarMenu getActionBarMenu() {
        return actionBarMenu;
    }

    @Override
    protected void initView() {
        super.initView();
        // ???????????????item????????????
        setItemClickListener(itemLsn);
        // ?????????????????????item????????????
        setiEventLsn(eventListener);
        // ?????????????????????
        setCustomActionBar(true, eventListener, setActionBarItems());
        // ?????????????????????
        setActionBartitleContent(getTitileName(), false);
        // ??????????????????????????????
        setNavContent(getNavContentData(), getNavCurrentKey());
        //??????????????????
        setActionBarMenu(getActionBarMenus());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * ??????????????????????????????????????????setActionBarMenu??????
     * ????????????menus
     *
     * @param messageEvent
     */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getMessageType() == MessageEvent.APP_MODULE_TYPE) {
            setActionBarMenu(getActionBarMenus());
        }
    }

    public void setActionBarMenu(String[] menus) {
        if (menus != null)
            actionBarMenu = new ActionBarMenu(this, menus, eventListener);
    }

    /**
     * ???????????????????????????
     *
     * @return
     */

    public String[] getActionBarMenus() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        hasCalledStartActivity = false;
    }

    public String[] setActionBarItems() {
        return new String[]{IActionBar.IV_LMORE, IActionBar.IBTN_SEARCH, IActionBar.TV_TITLE};
    }

    /**
     * ???????????????????????????????????????????????????
     *
     * @see android.app.Activity#onActivityResult(int, int,
     * android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // ??????????????????
        if (resultCode == RESULT_OK || resultCode == PAUSE_SUCCESS_CODE) {
            initData();
        }
        // else if (getSearchStr() != null || !getSearchStr().equals("")) {
        // setSearchStr("");
        // initData();
        // }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * getWorkTaskObject:(?????????????????????). <br/>
     * date: 2015???2???6??? <br/>
     *
     * @return
     * @author lxf
     */
    public WorkTaskDBSrv getWorkTaskObject() {
        return null;
    }

    /**
     * getTitileName:(??????????????????). <br/>
     * date: 2015???2???6??? <br/>
     *
     * @return
     * @author lxf
     */
    public String getTitileName() {
        return "";
    }

    /**
     * getNavContentData:(??????????????????). <br/>
     * date: 2015???2???6??? <br/>
     *
     * @return
     * @author lxf
     */
    public List<AppModule> getNavContentData() {
        return SystemProperty.getSystemProperty().getMainAppModulelist("SJ");
    }

    /**
     * getNavCurrentKey:(??????????????????KEY). <br/>
     * date: 2015???2???6??? <br/>
     *
     * @return
     * @author lxf
     */
    public String getNavCurrentKey() {
        return null;
    }

    /**
     * getToActivityClass:(??????????????????Activity???). <br/>
     * date: 2015???2???6??? <br/>
     *
     * @return
     * @author lxf
     */
    public Class<?> getToActivityClass() {
        return null;
    }

    /**
     * getFunctionCode:(). <br/>
     * date: 2015???2???6??? <br/>
     *
     * @return
     * @author lxf
     */
    public String getFunctionCode() {
        return null;
    }

    /**
     * eventListener:TODO().
     */
    private IEventListener eventListener = new IEventListener() {

        @Override
        public void eventProcess(int eventType, Object... objects)
                throws HDException {
            // TODO Auto-generated method stub
            if (eventType == IEventType.WORK_LIST_CLICK) {
                if (objects[0] instanceof SuperEntity) {
                    startNextActivity((SuperEntity) objects[0]);
                }
            } else if (eventType == IEventType.ACTIONBAR_SEARCH_CLICK) {
                if (objects[0] instanceof String) {
                    setSearchStr((String) objects[0]);
                    initData();
                }
            } else if (IEventType.ACTIONBAR_CALNEL_CLICK == eventType) {
                setSearchStr("");
                initData();
            } else if (PhoneEventType.ZYPLIST_ITEM_LONG_CLICK == eventType) {
                // ?????????????????????
                WorkOrder mWorkOrder = quertWorkInfo.querySiteAuditBasicInfo(
                        (SuperEntity) objects[0], getFunctionCode(), null);
                // ???????????????????????????
                showWorkOrderPopupWin(mWorkOrder);
            } else if (eventType == IEventType.ACTIONBAR_JOIN_BTN_CLICK) {
                // ??????????????????
                showActionbarJoinStstus();
                setIsChoose(true);
            } else if (eventType == IEventType.ACTIONBAR_JOIN_OK_CLICK) {
                // ????????????????????????
                final List<SuperEntity> list = mExpandableListView
                        .getChoicedEntity();
                if (list != null && list.size() != 0) {
                    //??????????????????
                    final String actionType = IActionType.ACTION_TYPE_CANMERGESIGN;
                    final ProgressDialog dialog = new ProgressDialog(
                            BaseListBusActivity.this, "??????????????????");
                    BusinessAction action = new BusinessAction(
                            new CheckControllerActionListener());
                    BusinessAsyncTask asyncTask = new BusinessAsyncTask(action,
                            new AbstractAsyncCallBack() {

                                @Override
                                public void start(Bundle msgData) {

                                }

                                @Override
                                public void processing(Bundle msgData) {
                                    if (msgData.containsKey(actionType)) {
                                        dialog.showMsg(msgData
                                                .getString(actionType));
                                    }
                                }

                                @Override
                                public void error(Bundle msgData) {
                                    if (msgData.containsKey(actionType)) {
                                        dialog.dismiss();
                                        ToastUtils
                                                .imgToast(
                                                        BaseListBusActivity.this,
                                                        R.drawable.hd_hse_common_msg_wrong,
                                                        msgData.getString(actionType));
                                    }
                                }

                                @Override
                                public void end(Bundle msgData) {
                                    dialog.dismiss();
                                    startJoinActivity(list);
                                }
                            });
                    Map<String, Object> mapParam = new HashMap<String, Object>();
                    mapParam.put(WorkOrder.class.getName(), list);
                    asyncTask.execute(actionType, mapParam);
                    dismissActionbarJoinStstus();
                    setIsChoose(false);
                } else {
                    ToastUtils.toast(BaseListBusActivity.this, "??????????????????????????????");
                }
            } else if (eventType == IEventType.ACTIONBAR_JOIN_CANCEL_CLICK) {
                // ????????????????????????
                setIsChoose(false);
            } else if (eventType == IEventType.ACTIONBAR_MORE_CLICK) {
                if (actionBarMenu != null)
                    actionBarMenu.showAsDropDown((View) objects[0]);
            }
        }
    };
    private OnZYPItemClickListener itemLsn = new OnZYPItemClickListener() {

        @Override
        public void onClick(WorkOrder workOrder1, View anchorView,
                            int pointerHorizontalPosition) {
            startNextActivity(workOrder1);
        }

    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            setHomeAction();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * startCSActivityForResult:(????????????). <br/>
     * date: 2015???3???10??? <br/>
     *
     * @param workOrder
     * @author lxf
     */
    public void startCSActivityForResult(WorkOrder workOrder) {
        startNextActivity(workOrder);
    }

    private void startJoinActivity(List<SuperEntity> list) {
        if (hasCalledStartActivity) {
            return;
        }
        hasCalledStartActivity = true;
        if (getToActivityClass() == null) {
            return;
        }
        if (StringUtils.isEmpty(getFunctionCode())) {
            return;
        }
        Intent intent = new Intent(this, getToActivityClass());
        Bundle bundle = new Bundle();
        try {
            WorkOrder mWorkOrder;
            mWorkOrder = quertWorkInfo.querySiteAuditSignInfo(list.get(0),
                    getFunctionCode(), null);
            workOrder = mWorkOrder;

            List<SuperEntity> listPDAConfigInfo = workOrder
                    .getChild(PDAWorkOrderInfoConfig.class.getName());
            // lxf
            if (listPDAConfigInfo == null || listPDAConfigInfo.size() == 0) {
                ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                        "?????????????????????????????????");
                hasCalledStartActivity = false;
                return;
            }
            bundle.putSerializable("workOrder", workOrder);
            bundle.putSerializable("workOrderList", (Serializable) list);

            bundle.putBoolean("isJoin", true);
            intent.putExtras(bundle);
            startActivityForResult(intent, IEventType.WORKORDER_AUDIT);
        } catch (HDException e) {
            hasCalledStartActivity = false;
            logger.error(e.getMessage());
            ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                    e.getMessage());
        }
    }

    /**
     * TODO ?????????????????????????????? startNextActivity:(). date: 2015???02???06???
     * <p>
     * superEctity ?????????????????????
     *
     * @throws HDException
     * @author lxf
     */
    private void startNextActivity(SuperEntity superEntity) {
        if (hasCalledStartActivity) {
            return;
        }
        hasCalledStartActivity = true;
        if (getToActivityClass() == null) {
            return;
        }
        if (StringUtils.isEmpty(getFunctionCode())) {
            return;
        }
        Intent intent = new Intent(this, getToActivityClass());
        Bundle bundle = new Bundle();
        try {
            WorkOrder mWorkOrder;
            // ???????????????
            mWorkOrder = quertWorkInfo.querySiteAuditBasicInfo(superEntity,
                    getFunctionCode(), null);
            workOrder = mWorkOrder;

            @SuppressWarnings("unused")
            List<SuperEntity> listPDAConfigInfo = workOrder
                    .getChild(PDAWorkOrderInfoConfig.class.getName());
            // lxf
            if (listPDAConfigInfo == null || listPDAConfigInfo.size() == 0) {
                ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                        "?????????????????????????????????");
                hasCalledStartActivity = false;
                return;
            }
            bundle.putSerializable("workOrder", workOrder);
            intent.putExtras(bundle);
            startActivityForResult(intent, IEventType.WORKORDER_AUDIT);
        } catch (HDException e) {
            hasCalledStartActivity = false;
            logger.error(e.getMessage());
            ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                    e.getMessage());
        }
    }

    /**
     * setHomeAction:(????????????????????????). <br/>
     * date: 2015???2???6??? <br/>
     *
     * @author lxf
     */
    private void setHomeAction() {
        // ???????????????
        // if (this.getDataList() == null || this.getDataList().size() == 0) {
        // SystemApplication.getInstance().popActivity();
        // return;
        // }
        // ????????????????????????
        MessageDialog.Builder builder = new MessageDialog.Builder(this);
        builder.setTitle("??????");
        builder.setMessage("??????????????????????????????");
        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SystemApplication.getInstance().popActivity();
                dialog.dismiss();
                // ????????????????????????
                if (LocationSwCard.mTimer != null) {
                    LocationSwCard.mTimer.cancel();
                    LocationSwCard.mTimer = null;
                }
                // ??????????????????
                SystemProperty.getSystemProperty().setPositionCard(null);
            }
        });
        builder.setNegativeButton("??????",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.createWarm().show();
    }
}
