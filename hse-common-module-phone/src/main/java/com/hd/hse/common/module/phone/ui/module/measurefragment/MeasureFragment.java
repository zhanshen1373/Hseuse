package com.hd.hse.common.module.phone.ui.module.measurefragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.custom.StepCheckTabView;
import com.hd.hse.common.module.ui.model.fragment.NaviFrameFragmentExmaine;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.entity.base.MeasureReviewSub;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.workorder.WorkApplyMeasure;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.system.SystemProperty;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MeasureFragment extends NaviFrameFragmentExmaine {

    private static Logger logger = LogUtils.getLogger(MeasureFragment.class);

    // ////////////////////////////////////////////////////////////////缓存信息
    /**
     * Fragment 的缓存, 完整的类名做 key ， 实例对象最 value
     */
    @SuppressWarnings("rawtypes")
    private Map<String, MeasureFragmentBase> cacheFragment = new HashMap<String, MeasureFragmentBase>();
    /**
     * hasmapTab:TODO(存界面加载数据源的缓存信息).
     */
    private HashMap<Object, Object> hasmapTab = new HashMap<Object, Object>();

    /**
     * cacheApp:TODO(审批环节点的缓存信息).
     */
    private HashMap<Object, Object> cacheApp = new HashMap<Object, Object>();

    // ////////////////////////////////////////////////////////////////控件对象
    /**
     * contentView:TODO(view).
     */
    private View contentView;
    /**
     * pageIndicator:TODO(tab选择措施页).
     */
    private StepCheckTabView stepCheckTabView;

    /**
     * linerLayoutTab:TODO(线性布局).
     */
    private LinearLayout linerLayoutTab;
    // ////////////////////////////////////////////////////////////////业务对象
    /**
     * queryWorkInfo:TODO().
     */
    private QueryWorkInfo queryWorkInfo;

    /**
     * listConfig:TODO().
     */
    private List<PDAWorkOrderInfoConfig> listConfig;

    // ////////////////////////////////////////////////////////////////当前显示的数据
    /**
     * currentIndex:TODO(表示当前位置索引).
     */
    private int currentIndex = 0;
    /**
     * 表示当前界面的Fragment类
     */
    private Class<? extends MeasureFragmentBase> currentFragment;
    /**
     * currentFragmentObject:TODO(当前Fragment对象).
     */
    public MeasureFragmentBase currentFragmentObject;
    /**
     * currentFragmentKey:TODO(当前Fragmentkey).
     */
    private String currentFragmentKey;
    /**
     * currentListData:TODO(表示当前显示的数据).
     */
    private List<SuperEntity> currentListData;
    /**
     * currentTabPDAConfigInfo:TODO(表示当前PAD选中配置信息对象).
     */
    public SuperEntity currentTabPADConfigInfo;

    // ////////////////////////////////////////////////////////////////配置表信息开始

    /**
     * CONTROLTYPELIE:TODO(措施细类型).
     */
    private final String CONTROLTYPELIE = "contypelie";
    /**
     * CSTYPE:TODO(选择框内容).
     */
    private final String CSTYPE = "cstype";
    /**
     * CURRENTCBCODE:TODO(当前选择框选中的编码).
     */
    private final String CURRENTCBCODE = "currentcbcode";

    /**
     * CBISENABLE:TODO(是否启用下来框).
     */
    private final String CBISENABLE = "cbisenable";

    /**
     * isload:TODO(第一次载入).
     */
    boolean isload = true;

    /**
     * sumMeasureCount:TODO(存措施总的页签数).
     */
    private int sumMeasureCount = 0;

    /**
     * eventType:TODO(事件类型).
     */
    private int eventTypep;
    /**
     * objects:TODO(事件类型传递参数).
     */
    public Object objectsp;

    private static String param = "WQRCSMRBSY";
    private static boolean flag = false;

    // String param = "";
    // QueryRelativeConfig config = new QueryRelativeConfig();
    // flag = config.isHadRelative(param);

    /**
     * getQueryWorkInfo:(获取查询对象). <br/>
     * date: 2014年11月4日 <br/>
     *
     * @return
     * @author lxf
     */
    public QueryWorkInfo getQueryWorkInfo() {
        if (null == queryWorkInfo) {
            queryWorkInfo = new QueryWorkInfo();
        }
        return queryWorkInfo;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        QueryRelativeConfig config = new QueryRelativeConfig();
        flag = config.isHadRelative(param);

        if (null == contentView) {
            contentView = inflater.inflate(
                    R.layout.hd_hse_common_module_phone_measure_fragmentlayout,
                    null);
            stepCheckTabView = (StepCheckTabView) contentView
                    .findViewById(R.id.hd_hse_common_component_phone_pageindicator_id);

            linerLayoutTab = (LinearLayout) contentView
                    .findViewById(R.id.hd_hse_common_module_phone_measure_tag_id);
            initParam();
        }

        return contentView;
    }

    /**
     * init:(初始化参数信息). <br/>
     * date: 2014年10月24日 <br/>
     *
     * @author lxf
     */
    private void initParam() {
        queryWorkInfo = new QueryWorkInfo();
        if (null != stepCheckTabView) {
            stepCheckTabView.setOnIEventListener(onTabClick);
        }
    }

    @Override
    public void refreshData() {
        if (null == getWorkEntity()) {
            return;
        }
        if (isload) {
            isload = false;
            // 绑定tab页数据
            try {
                bindPageIndicatorData();
                if (null != listConfig && currentIndex < listConfig.size()) {
                    currentTabPADConfigInfo = listConfig.get(currentIndex);
                    switchTab(currentTabPADConfigInfo);
                }
            } catch (HDException e) {
                logger.equals(e.getMessage());
                ToastUtils.imgToast(getActivity(),
                        R.drawable.hd_hse_common_msg_wrong, "加载数据报错，请联系管理员！");
            }
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public void initData(List<Object> data) {
        if (data != null) {
            if (data.get(0) instanceof WorkOrder) {
                setWorkEntity((WorkOrder) data.get(0));
            }
            if (data.get(1) instanceof List) {
                listConfig = (List<PDAWorkOrderInfoConfig>) data.get(1);
            }
        }

    }

    /**
     * onSignAppClick:TODO(表示点击tab页事件).
     */
    IEventListener onTabClick = new IEventListener() {
        @Override
        public void eventProcess(int eventType, Object... objects)
                throws HDException {
            // 表示tab点击事件
            if (eventType == IEventType.TOP_CIRCLE_CHECKED) {
                if (null != objects) {
                    if (objects.length > 0) {
                        if (objects[0] instanceof SuperEntity) {
                            currentTabPADConfigInfo = (SuperEntity) objects[0];
                            // 根据选择的内容区读取数据库数据
                            switchTab(currentTabPADConfigInfo);
                        }
                    }
                    currentIndex = 0;
                }
            }
        }
    };

    public List<SuperEntity> getOpSaveDatalist() {
        if (currentFragmentObject != null) {
            return currentFragmentObject.getSaveDataList();
        }
        return null;
    }

    /**
     * switchTab:(切换措施导航栏). <br/>
     * date: 2015年2月2日 <br/>
     *
     * @param currentTouchEntity
     * @throws HDException
     * @author lxf
     */
    @SuppressWarnings("unchecked")
    private void switchTab(SuperEntity currentTouchEntity) throws HDException {
        boolean isinit = false;
        currentListData = null;
        List<Object> listTemp = new ArrayList<Object>();
        if (null != currentTabPADConfigInfo) {
            // 设置pda配置页签总数
            currentTabPADConfigInfo.setAttribute("childCount", sumMeasureCount);
        }
        int controlType = getSuperControlType(currentTouchEntity);
        controlType = praseControlType(currentTouchEntity, controlType);
        // 表示条件type
        String csType = getSuperMeasureCSType(currentTouchEntity);//
        String dycode = getSuperMeasureDycode(currentTouchEntity);
        // 表示数据库存的cstype
        MeasureFragmentBase nav = null;
        String key = controlType + csType + dycode;

        List<SuperEntity> getlistSup = null;// 措施信息集合
        List<SuperEntity> listApp = null;// 环节点集合
        // 表示带选择框，应该把第一次查询的数据保存下来
        if (!hasmapTab.containsKey(key)) {
            currentListData = getMeasureList(currentTouchEntity);
            hasmapTab.put(key, currentListData);
            isinit = true;
        } else {
            currentListData = (List<SuperEntity>) hasmapTab.get(key);
        }
        if (!cacheApp.containsKey(key)) {
            listApp = getListAppInfo(currentTouchEntity);
            cacheApp.put(key, listApp);
        } else {
            listApp = (List<SuperEntity>) cacheApp.get(key);
        }
        switch (controlType) {
            case IConfigEncoding.MEASURE_TYPENOONEBYNOE:
                // 非逐条批量
                switchContentView(key, MeasureFragmentStepCheckPart.class);
                nav = getFragmentByClass(key, MeasureFragmentStepCheckPart.class);
                break;
            case IConfigEncoding.MEASURE_TYPEONEBYNOE:
                // 逐条
                switchContentView(key, MeasureFragmentStepCheckPoint.class);
                nav = getFragmentByClass(key, MeasureFragmentStepCheckPoint.class);
                break;
            case IConfigEncoding.MEASURE_TYPEONEBYNOEBATCH:
                // 逐条批量
                switchContentView(key, MeasureFragmentStepCheckPartMultiple.class);
                nav = getFragmentByClass(key,
                        MeasureFragmentStepCheckPartMultiple.class);
                break;
            default:
                break;
            // ((MeasureFragmentPoint) nav).setPromies(getPromiseInfo());
        }
        // listTemp.add(getWorkEntity());
        listTemp.add(currentTabPADConfigInfo);
        listTemp.add(currentListData);
        listTemp.add(listApp);
        listTemp.add(controlType);
        listTemp.add(getChildKey());
        listTemp.add(isRelation());
        nav.setIEventListener(btnAppEventListener);
        // 设置环节点
        setExamineList(listApp);

        if (null != nav && isinit) {
            // 设置数据源
            nav.initData(listTemp);
        }
    }

    /**
     * TODO 表示切换tab页的显示
     *
     * @see com.hd.hse.common.module.ui.model.fragment.NaviFrameFragment#switchContentView(java.lang.Class)
     */
    public void switchContentView(String key,
                                  Class<? extends MeasureFragmentBase> clz) {
        if (clz == null) {
            throw new IllegalArgumentException("clz 不能为 null");
        }
        key = key + clz.getName();
        // 判断是不是当前页面，如果是不用再进行页面切换直接返回。
        if (clz == currentFragment && key.equalsIgnoreCase(currentFragmentKey)) {
            return;
        }
        // 切换Fragment代码------------------------
        @SuppressWarnings("rawtypes")
        MeasureFragmentBase frg = cacheFragment.get(key);
        // 创建 Fragment 及缓存的过程。------------
        if (frg == null) {
            // 缓存中没有相应的 实例，所以要创建
            try {
                frg = (MeasureFragmentBase) clz.newInstance();
                // 放入缓存。
                cacheFragment.put(key, frg);
            } catch (java.lang.InstantiationException e) {
                logger.error("措施切换tab页报错", e);
            } catch (IllegalAccessException e) {
                logger.error("措施切换tab页报错", e);
            }
        }
        // 切换 Fragment
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.hd_hse_common_component_phone_content_id, frg);
        transaction.commit();
        // 同步当前页面 。
        currentFragment = clz;
        currentFragmentObject = frg;
        currentFragmentKey = key;
    }

    /**
     * 根据字节码获得缓存中的 实例对象，如果缓存中没有则返回 null getCurrentFragment:(). <br/>
     * date: 2014年10月16日 <br/>
     *
     * @return
     * @author lxf
     */
    public MeasureFragmentBase getFragmentByClass(String key,
                                                  Class<? extends Fragment> clz) {
        if (null == cacheFragment) {
            return null;
        }
        return cacheFragment.get(key + clz.getName());
    }

    /**
     * btnAppEventListener:TODO(表示点击审核或保存事件).
     */
    private IEventListener btnAppEventListener = new IEventListener() {

        @Override
        public void eventProcess(int eventType, Object... objects)
                throws HDException {
            // 点击保存按钮之前进行判断，看是否满足弹出或者保存数据
            if (getBtnAppClickBefore() != null) {
                getBtnAppClickBefore().eventProcess(eventType, objects);
            }

            if (eventType == IEventType.EXAMINE_SAVE_ClICK_AFTER
                    || eventType == IEventType.EXAMINE_EXAMINE_ClICK_AFTER) {
                setTabPADConfigInfo(currentTabPADConfigInfo);
            }
            if (eventType == IEventType.EXAMINE_SAVE_ClICK_AFTER) {
                // 表示点击保存
                if (isApp()) {


                    String ud_zyxk_zysqid = (String) currentListData.get(0).getAttribute("ud_zyxk_zysqid");
                    if (ud_zyxk_zysqid == null) {
                        Map<String, List<SuperEntity>> childs = currentListData.get(0).getChilds();
                        if (childs != null) {
                            if (childs.get("com.hd.hse.entity.workorder.WorkApplyMeasure")!=null){
                                ud_zyxk_zysqid = (String) childs.get("com.hd.hse.entity.workorder.WorkApplyMeasure").get(0).getAttribute("ud_zyxk_zysqid");

                            }else if (childs.get("com.hd.hse.entity.base.MeasureReviewSub")!=null){
                                ud_zyxk_zysqid = (String) childs.get("com.hd.hse.entity.base.MeasureReviewSub").get(0).getAttribute("ud_zyxk_zysqid");

                            }
                        }
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

//                    SharedPreferences sharedPreferences = mActivity.getSharedPreferences("lz", Context.MODE_PRIVATE); //私有数据
//                    SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
//                    String savebt = sharedPreferences.getString("savebt", "");
//                    if (savebt.equals(ud_zyxk_zysqid)) {
//                        ToastUtils.imgToast(mActivity,
//                                R.drawable.hd_hse_common_msg_wrong, "会签已结束，不能再更改措施了！");
//                        return;
//                    }
//                    editor.putString("savebt", "savebt");
//                    editor.commit();//提交修改

                    exmaineSave();

                }
            } else if (eventType == IEventType.EXAMINE_EXAMINE_ClICK_AFTER) {
                if (isApp()) {
                    SystemProperty.getSystemProperty().setClickSHbt(true);
                    ShowExmaineDialog();
                }
            } else if (eventType == IEventType.ACTION_DIALOG_DIS) {
                // 表示dialog消失事件
                if (issave) {
                    updateValues();
                }
                if (objects != null
                        && objects[0] instanceof WorkApprovalPermission) {
                    // 如果当前页面逐条和非逐条之分
                    WorkApprovalPermission workapp = (WorkApprovalPermission) objects[0];
                    if (currentFragmentObject instanceof MeasureFragmentStepCheckPartMultiple
                            || currentFragmentObject instanceof MeasureFragmentStepCheckPoint) {
                        // 表示逐条或逐条批量、设置 数据源的数据为空
                        List<WorkApprovalPermission> listTemp = getExamineList();
                        if (listTemp != null) {
                            for (WorkApprovalPermission superEntity : listTemp) {
                                // 逐条批量人员信息必须为多人刷卡 sptime 不能清空
                                superEntity.setAttribute("personid", "");
                                superEntity.setAttribute("persondesc", "");
                                superEntity.setAttribute("departmentdesc", "");
                                superEntity.setAttribute("defaultpersonid", "");
                                superEntity.setAttribute("defaultpersondesc",
                                        "");
                                if (workapp.getIsend() == 1) {
                                    superEntity.setAttribute("sptime", "");
                                }
                            }
                        }
                    }
                }
            }

        }
    };

    /**
     * isApp:(是否可以点击审核按钮). <br/>
     * date: 2015年3月10日 <br/>
     *
     * @return
     * @author lxf
     */
    private boolean isApp() {
        boolean ret = true;
        List<SuperEntity> tempChangeData = getSaveDatalist();
        // TODO 修改措施结果
        if (flag) {
            if (tempChangeData != null && tempChangeData.size() > 0) {
                for (SuperEntity mSuper : tempChangeData) {
                    if (mSuper instanceof WorkApplyMeasure) {
                        if (((WorkApplyMeasure) mSuper).getCheckresult() == null || ((WorkApplyMeasure) mSuper).getCheckresult() < 0) {
                            ((WorkApplyMeasure) mSuper).setCheckresult(2);
                        }
                    } else if (mSuper instanceof MeasureReviewSub) {
                        if (((MeasureReviewSub) mSuper).getCheckresult() == null || ((MeasureReviewSub) mSuper).getCheckresult() < 0) {
                            ((MeasureReviewSub) mSuper).setCheckresult(2);
                        }
                    }
                }
            }
            updateValues();
        } else {
            if (tempChangeData != null && tempChangeData.size() > 0) {
                for (SuperEntity mSuper : tempChangeData) {
                    if (mSuper instanceof WorkApplyMeasure) {
                        if (((WorkApplyMeasure) mSuper).getCheckresult() == null
                                || ((WorkApplyMeasure) mSuper).getCheckresult() < 0) {
                            ret = false;
                            break;
                        }
                    } else if (mSuper instanceof MeasureReviewSub) {
                        if (((MeasureReviewSub) mSuper).getCheckresult() == null
                                || ((MeasureReviewSub) mSuper).getCheckresult() < 0) {
                            ret = false;
                            break;
                        }
                    }

                }
                if (!ret) {
                    // 区分逐条和非逐条
                    if (currentFragmentObject instanceof MeasureFragmentStepCheckPart) {
                        ToastUtils.toast(getActivity(), "请落实全部措确认结果!");
                    } else {
                        ToastUtils.toast(getActivity(), "请落实选中措施的结果!");
                    }
                }
            } else {
                ret = false;
                // 没有选中要保存的数据
                ToastUtils.toast(getActivity(), "请选中要确认的措施信息");
            }
        }

        return ret;
    }

    /**
     * btnAppClickBefore:TODO(点击事件操作前的事件).
     */
    private IEventListener btnAppClickBefore = null;

    /**
     * getBtnAppClickBefore:(获取监听事件). <br/>
     * date: 2015年2月28日 <br/>
     *
     * @return
     * @author zhaofeng
     */
    public IEventListener getBtnAppClickBefore() {
        return btnAppClickBefore;
    }

    /**
     * setBtnAppClickBefore:(设置监听事件). <br/>
     * date: 2015年2月28日 <br/>
     *
     * @param btnAppClickBefore
     * @author zhaofeng
     */
    public void setBtnAppClickBefore(IEventListener btnAppClickBefore) {
        this.btnAppClickBefore = btnAppClickBefore;
    }

    /**
     * updateValues:(更新保存过的数据方法). <br/>
     * date: 2015年2月11日 <br/>
     *
     * @author lxf
     */
    public void updateValues() {
        // 获取当前fragment对象，刷新里边适配器
        if (currentFragmentObject != null) {
            currentFragmentObject.updateValues(getCurApproveNode());
        }
    }

    /**
     * BindPageIndicatorData:(tab绑定数据). <br/>
     * date: 2014年10月24日 <br/>
     *
     * @author lxf
     */
    private void bindPageIndicatorData() {
        if (null != stepCheckTabView) {
            if (null == listConfig) {
                // 隐藏tab页面
                if (linerLayoutTab != null) {
                    linerLayoutTab.setVisibility(View.GONE);
                }
                return;
            }
            sumMeasureCount = listConfig.size();
            if (sumMeasureCount == 1) {
                // 隐藏tab页面
                if (linerLayoutTab != null) {
                    linerLayoutTab.setVisibility(View.GONE);
                    return;
                }
            }
            stepCheckTabView.setDataList(listConfig);
            if (listConfig != null && listConfig.size() > 0) {
                stepCheckTabView.setCurrentItem(listConfig.get(0));
            }
        }
    }

    // ///////获取业务数据的开始 start

    /**
     * getMeasureInfoWhere:(根绝措施类别返回措施信息). <br/>
     * date: 2014年10月24日 <br/>
     *
     * @param listSuper
     * @param cstype
     * @return
     * @author lxf
     */
    @SuppressWarnings("unused")
    private List<SuperEntity> getMeasureInfoWhereCsType(
            List<SuperEntity> listSuper, String cstype) {
        List<SuperEntity> retListSuper = new ArrayList<SuperEntity>();
        int count = listSuper.size();
        for (int i = 0; i < count; i++) {
            if (cstype
                    .equalsIgnoreCase(getSuperMeasureCSType(listSuper.get(i)))) {
                retListSuper.add(listSuper.get(i));
            }
        }
        return retListSuper;
    }

    /**
     * getListAppInfo:(获取审批环节点). <br/>
     * date: 2014年10月23日 <br/>
     *
     * @throws HDException
     * @author lxf
     */
    private List<SuperEntity> getListAppInfo(SuperEntity controlType)
            throws HDException {
        List<SuperEntity> listAppinfo = null;

        if (null == getWorkEntity()) {
            return null;
        }
        SuperEntity supterEntity = getApproalWhereSuper();
        listAppinfo = changeToSuperEntity((List) queryWorkInfo
                .queryApprovalPermission(getWorkEntity(), controlType,
                        supterEntity, null));
        return listAppinfo;
    }

    // ///////获取业务数据的结束 end

    // ///////获取对象属性值得开始 start

    /**
     * getSuperControlCBCode:(取带措施类型). <br/>
     * date: 2014年10月24日 <br/>
     *
     * @param sup
     * @return
     * @author lxf
     */
    public String getSuperControlCBCode(SuperEntity sup) {
        String ret = null;
        if (null != sup) {
            int isEnable = getSuperCBIsEnable(sup);
            if (isEnable != 0) {
                // 表示启用下拉框
                ret = sup.getAttribute(CURRENTCBCODE) != null ? sup
                        .getAttribute(CURRENTCBCODE).toString() : "";
                // 表示当前没有默认值
                if (StringUtils.isEmpty(ret)) {
                    ret = getSuperMeasureCSType(sup);
                    if (!StringUtils.isEmpty(ret)) {
                        String str = ret.split(",")[0].split(":")[0];
                        ret = str;
                        sup.setAttribute(CURRENTCBCODE, str);
                    }
                }
            } else {
                ret = getSuperMeasureCSType(sup);
            }
        }
        return ret;
    }

    /**
     * getSuperMeasureCSType:(获取措施类别). <br/>
     * date: 2014年10月24日 <br/>
     *
     * @param superEntity
     * @return
     * @author lxf
     */
    private String getSuperMeasureCSType(SuperEntity superEntity) {
        String ret = null;
        if (null != superEntity) {
            ret = superEntity.getAttribute(CSTYPE) != null ? superEntity
                    .getAttribute(CSTYPE).toString() : "";
        }
        return ret;
    }


    private String getSuperMeasureDycode(SuperEntity superEntity) {
        String ret = null;
        if (null != superEntity) {
            ret = superEntity.getAttribute("dycode") != null ? superEntity
                    .getAttribute("dycode").toString() : "";
        }
        return ret;
    }

    /**
     * getSuperCBIsEnable:(判断是否是选择框). <br/>
     * date: 2014年10月24日 <br/>
     *
     * @param sup
     * @return
     * @author lxf
     */
    private int getSuperCBIsEnable(SuperEntity sup) {
        int ret = 0;
        if (null != sup) {
            ret = (int) (sup.getAttribute(CBISENABLE) != null ? sup
                    .getAttribute(CBISENABLE) : 0);
        }
        return ret;
    }

    /**
     * getSuperControlType:(获取控件类型). <br/>
     * date: 2014年10月23日 <br/>
     *
     * @param sup
     * @return
     * @author lxf
     */
    public int getSuperControlType(SuperEntity sup) {
        int ret = -2;
        if (null != sup) {
            ret = (int) sup.getAttribute(CONTROLTYPELIE);
        }
        return ret;
    }

    /**
     * praseControlType:(控件类型转换). <br/>
     * date: 2014年11月7日 <br/>
     *
     * @param superEntity
     * @param oldtype
     * @return
     * @author lxf
     */
    private int praseControlType(SuperEntity superEntity, int oldtype) {
        int newtype = -10;
        switch (oldtype) {
            default:
                // 读取是否逐条
                if (getSuperIsOneByOne(superEntity) != 0) {
                    // 读取是否批量逐条
                    if (getSuperIsOneByOneBatch(superEntity) != 0) {
                        newtype = IConfigEncoding.MEASURE_TYPEONEBYNOEBATCH;
                    } else {
                        newtype = IConfigEncoding.MEASURE_TYPEONEBYNOE;
                    }
                } else {
                    newtype = IConfigEncoding.MEASURE_TYPENOONEBYNOE;
                }
                break;
        }
        return newtype;
    }

    /**
     * getSuperIsOneByOne:(是否逐条). <br/>
     * date: 2014年11月7日 <br/>
     *
     * @param sup
     * @return
     * @author lxf
     */
    public int getSuperIsOneByOne(SuperEntity sup) {
        int ret = 0;
        if (null != sup) {
            ret = (int) (sup.getAttribute("conlevel") == null ? 0 : sup
                    .getAttribute("conlevel"));
        }
        return ret;
    }

    /**
     * getSuperIsOneByOneBatch:(是否逐条批量). <br/>
     * date: 2014年11月7日 <br/>
     *
     * @param sup
     * @return
     * @author lxf
     */
    public int getSuperIsOneByOneBatch(SuperEntity sup) {
        int ret = 0;
        if (null != sup) {
            ret = (int) (sup.getAttribute("batappr") == null ? 0 : sup
                    .getAttribute("batappr"));
        }
        return ret;
    }

    // ///////获取对象属性值得结束 end

    // ///////////子类需要重载的方法Strat

    /**
     * getMeasureList:(获取显示的措施数据). <br/>
     * date: 2014年11月4日 <br/>
     *
     * @param currentTouchEntity
     * @return
     * @throws HDException
     * @author lxf
     */
    public abstract List<SuperEntity> getMeasureList(
            SuperEntity currentTouchEntity);

    /**
     * getApproalWhereSuper:(获取查询审批环节点，记录的条件). <br/>
     * date: 2014年11月18日 <br/>
     *
     * @return
     * @author lxf
     */
    public SuperEntity getApproalWhereSuper() {
        return null;
    }

    /**
     * isRelation:(判断关系). <br/>
     * date: 2015年3月5日 <br/>
     *
     * @return
     * @author lxf
     */
    public boolean isRelation() {
        return false;
    }

    /**
     * childKey:(子表集合对象). <br/>
     * date: 2015年2月2日 <br/>
     *
     * @return
     * @author lxf
     */
    public abstract String getChildKey();

    /**
     * TODO dialog 注册事件
     *
     * @see com.hd.hse.common.module.ui.model.fragment.NaviFrameFragmentExmaine#getDialogCancelIEventLsn()
     */
    @Override
    public IEventListener getDialogIEventLsn() {
        // TODO Auto-generated method stub
        return btnAppEventListener;
    }

    // ///////////子类需要重载的方法end
}
