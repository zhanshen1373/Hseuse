/**
 * Project Name:hse-entity-service
 * File Name:CheckTabStatusForBack.java
 * Package Name:com.hd.hse.service.workorder.checkrules
 * Date:2014年11月10日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 */

package com.hd.hse.osc.service.checkrules;

import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IConfigEncoding;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.dao.result.IProcessResultSet;
import com.hd.hse.entity.base.PDAWorkOrderInfoConfig;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.utils.UtilTools;

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * ClassName:CheckTabStatusForBack (退回作业票之前先判断危害、PPE、措施是否已审核，若已完成，则不允许退回).<br/>
 * Date: 2014年11月10日 <br/>
 *
 * @author lg
 * @see
 */
public class CheckTabStatusForBack extends AbstractCheckListener {

    /**
     * TODO
     *
     * @see com.hd.hse.service.workorder.checkrules.AbstractCheckListener#action(String,
     * Object[])
     */
    @Override
    public Object action(String action, Object... args) throws HDException {
        Map<String, Object> mapParas = objectCast(args[0]);
        WorkOrder workOrder = (WorkOrder) UtilTools.judgeMapValue(mapParas,
                WorkOrder.class, true);
        if (workOrder.getCssavefied() != null
                && !StringUtils.isEmpty(workOrder.getCssavefied())) {
            checkTabStatus(workOrder);
        } else {
            //作业票浏览时验证
            String sql = "select zypclass,cssavefied,isqtjc,zyptype from ud_zyxk_zysq where ud_zyxk_zysqid='" + workOrder.getUd_zyxk_zysqid() + "'";
            IProcessResultSet processRs = new EntityResult(WorkOrder.class);
            WorkOrder browseWorkOrder = (WorkOrder) dao.executeQuery(connection, sql, processRs);
            checkTabStatus(browseWorkOrder);
        }
        return super.action(action, args);
    }

    /**
     * checkTabStatus:(判断危害、个人防护装备、措施的确认状态，如果都确认，则不允许退回作业票). <br/>
     * date: 2014年11月10日 <br/>
     *
     * @param workOrder
     * @throws HDException
     * @author lg
     */
    private void checkTabStatus(WorkOrder workOrder) throws HDException {
        IQueryWorkInfo qry = new QueryWorkInfo(connection);
        List<PDAWorkOrderInfoConfig> lstConfig = qry.queryWorkInfoConfigInfo(
                workOrder, IConfigEncoding.SP, null);
        if (lstConfig != null && !lstConfig.isEmpty()) {
            boolean finish = true;
            StringBuilder sbErr = new StringBuilder();
            String cssavefied = workOrder.getCssavefied() == null ? "" : workOrder.getCssavefied();//
            for (PDAWorkOrderInfoConfig config : lstConfig) {
                /*if (config.getContype().equals(IConfigEncoding.HARM_TYPE)) {
                    sbErr.append("危害、");
					if (!cssavefied.contains(String
							.valueOf(IConfigEncoding.HARM_TYPE))) {
						finish = false;
						break;
					}
				} else if (config.getContype().equals(IConfigEncoding.PPE_TYPE)) {
					sbErr.append("个人防护装备、");
					if (!cssavefied.contains(String
							.valueOf(IConfigEncoding.PPE_TYPE))) {
						finish = false;
						break;
					}
				} else */
                if (config.getContype().equals(
                        IConfigEncoding.MEASURE_TYPE)) {
                    sbErr.append("措施、");
                    if (!cssavefied.contains(String
                            .valueOf(IConfigEncoding.MEASURE_TYPE))) {
                        finish = false;
                        break;
                    }
                }
            }
            if (workOrder.getZypclass().equals("zylx89")) {

            } else {
                if (finish) {
                    if (sbErr.length() > 0) {
                        sbErr.setLength(sbErr.length() - 1);
                        sbErr.append("已确认，不允许退回！");
                    } else {
                        sbErr.append("此作业票不允许退回！");
                    }
                    throw new HDException(sbErr.toString());
                }
            }

        }
    }

}
