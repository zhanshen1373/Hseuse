package com.hd.hse.dc.business.web.remoteappr;

import com.google.gson.Gson;
import com.hd.hse.business.task.IMessageWhat;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.dc.business.common.weblistener.AbsWebListener;
import com.hd.hse.entity.workorder.RemoteApprDataCheckOrDisAgree;
import com.hd.hse.entity.workorder.RemoteApprLine;
import com.hd.hse.padinterface.PadInterfaceContainers;
import com.hd.hse.padinterface.PadInterfaceRequest;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

/**
 * 远程审批，不同意
 * created by yangning on 2017/9/21 10:46.
 */

public class DisagreeRemoteAppr extends AbsWebListener {

    private static Logger logger = LogUtils.getLogger(UpRemoteApprInfo.class);
    protected HashMap<String, Object> hasmap = null;
    private RemoteApprDataCheckOrDisAgree remoteApprDataDisAgree;
    private List<RemoteApprLine> apprLines;
    public DisagreeRemoteAppr(List<RemoteApprLine> apprLines) {
        this.apprLines = apprLines;
    }

    @Override
    public Object action(String action, Object... args) throws HDException {
        Object obj;
        try {
            super.action(action, args);
            beforUpDataInfo(args);
            obj = upDataInfo(args);
            afterUpDataInfo(obj, args);
            this.sendMessage(IMessageWhat.END, 100, 100, "");
        } catch (HDException e) {
            setWritelog(e.getMessage());
            this.sendMessage(IMessageWhat.ERROR, 100, 100, e.getMessage());
            throw e;
        }
        return obj;
    }

    private void beforUpDataInfo(Object[] args) {
        hasmap = new HashMap<String, Object>();
    }

    private Object upDataInfo(Object[] args) throws HDException {
        RemoteApprDataCheckOrDisAgree remoteApprDataDisAgree = new RemoteApprDataCheckOrDisAgree();
        remoteApprDataDisAgree.setUD_ZYXK_REMOTEAPPROVE(apprLines);
        Gson gson = new Gson();
        hasmap.put(PadInterfaceRequest.KEYDATA, gson.toJson(remoteApprDataDisAgree));
        Object obj = null;
        try {
            obj = sClient.action(getMethodType(), hasmap);
        } catch (HDException e) {
            throw e;
        }
        return obj;
    }

    private void afterUpDataInfo(Object obj, Object[] args) {

    }

    @Override
    public Object initParam() throws HDException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Logger getLogger() {
        // TODO Auto-generated method stub
        return logger;
    }

    @Override
    public String getMethodType() {
        return PadInterfaceContainers.REMOTE_APPR_DISAGREE;
    }

}
