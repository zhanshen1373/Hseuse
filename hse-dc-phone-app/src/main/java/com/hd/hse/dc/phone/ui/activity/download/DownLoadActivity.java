/**
 * Project Name:hse-dc-app
 * File Name:DownLoadActivity.java
 * Package Name:com.hd.hse.dc.ui.activity.list
 * Date:2014年10月10日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 */
package com.hd.hse.dc.phone.ui.activity.download;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.MessageDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.PhoneEventType;
import com.hd.hse.common.module.phone.custom.SelectableZYPExpandableListView;
import com.hd.hse.common.module.phone.custom.StateDownloadSelectableZYPExpandableListView;
import com.hd.hse.common.module.phone.ui.activity.BaseFrameActivity;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.dc.phone.R;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.entity.workorder.WorkTask;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.system.SystemProperty;
import com.hd.hse.utils.UtilTools;
import com.hd.hse.utils.WorkOrderUtilTools;

/**
 * ClassName: DownLoadActivity ()<br/>
 * date: 2014年10月10日 <br/>
 *
 * @author zhaofeng
 * @version
 */
public class DownLoadActivity extends BaseFrameActivity {

    /**
     * down:TODO(获取数据源类).
     */
    DownLoadWorkTaskSrv down = null;
    /**
     * downzyp:TODO(下载作业票进度条).
     */
    DownZYPProgressDiaolog downzyp = null;

