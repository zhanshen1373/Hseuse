/**
 * Project Name:hse-entity-service
 * File Name:CheckDelayEffectiveTime.java
 * Package Name:com.hd.hse.service.workorder.checkrules
 * Date:2014年11月4日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 */

package com.hd.hse.dly.service.checkrules;

import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.dao.connection.IConnection;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkDelay;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:CheckDelayEffectiveTime (作业票延期失效校验).<br/>
 * Date: 2014年11月4日 <br/>
 *
 * @author ZhangJie
 * @see
 */
public class CheckDelayEffectiveTime extends AbstractCheckListener {

    public CheckDelayEffectiveTime() {

    }

    public CheckDelayEffectiveTime(IConnection connection) {
        super(connection);
    }

    @Override
    public Object action(String action, Object... args) throws HDException {
        if (action.equalsIgnoreCase(IActionType.ACTION_TYPE_DELAYSIGN)) {
            Map<String, Object> mapParas = objectCast(args[0]);
            // 作业票
            Object objWorkorder = UtilTools.judgeMapValue(mapParas,
                    WorkOrder.class, false);
            WorkOrder workorder = (WorkOrder) objWorkorder;
            // 延期表
            Object obj = UtilTools.judgeMapValue(mapParas, WorkDelay.class,
                    false);
            WorkDelay workDelay = (WorkDelay) obj;
            // 环节点
            Object objApproval = UtilTools.judgeMapValue(mapParas,
                    WorkApprovalPermission.class, false);
            // WorkApprovalPermission approval = (WorkApprovalPermission)
            // objApproval;
            // 没有审批环节点或最终审批人时，校验
            // approval.getIndex()==0
            checkDelayEffectiveTime(workDelay, workorder);
        }
        return super.action(action, args);
    }

    /**
     * checkDelayEffectiveTime:(延期最大有效期校验). <br/>
     * date: 2014年11月5日 <br/>
     *
     * @param workDelay
     * @param workorder
     * @throws HDException
     * @author ZhangJie
     */
    private void checkDelayEffectiveTime(WorkDelay workDelay,
                                         WorkOrder workorder) throws HDException {
        // TODO Auto-generated method stub table:PrescriptionFrequencyConfigure
        String yqendtime = workDelay.getYqendtime();// 延期时间
        String zypclass = workorder.getZypclass();// 作业票类别
        String zylevel = workorder.getZylevel() == null ? "" : workorder
                .getZylevel();// 作业票级别
        String workstarttime = workorder.getZystarttime();// 作业票实际结束时间
        String zystarttime = workDelay.getYqstarttime();
        checkStarttimeInterval(zystarttime);
        String ud_zyxk_sxpcpzid = workorder.getUd_zyxk_sxpcpzid();
        // 延期开始时间应该大于等于作业票的实际结束时间
        if (ParseToCalendar(workDelay.getYqstarttime()).compareTo(
                ParseToCalendar(workorder.getSjendtime())) < 0) {
            throw new AppException("请设置延期开始时间大于等于作业实际结束时间!");
        }
        // 判断延期结束时间大于延期开始时间
        if (ParseToCalendar(yqendtime).compareTo(ParseToCalendar(zystarttime)) <= 0) {
            throw new AppException("请设置延期结束时间大于延期开始时间!");
        }
        if (!isRelationAction(IRelativeEncoding.SXPCJY))
            return;
        String sql = "";
        if (null != ud_zyxk_sxpcpzid && !"".equals(ud_zyxk_sxpcpzid)
                && !"0".equals(ud_zyxk_sxpcpzid)) {
            // 大连一票制特殊处理
            sql = "select zqx, maxyxq from ud_zyxk_sxpcpz where ud_zyxk_sxpcpzid = '"
                    + ud_zyxk_sxpcpzid + "'";
        } else {
            sql = "select zqx, maxyxq from ud_zyxk_sxpcpz where jobtype='"
                    + zypclass
                    + "' and ',' || ifnull(zylevel,'') || ',' like  '%,' || '"
                    + zylevel + "' || ',%'   and ifnull(dr,0) = 0";
        }

        Map<String, Object> map = getMapResult(sql);
        int effectivehours = Integer.parseInt(map.get("maxyxq") == null ? "0"
                : map.get("maxyxq").toString());// 有效小时数
        int zqx = Integer.parseInt(map.get("zqx") == null ? "0" : map
                .get("zqx").toString());// 有效小时数
        int maxZqx = Math.max(effectivehours, zqx);
        if (maxZqx == 0)
            return;

        String sqq = "select isqy from sys_relation_info where sys_type='IS_YQ_DCYX'";
        Map<String, Object> mapp = getMapResult(sqq);
        int isqy = -1;
        if (mapp != null && mapp.containsKey("isqy")) {

            isqy = (int) mapp.get("isqy");

        }

        if (isqy == 1) {
            //去掉单次有效期校验
        } else {
            // 单次有效期判断
            if (effectivehours > 0 && effectivehours != maxZqx) {
                // 表示具有单次最大有效期的概念
                Calendar effectiveTime = ParseToCalendar(zystarttime);
                effectiveTime.add(Calendar.HOUR_OF_DAY, effectivehours);
                if (ParseToCalendar(yqendtime).compareTo(effectiveTime) > 0)// 超出延期时效
                {
                    throw new AppException("延期时间超出最大单次有效期!");
                }
            }

        }

        if (maxZqx > 0) {
            // 延期时间小于计划开始时间+有效期-总有效期判断
            Calendar effectiveTime = ParseToCalendar(workstarttime);
            effectiveTime.add(Calendar.HOUR_OF_DAY, maxZqx);
            if (ParseToCalendar(yqendtime).compareTo(effectiveTime) > 0)// 超出延期时效
            {
                throw new AppException("延期时间超出最大有效期!");
            }
        }
    }

    private void checkStarttimeInterval(String startTime) throws HDException {
        // 从关系表查询delayinterval，若启用。判断时间间隔，
        String sql = "select Input_value, isqy from sys_relation_info where sys_type ='DELAYINTERVAL'";
        try {
            HashMap<String, Object> result = (HashMap<String, Object>) getMapResult(sql);
            if (result.containsKey("isqy")) {
                int isqy = (Integer) result.get("isqy");
                if (isqy == 1) {
                    String sStartTime = startTime;
                    Calendar c = Calendar.getInstance();
                    c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            .parse(sStartTime));
                    long lStartTime = c.getTimeInMillis();
                    if (result.containsKey("input_value")) {
                        try {
                            if (null == result.get("input_value")) {
                                return;
                            }
                            int delayinterval = Integer
                                    .parseInt((String) result
                                            .get("input_value"));
                            if (lStartTime - ServerDateManager.getCurrentTimeMillis() > delayinterval * 60 * 1000) {
                                throw new HDException("延期开始时间与当前时间间隔大于"
                                        + delayinterval + "分钟");
                            }
                        } catch (ClassCastException e) {
                            e.printStackTrace();
                            throw new HDException(e.toString());
                        }

                    }

                }
            }
        } catch (DaoException e) {
            e.printStackTrace();
            throw new HDException(e.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new HDException(e.toString());
        }
    }
}
