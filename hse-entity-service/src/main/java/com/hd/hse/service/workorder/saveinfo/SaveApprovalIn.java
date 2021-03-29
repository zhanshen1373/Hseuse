/***********************************************************************
 * Module:  SaveApprovalIn.java
 * Author:  zhaofeng
 * Purpose: Defines the Class SaveApprovalIn
 ***********************************************************************/

package com.hd.hse.service.workorder.saveinfo;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dao.SequenceGenerator;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkApprovalPersonRecord;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 保存审批信息，
 *
 * @pdOid acbcaf12-1574-48a7-b728-4a7667c1f7eb
 */
public class SaveApprovalIn extends AbstractCheckListener {
    /**
     * @param action
     * @param object
     * @throws HDException
     * @pdOid 442bb90c-2f95-460a-8bbf-9a96661ab833
     */
    public Object action(String action, Object... object) throws HDException {
        // TODO: implement
        WorkApprovalPermission cloneApproval = null;// 克隆对象
        WorkApprovalPersonRecord personRecord = new WorkApprovalPersonRecord();
        Map<String, Object> map = objectCast(object[0]);
        WorkApprovalPermission approval = (WorkApprovalPermission) UtilTools
                .judgeMapValue(map, WorkApprovalPermission.class, false);
        WorkOrder workOrder = (WorkOrder) UtilTools.judgeMapValue(map,
                WorkOrder.class, true);
        if (approval == null)
            return null;
        PDAWorkOrderInfoConfig workConfig = (PDAWorkOrderInfoConfig) UtilTools
                .judgeMapValue(map, PDAWorkOrderInfoConfig.class, false);
        if (workConfig != null && workConfig.getConlevel() != null
                && workConfig.getConlevel() == 1)
            return null;// 逐条非批量不保存审批记录

        // 克隆
        try {
            Map<String, Object> cloneMap = objectCast(object[1]);//
            cloneApproval = (WorkApprovalPermission) approval.clone();
            cloneMap.put(WorkApprovalPermission.class.getName(), cloneApproval);
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            throw new HDException("系统错误，请联系管理员！");
        }

        //初始值是空的
        personRecord.setUd_zyxk_zyspryjlid(approval.getUd_zyxk_zyspryjlid());
        if (workConfig != null)
            personRecord.setType(workConfig.getPscode());
        personRecord.setSpnode(approval.getSpfield());
        personRecord.setValue(approval.getSpfield_desc());
        personRecord.setSptime(UtilTools.getSysCurrentTime());
        personRecord.setPerson(approval.getPersonid());
        personRecord.setPerson_name(approval.getPersondesc());
        personRecord.setDycode(approval.getZylocation());
        personRecord.setUd_zyxk_zysqid(workOrder.getUd_zyxk_zysqid());
        personRecord.setJob(approval.getJob());
        personRecord.setCode(approval.getCode());
        personRecord.setZcproject(approval.getZcproject());
        personRecord.setIsupload(0);
        personRecord.setUd_zyxk_zyspqxid(approval.getUd_zyxk_zyspqxid());

        cloneApproval.setSptime(personRecord.getSptime());
        cloneApproval.setDefaultpersonid(approval.getPersonid());
        cloneApproval.setDefaultpersondesc(approval.getPersondesc());
        saveApprovalIn(personRecord, cloneApproval);
        //这也是空的
        cloneApproval.setUd_zyxk_zyspryjlid(personRecord
                .getUd_zyxk_zyspryjlid());
        return null;
    }

    private void saveApprovalIn(WorkApprovalPersonRecord personRecord,
                                WorkApprovalPermission cloneApprovalPermission) throws HDException {
        try {

            if (!StringUtils.isEmpty(personRecord.getUd_zyxk_zyspryjlid())) {
                // 走更新
                dao.updateEntity(connection, personRecord, new String[]{
                        "person_name", "person", "sptime", "job", "code", "zcproject", "isupload", "ud_zyxk_zyspryjlid"});
            } else {
                // 走新增
                SequenceGenerator
                        .genPrimaryKeyValue(new SuperEntity[]{personRecord});
                cloneApprovalPermission.setUd_zyxk_zyspryjlid(personRecord
                        .getUd_zyxk_zyspryjlid());
                //审批人员记录表有主键了
                dao.insertEntity(connection, personRecord);
            }
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            throw new HDException("审批信息保存失败！", e);
        }
    }


}