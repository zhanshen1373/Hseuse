/**
 * Project Name:hse-entity-service
 * File Name:CheckApproveRight.java
 * Package Name:com.hd.hse.service.checkright
 * Date:2014年10月13日
 * Copyright (c) 2014, zhaofeng@ushayden.com All Rights Reserved.
 */
package com.hd.hse.service.checkright;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.hd.hse.common.entity.SuperEntity;
import com.hd.hse.common.exception.DaoException;
import com.hd.hse.common.exception.HDException;
import com.hd.hse.common.logger.LogUtils;
import com.hd.hse.constant.IRelativeEncoding;
import com.hd.hse.dao.BaseDao;
import com.hd.hse.dao.result.EntityListResult;
import com.hd.hse.dao.result.EntityResult;
import com.hd.hse.dao.result.MapResult;
import com.hd.hse.entity.base.Domain;
import com.hd.hse.entity.base.RelationTableName;
import com.hd.hse.entity.calendar.DutyTime;
import com.hd.hse.entity.common.PersonCard;
import com.hd.hse.entity.common.RyLine;
import com.hd.hse.entity.other.ContractorAptitudeConfig;
import com.hd.hse.entity.other.Persongroup;
import com.hd.hse.entity.time.ServerDateManager;
import com.hd.hse.entity.workorder.PersonSpecialTypeWork;
import com.hd.hse.entity.workorder.RepellentHjd;
import com.hd.hse.entity.workorder.WorkApprovalPersonRecord;
import com.hd.hse.entity.workorder.WorkOrder;
import com.hd.hse.service.config.IQueryRelativeConfig;
import com.hd.hse.service.config.QueryRelativeConfig;
import com.hd.hse.service.workorder.queryinfo.IQueryWorkInfo;
import com.hd.hse.service.workorder.queryinfo.QueryWorkInfo;
import com.hd.hse.utils.UtilTools;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName: CheckApproveRight ()<br/>
 * date: 2014年10月13日 <br/>
 *
 * @author zhaofeng
 */
public class CheckApproveRight implements ICheckApproveRight {

    /**
     * logger:TODO(日志).
     */
    private final Logger logger = LogUtils.getLogger(CheckApproveRight.class);

    /**
     * dao:TODO(dao层).
     */
    BaseDao dao = null;
    /**
     * sbSql:TODO(字符串连接类).
     */
    StringBuilder sbSql = null;
    /**
     * isContractUnit:TODO(是否区分承包商的单位).
     */
    private boolean isContractUnit = true;
    /**
     * relativeConfig:TODO(关系检查).
     */
    public IQueryRelativeConfig relativeConfig = new QueryRelativeConfig();
    /**
     * 值班人员组编码
     */

    private final String code = "DUTY";
    private RyLine ryLine;

    /**
     * setContractnit:(设置是否区分承包商的单位). <br/>
     * date: 2014年10月13日 <br/>
     *
     * @author zhaofeng
     */
    public final void setContractUnit(boolean isContractUnit) {
        this.isContractUnit = isContractUnit;
    }

    /**
     * TODO 实现检查刷卡人是否有审批权限
     *
     * @throws HDException
     * @see com.hd.hse.service.checkright.ICheckApproveRight#(com.hd.hse.common.entity.SuperEntity,
     * com.hd.hse.common.entity.SuperEntity,
     * com.hd.hse.common.entity.SuperEntity)
     */
    @Override
    public void checkApproveRight(Context context, Handler handler, SuperEntity nodeEntity,
                                  SuperEntity personCard, SuperEntity workEntity) throws HDException {
        dao = new BaseDao();
        // TODO Auto-generated method stub
        // 根据人员卡号获取人员信息
        // 判断人员部门和作业票部门之间的关系
        // 根据环节点，人员，作业票判断走普通流程还是走承包商流程
        // 1.普通流程走人员组和人员组关系；2.承包商流程判断承包商的入厂时效和资质审核
        if (nodeEntity == null)
            throw new HDException("环节点信息为空,不能判断权限！");
        if (personCard == null)
            throw new HDException("人员信息为空,不能判断权限！");
        if (workEntity == null)
            throw new HDException("作业票信息为空,不能判断权限！");

        personCard = getPersonCard(personCard);// 获取人员信息

        IQueryRelativeConfig relation = new QueryRelativeConfig();
        RelationTableName relationEntity = new RelationTableName();
        relationEntity.setSys_type(IRelativeEncoding.CERTCHECKBOX);
        boolean certcheckbox = relation.isHadRelative(relationEntity);


        RelationTableName relationEntityYpz = new RelationTableName();
        relationEntityYpz.setSys_type(IRelativeEncoding.ISZYSQOFONE);
        boolean iszysqofone = relation.isHadRelative(relationEntityYpz);

        if (isConstractFlow(nodeEntity, personCard, workEntity)) {
            //承包商流程
//            try {
//                ryLine = getRykData(personCard);
//            } catch (Exception e) {
//                throw new HDException(e.getMessage());
//
//            }
            //刷卡时显示证书资质
            if (certcheckbox) {
                if (!iszysqofone) {
                    //非大连项目，比如：吉林项目
                    //需要先进行一系列的校验,大连项目不需要进行校验，直接checkContractFlowForYPZ
                    checkContractFlow2(nodeEntity, personCard, workEntity);
                }
                checkContractFlowForYPZ(context, handler, nodeEntity, personCard, workEntity);
            } else {
                // 走承包商流程
                checkContractFlow(nodeEntity, personCard, workEntity);
            }
        } else {
            // 走非承包商流程(未入厂的会进来)
            checkNonContractFlow(nodeEntity, personCard, workEntity);
            if (certcheckbox) {
                checkContractFlowForYPZ(context, handler, nodeEntity, personCard, workEntity);
            }
        }
        setNodeEntityCheckIn(nodeEntity, personCard);// 有权限，给环节点赋值
    }

