package com.hd.hse.entity.common;

import java.io.Serializable;

/**
 * Created by dubojian on 2017/8/24.
 */

public class DetailSearch implements Serializable{

    //作业开始时间
    private String ZYSTARTTIME;
    //作业结束时间
    private String ZYENDTIME;
    //作业单位
    private String ZYDEPT;
    //作业类别
    private String ZYPCLASS;
    //作业名称
    private String ZYNAME;
    //作业区域
    private String ZYLOCATION_DESC;


    public String getZYSTARTTIME() {
        return ZYSTARTTIME;
    }

    public void setZYSTARTTIME(String ZYSTARTTIME) {
        this.ZYSTARTTIME = ZYSTARTTIME;
    }

    public String getZYENDTIME() {
        return ZYENDTIME;
    }

    public void setZYENDTIME(String ZYENDTIME) {
        this.ZYENDTIME = ZYENDTIME;
    }

    public String getZYDEPT() {
        return ZYDEPT;
    }

    public void setZYDEPT(String ZYDEPT) {
        this.ZYDEPT = ZYDEPT;
    }

    public String getZYPCLASS() {
        return ZYPCLASS;
    }

    public void setZYPCLASS(String ZYPCLASS) {
        this.ZYPCLASS = ZYPCLASS;
    }

    public String getZYNAME() {
        return ZYNAME;
    }

    public void setZYNAME(String ZYNAME) {
        this.ZYNAME = ZYNAME;
    }

    public String getZYLOCATION_DESC() {
        return ZYLOCATION_DESC;
    }

    public void setZYLOCATION_DESC(String ZYLOCATION_DESC) {
        this.ZYLOCATION_DESC = ZYLOCATION_DESC;
    }
}
