package com.hd.hse.main.business.remoteappr;

import android.app.Activity;
import android.os.Bundle;

import com.hd.hse.business.action.BusinessAction;
import com.hd.hse.business.task.AbstractAsyncCallBack;
import com.hd.hse.business.task.BusinessAsyncTask;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.dc.business.web.remoteappr.DisagreeRemoteAppr;
import com.hd.hse.entity.workorder.RemoteApprLine;

import java.util.List;

/**
 * created by yangning on 2017/9/21 13:43.
 */

public class DisagreeRemoteApprBusiness extends RemoteApprBusiness {
    public DisagreeRemoteApprBusiness(Activity activity) {
        super(activity);
    }

    public void upRemoteAppr(List<RemoteApprLine> apprLines) {

        DisagreeRemoteAppr upRemoteApprInfo = new DisagreeRemoteAppr(apprLines);
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
}
