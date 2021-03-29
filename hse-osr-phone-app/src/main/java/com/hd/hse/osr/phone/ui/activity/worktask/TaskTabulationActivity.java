package com.hd.hse.osr.phone.ui.activity.worktask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.custom.NormalZYPExpandableListView.OnZYPItemClickListener;
import com.hd.hse.common.module.phone.custom.PopWinButton;
import com.hd.hse.common.module.phone.ui.module.activity.BaseListBusActivity;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.osr.business.workorder.WorkTaskSrv;
import com.hd.hse.osr.phone.R;
import com.hd.hse.osr.phone.ui.activity.workorder.WorkOrderActivity;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.service.workorder.WorkTaskDBSrv;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TaskTabulationActivity extends BaseListBusActivity {

    private PopWinButton[] mPopWinButton;
    private boolean isrelation = false;
    private static Logger logger = LogUtils
            .getLogger(TaskTabulationActivity.class);
    public static final String QTJC_FC = "QTJC_FC";

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        popWindowsMiss();
    }

    @Override
    public String[] setActionBarItems() {
        return new String[]{IActionBar.IV_LMORE, IActionBar.IBTN_SEARCH, IActionBar.TV_TITLE};
    }

    @Override
    protected void initView() {
        // TODO Auto-generated method stub
        super.initView();
        IQueryRelativeConfig relation = new QueryRelativeConfig();
        RelationTableName relationEntity = new RelationTableName();
        relationEntity.setSys_type(IRelativeEncoding.PBQTJCGN);
        relationEntity.setSys_fuction(IConfigEncoding.FC);

        mPopWinButton = new PopWinButton[2];
        String[] strBtn = new String[]{"措施复查", "气体检测"};
        int[] strEventType = new int[]{IEventType.ACTION_CSQR_FC,
                IEventType.ACTION_QTJC_FC};
        int[] strDrawable = new int[]{
                R.drawable.hd_hse_common_module_pop_btn,
                R.drawable.hd_hse_common_module_pop_btn};
        for (int i = 0; i < 2; i++) {
            mPopWinButton[i] = new PopWinButton();
            mPopWinButton[i].eventType = strEventType[i];
            mPopWinButton[i].text = strBtn[i];
            mPopWinButton[i].drawableId = strDrawable[i];
        }
        // 判断是否屏蔽气体检测功能
        isrelation = relation.isHadRelative(relationEntity);
        if (!isrelation) {
            initPopWindows(mPopWinButton, iEventListener);
        }
        // 作业票列表item点击监听
        setItemClickListener(itemLsn);
    }

    @Override
    protected void initData() {
        super.initData();
        //复查页面 因为带压堵漏票没有措施，把这个票去掉
        for (int j = 0; j < listEntity.size(); j++) {
            List<SuperEntity> workOrderArrayList = listEntity.get(j).
                    getChilds().get("com.hd.hse.entity.workorder.WorkOrder");
            for (int k = 0; k < workOrderArrayList.size(); k++) {
                WorkOrder workOrder = (WorkOrder) workOrderArrayList.get(k);
                if (workOrder.getZypclass().equals("zylx89")) {
                    workOrderArrayList.remove(k);
                }
            }
        }
        setDataList(listEntity);
    }

    @Override
    public WorkTaskDBSrv getWorkTaskObject() {
        // TODO Auto-generated method stub
        return new WorkTaskSrv();
    }

    @Override
    public String getTitileName() {
        // TODO Auto-generated method stub
        return "现场复查";
    }

    @Override
    public String getNavCurrentKey() {
        // TODO Auto-generated method stub
        return "hse-osr-phone-app";
    }

    @Override
    public Class<?> getToActivityClass() {
        // TODO Auto-generated method stub
        return WorkOrderActivity.class;
    }

    @Override
    public String getFunctionCode() {
        // TODO Auto-generated method stub
        return IConfigEncoding.FC;
    }

    private OnZYPItemClickListener itemLsn = new OnZYPItemClickListener() {

        @Override
        public void onClick(WorkOrder workOrder1, View anchorView,
                            int pointerHorizontalPosition) {
            int isQtjc = workOrder1.getIsqtjc() == null
                    || workOrder1.getIsqtjc() == 0 ? 0 : 1;
            if (isQtjc == 1 && !isrelation) {
                showNavPopWindows(workOrder1, anchorView,
                        pointerHorizontalPosition);
            } else {
                startNextAct(workOrder1);
            }
        }

    };
    IQueryWorkInfo quertWorkInfo = new QueryWorkInfo();
    /**
     * eventListener:TODO().
     */
    private IEventListener iEventListener = new IEventListener() {
        @Override
        public void eventProcess(int eventType, Object... objects) {
            switch (eventType) {
                case IEventType.ACTION_QTJC_FC:
                    Intent intent = new Intent(getApplicationContext(),
                            WorkOrderActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(QTJC_FC, QTJC_FC);
                    PDAWorkOrderInfoConfig workInfoConfig = new PDAWorkOrderInfoConfig();
                    workInfoConfig.setPscode(IConfigEncoding.FC);
                    workInfoConfig.setContype(IConfigEncoding.GAS_TYPE);
                    workInfoConfig.setDycode(IConfigEncoding.FC_GAS_NUM);
                    workInfoConfig.setSname("气体检测");
                    workInfoConfig.setContypedesc("气体检测");
                    WorkOrder mWorkOrder;
                    try {
                        mWorkOrder = quertWorkInfo.querySiteAuditBasicInfo(
                                (WorkOrder) objects[0], IConfigEncoding.FC, null);
                        workOrder = mWorkOrder;
                        List<SuperEntity> listPDAConfigInfo = new ArrayList<SuperEntity>();
                        listPDAConfigInfo.add(workInfoConfig);
                        workInfoConfig.setChild(
                                PDAWorkOrderInfoConfig.class.getName(),
                                listPDAConfigInfo);
                        workOrder
                                .clearChild(PDAWorkOrderInfoConfig.class.getName());
                        workOrder.setChild(PDAWorkOrderInfoConfig.class.getName(),
                                listPDAConfigInfo);

                        bundle.putSerializable("workOrder", workOrder);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, IEventType.WORKORDER_AUDIT);

                        overridePendingTransition(R.anim.hd_hse_common_zoomin,
                                R.anim.hd_hse_common_zoomout); // Activity切换动画
                    } catch (HDException e) {
                        logger.error(e.getMessage());
                        ToastUtils.imgToast(getApplicationContext(),
                                R.drawable.hd_hse_common_msg_wrong, "查询失败，请联系管理员！");
                    }
                    break;
                case IEventType.ACTION_CSQR_FC:
                    startNextAct((WorkOrder) objects[0]);
                    // if (objects[0] instanceof WorkOrder) {
                    // startCSActivityForResult((WorkOrder) objects[0]);
                    // }

                    break;
            }
        }
    };

    /**
     * startNextAct:(启动窗体). <br/>
     * date: 2015年3月11日 <br/>
     *
     * @param workOrder
     * @author lxf
     */
    private void startNextAct(WorkOrder workOrder) {
        if (hasCalledStartActivity) {
            return;
        }
        hasCalledStartActivity = true;
        Intent intentfc = new Intent(getApplicationContext(),
                WorkOrderActivity.class);
        Bundle bundlefc = new Bundle();
        try {
            WorkOrder mWorkOrderfc = quertWorkInfo.querySiteAuditBasicInfo(
                    workOrder, getFunctionCode(), null);
            workOrder = mWorkOrderfc;
            List<SuperEntity> listPDAConfigInfo = workOrder
                    .getChild(PDAWorkOrderInfoConfig.class.getName());
            // lxf 移除气体检测
            for (SuperEntity padconfig : listPDAConfigInfo) {
                if (padconfig instanceof PDAWorkOrderInfoConfig) {
                    if (((PDAWorkOrderInfoConfig) padconfig).getDycode()
                            .equalsIgnoreCase(IConfigEncoding.FC_GAS_NUM)) {
                        listPDAConfigInfo.remove(padconfig);
                        break;
                    }
                }
            }
            // lxf
            if (listPDAConfigInfo == null || listPDAConfigInfo.size() == 0) {
                ToastUtils.imgToast(getApplicationContext(),
                        R.drawable.hd_hse_common_msg_wrong, "没有配置详细信息页面。");
                hasCalledStartActivity = false;
                return;
            }
            bundlefc.putSerializable("workOrder", workOrder);
            intentfc.putExtras(bundlefc);
            startActivityForResult(intentfc, IEventType.WORKORDER_AUDIT);
            overridePendingTransition(R.anim.hd_hse_common_zoomin,
                    R.anim.hd_hse_common_zoomout); // Activity切换动画
        } catch (HDException e) {
            hasCalledStartActivity = false;
            logger.error(e.getMessage());
            ToastUtils.imgToast(getApplicationContext(),
                    R.drawable.hd_hse_common_msg_wrong, e.getMessage());
        }
    }
}
