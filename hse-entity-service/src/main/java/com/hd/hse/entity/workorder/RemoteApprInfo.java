package com.hd.hse.entity.workorder;

import java.io.Serializable;
import java.util.List;

/**
 * 服务器上远程审批信息
 *
 * @author yn
 */
public class RemoteApprInfo implements Serializable {

    /**
     * isupload : 0 ud_zyxk_remoteapproveid : 70 spot_final_personid : MAXADMIN
     * zyptype : zylx99,zylx04 spnode : CHQ_ZLAQHBC remotenum : 1067 zysqid :
     * 65830,65831 spnode_desc : 质量安全环保处 approve_person_name : 黄文彬,高春雷,大管家
     * approve_personid : HUANGWENBIN, GAO1,MAXADMIN zyptype_desc : 作业大票,高处作业
     * spot_final_person_name : 大管家 send_remind_message : 属地单位【一联合车间 】在【气分装置泵廊中点
     * 】区域进行【*高处作业】作业，请您审批！ UD_ZYXK_REMOTEAPPROVE_LINE :
     * [{"issp":"0","ud_zyxk_remoteapproveid":"70"
     * ,"spnode":"CHQ_ZLAQHBC","remotenum"
     * :"1067","zysqid":"65830","zypclass_desc"
     * :"作业大票","zypclass":"zylx99","spnode_desc"
     * :"质量安全环保处","ud_zyxk_remoteapprove_lineid"
     * :"112"},{"issp":"0","ud_zyxk_remoteapproveid"
     * :"70","spnode":"CHQ_ZLAQHBC"
     * ,"remotenum":"1067","zysqid":"65831","zypclass_desc" :"高处作业","zypclass"
     * :"zylx04","spnode_desc":"质量安全环保处","ud_zyxk_remoteapprove_lineid" :"113"}]
     */

    private String isupload;
    private String ud_zyxk_remoteapproveid;
    private String spot_final_personid;
    private String zyptype;
    private String spnode;
    private String remotenum;
    private String zysqid;
    private String spnode_desc;
    private String approve_person_name;
    private String approve_personid;
    private String zyptype_desc;
    private String spot_final_person_name;
    private String send_remind_message;
    private List<RemoteApprLine> UD_ZYXK_REMOTEAPPROVE_LINE;

    private String zysqidall;
    private String zyptypeall_desc;
    private String zyptypeall;
    private String statusall;
    private String zylevelall;

    public String getZylevelall() {
        return zylevelall;
    }

    public void setZylevelall(String zylevelall) {
        this.zylevelall = zylevelall;
    }

    public String getStatusall() {
        return statusall;
    }

    public void setStatusall(String statusall) {
        this.statusall = statusall;
    }

    public String getZysqidall() {
        return zysqidall;
    }

    public void setZysqidall(String zysqidall) {
        this.zysqidall = zysqidall;
    }

    public String getZyptypeall_desc() {
        return zyptypeall_desc;
    }

    public void setZyptypeall_desc(String zyptypeall_desc) {
        this.zyptypeall_desc = zyptypeall_desc;
    }

    public String getZyptypeall() {
        return zyptypeall;
    }

    public void setZyptypeall(String zyptypeall) {
        this.zyptypeall = zyptypeall;
    }

    public String getIsupload() {
        return isupload;
    }

    public void setIsupload(String isupload) {
        this.isupload = isupload;
    }

    public String getUd_zyxk_remoteapproveid() {
        return ud_zyxk_remoteapproveid;
    }

    public void setUd_zyxk_remoteapproveid(String ud_zyxk_remoteapproveid) {
        this.ud_zyxk_remoteapproveid = ud_zyxk_remoteapproveid;
    }

    public String getSpot_final_personid() {
        return spot_final_personid;
    }

    public void setSpot_final_personid(String spot_final_personid) {
        this.spot_final_personid = spot_final_personid;
    }

    public String getZyptype() {
        return zyptype;
    }

    public void setZyptype(String zyptype) {
        this.zyptype = zyptype;
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

    public String getSpnode_desc() {
        return spnode_desc;
    }

    public void setSpnode_desc(String spnode_desc) {
        this.spnode_desc = spnode_desc;
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

    public String getZyptype_desc() {
        return zyptype_desc;
    }

    public void setZyptype_desc(String zyptype_desc) {
        this.zyptype_desc = zyptype_desc;
    }

    public String getSpot_final_person_name() {
        return spot_final_person_name;
    }

    public void setSpot_final_person_name(String spot_final_person_name) {
        this.spot_final_person_name = spot_final_person_name;
    }

    public String getSend_remind_message() {
        return send_remind_message;
    }

    public void setSend_remind_message(String send_remind_message) {
        this.send_remind_message = send_remind_message;
    }

    public List<RemoteApprLine> getUD_ZYXK_REMOTEAPPROVE_LINE() {
        return UD_ZYXK_REMOTEAPPROVE_LINE;
    }

    public void setUD_ZYXK_REMOTEAPPROVE_LINE(
            List<RemoteApprLine> UD_ZYXK_REMOTEAPPROVE_LINE) {
        this.UD_ZYXK_REMOTEAPPROVE_LINE = UD_ZYXK_REMOTEAPPROVE_LINE;
    }

}
