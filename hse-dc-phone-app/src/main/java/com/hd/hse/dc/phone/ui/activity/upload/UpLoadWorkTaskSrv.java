/**
 * Project Name:hse-dc-app
 * File Name:UpLoadWorkTaskSrv.java
 * Package Name:com.hd.hse.dc.ui.activity.list.upload.datasource
 * Date:2014年10月10日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 */
package com.hd.hse.dc.phone.ui.activity.upload;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.service.workorder.WorkTaskDBSrv;
import com.hd.hse.system.SystemProperty;

/**
 * ClassName: UpLoadWorkTaskSrv ()<br/>
 * date: 2014年10月10日 <br/>
 *
 * @author zhaofeng
 * @version
 */
public class UpLoadWorkTaskSrv extends WorkTaskDBSrv {

    /**
     * TODO 增加上传列表中作业票显示条件
     *
     * @see com.hd.hse.service.workorder.WorkTaskDBSrv#getWorkOrderExtWhere()
     */
    @Override
    protected String getWorkOrderExtWhere() throws HDException {
        // TODO Auto-generated method stub
        PersonCard personCard = SystemProperty.getSystemProperty()
                .getLoginPerson();
        // 是否允许异步审批
        IQueryRelativeConfig relation = new QueryRelativeConfig();
        RelationTableName relationEntity = new RelationTableName();
        relationEntity.setSys_type(IRelativeEncoding.ISASYNCAPPR);
        boolean isAsyncAppr = relation.isHadRelative(relationEntity);
        StringBuilder sbWhere = new StringBuilder();
        if (isAsyncAppr) {
            sbWhere.append(" zysq.needupload = 1 and isupload = 0");
            sbWhere.append(
                    " or (ifnull(zysq.spstatus,'') !='' and ifnull(isupload,0)=0 and zysq.spstatus!='")
                    .append(IWorkOrderStatus.VIRTUAL_UPLOAD).append("'");
            sbWhere.append(" and zysq.status!='")
                    .append(IWorkOrderStatus.INPRG).append("' )");
            return sbWhere.toString();
        }
        sbWhere.append("( zysq.status='").append(IWorkOrderStatus.APPAUDITED);
        sbWhere.append(
                "' or (ifnull(zysq.spstatus,'') !='' and ifnull(isupload,0)=0 and ( zysq.spstatus!='")
                .append(IWorkOrderStatus.VIRTUAL_UPLOAD).append("'").append("or ifnull(zysq.ispause,0)=1 )");//增加作业票暂停状态可上传
        sbWhere.append(" and zysq.status!='").append(IWorkOrderStatus.INPRG)
                .append("' ))");
        // if (personCard != null)
        // sbWhere.append(" and zysq.sddept ='")
        // .append(personCard.getDepartment()).append("'");
        sbWhere.append(" order by zysq.zystarttime desc");// 排序
        return sbWhere.toString();
    }

}
