package com.hd.hse.cc.phone.ui.activity.worktask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hd.hse.cc.business.workorder.WorkTaskSrv;
import com.hd.hse.cc.phone.R;
import com.hd.hse.cc.phone.ui.activity.cancel.WorkOrderCanelActivity;
import com.hd.hse.cc.phone.ui.activity.close.WorkOrderCloseActivity;
import com.hd.hse.cc.phone.ui.activity.interrupt.WorkOrderInterruptActivity;
import com.hd.hse.cc.phone.ui.activity.interruptend.WorkOrderInterruptEndActivity;
import com.hd.hse.cc.phone.ui.activity.pause.WorkOrderPauseActivity;
import com.hd.hse.common.component.phone.constant.IActionBar;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.PhoneEventType;
import com.hd.hse.common.module.phone.custom.NormalZYPExpandableListView.OnZYPItemClickListener;
import com.hd.hse.common.module.phone.custom.PopWinButton;
import com.hd.hse.common.module.phone.ui.module.activity.BaseListBusActivity;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.other.PrescriptionFrequencyConfigure;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.service.workorder.WorkTaskDBSrv;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.system.SystemProperty;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * TODO 关闭取消 ClassName: CloseCancelTaskTabulationActivity ()</br> date:
 * 2014年10月23日 </br>
 *
 * @author zhulei
 */
public class TaskTabulationActivity extends BaseListBusActivity {
    private static Logger logger = LogUtils
            .getLogger(TaskTabulationActivity.class);
    public final static String WORK_ORDER = "workorder";
    public final static String WORK_ORDERS = "workorders";

    ProgressDialog prgDialog;
    private boolean isfirst = false;// 是否第一次执行indata（）
    private PopWinButton[] mPopWinButton;
    private WorkTaskSrv wts;
    private List<SuperEntity> lstEntity = null;
    private IQueryWorkInfo quertWorkInfo;
    private boolean isPbQxgn = false;

    public TaskTabulationActivity() {
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.hd.hse.common.module.ui.model.activity.BaseListActivity#onCreate(
     * android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        isfirst = true;
        super.onCreate(savedInstanceState);
        // lxf注释 父类会执行
        // initView();
        // initData();
    }

    /**
     */
    @Override
    public void initActionBar() {
        // 设置导航栏信息
       /* QueryRelativeConfig config = new QueryRelativeConfig();
        RelationTableName tableName = new RelationTableName();
        tableName.setSys_type(IRelativeEncoding.HBZYP);*/
        //if (config.isHadRelative(tableName)) {
        setCustomActionBar(true, iEventLsn, new String[]{IActionBar.IV_LMORE, IActionBar.IBTN_SEARCH, IActionBar.TV_TITLE, IActionBar.TV_MORE});
        /*} else {
            setCustomActionBar(true, iEventLsn, new String[]{
                    IActionBar.IBTN_SEARCH, IActionBar.TV_TITLE});
        }*/

        // 设置导航栏标题
        setActionBartitleContent("关闭取消", false);
        // 设置左侧模块选择抽屉
        setNavContent(
                SystemProperty.getSystemProperty().getMainAppModulelist("SJ"),
                getNavCurrentKey());
        super.initActionBar();
    }


    @Override
    public String[] getActionBarMenus() {
        QueryRelativeConfig config = new QueryRelativeConfig();
        RelationTableName tableName = new RelationTableName();
        tableName.setSys_type(IRelativeEncoding.HBZYP);
        if (config.isHadRelative(tableName)) {
            return new String[]{IActionBar.TV_JOIN, IActionBar.TV_POWER_UPDATE};
        } else {
            return new String[]{IActionBar.TV_POWER_UPDATE};
        }
    }