    private SelectableZYPExpandableListView selectable;
    private List<SuperEntity> dataList;
    List<SuperEntity> checkedList;
    private int initDHGX = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        selectable = new StateDownloadSelectableZYPExpandableListView(this);
        selectable.setOnEventListener(eventListener);
        setCustomActionBar(true, eventListener, new String[]{
                IActionBar.IV_LMORE, IActionBar.TV_TITLE,
                IActionBar.IBTN_SEARCH, IActionBar.IBTN_DOWNLOAD});
        // 设置不可见
        setHSEActionBarInVisible(new String[]{IActionBar.IBTN_DOWNLOAD});
        iniParam();
        initData();
        // 设置导航栏标题
        setActionBartitleContent("作业票下载", false);
        // 设置左侧模块抽屉
        setNavContent(
                SystemProperty.getSystemProperty().getMainAppModulelist("SJ"),
                null);
        setContentView(selectable);
    }

    /**
     * iniParam:(初始化参数). <br/>
     * date: 2015年3月4日 <br/>
     *
     * @author lxf
     */
    protected void iniParam() {
        down = new DownLoadWorkTaskSrv(this);
        downzyp = new DownZYPProgressDiaolog(this);
        downzyp.setGetDataSourceListener(eventListener);
        down.setGetDataSourceListener(eventListener);
    }

    /**
     * initData:(获取数据源). <br/>
     * date: 2015年3月4日 <br/>
     *
     * @author lxf
     */
    protected void initData() {
        // TODO Auto-generated method stub
        if (down != null) {
            down.geteDownLoadWorkList(getSearchStr());// 查询
        }
    }

    /**
     * eventLst:TODO(标题栏事件).
     */
    public IEventListener eventListener = new IEventListener() {

        @Override
        public void eventProcess(int eventType, Object... objects)
                throws HDException {
            // TODO Auto-generated method stub
            switch (eventType) {
                case IEventType.DOWN_WORK_LIST_SHOW:
                    // 显示下载列表
                    if (objects[0] instanceof List<?>) {
                        dataList = (List<SuperEntity>) objects[0];
                        LocalWorkTaskSrv localSrv = new LocalWorkTaskSrv();
                        List<SuperEntity> localWorkTasks = localSrv
                                .loadWorkTaskList("");
                        // 与本地PDA数据进行对比
                        WorkOrderUtilTools.compareWorkOrder(dataList,
                                localWorkTasks);
                    }
                    if (null != dataList) {
                        setDataOrder();
                        selectable.setData(dataList);
                    } else {
                        ToastUtils.imgToast(DownLoadActivity.this,
                                R.drawable.hd_hse_common_msg_wrong, "没有作业票！");
                    }
                    setControlInfo();
                    break;
                case IEventType.DOWN_WORK_LIST_LOAD:
                    // 表示下载成功后执行的方法
                    // initData();
                    downAfter();
                    break;
                case PhoneEventType.ZYPLIST_ITEM_CLICK:
                    // 表示点击票事件
                    setControlInfo();
                    // 判断是否 有电必有火
                    if (null != objects && objects.length > 4) {
                        SelectableZYPExpandableListView _elv = (SelectableZYPExpandableListView) objects[0];

                        boolean _isChecked = (Boolean) objects[1];
                        SuperEntity _currEntitiy = (SuperEntity) objects[2];
                        SuperEntity _allEntity = (SuperEntity) objects[3];
                        List<boolean[]> _state = (List<boolean[]>) objects[4];

                        // 读取数据库 判断是否有点必有火的关联
                        if (initDHGX == -1) {
                            QueryRelativeConfig queryRelation = new QueryRelativeConfig();
                            RelationTableName relationInfo = new RelationTableName();
                            relationInfo.setSys_type(IRelativeEncoding.ISDHYD);
                            if (queryRelation.isHadRelative(relationInfo)) {
                                initDHGX = 1;
                            } else {
                                initDHGX = 0;
                            }
                        }
                        if (initDHGX == 1) {
                            setChooseZYPInfo(_elv, _isChecked,
                                    (WorkOrder) _currEntitiy, _allEntity, _state,
                                    ",zylx01,zylx07,");
                        }
                    }
                    break;
                case PhoneEventType.ZYPLIST_ICON_CLICK:
                    // 表示点击全选
                    setControlInfo();
                    break;
                case IEventType.ACTIONBAR_DOWNLOAD_CLICK:
                    checkedList = selectable.getChoicedEntity();// 获取下载数据集
                    if (null != checkedList && checkedList.size() > 0) {
                        // 下载数据
                        if (isLocalHaveZyp(checkedList)) {
                            downZYPMsg();
                        } else {
                            downzyp.execute("下载", "下载作业票,请等待.....", "", checkedList);
                        }

                    } else {
                        ToastUtils.toast(getBaseContext(), "没有选择要下载的数据!");
                    }
                    break;
                case IEventType.ACTIONBAR_SEARCH_CLICK:
                    // 表示搜索
                    if (objects[0] instanceof String) {
                        setSearchStr((String) objects[0]);
                        initData();
                    }
                    break;
            }
        }
    };

    /**
     * downzypmsg:(). <br/>
     * date: 2015年3月18日 <br/>
     *
     * @author lxf
     */
    private void downZYPMsg() {
        // TODO Auto-generated method stub
        MessageDialog.Builder builder = new MessageDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定覆盖已经下载的作业票吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                downzyp.execute("下载", "下载作业票,请等待.....", "", checkedList);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.createWarm().show();
    }

    /**
     * isLocalHaveZyp:(验证本地是否包含下载的作业票). <br/>
     * date: 2015年3月18日 <br/>
     *
     * @author lxf
     * @param listTemp
     * @return
     */
    private boolean isLocalHaveZyp(List<SuperEntity> listTemp) {
        boolean ret = false;
        if (listTemp != null) {
            for (SuperEntity superEntity : listTemp) {
                if (superEntity instanceof WorkOrder) {
                    if (((WorkOrder) superEntity).isDownloaded()) {
                        ret = true;
                        break;
                    }
                }
            }
        }
        return ret;

    }

    /**
     * downAfter:(下载成功后执行的方法). <br/>
     * date: 2015年3月6日 <br/>
     *
     * @author lxf
     */
    private void downAfter() {
        // 获取选中下载的数据
        if (checkedList != null && checkedList.size() > 0 && dataList != null
                && dataList.size() > 0) {
            List<SuperEntity> tempsup = new ArrayList<SuperEntity>();

            for (SuperEntity down : checkedList) {
                int index = 0;
                if (((WorkOrder) down).getStatus().equalsIgnoreCase(
                        IWorkOrderStatus.INPRG)) {
                    for (SuperEntity workTask : dataList) {
                        // 取出服务端一个任务下所有作业票
                        List<SuperEntity> serverEntities = workTask
                                .getChild(WorkOrder.class.getName());
                        serverEntities.remove(down);
                        if (serverEntities.size() == 0) {
                            tempsup.add(workTask);
                        }
                        index++;
                    }
                } else {
                    ((WorkOrder) down).setDownloaded(true);
                }
            }
            if (tempsup.size() > 0) {
                dataList.removeAll(tempsup);
            }
        }
        if (null != dataList) {
            selectable.setData(dataList);
        }
//		if (null == dataList || dataList.size() == 0) {
//				ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
//						"没有作业票！");
//		}
        setControlInfo();
    }

    boolean isVisable = false;

    private void setControlInfo() {
        List<SuperEntity> checkedList = selectable.getChoicedEntity();// 获取下载数据集
        if (null != checkedList && checkedList.size() > 0) {
            // 设置下载按钮可见
            if (!isVisable) {
                setSearchVisible(false);
                setHSEActionBarInVisible(new String[]{IActionBar.IBTN_SEARCH});
                setHSEActionBarVisible(new String[]{IActionBar.IBTN_DOWNLOAD});
                isVisable = true;

            }
        } else {
            if (isVisable) {
                setHSEActionBarInVisible(new String[]{IActionBar.IBTN_DOWNLOAD});
                setHSEActionBarVisible(new String[]{IActionBar.IBTN_SEARCH});
                isVisable = false;
            }
        }
    }

    /**
     * setChooseZYPInfo:(设置票选中时，同时自动选中相关联的票). <br/>
     * date: 2014年11月3日 <br/>
     *
     * @author lxf
     * @param listview
     * @param currentWorkOrder
     * @param strZypClass
     */
    protected void setChooseZYPInfo(SelectableZYPExpandableListView elv,
                                    boolean isChecked, WorkOrder currentWorkOrder,
                                    SuperEntity allEntity, List<boolean[]> state, String strZypClass) {
        if (StringUtils.isEmpty(strZypClass)) {
            return;
        }

        // if (null != currentWorkOrder
        // && strZypClass.toLowerCase().contains(
        // "," + currentWorkOrder.getZypclass().toLowerCase()
        // + ",")) {
        // WorkTask _thisTask = null;
        // int _thisChildCount = 0;
        // List<SuperEntity> _thisChilds = null;
        // _thisTask = (WorkTask) allEntity;
        // _thisChilds = _thisTask.getChild(WorkOrder.class.getName());
        // _thisChildCount = _thisChilds.size();
        //
        // for (int j = 0; j < (_thisChildCount); j++) {
        // WorkOrder _thisWork = (WorkOrder) _thisChilds.get(j);
        // if (strZypClass.toLowerCase().contains(
        // "," + _thisWork.getZypclass().toLowerCase() + ",")) {
        // // state.set(j + 1, isChecked);
        // setStateResult(state, j, isChecked);
        // }
        // }
        // }
        // 表示选中电票
        if (null != currentWorkOrder
                && "zylx07".equalsIgnoreCase(currentWorkOrder.getZypclass())) {
            WorkTask _thisTask = null;
            int _thisChildCount = 0;
            List<SuperEntity> _thisChilds = null;
            _thisTask = (WorkTask) allEntity;
            _thisChilds = _thisTask.getChild(WorkOrder.class.getName());
            _thisChildCount = _thisChilds.size();

            for (int j = 0; j < (_thisChildCount); j++) {
                WorkOrder _thisWork = (WorkOrder) _thisChilds.get(j);
                if (_thisWork.getUd_zyxk_zysqid().equalsIgnoreCase(
                        currentWorkOrder.getDhzy_id())) {
                    setStateResult(state, j, isChecked);
                }
            }
        }
        // 表示选中动火
        if (null != currentWorkOrder
                && "zylx01".equalsIgnoreCase(currentWorkOrder.getZypclass())) {
            WorkTask _thisTask = null;
            int _thisChildCount = 0;
            List<SuperEntity> _thisChilds = null;
            _thisTask = (WorkTask) allEntity;
            _thisChilds = _thisTask.getChild(WorkOrder.class.getName());
            _thisChildCount = _thisChilds.size();

            for (int j = 0; j < (_thisChildCount); j++) {
                WorkOrder _thisWork = (WorkOrder) _thisChilds.get(j);
                if (currentWorkOrder.getUd_zyxk_zysqid().equalsIgnoreCase(
                        _thisWork.getDhzy_id())) {
                    setStateResult(state, j, isChecked);
                }
            }
        }
        elv.notifyDataSetChanged();
    }

    /**
     * setStateResult:(改变集合里结果). <br/>
     * date: 2015年3月5日 <br/>
     *
     * @author lxf
     * @param state
     * @param index
     * @param res
     */
    private void setStateResult(List<boolean[]> state, int index, boolean res) {
        int i = 0;
        for (boolean[] booleans : state) {
            int j = 0;
            for (boolean boolean1 : booleans) {
                if (i == index) {
                    booleans[j] = res;
                    return;
                }
                j++;
                i++;
            }
        }
    }

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
     * @author lxf
     * @param unsorted
     */
    private void bubble_sort(List<SuperEntity> unsorted) {
        if (unsorted == null || unsorted.size() == 1) {
            return;
        }
        int len = unsorted.size();
        for (int i = 0; i < len; i++) {
            for (int j = i; j < len; j++) {
                try {
                    if (unsorted.get(j).getAttribute(zystarttimeKey) != null) {
                        // 表示第一个大
                        if (!UtilTools.dataTimeCompare(unsorted.get(i)
                                .getAttribute(zystarttimeKey).toString(), unsorted
                                .get(j).getAttribute(zystarttimeKey).toString())) {
                            SuperEntity temp = unsorted.get(i);
                            unsorted.set(i, unsorted.get(j));
                            unsorted.set(j, temp);
                        }
                    } else {
                        List<SuperEntity> child = unsorted.get(j).getChild("com.hd.hse.entity.workorder.WorkOrder");
                        if (child != null && child.size() > 0) {
                            for (int k = 0; k < child.size(); k++) {
                                if (child.get(k).getAttribute(zystarttimeKey) != null) {
                                    String s = child.get(k).getAttribute(zystarttimeKey).toString();
                                    unsorted.get(j).setAttribute(zystarttimeKey, s);

                                    if (!UtilTools.dataTimeCompare(unsorted.get(i)
                                            .getAttribute(zystarttimeKey).toString(), unsorted.get(j)
                                            .getAttribute(zystarttimeKey).toString())) {
                                        SuperEntity temp = unsorted.get(i);
                                        unsorted.set(i, unsorted.get(j));
                                        unsorted.set(j, temp);
                                    }
                                }


                            }
                        }
                    }

                } catch (HDException e) {
                    ToastUtils.toast(getBaseContext(), "作业开始时间比较报错,请联系管理员");
                }
            }
        }


    }
}