    private RyLine getRykData(SuperEntity personCard) throws HDException {

        PersonCard psnCard = null;
        if (personCard instanceof PersonCard)
            psnCard = (PersonCard) personCard;


        StringBuilder sq = new StringBuilder();

//        sq.append("select * from ud_cbsgl_ryline ryl  where ryl.rowid in " +
//                "(select min(rowid) id from ud_cbsgl_ryline where  processstatus='已入厂' group by IDCARD) " +
//                "and (ryl.innercard='").append(psnCard.getAttribute("ryrcnum")).append("' or " +
//                "ryl.IDCARD='").append(psnCard.getPersonid()).append("') and ryl.name = '").append(psnCard.getPersonid_desc()).append("';");

        sq.append("select max(changedate),* from ud_cbsgl_ryline ryl where (ryl.innercard='" + psnCard.getAttribute("ryrcnum") + "' or ryl.IDCARD='" + psnCard.getPersonid() + "');");


        RyLine ryLine = null;
        try {
            ryLine = (RyLine) dao.executeQuery(sq.toString(),
                    new EntityResult(RyLine.class));
        } catch (DaoException e) {
            e.printStackTrace();
        }

        if (ryLine != null) {
            return ryLine;
        } else {
            throw new HDException("承包商人员明细表中没有数据！");
        }
//        return null;

    }

    /**
     * getPersonCard:(获取刷卡人员信息). <br/>
     * date: 2014年10月13日 <br/>
     *
     * @param personCard
     * @return
     * @throws HDException
     * @author zhaofeng
     */

    private SuperEntity getPersonCard(SuperEntity personCard)
            throws HDException {
        if (sbSql == null)
            sbSql = new StringBuilder();
        sbSql.setLength(0);
        // 获取人员信息
        PersonCard psnCard = null;
        if (personCard instanceof PersonCard)
            psnCard = (PersonCard) personCard;

        try {
            String cardNum = psnCard.getPcardnum();// 人员卡卡号
            String cardNumMD5 = "";
            // 判断是否需要对人员卡加密
            RelationTableName relationEntity = new RelationTableName();
            relationEntity.setSys_type(IRelativeEncoding.CARDISMD5);
            relationEntity.setSys_fuction(null);
            relationEntity.setSys_value("");
            if (relativeConfig.isHadRelative(relationEntity)) {
                cardNumMD5 = UtilTools.string2MD5(cardNum);
            }
            sbSql.append("select * from ud_zyxk_ryk ");
            // 通过人员卡登录
            if (!StringUtils.isEmpty(cardNum)) {
                sbSql.append(" where pcardnum='").append(cardNum).append("' COLLATE NOCASE");
                sbSql.append(" or pcardnum='").append(cardNumMD5).append("' COLLATE NOCASE;");
            } else {
                throw new HDException("不存在该卡号的人员！");
            }
            psnCard = (PersonCard) dao.executeQuery(sbSql.toString(),
                    new EntityResult(PersonCard.class));
            if (psnCard == null)
                throw new HDException("无效刷卡用户！");
            if (psnCard.getIscan() == null || psnCard.getIscan() == 0) {
                throw new HDException("该人员卡已失效，请联系相关负责人！");
            }
            if (psnCard.getIshmd() != null && psnCard.getIshmd() == 1) {
                //pc端好像并没有这个值
                throw new HDException("该人员已进入黑名单！");
            }
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            throw new DaoException("判断审批权限失败！");
        }
        return psnCard;
    }

