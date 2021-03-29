/**
 * Project Name:hse-cbs-app
 * File Name:IActionType.java
 * Package Name:com.hd.hse.business.action
 * Date:2014年9月3日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 */

package com.hd.hse.constant;

/**
 * ClassName:IActionType (注册业务动作).<br/>
 * Date: 2014年9月3日 <br/>
 *
 * @author lg
 * @see
 */
public interface IActionType {

    /**
     * ==============登录界面==============
     */
    /**
     * LOGIN_LOGIN:TODO(登录).
     */
    public final static String LOGIN_LOGIN = "LOGIN_LOGIN";

    /**
     * ==============主界面==============
     */
    /**
     * MAIN_LOGOUT:TODO(退出).
     */
    public final static String MAIN_LOGOUT = "MAIN_LOGOUT";
    /**
     * MAIN_TASKLIST:TODO(任务列表).
     */
    public final static String MAIN_TASKLIST = "MAIN_TASKLIST";
    /**
     * ==============注册业务动作类型(CHECKACTIONCFG) =================
     * */
    /**
     * ACTION_TYPE_HAZ_STRINGARD:TODO(危害确认).
     */
    public static final String ACTION_TYPE_HAZ_STRINGARD = "HAZARD";
    /**
     * ACTION_TYPE_PRECAUTION:TODO(措施).
     */
    public static final String ACTION_TYPE_PRECAUTION = "PRECAUTION";
    /**
     * ACTION_TYPE_PPE:TODO(PPE).
     */
    public static final String ACTION_TYPE_PPE = "PPE";
    /**
     * ACTION_TYPE_GAS:TODO(气体检测).
     */
    public static final String ACTION_TYPE_GAS = "GAS";
    public static final String ACTION_TYPE_ELE = "ELE";
    public static final String ACTION_TYPE_HOISTING = "HOISTING";


    /**
     *ACTION_TYPE_CONDITIONENSURE:TODO(条件确认)
     */
    public static final String ACTION_TYPE_CONDITIONENSURE = "CE";
    /**
     * ACTION_TYPE_ENVIRONMENTHARMJUDGE:TODO(环境危害评价)
     */
    public static final String ACTION_TYPE_ENVIRONMENTHARMJUDGE = "EHJ";
    /**
     * ACTION_TYPE_CANSIGN:TODO(签发).
     */
    public static final String ACTION_TYPE_CANSIGN = "CANSIGN";

    /**
     * ACTION_TYPE_CANMERGESIGN:TODO(合并会签的校验).
     */
    public static final String ACTION_TYPE_CANMERGESIGN = "CANMERGESIGN";
    /**
     * ACTION_TYPE_SIGN:TODO(会签审核).
     */
    public static final String ACTION_TYPE_SIGN = "SIGN";

    /**
     * ACTION_TYPE_MERGE_SIGN:TODO(合并 会签审核).
     */
    public static final String ACTION_TYPE_MERGE_SIGN = "MERGESIGN";
    /**
     * ACTION_TYPE_RETURN:TODO(退回).
     */
    public static final String ACTION_TYPE_RETURN = "RETURN";
    /**
     * ACTION_TYPE_CANNULLIFY:TODO(点击作废).
     */
    public static final String ACTION_TYPE_CANNULLIFY = "CANNULLIFY";
    /**
     * ACTION_TYPE_NULLIFY:TODO(确认作废).
     */
    public static final String ACTION_TYPE_NULLIFY = "NULLIFY";
    /**
     * ACTION_TYPE_CLOSE:TODO(确认关闭).
     */
    public static final String ACTION_TYPE_CLOSE = "CLOSE";

