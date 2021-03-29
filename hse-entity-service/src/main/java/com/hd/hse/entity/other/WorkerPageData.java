package com.hd.hse.entity.other;

import com.hd.hse.common.entity.SuperEntity;

import java.util.List;

/**
 * Created by dubojian on 2017/9/29.
 */

public class WorkerPageData extends SuperEntity {


    private List<MESSAGEBean> MESSAGE;
    private List<ZYPTIMEBean> ZYPTIME;
    private List<QTJCTIMEBean> QTJCTIME;

    public List<MESSAGEBean> getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(List<MESSAGEBean> MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public List<ZYPTIMEBean> getZYPTIME() {
        return ZYPTIME;
    }

    public void setZYPTIME(List<ZYPTIMEBean> ZYPTIME) {
        this.ZYPTIME = ZYPTIME;
    }

    public List<QTJCTIMEBean> getQTJCTIME() {
        return QTJCTIME;
    }

    public void setQTJCTIME(List<QTJCTIMEBean> QTJCTIME) {
        this.QTJCTIME = QTJCTIME;
    }

    public static class MESSAGEBean {
        /**
         * content : 安全受控系统操作手册

         */

        private String content;
        private String msgcount;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMsgcount() {
            return msgcount;
        }

        public void setMsgcount(String msgcount) {
            this.msgcount = msgcount;
        }
    }

    public static class ZYPTIMEBean {
        /**
         * zyname : qwewq*作业大票
         * id : 23816
         * sjendtime : 2017-07-11 13:00:00
         */

        private String zyname;
        private String id;
        private String sjendtime;

        public String getZyname() {
            return zyname;
        }

        public void setZyname(String zyname) {
            this.zyname = zyname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSjendtime() {
            return sjendtime;
        }

        public void setSjendtime(String sjendtime) {
            this.sjendtime = sjendtime;
        }
    }

    public static class QTJCTIMEBean {
        /**
         * zyname : 11月22日橡胶厂培训*动火作业
         * id : 22887
         * jctime : 2015-11-22 15:14:16
         * sx : 120
         */

        private String zyname;
        private String id;
        private String jctime;
        private String sx;

        public String getZyname() {
            return zyname;
        }

        public void setZyname(String zyname) {
            this.zyname = zyname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJctime() {
            return jctime;
        }

        public void setJctime(String jctime) {
            this.jctime = jctime;
        }

        public String getSx() {
            return sx;
        }

        public void setSx(String sx) {
            this.sx = sx;
        }
    }
}