    /**
     * isConstractFlow:(判断是否满足承包商流程). <br/>
     * date: 2014年10月13日 <br/>
     *
     * @param nodeEntity
     * @param personCard
     * @param workEntity
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    private boolean isConstractFlow(SuperEntity nodeEntity,
                                    SuperEntity personCard, SuperEntity workEntity) throws HDException {
        // 判断是否承包商流程
        if (nodeEntity.getAttribute("iscbs") == null)
            throw new HDException("环节点信息的iscbs属性获取失败！");
        if (personCard.getAttribute("iscbs") == null)
            throw new HDException("人员信息的iscbs属性获取失败！");
        if (workEntity.getAttribute("iscbs") == null)
            throw new HDException("作业票信息的iscbs属性获取失败！");
        boolean cbsexauthority = false;
        RelationTableName relationEntityAu = new RelationTableName();
        relationEntityAu.setSys_type(IRelativeEncoding.CBSEXAUTHORITY);
        relationEntityAu.setSys_fuction(null);
        relationEntityAu
                .setSys_value(workEntity.getAttribute("zypclass") == null ? ""
                        : workEntity.getAttribute("zypclass").toString());
        if (relativeConfig.isHadRelative(relationEntityAu)) {
            cbsexauthority = true;
        }
        IQueryRelativeConfig relation = new QueryRelativeConfig();
        /*RelationTableName relationEntityYpz = new RelationTableName();
        relationEntityYpz.setSys_type(IRelativeEncoding.ISZYSQOFONE);
        boolean iszysqofone = relation.isHadRelative(relationEntityYpz);*/
        if ((Integer.parseInt(nodeEntity.getAttribute("iscbs").toString())) == 1
                && ((Integer.parseInt(personCard.getAttribute("iscbs")
                .toString())) == 1 /*|| iszysqofone*/)
                && ((Integer.parseInt(workEntity.getAttribute("iscbs")
                .toString())) == 1 || cbsexauthority)) {
            // 判断是否需要区别单位
            RelationTableName relationEntity = new RelationTableName();
            relationEntity.setSys_type(IRelativeEncoding.CBSSK);
            relationEntity.setSys_fuction(null);
            relationEntity
                    .setSys_value(workEntity.getAttribute("zypclass") == null ? ""
                            : workEntity.getAttribute("zypclass").toString());
            //没配才会进来
            if (!relativeConfig.isHadRelative(relationEntity)) {
                // 需要区别单位
                if (personCard.getAttribute("department") == null)
                    throw new HDException("人员信息的department属性获取失败！");
                if (workEntity.getAttribute("zydept") == null)
                    throw new HDException("作业票信息的zydept属性获取失败！");
                if (personCard.getAttribute("department").toString()
                        .equals(workEntity.getAttribute("zydept").toString()))
                    return true;
                throw new HDException("人员所在单位与作业票作业单位不相符！");
//                return false;
            }
            relationEntity.setSys_type(IRelativeEncoding.FCGLCBS);
            if (relativeConfig.isHadRelative(relationEntity)) {
                // 启用该关系，作业票属地部门与人员卡属地部门一致，才有继续承包商的权限，否则就得看看是否配置了相应的权限
                if (workEntity.getAttribute("zyarea") == null)
                    throw new HDException("作业票信息的zyarea属性获取失败！");
                if (personCard.getAttribute("zyarea") == null)
                    throw new HDException("人员信息的zyarea属性获取失败！");
                if (personCard.getAttribute("zyarea").toString()
                        .contains(workEntity.getAttribute("zyarea").toString()))
                    return true;
                throw new HDException("人员未在作业票属地单位办理入厂！");
//                return false;
            }
            return true;// 不需要区别单位
        }
        return false;
    }

    private void checkContractFlowForYPZ(final Context context, Handler handler,
                                         final SuperEntity nodeEntity, SuperEntity personCard,
                                         final SuperEntity workEntity) throws HDException {
        //checkZbPersonGroup(nodeEntity, code);
        // 走承包商流程
        if (sbSql == null)
            sbSql = new StringBuilder();
        sbSql.setLength(0);
        try {
            // 入场时效检验

            if (ryLine != null) {

                /*暂时不用
                if (ryLine.getAttribute("endtime") == null && (int) personCard.getAttribute("iscbs") == 1)
                    throw new HDException("此刷卡人未配置离厂日期！");
                if ((int) personCard.getAttribute("iscbs") == 1 && !compareDate(ryLine.getAttribute("endtime").toString(),
                        null))
                    throw new HDException(
                            personCard.getAttribute("personid_desc") == null ? ""
                                    : personCard.getAttribute("personid_desc")
                                    .toString() + "入厂时间已经到期，不能审核！");
                */
            } else {

                if (personCard.getAttribute("endtime") == null && (int) personCard.getAttribute("iscbs") == 1)
                    throw new HDException("此刷卡人未配置离厂日期！");
                if ((int) personCard.getAttribute("iscbs") == 1 && !compareDate(personCard.getAttribute("endtime").toString(),
                        null))
                    throw new HDException(
                            personCard.getAttribute("personid_desc") == null ? ""
                                    : personCard.getAttribute("personid_desc")
                                    .toString() + "入厂时间已经到期，不能审核！");

            }


            if ((Integer) nodeEntity.getAttribute("iscbszz") == 1) {

                IQueryRelativeConfig relation = new QueryRelativeConfig();
                RelationTableName relationEntityYpz = new RelationTableName();
                relationEntityYpz.setSys_type(IRelativeEncoding.ISZYSQOFONE);
                boolean iszysqofone = relation.isHadRelative(relationEntityYpz);

                if (iszysqofone) {
                    //大连一票制用的是topzypclass,strzyspqxid
                    sbSql.append("select sszz from ud_zyxk_cbszz where topzypclass='").append(workEntity.getAttribute("zypclass"));
                    sbSql.append("' and ud_zyxk_zyspqxid in(")
                            .append(nodeEntity.getAttribute("strzyspqxid"))
                            .append(");");
                } else {
                    sbSql.append("select sszz from ud_zyxk_cbszz where zytype='").append(workEntity.getAttribute("zypclass"));
                    sbSql.append("' and ud_zyxk_zyspqxid in(")
                            .append(nodeEntity.getAttribute("ud_zyxk_zyspqxid"))
                            .append(");");
                }


                List<ContractorAptitudeConfig> cacs = (List<ContractorAptitudeConfig>) dao.executeQuery(sbSql.toString(), new EntityListResult(ContractorAptitudeConfig.class));
                sbSql.setLength(0);
                if (cacs == null || cacs.size() == 0) {
                    sbSql.append(
                            "select r.*, a.description as description from ud_cbsgl_rytzzy r"
                                    + " left join alndomain a on r.certificatetype = a.value and a.domainid in('ZZ_CERTIFICATE','CERTIFICATETYPE')"
                                    + " where idcard='")
                            .append(personCard.getAttribute("personid").toString())
                            .append("';");
                } else {
                    sbSql.append(
                            "select r.*, a.description as description from ud_cbsgl_rytzzy r"
                                    + " left join alndomain a on r.certificatetype = a.value and a.domainid in('ZZ_CERTIFICATE','CERTIFICATETYPE')"
                                    + " where certificatetype in (select sszz from ud_zyxk_cbszz where ");
                    if (iszysqofone) {
                        sbSql.append("topzypclass='");
                    } else {
                        sbSql.append("zytype='");
                    }
                    sbSql.append(workEntity.getAttribute("zypclass"))
                            .append("'  and ud_zyxk_zyspqxid in(");
                    if (iszysqofone) {
                        sbSql.append(nodeEntity.getAttribute("strzyspqxid").toString());
                    } else {
                        sbSql.append(nodeEntity.getAttribute("ud_zyxk_zyspqxid").toString());
                    }
                    sbSql.append(")) and idcard='")
                            .append(personCard.getAttribute("personid").toString())
                            .append("';");
                }
                final List<PersonSpecialTypeWork> pstws = (List<PersonSpecialTypeWork>) dao
                        .executeQuery(sbSql.toString(), new EntityListResult(
                                PersonSpecialTypeWork.class));
                Message msg = handler.obtainMessage();
                msg.what = 0;
                msg.obj = pstws;
                handler.sendMessage(msg);
            }
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("查询承包商人员资质失败，请联系管理员！");
        }
    }

    private void checkpstw(PersonSpecialTypeWork pstw, SuperEntity workEntity,
                           SuperEntity nodeEntity) throws HDException {
        try {
            if (pstw == null || pstw.getDqdate() == null) {
                sbSql.setLength(0);
                sbSql.append(
                        "select domain.description as description,sszz from ud_zyxk_cbszz inner join (select * from alndomain where domainid in('ZZ_CERTIFICATE','CERTIFICATETYPE')) as domain on sszz=domain.value where zytype='")
                        .append(workEntity.getAttribute("zypclass"))
                        .append("'  and ud_zyxk_zyspqxid='");
                sbSql.append(nodeEntity.getAttribute("ud_zyxk_zyspqxid")
                        .toString());
                sbSql.append("';");
                HashMap<String, Object> mapRS = (HashMap<String, Object>) dao
                        .executeQuery(sbSql.toString(), new MapResult());
                if (mapRS == null || mapRS.size() == 0) {
                    throw new HDException("该节点资质配置缺失，请联系管理员。");
                } else {
                    if (pstw == null) {
                        throw new HDException("没有配置【"
                                + mapRS.get("description") + "】证书，请联系管理员！");
                    } else if (pstw.getDqdate() == null) {
                        throw new HDException("【" + mapRS.get("description")
                                + "】证书请配置到期时间！");
                    }


                }

            }

            if (pstw != null && !compareDate(pstw.getDqdate(), null)) {
                IQueryWorkInfo queryWorkInfo = new QueryWorkInfo();
                boolean config = false;
                for (Domain domain : queryWorkInfo.queryDomain(
                        "ZZ_CERTIFICATE','CERTIFICATETYPE", null)) {
                    if (domain.getValue().equals(pstw.getCertificatetype())) {
                        config = true;
                        throw new HDException(domain.getDescription()
                                + "证书已经到期，不能审核！");
                    }
                }
                if (!config) {
                    throw new HDException(pstw.getCertificatetype()
                            + "证书已经到期，不能审核！");
                }
            }
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("查询承包商人员资质失败，请联系管理员！");
        }
    }

    /**
     * checkContractFlow:(走承包商刷卡流程). <br/>
     * date: 2014年10月14日 <br/>
     *
     * @param nodeEntity
     * @param personCard
     * @param workEntity
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    private void checkContractFlow(SuperEntity nodeEntity,
                                   SuperEntity personCard, SuperEntity workEntity) throws HDException {
        // 走承包商流程
        if (sbSql == null)
            sbSql = new StringBuilder();
        sbSql.setLength(0);
        try {
            String sddept = ((WorkOrder) workEntity).getSddept();


            // 入场时效检验
            if (((String) personCard.getAttribute("trainresult")) != null) {
                if (((String) personCard.getAttribute("trainresult")).contains(sddept)) {
                    //未通过培训
                    throw new HDException("未通过公司级或车间级培训，请完成培训之后再进行施工作业");
                }
            }

            /*优化注释掉的
            if (personCard.getAttribute("endtime") == null)
                throw new HDException("此刷卡人未配置离厂日期！");
            if (!compareDate(personCard.getAttribute("endtime").toString(),
                    null))
                throw new HDException(
                        personCard.getAttribute("personid_desc") == null ? ""
                                : personCard.getAttribute("personid_desc")
                                .toString() + "入厂时间已经到期，不能审核！");
           */

            /*暂时不用
            if (ryLine.getAttribute("endtime") == null)
                throw new HDException("此刷卡人未配置离厂日期！");
            if (!compareDate(ryLine.getAttribute("endtime").toString(),
                    null))
                throw new HDException(
                        personCard.getAttribute("personid_desc") == null ? ""
                                : personCard.getAttribute("personid_desc")
                                .toString() + "入厂时间已经到期，不能审核！");
             */

            // 资质证书检验ContractorAptitudeConfig
            if (nodeEntity.getAttribute("iscbszz") == null)
                throw new HDException("获取审批环节点的iscbszz属性失败！");
            if (nodeEntity.getAttribute("ud_zyxk_zyspqxid") == null)
                throw new HDException("获取审批环节点的ud_zyxk_zyspqxid属性失败！");
            if (personCard.getAttribute("personid") == null)
                throw new HDException("获取人员信息的personid属性失败！");
            if ((Integer) nodeEntity.getAttribute("iscbszz") == 1) {
                sbSql.append(
                        "select * from ud_cbsgl_rytzzy where certificatetype in (select sszz from ud_zyxk_cbszz where zytype='")
                        .append(workEntity.getAttribute("zypclass"))
                        .append("'  and ud_zyxk_zyspqxid='");
                sbSql.append(nodeEntity.getAttribute("ud_zyxk_zyspqxid")
                        .toString());
                sbSql.append("') and idcard='")
                        .append(personCard.getAttribute("personid").toString())
                        .append("';");
                PersonSpecialTypeWork pstw = (PersonSpecialTypeWork) dao
                        .executeQuery(sbSql.toString(), new EntityResult(
                                PersonSpecialTypeWork.class));
                checkpstw(pstw, workEntity, nodeEntity);
            }
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("查询承包商人员资质失败，请联系管理员！");
        }
    }

    /**
     * 承包商流程校验
     *
     * @param nodeEntity
     * @param personCard
     * @param workEntity
     * @throws HDException
     */
    private void checkContractFlow2(SuperEntity nodeEntity,
                                    SuperEntity personCard, SuperEntity workEntity) throws HDException {
        // 走承包商流程
        try {
            String sddept = ((WorkOrder) workEntity).getSddept();

            // 入场时效检验
            if (((String) personCard.getAttribute("trainresult")) != null) {
                if (((String) personCard.getAttribute("trainresult")).contains(sddept)) {
                    //未通过培训
                    throw new HDException("未通过公司级或车间级培训，请完成培训之后再进行施工作业");
                }
            }




          /* 暂时不用
            if (ryLine.getAttribute("endtime") == null)
                throw new HDException("此刷卡人未配置离厂日期！");
            if (!compareDate(ryLine.getAttribute("endtime").toString(),
                    null))
                throw new HDException(
                        personCard.getAttribute("personid_desc") == null ? ""
                                : personCard.getAttribute("personid_desc")
                                .toString() + "入厂时间已经到期，不能审核！");
           */

            /*优化注释掉的
            if (personCard.getAttribute("endtime") == null)
                throw new HDException("此刷卡人未配置离厂日期！");
            if (!compareDate(personCard.getAttribute("endtime").toString(),
                    null))
                throw new HDException(
                        personCard.getAttribute("personid_desc") == null ? ""
                                : personCard.getAttribute("personid_desc")
                                .toString() + "入厂时间已经到期，不能审核！");
             */

            // 资质证书检验ContractorAptitudeConfig
            if (nodeEntity.getAttribute("iscbszz") == null)
                throw new HDException("获取审批环节点的iscbszz属性失败！");
            if (nodeEntity.getAttribute("ud_zyxk_zyspqxid") == null)
                throw new HDException("获取审批环节点的ud_zyxk_zyspqxid属性失败！");
            if (personCard.getAttribute("personid") == null)
                throw new HDException("获取人员信息的personid属性失败！");

            //if (nodeEntity.getAttribute("")!=null&&)
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            throw new DaoException("查询承包商人员资质失败，请联系管理员！");
        }
    }


    /**
     * checkNonContractFlow:(走非承包商刷卡流程). <br/>
     * date: 2014年10月14日 <br/>
     *
     * @param nodeEntity
     * @param personCard
     * @param workEntity
     * @return
     * @throws HDException
     * @author zhaofeng
     */

    private void checkNonContractFlow(SuperEntity nodeEntity,
                                      SuperEntity personCard, SuperEntity workEntity) throws HDException {
        // 走非承包商流程
        boolean flag = false;

        //校验值班人员组
        checkZbPersonGroup(nodeEntity, code);

        String authorizer = getAuthorizer(nodeEntity, personCard);
        if (checkPersonWork(personCard, workEntity)
                || !StringUtils.isEmpty(authorizer)) {
            // 走私有组,不满足再走公共组
            if (checkPrivateGroup(nodeEntity, personCard, authorizer))
                flag = true;
        }
        if (!flag) {
            // 直接走公共组
            checkPublicGroup(nodeEntity, personCard, workEntity, authorizer);
        }

    }

    /**
     * checkPublicGroup:(判断公共组). <br/>
     * date: 2014年10月14日 <br/>
     *
     * @param nodeEntity
     * @param personCard
     * @param authorizer
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    private void checkPublicGroup(SuperEntity nodeEntity,
                                  SuperEntity personCard, SuperEntity workEntity, String authorizer)
            throws HDException {
        if (sbSql == null)
            sbSql = new StringBuilder();
        sbSql.setLength(0);
        try {
            if (personCard.getAttribute("personid") == null)
                throw new HDException("该刷卡人员的编码信息配置错误，请联系管理员！");
            if (nodeEntity.getAttribute("qxrole") == null)
                throw new HDException("该环节点没有配置人员组信息，请联系管理员！");
            // if (personCard.getAttribute("department") == null)
            // throw new HDException("该刷卡人员的部门信息配置错误，请联系管理员！");
            if (workEntity.getAttribute("sddept") == null)
                throw new HDException("作业票的属地部门信息没有配置,请联系管理员！");
            sbSql.append("select PERSONGROUP from PERSONGROUP,UD_SY_DEPT as dept where PERSONGROUP in (");
            sbSql.append(
                    "select PERSONGROUP from persongroupteam where RESPPARTYGROUP in ('")
                    .append(personCard.getAttribute("personid").toString())
                    .append("','").append(authorizer).append("'))");
            sbSql.append(" and  (',' || SSDEPT ||',' like '%,' || dept.DEPTNUM ||',%' and (");
            sbSql.append(
                    "  select VREPORTDEPTNUM from UD_SY_DEPT where deptnum='")
                    .append(workEntity.getAttribute("sddept").toString())
                    .append("') ");
            sbSql.append("  like dept.VREPORTDEPTNUM || '%') and  ',' || '")
                    .append(nodeEntity.getAttribute("qxrole").toString())
                    .append("' || ',' like '%,'|| PERSONGROUP ||',%';");
            Object object = dao.executeQuery(sbSql.toString(),
                    new EntityResult(Persongroup.class));
            if (object == null)
                throw new HDException("该刷卡人未配置刷卡权限！");
        } catch (DaoException e) {
            // TODO: handle exception
            logger.error(e.getMessage(), e);
            throw new HDException("查询共有组组信息失败，请联系管理员！", e);
        }
    }

    /**
     * checkPrivateGroup:(判断私有组). <br/>
     * date: 2014年10月14日 <br/>
     *
     * @param nodeEntity
     * @param personCard
     * @param authorizer
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    private boolean checkPrivateGroup(SuperEntity nodeEntity,
                                      SuperEntity personCard, String authorizer) throws HDException {
        if (sbSql == null)
            sbSql = new StringBuilder();
        sbSql.setLength(0);
        try {
            if (personCard.getAttribute("personid") == null)
                throw new HDException("该刷卡人员的编码信息配置错误，请联系管理员！");
            if (nodeEntity.getAttribute("qxrole") == null)
                throw new HDException("该环节点没有配置人员组信息，请联系管理员！");
            sbSql.append("select PERSONGROUP from PERSONGROUP where PERSONGROUP in (select PERSONGROUP from persongroupteam ");
            sbSql.append(" where RESPPARTYGROUP in ('")
                    .append(personCard.getAttribute("personid").toString())
                    .append("','").append(authorizer).append("'))");
            sbSql.append(" and ifnull(SSDEPT,'')='' and ',' || '")
                    .append(nodeEntity.getAttribute("qxrole").toString())
                    .append("' || ',' like '%,'|| PERSONGROUP ||',%';");
            Object object = dao.executeQuery(sbSql.toString(),
                    new EntityResult(Persongroup.class));
            if (object != null)
                return true;
            return false;
        } catch (DaoException e) {
            // TODO: handle exception
            logger.error(e.getMessage(), e);
            throw new HDException("查询私有组信息失败，请联系管理员！", e);
        }
    }

    /**
     * checkPersonWork:(判断人员与作业票的部门关系). <br/>
     * date: 2014年10月13日 <br/>
     *
     * @param personCard
     * @param workEntity
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    private boolean checkPersonWork(SuperEntity personCard,
                                    SuperEntity workEntity) throws HDException {
        // 检查人员和作业票的部门是否有关系-相同或者上下级关系
        if (sbSql == null)
            sbSql = new StringBuilder();
        sbSql.setLength(0);
        try {
            if (workEntity.getAttribute("sddept") == null)
                throw new HDException("作业票的属地部门信息没有配置,请联系管理员！");
            if (personCard.getAttribute("department") == null)
                throw new HDException("该刷卡人员的部门信息配置错误，请联系管理员！");
            sbSql.append("select dept.vreportdeptnum from ud_sy_dept dept left join ");
            sbSql.append(
                    "(select vreportdeptnum from ud_sy_dept  where deptnum='")
                    .append(workEntity.getAttribute("sddept").toString())
                    .append("') as zypdept on ");
            sbSql.append(" dept.vreportdeptnum like '%zypdept.vreportdeptnum%' ");
            sbSql.append(" where dept.deptnum='")
                    .append(personCard.getAttribute("department").toString())
                    .append("';");
            SuperEntity entity = (SuperEntity) dao.executeQuery(
                    sbSql.toString(), new EntityResult(PersonCard.class));
            if (entity != null)
                return true;
        } catch (DaoException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
            throw new HDException("查询人员部门和作业票的部门失败,请联系管理员！");
        }
        return false;
    }

    /**
     * getAuthorizer:(获取被授权人信息). <br/>
     * date: 2014年10月14日 <br/>
     *
     * @param nodeEntity
     * @param personCard
     * @return
     * @throws HDException
     * @author zhaofeng
     */
    private String getAuthorizer(SuperEntity nodeEntity, SuperEntity personCard)
            throws HDException {
        if (nodeEntity.getAttribute("qxperson") == null)
            return null;
        if (personCard.getAttribute("personid") == null)
            return null;
        String qxpersons = nodeEntity.getAttribute("qxperson").toString();
        String personid = personCard.getAttribute("personid").toString();
        if (!StringUtils.isEmpty(qxpersons)) {
            // 存在授权环节，进行相应的处理
            String[] personArray = qxpersons.split(";");
            for (int i = 0; i < personArray.length; i++) {
                if (personArray[i].contains(personid)) {
                    String authorizer = personArray[i].split("#")[0];
                    if (!personid.equals(authorizer)) {
                        return authorizer;
                    }
                }
            }
        }
        return null;
    }

    /**
     * setNodeEntityCheckIn:(给审批信息赋值). <br/>
     * date: 2014年10月22日 <br/>
     *
     * @param nodeEntity
     * @param personCard
     * @author zhaofeng
     */
    private void setNodeEntityCheckIn(SuperEntity nodeEntity,
                                      SuperEntity personCard) {
        String personid = "";
        String persondescString = "";
        if (nodeEntity.getAttribute("defaultpersonid") != null
                && !StringUtils.isEmpty(nodeEntity.getAttribute(
                "defaultpersonid").toString())) {
            personid = nodeEntity.getAttribute("defaultpersonid").toString();
        }
        if (nodeEntity.getAttribute("defaultpersondesc") != null
                && !StringUtils.isEmpty(nodeEntity.getAttribute(
                "defaultpersondesc").toString())) {
            persondescString = nodeEntity.getAttribute("defaultpersondesc")
                    .toString();
        }
        if (!StringUtils.isEmpty(personid)) {
            if (personCard.getAttribute("personid") != null
                    && !StringUtils.isEmpty(personCard.getAttribute("personid")
                    .toString())) {
                personid += ("," + personCard.getAttribute("personid")
                        .toString());
            }
        } else {
            if (personCard.getAttribute("personid") != null
                    && !StringUtils.isEmpty(personCard.getAttribute("personid")
                    .toString())) {
                personid += (personCard.getAttribute("personid").toString());
            }
        }

        if (!StringUtils.isEmpty(persondescString)) {
            if (personCard.getAttribute("personid_desc") != null
                    && !StringUtils.isEmpty(personCard.getAttribute(
                    "personid_desc").toString())) {
                persondescString += ("," + personCard.getAttribute(
                        "personid_desc").toString());
            }
        } else {
            if (personCard.getAttribute("personid_desc") != null
                    && !StringUtils.isEmpty(personCard.getAttribute(
                    "personid_desc").toString())) {
                persondescString += (personCard.getAttribute("personid_desc")
                        .toString());
            }
        }
        nodeEntity.setAttribute("personid", personid);
        nodeEntity.setAttribute("persondesc", persondescString);
        nodeEntity.setAttribute("departmentdesc",
                personCard.getAttribute("department_desc"));
    }

    /**
     * compareDate:(比较时间大小). <br/>
     * date: 2014年10月13日 <br/>
     *
     * @param date1
     * @param date2
     * @return
     * @throws HDException 人员资质，承包商人员
     * @author zhaofeng
     */
    private boolean compareDate(String date1, String date2) throws HDException {
        DateFormat df = null;
        if (date1.length() == 19) {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            df = new SimpleDateFormat("yyyy-MM-dd");
        }
        try {
            if (date2 == null) {
                if (df.parse(date1).getTime() >= ServerDateManager.getCurrentTimeMillis())
                    return true;
            } else {
                if (df.parse(date1).getTime() >= df.parse(date2).getTime())
                    return true;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            throw new HDException("时间格式不正确！");
        }
        return false;
    }

    /**
     * checkApproveRight:实现检查刷卡人是否有审批权限(). <br/>
     * date: 2015年9月8日 <br/>
     *
     * @param nodeEntity
     * @param personCard
     * @param workEntity
     * @throws HDException
     * @author lxf
     */
    public void checkApproveRight(SuperEntity nodeEntity,
                                  SuperEntity personCard, List<SuperEntity> workEntity)
            throws HDException {
        dao = new BaseDao();
        // TODO Auto-generated method stub
        // 根据人员卡号获取人员信息
        // 判断人员部门和作业票部门之间的关系
        // 根据环节点，人员，作业票判断走普通流程还是走承包商流程
        // 1.普通流程走人员组和人员组关系；2.承包商流程判断承包商的入厂时效和资质审核
        if (nodeEntity == null)
            throw new HDException("环节点信息为空,不能判断权限！");
        if (personCard == null)
            throw new HDException("人员信息为空,不能判断权限！");
        if (workEntity == null)
            throw new HDException("作业票信息为空,不能判断权限！");
        personCard = getPersonCard(personCard);// 获取人员信息
        for (SuperEntity superEntity : workEntity) {

            if (isConstractFlow(nodeEntity, personCard, superEntity)) {

//                try {
//                    ryLine = getRykData(personCard);
//                } catch (Exception e) {
//                    throw new HDException(e.getMessage());
//                }

                // 走承包商流程
                checkContractFlow(nodeEntity, personCard, superEntity);
            } else {
                // 走非承包商流程
                checkNonContractFlow(nodeEntity, personCard, superEntity);
            }
        }
        setNodeEntityCheckIn(nodeEntity, personCard);// 有权限，给环节点赋值
    }

    /**
     * 校验值班人员组
     * 1.判断qxrole是否包含值班人员组
     * 2.不包含：return
     * 3.包含：判断当前时间是否在值班时间内
     * 4.不在值班时间内：qxrole去掉值班人员组，return
     * 5.在值班时间内：return
     *
     * @param nodeEntity 人员审批权限
     * @param code       值班人员组
     */
    private void checkZbPersonGroup(SuperEntity nodeEntity, String code) throws HDException {
        String qxrole = (String) nodeEntity.getAttribute("qxrole");
        if (qxrole == null)
            return;
        if (!qxrole.contains(code))
            return;
        else if (isInDutyTime()) {
            return;
        } else {
            //执行完后，会刷不上卡
            qxrole = qxrole.replace(code, "");
            nodeEntity.setAttribute("qxrole", qxrole);
        }
    }

    // 根据日期取得星期几
    public String getWeek(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String week = sdf.format(date);
        return week;
    }

    /**
     * 判断是否在值班时间内
     *
     * @return
     */
    private boolean isInDutyTime() throws HDException {
        //判断是不是在假期内,如果在假期内，返回true
        //如果是周六日，返回true
        //如果不在假期内在值班时段内也返回true
        boolean flag = false;
        String sql = "select startdate , enddate,isweekday from calendar";
        try {
            List<DutyTime> dutyTimes = (List<DutyTime>) dao.executeQuery(sql, new EntityListResult(DutyTime.class));
            if (dutyTimes != null) {
                for (int i = 0; i < dutyTimes.size(); i++) {
                    String startdate = dutyTimes.get(i).getStartdate();
                    String enddate = dutyTimes.get(i).getEnddate();
                    Integer isweekday = dutyTimes.get(i).getIsweekday();

                    if (!TextUtils.isEmpty(startdate) && !TextUtils.isEmpty(enddate)) {
                        startdate += " 00:00:00";
                        enddate += " 24:00:00";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        try {
                            Date currentTime = ServerDateManager.getCurrentDate();
                            Date startdate2 = simpleDateFormat.parse(startdate);
                            Date enddate2 = simpleDateFormat.parse(enddate);
                            if (currentTime.after(startdate2) && currentTime.before(enddate2)) {
                                if (isweekday == 0) {
                                    return true;
                                } else if (isweekday == 1) {
                                    flag = true;
                                }
                            }
                        } catch (ParseException e) {
                            //e.printStackTrace();
                            logger.error(e.getMessage(), e);
                            throw new HDException("假期时间转化异常");
                        }
                    }


                }


            }
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            throw new HDException("数据库查询假期时间异常");
        }

        //判断是否是周六日
        String week = getWeek(ServerDateManager.getCurrentDate());
        if (!flag) {
            if (week.equals("周六") || week.equals("周日")) {
                return true;
            }
        }

        //判断是否在值班时间内，值班时间格式为：00:00-9:00
        try {
            String domainSbSql = "select * from alndomain where domainid='DUTYALN';";
            List<Domain> domainList = (List<Domain>) dao.executeQuery(domainSbSql,
                    new EntityListResult(Domain.class));
            //未在域里面找到值班时间配置
            if (domainList == null || domainList.size() <= 0) {
                return false;
            }
            for (Domain domain : domainList) {
                String value = domain.getValue();
                String[] time = value.split("-");
                if (time.length != 2) {
                    throw new HDException("值班时间数据格式异常，请联系管理员");
                }
                try {

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                    Date startdate = simpleDateFormat.parse(time[0]);
                    Date enddate = simpleDateFormat.parse(time[1]);
                    Date currentTime = simpleDateFormat.parse(simpleDateFormat.format(ServerDateManager.getCurrentDate()));
                    if (currentTime.after(startdate) && currentTime.before(enddate)) {
                        return true;
                    }
                } catch (ParseException e) {
                    logger.error(e.getMessage(), e);
                    throw new HDException("值班时间转化异常");
                }
            }
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            throw new HDException("数据库查询值班时间异常");
        }
        return false;

    }

    /**
     * 同一刷卡人，是否存在互斥的环节点刷卡
     *
     * @return
     */
    public void isRepellentHjd(SuperEntity nodeEntity,
                               String cardNum, SuperEntity workEntity) throws HDException {
        if (dao == null) {
            dao = new BaseDao();
        }
        //关系表查询是否启用互斥环节点配置
        QueryRelativeConfig queryRelation = new QueryRelativeConfig();
        if (!queryRelation.isHadRelative("ISQYHCHJDPZ")) {
            return;
        }

        PersonCard pcard = new PersonCard();
        pcard.setPcardnum(cardNum);
        SuperEntity personCard = getPersonCard(pcard);

        //查询互斥环节点配置表里面是否存在数据，
        List<RepellentHjd> relations = getRepellentHjdList();
        if (relations != null && !relations.isEmpty()) {
            //当前环节点，作业审批权限
            String ud_zyxk_zyspqxid = (String) nodeEntity.getAttribute("ud_zyxk_zyspqxid");
            List<String> zyspqxidList = new ArrayList<>();
            if (ud_zyxk_zyspqxid != null) {

                for (RepellentHjd repellentHjd : relations) {
                    String hjdOne = repellentHjd.getHjdone();
                    String hjdTwo = repellentHjd.getHjdtwo();
                    if (!TextUtils.isEmpty(hjdOne) &&
                            (hjdOne.startsWith(ud_zyxk_zyspqxid + ",")
                                    || hjdOne.contains("," + ud_zyxk_zyspqxid + ",")
                                    || hjdOne.endsWith("," + ud_zyxk_zyspqxid)
                                    || hjdOne.equals(ud_zyxk_zyspqxid)
                            )) {
                        if (!TextUtils.isEmpty(hjdTwo)) {
                            zyspqxidList.addAll(Arrays.asList(hjdTwo.split(",")));
                        }
                    } else if (!TextUtils.isEmpty(hjdTwo) &&
                            (hjdTwo.startsWith(ud_zyxk_zyspqxid + ",")
                                    || hjdTwo.contains("," + ud_zyxk_zyspqxid + ",")
                                    || hjdTwo.endsWith("," + ud_zyxk_zyspqxid)
                                    || hjdTwo.equals(ud_zyxk_zyspqxid)
                            )) {
                        if (!TextUtils.isEmpty(hjdOne)) {
                            zyspqxidList.addAll(Arrays.asList(hjdOne.split(",")));
                        }
                    }
                }
                //存在互斥环节点
                if (!zyspqxidList.isEmpty()) {

                    List<WorkApprovalPersonRecord> records = getRecordList((String) workEntity.getAttribute("ud_zyxk_zysqid"));
                    if (records != null && !records.isEmpty()) {
                        //这张票存在审批记录，筛选当前刷卡人是否存在审批记录
                        for (int i = 0; i < records.size(); i++) {
                            WorkApprovalPersonRecord record = records.get(i);
                            String[] persons = record.getPerson().split(",");
                            boolean hasRecord = false;
                            for (String person : persons) {
                                if (person.contains((String) personCard.getAttribute("personid"))) {
                                    hasRecord = true;
                                    break;
                                }
                            }
                            if (!hasRecord) {
                                records.remove(i);
                                i--;
                            }
                        }
                        //若当前刷卡人存在审批记录,遍历当前刷卡人审批记录，遍历互斥环节点spqxid，跟审批记录里面的spqxid对比
                        if (!records.isEmpty()) {
                            for (WorkApprovalPersonRecord record : records) {
                                for (String spqxid : zyspqxidList) {
                                    if (spqxid.equals(record.getUd_zyxk_zyspqxid())) {
                                        throw new HDException("不能同时作为<" + record.getValue() + ">签名！");
                                    }

                                }

                            }

                        }
                    }


                }


            }

        }

    }

    /**
     * 获取当前票的所有审批记录
     *
     * @param zysqid
     * @return
     * @throws HDException
     */
    private List<WorkApprovalPersonRecord> getRecordList(String zysqid) {
        String sql = "select * from ud_zyxk_zyspryjl where ud_zyxk_zysqid='" + zysqid + "';";
        List<WorkApprovalPersonRecord> records = null;
        try {
            records = (List<WorkApprovalPersonRecord>) dao.executeQuery(sql,
                    new EntityListResult(WorkApprovalPersonRecord.class));
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return records;
    }

    /**
     * @return
     * @throws HDException
     */
    private List<RepellentHjd> getRepellentHjdList() {
        List<RepellentHjd> relations = null;
        String sq = "select * from ud_zyxk_hchjdpz";
        try {
            relations = (List<RepellentHjd>) dao.executeQuery(sq,
                    new EntityListResult(RepellentHjd.class));
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return relations;
    }


}
