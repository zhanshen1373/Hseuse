/***********************************************************************
 * Module:  CheckConfirmAllPrecautions.java
 * Author:  ZhangJie
 * Purpose: Defines the Class CheckConfirmAllPrecautions
 ***********************************************************************/

package com.hd.hse.osc.service.checkrules;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.workorder.WorkApplyMeasure;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName: CheckConfirmAllPrecautions (校验措施确认情况：不能都选择不适用。措施确认时调用)<br/>
 * date: 2014年10月18日 <br/>
 *
 * @author ZhangJie
 */
public class CheckConfirmPrecautions extends AbstractCheckListener {

    /**
     * TODO
     *
     * @see com.hd.hse.service.workorder.checkrules.AbstractCheckListener#action(String,
     * Object[])
     */
    @SuppressWarnings("unchecked")
    public Object action(String action, Object... args) throws HDException {
        // 判断所有措施项都进行了确认，且不能全部不适用
        Object object = null;
        Map<String, Object> mapParas = objectCast(args[0]);

        PDAWorkOrderInfoConfig workConfig = (PDAWorkOrderInfoConfig) UtilTools
                .judgeMapValue(mapParas, PDAWorkOrderInfoConfig.class, true);
        // 逐条非批量时，不校验（会签时再校验）
        // if (workConfig.getConlevel() != null && workConfig.getConlevel() ==
        // 1) {
        // return object;
        // }
        // lxf 添加 获取当前环节点对象
        Object approvalObject = UtilTools.judgeMapValue(mapParas,
                WorkApprovalPermission.class, false);
        List<WorkApplyMeasure> measureList = (List<WorkApplyMeasure>) UtilTools
                .judgeMapListValue(mapParas, WorkApplyMeasure.class, true);
        checkMeasure(workConfig, measureList, approvalObject == null ? null
                : (WorkApprovalPermission) approvalObject);// 非逐条校验
        return object;
    }

    /**
     * checkMeasure:(检验). <br/>
     * date: 2014年10月30日 <br/>
     *
     * @param workConfig
     * @param measureList
     * @throws HDException
     * @author zhaofeng
     */
    private void checkMeasure(PDAWorkOrderInfoConfig workConfig,
                              List<WorkApplyMeasure> measureList, WorkApprovalPermission approval)
            throws HDException {
        boolean isOperate = false;
        String desc = "";
        for (int i = 0; i < measureList.size(); i++) {
            desc = measureList.get(i).getDescription();
            if (measureList.get(i).getCheckresult() == null
                    || measureList.get(i).getCheckresult() == -1) {
                throw new HDException(measureList.get(i).getDescription()
                        + "没有操作，不能保存！");
            } else if (measureList.get(i).getCheckresult() == 1
                    && (desc.replace(" ", "").contains("()") || desc.replace(
                    " ", "").contains("（）"))) {
                throw new HDException(measureList.get(i).getDescription()
                        + "为必填项，未填写，不能保存！");
            }
            if (measureList.get(i).getCheckresult() == 1
                    || measureList.get(i).getCheckresult() == 0) {
                isOperate = true;
            }
            // lxf 添加 非逐条、isend时判断措施中是否包含否的，如果包含，不准许通过
            if (measureList.get(i).getCheckresult() == 0 && approval != null
                    && approval.getIsend() == 1
                    && workConfig.getConlevel() != null
                    && workConfig.getConlevel() != 1) {
                throw new HDException(measureList.get(i).getDescription()
                        + "  不能为“否”");
            }
        }
        if (workConfig.getConlevel() != null && workConfig.getConlevel() == 1) {
            isOperate = true;
        }
        if (workConfig.getIscscheck() == null) {
            workConfig.setIscscheck(0);
        }
        if (!isOperate && workConfig.getIscscheck() != 1) {
            throw new HDException("至少有一项为“是”或者“否”！");
        }

        //存在PC标记的对象
        List<WorkApplyMeasure> mSelectedValues = new ArrayList<WorkApplyMeasure>();
        for (int i = 0; i < measureList.size(); i++) {
            WorkApplyMeasure t = measureList.get(i);
            if (t.getAttribute("isselect") != null && (int) t.getAttribute("isselect") != 0) {
                mSelectedValues.add(t);
            }
        }
        if (mSelectedValues.size() > 0) {
            for (int i = 0; i < mSelectedValues.size(); i++) {
                if ((int) mSelectedValues.get(i).getAttribute("checkresult") != 1) {
                    throw new HDException("PC端勾选措施，不能为不适用！");
                }
            }

        }


    }

}