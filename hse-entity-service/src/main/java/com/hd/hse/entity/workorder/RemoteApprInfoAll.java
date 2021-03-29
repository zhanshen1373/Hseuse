package com.hd.hse.entity.workorder;

import java.util.List;

import com.hd.hse.common.entity.SuperEntity;

public class RemoteApprInfoAll extends SuperEntity{
	private List<RemoteApprInfo> UD_ZYXK_REMOTEAPPROVE;

    public List<RemoteApprInfo> getUD_ZYXK_REMOTEAPPROVE() {
        return UD_ZYXK_REMOTEAPPROVE;
    }

    public void setUD_ZYXK_REMOTEAPPROVE(List<RemoteApprInfo> UD_ZYXK_REMOTEAPPROVE) {
        this.UD_ZYXK_REMOTEAPPROVE = UD_ZYXK_REMOTEAPPROVE;
    }

}
