package com.hd.hse.entity.base;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * Created by dubojian on 2017/11/10.
 */
@DBTable(tableName = "ud_hsebz_zyxx")
public class ZyxxTableName extends SuperEntity {

    @DBField
    private Integer ud_hsebz_zyxxid;

    @DBField
    private Integer zyno;

    @DBField
    private Integer pass;

    @DBField
    private Integer zymaxscore;

    @DBField
    private Integer hasld;

    @DBField
    private Integer tag;

    @DBField
    private Integer dr;

    @DBField
    private String description;

    @DBField
    private String zyname;
    @DBField
    private String zycode;

    @DBField
    private String ud_hsebz_zymcid;

    @DBField
    private String zrcs;

    @DBField
    private String zrcscode;

    @DBField
    private String rowstamp;

    @DBField
    private String changedate;

    public String getChangedate() {
        return changedate;
    }

    public void setChangedate(String changedate) {
        this.changedate = changedate;
    }

    public Integer getUd_hsebz_zyxxid() {
        return ud_hsebz_zyxxid;
    }

    public void setUd_hsebz_zyxxid(Integer ud_hsebz_zyxxid) {
        this.ud_hsebz_zyxxid = ud_hsebz_zyxxid;
    }

    public Integer getZyno() {
        return zyno;
    }

    public void setZyno(Integer zyno) {
        this.zyno = zyno;
    }

    public Integer getPass() {
        return pass;
    }

    public void setPass(Integer pass) {
        this.pass = pass;
    }

    public Integer getZymaxscore() {
        return zymaxscore;
    }

    public void setZymaxscore(Integer zymaxscore) {
        this.zymaxscore = zymaxscore;
    }

    public Integer getHasld() {
        return hasld;
    }

    public void setHasld(Integer hasld) {
        this.hasld = hasld;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getZyname() {
        return zyname;
    }

    public void setZyname(String zyname) {
        this.zyname = zyname;
    }

    public String getZycode() {
        return zycode;
    }

    public void setZycode(String zycode) {
        this.zycode = zycode;
    }

    public String getUd_hsebz_zymcid() {
        return ud_hsebz_zymcid;
    }

    public void setUd_hsebz_zymcid(String ud_hsebz_zymcid) {
        this.ud_hsebz_zymcid = ud_hsebz_zymcid;
    }

    public String getZrcs() {
        return zrcs;
    }

    public void setZrcs(String zrcs) {
        this.zrcs = zrcs;
    }

    public String getZrcscode() {
        return zrcscode;
    }

    public void setZrcscode(String zrcscode) {
        this.zrcscode = zrcscode;
    }

    public String getRowstamp() {
        return rowstamp;
    }

    public void setRowstamp(String rowstamp) {
        this.rowstamp = rowstamp;
    }
}
