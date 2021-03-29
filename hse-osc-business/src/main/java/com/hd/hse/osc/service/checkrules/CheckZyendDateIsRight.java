/***********************************************************************
 * Module:  CheckZyendDateIsRight.java
 * Author:  Administrator
 * Purpose: Defines the Class CheckZyendDateIsRight
 ***********************************************************************/

package com.hd.hse.osc.service.checkrules;


import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.AppException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.checkrules.AbstractCheckListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * ClassName: CheckZyendDateIsRight (当前时间不能大于计划结束时间)<br/>
 * date: 2014年10月18日  <br/>
 *
 * @author ZhangJie
 */
public class CheckZyendDateIsRight extends AbstractCheckListener {
    /**
     * TODO
     *
     * @see com.hd.hse.service.workorder.checkrules.AbstractCheckListener#action(String, Object[])
     */
    public Object action(String action, Object... args) throws HDException {
        // TODO Auto-generated method stub
        String zyendtime = "";
        Map<String, Object> mapParas = objectCast(args[0]);
        if (mapParas.containsKey(WorkOrder.class.getName())) {
            Object obj = mapParas.get(WorkOrder.class.getName());
            if (obj instanceof SuperEntity) {
                WorkOrder workorder = (WorkOrder) obj;
                zyendtime = workorder.getSjendtime();
            }
            if (zyendtime.length() == 0) {
                throw new AppException(UNKNOW_ERROR);
            }
            checkEndDate(zyendtime);
        }
        return null;
    }

    /**
     * checkEndDate:(必须：签批时间<=计划结束时间). <br/>
     * date: 2014年10月18日 <br/>
     *
     * @throws AppException
     * @author ZhangJie
     */
    private void checkEndDate(String zyendtime) throws HDException {
        // TODO Auto-generated method stub
        Date zyendtimeDate = ParseToDate(zyendtime);
        Calendar tmptime = Calendar.getInstance();
        tmptime.setTime(zyendtimeDate);
        //当前时间
        Calendar now = Calendar.getInstance();
        now.setTime(ServerDateManager.getCurrentDate());
        if (now.compareTo(tmptime) > 0) {
            throw new AppException("作业票已过期，计划结束时间为:" + zyendtime);
        }
    }

}