    @Override
    protected void initView() {
        super.initView();
        IQueryRelativeConfig relation = new QueryRelativeConfig();
        RelationTableName relationEntity = new RelationTableName();
        relationEntity.setSys_type(IRelativeEncoding.PBQXGN);
        mPopWinButton = new PopWinButton[5];
        String[] strBtn = new String[]{"关闭", "取消", "暂停", "作业中断", "中断结束"};
        int[] strEventType = new int[]{IEventType.CLOSE_CLICK,
                IEventType.CANCEL_CLICK, IEventType.PAUSE_CLICK, IEventType.ZY_INTERRUPT_CLICK,
                IEventType.ZY_INTERRUPTEND_CLICK};
        int[] strDrawable = new int[]{
                R.drawable.hd_hse_common_module_pop_btn,
                R.drawable.hd_hse_common_module_pop_btn,
                R.drawable.hd_hse_common_module_pop_btn,
                R.drawable.hd_hse_common_module_pop_btn,
                R.drawable.hd_hse_common_module_pop_btn};
        for (int i = 0; i < 5; i++) {
            mPopWinButton[i] = new PopWinButton();
            mPopWinButton[i].eventType = strEventType[i];
            mPopWinButton[i].text = strBtn[i];
            mPopWinButton[i].drawableId = strDrawable[i];
        }


        // 判断是否屏蔽取消功能,2017/2/24进行了修改，
        isPbQxgn = relation.isHadRelative(relationEntity);
        if (isPbQxgn) {
            // setItemClickListener(zypItemClickListener);
            // 去掉取消按钮
            mPopWinButton[1] = mPopWinButton[mPopWinButton.length - 1];
            mPopWinButton = Arrays.copyOf(mPopWinButton,
                    mPopWinButton.length - 1);
        }
        initPopWindows(mPopWinButton, iEventLsn);
        setiEventLsn(iEventLsn);
    }

    /**
     * 判断作业票是否显示暂停
     */

    @Override
    public PopWinButton[] onZYPItemClick(WorkOrder workOrder,
                                         PopWinButton[] mPopWinButton) {

        IQueryRelativeConfig relation = new QueryRelativeConfig();
        RelationTableName relationEntity = new RelationTableName();
        relationEntity.setSys_type(IRelativeEncoding.PBQXGN);
        mPopWinButton = new PopWinButton[5];
        String[] strBtn = new String[]{"关闭", "取消", "暂停", "作业中断", "中断结束"};
        int[] strEventType = new int[]{IEventType.CLOSE_CLICK,
                IEventType.CANCEL_CLICK, IEventType.PAUSE_CLICK, IEventType.ZY_INTERRUPT_CLICK,
                IEventType.ZY_INTERRUPTEND_CLICK};
        int[] strDrawable = new int[]{
                R.drawable.hd_hse_common_module_pop_btn,
                R.drawable.hd_hse_common_module_pop_btn,
                R.drawable.hd_hse_common_module_pop_btn,
                R.drawable.hd_hse_common_module_pop_btn,
                R.drawable.hd_hse_common_module_pop_btn};
        for (int i = 0; i < 5; i++) {
            mPopWinButton[i] = new PopWinButton();
            mPopWinButton[i].eventType = strEventType[i];
            mPopWinButton[i].text = strBtn[i];
            mPopWinButton[i].drawableId = strDrawable[i];
        }


        // 判断是否屏蔽取消功能,2017/2/24进行了修改，
        isPbQxgn = relation.isHadRelative(relationEntity);
        if (isPbQxgn) {
            // setItemClickListener(zypItemClickListener);
            // 去掉取消按钮
            mPopWinButton[1] = mPopWinButton[mPopWinButton.length - 1];
            mPopWinButton = Arrays.copyOf(mPopWinButton,
                    mPopWinButton.length - 1);
        }


        int showInterruptgn = isShowInterruptgn(workOrder);
        String pausestatus = workOrder.getPausestatus();


        if (isPbQxgn) {

            //都不显示
            if (showInterruptgn == 0) {
                mPopWinButton[1] = mPopWinButton[2];
                mPopWinButton = Arrays.copyOf(mPopWinButton,
                        mPopWinButton.length - 2);
            } else if (showInterruptgn == 1) {

                //显示中断
                if (("UNPAUSE").equalsIgnoreCase(pausestatus) ||
                        ("PCANCEL").equalsIgnoreCase(pausestatus) ||
                        "".equals(pausestatus) || pausestatus == null) {
                    mPopWinButton[1] = mPopWinButton[3];
                    mPopWinButton = Arrays.copyOf(mPopWinButton,
                            mPopWinButton.length - 1);
                }
                //显示中断结束
                if (("PAUSED").equalsIgnoreCase(pausestatus)) {
                    mPopWinButton = Arrays.copyOf(mPopWinButton,
                            mPopWinButton.length - 1);
                }

            } else {
                mPopWinButton[1] = mPopWinButton[2];
                mPopWinButton = Arrays.copyOf(mPopWinButton,
                        mPopWinButton.length - 2);

            }


        } else {

            //都不显示
            if (showInterruptgn == 0) {
                mPopWinButton = Arrays.copyOf(mPopWinButton,
                        mPopWinButton.length - 2);
            } else if (showInterruptgn == 1) {

                //显示中断
                if (("UNPAUSE").equalsIgnoreCase(pausestatus) ||
                        ("PCANCEL").equalsIgnoreCase(pausestatus) ||
                        "".equals(pausestatus) || pausestatus == null) {
                    mPopWinButton = Arrays.copyOf(mPopWinButton,
                            mPopWinButton.length - 1);
                }
                //显示中断结束
                if (("PAUSED").equalsIgnoreCase(pausestatus)) {
                    mPopWinButton[3] = mPopWinButton[mPopWinButton.length - 1];
                    mPopWinButton = Arrays.copyOf(mPopWinButton,
                            mPopWinButton.length - 1);
                }

            } else {
                mPopWinButton = Arrays.copyOf(mPopWinButton,
                        mPopWinButton.length - 2);
            }


        }


        boolean isShowPauseBt = isShowPauseBt(workOrder);
        if (!isShowPauseBt && isPbQxgn) {
            // 取消，暂停按钮都不显示，直接跳转关闭页，
            startCloseActivity(workOrder, false, null);
            return null;
        }

        if (!isShowPauseBt && !isPbQxgn) {
            mPopWinButton[2] = mPopWinButton[mPopWinButton.length - 1];

        }
        if (!isShowPauseBt) {
            mPopWinButton = Arrays.copyOf(mPopWinButton,
                    mPopWinButton.length - 1);
        }


        return mPopWinButton;
    }

