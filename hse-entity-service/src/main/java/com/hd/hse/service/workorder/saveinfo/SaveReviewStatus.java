/***********************************************************************
 * Module:  SaveReviewStatus.java
 * Author:  zhaofeng
 * Purpose: Defines the Class SaveReviewStatus
 ***********************************************************************/

package com.hd.hse.service.workorder.saveinfo;

import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkMeasureReview;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

import java.util.Map;

/**
 * 更新复查状态。。。
 *
 * @pdOid 4b1372c0-b197-402c-9e21-6d8e214253af
 */
public class SaveReviewStatus extends AbstractCheckListener {
    /**
     * @param action
     * @param object
     * @throws HDException
     * @pdOid fb2c390f-ebdb-47ce-b378-129f0a90a874
     */
    public Object action(String action, Object... object) throws HDException {
        // TODO: implement
        Map<String, Object> map = objectCast(object[0]);
        WorkApprovalPermission approval = (WorkApprovalPermission) UtilTools
                .judgeMapValue(map, WorkApprovalPermission.class, false);

        if (approval != null && approval.getIsend() != 1) {
            return null;
        }

        WorkMeasureReview cloneMeasureReview = null;
        Map<String, Object> cloneMap = objectCast(object[1]);//
        if (cloneMap != null)
            cloneMeasureReview = (WorkMeasureReview) cloneMap
                    .get(WorkMeasureReview.class.getName());
        if (cloneMeasureReview == null)
            cloneMeasureReview = (WorkMeasureReview) UtilTools.judgeMapValue(
                    map, WorkMeasureReview.class, true);

        if (cloneMeasureReview.getChilds() != null) {
            if (cloneMeasureReview.getChilds().get("com.hd.hse.entity.base.MeasureReviewSub") != null) {
                if (cloneMeasureReview.getChilds().get("com.hd.hse.entity.base.MeasureReviewSub").size() == 0) {
                    //盲板抽堵票没有措施，用这个区分
                    cloneMeasureReview.setCsnum(0);
                }
            }
        }


        saveReviewStatus(action, cloneMeasureReview);
        return null;
    }

    private void saveReviewStatus(String action, WorkMeasureReview measureReview)
            throws HDException {
        try {
            if (measureReview.getCsnum() <= 0) {// 措施都已经完成
                measureReview.setStatus(IWorkOrderStatus.REVIEW_STATUS_FINISH);
                measureReview.setIsupload(0);
                dao.updateEntity(connection, measureReview,
                        new String[]{"status", "isupload"});
            }
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            throw new HDException("保存复查状态信息失败！");
        }
    }

}