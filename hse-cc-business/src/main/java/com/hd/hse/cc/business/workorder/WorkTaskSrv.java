/**
 * Project Name:hse-osc-app
 * File Name:WorkTaskSrv.java
 * Package Name:com.hd.hse.osc.business.workorder
 * Date:2014年9月28日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 */

package com.hd.hse.cc.business.workorder;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.common.PositionCard;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.service.workorder.WorkTaskDBSrv;
import com.hd.hse.system.SystemProperty;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * ClassName:WorkTaskSrv (关闭取消作业票列表数据查询服务).<br/>
 * Date: 2014年9月28日 <br/>
 *
 * @author lg
 * @see
 */
public class WorkTaskSrv extends WorkTaskDBSrv {

    @Override
    protected String getWorkTaskExtWhere() throws HDException {
        // TODO Auto-generated method stub
        return super.getWorkTaskExtWhere();
    }

    @Override
    protected String getWorkOrderExtWhere() throws HDException {
        // TODO Auto-generated method stub
        IQueryRelativeConfig relationConfig = new QueryRelativeConfig();
        String dateTime = SystemProperty.getSystemProperty().getSysDateTime();
        RelationTableName config = new RelationTableName();
        config.setSys_type(IRelativeEncoding.HCSJPZ);
        RelationTableName relation = relationConfig.getRelativeObj(config);
        int hcsj = 0;
        if (relation != null
                && !StringUtils.isEmpty(relation.getInput_value())
                && NumberUtils.isNumber(relation.getInput_value())) {
            hcsj = Integer.valueOf(relation.getInput_value());
        }
        PositionCard positionCard = SystemProperty.getSystemProperty()
                .getPositionCard();
        StringBuilder sbWhere = new StringBuilder();
        sbWhere.append("zysq.status = '").append(IWorkOrderStatus.APPR)
                .append("'");// 作业中
//		sbWhere.append(" 1=1 ");
        if (positionCard != null)
            sbWhere.append(" and zysq.zylocation = '")
                    .append(positionCard.getLocation()).append("'");// 位置卡
        sbWhere.append(" and (julianday('" + ServerDateManager.getCurrentTime() + "')-julianday(sjendtime))*24*60< ").append(hcsj).append(" ");// 作业实际结束时间
        sbWhere.append(" and ifnull(zysq.SPSTATUS,'') !='SPAPPR'");
        sbWhere.append(" order by zysq.zystarttime desc");// 排序
        return sbWhere.toString();
    }

}
