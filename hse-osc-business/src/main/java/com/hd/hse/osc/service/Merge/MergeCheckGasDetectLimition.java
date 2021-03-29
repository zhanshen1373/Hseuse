package com.hd.hse.osc.service.Merge;

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
import java.util.List;
import java.util.Map;

/**
 * 合并会签 气体检测时效判断，会签调用 查询pAD端数据库 ClassName: MergeCheckGasDetectLimition ()<br/>
 * date: 2015年9月6日 <br/>
 *
 * @author lxf
 */
public class MergeCheckGasDetectLimition extends AbstractCheckListener {

    public Object action(String action, Object... args) throws HDException {
        // TODO Auto-generated method stub
        Map<String, Object> mapParas = objectCast(args[0]);
        WorkApprovalPermission approvalObject = (WorkApprovalPermission) UtilTools
                .judgeMapValue(mapParas, WorkApprovalPermission.class, false);
        // 此处要判断环节点是否是最终刷卡人，如果是校验气体检测时效判断
        String strid = approvalObject.getStrisend();
        if (strid != null && strid.length() > 0) {
            String[] strisend = strid.split(",");
            String[] strzysqid = approvalObject.getStrzysqid().split(",");
            if (strisend.length != strzysqid.length) {
                throw new AppException("读取数据库，组织数据异常,请联系管理员");
            }
            int i = 0;
            for (String isend : strisend) {
                if (isend != null && (isend.equalsIgnoreCase("1") || isend.equalsIgnoreCase("'1'"))) {
                    // 此时需要校验
                    if (mapParas.containsKey(WorkOrder.class.getName())) {
                        Object obj = mapParas.get(WorkOrder.class.getName());
                        // 验证集合
                        if (obj instanceof List) {
                            @SuppressWarnings("unchecked")
                            List<SuperEntity> listSuper = (List<SuperEntity>) obj;
                            for (SuperEntity superEntity : listSuper) {
                                if (("'" + superEntity
                                        .getAttribute(
                                                superEntity.getPrimaryKey())
                                        .toString() + "'")
                                        .equalsIgnoreCase(strzysqid[i]))
                                    checkGas((WorkOrder) superEntity);
                            }
                        }
                    }
                }
                i++;
            }
        }
        return super.action(action, args);
    }

    /**
     * checkGas:(气体检测校验). <br/>
     * date: 2015年9月6日 <br/>
     *
     * @param workorder
     * @throws HDException
     * @author lxf
     */
    private void checkGas(WorkOrder workorder) throws HDException {
        String zysqid = "";// 票的id
        String zypclass = "";// 作业票类型
        String zylevel = "";// 作业等级

        zysqid = workorder.getUd_zyxk_zysqid();
        zypclass = workorder.getZypclass();
        zylevel = workorder.getZylevel() == null ? "" : workorder.getZylevel();
        if (zysqid.length() == 0) {
            throw new AppException(UNKNOW_ERROR);
        }
        Integer isqtjc = workorder.getIsqtjc();
        if (isqtjc != null && isqtjc == 1) {
            checkGasTimeLimit(zysqid, zypclass, zylevel);
        }
    }

    /**
     * checkGasTimeLimit:(读取配置信息,签批时间<检测时间+配置的时间（时效）). <br/>
     * date: 2015年9月6日 <br/>
     *
     * @param zysqid
     * @param wttype
     * @param wtlevel
     * @throws HDException
     * @author lxf
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
