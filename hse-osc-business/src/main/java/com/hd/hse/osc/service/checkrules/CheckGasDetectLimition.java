/***********************************************************************
 * Module:  CheckGasDetectLimition.java
 * Author:  Administrator
 * Purpose: Defines the Class CheckGasDetectLimition
 ***********************************************************************/

package com.hd.hse.osc.service.checkrules;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.entity.workorder.WorkApprovalPermission;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;
import com.hd.hse.utils.UtilTools;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 气体检测时效判断，会签调用 查询pAD端数据库
 *
 * @pdOid 6c26affc-def9-42d5-a03f-db7d7128b0d7
 */
public class CheckGasDetectLimition extends AbstractCheckListener {
    /**
     * @param action
     * @param args
     * @throws HDException
     * @pdOid 08717b33-edc0-4165-aeb1-1c3f315e8d7a
     */
    public Object action(String action, Object... args) throws HDException {
        // TODO Auto-generated method stub
        String zysqid = "";// 票的id
        String zypclass = "";// 作业票类型
        String zylevel = "";// 作业等级
        Map<String, Object> mapParas = objectCast(args[0]);
        WorkApprovalPermission approvalObject = (WorkApprovalPermission) UtilTools
                .judgeMapValue(mapParas, WorkApprovalPermission.class, false);
        if (approvalObject != null && approvalObject.getIsend() != 1)
            return null;
        if (mapParas.containsKey(WorkOrder.class.getName())) {
            Object obj = mapParas.get(WorkOrder.class.getName());
            if (obj instanceof SuperEntity) {
                WorkOrder workorder = (WorkOrder) obj;
                zysqid = workorder.getUd_zyxk_zysqid();
                zypclass = workorder.getZypclass();
                zylevel = workorder.getZylevel() == null ? "" : workorder
                        .getZylevel();
                if (zysqid.length() == 0) {
                    throw new AppException(UNKNOW_ERROR);
                }
                Integer isqtjc = workorder.getIsqtjc();
                if (isqtjc != null && isqtjc == 1) {
                    checkGasTimeLimit(zysqid, zypclass, zylevel);
                }
            }
        }
        return super.action(action, args);
    }

    /**
     * checkGasTimeLimit:(读取配置信息,签批时间<检测时间+配置的时间（时效）). <br/>
     * date: 2014年10月18日 <br/>
     *
     * @throws HDException
     * @author ZhangJie
     */
    private void checkGasTimeLimit(String zysqid, String wttype, String wtlevel)
            throws HDException {
        // TODO Auto-generated method stub
        // 读取数据库配置的检测时效信息
        String sx = "";
        String sql = "select sx from ud_zyxk_qtjcsx where ifnull(wtlevel,'')='"
                + wtlevel + "' and wttype='" + wttype + "' and dr=0";
        Map<String, Object> map = getMapResult(sql);
        if (map.isEmpty()) {
            sx = "30";
        } else {
            sx = map.get("sx").toString();
            if (sx.length() == 0) {
                sx = "0";
            }
        }
        int minutes = Integer.parseInt(sx);
        // 读取数据库检测时间
        sql = "select jctime from ud_zyxk_qtjc where ud_zyxk_zysqid='" + zysqid
                + "' order by jctime desc";
        map = getMapResult(sql);
        if (!map.isEmpty()) {
            map = getMapResult(sql);
            String jctime = map.get("jctime").toString();
            Date jctimeDate = ParseToDate(jctime);
            Calendar tmptime = Calendar.getInstance();
            tmptime.setTime(jctimeDate);
            tmptime.add(Calendar.MINUTE, minutes);
            // 获得当前时间(签批时间)
            Calendar now = Calendar.getInstance();
            now.setTime(ServerDateManager.getCurrentDate());
            if (tmptime.compareTo(now) <= 0) {
                throw new AppException("检测时间已失效");
            }
        } else {
            throw new AppException("气体检测没有完成");
        }
    }
}