/**
 * Project Name:hse-entity-service
 * File Name:IWorkOrderStatus.java
 * Package Name:com.hd.hse.entity.workorder
 * Date:2014年9月26日
 * Copyright (c) 2014, longgang@ushayden.com All Rights Reserved.
 */

package com.hd.hse.constant;

/**
 * ClassName:IWorkOrderStatus (作业票状态).<br/>
 * Date: 2014年9月26日 <br/>
 *
 * @author lg
 * @see
 */
public interface IWorkOrderStatus {
    /**
     * WAPPR:TODO(草稿).
     */
    public static final String WAPPR = "WAPPR";

    /**
     * INPRG:TODO(现场核查).
     */
    public static final String INPRG = "INPRG";

    /**
     * APPAUDITED:TODO( 现场核查完成).
     */
    public static final String APPAUDITED = "APPAUDITED";
    /**
     * APPR:TODO(作业中).
     */
    public static final String APPR = "APPR";

    /**
     * CLOSE:TODO(作业关闭).
     */
    public static final String CLOSE = "CLOSE";

    /**
     * CAN:TODO(作业取消).
     */
    public static final String CAN = "CAN";

    /**
     * INTERRUPT:TODO(作业中断).
     */
    public static final String INTERRUPT = "INTERRUPT";

    /**
     * INTERRUPTEND:TODO(作业中断结束).
     */
    public static final String INTERRUPTEND = "INTERRUPTEND";

    /**
     * PAUSED:TODO(作业票的暂停状态).
     */
    public static final String PAUSED = "PAUSED";

    /**
     * PAUSED:TODO(作业票的暂停状态).
     */
    public static final String PCANCEL = "PCANCEL";

    /**
     * CQCLOSE:TODO(超期关闭).
     */
    public static final String CQCLOSE = "CQCLOSE";

    /**
     * NULLIFY:TODO(作废).
     */
    public static final String NULLIFY = "NULLIFY";
    /**
     * 远程审批状态
     */
    public static final String REMOTE = "REMOTE";

    /**
     * VIRTUAL_UPLOAD:TODO(虚拟状态-PAD端的字段值).
     */
    public static final String VIRTUAL_UPLOAD = "UPLOAD";

    /**
     * REVIEW_STATUS_PROCEED:TODO(措施复查状态-进行中).
     */
    public static final String REVIEW_STATUS_PROCEED = "PROCEED";// 措施正在操作
    /**
     * REVIEW_STATUS_DELAY_PROCEED:TODO(延期操作-已经生成延期记录时的措施状态).
     */
    public static final String REVIEW_STATUS_DELAY_PROCEED = "DELAY_PROCEED";// 措施正在操作
    /**
     * REVIEW_STATUS_FINISH:TODO(措施复查状态-完成).
     */
    public static final String REVIEW_STATUS_FINISH = "FINISH";// 措施已经完成

    /**
     * SPSTATUS_JJB:TODO(完成交接班之后的作业票的状态).
     */
    public static final String SPSTATUS_JJB = "JJBSTATUS";

    /**
     * GAS_STATUS_PROCEED:TODO(气体检测的状态-进行中).
     */
    public static final String GAS_STATUS_PROCEED = "PROCEED";// 气体正在操作
    /**
     * GAS_STATUS_FINISH:TODO(气体检测的状态-已完成).
     */
    public static final String GAS_STATUS_FINISH = "FINISH";// 气体已经完成

    /**
     * SPSTATUS_SPAPPR:TODO(表示作业票审批通过).
     */
    public static final String SPSTATUS_SPAPPR = "SPAPPR";// PAD中的虚拟字段（PC没有）表示作业中
    /**
     * SPSTATUS_REVIEW:TODO(表示作业票复查过).
     */
    public static final String SPSTATUS_REVIEW = "REVIEW";// PAD中的虚拟字段（PC没有）表示复查过
    /**
     * SPSTATUS_DELAY:TODO(表示作业延期).
     */
    public static final String SPSTATUS_DELAY = "DELAY";// PAD中的虚拟字段（PC没有）表示延期
    /**
     * SPSTATUS_CQCLOSE:TODO(PAD超期关闭的状态).
     */
    public static final String SPSTATUS_CQCLOSE = "OVERCLOSE";// PAD中的虚拟字段（PC没有）表示自动关闭
    /**
     * SPSTATUS_NULLIFY:TODO(PAD自动自动作废的状态).
     */
    public static final String SPSTATUS_NULLIFY = "OVERSTATE";// PAD中的虚拟字段（PC没有）表示自动作废
}
