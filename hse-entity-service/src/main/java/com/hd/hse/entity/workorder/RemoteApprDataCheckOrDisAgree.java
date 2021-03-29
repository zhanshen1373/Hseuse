package com.hd.hse.entity.workorder;

import java.io.Serializable;
import java.util.List;

/**
 * 远程审批，审批上传数据校验或者不同意审批上传
 * created by yangning on 2017/9/21 10:39.
 */

public class RemoteApprDataCheckOrDisAgree implements Serializable {
    private List<RemoteApprLine> UD_ZYXK_REMOTEAPPROVE;

    public List<RemoteApprLine> getUD_ZYXK_REMOTEAPPROVE() {
        return UD_ZYXK_REMOTEAPPROVE;
    }

    public void setUD_ZYXK_REMOTEAPPROVE(List<RemoteApprLine> UD_ZYXK_REMOTEAPPROVE) {
        this.UD_ZYXK_REMOTEAPPROVE = UD_ZYXK_REMOTEAPPROVE;
    }
}