    /**
     * ACTION_TYPE_MERGECLOSE:TODO(合并关闭).
     */
    public static final String ACTION_TYPE_MERGECLOSE = "MERGECLOSE";
    /**
     * ACTION_TYPE_CNACEL:TODO(确认取消).
     */
    public static final String ACTION_TYPE_CANCEL = "CANCEL";
    /**
     * ACTION_TYPE_CNACEL:TODO(确认中断).
     */
    public static final String ACTION_TYPE_INTERRUPT = "INTERRUPT";
    /**
     * ACTION_TYPE_CNACEL:TODO(确认中断结束).
     */
    public static final String ACTION_TYPE_INTERRUPTEND = "INTERRUPTEND";
    /**
     * ACTION_TYPE_PAUSE:TODO(确认暂停).
     */
    public static final String ACTION_TYPE_PAUSE = "PAUSE";

    /**
     * ACTION_TYPE_DELAYPRECAUTION:TODO(延期复查措施).
     */
    public static final String ACTION_TYPE_DELAYPRECAUTION = "DELAYPRECAUTION";
    /**
     * ACTION_TYPE_DELAYSIGN:TODO(延期审核).
     */
    public static final String ACTION_TYPE_DELAYSIGN = "DELAYSIGN";
    /**
     * ACTION_TYPE_RECHECKPRECAUTION:TODO(复查措施确认).
     */
    public static final String ACTION_TYPE_RECHECKPRECAUTION = "RECHECKPRECAUTION";
    /**
     * ACTION_TYPE_RECHECKGAS:TODO(复查气体检测).
     */
    public static final String ACTION_TYPE_RECHECKGAS = "RECHECKGAS";

    /**
     * ==============注册业务动作类型(CHECKACTIONCFG)END =================
     * */
    /**
     * BACK_SERVICE:TODO(后台服务功能点-自动关闭作废).
     */
    public static final String BACK_SERVICE_AUTO_UPDATE_STATUS = "BACK_SERVICE_AUTO_UPDATE_STATUS";
    /**
     * BACK_SERVICE_AUTO_TIMEOUT_EXITE:TODO(后台服务功能-超时自动退出).
     */
    public static final String BACK_SERVICE_AUTO_TIMEOUT_EXITE = "BACK_SERVICE_AUTO_TIMEOUT_EXITE";
    /**
     * BACK_SERVICE_AUTO_TIMEOUT_LOCATION_EXITE:TODO(后台服务功能-位置卡超时提醒刷卡).
     */
    public static final String BACK_SERVICE_AUTO_TIMEOUT_LOCATION_WARN = "BACK_SERVICE_AUTO_TIMEOUT_LOCATION_EXITE";
    /**
     * WEB_VUPGRADE:TODO(版本更新)
     */
    public static final String WEB_VUPGRADE = "WEB_VUPGRADE";
    /**
     * WEB_GETVERSIONUPGRADE：TODO(版本信息)
     */
    public static final String WEB_GETVERSIONUPGRADE = "WEB_GETVERSIONUPGRADE";

    /**
     * JJB_WORK_PERSON_CHANGE:TODO(交接班-作业人员变更).
     */
    public static final String JJB_WORK_PERSON_CHANGE = "JJB_WORK_PERSON_CHANGE";
    /**
     * JJB_GUARDIAN_CHANGE:TODO(交接班-监护人员变更).
     */
    public static final String JJB_GUARDIAN_CHANGE = "JJB_GUARDIAN_CHANGE";

    /**
     * QTJC_SYNCHRONOUS:TODO(气体检测同步).
     */
    public static final String QTJC_SYNCHRONOUS = "qtjc_synchronous";

    /**
     * VP_SAVELOCTION:TODO(保存虚拟位置).
     */
    public static final String VP_SAVELOCTION = "VP_SAVELOCTION";

    /**
     * VP_LOCATIONSAVELOCTION:TODO(保存本地虚拟位置).
     */
    public static final String VP_LOCATIONSAVELOCTION = "VP_LOCATIONSAVELOCTION";

    /**
     * ACTION_TYPE_ENERGY:TODO(能量隔离).
     */
    public static final String ACTION_TYPE_ENERGY = "ENERGY";
    /**
     * WEB_INOROUTRECORD:TODO(进出厂记录).
     */
    public final static String WEB_INOROUTRECORD = "WEB_INOROUTRECORD";
}
