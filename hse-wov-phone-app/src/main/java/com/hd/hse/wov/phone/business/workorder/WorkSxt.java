package com.hd.hse.wov.phone.business.workorder;

import android.content.Context;
import android.os.Bundle;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.AysncTaskMessage;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.custom.ProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.listener.GetDetailSearchWorkListListener;
import com.hd.hse.dc.business.listener.GetListActionListener;
import com.hd.hse.dc.business.listener.GetSxtActionListener;
import com.hd.hse.entity.workorder.SxtBean;

import org.apache.log4j.Logger;

import java.util.List;

public class WorkSxt {

    /**
     * logger:TODO(日志).
     */
    private Logger logger = LogUtils.getLogger(WorkSxt.class);
    /**
     * context:TODO(上下文).
     */
    private Context context;
    /**
     * action:TODO(后台业务处理).
     */
    private BusinessAction action;

    /**
     * actionLsnr:TODO(特殊业务动作处理).
     */
    private GetSxtActionListener actionLsnr;
    /**
     * actionDswll:TODO(作业票详细搜索)
     */
    private GetDetailSearchWorkListListener actionDswll;

    /**
     * asyncTask:TODO(登录异步处理).
     */
    private BusinessAsyncTask asyncTask;

    /**
     * prsDlg:TODO(获取列表等待).
     */
    private ProgressDialog prsDlg;

    /**
     * getDataSourceListener:TODO(获取数据时的监听事件).
     */
    private IEventListener getDataSourceListener = null;

    /**
     * Creates a new instance of WorkTaskSrv.
     *
     * @param context
     */
    public WorkSxt(Context context) {
        this.context = context;
    }

    /**
     * Creates a new instance of WorkTaskSrv.
     */
    public WorkSxt() {
    }

    /**
     * 查询回调
     */
    private AbstractAsyncCallBack abstractAsyncCallBack = new AbstractAsyncCallBack() {

        @Override
        public void start(Bundle msgData) {
            // TODO Auto-generated method stub
        }

        @Override
        public void processing(Bundle msgData) {
            AysncTaskMessage msg = (AysncTaskMessage) msgData
                    .getSerializable("p");
            prsDlg.showMsg(msg.getMessage());
        }

        @Override
        public void error(Bundle msgData) {
            AysncTaskMessage msg = (AysncTaskMessage) msgData
                    .getSerializable("p");
            prsDlg.dismiss();
            ToastUtils.toast(context, msg.getMessage());
        }

        @SuppressWarnings("unchecked")
        @Override
        public void end(Bundle msgData) {
            AysncTaskMessage msg = (AysncTaskMessage) msgData
                    .getSerializable("p");
            if (getDataSourceListener != null) {
                try {
                    getDataSourceListener.eventProcess(
                            IEventType.DOWN_SXT_LIST,
                            (List<SxtBean>) msg.getReturnResult());
                } catch (HDException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            prsDlg.dismiss();
        }

    };

    /**
     * initialize:(异步加载数据). <br/>
     * date: 2014年10月10日 <br/>
     *
     * @author zhaofeng
     */
    public void geteDownLoadWorkList(String searchStr,StringBuilder locations) {
        if (action == null) {
            actionLsnr = new GetSxtActionListener(locations);
            action = new BusinessAction(actionLsnr);
        }
        if (asyncTask == null) {
            asyncTask = new BusinessAsyncTask(action, abstractAsyncCallBack);
        }
        // 异步处理
        try {
            asyncTask.execute("", searchStr);
            prsDlg = new ProgressDialog(context, "远程获取数据");
            prsDlg.setCancelable(true);
            prsDlg.setCanceledOnTouchOutside(false);
            prsDlg.show();
        } catch (HDException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        }
    }


    public final void setGetDataSourceListener(
            IEventListener getDataSourceListener) {
        this.getDataSourceListener = getDataSourceListener;
    }

}
