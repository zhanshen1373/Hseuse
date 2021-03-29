/**
 * Project Name:hse-cc-app
 * File Name:CcSaveWorkStatus.java
 * Package Name:com.hd.hse.cc.service.saveinfo
 * Date:2014年11月28日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 */
package com.hd.hse.cc.service.saveinfo;

import java.util.Map;

import com.hd.hse.constant.IActionType;
import com.hd.hse.constant.IWorkOrderStatus;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.workorder.saveinfo.SaveWorkStatus;


/**
 * ClassName: CcSaveWorkStatus ()<br/>
 * date: 2014年11月28日 <br/>
 *
 * @author zhaofeng
 */
public class CcSaveWorkStatus extends SaveWorkStatus {

    /**
     * TODO
     *
     * @see com.hd.hse.service.workorder.saveinfo.SaveWorkStatus#setWorkAttributes(com.hd.hse.entity.workorder.WorkOrder)
     */
    @Override
    public void setWorkAttributes(String action, WorkOrder workOrder,
                                  Map<String, Object> map) {
        // TODO Auto-generated method stub
        if (IActionType.ACTION_TYPE_CLOSE.equals(action)) {
            // 关闭
            if (IWorkOrderStatus.SPSTATUS_CQCLOSE.equalsIgnoreCase(workOrder
                    .getSpstatus())) {
                // 关闭时，已经超期了
                workOrder.setStatus(IWorkOrderStatus.CQCLOSE);
            } else {
                workOrder.setStatus(IWorkOrderStatus.CLOSE);
                workOrder.setSpstatus(IWorkOrderStatus.CLOSE);
            }
        } else if (IActionType.ACTION_TYPE_CANCEL.equals(action)) {
            // 取消
            workOrder.setStatus(IWorkOrderStatus.CAN);
            workOrder.setSpstatus(IWorkOrderStatus.CAN);
        } else if (IActionType.ACTION_TYPE_INTERRUPT.equals(action)) {
            //确认中断
            workOrder.setStatus(IWorkOrderStatus.APPR);
            workOrder.setSpstatus(IWorkOrderStatus.INTERRUPT);
            workOrder.setPausestatus(IWorkOrderStatus.PAUSED);
        } else if (IActionType.ACTION_TYPE_INTERRUPTEND.equals(action)) {
            //确认中断结束
            workOrder.setStatus(IWorkOrderStatus.APPR);
            workOrder.setSpstatus(IWorkOrderStatus.INTERRUPTEND);
            workOrder.setPausestatus(IWorkOrderStatus.PCANCEL);
        }
    }

}
