/**
 * Project Name:hse-entity-service
 * File Name:IRelativeEncoding.java
 * Package Name:com.hd.hse.constant
 * Date:2014年11月25日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 */
package com.hd.hse.constant;

/**
 * ClassName: IRelativeEncoding ()<br/>
 * date: 2014年11月25日 <br/>
 *
 * @author zhaofeng
 */
public interface IRelativeEncoding {

	/*
     * 记录所有关系配置信息中的编码
	 */

    /**
     * BIGANDSPECIAL:TODO(大小票关系).
     */
    public static final String BIGANDSPECIAL = "BIGANDSPECIAL";
    /**
     * DHYD:TODO(动火临时用电关系).
     */
    public static final String DHYD = "DHYD";
    /**
     * QTJCFY:TODO(气体检测复用关系).
     */
    public static final String QTJCFY = "QTJCFY";
    /**
     * CBSSK:TODO(承包商刷卡权限不受单位限制).
     */
    public static final String CBSSK = "CBSSK";
    /**
     * ISASYSCPCQT:TODO(增加同步PC端气体模块).
     */
    public static final String ISASYSCPCQT = "ISASYSCPCQT";
    /**
     * HCSJPZ:TODO(缓冲时间配置).
     */
    public static final String HCSJPZ = "HCSJPZ";
    /**
     * DELAYCONFIGUR:TODO (非连续延期缓冲时间配置)
     */
    public static final String DELAYCONFIGUR = "DELAYCONFIGUR";
    /**
     * PBQXGN:TODO(屏蔽取消功能).
     */
    public static final String PBQXGN = "PBQXGN";
    /**
     * PBQXGN:TODO(屏蔽暂停功能).
     */
    public static final String PBZTGN = "PBZTGN";
    /**
     * PBQTJCGN:TODO(屏蔽气体检测功能).
     */
    public static final String PBQTJCGN = "PBQTJCGN";
    /**
     * SXPCJY:TODO(进行时效频次校验).
     */
    public static final String SXPCJY = "SXPCJY";
    /**
     * JJBJHRBG:TODO(是否有交接班监护人变更).
     */
    public static final String JJBJHRBG = "JJBJHRBG";
    /**
     * JJBZYRBG:TODO(是否有作业人变更).
     */
    public static final String JJBZYRBG = "JJBZYRBG";
    /**
     * PROMPTQT:TODO(关闭时，是否提示气体检测的信息).
     */
    public static final String PROMPTQT = "PROMPTQT";

    /**
     * PROMPTQT:TODO(是否支持危害都不确认).
     */
    public static final String ISWHNOSURE = "ISWHNOSURE";
    /**
     * PROMPTQT:TODO(是否支持个人防护都不确认).
     */
    public static final String ISPPENOSURE = "ISPPENOSURE";

    /**
     * PCCSNOAPPLY:TODO(PC勾选措施不可选不适用).
     */
    public static final String PCCSNOAPPLY = "PCCSNOAPPLY";

    /**
     * ISDHYD:TODO(动作与电的关系).
     */
    public static final String ISDHYD = "DHYD";
    /**
     * SHOWMODELSTR:TODO(表示是否进行模块显示的过滤关系).
     */
    public static final String SHOWMODELSTR = "SHOWMODELSTR";

    /**
     * REUSINGLOCATIONCARD:TODO(是否重用位置).
     */
    public static final String REUSINGLOCATIONCARD = "REUSINGLOCATIONCARD";

    /**
     * UPDATAHANDLE:TODO(删除上传完后的所有数据).
     */
    public static final String UPDATAHANDLE = "UPDATAHANDLE";
    /**
     * CBSEXAUTHORITY:TODO(承包商是否具有审核厂里作业票的权限).
     */
    public static final String CBSEXAUTHORITY = "CBSEXAUTHORITY";
    /**
     * CARDISMD5:TODO(人员卡是否加密).
     */
    public static final String CARDISMD5 = "CARDISMD5";
    /**
     * QTJCSJZYHQ:TODO(气体检测时间早于会签).
     */
    public static final String QTJCSJZYHQ = "QTJCSJZYHQ";
    /**
     * FCGLCBS:TODO(分厂管理承包商).
     */
    public static final String FCGLCBS = "FCGLCBS";

    /**
     * QYXNWZ:TODO(是否启用虚拟位置).
     */
    public static final String QYXNWZ = "ISQYXNWZ";

    /**
     * FYQTJC:TODO(是否复用气体检测).
     */
    public static final String FYQTJC = "ISFYQTJC";

    /**
     * HBZYP:TODO(是否合并审核).
     */
    public static final String HBZYP = "ISHBZYP";

    /**
     * ISMENUCTRL:TODO(是否启用PAD端菜单权限控制). Created by liuyang on 2016年3月18日
     */
    public static final String ISMENUCTRL = "ISMENUCTRL";
    /**
     * ISTEST:TODO(是否调试状态).
     */
    public static final String ISTEST = "ISTEST";
    /**
     * ISZYSQOFONE:TODO(是否启用一票制).
     */
    public static final String ISZYSQOFONE = "ISZYSQOFONE";
    /**
     * 刷卡时显示证书资质
     */
    public static final String CERTCHECKBOX = "CERTCHECKBOX";
    /**
     * 是否启用imei校验
     */
    public static final String ISLICENCE = "ISLICENCE";
    /**
     * 是否启用拍照
     */
    public static final String PHOTOCONFIGUR = "PHOTOCONFIGUR";
    /**
     * 截取人员卡卡号
     * Created by liuyang on 2016年10月8日
     */
    public static final String ISSUBSTRING = "ISSUBSTRING";
    /**
     * 是否允许异步审批
     */
    public static final String ISASYNCAPPR = "ISASYNCAPPR";
    /**
     * 安全监督部门过滤
     */
    public static final String AQJDDEPTGL = "AQJDDEPTGL";
    /**
     * 现场监督显示配置
     */
    public static final String SUPERVISEDISPLAYCFG = "SUPERVISEDISPLAYCFG";
    /**
     * 远程审批显示配置
     */
    public static final String ISREMOTE = "ISREMOTE";
    /**
     * 气体检测复查，暂停结束 显示配置
     */
    public static final String GASDETECTPAUSE = "GASDETECTPAUSE";
    /**
     * 作业票浏览报表按级别显示
     */
    public static final String RQURL4LVL = "RQURL4LVL";
}
