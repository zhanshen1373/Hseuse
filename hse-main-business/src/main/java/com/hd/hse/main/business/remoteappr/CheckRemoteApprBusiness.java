package com.hd.hse.main.business.remoteappr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.AysncTaskMessage;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dc.business.web.remoteappr.CheckRemoteAppr;
import com.hd.hse.entity.workorder.RemoteApprLine;

import java.util.List;

/**
 * created by yangning on 2017/9/20 10:05.
 * 远程审批上传前校验
 */

public class CheckRemoteApprBusiness {

    private Activity activity;
    private ProgressDialog dialog;
    private OnCheckRemoteApprListener mListener;

    public CheckRemoteApprBusiness(Activity activity) {
        this.activity = activity;
        if (activity != null) {
            dialog = new ProgressDialog(activity);
            dialog.setMessage("正在校验远程审批数据...");
            dialog.setCanceledOnTouchOutside(false);
        }
    }

    public void setOnCheckRemoteApprListener(OnCheckRemoteApprListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 校验是否可远程审批
     *
     * @param apprLines
     */
    public void checkRemoteAppr(List<RemoteApprLine> apprLines) {

        CheckRemoteAppr upRemoteApprInfo = new CheckRemoteAppr(apprLines);
        BusinessAction action = new BusinessAction(upRemoteApprInfo);
        BusinessAsyncTask task = new BusinessAsyncTask(action,
                new AbstractAsyncCallBack() {

                    @Override
                    public void start(Bundle msgData) {

                    }

                    @Override
                    public void processing(Bundle msgData) {

                    }

                    @Override
                    public void error(Bundle msgData) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.cancel();
                        }
                        if (mListener != null) {
                            mListener.onCheckError();
                        }

                    }

                    @Override
                    public void end(Bundle msgData) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.cancel();
                        }
                        AysncTaskMessage msg = (AysncTaskMessage) msgData
                                .getSerializable("p");
                        String message = msg.getMessage();

                        if (mListener != null) {

                            if (message != null && !"".equals(message)) {
                                //不允许审批
                                mListener.onCheckFail(message);
                            } else {
                                //可以审批，弹出同意或者不同意的dialog
                                mListener.onCheckSuccess();
                            }
                        }


                    }
                });
        try {
            task.execute("");
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        } catch (HDException e) {
            if (dialog != null && dialog.isShowing()) {
                dialog.cancel();
            }
            if (mListener != null) {
                mListener.onCheckError();
            }
        }
    }

    public interface OnCheckRemoteApprListener {
        /**
         * 校验失败
         */

        void onCheckFail(String failReason);

        /**
         * 校验出错，可能是网络原因
         */
        void onCheckError();

        /**
         * 校验成功
         */
        void onCheckSuccess();


    }
}
