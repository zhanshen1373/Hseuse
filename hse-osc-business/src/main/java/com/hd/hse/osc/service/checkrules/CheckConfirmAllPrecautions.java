/***********************************************************************
 * Module:  CheckConfirmAllPrecautions.java
 * Author:  ZhangJie
 * Purpose: Defines the Class CheckConfirmAllPrecautions
 ***********************************************************************/

package com.hd.hse.osc.service.checkrules;

import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

import java.util.List;
import java.util.Map;

/**
 * ClassName: CheckConfirmAllPrecautions (校验措施确认情况：不允许存在为否的措施，措施不允许都为不适用。会签调用。)<br/>
 * date: 2014年10月18日 <br/>
 *
 * @author ZhangJie
 */
public class CheckConfirmAllPrecautions extends AbstractCheckListener {

    /**
     * TODO
     *
     * @see com.hd.hse.service.workorder.checkrules.AbstractCheckListener#action(String,
     * Object[])
     */
    public Object action(String action, Object... args) throws HDException {
        // 判断所有措施项都进行了确认，且不能全部不适用
        Object object = null;
        Map<String, Object> mapParas = objectCast(args[0]);
        // 会签-最终确认人
        Object objApproval = UtilTools.judgeMapValue(mapParas,
                WorkApprovalPermission.class, false);
        WorkApprovalPermission approval = (WorkApprovalPermission) objApproval;
        // 没有审批环节点或最终审批人时，校验
        if (approval == null || approval.getIsend() == 1) {
            String zysqid = "";
            WorkOrder workorder = (WorkOrder) UtilTools.judgeMapValue(mapParas,
                    WorkOrder.class, true);
            zysqid = workorder.getUd_zyxk_zysqid();
            if (zysqid == null || zysqid.length() == 0) {
                throw new AppException(UNKNOW_ERROR);
            }
            checkPrecaution(zysqid);
        }
        return object;
    }

    /**
     * checkPrecaution:(检查作业票所有措施确认情况). <br/>
     * 所有所示是否全部确认，不能为否 到pad数据库端查询，验证。 date: 2014年10月17日 <br/>
     *
     * @throws HDException
     * @author ZhangJie
     */
    private void checkPrecaution(String zysqid) throws HDException {
        // TODO Auto-generated method stub
        String sql = "select checkresult,description from ud_zyxk_zysq2precaution where ud_zyxk_zysqid='"
                + zysqid + "'";
        List<Map<String, Object>> list = super.getListMapResult(sql);
        String sqq = "select zypclass from ud_zyxk_zysq where ud_zyxk_zysqid='" + zysqid + "'";
        Map<String, Object> mapResult = super.getMapResult(sqq);
        /** 是否有未确认项 */
        String checkresultField = "checkresult";
        String descriptionField = "description";
        boolean bAllInably = true;// 措施项全为不适用
        for (Map<String, Object> map : list) {
            String value = map.get(checkresultField).toString();
            String description = map.get(descriptionField).toString();
            if (value.equalsIgnoreCase("-1")) {
                throw new AppException("措施[" + description + "]未落实");
            } else if (value.equalsIgnoreCase(PRECAUTIONS_VALUE_0)) {
                throw new AppException("措施[" + description + "]确认结果为否");
            } else if (value.equalsIgnoreCase(PRECAUTIONS_VALUE_1) && bAllInably) {
                bAllInably = false;
            }
        }
        if (!mapResult.get("zypclass").equals("zylx89")) {
            //盲板抽堵
            if (bAllInably) {
                throw new AppException("措施项不能全为不适用");
            }
        }

    }

}