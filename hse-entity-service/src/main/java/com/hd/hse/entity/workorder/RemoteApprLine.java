package com.hd.hse.entity.workorder;

import java.io.Serializable;

/**
 * 远程审批，审批上传的数据
 *
 * @author yn
 */
public class RemoteApprLine implements Serializable {
    /**
     * issp : 0 ud_zyxk_remoteapproveid : 70 spnode : CHQ_ZLAQHBC remotenum :
     * 1067 zysqid : 65830 zypclass_desc : 作业大票 zypclass : zylx99 spnode_desc :
     * 质量安全环保处 ud_zyxk_remoteapprove_lineid : 112
     */

    private String issp;
    private String ud_zyxk_remoteapproveid;
    private String spnode;
    private String remotenum;
    private String zysqid;
    private String zypclass_desc;
    private String zypclass;
    private String spnode_desc;
    private String ud_zyxk_remoteapprove_lineid;
    private String approve_person_name;
    private String approve_personid;
    private String SPTIME;
    private String status;
    private String disagree_reason;
    private String zylevel;

    public String getZylevel() {
        return zylevel;
    }

    public void setZylevel(String zylevel) {
        this.zylevel = zylevel;
    }

    public String getDisagree_reason() {
        return disagree_reason;
    }

    public void setDisagree_reason(String disagree_reason) {
        this.disagree_reason = disagree_reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIssp() {
        return issp;
    }

    public void setIssp(String issp) {
        this.issp = issp;
    }

    public String getUd_zyxk_remoteapproveid() {
        return ud_zyxk_remoteapproveid;
    }

    public void setUd_zyxk_remoteapproveid(String ud_zyxk_remoteapproveid) {
        this.ud_zyxk_remoteapproveid = ud_zyxk_remoteapproveid;
    }

    public String getSpnode() {
        return spnode;
    }

    public void setSpnode(String spnode) {
        this.spnode = spnode;
    }

    public String getRemotenum() {
        return remotenum;
    }

    public void setRemotenum(String remotenum) {
        this.remotenum = remotenum;
    }

    public String getZysqid() {
        return zysqid;
    }

    public void setZysqid(String zysqid) {
        this.zysqid = zysqid;
    }

    public String getZypclass_desc() {
        return zypclass_desc;
    }

    public void setZypclass_desc(String zypclass_desc) {
        this.zypclass_desc = zypclass_desc;
    }

    public String getZypclass() {
        return zypclass;
    }

    public void setZypclass(String zypclass) {
        this.zypclass = zypclass;
    }

    public String getSpnode_desc() {
        return spnode_desc;
    }

    public void setSpnode_desc(String spnode_desc) {
        this.spnode_desc = spnode_desc;
    }

    public String getUd_zyxk_remoteapprove_lineid() {
        return ud_zyxk_remoteapprove_lineid;
    }

    public void setUd_zyxk_remoteapprove_lineid(
            String ud_zyxk_remoteapprove_lineid) {
        this.ud_zyxk_remoteapprove_lineid = ud_zyxk_remoteapprove_lineid;
    }

    public String getApprove_person_name() {
        return approve_person_name;
    }

    public void setApprove_person_name(String approve_person_name) {
        this.approve_person_name = approve_person_name;
    }

    public String getApprove_personid() {
        return approve_personid;
    }

    public void setApprove_personid(String approve_personid) {
        this.approve_personid = approve_personid;
    }

    public String getSPTIME() {
        return SPTIME;
    }

    public void setSPTIME(String sPTIME) {
        SPTIME = sPTIME;
    }
}
