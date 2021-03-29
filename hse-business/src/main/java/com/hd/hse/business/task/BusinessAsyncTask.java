/**
 * Project Name:hse-business
 * File Name:BusinessAsyncTask.java
 * Package Name:com.hd.hse.business.task
 * Date:2014年8月27日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 */

package com.hd.hse.business.task;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;

import org.apache.log4j.Logger;

/**
 * ClassName:BusinessAsyncTask (异步任务).<br/>
 * Date: 2014年8月27日 <br/>
 *
 * @author lg
 * @version
 * @see
 */
public class BusinessAsyncTask extends Handler {

    private BusinessAction action;

    /**
     * callBack:TODO(回调对象).
     */
    private AbstractAsyncCallBack callBack;

    public BusinessAsyncTask() {

    }

    public BusinessAsyncTask(BusinessAction action) {
        this.action = action;
    }

    public BusinessAsyncTask(BusinessAction action,
                             AbstractAsyncCallBack callBack) {
        this.action = action;
        this.callBack = callBack;
    }

    /**
     * execute:(业务操作). <br/>
     * date: 2014年8月27日 <br/>
     *
     * @author lg
     * @param actionType
     * @throws HDException
     */
    public void execute(String actionType, Object... args) throws HDException {
        if (action == null)
            return;
        BusinessActionThread thread = new BusinessActionThread(actionType,
                action, this, args);
        thread.start();
    }

    /**
     * TODO 异步处理界面刷新
     *
     * @see android.os.Handler#handleMessage(android.os.Message)
     */
    @Override
    public void handleMessage(Message msg) {
        // TODO Auto-generated method stub
        if (callBack == null)
            return;
        int msgWhat = msg.what;
        Bundle msgData = msg.getData();
        switch (msgWhat) {
            case IMessageWhat.START:
                callBack.start(msgData);
                break;
            case IMessageWhat.PROCESSING:
                callBack.processing(msgData);
                break;
            case IMessageWhat.END:
                callBack.end(msgData);
                break;
            case IMessageWhat.ERROR:
                callBack.error(msgData);
                break;
            default:
        }
        super.handleMessage(msg);
    }
}

class BusinessActionThread extends Thread {

    private static Logger logger = LogUtils
            .getLogger(BusinessActionThread.class);
    private String actionType;

    private BusinessAction action;

    private BusinessAsyncTask asyncTask;

    private Object[] args;

    public BusinessActionThread(String actionType, BusinessAction action,
                                BusinessAsyncTask asyncTask, Object... args) {
        // TODO Auto-generated constructor stub
        this.actionType = actionType;
        this.action = action;
        this.asyncTask = asyncTask;
        this.args = args;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            action.action(actionType, asyncTask, args);
        } catch (HDException e) {
            logger.error(e.getMessage(), e);

        }
    }
}
