package com.hd.hse.common.module.phone.ui.module.measurefragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hd.hse.common.component.phone.adapter.BaseStepCheckAdapter;
import com.hd.hse.common.component.phone.adapter.BaseStepCheckExpanableListAdapter;
import com.hd.hse.common.component.phone.custom.BottomMenu;
import com.hd.hse.common.component.phone.custom.CustomListView;
import com.hd.hse.common.component.phone.custom.PinnedSectionExpandableListView;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.ui.model.fragment.NaviFrameFragment;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.entity.base.MeasureReviewSub;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.workorder.WorkApplyMeasure;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.system.SystemProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MeasureFragmentBase extends NaviFrameFragment {

    private View contentView;

    /**
     * listDataSoruce:TODO(措施数据源).
     */
    public List<SuperEntity> listDataSoruce;
    /**
     * listApp:TODO(环节点数据源).
     */
    private List<SuperEntity> listApp = null;// 环节点集合

    /**
     * padConfig:TODO(pda配置页签信息).
     */
    private PDAWorkOrderInfoConfig padConfig;

    /**
     * configType:TODO(措施类型).
     */
    private int configType = 0;
    /**
     * childKey:TODO(子表集合KEY,可扩张listview 数据源获取子表数据的KEY).
     */
    private String childKey;

    private String isSelectAttr = "isselectitem";

    /**
     * listView:TODO(listView不带标题).
     */
    private CustomListView listView;
    /**
     * expandableListView:TODO(带标题可扩展的listview).
     */
    private PinnedSectionExpandableListView expandableListView;

    /**
     * bottomMenu:TODO(底部菜单).
     */
    private BottomMenu bottomMenu;

    /**
     * showList:TODO(控制显示列).
     */
    private List<String> showList;

    /**
     * autoHeight:TODO(是否自使用高度).
     */
    private boolean autoHeight = false;
    /**
     * baseAdapter:TODO(措施适配器).
     */
    private MeasureAatapterInterface measureAdapter;

    /**
     * ispcNoApply:TODO(默认是不验证的).
     */
    private boolean ispcNoApply = false;
    private List<Integer> checkresultList;
    private List<Integer> postcheckresultList;
    private List<Integer> expandcheckresultList;
    private String ud_zyxk_zysqid;


    @Override
    protected void init() {
        // TODO Auto-generated method stub

    }

    @Override
    public void refreshData() {
        // TODO Auto-generated method stub
        if (listApp == null || listApp.size() == 0) {
            if (bottomMenu != null) {
                bottomMenu.setButtonText("保存");
            }
        } else {
            if (bottomMenu != null) {
                bottomMenu.setButtonText("审核");
            }
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        // TODO Auto-generated method stub
        if (null == contentView) {
            contentView = inflater.inflate(
                    R.layout.hd_hse_common_module_phone_measure_layout, null);
            listView = (CustomListView) contentView
                    .findViewById(R.id.hd_hse_common_module_phone_measure_listViewid);
            expandableListView = (PinnedSectionExpandableListView) contentView
                    .findViewById(R.id.hd_hse_common_module_phone_measure_expandablelistViewid);
            bottomMenu = (BottomMenu) contentView
                    .findViewById(R.id.hd_hse_common_module_phone_measure_bottommeneid);
            // 设置事件
            bottomMenu.setIEventListener(setButtomMeunEventListener);
            // 判断是逐条
            if (configType != IConfigEncoding.MEASURE_TYPEONEBYNOE) {
                bottomMenu.setArrowButtonVsibibable(false);
            }

            listView.setAutoHeight(getAutoHeight());
            // 设置措施显示内容
            showList = getShowList();
            measureAdapter = getMeasureAdapter();

            if (null != measureAdapter) {
                measureAdapter.setIEventListener(getIeventListener());
            }
            if (null != measureAdapter && null != showList
                    && showList.size() > 0) {
                // listView.setAdapter(baseAdapter);
                if (measureAdapter.getAdapter() instanceof BaseStepCheckExpanableListAdapter) {
                    listView.setVisibility(View.GONE);
                    expandableListView.setVisibility(View.VISIBLE);
                    expandableListView
                            .setAdapter((BaseStepCheckExpanableListAdapter) measureAdapter
                                    .getAdapter());
                    int groupcout = expandableListView.getCount();
                    for (int i = 0; i < groupcout; i++) {
                        expandableListView.expandGroup(i);
                    }
                } else if (measureAdapter.getAdapter() instanceof BaseStepCheckAdapter) {
                    listView.setVisibility(View.VISIBLE);
                    expandableListView.setVisibility(View.GONE);
                    listView.setAdapter((BaseStepCheckAdapter) measureAdapter
                            .getAdapter());

                }
            }
        }
        return contentView;
    }

    /**
     * TODO
     *
     * @see com.hd.hse.common.module.ui.model.fragment.NaviFrameFragment#initData(java.util.List)
     */
    @SuppressWarnings("unchecked")
    public void initData(List<Object> data) {
        if (data != null) {
            int i = 0;
            // if (data.size() > i && data.get(i) instanceof WorkOrder) {
            // // workorder对象
            // workOrder = (WorkOrder) data.get(i);
            // }
            // i++;
            if (data.size() > i
                    && data.get(i) instanceof PDAWorkOrderInfoConfig) {
                // PDA 配置信息
                padConfig = (PDAWorkOrderInfoConfig) data.get(i);
            }
            i++;
            if (data.size() > i && data.get(i) instanceof List) {
                // 显示数据源
                listDataSoruce = (List<SuperEntity>) data.get(i);
                if (listDataSoruce != null && listDataSoruce.size() > 0){
                    ud_zyxk_zysqid = (String) listDataSoruce.get(0).getAttribute("ud_zyxk_zysqid");
                    if (ud_zyxk_zysqid == null) {
                        Map<String, List<SuperEntity>> childs = listDataSoruce.get(0).getChilds();
                        if (childs != null) {
                            if (childs.get("com.hd.hse.entity.workorder.WorkApplyMeasure") != null) {
                                ud_zyxk_zysqid = (String) childs.get("com.hd.hse.entity.workorder.WorkApplyMeasure").get(0).getAttribute("ud_zyxk_zysqid");

                            } else if (childs.get("com.hd.hse.entity.base.MeasureReviewSub") != null) {
                                ud_zyxk_zysqid = (String) childs.get("com.hd.hse.entity.base.MeasureReviewSub").get(0).getAttribute("ud_zyxk_zysqid");
                            }
                        }
                    }
                    checkresultList = new ArrayList<>();

                    //第一次进到某个措施确认界面
                    if (listDataSoruce != null && listDataSoruce.size() > 0) {
                        for (int k = 0; k < listDataSoruce.size(); k++) {
                            if (listDataSoruce.get(k) != null) {

                                if (listDataSoruce.get(k).getAttribute("checkresult") == null) {
                                    checkresultList.add(-1);
                                } else {
                                    int checkresult = (int) listDataSoruce.get(k).getAttribute("checkresult");
                                    checkresultList.add(checkresult);
                                }

                            }

                        }
                    }
                }


            }
            i++;
            if (data.size() > i && data.get(i) instanceof List) {
                // 环节点数据
                listApp = (List<SuperEntity>) data.get(i);
            }
            i++;
            if (data.size() > i && data.get(i) instanceof Integer) {
                // 控件类别
                configType = (int) data.get(i);
            }
            i++;
            if (data.size() > i && data.get(i) instanceof String) {
                // 控件类别
                childKey = (String) data.get(i);

                expandcheckresultList = new ArrayList<>();

                //第一次进到某个措施确认界面
                if (listDataSoruce != null && listDataSoruce.size() > 0) {
                    for (int k = 0; k < listDataSoruce.size(); k++) {
                        if (listDataSoruce.get(k) != null) {


                            List<SuperEntity> child = listDataSoruce.get(k).getChild(childKey);
                            if (child != null) {
                                for (int w = 0; w < child.size(); w++) {
                                    if (child.get(w).getAttribute("checkresult") == null) {
                                        expandcheckresultList.add(-1);
                                    } else {
                                        expandcheckresultList.add((int) child.get(w).getAttribute("checkresult"));
                                    }
                                }

                            }
                        }

                    }
                }
            }
            i++;
            if (data.size() > i && data.get(i) instanceof Boolean) {
                // 控件类别
                ispcNoApply = (boolean) data.get(i);
            }

        }
    }

    /**
     * getBaseAdapter:(设置设配器). <br/>
     * date: 2015年1月26日 <br/>
     *
     * @return
     * @author lxf
     */
    private MeasureAatapterInterface getMeasureAdapter() {
        if (measureAdapter == null && listDataSoruce != null) {
            measureAdapter = MeasureAdapterFactory.newIntance()
                    .getMeasureAdapter(padConfig, configType, getActivity(),
                            listDataSoruce, showList, "isselect", listView,
                            expandableListView, childKey);
            measureAdapter.setIsselectAttr(isSelectAttr);
        }
        return measureAdapter;
    }

    /**
     * getShowList:(设置显示的列). <br/>
     * date: 2014年10月24日 <br/>
     *
     * @return
     * @author lxf
     */
    public abstract List<String> getShowList();

    /**
     * setAutoHeight:(获取是否自使用高度). <br/>
     * date: 2014年11月5日 <br/>
     *
     * @param autoHeight
     * @author lxf
     */
    public boolean getAutoHeight() {
        return autoHeight;
    }

    /**
     * getIeventListener:(设置bottomMenu事件). <br/>
     * date: 2015年01月26日 <br/>
     *
     * @author lxf
     * @return
     */
    private IEventListener setButtomMeunEventListener = new IEventListener() {

        @Override
        public void eventProcess(int eventType, Object... objects)
                throws HDException {
            switch (eventType) {
                case IEventType.BOTTOM_UPWARD_CLICK:
                    // 表示上一条
                    if (measureAdapter != null) {
                        measureAdapter.previousItem();
                    }
                    break;
                case IEventType.BOTTOM_DOWNWARD_CLICK:
                    // 表示下一条
                    if (measureAdapter != null) {
                        measureAdapter.nextItem();
                    }
                    break;
                case IEventType.BOTTOM_BUTTON_CLICK:

                    String zylocation = null;
                    //措施确认最后环节点之后点击按钮

                    if (listApp != null && listApp.size() >= 1) {
                        zylocation = (String) listApp.get(0).getAttribute("zylocation");
                    }

                    BaseDao baseDao = new BaseDao();
                    String sql = "select status from ud_zyxk_zysq where ud_zyxk_zysqid='" + ud_zyxk_zysqid + "'";

                    WorkOrder workOrder = (WorkOrder) baseDao.executeQuery(sql.toString(),
                            new EntityResult(WorkOrder.class));

                    String status = null;
                    if (workOrder != null) {
                        status = workOrder.getStatus();
                    }

                    if (status != null) {
                        if (status.equals("APPR")) {

                            ToastUtils.imgToast(mActivity,
                                    R.drawable.hd_hse_common_msg_wrong, "会签已结束，不能再更改措施了！");
                            return;
                        }
                    }


//                    SharedPreferences sp = mActivity.getSharedPreferences("lz", Context.MODE_PRIVATE); //私有数据
//                    String isCanClick = sp.getString("isCanClick", "");
//                    if (!isCanClick.equals("")) {
//                        if (isCanClick.equals(zylocation + "&" + ud_zyxk_zysqid)) {
//
//                            ToastUtils.imgToast(mActivity,
//                                    R.drawable.hd_hse_common_msg_wrong, "会签已结束，不能再更改措施了！");
//                            return;
//                        }
//                    }

                    if (measureAdapter.getAdapter() instanceof BaseStepCheckExpanableListAdapter) {
                        //扩展的

                        postcheckresultList = new ArrayList<>();
                        if (listDataSoruce != null && listDataSoruce.size() > 0) {
                            for (int j = 0; j < listDataSoruce.size(); j++) {
                                //列表里的数据
                                if (listDataSoruce.get(j) != null) {


                                    List<SuperEntity> child = listDataSoruce.get(j).getChild(childKey);
                                    for (int w = 0; w < child.size(); w++) {
                                        if (child.get(w).getAttribute("checkresult") == null) {
                                            postcheckresultList.add(-1);
                                        } else {
                                            postcheckresultList.add((int) child.get(w).getAttribute("checkresult"));
                                        }
                                    }

                                }

                            }
                        }


                    } else if (measureAdapter.getAdapter() instanceof BaseStepCheckAdapter) {

                        postcheckresultList = new ArrayList<>();
                        if (listDataSoruce != null && listDataSoruce.size() > 0) {
                            for (int j = 0; j < listDataSoruce.size(); j++) {
                                //列表里的数据
                                if (listDataSoruce.get(j) != null) {
                                    if (listDataSoruce.get(j).getAttribute("checkresult") == null) {
                                        postcheckresultList.add(-1);
                                    } else {
                                        int postcheckresult = (int) listDataSoruce.get(j).getAttribute("checkresult");
                                        postcheckresultList.add(postcheckresult);
                                    }

                                }

                            }
                        }

                    }


                    if (SystemProperty.getSystemProperty().getHs() != null) {
                        HashMap<String, List> hs = SystemProperty.getSystemProperty().getHs();
                        if (zylocation != null) {
                            if (hs.get(ud_zyxk_zysqid + "&" + zylocation) != null) {

                                if (measureAdapter.getAdapter() instanceof BaseStepCheckExpanableListAdapter) {

                                    expandcheckresultList = hs.get(ud_zyxk_zysqid + "&" + zylocation);
                                    for (int w = 0; w < expandcheckresultList.size(); w++) {
                                        if (expandcheckresultList.get(w) == -1) {
                                            expandcheckresultList.set(w, 2);
                                        }
                                    }
                                } else if (measureAdapter.getAdapter() instanceof BaseStepCheckAdapter) {

                                    checkresultList = hs.get(ud_zyxk_zysqid + "&" + zylocation);
                                    for (int w = 0; w < checkresultList.size(); w++) {
                                        if (checkresultList.get(w) == -1) {
                                            checkresultList.set(w, 2);
                                        }
                                    }
                                }


                            }

                        }


                    }

                    boolean t = false;

                    if (measureAdapter.getAdapter() instanceof BaseStepCheckExpanableListAdapter) {
                        for (int i = 0; i < expandcheckresultList.size(); i++) {

                            if (((int) expandcheckresultList.get(i)) != ((int) postcheckresultList.get(i))) {
                                t = true;
                                break;
                            }
                        }

                    } else if (measureAdapter.getAdapter() instanceof BaseStepCheckAdapter) {
                        for (int i = 0; i < listDataSoruce.size(); i++) {

                            if (((int) checkresultList.get(i)) != ((int) postcheckresultList.get(i))) {
                                t = true;
                                break;
                            }
                        }

                    }

                    SystemProperty.getSystemProperty().setCheckResultIsUpdate(t);

                    buttonAppClick();
                    break;
                case IEventType.BOTTOM_SELECT_CHECKED:
                    // 选择是
                    changeDataStatus(1);
                    break;
                case IEventType.BOTTOM_UNSELECT_CHECKED:
                    // 选择否
                    changeDataStatus(0);
                    break;
                case IEventType.BOTTOM_CIRCLE_CHECKED:
                    // 选择不适用
                    changeDataStatus(2);
                    break;
                default:
                    break;
            }
        }
    };

    private void buttonAppClick() throws HDException {

        int eventType = -100;

        // 点击确定或者审核
        // List<SuperEntity> tempdate = getSaveDataList();
        // 表示弹出刷卡界面此事件可以向外抛
        if (listApp == null || listApp.size() == 0) {
            // 表示执行保存动作、事件抛出'
            eventType = IEventType.EXAMINE_SAVE_ClICK_AFTER;
        } else {
            // 表示弹出环节点界面
            eventType = IEventType.EXAMINE_EXAMINE_ClICK_AFTER;
        }
        if (iEventListener != null) {
            iEventListener.eventProcess(eventType);
        }
    }

    /**
     * changeDataStatus:(更改数据源的状态). <br/>
     * date: 2015年1月27日 <br/>
     *
     * @param value
     * @author lxf
     */
    private void changeDataStatus(int value) {
        // 1获取数据源
        if (measureAdapter != null) {
            // 此处调用控件的方法得到选中的数据源
            // List<SuperEntity> listtemp = measureAdapter.getSelectedValues();
            List<SuperEntity> listtemp = measureAdapter
                    .getCurrentSelectedValues();

            boolean ismsg = false;
            String msg = null;
            if (listtemp != null) {
                if (ispcNoApply) {
                    if (value == 2) {
                        // 表示是不适用是做判断
                        for (SuperEntity superEntity : listtemp) {
                            if (superEntity instanceof WorkApplyMeasure) {
                                if (((WorkApplyMeasure) superEntity)
                                        .getIsselect() == 1) {
                                    msg = ((WorkApplyMeasure) superEntity)
                                            .getDescription();
                                    ismsg = true;
                                    break;
                                }

                            } else if (superEntity instanceof MeasureReviewSub) {
                                if (((MeasureReviewSub) superEntity)
                                        .getIsselect() == 1) {
                                    msg = ((MeasureReviewSub) superEntity)
                                            .getDescription();
                                    ismsg = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                if (ismsg) {
                    ToastUtils.imgToast(getActivity(),
                            R.drawable.hd_hse_common_msg_wrong, "《" + msg
                                    + "》必须落实，不能选择不适用！");
                } else {
                    for (SuperEntity superEntity : listtemp) {
                        if (superEntity instanceof WorkApplyMeasure) {

                            Integer oldcheck = ((WorkApplyMeasure) superEntity)
                                    .getOldcheckresult();
                            // 判断原来结果值
                            if (oldcheck == null || oldcheck == -1) {
                                // 判断是否已经有结果值
                                if (!((WorkApplyMeasure) superEntity)
                                        .isModified()) {
                                    oldcheck = ((WorkApplyMeasure) superEntity)
                                            .getCheckresult();
                                }
                                if (oldcheck == null) {
                                    oldcheck = -1;
                                }
                                ((WorkApplyMeasure) superEntity)
                                        .setOldcheckresult(oldcheck);
                            }
                            ((WorkApplyMeasure) superEntity)
                                    .setCheckresult(value);
                            ((WorkApplyMeasure) superEntity).setModified(true);
                        } else if (superEntity instanceof MeasureReviewSub) {
                            Integer oldcheck = ((MeasureReviewSub) superEntity)
                                    .getOldcheckresult();
                            // 判断原来结果值
                            if (oldcheck == null || oldcheck == -1) {
                                // 判断是否已经有结果值
                                if (!((MeasureReviewSub) superEntity)
                                        .isModified()) {
                                    oldcheck = ((MeasureReviewSub) superEntity)
                                            .getCheckresult();
                                }
                                if (oldcheck == null) {
                                    oldcheck = -1;
                                }
                                ((MeasureReviewSub) superEntity)
                                        .setOldcheckresult(oldcheck);
                            }
                            ((MeasureReviewSub) superEntity)
                                    .setCheckresult(value);
                            ((MeasureReviewSub) superEntity).setModified(true);
                        }
                    }
                    measureAdapter.updateCurrentValues(listtemp);
                }
            }
        }
        if (bottomMenu != null) {
            // bottomMenu.clearCheck();
        }
    }

    public List<SuperEntity> tempSaveDatalist = null;

    /**
     * getOpDataList:(获取要保存的数据源). <br/>
     * date: 2015年1月27日 <br/>
     *
     * @return
     * @author lxf
     */
    public List<SuperEntity> getSaveDataList() {
        List<SuperEntity> listData = null;
        List<SuperEntity> listDataTemp = new ArrayList<SuperEntity>();
        if (measureAdapter != null) {
            if (getAllData()) {
                // 以所有数据作为帅选条件
                listData = measureAdapter.getSourceValues();
            } else {
                // 以当前选中的数据，作为帅选条件
                listData = measureAdapter.getSelectedValues();
                // 2015-04-14 新增，如果没有筛选出数据，则选出已经带结果的数据
                if (listData == null || listData.size() < 1) {
                    List<SuperEntity> listAll = measureAdapter
                            .getSourceValues();
                    for (SuperEntity superEntity : listAll) {
                        if (superEntity instanceof WorkApplyMeasure) {
                            if (((WorkApplyMeasure) superEntity)
                                    .getCheckresult() != null
                                    && ((WorkApplyMeasure) superEntity)
                                    .getCheckresult() != -1) {
                                listDataTemp.add(superEntity);
                            }
                        } else if (superEntity instanceof MeasureReviewSub) {
                            if (((MeasureReviewSub) superEntity)
                                    .getCheckresult() != null
                                    && ((MeasureReviewSub) superEntity)
                                    .getCheckresult() != -1) {
                                listDataTemp.add(superEntity);
                            }
                        }
                    }
                    listData = listDataTemp;
                }
            }
            // lxf 注释2015-04-14 没看出有什么用

            // if (getAllData()) {
            // for (SuperEntity superEntity : listData) {
            // if (superEntity instanceof WorkApplyMeasure) {
            // if (((WorkApplyMeasure) superEntity).getCheckresult() != null) {
            // listDataTemp.add(superEntity);
            // }
            // } else if (superEntity instanceof MeasureReviewSub) {
            // if (((MeasureReviewSub) superEntity).getCheckresult() != null) {
            // listDataTemp.add(superEntity);
            // }
            // }
            // }
            //
            // tempSaveDatalist = listDataTemp;
            // }
        }
        tempSaveDatalist = listData;
        return tempSaveDatalist;
    }

    /**
     * updateValues:(更新保存完的数据). <br/>
     * date: 2015年2月11日 <br/>
     *
     * @author lxf
     */
    public void updateValues(WorkApprovalPermission curAppNode) {
        if (measureAdapter != null) {
            if (tempSaveDatalist != null && tempSaveDatalist.size() > 0) {
                // 把人员名字赋值给措施信息
                if (curAppNode != null) {
                    for (int i = 0; i < tempSaveDatalist.size(); i++) {
                        tempSaveDatalist.get(i).setAttribute("persondesc",
                                curAppNode.getPersondesc());
                        tempSaveDatalist.get(i).setAttribute("oldcheckresult",
                                -1);
                    }
                }
                measureAdapter.updateValues(tempSaveDatalist);
            }
        }
    }

    /**
     * getAllData:(是否获取所有数据). <br/>
     * date: 2015年1月28日 <br/>
     *
     * @return
     * @author lxf
     */
    public abstract boolean getAllData();

    public boolean isHaveTitle() {
        boolean ret = false;
        if (padConfig != null && padConfig.getCbname() != null
                && padConfig.getCbisenable() > 0) {
            ret = true;
        }
        return ret;
    }
}
