package com.hd.hse.ss.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.PhoneEventType;
import com.hd.hse.common.module.phone.custom.StateDownloadZYPExpandableListView;
import com.hd.hse.common.module.phone.ui.activity.LocationSwCard;
import com.hd.hse.common.module.phone.ui.activity.SystemApplication;
import com.hd.hse.common.module.phone.ui.activity.WorkOrderDetailActivity;
import com.hd.hse.common.module.phone.ui.module.activity.BaseListBusActivity;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.entity.workorder.WorkTask;
import com.hd.hse.service.workorder.WorkTaskDBSrv;
import com.hd.hse.ss.R;
import com.hd.hse.ss.WorkTaskSrv;
import com.hd.hse.ss.business.workorder.ServerWorkTaskSrv;
import com.hd.hse.system.SystemProperty;
import com.hd.hse.utils.UtilTools;
import com.hd.hse.utils.WorkOrderUtilTools;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: TaskTabulationActivity ()<br/>
 * date: 2015年1月27日 <br/>
 *
 * @author lych
 */
public class TaskTabulationActivity extends BaseListBusActivity {
    /**
     * down:TODO(获取数据源类).
     */
    private ServerWorkTaskSrv workorderdown = null;
    /**
     * downzyp:TODO(下载作业票进度条).
     */
    private static final String UD_ZYXK_ZYSQID = "ud_zyxk_zysqid";
    private List<SuperEntity> refreshListData;
    //private DownZYPProgressDiaolog downzyp = null;
    private StateDownloadZYPExpandableListView selectable;
    private List<SuperEntity> dataList;
    //作业票所在list任务位置
    private int dataPosition = -1;
    private String sddept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initPrarm();
        super.onCreate(savedInstanceState);
//		initData();
//		initView();
    }

    /**
     * initView:(). <br/>
     * date: 2015年3月4日 <br/>
     *
     * @author lxf
     */
    @Override
    public void initView() {
        setContentView(selectable);
        // 初始化抽屉和ActionBar
        setCustomActionBar(false, eventLst, new String[]{IActionBar.TV_BACK,
                IActionBar.TV_TITLE, IActionBar.IBTN_SEARCH}); // 设置导航栏标题
        setActionBartitleContent("作业票浏览", false);
        // 设置左侧模块选择抽屉
//		setNavContent(
//				SystemProperty.getSystemProperty().getMainAppModulelist("SJ"),getNavCurrentKey());
    }

    /**
     * initPrarm:(初始化参数). <br/>
     * date: 2015年3月4日 <br/>
     *
     * @author lxf
     */
    private void initPrarm() {
        selectable = new StateDownloadZYPExpandableListView(this);
        selectable.setOnEventListener(eventLst);
        workorderdown = new ServerWorkTaskSrv(this);
        workorderdown.setGetDataSourceListener(eventLst);
        Intent intent = getIntent();
        if (intent != null) {
            sddept = intent.getStringExtra(SupervisionDetailActivity.class.getClass().getName());
        }
//		downzyp = new DownZYPProgressDiaolog(this);
//		downzyp.setGetDataSourceListener(eventLst);
    }

    /**
     * initData:(获取数据源). <br/>
     * date: 2015年3月4日 <br/>
     *
     * @author lxf
     */
    @Override
    public void initData() {
        if (workorderdown != null) {
            workorderdown.geteDownLoadWorkList(getSearchStr());// 异步查询数据
        }

    }

    IEventListener eventLst = new IEventListener() {
        @SuppressWarnings("unchecked")
        public void eventProcess(int eventType, Object... objects)
                throws HDException {
            switch (eventType) {
                case IEventType.DOWN_WORK_LIST_SHOW:

                    List<SuperEntity> serverWorkTask = null;
                    if (objects[0] instanceof List<?>) {
                        // setDataList((List<SuperEntity>) objects[0]);
                        serverWorkTask = (List<SuperEntity>) objects[0];
                    }
                    // 显示下载列表
                    if (serverWorkTask != null) {
                        // 获取本地WorkOrder
                        List<SuperEntity> localListTask = new WorkTaskSrv()
                                .loadWorkTaskList(getSearchStr());
                        // 比较WorkOrder
                        dataList = WorkOrderUtilTools
                                .compareWorkOrder(serverWorkTask, localListTask);
                        //排序
                        setDataOrder();
                        //作业票按照属地单位过滤
                        if (sddept != null && !sddept.equals("")) {
                            if (dataList != null) {
                                for (int i = 0; i < dataList.size(); i++) {
                                    WorkTask workTask = (WorkTask) dataList.get(i);
                                    String workTaskSddept = workTask.getSddept();
                                    if (!workTaskSddept.equals(sddept)) {
                                        dataList.remove(workTask);
                                        i--;
                                    }
                                    workTask.setGl("gl");
                                }
                            }
                        }
                        selectable.setData(dataList);
//                        if (dataList != null) {
//                            if (dataList.size() == 0) {
//                                Toast.makeText(TaskTabulationActivity.this, "按照属地单位过滤后没有作业票", Toast.LENGTH_SHORT).show();
//                            }
//                        }

                    }
                    break;
                case IEventType.ACTIONBAR_SEARCH_CLICK:
                    // 表示搜索
                    if (objects[0] instanceof String) {
                        setSearchStr((String) objects[0]);
                        initData();
                    }
                    break;
                case PhoneEventType.ZYPLIST_ITEM_CLICK:
                    dataPosition = (Integer) objects[1];
                    Intent intent = new Intent();
//				intent.setClass(TaskTabulationActivity.this,
//						WorkOrderDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(WorkOrder.class.getName(),
                            (WorkOrder) objects[0]);
                    intent.putExtras(bundle);
//				startActivityForResult(intent, WorkOrderDetailActivity.REQUESTCODE);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    break;
            }
        }
    };
    private final String zystarttimeKey = "zystarttime";

    /**
     * setDataOrder:(设置数据排序). <br/>
     * date: 2015年2月28日 <br/>
     *
     * @author lxf
     */
    private void setDataOrder() {
        if (dataList == null)
            return;
        String[] workOderStr = getResources().getStringArray(
                R.array.hd_hse_main_app_workorder_order);
        if (null == workOderStr || workOderStr.length <= 1) {
            return;
        }
        int count = dataList.size();
        List<SuperEntity> listchild = null;
        SuperEntity mainSup = null;
        for (int i = 0; i < count; i++) {
            mainSup = dataList.get(i);
            listchild = mainSup.getChild(WorkOrder.class.getName());
            if (null == listchild || listchild.size() <= 1) {
                // 表示内容为1时不需要排序
                continue;
            }
            List<SuperEntity> listChildOrder = new ArrayList<SuperEntity>();
            String subSuperType = null;
            // 循环票的类型
            for (String zypOrder : workOderStr) {
                // 查找子类信息
                for (SuperEntity subSuper : listchild) {
                    subSuperType = subSuper.getAttribute("zypclass") == null ? ""
                            : subSuper.getAttribute("zypclass").toString();
                    if (zypOrder.equalsIgnoreCase(subSuperType)) {
                        listChildOrder.add(subSuper);
                        listchild.remove(subSuper);
                        break;
                    }
                }
            }
            // 判断没有排序上的添加在后边
            if (listchild.size() > 0) {
                listChildOrder.addAll(listchild);
            }
            // 清除原来的KEY-Value
            mainSup.getChilds().remove(WorkOrder.class.getName());
            // 重新复制
            mainSup.setChild(WorkOrder.class.getName(), listChildOrder);
            // dataList.get(i).setChild(entityName, lstChild);
        }
        bubble_sort(dataList);
    }

    /**
     * bubble_sort:(按照开始时间倒序). <br/>
     * date: 2014年11月26日 <br/>
     *
     * @param unsorted
     * @author lxf
     */
    private void bubble_sort(List<SuperEntity> unsorted) {
        if (unsorted == null || unsorted.size() == 1) {
            return;
        }
        int len = unsorted.size();
        for (int i = 0; i < len; i++) {
            for (int j = i; j < len; j++) {
                try {
                    // 表示第一个大
                    if (!UtilTools.dataTimeCompare(unsorted.get(i)
                            .getAttribute(zystarttimeKey).toString(), unsorted
                            .get(j).getAttribute(zystarttimeKey).toString())) {
                        SuperEntity temp = unsorted.get(i);
                        unsorted.set(i, unsorted.get(j));
                        unsorted.set(j, temp);
                    }
                } catch (HDException e) {
                    ToastUtils.toast(getBaseContext(), "作业开始时间比较报错,请联系管理员");
                }
            }
        }
    }

    /**
     * 去除缓存中已退回作业票
     *
     * @param workOrder
     */
    public void refreshDatas(SuperEntity workOrder) {
        if (workOrder == null && dataPosition != -1)
            return;
        refreshListData = dataList.get(dataPosition).getChild(WorkOrder.class.getName());
        for (SuperEntity subEntity : refreshListData) {
            if (workOrder.getAttribute(UD_ZYXK_ZYSQID).equals(subEntity.getAttribute(UD_ZYXK_ZYSQID))) {
                refreshListData.remove(subEntity);
                break;
            }
        }
        //如果任务下没有作业票则删除任务
        if (refreshListData.size() < 1) {
            dataList.remove(dataPosition);
        } else {
            dataList.get(dataPosition).getChilds().remove(WorkOrder.class.getName());
            dataList.get(dataPosition).setChild(WorkOrder.class.getName(), refreshListData);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == WorkOrderDetailActivity.REQUESTCODE && data != null) {
            refreshDatas((SuperEntity) data.getSerializableExtra(WorkOrderDetailActivity.WORK_ORDER));
            if (selectable != null && dataList != null)
                selectable.setData(dataList);
        }
    }

    @Override
    public WorkTaskDBSrv getWorkTaskObject() {
        // TODO Auto-generated method stub
        return new WorkTaskSrv();
    }

    @Override
    public String getTitileName() {
        // TODO Auto-generated method stub
        return "选择作业票";
    }

    @Override
    public String getNavCurrentKey() {
        // TODO Auto-generated method stub
        return "hse-wov-phone-app";
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            SystemApplication.getInstance().popActivity();
            if (LocationSwCard.mTimer != null) {
                LocationSwCard.mTimer.cancel();
                LocationSwCard.mTimer = null;
            }
            // 清空刷卡位置
            SystemProperty.getSystemProperty().setPositionCard(null);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
