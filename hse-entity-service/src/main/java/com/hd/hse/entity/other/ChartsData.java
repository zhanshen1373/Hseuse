package com.hd.hse.entity.other;

import com.hd.hse.common.entity.SuperEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dubojian on 2017/7/27.
 * 兰州管理人员首页数据
 */

public class ChartsData extends SuperEntity{


    private List<AXISBean> AXIS;
    private List<CHART1Bean> CHART1;
    private List<CHART2Bean> CHART2;
    private List<TABLEBean> TABLE;

    public List<AXISBean> getAXIS() {
        return AXIS;
    }

    public void setAXIS(List<AXISBean> AXIS) {
        this.AXIS = AXIS;
    }

    public List<CHART1Bean> getCHART1() {
        return CHART1;
    }

    public void setCHART1(List<CHART1Bean> CHART1) {
        this.CHART1 = CHART1;
    }

    public List<CHART2Bean> getCHART2() {
        return CHART2;
    }

    public void setCHART2(List<CHART2Bean> CHART2) {
        this.CHART2 = CHART2;
    }

    public List<TABLEBean> getTABLE() {
        return TABLE;
    }

    public void setTABLE(List<TABLEBean> TABLE) {
        this.TABLE = TABLE;
    }

    public static class AXISBean {
        /**
         * dept : P1520,P1521,P1522,P1523,P1525,P1526,P1527,P1528,P1556,P1529,P1530,P1552,P1524
         * dept_desc : 炼油厂,化肥厂,橡胶厂,石化厂,催化剂厂,动力厂,污水处理厂,助剂厂,国储公司,油品储运厂,化工储运厂,聚丙烯厂,乙烯厂
         */

        private String dept;
        private String dept_desc;

        public String getDept() {
            return dept;
        }

        public void setDept(String dept) {
            this.dept = dept;
        }

        public String getDept_desc() {
            return dept_desc;
        }

        public void setDept_desc(String dept_desc) {
            this.dept_desc = dept_desc;
        }
    }

    public static class CHART1Bean {
        /**
         * zydept_desc : 化肥厂
         * zydept : P1521
         * zylx99 : 30
         */

        private String zydept_desc;
        private String zydept;
        private String zylx99;

        public String getZydept_desc() {
            return zydept_desc;
        }

        public void setZydept_desc(String zydept_desc) {
            this.zydept_desc = zydept_desc;
        }

        public String getZydept() {
            return zydept;
        }

        public void setZydept(String zydept) {
            this.zydept = zydept;
        }

        public String getZylx99() {
            return zylx99;
        }

        public void setZylx99(String zylx99) {
            this.zylx99 = zylx99;
        }
    }

    public static class CHART2Bean {
        /**
         * zydept_desc : 化肥厂
         * zydept : P1521
         * zylx99 : 107
         */

        private String zydept_desc;
        private String zydept;
        private String zylx99;

        public String getZydept_desc() {
            return zydept_desc;
        }

        public void setZydept_desc(String zydept_desc) {
            this.zydept_desc = zydept_desc;
        }

        public String getZydept() {
            return zydept;
        }

        public void setZydept(String zydept) {
            this.zydept = zydept;
        }

        public String getZylx99() {
            return zylx99;
        }

        public void setZylx99(String zylx99) {
            this.zylx99 = zylx99;
        }
    }

    public static class TABLEBean implements Serializable{
        /**
         * zylx06 : 1
         * zylx05 : 5
         * description : 化肥厂
         * zydept : P1521
         * zylx99 : 47
         * zylx01 : 30
         * zylx02 : 0
         * zylx04 : 20
         */

        private String zylx06;
        private String zylx05;
        private String description;
        private String zydept;
        private String zylx99;
        private String zylx01;
        private String zylx02;
        private String zylx04;

        public String getZylx06() {
            return zylx06;
        }

        public void setZylx06(String zylx06) {
            this.zylx06 = zylx06;
        }

        public String getZylx05() {
            return zylx05;
        }

        public void setZylx05(String zylx05) {
            this.zylx05 = zylx05;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getZydept() {
            return zydept;
        }

        public void setZydept(String zydept) {
            this.zydept = zydept;
        }

        public String getZylx99() {
            return zylx99;
        }

        public void setZylx99(String zylx99) {
            this.zylx99 = zylx99;
        }

        public String getZylx01() {
            return zylx01;
        }

        public void setZylx01(String zylx01) {
            this.zylx01 = zylx01;
        }

        public String getZylx02() {
            return zylx02;
        }

        public void setZylx02(String zylx02) {
            this.zylx02 = zylx02;
        }

        public String getZylx04() {
            return zylx04;
        }

        public void setZylx04(String zylx04) {
            this.zylx04 = zylx04;
        }
    }
}
