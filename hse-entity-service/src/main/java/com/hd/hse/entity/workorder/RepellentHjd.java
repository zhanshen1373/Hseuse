package com.hd.hse.entity.workorder;

import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * created by yangning on 2019/1/3 13:49.
 * 互斥环节点配置表
 */
@DBTable(tableName = "ud_zyxk_hchjdpz")
public class RepellentHjd {
    public String getHjdone() {
        return hjdone;
    }

    public void setHjdone(String hjdone) {
        this.hjdone = hjdone;
    }

    public String getHjdtwo() {
        return hjdtwo;
    }

    public void setHjdtwo(String hjdtwo) {
        this.hjdtwo = hjdtwo;
    }

    public Integer getDr() {
        return dr;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    @DBField
    private String ud_zyxk_hchjdpzid;
    @DBField
    private String hjdone;
    @DBField
    private String hjdtwo;
    @DBField
    private Integer dr;
    @DBField
    private Integer tag;
    @DBField
    private String description;
    @DBField
    private String changedate;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChangedate() {
        return changedate;
    }

    public void setChangedate(String changedate) {
        this.changedate = changedate;
    }

    public String getUd_zyxk_hchjdpzid() {
        return ud_zyxk_hchjdpzid;
    }

    public void setUd_zyxk_hchjdpzid(String ud_zyxk_hchjdpzid) {
        this.ud_zyxk_hchjdpzid = ud_zyxk_hchjdpzid;
    }

}
