package com.hd.hse.main.business.remoteappr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dc.business.web.remoteappr.UpRemoteApprInfo;
import com.hd.hse.entity.workorder.RemoteApprLine;

import java.util.List;

/**
 * 上传远程审批
 *
 * @author yn
 */
public class RemoteApprBusiness {
    private Activity activity;
    protected ProgressDialog dialog;
    protected UpRemoteApprInfoListener mListener;

    public RemoteApprBusiness(Activity activity) {
        this.activity = activity;
        if (activity != null) {
            dialog = new ProgressDialog(activity);
            dialog.setMessage("正在上传远程审批数据...");
            dialog.setCanceledOnTouchOutside(false);
        }

    }

    public void setUpRemoteApprInfoListener(UpRemoteApprInfoListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 上传远程审批数据
     *
     * @param apprLines
     */
    public void upRemoteAppr(List<RemoteApprLine> apprLines) {

        UpRemoteApprInfo upRemoteApprInfo = new UpRemoteApprInfo(apprLines);
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
                            mListener.UpRemoteApprInfoError();
                        }

                    }

                    @Override
                    public void end(Bundle msgData) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.cancel();
                        }
                        if (mListener != null) {
                            mListener.UpRemoteApprInfoSuccess();
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
                mListener.UpRemoteApprInfoError();
            }
        }
    }

    public interface UpRemoteApprInfoListener {
        void UpRemoteApprInfoError();

        void UpRemoteApprInfoSuccess();
    }

}
