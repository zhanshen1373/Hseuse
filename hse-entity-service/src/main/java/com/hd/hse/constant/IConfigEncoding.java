/**
 * Project Name:hse-entity-service
 * File Name:IConfigEncoding.java
 * Package Name:com.hd.hse.constant
 * Date:2014年10月23日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 */
package com.hd.hse.constant;

/**
 * ClassName: IConfigEncoding ()<br/>
 * date: 2014年10月23日 <br/>
 *
 * @author zhaofeng
 */
public interface IConfigEncoding {

    /**
     * SP:TODO(现场核查-功能编码).
     */
    public static final String SP = "SP01";
    /**
     * FC:TODO(现场复查-功能编码).
     */
    public static final String FC = "FC01";
    /**
     * YQ:TODO(延期-功能编码).
     */
    public static final String YQ = "YQ01";
    /**
     * GB:TODO(关闭取消-功能编码).
     */
    public static final String GB = "GB01";
    /**
     * QX:TODO(取消).
     */
    public static final String QX = "QX01";
    /**
     * ZD:TODO(中断).
     */
    public static final String ZD = "PAUSE01";
    /**
     * ZDJS:TODO(中断结束).
     */
    public static final String ZDJS = "PAUSE01";
    /**
     * PAUSE:TODO(暂停).
     */
    public static final String PAUSE = "PAUSE01";
    /**
     * JJB:TODO(交接班-作业人变更).
     */
    public static final String JJB01 = "JJB01";
    /**
     * JJB:TODO(交接班-监护人变更).
     */
    public static final String JJB02 = "JJB02";
    /**
     * LL01:TODO(作业票浏览).
     */
    public static final String LL01 = "LL01";
    /**
     * MEASURE_TYPE:TODO(表示措施类别).
     */
    public static final int MEASURE_TYPE = 1;

    public static final int MEASURE_TYPELIETWO = -2;
    /**
     * MEASURE_TYPELIETWO:TODO(批量显示人名).
     */
    public static final int MEASURE_TYPELIETHREE = -3;
    /**
     * MEASURE_TYPELIEONE:TODO(逐条).
     */
    public static final int MEASURE_TYPELIEONE = -1;

    /**
     * MEASURE_TYPELIETHREE:TODO(显示措施).
     */
    public static final int MEASURE_TYPELIEZERO = 0;

    /**
     * MEASURE_TYPELIEFOUR:TODO(危害+措施显示).
     */
    public static final int MEASURE_TYPELIEFOUR = 1;

    /**
     * MEASURE_TYPEONEBYNOE:TODO(逐条).
     */
    public static final int MEASURE_TYPEONEBYNOE = 10;

    /**
     * MEASURE_TYPEONEBYNOE:TODO(非逐条).
     */
    public static final int MEASURE_TYPENOONEBYNOE = 11;
    /**
     * MEASURE_TYPEONEBYNOE:TODO(逐条批量).
     */
    public static final int MEASURE_TYPEONEBYNOEBATCH = 12;

    /**
     * HARM_TYPE:TODO(危害类别).
     */
    public static final int HARM_TYPE = 3;
    /**
     * PPE_TYPE:TODO(个人防护类别).
     */
    public static final int PPE_TYPE = 2;
    /**
     * ENERGY_TYPE:TODO(能量隔离类别).
     */
    public static final int ENERGY_TYPE = 4;
    /**
     * QT_TYPE:TODO(气体检测类别).
     */
    public static final int GAS_TYPE = 6;
    /**
     * HQ_TYPE:TODO(会签类别).
     */
    public static final int SIGN_TYPE = 10;
    /**
     * TEMPELE_TYPE:TODO(临时用电设备清单类别).
     */
    public static final int TEMPELE_TYPE = 25;

    /**
     * YQ_SIGN_TYPE:TODO(延期确认类别).
     */
    public static final int YQ_SIGN_TYPE = 11;

    /**
     * MEGER_SIGN_TYPE:TODO(合并会签).
     */
    public static final int MEGER_SIGN_TYPE = 12;
    /**
     * DWDC_INFO(吊物吊车信息)
     */
    public static final int DWDC_INFO_TYPE = 26;

    /**
     * (环境危害评价)
     */
    public static final int ENVIRONMENTHARMJUDGE = 28;


    /**
     * QTJC_NUM:TODO(审批-气体检测动态编码).
     */
    public static final String SP_GAS_NUM = "PGTYPE005";
    /**
     * SP_ENERGY_NUM:TODO(能量隔离界面编码).
     */
    public static final String SP_ENERGY_NUM = "PGTYPE004";
    /**
     * SP_SIGN_NUM:TODO(会签界面编码).
     */
    public static final String SP_SIGN_NUM = "PGTYPE009";

    /**
     * FC_GAS_NUM:TODO(复查-气体检测界面编码).
     */
    public static final String FC_GAS_NUM = "PGTYPE104";

    /**
     * YQ_SIGN_NUM:TODO(延期确认的编码).
     */
    public static final String YQ_SIGN_NUM = "PGTYPE204";

    /**
     * JJB_WORK_PERSON_CHANGE_NUM:TODO(交接班-作业人变更界面编码).
     */
    public static final String JJB_WORK_PERSON_CHANGE_NUM = "PGTYPE501";

    /**
     * JJB_GUARDIAN_CHANGE_NUM:TODO(交接班-监护人变更界面编码).
     */
    public static final String JJB_GUARDIAN_CHANGE_NUM = "PGTYPE502";

    /**
     * 关闭界面编码 Created by liuyang on 2016年9月26日
     */
    public static final String GB_NUM = "PGTYPE301";

    /**
     * 取消界面编码 Created by liuyang on 2016年9月26日
     */
    public static final String QX_NUM = "PGTYPE302";
    /**
     * 暂停界面编码
     */
    public static final String PAUSE_NUM = "PGTYPE304";
    /**
     * 中断界面编码
     */
    public static final String INTERRUPT_NUM = "PGTYPE305";
    /**
     * 中断结束界面编码
     */
    public static final String INTERRUPTEND_NUM = "PGTYPE306";
    /**
     * RESULT_OKCODE:TODO(返回ok结果).
     */
    public static final int RESULT_OKCODE = 1;
    /**
     * RESULT_NOCODE:TODO(返回NO结果).
     */
    public static final int RESULT_NOCODE = 0;
    /**
     * REQUESTCODE:TODO(界面请求编码).
     */
    public static final int REQUESTCODE = 1;

    /**
     * ISTEST:TODO(false 刷卡的，true 不需要刷卡).
     */
    // public static final boolean ISTEST=true;
    /**
     * ZY_TYPE:TODO(作业控件类型).
     */
    public static final int ZY_TYPE = 20;

}
