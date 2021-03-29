package com.hd.hse.entity.common;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

/**
 * Created by dubojian on 2018/6/2.
 */

@DBTable(tableName = "ud_cbsgl_ryline")
public class RyLine extends SuperEntity {


    @DBField(id = true)
    private Integer ud_cbsgl_rylineid;

    @DBField
    private Integer ishmd;

    @DBField
    private Integer age;

    @DBField
    private Integer dr;

    @DBField
    private Integer tag;

    @DBField
    private String swtime;

    @DBField
    private String starttime;

    @DBField
    private String endtime;

    @DBField
    private String changedate;


    @DBField
    private String ud_cbsgl_rynum;


    @DBField
    private String cbsdjnum;


    @DBField
    private String name;


    @DBField
    private String sex;


    @DBField
    private String worktype;


    @DBField
    private String innercard;

    @DBField
    private String ygdeptnum_desc;


    @DBField
    private String processstatus;

    @DBField
    private String idcard;

    @DBField
    private String checkresult;

    @DBField
    private String tjresult;

    @DBField
    private String trainresult;

    public Integer getUd_cbsgl_rylineid() {
        return ud_cbsgl_rylineid;
    }

    public void setUd_cbsgl_rylineid(Integer ud_cbsgl_rylineid) {
        this.ud_cbsgl_rylineid = ud_cbsgl_rylineid;
    }

    public Integer getIshmd() {
        return ishmd;
    }

    public void setIshmd(Integer ishmd) {
        this.ishmd = ishmd;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public String getSwtime() {
        return swtime;
    }

    public void setSwtime(String swtime) {
        this.swtime = swtime;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getChangedate() {
        return changedate;
    }

    public void setChangedate(String changedate) {
        this.changedate = changedate;
    }

    public String getUd_cbsgl_rynum() {
        return ud_cbsgl_rynum;
    }

    public void setUd_cbsgl_rynum(String ud_cbsgl_rynum) {
        this.ud_cbsgl_rynum = ud_cbsgl_rynum;
    }

    public String getCbsdjnum() {
        return cbsdjnum;
    }

    public void setCbsdjnum(String cbsdjnum) {
        this.cbsdjnum = cbsdjnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWorktype() {
        return worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype;
    }

    public String getInnercard() {
        return innercard;
    }

    public void setInnercard(String innercard) {
        this.innercard = innercard;
    }

    public String getYgdeptnum_desc() {
        return ygdeptnum_desc;
    }

    public void setYgdeptnum_desc(String ygdeptnum_desc) {
        this.ygdeptnum_desc = ygdeptnum_desc;
    }

    public String getProcessstatus() {
        return processstatus;
    }

    public void setProcessstatus(String processstatus) {
        this.processstatus = processstatus;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getCheckresult() {
        return checkresult;
    }

    public void setCheckresult(String checkresult) {
        this.checkresult = checkresult;
    }

    public String getTjresult() {
        return tjresult;
    }

    public void setTjresult(String tjresult) {
        this.tjresult = tjresult;
    }

    public String getTrainresult() {
        return trainresult;
    }

    public void setTrainresult(String trainresult) {
        this.trainresult = trainresult;
    }
}
