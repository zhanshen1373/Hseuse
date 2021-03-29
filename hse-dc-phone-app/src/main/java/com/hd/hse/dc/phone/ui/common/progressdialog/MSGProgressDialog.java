package com.hd.hse.dc.phone.ui.common.progressdialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.listener.AbstractActionListener;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.AysncTaskMessage;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.component.phone.custom.MessageProgressDialog;
import com.hd.hse.common.component.phone.event.IEventType;
import com.hd.hse.common.component.phone.listener.IEventListener;
import com.hd.hse.common.component.phone.util.ToastUtils;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;

import org.apache.log4j.Logger;

/**
 * 进度条父类 ClassName: MSGProgressDialog (progressDialog)<br/>
 * date: 2014年10月11日 <br/>
 *
 * @author lxf
 */
public class MSGProgressDialog {
    /**
     * logger:TODO(日志).
     */
    private Logger logger = LogUtils.getLogger(MSGProgressDialog.class);
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
    private AbstractActionListener actionLsnr;

    /**
     * asyncTask:TODO(异步处理).
     */
    private BusinessAsyncTask asyncTask;

    /**
     * mDialog:TODO(显示进度条).
     */
    private MessageProgressDialog mDialog = null;

    /**
     * getDataSourceListener:TODO(获取数据时的监听事件).
     */
    private IEventListener getDataSourceListener = null;

    public MSGProgressDialog(Context context) {
        this.context = context;
    }

    /**
     * setGetDataSourceListener:(). <br/>
     * date: 2014年10月11日 <br/>
     *
     * @param getDataSourceListener
     * @author lxf
     */
    public final void setGetDataSourceListener(
            IEventListener getDataSourceListener) {
        this.getDataSourceListener = getDataSourceListener;
    }

    /**
     * isShowing:TODO(Dialog是否处于显示中).
     */
    private boolean isShowing = false;

    private void showDialog(String titile, String message) {
        isShowing = true;
        mDialog = new MessageProgressDialog(context);
        mDialog.setTitle(titile);
        mDialog.setMessage(message);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // mDialog.executSuccessful();
        mDialog.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub
                // cancel(true);
                actionLsnr.isCancel = true;
            }
        });
        mDialog.show();
        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
        DisplayMetrics outMetrics = new DisplayMetrics();
        mDialog.getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        params.width = outMetrics.widthPixels * 3 / 4;
        mDialog.getWindow().setAttributes(params);

        mDialog.setMax(100);
    }

    /**
     * getActionListner:(得到动作类型). <br/>
     * date: 2014年10月11日 <br/>
     *
     * @return
     * @author lxf
     */
    public AbstractActionListener getActionListner() {
        return null;
    }

    private boolean bj = true;

    /**
     * initInfo:(初始化参数). <br/>
     * date: 2014年10月11日 <br/>
     *
     * @author lxf
     */
    private void initInfo() {
        if (action == null) {
            actionLsnr = getActionListner();// new GetListActionListener();
            if (null == actionLsnr) {
                return;
            }
            action = new BusinessAction(actionLsnr);
        }
        if (asyncTask == null) {
            asyncTask = new BusinessAsyncTask(action,
                    new AbstractAsyncCallBack() {

                        @Override
                        public void start(Bundle msgData) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void processing(Bundle msgData) {
                            AysncTaskMessage msg = (AysncTaskMessage) msgData
                                    .getSerializable("p");
                            int process = msg.getCurrent();
                            if (null != mDialog) {
                                mDialog.setMessage(msg.getMessage());

                                if (bj) {
                                    bj = false;
                                    mDialog.setStop(false);
                                }
                                if (process < 15) {
                                    mDialog.setContent("连接服务器");
                                } else if (process == 15) {
                                    mDialog.setContent("联网获取数据");
                                } else if (process > 15 && process < 100) {
                                    mDialog.setContent("本地处理数据");
                                }

                                if (process >= 100) {
                                    // mDialog.dismiss();
                                    // mDialog=null;
                                } else {
                                    mDialog.setProgress(process);
                                }
                            }

                        }

                        @Override
                        public void error(Bundle msgData) {
                            AysncTaskMessage msg = (AysncTaskMessage) msgData
                                    .getSerializable("p");
                            String error = msg.getMessage();
                            if (null != mDialog) {
                                mDialog.setMessage(error);
                                if (!bj) {
                                    bj = true;
                                    mDialog.setStop(true);
                                }
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                }
                                // 设置显示按钮 提示错误
                                mDialog.executFailed();
                                isShowing = false;
                            } else {
                                ToastUtils.toast(context, error);
                            }
                            if (getDataSourceListener != null) {
                                try {
                                    // 回调函数
                                    getDataSourceListener.eventProcess(
                                            IEventType.DOWN_WORK_LIST_SHOW,
                                            msg.getReturnResult());
                                } catch (HDException e) {
                                    logger.error(e.getMessage(), e);
                                }
                            }
                            // ToastUtils.toast(context, msg.getMessage());
                        }

                        @SuppressWarnings("unchecked")
                        @Override
                        public void end(Bundle msgData) {
                            // TODO Auto-generated method stub
                            AysncTaskMessage msg = (AysncTaskMessage) msgData
                                    .getSerializable("p");
                            if (null != mDialog) {
                                //mDialog.setMessage("恭喜你,操作成功!");
                                mDialog.setProgress(100);
                                if (!bj) {
                                    bj = true;
                                    mDialog.setStop(true);
                                }
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                }
                                mDialog.executSuccessful1();
                                isShowing = false;
                            }
                            if (getDataSourceListener != null) {
                                try {
                                    // 回调函数
                                    getDataSourceListener.eventProcess(
                                            IEventType.DOWN_WORK_LIST_LOAD,
                                            msg.getReturnResult());
                                } catch (HDException e) {
                                    logger.error(e.getMessage(), e);
                                }
                            }
                        }
                    });
        }
    }

    /**
     * execute:(表示执行). <br/>
     * date: 2014年10月11日 <br/>
     *
     * @param Action
     * @param args
     * @author lxf
     */
    public void execute(String titile, String message, String Action,
                        Object... args) {
        initInfo();
        if (null == asyncTask) {
            return;
        }
        try {
            if (mDialog != null && mDialog.isShowing())
                return;
            showDialog(titile, message);
            asyncTask.execute(Action, args);
        } catch (HDException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
