package com.hd.hse.common.module.ui.model.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.R;
import com.hd.hse.common.module.phone.ui.activity.PaintSignatureActivity;
import com.hd.hse.common.module.phone.ui.activity.ReadCardExamineActivity;
import com.hd.hse.common.module.phone.ui.custom.ExamineDialog;
import com.hd.hse.common.module.phone.ui.utils.ImageUtils;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.common.Image;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.CheckControllerActionListener;
import com.hd.hse.system.SystemProperty;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class NaviFrameFragmentExmaine extends NaviFrameFragment {

    private static Logger logger = LogUtils
            .getLogger(NaviFrameFragmentExmaine.class);

    private ExamineDialog<WorkApprovalPermission> exmaineDialog = null;
    public ProgressDialog mDialog = null;
    private List<WorkApprovalPermission> examineList = null;

    private boolean isTPQM = false; // 是否使用图片签名
    private Image image;

    private boolean isItemByItem; // 是否逐条或逐条批量

    /**
     * workOrder:TODO(作业票实体对象).
     */
    private WorkOrder workOrder;

    /**
     * busAction:TODO(后台业务处理对象).
     */
    private BusinessAction busAction;

    /**
     * asyTask:TODO(后台异步任务).
     */
    private BusinessAsyncTask asyTask;

    /**
     * currentTabPDAConfigInfo:TODO(表示当前PAD选中的).
     */
    private SuperEntity currentTabPADConfigInfo;

    /**
     * curApproveNode:TODO(显示当前审批环节点).
     */
    private WorkApprovalPermission curApproveNode;

    public boolean issave = false;
    private HashMap<String, List> hm = new HashMap<>();
    private List<SuperEntity> curChangeData;
    ;

    /**
     * setTabPADConfigInfo:(设置当前PAD选中的). <br/>
     * date: 2015年2月2日 <br/>
     *
     * @param currentPADConfig
     * @author lxf
     */
    public void setTabPADConfigInfo(SuperEntity currentPADConfig) {
        currentTabPADConfigInfo = currentPADConfig;
    }

    /**
     * setWorkEntity:(设置作业票对象). <br/>
     * date: 2015年2月2日 <br/>
     *
     * @param sup
     * @author Administrator
     */
    public void setWorkEntity(WorkOrder sup) {
        workOrder = sup;
    }

    /**
     * getCurApproveNode:(获取当前环节点). <br/>
     * date: 2015年2月12日 <br/>
     *
     * @return
     * @author lxf
     */
    public WorkApprovalPermission getCurApproveNode() {
        return curApproveNode;
    }

    /**
     * getWorkEntity:(设置作业票对象). <br/>
     * date: 2015年2月2日 <br/>
     *
     * @return
     * @author Administrator
     */
    public WorkOrder getWorkEntity() {
        return workOrder;
    }

    /**
     * setExamineList:(设置环节点的数据). <br/>
     * date: 2015年2月2日 <br/>
     *
     * @param list
     * @author lxf
     */
    public void setExamineList(List list) {
        examineList = list;
        if (null != exmaineDialog) {
            exmaineDialog.setExamineList(examineList);
        }
    }

    /**
     * getExamineList:(获取环节点数据集合). <br/>
     * date: 2015年2月3日 <br/>
     *
     * @return
     * @author lxf
     */
    public List<WorkApprovalPermission> getExamineList() {
        return examineList;
    }

    @Override
    protected void init() {
        // TODO Auto-generated method stub
        exmaineDialog = new ExamineDialog<WorkApprovalPermission>(
                getActivity(), 0);
        exmaineDialog.setWorkOrder(workOrder);
        exmaineDialog.setExamineList(examineList);
        exmaineDialog.setiEventLsn(getDialogIEventLsn());
        exmaineDialog.setFragment(this);
        busAction = new BusinessAction(new CheckControllerActionListener());
        asyTask = new BusinessAsyncTask(busAction, appCallBack);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (exmaineDialog != null) {
            exmaineDialog.setIsClick(true);
        }
    }

    /**
     * ShowDialogMessage:(显示dialog). <br/>
     * date: 2015年2月2日 <br/>
     *
     * @param msg 显示内容
     * @author lxf
     */
    public void ShowDialogMessage(String msg) {
        if (mDialog == null) {
            mDialog = new ProgressDialog(getActivity(), msg);
            mDialog.show();
        } else {
            mDialog.showMsg(msg);
            mDialog.show();
        }
    }

    /**
     * ShowExmaineDialog:(显示环节点dialog). <br/>
     * date: 2015年2月2日 <br/>
     *
     * @author lxf
     */
    public void ShowExmaineDialog() {
        issave = false;
        if (exmaineDialog != null && !exmaineDialog.isShowing()) {
            exmaineDialog.reFresh();
            exmaineDialog.show();
        }
    }

    /**
     * reFresh:(刷新环节点). <br/>
     * date: 2015年2月28日 <br/>
     *
     * @author lxf
     */
    public void reFresh() {
        if (exmaineDialog != null) {
            exmaineDialog.reFresh();
        }
    }

    /**
     * DismissDialog:(是否dialog). <br/>
     * date: 2015年2月2日 <br/>
     *
     * @author lxf
     */
    public void DismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == IEventType.READCARD_TYPE
                || resultCode == IEventType.TPQM_TYPE) {
            curApproveNode = (WorkApprovalPermission) data
                    .getSerializableExtra(ReadCardExamineActivity.WORKAPPROVALPERMISSION);
            if (currentTabPADConfigInfo != null
                    && currentTabPADConfigInfo instanceof PDAWorkOrderInfoConfig) {
                isItemByItem = false;
                if (((PDAWorkOrderInfoConfig) currentTabPADConfigInfo)
                        .getConlevel() != null
                        && ((PDAWorkOrderInfoConfig) currentTabPADConfigInfo)
                        .getConlevel() != 0) {
                    // 表示逐条或者逐条批量
                    isItemByItem = true;
                    int lastin = curApproveNode.getPersonid().lastIndexOf(",");
                    if (lastin != -1) {
                        curApproveNode.setPersonid(curApproveNode.getPersonid()
                                .substring(lastin + 1));
                    }
                    lastin = curApproveNode.getPersondesc().lastIndexOf(",");
                    if (lastin != -1) {
                        curApproveNode.setPersondesc(curApproveNode
                                .getPersondesc().substring(lastin + 1));
                    }
                }
            }
            isTPQM = false;
            if (resultCode == IEventType.READCARD_TYPE) {
                image = (Image) data
                        .getSerializableExtra(ReadCardExamineActivity.IMAGE);
            }
            if (resultCode == IEventType.TPQM_TYPE) {
                image = (Image) data
                        .getSerializableExtra(PaintSignatureActivity.IMAGE);
                isTPQM = true;
            }
            appSaveClick(IEventType.EXAMINE_EXAMINE_ClICK, curApproveNode);
        }
    }

    /**
     * exmaineSave:(环节点保存动作). <br/>
     * date: 2015年2月2日 <br/>
     *
     * @author lxf
     */
    public void exmaineSave() {
        curApproveNode = null;
        appSaveClick(0);
    }

    ;

    /**
     * AppSaveClick:(表示审核保存方法). <br/>
     * date: 2014年10月25日 <br/>
     *
     * @param eventType
     * @param objects
     * @throws HDException
     * @author lxf
     */
    private void appSaveClick(int eventType, Object... objects) {
        Map<String, Object> mapParam = new HashMap<String, Object>();
        // 1.作业票
        mapParam.put(WorkOrder.class.getName(), workOrder);
        // 2.pda配置信息
        if (currentTabPADConfigInfo == null) {
            ToastUtils.toast(getActivity(), "请传入当前页面配置信息对象！");
            return;
        }
        mapParam.put(PDAWorkOrderInfoConfig.class.getName(),
                currentTabPADConfigInfo);
        // 3.审批环节点
        if (IEventType.EXAMINE_EXAMINE_ClICK == eventType
                && null != curApproveNode) {
            // 审批环节点
            mapParam.put(WorkApprovalPermission.class.getName(), curApproveNode);
        }
        // 措施信息
        String key = getMeasureClassName();
        if (!StringUtils.isEmpty(key)) {
            curChangeData = getSaveDatalist();
            if (isCheckSaveData()) {
                if (curChangeData == null || curChangeData.size() == 0) {
                    ToastUtils.toast(getActivity(), "请选中要保存的数据");
                    return;
                }
                mapParam.put(key, curChangeData);
            } else {
                if (curChangeData != null && curChangeData.size() > 0) {
                    mapParam.put(key, curChangeData);
                }
            }
        } else {
            ToastUtils.toast(getActivity(), "请传入要保存业务数据的KEY");
            return;
        }
        //
        Map<String, Object> mapTemp = getMapParam();
        if (null != mapTemp && mapParam.size() > 0) {
            mapParam.putAll(mapTemp);
        }
        // 异步保存数据
        ShowDialogMessage("保存信息。。。");
        try {
            asyTask.execute(getIAtionType(), mapParam);
        } catch (HDException e) {
            logger.error(e);
            ToastUtils.toast(getActivity(), "保存数据失败!请联系管理员.");
        }
    }

    /**
     * auditCallBack:TODO(审批环节的回调).
     */
    private AbstractAsyncCallBack appCallBack = new AbstractAsyncCallBack() {

        @Override
        public void start(Bundle msgData) {
            // TODO Auto-generated method stub

        }

        @Override
        public void processing(Bundle msgData) {
            // TODO Auto-generated method stub
            String actiontype = getIAtionType();
            if (msgData.containsKey(actiontype)) {
                ShowDialogMessage(msgData.getString(actiontype));
            }
        }

        @Override
        public void error(Bundle msgData) {
            // TODO Auto-generated method stub
            String actiontype = getIAtionType();
            if (msgData.containsKey(actiontype)) {
                DismissDialog();
                ToastUtils.imgToast(getActivity(),
                        R.drawable.hd_hse_common_msg_wrong,
                        msgData.getString(actiontype));
            }
        }

        @Override
        public void end(Bundle msgData) {
            // TODO Auto-generated method stub
            DismissDialog();
            // refresh();
            // if (iEventListener != null) {
            // try {
            // iEventListener.eventProcess(eventTypep, objectsp);
            // } catch (HDException e) {
            // logger.error(e.getMessage());
            // ToastUtils.imgToast(getActivity(),
            // R.drawable.hd_hse_common_msg_wrong, "程序报错，请联系管理员");
            // }
            // }

            refreshViewControl(curApproveNode);
            // 如果使用手签，保存图片信息
            if (isTPQM) {
                PaintSignatureActivity.saveImageToDB(isItemByItem,
                        curApproveNode, workOrder, null, image);
            }
            if (curApproveNode != null) {
                issave = true;
                exmaineDialog.setCurrentEntity(curApproveNode);
                if (curApproveNode.getIsend() == 1) {
                    //措施确认里每次刷卡或者操作后到最后环节点


                    String ud_zyxk_zysqid = workOrder.getUd_zyxk_zysqid();
                    String zylocation = curApproveNode.getZylocation();
                    List<Integer> xzlist = new ArrayList<>();
                    for (int i = 0; i < curChangeData.size(); i++) {
                        Integer checkresult = (Integer) curChangeData.get(i).getAttribute("checkresult");
                        xzlist.add(checkresult);
                    }
                    hm.put(ud_zyxk_zysqid + "&" + zylocation, xzlist);
                    SystemProperty.getSystemProperty().setHs(hm);


                }
                // 保存拍照信息
                if (!isTPQM) {

                    if (currentTabPADConfigInfo != null
                            && currentTabPADConfigInfo instanceof PDAWorkOrderInfoConfig) {
                        if (image != null) {
                            ImageUtils mImageUtils = new ImageUtils();
                            mImageUtils
                                    .saveImage(
                                            (PDAWorkOrderInfoConfig) currentTabPADConfigInfo,
                                            curApproveNode, workOrder, image);
                        }

                    }
                }

            }
        }
    };

    /**
     * TODO 转换成标准实体类型 changeToSuperEntity:(). date: 2014年10月22日
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
     * isCheckSaveData:(是否检查要保存的数据). <br/>
     * date: 2015年2月28日 <br/>
     *
     * @return
     * @author lxf
     */
    public boolean isCheckSaveData() {
        return true;
    }

    /**
     * getIAtionType:(获取动作类型). <br/>
     * date: 2014年11月4日 <br/>
     *
     * @return
     * @author lxf
     */
    public abstract String getIAtionType();

    /**
     * getMeasureClassName:(获取业务对象的包名URL). <br/>
     * date: 2015年2月2日 <br/>
     *
     * @return
     * @author lxf
     */
    public abstract String getMeasureClassName();

    /**
     * getSaveDatalist:(获取措施保存数据集合). <br/>
     * date: 2015年2月2日 <br/>
     *
     * @return
     * @author lxf
     */
    public abstract List<SuperEntity> getSaveDatalist();

    /**
     * getMapParam:(获取保存时的参数对象). <br/>
     * date: 2014年11月12日 <br/>
     *
     * @return
     * @author lxf
     */
    public abstract Map<String, Object> getMapParam();

    /**
     * getIEventLsn:(). <br/>
     * date: 2015年2月11日 <br/>
     *
     * @return
     * @author lxf
     */
    public IEventListener getDialogIEventLsn() {
        return null;
    }

    /**
     * refreshViewPage:(刷新ViewControl控件). <br/>
     * date: 2015年2月3日 <br/>
     *
     * @author lxf
     */
    public abstract void refreshViewControl(Object... objects);

}
