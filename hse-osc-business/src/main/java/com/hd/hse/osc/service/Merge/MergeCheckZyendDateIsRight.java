package com.hd.hse.osc.service.Merge;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ClassName: MergeCheckZyendDateIsRight (合并会签审核当前时间不能大于计划结束时间)<br/>
 * date: 2015年9月6日 <br/>
 *
 * @author lxf
 */
public class MergeCheckZyendDateIsRight extends AbstractCheckListener {

    public Object action(String action, Object... args) throws HDException {
        // TODO Auto-generated method stub

        Map<String, Object> mapParas = objectCast(args[0]);
        if (mapParas.containsKey(WorkOrder.class.getName())) {
            Object obj = mapParas.get(WorkOrder.class.getName());
            if (obj instanceof SuperEntity) {
                check((WorkOrder) obj);
            }
            // 验证集合  循环验证每一个票
            if (obj instanceof List) {
                @SuppressWarnings("unchecked")
                List<SuperEntity> listSuper = (List<SuperEntity>) obj;
                for (SuperEntity superEntity : listSuper) {
                    check((WorkOrder) superEntity);
                }
            }

        }
        return null;
    }

    /**
     * check:(抽取公共验证). <br/>
     * date: 2015年9月6日 <br/>
     *
     * @param workorder
     * @throws HDException
     * @author LXF
     */
    private void check(WorkOrder workorder) throws HDException {
        String zyendtime = "";
        zyendtime = workorder.getSjendtime();
        if (zyendtime.length() == 0) {
            throw new AppException(UNKNOW_ERROR);
        }
        checkEndDate(zyendtime);
    }

    /**
     * checkEndDate:(必须：签批时间<=计划结束时间). <br/>
     * date: 2015年9月6日 <br/>
     *
     * @param zyendtime
     * @throws HDException
     * @author lxf
     */
    private void checkEndDate(String zyendtime) throws HDException {
        // TODO Auto-generated method stub
        Date zyendtimeDate = ParseToDate(zyendtime);
        Calendar tmptime = Calendar.getInstance();
        tmptime.setTime(zyendtimeDate);
        // 当前时间
        Calendar now = Calendar.getInstance();
        now.setTime(ServerDateManager.getCurrentDate());
        if (now.compareTo(tmptime) > 0) {
            throw new AppException("作业票已过期，计划结束时间为:" + zyendtime);
        }
    }
}