    private int isShowInterruptgn(WorkOrder workOrder) {
        BaseDao dao = new BaseDao();

        StringBuilder stringBuilder = new StringBuilder();

        String sql = "select a.ispause from ud_zyxk_sxpcpz a where a.jobtype='" + workOrder.getZypclass() + "'";
        stringBuilder.append(sql);
        if (workOrder.getZylevel() != null) {
            stringBuilder.append("and a.zylevel='" + workOrder.getZylevel() + "';");
        } else {
            stringBuilder.append(";");
        }

        PrescriptionFrequencyConfigure prescriptionFrequencyConfigure = null;
        try {
            prescriptionFrequencyConfigure = (PrescriptionFrequencyConfigure) dao.
                    executeQuery(stringBuilder.toString(), new EntityResult(PrescriptionFrequencyConfigure.class));
        } catch (DaoException e) {
            e.printStackTrace();
        }
        if (prescriptionFrequencyConfigure != null) {
            Integer ispause = prescriptionFrequencyConfigure.getIspause();
            return ispause == null ? -1 : ispause;
        }
        return -1;

    }

    /**
     * eventListener:TODO().
     */
    private IEventListener iEventLsn = new IEventListener() {

        @Override
        public void eventProcess(int eventType, Object... objects)
                throws HDException {
            switch (eventType) {

                case IEventType.ZY_INTERRUPT_CLICK:
                    Intent interruptIntent = new Intent(TaskTabulationActivity.this,
                            WorkOrderInterruptActivity.class);
                    Bundle interruptBundle = new Bundle();
                    interruptBundle.putSerializable(WORK_ORDER, (WorkOrder) objects[0]);
                    interruptIntent.putExtras(interruptBundle);
                    startActivity(interruptIntent);
                    overridePendingTransition(R.anim.hd_hse_common_zoomin,
                            R.anim.hd_hse_common_zoomout); // Activity切换动画
                    break;

                case IEventType.ZY_INTERRUPTEND_CLICK:
                    Intent interruptendIntent = new Intent(TaskTabulationActivity.this,
                            WorkOrderInterruptEndActivity.class);
                    Bundle interruptendBundle = new Bundle();
                    interruptendBundle.putSerializable(WORK_ORDER, (WorkOrder) objects[0]);
                    interruptendIntent.putExtras(interruptendBundle);
                    startActivity(interruptendIntent);
                    overridePendingTransition(R.anim.hd_hse_common_zoomin,
                            R.anim.hd_hse_common_zoomout); // Activity切换动画
                    break;
                case IEventType.ACTIONBAR_SEARCH_CLICK:
                    if (objects[0] instanceof String) {
                        setSearchStr((String) objects[0]);
                        initData();
                        // lsv.addSetions();
                    }
                    break;

                case IEventType.CANCEL_CLICK:
                    Intent intent = new Intent(TaskTabulationActivity.this,
                            WorkOrderCanelActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(WORK_ORDER, (WorkOrder) objects[0]);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.hd_hse_common_zoomin,
                            R.anim.hd_hse_common_zoomout); // Activity切换动画
                    break;
                case IEventType.CLOSE_CLICK:
                    Intent closeIntent = new Intent(TaskTabulationActivity.this,
                            WorkOrderCloseActivity.class);
                    Bundle closeBundle = new Bundle();
                    closeBundle.putSerializable(WORK_ORDER, (WorkOrder) objects[0]);
                    closeIntent.putExtras(closeBundle);
                    startActivity(closeIntent);
                    overridePendingTransition(R.anim.hd_hse_common_zoomin,
                            R.anim.hd_hse_common_zoomout); // Activity切换动画
                    break;
                case IEventType.PAUSE_CLICK:
                    // 对票进行暂停操作
                    Intent i = new Intent(TaskTabulationActivity.this,
                            WorkOrderPauseActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable(WORK_ORDER, (WorkOrder) objects[0]);
                    i.putExtras(b);
                    // startActivity(i);
                    startActivityForResult(i, 0);
                    overridePendingTransition(R.anim.hd_hse_common_zoomin,
                            R.anim.hd_hse_common_zoomout); // Activity切换动画

                    break;
                case PhoneEventType.ZYPLIST_ITEM_LONG_CLICK:
                    if (quertWorkInfo == null) {
                        quertWorkInfo = new QueryWorkInfo();
                    }
                    WorkOrder workOrder = quertWorkInfo.queryWorkInfo(
                            (WorkOrder) objects[0], null);
                    showWorkOrderPopupWin(workOrder);
                    break;
                case IEventType.ACTIONBAR_JOIN_BTN_CLICK:
                    setIsChoose(true);
                    break;
                case IEventType.ACTIONBAR_JOIN_OK_CLICK:
                    List<SuperEntity> list = mExpandableListView.getChoicedEntity();
                    if (list != null && list.size() != 0) {
                        startCloseActivity((WorkOrder) list.get(0), true, list);
                        dismissActionbarJoinStstus();
                        setIsChoose(false);
                    } else {
                        ToastUtils.toast(TaskTabulationActivity.this, "请选择要合并的作业票");
                    }
                    break;
                case IEventType.ACTIONBAR_JOIN_CANCEL_CLICK:
                    setIsChoose(false);
                    break;

                case IEventType.ACTIONBAR_MORE_CLICK:
                    if (getActionBarMenu() != null)
                        getActionBarMenu().showAsDropDown((View) objects[0]);
                    break;

            }
        }
    };

    /**
     * startCloseActivity:(跳转到作业关闭界面). <br/>
     * 如果是合并会签，传入workOrders参数，如果不是合并，传入workOrder参数 date: 2015年9月11日 <br/>
     *
     * @param workOrder
     * @param isJoin
     * @param workOrders
     * @author LiuYang
     */
    private void startCloseActivity(WorkOrder workOrder, boolean isJoin,
                                    List<SuperEntity> workOrders) {
        Intent intent = new Intent(TaskTabulationActivity.this,
                WorkOrderCloseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(WORK_ORDER, workOrder);
        bundle.putSerializable(WORK_ORDERS, (Serializable) workOrders);
        bundle.putBoolean("isJoin", isJoin);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.hd_hse_common_zoomin,
                R.anim.hd_hse_common_zoomout); // Activity切换动画
    }

    private OnZYPItemClickListener zypItemClickListener = new OnZYPItemClickListener() {

        @Override
        public void onClick(WorkOrder workOrder, View anchorView,
                            int pointerHorizontalPosition) {
            startCloseActivity(workOrder, false, null);
        }
    };
    private int times = 0;

    /**
     * TODO 重写方法
     */
    @Override
    protected void initData() {
        // TODO Auto-generated method stub
        // super.initData();
        if (isfirst) {
            return;
        }
        if (wts == null) {
            wts = new WorkTaskSrv();
        }
        try {
            //设置查询的作业票的内容
            lstEntity = wts.loadWorkTaskList(getSearchStr());
            if (times != 0 && !isfirst
                    && (lstEntity == null || lstEntity.size() < 1)) {
                ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                        "没有作业票！");
            }
            times++;
            setDataList(lstEntity);
        } catch (HDException e) {
            // TODO Auto-generated catch block
            ToastUtils.imgToast(this, R.drawable.hd_hse_common_msg_wrong,
                    "数据集获取失败！");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isfirst = false;
        popWindowsMiss();
        // lxf2015-08-27 注释，因为在oncreate中已经执行过了。
        initData();
        // lsv.addSetions();
    }

    @Override
    public WorkTaskDBSrv getWorkTaskObject() {
        // TODO Auto-generated method stub
        return new WorkTaskSrv();
    }

    @Override
    public String getTitileName() {
        // TODO Auto-generated method stub
        return "关闭取消";
    }

    @Override
    public String getNavCurrentKey() {
        // TODO Auto-generated method stub
        return "hse-cc-phone-app";
    }

    @Override
    public String getFunctionCode() {
        // TODO Auto-generated method stub
        return IConfigEncoding.GB;
    }

    /**
     * 是否显示暂停按钮
     *
     * @return
     */
    private boolean isShowPauseBt(WorkOrder workOrder) {
        if (isDelay(workOrder) && isRelationShowPauseBt()
                && !isContinuousDelay(workOrder) && !isPauseStatus(workOrder)
                && !isOverContinuousCount(workOrder))
            return true;
        return false;
    }


    /**
     * 是否可延期
     */

    private boolean isDelay(WorkOrder workOrder) {
        if (null != workOrder.getIskyyq() && "1".equals(workOrder.getIskyyq()))
            return true;
        return false;
    }

    /**
     * 关系表是否配置暂停按钮
     */
    private boolean isRelationShowPauseBt() {
        boolean isShow = false;
        IQueryRelativeConfig relation = new QueryRelativeConfig();
        RelationTableName relationEntity = new RelationTableName();
        relationEntity.setSys_type(IRelativeEncoding.DELAYCONFIGUR);
        isShow = relation.isHadRelative(relationEntity);
        return isShow;
    }

    /**
     * 时效频次表是否连续延期,应该默认连续延期，
     */
    private boolean isContinuousDelay(WorkOrder mWorkOrder) {

        BaseDao dao = new BaseDao();
        String sql = "select ifnull(iscontinuousdelay ,1) as iscontinuousdelay from ud_zyxk_sxpcpz where ud_zyxk_sxpcpzid = (select ud_zyxk_sxpcpzid from ud_zyxk_zysq where ud_zyxk_zysqid = '"
                + mWorkOrder.getUd_zyxk_zysqid() + "')";
        try {
            PrescriptionFrequencyConfigure pfc = (PrescriptionFrequencyConfigure) dao
                    .executeQuery(sql, new EntityResult(
                            PrescriptionFrequencyConfigure.class));
            if (null != pfc && pfc.getIscontinuousdelay() != 1) {
                return false;
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return true;

    }

    /**
     * 是否已为暂停状态
     */
    private boolean isPauseStatus(WorkOrder mWorkOrder) {
        if (null != mWorkOrder.getIspause() && mWorkOrder.getIspause() == 1)
            return true;
        return false;
    }

    /**
     * 是否超过延期次数
     */
    private boolean isOverContinuousCount(WorkOrder mWorkOrder) {
        if (mWorkOrder.getYyqcount() == null)
            mWorkOrder.setYyqcount(0);
        if (mWorkOrder.getYqcount() == null)
            return true;
        if (mWorkOrder.getYyqcount() < mWorkOrder.getYqcount())
            return false;
        return true;
    }

}
