package com.hd.hse.entity.sys;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * Created by dubojian on 2017/8/18.
 */
@DBTable(tableName = "ud_zyxk_pdapage")
public class ShouYeDetect extends SuperEntity {

    @DBField
    private Integer ud_zyxk_padpageid;

    @DBField
    private Integer isenable;

    @DBField
    private String description;

    @DBField
    private String changeby;

    @DBField
    private String childdept;

    @DBField
    private String createby;

    @DBField
    private String deptnum;

    @DBField
    private String deptnum_desc;

    @DBField
    private String menuctrl;

    @DBField
    private String orgid;

    @DBField
    private String qxname;

    @DBField
    private String qxrole;

    @DBField
    private String qxrole_desc;

    @DBField
    private String siteid;

    @DBField
    private String parameter;

    @DBField
    private String parameter_desc;


    @DBField
    private Integer tag;

    @DBField
    private Integer dr;

    @DBField
    private String changedate;

    @DBField
    private String createdate;


    public Integer getUd_zyxk_padpageid() {
        return ud_zyxk_padpageid;
    }

    public void setUd_zyxk_padpageid(Integer ud_zyxk_padpageid) {
        this.ud_zyxk_padpageid = ud_zyxk_padpageid;
    }


    public Integer getIsenable() {
        return isenable;
    }

    public void setIsenable(Integer isenable) {
        this.isenable = isenable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChangeby() {
        return changeby;
    }

    public void setChangeby(String changeby) {
        this.changeby = changeby;
    }

    public String getChilddept() {
        return childdept;
    }

    public void setChilddept(String childdept) {
        this.childdept = childdept;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getDeptnum() {
        return deptnum;
    }

    public void setDeptnum(String deptnum) {
        this.deptnum = deptnum;
    }

    public String getDeptnum_desc() {
        return deptnum_desc;
    }

    public void setDeptnum_desc(String deptnum_desc) {
        this.deptnum_desc = deptnum_desc;
    }

    public String getMenuctrl() {
        return menuctrl;
    }

    public void setMenuctrl(String menuctrl) {
        this.menuctrl = menuctrl;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getQxname() {
        return qxname;
    }

    public void setQxname(String qxname) {
        this.qxname = qxname;
    }

    public String getQxrole() {
        return qxrole;
    }

    public void setQxrole(String qxrole) {
        this.qxrole = qxrole;
    }

    public String getQxrole_desc() {
        return qxrole_desc;
    }

    public void setQxrole_desc(String qxrole_desc) {
        this.qxrole_desc = qxrole_desc;
    }

    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter_desc() {
        return parameter_desc;
    }

    public void setParameter_desc(String parameter_desc) {
        this.parameter_desc = parameter_desc;
    }


    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public Integer getDr() {
        return dr;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public String getChangedate() {
        return changedate;
    }

    public void setChangedate(String changedate) {
        this.changedate = changedate;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }
}
