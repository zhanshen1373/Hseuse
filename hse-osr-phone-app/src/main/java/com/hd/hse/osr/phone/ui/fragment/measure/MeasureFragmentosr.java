package com.hd.hse.osr.phone.ui.fragment.measure;

import android.annotation.SuppressLint;

import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.common.module.phone.ui.module.measurefragment.MeasureFragment;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.constant.ITableName;
import com.hd.hse.entity.base.MeasureReviewSub;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkApprovalPersonRecord;
import com.hd.hse.entity.workorder.WorkMeasureReview;
import com.hd.hse.osr.phone.ui.activity.workorder.WorkOrderActivity;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressLint("NewApi")
public class MeasureFragmentosr extends MeasureFragment {
    private WorkMeasureReview workMeasureReview;
    private static Logger logger = LogUtils.getLogger(MeasureFragmentosr.class);

    @SuppressLint("NewApi")
    @Override
    public List<SuperEntity> getMeasureList(SuperEntity currentTouchEntity) {
        List<SuperEntity> listMeasure = null;
        try {
            workMeasureReview = getQueryWorkInfo().queryReviewInfo(getWorkEntity(),
                    currentTouchEntity, null);
            ((WorkOrderActivity) getActivity()).workMeasureReview = workMeasureReview;
            listMeasure = workMeasureReview
                    .getChild(MeasureReviewSub.class.getName());
        } catch (HDException e) {
            logger.error(e);
            ToastUtils.toast(getActivity(), "读取措施信息报错,请联系管理员。");
        }
        return listMeasure;
    }

    @Override
    public String getChildKey() {
        // TODO Auto-generated method stub
        return MeasureReviewSub.class.getName();
    }

    @Override
    public String getIAtionType() {
        // TODO Auto-generated method stub
        return IActionType.ACTION_TYPE_RECHECKPRECAUTION;
    }

    @Override
    public String getMeasureClassName() {
        // TODO Auto-generated method stub
        return MeasureReviewSub.class.getName();
    }

    @Override
    public List<SuperEntity> getSaveDatalist() {
        return getOpSaveDatalist();
    }

    /**
     * appRecord:TODO(审批记录对象).
     */
    private WorkApprovalPersonRecord appRecord = null;

    @Override
    public Map<String, Object> getMapParam() {
        Map<String, Object> mapParam = null;
        // 审批记录表对
        getApproalWhereSuper();
        if (null == mapParam) {
            mapParam = new HashMap<String, Object>();
            // 措施主表信息
            mapParam.put(WorkMeasureReview.class.getName(), workMeasureReview);
            mapParam.put(WorkApprovalPersonRecord.class.getName(), appRecord);
        }
        return mapParam;
    }

    @Override
    public SuperEntity getApproalWhereSuper() {
        if (null == appRecord) {
            appRecord = new WorkApprovalPersonRecord();
            appRecord.setTableid(workMeasureReview.getZycsfcnum());
            appRecord.setTablename(ITableName.UD_ZYXK_ZYCSFC);
            if (null != currentTabPADConfigInfo) {
                String Str = (String) (currentTabPADConfigInfo.getAttribute("dycode") == null ? ""
                        : currentTabPADConfigInfo.getAttribute("dycode"));
                appRecord.setDycode(Str);
            }
        }
        return appRecord;
    }

    @Override
    public void refreshViewControl(Object... objects) {
        // TODO Auto-generated method stub
        if (objects != null) {
            if (objects[0] == null) {
                ((WorkOrderActivity) getActivity()).setNaviBarIsCompLete();
                updateValues();
            }
            if (objects[0] instanceof WorkApprovalPermission) {
                WorkApprovalPermission workapp = (WorkApprovalPermission) objects[0];
                if (workapp.getIsend() != null && workapp.getIsend() == 1) {
                    ((WorkOrderActivity) getActivity()).setNaviBarIsCompLete();
                }
            }
        }
    }

    int times = 0;
    boolean isRelation;

    @Override
    public boolean isRelation() {
        if (times == 0) {
            RelationTableName relationTableName = new RelationTableName();
            relationTableName.setSys_type(IRelativeEncoding.PCCSNOAPPLY);
            relationTableName.setSys_fuction(IConfigEncoding.FC);
            relationTableName.setSys_value(getWorkEntity() == null ? ""
                    : getWorkEntity().getZypclass());
            IQueryRelativeConfig relativeConfig = new QueryRelativeConfig();
            isRelation = relativeConfig.isHadRelative(relationTableName);
            times++;
        }
        return isRelation;

    }

}
