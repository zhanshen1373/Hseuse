package com.hd.hse.cc.service.saveinfo;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

import java.util.Map;

/**
 * Created by dubojian on 2018/6/21.
 */

public class SaveWorkInterruptEndReason extends AbstractCheckListener {

    /**
     * @param action
     * @param objects
     * @pdOid 209fb421-7171-4665-abd5-60e6e0daada6
     */
    public Object action(String action, Object... object) throws HDException {
        // TODO: implement
        Map<String, Object> map = objectCast(object[0]);
        Object workObject = UtilTools.judgeMapValue(map, WorkOrder.class, true);
        Object approvalObject = UtilTools.judgeMapValue(map,
                WorkApprovalPermission.class, false);
        saveWorkInterruptEndReason((WorkOrder) workObject,
                approvalObject == null ? null
                        : (WorkApprovalPermission) approvalObject);
        return null;
    }

    private void saveWorkInterruptEndReason(WorkOrder workOrder,
                                     WorkApprovalPermission approvalPermission) throws HDException {
        try {
            if (approvalPermission == null
                    || approvalPermission.getIsend() == 1) {
                dao.updateEntity(connection, workOrder, new String[]{
                        "pausestatus"});
            }
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            throw new HDException("更新作业申请中的是否暂停字段的值失败！");
        }
    }


}
