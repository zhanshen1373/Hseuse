package com.hd.hse.entity.workorder;

import com.hd.hse.common.field.DBField;
import com.hd.hse.common.table.DBTable;

@DBTable(tableName = "ud_zyxk_car_xkz")
public class CarXkz extends com.hd.hse.common.entity.SuperEntity {


    @DBField(id = true)
    private String ud_zyxk_car_xkzid;
    /**
     * 属地单位
     */
    @DBField
    private String deptunit;
    /**
     * 属地单位描述
     */
    @DBField
    private String deptunit_desc;
    /**
     * 车辆单位
     */
    @DBField
    private String carunit;
    /**
     * 车辆驾驶员
     */
    @DBField
    private String driver;
    /**
     * 车辆牌号
     */
    @DBField
    private String carnumber;
    /**
     * 监护人
     */
    @DBField
    private String jhr;
    /**
     * 进入部位
     */
    @DBField
    private String enterposition;
    /**
     * 进入原因
     */
    @DBField
    private String enterreason;
    /**
     * 进入时间
     */
    @DBField
    private String entertime;
    /**
     * 执行安全措施编码
     */
    @DBField
    private String zxaqcscode;
    /**
     * 执行安全措施描述
     */
    @DBField
    private String zxaqcsdesc;
    /**
     * 补充安全措施
     */
    @DBField
    private String bcaqcs;
    /**
     * 申请人
     */
    @DBField
    private String sqr;
    /**
     * 属地负责人
     */
    @DBField
    private String sdmanager;
    /**
     * hse攻城狮
     */
    @DBField
    private String hseengineer;
    /**
     * 行驶路线图
     */
    @DBField
    private String routepicturepath;

    private String url;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUd_zyxk_car_xkzid() {
        return ud_zyxk_car_xkzid;
    }

    public void setUd_zyxk_car_xkzid(String ud_zyxk_car_xkzid) {
        this.ud_zyxk_car_xkzid = ud_zyxk_car_xkzid;
    }

    public String getRoutepicturepath() {
        return routepicturepath;
    }

    public void setRoutepicturepath(String routepicturepath) {
        this.routepicturepath = routepicturepath;
    }


    public String getDeptunit() {
        return deptunit;
    }

    public void setDeptunit(String deptunit) {
        this.deptunit = deptunit;
    }

    public String getDeptunit_desc() {
        return deptunit_desc;
    }

    public void setDeptunit_desc(String deptunit_desc) {
        this.deptunit_desc = deptunit_desc;
    }

    public String getCarnumber() {
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber;
    }

    public String getZxaqcscode() {
        return zxaqcscode;
    }

    public void setZxaqcscode(String zxaqcscode) {
        this.zxaqcscode = zxaqcscode;
    }

    public String getZxaqcsdesc() {
        return zxaqcsdesc;
    }

    public void setZxaqcsdesc(String zxaqcsdesc) {
        this.zxaqcsdesc = zxaqcsdesc;
    }

    public String getCarunit() {
        return carunit;
    }

    public void setCarunit(String carunit) {
        this.carunit = carunit;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }


    public String getJhr() {
        return jhr;
    }

    public void setJhr(String jhr) {
        this.jhr = jhr;
    }

    public String getEnterposition() {
        return enterposition;
    }

    public void setEnterposition(String enterposition) {
        this.enterposition = enterposition;
    }

    public String getEnterreason() {
        return enterreason;
    }

    public void setEnterreason(String enterreason) {
        this.enterreason = enterreason;
    }

    public String getEntertime() {
        return entertime;
    }

    public void setEntertime(String entertime) {
        this.entertime = entertime;
    }


    public String getBcaqcs() {
        return bcaqcs;
    }

    public void setBcaqcs(String bcaqcs) {
        this.bcaqcs = bcaqcs;
    }

    public String getSqr() {
        return sqr;
    }

    public void setSqr(String sqr) {
        this.sqr = sqr;
    }

    public String getSdmanager() {
        return sdmanager;
    }

    public void setSdmanager(String sdmanager) {
        this.sdmanager = sdmanager;
    }

    public String getHseengineer() {
        return hseengineer;
    }

    public void setHseengineer(String hseengineer) {
        this.hseengineer = hseengineer;
    }
}
